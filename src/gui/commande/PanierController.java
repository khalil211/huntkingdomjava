/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.commande;

import entities.commande.Commande;
import entities.user.CurrentUser;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import huntkingdom.HuntKingdom;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
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
    @FXML
    private VBox listeCommande;
    @FXML
    private VBox detailsCommande;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //panier
        produitPanierPane=new HashMap<>();
        CommandeService cs=new CommandeService();
        Commande c=cs.getPanier();
        clientLabel.setText(c.getUsername());
        nbProduitsLabel.setText(Integer.toString(c.getNbProduits()));
        totalLabel.setText(Double.toString(c.getTotal()));
        ProduitCommandeService pcs=new ProduitCommandeService();
        pcs.getProduitCommande(c).forEach((pc) -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/commande/ProduitPanier.fxml"));
                Parent produitPanier=(Pane)fxmlLoader.load();
                ((ProduitPanierController)fxmlLoader.getController()).setData(pc);
                listeProduitPanier.getChildren().add(produitPanier);
                produitPanierPane.put(pc.getId(),produitPanier);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        });
        //liste commande
        afficherCommandes();
    }
    
    public void afficherCommandes() {
        listeCommande.getChildren().clear();
        CommandeService cs=new CommandeService();
        cs.getCommandeClient().forEach((cc) -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/commande/CommandeClient.fxml"));
                Pane commandeClient=(Pane)fxmlLoader.load();
                if (cc.getEtat()==1) {
                    Button annuler=new Button();
                    annuler.setText("Annuler");
                    annuler.setOnMouseClicked(event-> {
                        cc.setEtat(3);
                        cs.modifierEtatCommande(cc, 3);
                        commandeClient.getChildren().remove(annuler);
                        commandeClient.getChildren().stream().filter(child->child instanceof Label).map(child->(Label)child).filter(child->child.getText().equals("En attente")).findFirst().get().setText("Annulée");
                    });
                    commandeClient.getChildren().add(annuler);
                }
                ((CommandeClientController)fxmlLoader.getController()).setData(cc);
                listeCommande.getChildren().add(commandeClient);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        });
    }

    @FXML
    private void passerCommande(MouseEvent event) {
        CommandeService cs=new CommandeService();
        Commande c=cs.getPanier();
        c.setEtat(1);
        c.setDate(LocalDateTime.now());
        cs.passerCommande(c);
        cs.ajouter(CurrentUser.CurrentUser().id);
        listeProduitPanier.getChildren().clear();
        nbProduitsLabel.setText("0");
        totalLabel.setText("0");
        afficherCommandes();
        ((Label)HuntKingdom.stage.getScene().lookup("#panier")).setText("Panier ("+new CommandeService().getPanier().getNbProduits()+")");
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
    
    public void setDetailsCommande(Commande c) {
        detailsCommande.getChildren().clear();
        ProduitCommandeService pcs=new ProduitCommandeService();
        pcs.getProduitCommande(c).forEach((pc) -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/commande/ProduitPanier.fxml"));
                Pane produitCommandeClient=(Pane)fxmlLoader.load();
                ((ProduitPanierController)fxmlLoader.getController()).setData(pc);
                produitCommandeClient.getChildren().remove(produitCommandeClient.getChildrenUnmodifiable().stream().filter(child->child instanceof Button).findFirst().get());
                produitCommandeClient.getChildren().remove(produitCommandeClient.getChildrenUnmodifiable().stream().filter(child->child instanceof Spinner).findFirst().get());
                detailsCommande.getChildren().add(produitCommandeClient);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        });
    }
}
