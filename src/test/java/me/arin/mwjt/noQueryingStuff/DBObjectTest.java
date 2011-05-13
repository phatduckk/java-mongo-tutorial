package me.arin.mwjt.noQueryingStuff;

import com.mongodb.BasicDBObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class DBObjectTest {
    @Test
    public void itsAMap() {
        final BasicDBObject basicDBObject = new BasicDBObject();
        Assert.assertTrue(basicDBObject instanceof Map);
    }

    @Test
    public void lookItsFluent() {
        final String k1 = "k1";
        final String k2 = "k2";
        final String k3 = "k3";

        final String v1 = "v1";
        final String v2 = "v2";

        final BasicDBObject basicDBObject = new BasicDBObject(k1, v1).append(k2, v2);
        basicDBObject.append(k3, new BasicDBObject());

        Assert.assertTrue(basicDBObject.containsField(k1));
        Assert.assertTrue(basicDBObject.containsField(k2));
        Assert.assertTrue(basicDBObject.containsField(k3));

        Assert.assertEquals(basicDBObject.get(k1), v1);
        Assert.assertEquals(basicDBObject.get(k2), v2);
        Assert.assertTrue(basicDBObject.get(k3) instanceof BasicDBObject);

        basicDBObject.removeField(k1);
        Assert.assertEquals(null, basicDBObject.get(k1));
    }
}
