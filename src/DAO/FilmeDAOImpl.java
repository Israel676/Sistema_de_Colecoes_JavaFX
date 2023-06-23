package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Filme;

public class FilmeDAOImpl implements FilmeDAO {
	private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
	private static final String JDBC_URL = "jdbc:mariadb:"
										+ "//localhost:3306/colecao?allowMultiQueries=true";
	private static final String JDBC_USER = "root";
	private static final String JDBC_PASS = "";
	private Connection con;
	
	
	public FilmeDAOImpl() throws ClassNotFoundException, SQLException {
		Class.forName(JDBC_DRIVER);
		con = DriverManager.getConnection(JDBC_URL, 
				JDBC_USER, JDBC_PASS);
	}
	
	
	@Override
	public Filme adicionar(Filme f) throws SQLException {
		String sql = "INSERT INTO filmes (nome, ano)" + "VALUES (?, ?)";
		PreparedStatement st = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		st.setString(1, f.getNome());
		st.setInt(2, f.getAno());
		st.executeUpdate();
		ResultSet rs = st.getGeneratedKeys();
		if(rs.next()) {
			f.setId(rs.getInt(1));
		}		
		return f;
	}

	@Override
	public void remover(int id) throws SQLException {
		String sql = "DELETE FROM filmes WHERE id = ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, id);
		st.executeUpdate();
	}

	@Override
	public void atualizar(int id, Filme f) throws SQLException {
		String sql = "UPDATE filmes SET nome=?, ano=? " + " WHERE id=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, f.getNome());
		st.setInt(2, f.getAno());
		st.setInt(3, id);
		st.executeUpdate();
	}

	@Override
	public List<Filme> pesquisarPorNome(String nome) throws SQLException {
		List<Filme> lista = new ArrayList<>();
		String sql = "SELECT * FROM filmes " + "WHERE nome LIKE ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%" + nome + "%");
		ResultSet rs = st.executeQuery();
		while(rs.next()) {
			Filme f = new Filme();
			f.setId(rs.getInt("id"));
			f.setNome(rs.getString("nome"));
			f.setAno(rs.getInt("ano"));
			lista.add(f);
		}		
		return lista;
	}

	@Override
	public Filme pesquisarPorId(int id) throws SQLException {

		return null;
	}

}
