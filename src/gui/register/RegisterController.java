/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.register;

import entities.user.User;
import entities.user.CurrentUser;
import huntkingdom.HuntKingdom;
import services.user.UserService;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;

/**
 * FXML Controller class
 *
 * @author moez
 */
public class RegisterController implements Initializable {

    @FXML
    private TextField tfusername;
    @FXML
    private TextField tfemail;
    @FXML
    private TextField tfpassword;
    @FXML
    private PasswordField tfrepeat;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
    }    
    
    @FXML
    private void Enregistrer(ActionEvent event) throws SQLException, IOException {
        if( !tfusername.getText().isEmpty() && !tfemail.getText().isEmpty()&& !tfpassword.getText().isEmpty()&& !tfrepeat.getText().isEmpty())
        {
            UserService us = new UserService();
           
            User u = new User(tfusername.getText(),tfemail.getText(),tfpassword.getText());
            us.ajouterUser(u);
            
        
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MenuAdmin.fxml"));
            Parent root = loader.load();
            tfusername.getScene().setRoot(root);
        }
    }

    @FXML
    private void login(ActionEvent event) throws Exception
    {
        CurrentUser x = CurrentUser.CurrentUser(); 
	System.out.println("String from x is " + x.id); 
	

		

		System.out.println("String from x is " + x.id); 
     Parent root = FXMLLoader.load(getClass().getResource("/gui/profile/Profile.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene);
    }


    
}
