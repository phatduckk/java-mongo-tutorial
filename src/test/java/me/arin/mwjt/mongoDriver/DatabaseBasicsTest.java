package me.arin.mwjt.mongoDriver;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import me.arin.mwjt.SetupMongo;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class DatabaseBasicsTest extends SetupMongo {
    private static final String ADMIN = "admin";

    @Test
    public void onlyHaveAdminDbAtStart() {
        final List<String> dbNames = mongo.getDatabaseNames();
        Assert.assertEquals(1, dbNames.size());
        Assert.assertEquals(ADMIN, dbNames.get(0));
    }

    @Test
    public void createDatabase() {
        final String newDbName = "newDb";
        final DB db = mongo.getDB(newDbName);

        // still only 1 DB cuz we haven't put anything in the new one yet...
        Assert.assertEquals(1, mongo.getDatabaseNames().size());

        // now lets create a collection w/ default options so make the DB creation "official"
        db.createCollection("testCollection", new BasicDBObject());

        final List<String> dbNames = mongo.getDatabaseNames();
        boolean foundAdmin = false;
        boolean foundNewDb = false;

        for (String name : dbNames) {
            if (name.equals(newDbName)) {
                foundNewDb = true;
            } else if (name.equals(ADMIN)) {
                foundAdmin = true;
            }
        }

        // make sure we've got admin and the new DB
        Assert.assertEquals(2, dbNames.size());
        Assert.assertTrue(foundAdmin && foundNewDb);
    }

    @Test
    public void dropDatabase() {
        final String someDB = "someDB";
        mongo.getDB(someDB).createCollection("someCollection", new BasicDBObject());

        // make sure someDB was created
        Assert.assertTrue(mongo.getDatabaseNames().contains(someDB));

        // drop it and make sure it's gone...
        mongo.dropDatabase(someDB);
        Assert.assertFalse(mongo.getDatabaseNames().contains(someDB));
    }
}
