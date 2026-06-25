package dao;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import database.DatabaseManager;
import model.Produto;

public class ProdutoDAO {

    private Dao<Produto, Integer> dao;

    public ProdutoDAO() throws SQLException {
        dao = DaoManager.createDao(
            DatabaseManager.getConnection(),
            Produto.class
        );
    }

    public void inserir(Produto produto)
            throws SQLException {

        dao.create(produto);
    }

    public List<Produto> listar()
            throws SQLException {

        return dao.queryForAll();
    }

    public void atualizar(Produto produto)
            throws SQLException {

        dao.update(produto);
    }

    public void excluir(int id)
            throws SQLException {

        dao.deleteById(id);
    }
}
