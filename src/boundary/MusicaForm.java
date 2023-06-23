package boundary;

import java.sql.SQLException;

import control.MusicaControl;
import entity.Musica;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

public class MusicaForm implements Tela {
	private TextField txtNome = new TextField();
	private TextField txtCantor = new TextField();
	private TextField txtAno = new TextField();
	private Label lblId = new Label("");
	private MusicaControl control;
	private TableView<Musica> table = new TableView<>();
	private BorderPane principal;
	private Executor executor;
	
	
	public void limparCampos() {
		txtNome.setText("");
		txtCantor.setText("");
		txtAno.setText("");
	}
	
	public void ligacoes() {
		Bindings.bindBidirectional(txtNome.textProperty(), control.nomeProperty());
		Bindings.bindBidirectional(txtCantor.textProperty(), control.cantorProperty());
		Bindings.bindBidirectional(txtAno.textProperty(), control.anoProperty(), (StringConverter)new IntegerStringConverter());
		Bindings.bindBidirectional(lblId.textProperty(), control.idProperty(), (StringConverter)new IntegerStringConverter());
	}
	
	public void adicionar() {
		try {
			control.salvar();
			Alert a = new Alert(AlertType.INFORMATION, "musica adicionada com sucesso", ButtonType.OK);
			a.showAndWait();
		}catch(SQLException e) {
			Alert a = new Alert(AlertType.ERROR, "erro ao adicionar musica " + e.getMessage(), ButtonType.OK);
			a.showAndWait();
		}
	}
	
	public void prepararTabela() {
		TableColumn<Musica, String> col1 = new TableColumn<>("Nome");
		col1.setCellValueFactory(new PropertyValueFactory<Musica, String>("nome"));
		TableColumn<Musica, String> col2 = new TableColumn<>("Cantor");
		col2.setCellValueFactory(new PropertyValueFactory<Musica, String>("cantor"));
		TableColumn<Musica, Integer> col3 = new TableColumn<>("Ano");
		col3.setCellValueFactory(new PropertyValueFactory<Musica, Integer>("ano"));
		
		TableColumn<Musica, Void> col4 = new TableColumn<>("Ações");
		Callback<TableColumn<Musica, Void>, TableCell<Musica, Void>> acoes = new Callback<>() {

			@Override
			public TableCell<Musica, Void> call(TableColumn<Musica, Void> param) {
				final Button btnExcluir = new Button("Excluir");
				TableCell <Musica, Void> cell = new TableCell<>() {
					{
						btnExcluir.setOnAction(event -> {
					Musica data = table.getItems().get(getIndex());
					try {
						control.excluir(data);
					}catch(SQLException e){
						Alert a = new Alert(AlertType.ERROR, "Erro ao excluir a musica " + e.getMessage(), ButtonType.OK);
						a.showAndWait();
					}
				});
					}
					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if(empty) {
							setGraphic(null);
						}else {
							setGraphic(btnExcluir);
						}
					}
				};
				
				return cell;
			}
			
		};
		col4.setCellFactory(acoes);
		table.getColumns().addAll(col1, col2, col3, col4);
		table.setItems(control.getList());
		table.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Musica>() {
			@Override
			public void onChanged(Change<? extends Musica> m) {
				if(!m.getList().isEmpty()) {
					control.fromEntity(m.getList().get(0));
				}
			}
		}
		
	  );
		
    }
	

	@Override
	public void start() throws Exception {
		try {
			control = new MusicaControl();
		}catch(SQLException e) {
			Alert a = new Alert(AlertType.ERROR, "erro ao conectar no banco de dados" + e.getMessage(), ButtonType.OK);
			a.showAndWait();
		}
		principal = new BorderPane();
		principal.setPadding(new Insets(50));
		GridPane painelForm = new GridPane();
		principal.setTop(new VBox(new Label("Coleção de Musicas"), painelForm));
		principal.setCenter(table);
		
		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPercentWidth(30);
		ColumnConstraints col2 = new ColumnConstraints();
		col2.setPercentWidth(70);
		painelForm.getColumnConstraints().addAll(col1, col2);
		
		Button btnNovo = new Button("Nova Musica");
		Button btnSalvar = new Button("Salvar");
		Button btnPesquisar = new Button("Pesquisar");
		
		btnNovo.setOnAction(e -> {
			control.novo();
		});
		
		btnSalvar.setOnAction((e)->{
			adicionar();
		});
		
		btnPesquisar.setOnAction((e)->{
			try {
				control.pesquisar();
			}catch(SQLException err) {
				Alert a = new Alert(AlertType.ERROR, "erro ao pesquisar pelo nome " + err.getMessage(), ButtonType.OK);
				a.showAndWait();
			}
		});
		
		painelForm.add(new Label("Id"), 0, 0);
		painelForm.add(lblId, 1, 0);
		painelForm.add(new Label("Nome"), 0, 1);
		painelForm.add(txtNome, 1, 1);
		painelForm.add(new Label("Cantor/Banda"), 0, 2);
		painelForm.add(txtCantor, 1, 2);
		painelForm.add(new Label("Ano"), 0, 3);
		painelForm.add(txtAno, 1, 3);
		
		FlowPane fpBotoes = new FlowPane();
		fpBotoes.getChildren().addAll(btnSalvar, btnPesquisar);
		
		painelForm.add(btnNovo, 0, 4);
		painelForm.add(fpBotoes, 1, 4);
		
		ligacoes();
		prepararTabela();
	}

	@Override
	public Pane render() {

		return principal;
	}

}
