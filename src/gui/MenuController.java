/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import huntkingdom.HuntKingdom;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author khalil
 */
public class MenuController implements Initializable {

    @FXML
    private ScrollPane content;
    
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
        //content.setCenter(root);
        content.setContent(root);
        //content.getChildren().clear();
        //content.getChildren().add(root);
    }
    
    @FXML
    private void shop(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/produit/ListeProduits.fxml"));
        //content.setCenter(root);
        content.setContent(root);
        //content.getChildren().clear();
        //content.getChildren().add(root);
    }

    @FXML
    private void profile(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/profile/Profile.fxml"));
        //content.setCenter(root);
        content.setContent(root);
        //content.getChildren().clear();
        //content.getChildren().add(root);
    }
    
}
