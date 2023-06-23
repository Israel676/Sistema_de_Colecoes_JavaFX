package boundary;


import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TelaPrincipal extends Application {
	private Map<String, Tela> telas = new HashMap<>(); 
	
	public void gerarTelas() throws Exception {
		telas.put("filme", new FilmeForm());
		telas.put("musica", new MusicaForm());
		
		for(Tela t: telas.values()) {
			t.start();
		}
	}
	
	public Tela getTela(String nome) {
		return telas.get(nome);
	}

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane bp = new BorderPane();
		gerarTelas();
		
		TabPane tabs = new TabPane();
		Tab tabFilme = new Tab("filme", new Label("Filmes"));
		Tab tabMusica = new Tab("musica", new Label("Musicas"));
		
		MenuBar mnuBar = new MenuBar();
		Menu mnuCadastro = new Menu("Cadastro");
		mnuBar.getMenus().add(mnuCadastro);
		
		MenuItem mnuFilme = new MenuItem("Filmes");
		MenuItem mnuMusica = new MenuItem("Musicas");
		mnuCadastro.getItems().addAll(mnuFilme, mnuMusica);
		
		mnuFilme.setOnAction((e)->{
			if(!tabs.getTabs().contains(tabFilme)) {
				tabFilme.setContent(getTela("filme").render());
				tabs.getTabs().add(tabFilme);
			}
		});
		
		mnuMusica.setOnAction((e)->{
			if(!tabs.getTabs().contains(tabMusica)) {
				tabMusica.setContent(getTela("musica").render());
				tabs.getTabs().add(tabMusica);
			}
		});
		
		bp.setTop(mnuBar);
		bp.setCenter(tabs);
		
		Scene scn = new Scene(bp, 800, 600);
		
		stage.setScene(scn);
		stage.setTitle("Gerenciador de Coleções");
		stage.show();
	}
	
	public static void main(String[] args) {
		Application.launch(TelaPrincipal.class, args);
	}
	
}
