/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.chien;

import entities.animal.CategorieAnimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import entities.chien.Chien;
import entities.coach.Coach;
import entities.commande.Commande;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import huntkingdom.HuntKingdom;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import services.chien.ChienService;
import services.coach.CoachService;



/**
 * FXML Controller class
 *
 * @author Louay
 */
public class ChienAdminController implements Initializable {

    @FXML private TableView<Chien> listechien;
    @FXML private TableColumn<Chien, String> clientCol;
    @FXML private TableColumn<Chien, String> nomCol;
    @FXML private TableColumn<Chien, Integer> ageCol;
    @FXML private TableColumn<Chien, String> raceCol;
    @FXML private TableColumn<Chien, String> coachCol;

    @FXML private TableColumn<Chien, String> maladieCol;
    @FXML private TableColumn<Chien, String> dateCol;
    @FXML private TableColumn<Chien, String> typeCol;
    @FXML private TableColumn<Chien, String> etatCol;
    @FXML
    private ComboBox<Coach> coachlist;

    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CoachService sca = new CoachService();
        coachlist.setItems(sca.getListeC());
        coachlist.getSelectionModel().selectFirst();
        
                ChienService cs=new ChienService();
       
        clientCol.setCellValueFactory(new PropertyValueFactory<Chien, String>("username"));
        nomCol.setCellValueFactory(new PropertyValueFactory<Chien, String>("nom"));
        maladieCol.setCellValueFactory(new PropertyValueFactory<Chien, String>("maladie"));
        raceCol.setCellValueFactory(new PropertyValueFactory<Chien, String>("race"));
        ageCol.setCellValueFactory(new PropertyValueFactory<Chien, Integer>("age"));
        dateCol.setCellValueFactory(new PropertyValueFactory<Chien, String>("dateToString"));
        etatCol.setCellValueFactory(new PropertyValueFactory<Chien, String>("etat"));
        typeCol.setCellValueFactory(new PropertyValueFactory<Chien, String>("typeChase"));
        coachCol.setCellValueFactory(new PropertyValueFactory<Chien, String>("nomCoach"));

        ObservableList<Chien> chiensObs=FXCollections.observableArrayList();
        
        for(Chien c : cs.getAllChiens())
            chiensObs.add(c);
        listechien.setItems(chiensObs);
        // TODO
    }    
    
    
   

    @FXML
    private void SupprimerChien(MouseEvent event) {
        ChienService cs=new ChienService();
        
        Chien c=listechien.getSelectionModel().getSelectedItem();
        
        cs.supprimerChien(c);
        listechien.getItems().remove(c);
        
    }
     @FXML
    private void accepterChien(MouseEvent event) throws SQLException {
        ArrayList<String> Chienaccepte=new ArrayList<>();
        Chien c=listechien.getSelectionModel().getSelectedItem();
        String race=coachlist.getSelectionModel().getSelectedItem().getRace();
        ChienService cs=new ChienService();
        c.setCoachId(coachlist.getSelectionModel().getSelectedItem().getId());
        if("Non Disponible".equals(coachlist.getSelectionModel().getSelectedItem().getEtat()))
        {
             Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ajout impossible");
            alert.setHeaderText("Coach Non Disponible");
            alert.showAndWait();
        }
        else{
            if(race.equals(listechien.getSelectionModel().getSelectedItem().getRace()))
            {
                 c.setCoachId(coachlist.getSelectionModel().getSelectedItem().getId());
            cs. accepterChien(c);
            }
            else
            {
                 Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ajout impossible");
            alert.setHeaderText("la spécialité du coach n'est pas: "+listechien.getSelectionModel().getSelectedItem().getRace());
            alert.showAndWait();
            }
            
        }
          
            listechien.refresh();
        }
    
    @FXML
    private void refuserChien(MouseEvent event) {
        ArrayList<String> Chienaccepte=new ArrayList<>();
        Chien c=listechien.getSelectionModel().getSelectedItem();
        ChienService cs=new ChienService();
      
           
            cs.refuserChien(c);
            
          
            listechien.refresh();
        }
    
     @FXML
    private void toMenuAdmin(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/MenuAdmin.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene);
    }
   
}
