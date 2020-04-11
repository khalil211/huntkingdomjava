/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.produit;

import entities.produit.Produit;
import gui.commande.PanierController;
import services.produit.ProduitService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Circle;
import huntkingdom.HuntKingdom;
import services.commande.CommandeService;

/**
 * FXML Controller class
 *
 * @author user
 */
public class ListeProduitsController implements Initializable {

      private ArrayList<Produit> data;
    
    @FXML
    private ScrollPane pane;
    @FXML
    private Button panier;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
   try {
               
            TilePane b = new TilePane();
            b.setPadding(new javafx.geometry.Insets(30));
            TilePane c = new TilePane();
           
            
            ProduitService ps = new ProduitService();
            data = ps.getAllProduit();
            for ( Produit d : data) {
                
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("DivListeProduit.fxml"));
                    Parent root = (Pane) loader.load();
                    DivListeProduitController DpC = loader.getController();
                    DpC.LoadValues(d);
                    
                    //   c.setVgap(40);
                    c.getChildren().removeAll();
                    
                    
                    c.getChildren().add(root);
                } catch (IOException ex) {
                    Logger.getLogger(ListeProduitsController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            c.setPrefColumns(3);
            c.setPadding(new javafx.geometry.Insets(0));
            c.setHgap(10);
            c.setVgap(80);
            b.getChildren().add(c);
            b.setPrefWidth(1000);
            pane.setContent(b);
        } catch (SQLException ex) {
            Logger.getLogger(ListeProduitsController.class.getName()).log(Level.SEVERE, null, ex);
        }
   
        CommandeService cs=new CommandeService();
        panier.setText("Panier ("+cs.getPanier().getNbProduits()+")");
    }    

    @FXML
    private void menu(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/MenuAdmin.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene);
    }

    @FXML
    private void consulterPanier(MouseEvent event) throws Exception {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/gui/commande/Panier.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        scene.setUserData((PanierController)loader.getController());
        HuntKingdom.stage.setScene(scene);
    }
}
