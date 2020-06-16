/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.produit;

import entities.produit.Produit;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author user
 */
public class DivListeProduitController implements Initializable {

    @FXML
    private AnchorPane ap;
    @FXML
    private Label name;
    @FXML
    private Label souscat;
    @FXML
    private Label id;
    @FXML
    private Rectangle rectangle;
    @FXML
    private Label prixmoy;
    @FXML
    private Button ajouter;
    @FXML
    private TextField qte;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ajouterP(ActionEvent event) {
    }
    
    
    Connection con;
    Statement ste;
     public void LoadValues(Produit e) throws IOException {
   
         name.setText(e.getNom());
        
       // souscat.setText(e.getCateorie()+"");
        

        prixmoy.setText(String.valueOf(e.getPrix())+" DT");
        id.setText(String.valueOf(e.getId()));
        

      
//        sq.setPadding(new Insets(-10, -10, -10, -10));

            Image imageURI = new Image("file:C:/wamp64/www/huntkingdom/web/images/" + e.getImage());
         rectangle.setFill(new ImagePattern(imageURI));
        
      
        ap.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
            //       doubleclick(event, e);
                }

            }
        });
    

      }
   
    
}
