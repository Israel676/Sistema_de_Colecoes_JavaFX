package boundary;

import java.sql.SQLException;

import control.FilmeControl;
import entity.Filme;
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

public class FilmeForm implements Tela {
	
	private TextField txtNome = new TextField();
	private TextField txtAno = new TextField();
	private Label lblId = new Label(""); 
	private FilmeControl control;
	private TableView<Filme> table = new TableView<>();
	private BorderPane principal;
	private Executor executor;
	
	
	public void limparCampos() {
		txtNome.setText("");
		txtAno.setText("");
	}
	
	public void ligacoes() {
		Bindings.bindBidirectional(txtNome.textProperty(), control.nomeProperty());
		Bindings.bindBidirectional(txtAno.textProperty(), control.anoProperty(), (StringConverter)new IntegerStringConverter());
		Bindings.bindBidirectional(lblId.textProperty(), control.idProperty(), (StringConverter)new IntegerStringConverter());
	}
	
	public void adicionar() {
		try {
			control.salvar();
			Alert a = new Alert(AlertType.INFORMATION, "Filme adicionado com sucesso", ButtonType.OK);
			a.showAndWait();
			control.novo();
		}catch(SQLException e) {
			Alert a = new Alert(AlertType.ERROR, "Erro ao adicionar o filme " + e.getMessage(), ButtonType.OK);
			a.showAndWait();
		}
	}
	
	public void prepararTabela() {
		TableColumn<Filme, String> col1 = new TableColumn<>("Nome");
		col1.setCellValueFactory(new PropertyValueFactory<Filme, String>("nome"));
		TableColumn<Filme, Integer> col2 = new TableColumn<>("Ano");
		col2.setCellValueFactory(new PropertyValueFactory<Filme, Integer>("Ano"));
		
		TableColumn<Filme, Void> col3 = new TableColumn<>("Ações");
		Callback<TableColumn<Filme, Void>, TableCell<Filme, Void>> acoes = new Callback<>() {

			@Override
			public TableCell<Filme, Void> call(TableColumn<Filme, Void> param) {
				final Button btnExcluir = new Button("Excluir");
				TableCell <Filme, Void> cell = new TableCell<>() {
					{
						btnExcluir.setOnAction(event -> {
					Filme data = table.getItems().get(getIndex());
					try {
						control.excluir(data);
					}catch(SQLException e ) {
						Alert a = new Alert(AlertType.ERROR, "erro ao excluir o filme " + e.getMessage(), ButtonType.OK);
						a.showAndWait();
					}
				});
					}
					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if(empty) {
							setGraphic(null);
						}else{
							setGraphic(btnExcluir);
						}
					}
						};
						return cell;	
			}
		};
		col3.setCellFactory(acoes);
		table.getColumns().addAll(col1, col2, col3);
		table.setItems(control.getList());
		table.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Filme>() {
			@Override
			public void onChanged(Change<? extends Filme> f) {
				if (!f.getList().isEmpty()) {
					control.fromEntity(f.getList().get(0));
				}
			}
		}
	
	  );
			
	}


	@Override
	public void start() throws Exception {
		try {
			control = new FilmeControl();
		} catch (SQLException e) {
			Alert a = new Alert(AlertType.ERROR, "erro ao conectar no banco de dados" + e.getMessage(), ButtonType.OK);
			a.showAndWait();
		}
		principal = new BorderPane();
		principal.setPadding(new Insets(50));
		GridPane painelForm = new GridPane();
		principal.setTop(new VBox(new Label("Coleção de Filmes"), painelForm));
		principal.setCenter(table);
		
		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPercentWidth(30);
		ColumnConstraints col2 = new ColumnConstraints();
		col2.setPercentWidth(70);
		painelForm.getColumnConstraints().addAll(col1, col2);
		
		Button btnNovo = new Button("Novo Filme");
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
			} catch (SQLException err) { 
				Alert a = new Alert(AlertType.ERROR, "Erro ao pesquisar pelo Nome " + err.getMessage(), ButtonType.OK);
     			a.showAndWait();
			}
		});
			
		painelForm.add(new Label("Id"), 0, 0);
		painelForm.add(lblId, 1, 0);
		painelForm.add(new Label("Nome"), 0, 1);
		painelForm.add(txtNome, 1, 1);
		painelForm.add(new Label("Ano"), 0, 2);
		painelForm.add(txtAno, 1, 2);
		
		FlowPane fpbotoes = new FlowPane();
		fpbotoes.getChildren().addAll(btnSalvar, btnPesquisar);
		
		painelForm.add(btnNovo, 0, 4);
		painelForm.add(fpbotoes, 1, 4);
		
		ligacoes();
		prepararTabela();
	}

	@Override
	public Pane render() {
		
		return principal;
	}
	
}
