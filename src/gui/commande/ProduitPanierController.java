/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.commande;

import entities.commande.ProduitCommande;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import services.commande.ProduitCommandeService;
import huntkingdom.HuntKingdom;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author khalil
 */
public class ProduitPanierController implements Initializable {

    @FXML
    private Rectangle image;
    @FXML
    private Label nom;
    @FXML
    private Label prix;
    @FXML
    private Spinner<Integer> quantite;
    @FXML
    private Label total;
    private ProduitCommande p;
    @FXML
    private Button supprimerButton;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void modifierProduitPanier(MouseEvent event) {
        p.setQuantite(quantite.getValue());
        total.setText(Double.toString(p.getPrixTotal()));
        ProduitCommandeService pcs=new ProduitCommandeService();
        pcs.modifier(p);
        ((PanierController)HuntKingdom.stage.getScene().getUserData()).updateDetails();
    }

    @FXML
    private void supprimerProduitPanier(MouseEvent event) {
        ProduitCommandeService pcs=new ProduitCommandeService();
        pcs.supprimer(p);
        ((PanierController)HuntKingdom.stage.getScene().getUserData()).supprimerProduitPanier(p.getId());
    }
    
    public void setData(ProduitCommande pc){
        p=pc;
        quantite.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000, pc.getQuantite()));
        image.setFill(new ImagePattern(new Image("file:C:/wamp64/www/HuntKingdomjava/uploads/" + pc.getImage())));
        nom.setText(pc.getNom());
        prix.setText(Double.toString(pc.getPrixUnitaire()));
        total.setText(Double.toString(pc.getPrixTotal()));
    }
}
