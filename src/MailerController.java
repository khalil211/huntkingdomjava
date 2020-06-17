/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.reclamation;

import utils.SendMail;
import entities.user.CurrentUser;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import static jdk.nashorn.internal.runtime.Debug.id;
import services.user.UserService;
import utils.MyDB;

/**
 * FXML Controller class
 *
 * @author marwe
 */
public class MailerController implements Initializable {

    @FXML
    private TextField subject;
    @FXML
    private TextArea content;

    
    @FXML
    private Button send;
    @FXML
    private Button R;

     private Connection cnx;
    private Statement st;
    private PreparedStatement pre;
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
    private void retour(ActionEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/huntkingdom/Home.fxml"));
        Parent root = loader.load();
        subject.getScene().setRoot(root);
    }
    @FXML
    void sendi(ActionEvent event) throws SQLException {
        CurrentUser cu = CurrentUser.CurrentUser();
        UserService us = new UserService();
        String email = us.GetEmail(cu.cid);
        String cn=content.getText();
        String sb=subject.getText();
        SendMail.sendMail(email,sb, cn);
        
    }

}
