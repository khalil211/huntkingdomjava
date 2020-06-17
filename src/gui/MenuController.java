/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.user.CurrentUser;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import services.commande.CommandeService;
import huntkingdom.HuntKingdom;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author khalil
 */
public class MenuController implements Initializable {

    @FXML
    private BorderPane content;
    @FXML
    private Label panier;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/gui/profile/Profile.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        content.setCenter(root);
        panier.setText("Panier ("+new CommandeService().getPanier().getNbProduits()+")");
    }
    
    @FXML
    private void shop(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/produit/ListeProduits.fxml"));
        content.setCenter(root);
    }

    @FXML
    private void profile(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/profile/Profile.fxml"));
        content.setCenter(root);
    }

    @FXML
    private void cart(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/commande/Panier.fxml"));
        Parent root = loader.load();
        HuntKingdom.stage.getScene().setUserData(loader.getController());
        content.setCenter(root);
    }
    
    @FXML
    private void animaux(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/animal/ListeAnimals.fxml"));
        content.setCenter(root);
    }

    @FXML
    private void Logout(MouseEvent event) {
        CurrentUser cu = CurrentUser.CurrentUser(); 
        cu.id=0;
        cu.role=0;
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/login/Login.fxml")); 
        Parent root = loader.load();
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene);
        }
        catch(IOException e) 
        { }
    }
}
