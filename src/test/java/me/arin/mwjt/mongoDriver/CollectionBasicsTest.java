package me.arin.mwjt.mongoDriver;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import me.arin.mwjt.SetupMongo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CollectionBasicsTest extends SetupMongo {
    public static final String DB_NAME = "CollectionBasicsTestDB";
    public static final String COLLECTION_NAME = "myRadCollection";

    protected static DB db = null;

    @BeforeClass
    public static void beforeClass() throws Exception {
        SetupMongo.setUp();
        db = mongo.getDB(DB_NAME);
    }

    @Before
    public void beforeMethod() {
        db.getCollection(COLLECTION_NAME).drop();
    }

    @Test
    public void collectionDoesntExist() {
        Assert.assertFalse(db.collectionExists(COLLECTION_NAME));
    }

    @Test
    public void newCollection() {
        Assert.assertFalse(db.collectionExists(COLLECTION_NAME));

        // create the collection & ensure its there...
        final DBCollection collection = db.createCollection(COLLECTION_NAME, new BasicDBObject());
        Assert.assertTrue(db.collectionExists(COLLECTION_NAME));

        // check the name
        Assert.assertEquals(COLLECTION_NAME, collection.getName());

        // check that there's nothing in it
        Assert.assertEquals(0, collection.count());

        // make sure its in the right DB
        Assert.assertEquals(DB_NAME, collection.getDB().getName());
    }

    @Test
    public void dropCollection() {
        // make sure it aint there to begin with
        Assert.assertFalse(db.collectionExists(COLLECTION_NAME));

        // create the collection & ensure its there...
        final DBCollection collection = db.createCollection(COLLECTION_NAME, new BasicDBObject());
        Assert.assertTrue(db.collectionExists(COLLECTION_NAME));

        // drop it and make sure its gone
        collection.drop();
        Assert.assertFalse(db.collectionExists(COLLECTION_NAME));
    }
}
