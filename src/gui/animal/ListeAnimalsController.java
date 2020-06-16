/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.animal;

import entities.animal.Animal;
import entities.animal.CategorieAnimal;
import huntkingdom.HuntKingdom;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import services.animal.ServiceAnimal;
import services.animal.ServiceCategorieAnimal;


/**
 * FXML Controller class
 *
 * @author G I E
 */
public class ListeAnimalsController implements Initializable {
    
    private ArrayList<Animal> data;

    @FXML
    private ScrollPane pane;
    @FXML
    private Button retour;
    @FXML
    private ComboBox<CategorieAnimal> triCombo;

    private TilePane c;
    
    private List<Parent> animauxRoot;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        animauxRoot=new ArrayList<>();
        try {
            TilePane b = new TilePane();
            b.setPadding(new javafx.geometry.Insets(30));
            c = new TilePane();
            
            ServiceAnimal sa = new ServiceAnimal();
            data = sa.getAllAnimal();
            for ( Animal d : data) {
                
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("DivListeAnimal.fxml"));
                    Parent root = (Pane) loader.load();
                    DivListeAnimalController DaC = loader.getController();
                    DaC.LoadValues(d);
                    
                    //   c.setVgap(40);
                    c.getChildren().removeAll();
                    
                    root.setUserData(d.getCategorie_id());
                    animauxRoot.add(root);
                    c.getChildren().add(root);
                } catch (IOException ex) {
                    Logger.getLogger(ListeAnimalsController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ListeAnimalsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        CategorieAnimal cat=new CategorieAnimal();
        cat.setId(-1);
        cat.setNom("Tout");
        ServiceCategorieAnimal sca=new ServiceCategorieAnimal();
        ObservableList<CategorieAnimal> list=sca.getListCategorieAnimal();
        list.add(0, cat);
        triCombo.setItems(list);
        triCombo.getSelectionModel().select(0);
    }    

    @FXML
    private void Retour(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/MenuAdmin.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene);
    }

    @FXML
    private void trier(ActionEvent event) {
        c.getChildren().clear();
        CategorieAnimal cat=triCombo.getSelectionModel().getSelectedItem();
        if (cat.getId()==-1)
            animauxRoot.forEach(ar->c.getChildren().add(ar));
        else
            animauxRoot.stream().filter(ar->(int)(ar.getUserData())==cat.getId()).forEach(ar->c.getChildren().add(ar));
    }
    
}
