/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.animal;

import entities.animal.Animal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author G I E
 */
public class DivListeAnimalController implements Initializable {

    @FXML
    private Rectangle rectangle;
    @FXML
    private Label nom;
    @FXML
    private Label desc;
    @FXML
    private Label zone;
    @FXML
    private Label saison;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void LoadValues(Animal a){
        Image imageURI = new Image("file:C:/wamp64/www/huntkingdom/web/images/" + a.getMedias());
        rectangle.setFill(new ImagePattern(imageURI));
        nom.setText(a.getNom());
        desc.setText(a.getDescription());
        zone.setText(a.getZone());
        saison.setText(a.getSaison());   
    }
}
