package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Contato;
import model.dao.ContatoDaoJdbc;
import model.dao.DaoFactory;
import start.projetopadrao.App;

public class FormularioController implements Initializable {

    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGravar;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtTelefone;
    @FXML
    private TextField txtEmail;

    private static Contato contatoSelecionado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(contatoSelecionado != null){
            txtNome.setText(contatoSelecionado.getNome());
            txtEmail.setText(contatoSelecionado.getEmail());
            txtTelefone.setText(contatoSelecionado.getTelefone());
        }
        contatoSelecionado = null;
    }    

    @FXML
    private void btnCancelarOnAction(ActionEvent event) throws IOException {
        App.setRoot("Principal");
    }

    @FXML
    private void btnGravarOnAction(ActionEvent event) throws Exception {
        Contato contato = new Contato();
        contato.setNome(txtNome.getText());
        contato.setEmail(txtEmail.getText());
        contato.setTelefone(txtTelefone.getText());

        ContatoDaoJdbc dao = DaoFactory.novoContatoDaoJdbc();

        if(contatoSelecionado == null){
            dao.incluir(contato);
        } else {
            contato.setId(contato.getId());
            dao.editar(contato);
            contatoSelecionado = null;
        }

        App.setRoot("Principal");
    }

    public static void setContatoSelecionado(Contato contatoSelecionado) {
        FormularioController.contatoSelecionado = contatoSelecionado;
    }

    public static Contato getContatoSelecionado() {
        return contatoSelecionado;
    }

    
}
