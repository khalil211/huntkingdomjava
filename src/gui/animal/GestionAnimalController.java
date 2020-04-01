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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import services.animal.ServiceAnimal;
import services.animal.ServiceCategorieAnimal;
import utils.MyDB;

/**
 * FXML Controller class
 *
 * @author G I E
 */
public class GestionAnimalController implements Initializable {

    @FXML
    private TextField nomText;
    @FXML
    private TextField descText;
    @FXML
    private TextField mediasText;
    @FXML
    private TextField zoneText;
    @FXML
    private TextField saisonText;
    @FXML
    private TableView<Animal> listAnimal;
    @FXML
    private TableColumn<Animal, String> nomCol;
    @FXML
    private TableColumn<Animal, String> descriptionCol;
    @FXML
    private TableColumn<Animal, String> mediasCol;
    @FXML
    private TableColumn<Animal, String> zoneCol;
    @FXML
    private TableColumn<Animal, String> saisonCol;
    @FXML
    private ComboBox<CategorieAnimal> categorieA;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServiceCategorieAnimal sca = new ServiceCategorieAnimal();
        categorieA.setItems(sca.getListCategorieAnimal());
        categorieA.getSelectionModel().selectFirst();
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        mediasCol.setCellValueFactory(new PropertyValueFactory<>("medias"));
        zoneCol.setCellValueFactory(new PropertyValueFactory<>("zone"));
        saisonCol.setCellValueFactory(new PropertyValueFactory<>("saison"));
        ServiceAnimal as = new ServiceAnimal();
        listAnimal.setItems(as.getListeAnimaux());
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
            Animal a  =  new Animal();
            ServiceAnimal sa = new ServiceAnimal();
            a.setNom(nomText.getText());
            a.setDescription(descText.getText());
            a.setMedias(mediasText.getText());
            a.setZone(zoneText.getText());
            a.setSaison(saisonText.getText());
            a.setCategorie_id(categorieA.getSelectionModel().getSelectedItem().getId());
            sa.ajouterAnimal(a);
            listAnimal.getItems().add(a);
            nomText.setText("");
            descText.setText("");
            mediasText.setText("");
            zoneText.setText("");
            saisonText.setText("");
        }
    }

    @FXML
    private void modifier(MouseEvent event) {
        Animal a = listAnimal.getSelectionModel().getSelectedItem();
        if (a!=null){
            ServiceAnimal sa = new ServiceAnimal();
            a.setCategorie_id(categorieA.getSelectionModel().getSelectedItem().getId());
            a.setNom(nomText.getText());
            a.setDescription(descText.getText());
            a.setMedias(mediasText.getText());
            a.setZone(zoneText.getText());
            a.setSaison(saisonText.getText());
            sa.updateAnimal(a.getId(),a.getCategorie_id(),a.getNom(), a.getDescription(), a.getMedias(),a.getZone(),a.getSaison());
            listAnimal.refresh();
        }
    }

    @FXML
    private void supprimer(MouseEvent event) throws SQLException {
        Animal a = listAnimal.getSelectionModel().getSelectedItem();
        if (a!=null){
            ServiceAnimal sa = new ServiceAnimal();
            sa.deleteAnimal(a.getId());
            listAnimal.getItems().remove(a);
            nomText.setText("");
            descText.setText("");
            mediasText.setText("");
            zoneText.setText("");
            saisonText.setText("");
        }
    }

    @FXML
    private void selection(MouseEvent event){
        Animal a = listAnimal.getSelectionModel().getSelectedItem();
        if (a!=null){
            categorieA.getSelectionModel().select(categorieA.getItems().stream().filter(c->c.getId()==a.getCategorie_id()).findFirst().get());
            nomText.setText(a.getNom());
            descText.setText(a.getDescription());
            mediasText.setText(a.getMedias());
            zoneText.setText(a.getZone());
            saisonText.setText(a.getSaison());
        }
    }
    
}
