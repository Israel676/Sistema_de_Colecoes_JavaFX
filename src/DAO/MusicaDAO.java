package DAO;

import java.sql.SQLException;
import java.util.List;

import entity.Musica;

public interface MusicaDAO {
	
	public Musica adicionar(Musica m) throws SQLException;
	public void remover(int id) throws SQLException;
	public void atualizar(int id, Musica m) throws SQLException;
	public List<Musica> pesquisarPorNome(String nome) throws SQLException;
	public Musica pesquisarPorId(int id) throws SQLException;
}
