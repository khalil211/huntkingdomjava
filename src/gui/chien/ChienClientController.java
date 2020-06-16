/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.chien;

import entities.animal.Animal;
import entities.animal.CategorieAnimal;
import entities.chien.Chien;
import entities.coach.Coach;
import entities.user.CurrentUser;
import huntkingdom.HuntKingdom;
import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import services.animal.ServiceAnimal;
import services.animal.ServiceCategorieAnimal;
import services.chien.ChienService;
import services.coach.CoachService;



/**
 * FXML Controller class
 *
 * @author Louay
 */
public class ChienClientController implements Initializable {
@FXML
private TextField nomText;
@FXML
private TextField ageText;
@FXML
private TextField maladieText;
@FXML
private ComboBox<String> Animal;
@FXML
private ComboBox<String> race;
@FXML
private Label nomInfo;
@FXML
private Label raceInfo;
@FXML
private Label maladieInfo;
@FXML
private Label coachInfo;
@FXML
private Label ageInfo;

@FXML
private ProgressBar noteInfo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> races = FXCollections.observableArrayList("Labrador", "Slougui", "Boxeur", "Berger");
        Chien co=new Chien();
        ChienService sca = new ChienService();
        race.setItems(races);
        race.getSelectionModel().selectFirst();
        ServiceAnimal sa = new ServiceAnimal();
        Animal.setItems(sa.getListeA());
        Animal.getSelectionModel().selectFirst();
        
       CurrentUser cu = CurrentUser.CurrentUser();
       
       co= sca.getC(cu.id);
       nomInfo.setText(co.getNom());
       raceInfo.setText(co.getRace());
       coachInfo.setText(co.getNomCoach());
       maladieInfo.setText(co.getMaladie());
       ageInfo.setText(co.getAge()+"");
       noteInfo.setProgress(co.getNote());
        // TODO
    }    
       @FXML
    private void retour(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/profile/Profile.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene);
    }
     @FXML
    private void demande(MouseEvent event) {
        if (nomText.getText().isEmpty()||ageText.getText().isEmpty()||maladieText.getText().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ajout impossible");
            alert.setHeaderText("Remplir les champs");
            alert.showAndWait();
        }
        else {
            Chien c =  new Chien();
            ChienService cs = new ChienService();
            c.setNom(nomText.getText());
            c.setTypeChase(Animal.getSelectionModel().getSelectedItem());
            c.setRace(race.getSelectionModel().getSelectedItem());
            c.setMaladie(maladieText.getText());
            int age= Integer.parseInt(ageText.getText());
            c.setAge(age);
            
            cs.ajouter(c);
            



            
        }
    }
    
}
