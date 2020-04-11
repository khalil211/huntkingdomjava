/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.coach;

import entities.chien.Chien;
import entities.coach.Coach;
import entities.user.CurrentUser;
import huntkingdom.HuntKingdom;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import services.chien.ChienService;
import services.coach.CoachService;

/**
 * FXML Controller class
 *
 * @author Louay
 */
public class CoachClientController implements Initializable {
     @FXML private TableView<Chien> listechien;
    @FXML private TableColumn<Chien, String> clientCol;
    @FXML private TableColumn<Chien, String> nomCol;
    @FXML private TableColumn<Chien, Integer> ageCol;
    @FXML private TableColumn<Chien, Integer> noteCol;
    

    @FXML private TableColumn<Chien, String> maladieCol;
    @FXML private TableColumn<Chien, String> dateCol;
    @FXML private TableColumn<Chien, String> typeCol;
    @FXML private TableColumn<Chien, String> etatCol;
    @FXML
    private TextField noteText;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CoachService sca = new CoachService();
       
        
                ChienService cs=new ChienService();
       
        clientCol.setCellValueFactory(new PropertyValueFactory<Chien, String>("username"));
        nomCol.setCellValueFactory(new PropertyValueFactory<Chien, String>("nom"));
        maladieCol.setCellValueFactory(new PropertyValueFactory<Chien, String>("maladie"));
        noteCol.setCellValueFactory(new PropertyValueFactory<Chien, Integer>("note"));
        ageCol.setCellValueFactory(new PropertyValueFactory<Chien, Integer>("age"));
        dateCol.setCellValueFactory(new PropertyValueFactory<Chien, String>("dateToString"));
        etatCol.setCellValueFactory(new PropertyValueFactory<Chien, String>("etat"));
        typeCol.setCellValueFactory(new PropertyValueFactory<Chien, String>("typeChase"));

        ObservableList<Chien> chiensObs=FXCollections.observableArrayList();
        
         try {
             for(Chien c : cs.getAllChiensOfCoach())
                 chiensObs.add(c);
         } catch (SQLException ex) {
             Logger.getLogger(CoachClientController.class.getName()).log(Level.SEVERE, null, ex);
         }
        listechien.setItems(chiensObs);
        // TODO
    }   
    @FXML
    private void noterChien(MouseEvent event) {
        Chien c=listechien.getSelectionModel().getSelectedItem();
        ChienService cs=new ChienService();

        int note = Integer.parseInt(noteText.getText());
     c.setNote(note);
           
            cs.update(c,note);
            
          
            listechien.refresh();
        }
    @FXML
    private void changeStatus(MouseEvent event) throws SQLException {
        
       CurrentUser cu = CurrentUser.CurrentUser();

        Coach c=new Coach();
        c.setUserId(cu.id);
        CoachService cs=new CoachService();
           
            cs.ChangeStatus(c);
            
          
            listechien.refresh();
        }
    @FXML
    private void retour(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/profile/Profile.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene);
    }
}
