package control;

import java.sql.SQLException;

import DAO.MusicaDAO;
import DAO.MusicaDAOImpl;
import entity.Musica;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MusicaControl {
	private IntegerProperty id = new SimpleIntegerProperty(0);
	private StringProperty nome = new SimpleStringProperty("");
	private StringProperty cantor = new SimpleStringProperty("");
	private IntegerProperty ano = new SimpleIntegerProperty(0);
	
	private ObservableList<Musica> musicas = FXCollections.observableArrayList();
	
	private MusicaDAO musicaDAO;
	
	public MusicaControl() throws ClassNotFoundException, SQLException {
		musicaDAO = new MusicaDAOImpl();
	}
	
	public void novo() {
		fromEntity(new Musica());
	}
	
	public void salvar() throws SQLException {
		Musica m = new Musica();
		m.setNome(nome.get());
		m.setCantor(cantor.get());
		m.setAno(ano.get());
		if (id.get() == 0) {
			m = musicaDAO.adicionar( m );
			musicas.add(m);
		}else {
			musicaDAO.atualizar(id.get(), m);
		}
	}
	
	public void excluir(Musica m) throws SQLException {
		musicas.remove(m);
		musicaDAO.remover(m.getId());
	}
	
	public void fromEntity(Musica m) {
		id.set(m.getId());
		nome.set(m.getNome());
		cantor.set(m.getCantor());
		ano.set(m.getAno());
	}
	
	public void pesquisar() throws SQLException {
		musicas.clear();
		musicas.addAll(musicaDAO.pesquisarPorNome(nome.get()));
		System.out.println("Nome: " + nome.get());
	}
	
	public StringProperty nomeProperty() {
		return nome;
	}
	
	public StringProperty cantorProperty() {
		return cantor;
	}
	
	public IntegerProperty anoProperty() {
		return ano;
	}
	
	public IntegerProperty idProperty() {
		return id;
	}
	
	public ObservableList<Musica> getList(){
		return musicas;
	}
}
