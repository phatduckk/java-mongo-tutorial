package me.arin.mwjt.mongoDriver;

import com.mongodb.*;
import me.arin.mwjt.SetupMongo;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public final class InsertDocument extends SetupMongo {
    protected static DB db = null;

    @BeforeClass
    public static void beforeClass() throws Exception {
        SetupMongo.setUp();
        db = mongo.getDB(CollectionBasicsTest.DB_NAME);
    }

    private DBCollection getCollection() {
        return db.getCollection(CollectionBasicsTest.COLLECTION_NAME);
    }

    @Test
    public void insertADocument() {
        final DBCollection collection = getCollection();
        final BasicDBObject document = new BasicDBObject("name", "Arin").append("age", 32).append(
                "favoriteMovies",
                new String[]{"Scream", "Halloween", "Donnie Darko"});

        collection.insert(document);
        Assert.assertEquals(1, collection.count());
    }

    @Test
    public void insertMultipleDocuments() {
        final DBCollection collection = getCollection();

        final List<DBObject> docs = new ArrayList<DBObject>();
        docs.add(new BasicDBObject("hello", "there"));
        docs.add(new BasicDBObject("deez", "nuts"));
        docs.add(new BasicDBObject("numberOfTheBeast", 666));

        // insert a list
        collection.insert(docs);
        Assert.assertEquals(docs.size(), collection.count());

        // we can insert an array of docs too
        final BasicDBObject[] docArray = {new BasicDBObject("yo", "dawg"), new BasicDBObject("a", 1)};
        collection.insert(docArray);
        Assert.assertEquals(docArray.length + docs.size(), collection.count());

        // you can use the ... thing too
        collection.insert(new BasicDBObject("9", "99"), new BasicDBObject("kill", "bill"));
        Assert.assertEquals(2 + docArray.length + docs.size(), collection.count());
    }

    @Test
    public void automaticallyAssigned_id() {
        final DBCollection collection = getCollection();
        final String idField = "_id";

        final BasicDBObject basicDBObject = new BasicDBObject("gimmie", "anId");
        Assert.assertFalse(basicDBObject.containsField(idField));

        // look ma - the driver gives me an ObjectId if I don't specify _id myself
        collection.insert(basicDBObject);
        Assert.assertTrue(basicDBObject.containsField(idField));
        Assert.assertTrue(basicDBObject.get(idField) instanceof ObjectId);
    }

    @Test
    public void dontHaveToUseObjectIdfor_id() {
        int i = 0;

        final DBCollection collection = getCollection();
        final String idField = "_id";
        final String myId = "myId";

        final BasicDBObject basicDBObject = new BasicDBObject(idField, myId).append("i", i++);
        collection.insert(basicDBObject);

        // just proving that it inserted fine
        Assert.assertEquals(1, collection.count());

        // grab the first (aka only) thing in this collection - the doc we just inserted
        final DBObject docFromDB = collection.findOne();
        Assert.assertNotNull(docFromDB);
        Assert.assertEquals(myId, docFromDB.get(idField));

        // _id doesnt have to be an ObjectId. It can be an int (or whatever else) too...
        // lets insert a doc with an int for _id
        collection.insert(new BasicDBObject(idField, 1).append("i", i));

        // sorting "backwards" - more on this stuff in the querying tests
        // for now just trust me that we're just getting the last doc
        final DBCursor cursor = collection.find().sort(new BasicDBObject("i", -1)).limit(1);
        final DBObject lastItemInCollection = cursor.iterator().next();

        Assert.assertEquals(1, lastItemInCollection.get("_id"));

    }
}
