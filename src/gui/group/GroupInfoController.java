/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.group;

import entities.group.Membership;
import entities.user.CurrentUser;
import huntkingdom.HuntKingdom;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import services.group.MembershipService;

/**
 * FXML Controller class
 *
 * @author moez
 */
public class GroupInfoController implements Initializable {

    @FXML
    private TableView<Membership> tablemembres;
    @FXML
    private TableColumn<?, ?> usernames;
    @FXML
    private TableColumn<?, ?> role;
    @FXML
    private Label groupename;
    @FXML
    private Label nbrmembre;
    @FXML
    private Label groupdescription;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        CurrentUser cu = CurrentUser.CurrentUser();
        MembershipService ms = new MembershipService();
        System.out.println("aa"+cu.targetGroupId);
        
        groupename.setText(ms.convertGroupToString(cu.targetGroupId));
        groupdescription.setText(ms.convertDescriptionToString(cu.targetGroupId));
        nbrmembre.setText(ms.numberGroupMembers()+" membres");
        
        usernames.setCellValueFactory(new PropertyValueFactory<>("stringUsername"));
        role.setCellValueFactory(new PropertyValueFactory<>("stringRole"));
        
        afficherMembers();
    }    

    @FXML
    private void selectionMember(MouseEvent event) throws Exception
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        Membership m = tablemembres.getSelectionModel().getSelectedItem();
        cu.targetId=m.getUserId();
        
        Parent root = FXMLLoader.load(getClass().getResource("/gui/profile/SearchProfile.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene); 
    }
    
    private void afficherMembers() 
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        ObservableList<Membership> myGroups = FXCollections.observableArrayList();
        MembershipService ms = new MembershipService();
        ms.getGroupMembers().forEach(f -> myGroups.add(f));
        tablemembres.setItems(myGroups);
    }

    @FXML
    private void goback(ActionEvent event) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/group/Group.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene); 
    }
    
}
