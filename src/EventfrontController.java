/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.appointments;

import entities.Appointments.appointments;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import static org.apache.commons.lang3.time.FastDateParserSDFTest.data;
import services.Appointments.appointmentsService;

/**
 * FXML Controller class
 *
 * @author marwe
 */
public class EventfrontController implements Initializable {

    @FXML
    private ScrollPane pane;
    
          private ArrayList<appointments> data;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           
            TilePane b = new TilePane();
            b.setPadding(new javafx.geometry.Insets(30));
            TilePane c = new TilePane();
        
           
            
            appointmentsService ps = new appointmentsService();
        try {
            data = (ArrayList<appointments>) ps.afficheEvents();
        } catch (SQLException ex) {
            Logger.getLogger(EventfrontController.class.getName()).log(Level.SEVERE, null, ex);
        }
            for ( appointments d : data) {
                
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("divlist.fxml"));
                    Parent root = (Pane) loader.load();
                    DivlistController DpC = loader.getController();
                    DpC.LoadValues(d);
                    
                    //   c.setVgap(40);
                    c.getChildren().removeAll();
                    
                    
                    c.getChildren().add(root);
                } catch (IOException ex) {
                    Logger.getLogger(DivlistController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            c.setPrefColumns(3);
            c.setPadding(new javafx.geometry.Insets(0));
            c.setHgap(10);
            c.setVgap(80);
            b.getChildren().add(c);
            b.setPrefWidth(1000);
            pane.setContent(b);
       
   
    }    
}

