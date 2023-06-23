package control;

import java.sql.SQLException;

import DAO.FilmeDAO;
import DAO.FilmeDAOImpl;
import entity.Filme;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FilmeControl {
	private IntegerProperty id = new SimpleIntegerProperty(0);
	private StringProperty nome = new SimpleStringProperty("");
	private IntegerProperty ano = new SimpleIntegerProperty(0);
	private ObservableList<Filme> filmes = FXCollections.observableArrayList(); 
	
	private FilmeDAO filmeDAO;
	
	
	public FilmeControl() throws ClassNotFoundException, SQLException  {
		filmeDAO = new FilmeDAOImpl();
	}
	
	public void novo() {
		fromEntity(new Filme());
	}
	
	public void salvar() throws SQLException {
		Filme f = new Filme();
		f.setNome(nome.get());
		f.setAno(ano.get());
		if (id.get() == 0) {
			f = filmeDAO.adicionar(f);
			filmes.add(f);
		}else {
			filmeDAO.atualizar(id.get(), f);
		}
	}
	
	public void excluir(Filme f) throws SQLException {
		filmes.remove(f);
		filmeDAO.remover(f.getId());
	}
	
	public void fromEntity(Filme f) {
		id.set(f.getId());
		nome.set(f.getNome());
		ano.set(f.getAno());
	}
	
	public void pesquisar() throws SQLException {
		filmes.clear();
		filmes.addAll(filmeDAO.pesquisarPorNome(nome.get()));
		System.out.println("Nome: "+ nome.get());
	}
	
	public IntegerProperty idProperty() {
		return id;
	}
	
	public StringProperty nomeProperty() {
		return nome;
	}
	
	public IntegerProperty anoProperty() {
		return ano;
	}
	
	public ObservableList<Filme> getList(){
		return filmes;
	}
}
