package me.arin.mwjt;

import com.mongodb.Mongo;
import org.junit.BeforeClass;

public class SetupMongo {
    protected static Mongo mongo;
    private static final String PORT = "27666";

    @BeforeClass
    public static void setUp() throws Exception {
        final int port = Integer.parseInt(System.getProperty("mongo.port", PORT));

        try {
            mongo = new Mongo("localhost", port);
            for (String dbName : mongo.getDatabaseNames()) {
                mongo.dropDatabase(dbName);
            }
        } catch (Exception e) {
            throw new SetupMongoException();
        }
    }

    public static Mongo getMongo() {
        return mongo;
    }

    private static class SetupMongoException extends Exception {
        public SetupMongoException() {
            super(new StringBuilder("Run mongo on port ").append(PORT)
                                                         .append(". via: /path/to/executable/mongod --port ")
                                                         .append(PORT).toString());
        }
    }
}
