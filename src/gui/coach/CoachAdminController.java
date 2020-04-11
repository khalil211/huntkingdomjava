/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.coach;

import entities.chien.Chien;
import entities.coach.Coach;
import entities.user.User;
import huntkingdom.HuntKingdom;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import services.animal.ServiceAnimal;
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
    @FXML
    private TextField experText;
    @FXML
    private ComboBox<User> user;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CoachService ca = new CoachService();
        user.setItems(ca.getListeu());
        user.getSelectionModel().selectFirst();
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
    
    
     @FXML
    private void ajouterCoach(MouseEvent event) throws SQLException {
        if (experText.getText().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ajout impossible");
            alert.setHeaderText("Remplir les champs");
            alert.showAndWait();
        }
        else {
            Coach c =  new Coach();
            CoachService cs = new CoachService();
            final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            int count=8;
            c.setUserId(user.getSelectionModel().getSelectedItem().getId());
            
            int exper= Integer.parseInt(experText.getText());
            c.setExperienceYears(exper);
            
          StringBuilder builder = new StringBuilder();
            while (count-- != 0) {
           int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
           builder.append(ALPHA_NUMERIC_STRING.charAt(character));
            }
            c.setCode(builder.toString());
            
            cs.ajouter(c);
            
          listecoach.refresh();


            
        }
    }
    @FXML
    private void supprimerCoach(MouseEvent event) {
        CoachService cs=new CoachService();
        
        Coach c=listecoach.getSelectionModel().getSelectedItem();
        
        cs.supprimerCoach(c);
        listecoach.getItems().remove(c);
        
    }
    @FXML
    private void retour(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/MenuAdmin.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene);
    }
}
