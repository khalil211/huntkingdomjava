/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.login;

import entities.user.User;
import entities.user.CurrentUser;
import huntkingdom.HuntKingdom;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import services.user.UserService;

/**
 * FXML Controller class
 *
 * @author moez
 */
public class LoginController implements Initializable {

    @FXML
    private PasswordField tfpassword;
    @FXML
    private TextField tfusername;
    @FXML
    private Label erreur;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    @FXML
    private void Authentifier(ActionEvent event) throws SQLException, IOException {
        if( !tfusername.getText().isEmpty() && !tfpassword.getText().isEmpty())
        {
            UserService us = new UserService();
           
            User u = new User(tfusername.getText(),tfpassword.getText());
            CurrentUser cu = CurrentUser.CurrentUser(); 
	System.out.println("current user id is " + cu.id); 
	
            if (us.check(u))
            {
                System.out.println("current user id is " + cu.id); 
                if (cu.role==1)
                {
                  FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MenuAdmin.fxml")); 
                  Parent root = loader.load();
                  tfusername.getScene().setRoot(root);
                }
                else
                {
                   FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/profile/Profile.fxml")); 
                   Parent root = loader.load();
                   tfusername.getScene().setRoot(root); 
                }
            }
            else
            {
                if (cu.error == 1)
                {
                  erreur.setText("Erreur : mot de passe incorrect ");  
                  cu.error=0;
                }
                else if (cu.error == 2)
                {
                   erreur.setText("Erreur : ce username n'existe pas ! ");
                   cu.error=0;
                }
            }
        }
    }

    @FXML
    private void register(ActionEvent event) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/register/Register.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene);
    }

    @FXML
    private void forgot(ActionEvent event) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/login/Forgot.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene);
    }
    
}
