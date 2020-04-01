/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.animal;

import entities.animal.CategorieAnimal;
import huntkingdom.HuntKingdom;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import services.animal.ServiceCategorieAnimal;

/**
 * FXML Controller class
 *
 * @author G I E
 */
public class GestionCategorieController implements Initializable {

    @FXML
    private TableColumn<CategorieAnimal, String> nomCol;
    @FXML
    private TableColumn<CategorieAnimal, String> descriptionCol;
    @FXML
    private TableView<CategorieAnimal> listCategorie;
    @FXML
    private TextField nomText;
    @FXML
    private TextArea descText;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        ServiceCategorieAnimal cs = new ServiceCategorieAnimal();
        listCategorie.setItems(cs.getListCategorieAnimal());
    }   
    

    @FXML
    private void ajouter(MouseEvent event) {
        if (nomText.getText().isEmpty()||descText.getText().isEmpty()){
            Alert alert=new Alert(AlertType.WARNING);
            alert.setTitle("Ajout impossible");
            alert.setHeaderText("Remplir les champs");
            alert.showAndWait();
        }
        else {
            CategorieAnimal ca  =  new CategorieAnimal();
            ServiceCategorieAnimal sca = new ServiceCategorieAnimal();
            ca.setNom(nomText.getText());
            ca.setDescription(descText.getText());
            sca.ajouterCategorieAnimal(ca);
            listCategorie.getItems().add(ca);
            nomText.setText("");
            descText.setText("");
        }
    }

    @FXML
    private void modifier(MouseEvent event) {
        CategorieAnimal ca = listCategorie.getSelectionModel().getSelectedItem();
        if (ca!=null){
            ServiceCategorieAnimal sca = new ServiceCategorieAnimal();
            ca.setNom(nomText.getText());
            ca.setDescription(descText.getText());
            sca.update(ca.getId(), ca.getNom(), ca.getDescription());
            listCategorie.refresh();
        }
    }

    @FXML
    private void supprimer(MouseEvent event) throws SQLException {
        CategorieAnimal ca = listCategorie.getSelectionModel().getSelectedItem();
        if (ca!=null){
            ServiceCategorieAnimal sca = new ServiceCategorieAnimal();
            sca.deleteCategorieAnimal(ca.getId());
            listCategorie.getItems().remove(ca);
            nomText.setText("");
            descText.setText("");
        }
    }

    @FXML
    private void selection(MouseEvent event) {
        CategorieAnimal ca = listCategorie.getSelectionModel().getSelectedItem();
        if (ca!=null){
            nomText.setText(ca.getNom());
            descText.setText(ca.getDescription());
        }
    }

    @FXML
    private void retour(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/animal/MenuAdmin.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene);
    }
    
}
