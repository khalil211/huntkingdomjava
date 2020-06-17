/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author khalil
 */
public class MenuAdminController implements Initializable {

    @FXML
    private BorderPane content;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void gestionUsers(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/userback/UserBack.fxml"));
        content.setCenter(root);
    }

    @FXML
    private void gestionCommandes(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/commande/CommandeAdmin.fxml"));
        content.setCenter(root);
    }
    
}
