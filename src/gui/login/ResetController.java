/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.login;

import huntkingdom.HuntKingdom;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import services.user.UserService;
import entities.user.CurrentUser;

/**
 * FXML Controller class
 *
 * @author moez
 */
public class ResetController implements Initializable {

    @FXML
    private PasswordField password;
    @FXML
    private PasswordField passwordtwo;
    @FXML
    private Label label;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void resetpassword(ActionEvent event) throws Exception
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        if(!password.getText().isEmpty()&& !passwordtwo.getText().isEmpty())
        {
         
            if (password.getText().equals(passwordtwo.getText()))
            {
                UserService us = new UserService();
                
                us.updatePassword(password.getText(), cu.targetId );
                
                Parent root = FXMLLoader.load(getClass().getResource("/gui/login/Login.fxml"));
                Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
                HuntKingdom.stage.setScene(scene);  
            }
            else
            {
               label.setText("Les deux mots de passe ne sont identiques");
            }
        }
        
        
    }
    
}
