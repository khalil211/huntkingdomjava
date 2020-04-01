/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.chien;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import entities.chien.Chien;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.chien.ChienService;



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
    @FXML private TableColumn<Chien, Integer> noteCol;
    @FXML private TableColumn<Chien, String> maladieCol;
    @FXML private TableColumn<Chien, String> dateCol;
    @FXML private TableColumn<Chien, String> typeCol;
    @FXML private TableColumn<Chien, String> etatCol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clientCol.setCellValueFactory(new PropertyValueFactory<Chien, String>("username"));
        nomCol.setCellValueFactory(new PropertyValueFactory<Chien, String>("nom"));
        maladieCol.setCellValueFactory(new PropertyValueFactory<Chien, String>("maladie"));
        noteCol.setCellValueFactory(new PropertyValueFactory<Chien, Integer>("note"));
        ageCol.setCellValueFactory(new PropertyValueFactory<Chien, Integer>("age"));
        dateCol.setCellValueFactory(new PropertyValueFactory<Chien, String>("dateToString"));
        etatCol.setCellValueFactory(new PropertyValueFactory<Chien, String>("etat"));
        typeCol.setCellValueFactory(new PropertyValueFactory<Chien, String>("typeChasse"));
        ObservableList<Chien> chiensObs=FXCollections.observableArrayList();
        ChienService cs=new ChienService();
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
    private void accepterChien(MouseEvent event) {
        ArrayList<String> Chienaccepte=new ArrayList<>();
        Chien c=listechien.getSelectionModel().getSelectedItem();
        ChienService cs=new ChienService();
      
           
            cs. accepterChien(c);
            
          
            listechien.refresh();
        }
    
   
}
