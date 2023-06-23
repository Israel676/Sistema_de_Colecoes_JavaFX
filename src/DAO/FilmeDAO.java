package DAO;

import java.sql.SQLException;
import java.util.List;

import entity.Filme;

public interface FilmeDAO {
	
	public Filme adicionar(Filme f) throws SQLException;
	public void remover(int id) throws SQLException;
	public void atualizar(int id, Filme f) throws SQLException;
	public List<Filme> pesquisarPorNome(String nome) throws SQLException;
	public Filme pesquisarPorId(int id) throws SQLException;
}
