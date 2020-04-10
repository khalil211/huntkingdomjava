/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.commande;

import entities.commande.Commande;
import entities.commande.ProduitCommande;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import huntkingdom.HuntKingdom;
import java.time.LocalDateTime;
import java.util.HashMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import services.commande.CommandeService;
import services.commande.ProduitCommandeService;

/**
 * FXML Controller class
 *
 * @author khalil
 */
public class PanierController implements Initializable {

    @FXML
    private VBox listeProduitPanier;
    @FXML
    private Label clientLabel;
    @FXML
    private Label nbProduitsLabel;
    @FXML
    private Label totalLabel;
    private HashMap<Integer,Parent> produitPanierPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        produitPanierPane=new HashMap<>();
        CommandeService cs=new CommandeService();
        Commande c=cs.getPanier();
        clientLabel.setText(c.getUsername());
        nbProduitsLabel.setText(Integer.toString(c.getNbProduits()));
        totalLabel.setText(Double.toString(c.getTotal()));
        ProduitCommandeService pcs=new ProduitCommandeService();
        for (ProduitCommande pc : pcs.getProduitCommande(c)) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/commande/ProduitPanier.fxml"));
                Parent produitPanier=(Pane)fxmlLoader.load();
                ((ProduitPanierController)fxmlLoader.getController()).setData(pc);
                listeProduitPanier.getChildren().add(produitPanier);
                produitPanierPane.put(pc.getId(),produitPanier);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }    

    @FXML
    private void toShop(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/produit/ListeProduits.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene);
    }

    @FXML
    private void passerCommande(MouseEvent event) {
        CommandeService cs=new CommandeService();
        Commande c=cs.getPanier();
        c.setEtat(1);
        c.setDate(LocalDateTime.now());
        cs.passerCommande(c);
        cs.ajouter();
        listeProduitPanier.getChildren().clear();
        nbProduitsLabel.setText("0");
        totalLabel.setText("0");
    }
    
    public void updateDetails() {
        CommandeService cs=new CommandeService();
        Commande c=cs.getPanier();
        nbProduitsLabel.setText(Integer.toString(c.getNbProduits()));
        totalLabel.setText(Double.toString(c.getTotal()));
    }
    
    public void supprimerProduitPanier(int id) {
        listeProduitPanier.getChildren().remove(produitPanierPane.get(id));
        updateDetails();
    }
}
