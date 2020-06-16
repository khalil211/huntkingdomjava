/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.group;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import entities.publication.Publication;
import entities.user.CurrentUser;
import huntkingdom.HuntKingdom;
import java.io.IOException;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import services.publication.PublicationService;
import services.group.MembershipService;

/**
 * FXML Controller class
 *
 * @author moez
 */
public class GroupController implements Initializable {

    @FXML
    private TableView<Publication> tablegrppublications;
    @FXML
    private TableColumn<?, ?> membre;
    @FXML
    private TableColumn<?, ?> publication;
    @FXML
    private TextArea pubgroup;
    @FXML
    private Button joinleave;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        CurrentUser cu = CurrentUser.CurrentUser();
        MembershipService ms = new MembershipService();
        if (ms.isAMember(cu.id,cu.targetGroupId))
        {
          joinleave.setText("quitter le groupe");  
        }
        else
        {
          joinleave.setText("joindre le groupe");
          pubgroup.setDisable(true);
        }
        publication.setCellValueFactory(new PropertyValueFactory<>("mypublication"));
        membre.setCellValueFactory(new PropertyValueFactory<>("usernamep"));
        
        afficherGroupPublications();
    }    

    @FXML
    private void monprofil(ActionEvent event) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/profile/Profile.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene);
    }

    @FXML
    private void joindrequitter(ActionEvent event) throws Exception
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        MembershipService ms = new MembershipService();
        if (ms.isAMember(cu.id,cu.targetGroupId))
        {
            ms.quitterGroupe();
        }
        else
        {
          ms.ajouterMembre(); 
        }
        
    }

    @FXML
    private void groupinfo(ActionEvent event) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/group/GroupInfo.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene);
    }

    @FXML
    private void publier(ActionEvent event) throws SQLException, IOException
    {
       CurrentUser cu = CurrentUser.CurrentUser(); 
        if( !pubgroup.getText().isEmpty())
        {
            PublicationService ps = new PublicationService();
           
            Publication p = new Publication(cu.id,pubgroup.getText(),cu.targetGroupId);
            ps.ajouterGroupPublication(p);
            
        } 
    }
    
    private void afficherGroupPublications() 
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        ObservableList<Publication> publications2 = FXCollections.observableArrayList();
        PublicationService ps2 = new PublicationService();
        ps2.getGroupPublications().forEach(p2 -> publications2.add(p2));
        tablegrppublications.setItems(publications2);
    }
    
}
