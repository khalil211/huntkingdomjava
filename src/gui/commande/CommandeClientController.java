/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.commande;

import entities.commande.Commande;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import huntkingdom.HuntKingdom;

/**
 * FXML Controller class
 *
 * @author khalil
 */
public class CommandeClientController implements Initializable {

    @FXML
    private Label dateLabel;
    @FXML
    private Label etatLabel;
    @FXML
    private Label nbProduitLabel;
    @FXML
    private Label totalLabel;
    
    private Commande c;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void detailsCommandeButton(MouseEvent event) {
        ((PanierController)HuntKingdom.stage.getScene().getUserData()).setDetailsCommande(c);
    }
    
    public void setData(Commande cc) {
        c=cc;
        dateLabel.setText(cc.getDateToString());
        etatLabel.setText(cc.getEtatToString());
        nbProduitLabel.setText(Integer.toString(cc.getNbProduits()));
        totalLabel.setText(Double.toString(cc.getTotal()));
    }
}
