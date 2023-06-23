package entity;

public class Musica {
	int id;
	String nome;
	String cantor;
	int ano;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCantor() {
		return cantor;
	}
	
	public void setCantor(String cantor) {
		this.cantor = cantor;
	}
	
	public int getAno() {
		return ano;
	}
	
	public void setAno(int ano) {
		this.ano = ano;
	}
	
	@Override
	public String toString() {
		return "Musica [id=" + id + ", nome=" + nome + ", cantor=" + cantor + ", ano=" + ano + "]";
	}
	
}
