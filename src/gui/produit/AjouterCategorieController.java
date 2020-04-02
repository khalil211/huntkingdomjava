/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.produit;



import entities.produit.Categorie;
import services.produit.CategorieService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author user
 */
public class AjouterCategorieController implements Initializable {
    
    @FXML
    protected TextArea DescriptionArea;
    @FXML
    private Button enregistrerButton;
    @FXML
    private Button annulerButton;
    @FXML
    private TextField NomField;
    
   // protected static int catId;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
  
 

    @FXML
    private void Enregistrer(ActionEvent event) throws IOException {
         if( !NomField.getText().isEmpty() && !DescriptionArea.getText().isEmpty()){
            CategorieService cs = new CategorieService();
           
            Categorie c = new Categorie(NomField.getText(),DescriptionArea.getText());
            cs.ajouterCat(c);
            
        
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListeCategorie.fxml"));
            Parent root = loader.load();
            NomField.getScene().setRoot(root);
        }
         TrayNotification tray =new TrayNotification();
            tray.setTitle("Succès");
        tray.setMessage("Ajout avec succès !");
        tray.setAnimationType(AnimationType.POPUP);
        tray.setNotificationType(NotificationType.INFORMATION);
        tray.showAndWait();
    }

    @FXML
    private void Annuler(ActionEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("ListeCategorie.fxml"));
        Parent root = loader.load();
        NomField.getScene().setRoot(root);
    }
    
}

