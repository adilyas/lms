package Database;

import java.sql.Connection;

public class Database {
    private Connection connection;


    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }
}
