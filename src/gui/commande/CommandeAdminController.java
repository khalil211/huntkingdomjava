package gui.commande;

import entities.commande.Commande;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import huntkingdom.HuntKingdom;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.commande.CommandeService;

/**
 * FXML Controller class
 *
 * @author khalil
 */
public class CommandeAdminController implements Initializable {

    @FXML private TableView<Commande> listeCommandes;
    @FXML private TableColumn<Commande, String> clientCol;
    @FXML private TableColumn<Commande, Integer> nbProduitsCol;
    @FXML private TableColumn<Commande, Double> totalCol;
    @FXML private TableColumn<Commande, String> dateCol;
    @FXML private TableColumn<Commande, String> etatCol;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clientCol.setCellValueFactory(new PropertyValueFactory<Commande, String>("username"));
        nbProduitsCol.setCellValueFactory(new PropertyValueFactory<Commande, Integer>("nbProduits"));
        totalCol.setCellValueFactory(new PropertyValueFactory<Commande, Double>("total"));
        dateCol.setCellValueFactory(new PropertyValueFactory<Commande, String>("dateToString"));
        etatCol.setCellValueFactory(new PropertyValueFactory<Commande, String>("etatToString"));
        ObservableList<Commande> commandesObs=FXCollections.observableArrayList();
        CommandeService cs=new CommandeService();
        for(Commande c : cs.getAllCommandes())
            commandesObs.add(c);
        listeCommandes.setItems(commandesObs);
    }    

    @FXML
    private void toMenuAdmin(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/MenuAdmin.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene);
    }
    
}
