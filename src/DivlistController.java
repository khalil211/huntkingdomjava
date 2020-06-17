/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.appointments;

import entities.Appointments.Participant;
import entities.Appointments.appointments;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import services.Appointments.appointmentsService;

/**
 * FXML Controller class
 *
 * @author marwe
 */
public class DivlistController implements Initializable {

    @FXML
    private AnchorPane ap;
    @FXML
    private Label name;
    @FXML
    private Label souscat;
    @FXML
    private Label id;
    @FXML
    private Rectangle rectangle;
    @FXML
    private Button ajouter;
    @FXML
    private Label notifPanier;
    @FXML
    private TextArea des;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ajouterP(ActionEvent event) {
    }
    Connection con;
    Statement ste;
     public void LoadValues(appointments e) throws IOException {
   
         name.setText(e.getTitle());
         des.setText(e.getDescription());
         
       // souscat.setText(e.getCateorie()+"");
        

        id.setText(String.valueOf(e.getId()));
        

      
//        sq.setPadding(new Insets(-10, -10, -10, -10));

            Image imageURI = new Image("file:C://Users/marwe/OneDrive/Documents/NetBeansProjects/huntkingdomjava/src/images/event.jpg");
         rectangle.setFill(new ImagePattern(imageURI));
        
      
        ap.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                   // doubleclick(event, e);
                }

            }
        });
    
       appointmentsService cs=new appointmentsService();
        ajouter.setOnMouseClicked(me-> {
            Participant pc=new Participant();
            pc.setUser_id(5);
            pc.setId_event(e.getId());
             try {
                 cs.ajoutP(pc);
             } catch (SQLException ex) {
                 Logger.getLogger(DivlistController.class.getName()).log(Level.SEVERE, null, ex);
             }
           
        });
      }
}
