package database;

import java.sql.SQLException;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

public class DatabaseManager {

    private static final String URL =
        "jdbc:sqlite:ru.db";

    private static ConnectionSource connection;

    public static ConnectionSource getConnection()
            throws SQLException {

        if(connection == null) {
            connection =
                new JdbcConnectionSource(URL);
        }

        return connection;
    }
}

package database;

import java.sql.SQLException;

import com.j256.ormlite.table.TableUtils;

import model.Produto;

public class CriarTabelas {

    public static void criar() throws SQLException {

        TableUtils.createTableIfNotExists(
            DatabaseManager.getConnection(),
            Produto.class
        );
    }
}
