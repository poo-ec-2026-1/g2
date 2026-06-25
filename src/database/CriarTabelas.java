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

