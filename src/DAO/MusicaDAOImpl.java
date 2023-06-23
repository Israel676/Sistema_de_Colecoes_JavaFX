package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Musica;

public class MusicaDAOImpl implements MusicaDAO {
	private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
	private static final String JDBC_URL = "jdbc:mariadb:"
										+ "//localhost:3306/colecao?allowMultiQueries=true";
	private static final String JDBC_USER = "root";
	private static final String JDBC_PASS = "";
	private Connection con;
	
	public MusicaDAOImpl() throws ClassNotFoundException, SQLException {
		Class.forName(JDBC_DRIVER);
		con = DriverManager.getConnection(JDBC_URL, 
				JDBC_USER, JDBC_PASS);
	}
	

	@Override
	public Musica adicionar(Musica m) throws SQLException {
		String sql = "INSERT INTO musicas (nome, cantor, ano)" + "VALUES (?, ?, ?)";
		PreparedStatement st = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		st.setString(1, m.getNome());
		st.setString(2, m.getCantor());
		st.setInt(3, m.getAno());
		st.executeUpdate();
		ResultSet rs = st.getGeneratedKeys();
		if (rs.next()) {
			m.setId(rs.getInt(1));
		}		
		return m;
	}

	@Override
	public void remover(int id) throws SQLException {
		String sql = "DELETE FROM musicas WHERE id = ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, id);
		st.executeUpdate();
	}

	@Override
	public void atualizar(int id, Musica m) throws SQLException {
		String sql = "UPDATE musicas SET nome=?, cantor=?, ano=? " + " WHERE id=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, m.getNome());
		st.setString(2, m.getCantor());
		st.setInt(3, m.getAno());
		st.setInt(4, id);
		st.executeUpdate();
		
	}

	@Override
	public List<Musica> pesquisarPorNome(String nome) throws SQLException {
		List<Musica> lista = new ArrayList<>();
		String sql = "SELECT * FROM musicas " + "WHERE nome LIKE ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%" + nome + "%");
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			Musica m = new Musica();
			m.setId(rs.getInt("id"));
			m.setNome(rs.getString("nome"));
			m.setCantor(rs.getString("cantor"));
			m.setAno(rs.getInt("ano"));
			lista.add(m);
		}
		return lista;
	}

	@Override
	public Musica pesquisarPorId(int id) throws SQLException {

		return null;
	}

}
