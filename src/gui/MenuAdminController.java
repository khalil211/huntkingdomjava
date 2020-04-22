package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import huntkingdom.HuntKingdom;
import entities.user.CurrentUser;
import java.io.IOException;
import javafx.event.ActionEvent;

/**
 * FXML Controller class
 *
 * @author khalil
 */
public class MenuAdminController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void gestionCommandes(MouseEvent event) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("/gui/commande/CommandeAdmin.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene);
    }
     @FXML
    private void gestionChien(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/chien/ChienAdmin.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene);
    }

    @FXML
    private void gestionAnimaux(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/animal/MenuAdmin.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene);
    }
    @FXML
    private void chienclient(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/chien/ChienClient.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene);
    }

    @FXML
    private void shop(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/produit/ListeProduits.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene);
    }
    @FXML
    private void produitback(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/produit/ListeProduit.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene);
    }
     @FXML
    private void gestionCoach(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/coach/CoachAdmin.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene);
    }

    @FXML
    private void gestionUsers(ActionEvent event) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/userback/UserBack.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene);
    }

    @FXML
    private void listeanimaux(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/animal/ListeAnimals.fxml"));

        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene);
    }
    
}
