/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.coach;

import entities.chien.Chien;
import entities.coach.Coach;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.chien.ChienService;
import services.coach.CoachService;

/**
 * FXML Controller class
 *
 * @author Louay
 */
public class CoachAdminController implements Initializable {
    @FXML private TableView<Coach> listecoach;
    @FXML private TableColumn<Coach, String> nameCol;
    @FXML private TableColumn<Coach, String> emailCol;
    @FXML private TableColumn<Coach, Integer> experienceCol;
    @FXML private TableColumn<Coach, String> statusCol;
    @FXML private TableColumn<Coach, String> dateCol;
    @FXML private TableColumn<Coach, String> codeCol;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nameCol.setCellValueFactory(new PropertyValueFactory<Coach, String>("nom"));
        emailCol.setCellValueFactory(new PropertyValueFactory<Coach, String>("email"));
        experienceCol.setCellValueFactory(new PropertyValueFactory<Coach, Integer>("experienceYears"));
        statusCol.setCellValueFactory(new PropertyValueFactory<Coach, String>("etat"));
        codeCol.setCellValueFactory(new PropertyValueFactory<Coach, String>("code"));
        dateCol.setCellValueFactory(new PropertyValueFactory<Coach, String>("dateToString"));

       
        ObservableList<Coach> coachObs=FXCollections.observableArrayList();
        CoachService cs=new CoachService();
        for(Coach c : cs.getAllCoachs())
            coachObs.add(c);
        listecoach.setItems(coachObs);
        // TODO
    }    
    
}
