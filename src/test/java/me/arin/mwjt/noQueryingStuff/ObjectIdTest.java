package me.arin.mwjt.noQueryingStuff;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;

public class ObjectIdTest {
    @Test
    public void getAnObjectId() {
        final ObjectId objectId = ObjectId.get();

        // just set a breakpoint here and scope out what an ObjectId looks like in the debugger
        // this assert is pretty worthless - but u should check your debugger
        Assert.assertTrue(objectId instanceof ObjectId);
    }

    @Test
    public void cantUseARandomString() {
        try {
            new ObjectId("thisIsARandomString");
            Assert.assertTrue(false);
        } catch (IllegalArgumentException e) {
            Assert.assertTrue("see you cant use random strings. ObjectIds have a special format", true);
        }

        Assert.assertFalse(ObjectId.isValid("something"));
        Assert.assertFalse(ObjectId.isValid("1"));
        Assert.assertFalse(ObjectId.isValid("666"));
        Assert.assertTrue(ObjectId.isValid(ObjectId.get().toString()));
    }

    @Test
    public void objectIdPeices() throws NoSuchAlgorithmException {
        final ObjectId objectId = ObjectId.get();
        final int nowIsh = (int) (System.currentTimeMillis()/1000L);

        // its 12 bytes & looks like:
        // ----------------------------------
        // 0123	| 456     | 78  | 9 10 11
        // time	| machine | pid	| inc
        // ----------------------------------
        final byte[] bytes = objectId.toByteArray();
        Assert.assertEquals(12, bytes.length);

        // lets look at the bytes...
        ByteBuffer bb = ByteBuffer.allocate(12).put(bytes);

        // see - the first 4 bytes are seconds since epoch...
        Assert.assertEquals(objectId.getTimeSecond(), bb.getInt(0));
        Assert.assertEquals(nowIsh, bb.getInt(0));
    }
}
