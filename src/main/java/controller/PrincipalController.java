package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Contato;
import model.dao.ContatoDaoJdbc;
import model.dao.DaoFactory;
import start.projetopadrao.App;

public class PrincipalController implements Initializable {

    @FXML
    private TableView<Contato> tblContato;
    @FXML
    private TableColumn<Contato, String> tblColNome;
    @FXML
    private TableColumn<Contato, String> tblColEmail;
    @FXML
    private TableColumn<Contato, String> tblColTelefone;
    @FXML
    private Button btnIncluir;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnExcluir;
    @FXML
    private TextField txtFiltro;
    @FXML
    private Button btnFiltrar;
    @FXML
    private Button btnLimpar;

    private List<Contato> listaContato;
    private ObservableList<Contato> observableListContato;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarContatos("");
    }

    @FXML
    private void btnIncluirOnAction(ActionEvent event) throws IOException {
        
        App.setRoot("Formulario");
    }

    @FXML
    private void btnEditarOnAction(ActionEvent event) throws IOException {
        Contato contatoSelecionado = tblContato.selectionModelProperty().getValue().getSelectedItem();

        if(contatoSelecionado != null){
            FormularioController.setContatoSelecionado(contatoSelecionado);
            App.setRoot("Formulario");
        }

        carregarContatos(txtFiltro.getText());
    }

    @FXML
    private void btnExcluirOnAction(ActionEvent event) throws Exception {
        Contato contatoSelecionado = tblContato.selectionModelProperty().getValue().getSelectedItem();

        if(contatoSelecionado != null){
            ContatoDaoJdbc dao = DaoFactory.novoContatoDaoJdbc();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Aviso");
            alert.setContentText("confirma exclusao de " + contatoSelecionado.getNome() + "?");

            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK){
                try {
                    dao.excluir(contatoSelecionado);
                } catch (Exception e) {
                    String mensagem = "Ocorreu um erro: " + e.getMessage();

                    Alert alertErro = new Alert(Alert.AlertType.INFORMATION);
                    alertErro.setTitle("Aviso");
                    alertErro.setContentText(mensagem);
                    alertErro.showAndWait();
                }
            }
        }

        carregarContatos(txtFiltro.getText());
    }

    @FXML
    private void btnFiltrarOnAction(ActionEvent event) {
        carregarContatos(txtFiltro.getText());
    }

    @FXML
    private void btnLimparOnAction(ActionEvent event) {
        txtFiltro.clear();
        carregarContatos("");
    }

    public void carregarContatos(String param) {
        tblColNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        tblColEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        tblColTelefone.setCellValueFactory(new PropertyValueFactory<>("Telefone"));

        try {
            ContatoDaoJdbc dao = DaoFactory.novoContatoDaoJdbc();
            listaContato = dao.listar(param);
        } catch (Exception e) {
            e.printStackTrace();
        }

        observableListContato = FXCollections.observableArrayList(listaContato);
        tblContato.setItems(observableListContato);
    }

}
