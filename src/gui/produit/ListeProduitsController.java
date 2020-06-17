/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.produit;

import entities.produit.Categorie;
import entities.produit.Produit;
import services.produit.ProduitService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import services.produit.CategorieService;

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
    private TextField rechercher;
    @FXML
    private ComboBox<Categorie> triCatCombo;
    
    private TilePane c;
    
    private List<Parent> produitsRoot;
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        produitsRoot=new ArrayList<>();
   try {
               
            TilePane b = new TilePane();
            b.setPadding(new javafx.geometry.Insets(30));
            c = new TilePane();
           
            
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
                    root.setUserData(d);
                    produitsRoot.add(root);
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
        CategorieService cs=new CategorieService();
        Categorie c=new Categorie();
        c.setId(-1);
        c.setNom("Tout");
        ObservableList<Categorie> list=cs.getListCategorie();
        list.add(0, c);
        triCatCombo.setItems(list);
        triCatCombo.getSelectionModel().select(0);
    }

    @FXML
    private void trier(ActionEvent event) {
        c.getChildren().clear();
        Categorie cat=triCatCombo.getSelectionModel().getSelectedItem();
        if (cat.getId()==-1)
            produitsRoot.stream().filter(pr->((Produit)pr.getUserData()).getNom().toUpperCase().contains(rechercher.getText().toUpperCase().trim())).forEach(pr -> c.getChildren().add(pr));
        else
            produitsRoot.stream().filter(pr->((Produit)pr.getUserData()).getCategorie()==cat.getId()).filter(pr->((Produit)pr.getUserData()).getNom().toUpperCase().contains(rechercher.getText().toUpperCase().trim())).forEach(pr->c.getChildren().add(pr));
    }

    
}
