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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import services.user.UserService;
import utils.SendMail;
import entities.user.CurrentUser;

/**
 * FXML Controller class
 *
 * @author moez
 */
public class ForgotController implements Initializable {

    @FXML
    private TextField tfusername;
    @FXML
    private TextField tfcode;
    @FXML
    private Label labelemail;
    @FXML
    private Button resetbutton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        tfcode.setDisable(true);
        resetbutton.setDisable(true);
    }    

    @FXML
    private void reinitialiser(ActionEvent event) throws Exception
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        if (cu.code.equals(tfcode.getText()))
        {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/login/Reset.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene);  
        }
        else
        {
            labelemail.setText("code incorrect");
        }
        
       
    }

    @FXML
    private void sendCode(ActionEvent event) throws Exception
    {
      CurrentUser cu = CurrentUser.CurrentUser();
      UserService us = new UserService();  
      if (us.usernameExist(tfusername.getText()))
      {
        tfcode.setDisable(false);
        resetbutton.setDisable(false);
        labelemail.setText("un code a été envoyé a votre Email, retapez-le ici");
        
        String code =us.getAlphaNumericString(8);
        cu.targetId=us.geIdbyUsername(tfusername.getText());
        cu.code=code;
        
        String email = us.getEmailbyUsername(tfusername.getText());
        us.updateCode(code, us.geIdbyUsername(tfusername.getText()));
        
        
        String cn="Saissisez ce code pour réinitialiser votre mot de passe HuntKingdom : "+code;
       
        String sb="Mot de passe oublié";
        SendMail.sendMail(email,sb, cn);
      }
      else
      {
         labelemail.setText("Username n'existe pas"); 
      }
    }
    
}
