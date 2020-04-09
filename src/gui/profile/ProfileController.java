/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.profile;

import entities.user.CurrentUser;
import entities.publication.Publication;
import entities.user.User;
import services.publication.PublicationService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import java.io.IOException;
import java.sql.SQLException;
import javafx.scene.control.TextArea;
import services.user.UserService;


/**
 * FXML Controller class
 *
 * @author moez
 */
public class ProfileController implements Initializable {

    @FXML
    private Button logout;
    @FXML
    private TextArea tfpublication;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void logout(ActionEvent event) 
    {
        CurrentUser cu = CurrentUser.CurrentUser(); 
        cu.id=0;
        cu.role=0;
        
        
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/login/Login.fxml")); 
        Parent root = loader.load();
        logout.getScene().setRoot(root);
        }
        catch(IOException e) 
        { }
        
         
    }

    @FXML
    private void publier(ActionEvent event) throws SQLException, IOException 
    {
        CurrentUser cu = CurrentUser.CurrentUser(); 
        if( !tfpublication.getText().isEmpty())
        {
            PublicationService ps = new PublicationService();
           
            Publication p = new Publication(cu.id,tfpublication.getText());
            ps.ajouterPublication(p);
            
        System.out.println("publication ajouté");
            
        }
    }
    
}
