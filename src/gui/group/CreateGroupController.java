/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.group;

import huntkingdom.HuntKingdom;
import entities.group.Group;
import entities.user.CurrentUser;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import services.group.GroupService;
import services.group.MembershipService;


/**
 * FXML Controller class
 *
 * @author moez
 */
public class CreateGroupController implements Initializable {

    @FXML
    private TextField tfnom;
    @FXML
    private TextArea tfdescription;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
    }    

    @FXML
    private void creergroupe(ActionEvent event) throws SQLException, IOException
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        if( !tfnom.getText().isEmpty() && !tfdescription.getText().isEmpty())
        {
            GroupService gs = new GroupService();
            MembershipService ms = new MembershipService();
           
            Group g = new Group(tfnom.getText(),tfdescription.getText());
            gs.ajouterGroup(g);
            cu.targetGroupId=gs.groupId();
            ms.ajouterAdmin();

            Parent root = FXMLLoader.load(getClass().getResource("/gui/group/Group.fxml"));
            Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
            HuntKingdom.stage.setScene(scene); 
        }
    }

    private void monprofil(ActionEvent event) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/profile/Profile.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene); 
    }
    
}
