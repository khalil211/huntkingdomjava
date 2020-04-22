/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.profile;

import entities.friends.Friends;
import entities.publication.Publication;
import entities.user.CurrentUser;
import entities.user.User;
import huntkingdom.HuntKingdom;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import services.user.UserService;
import services.friends.FriendsService;
import services.publication.PublicationService;
import services.interests.InterestsService;
import services.interests.MyinterestsService;
import entities.interests.Myinterests;
import entities.interests.Interests;


/**
 * FXML Controller class
 *
 * @author moez
 */
public class SearchProfileController implements Initializable {

    @FXML
    private Label usernameLabel;
    @FXML
    private Label aboutLabel;
    @FXML
    private Button addbutton;
    @FXML
    private TableView<Friends> tablefriends;
    @FXML
    private TableColumn<?, ?> friendsnumber;
    @FXML
    private TableView<Publication> tablepublications;
    @FXML
    private TableColumn<?, ?> publicationtext;
    @FXML
    private TableView<Myinterests> myinterests1;
    @FXML
    private TableColumn<?, ?> colmyinterests1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        User user = new User();
        UserService us = new UserService();
        MyinterestsService mi = new MyinterestsService();
        FriendsService fs = new FriendsService();
        
        user=us.GetProfile(cu.targetId);
        
        friendsnumber.setCellValueFactory(new PropertyValueFactory<>("secondusername"));
        friendsnumber.setText(fs.nbrFriends(cu.targetId)+" Amis");
        
        publicationtext.setCellValueFactory(new PropertyValueFactory<>("mypublication"));
        
        usernameLabel.setText(user.getUsername());
        aboutLabel.setText(user.getAbout());
        
        colmyinterests1.setCellValueFactory(new PropertyValueFactory<>("myinterest"));
        colmyinterests1.setText(mi.nbrInterests(cu.targetId)+" Interets");
        
        try {
            if (button())
            {
             addbutton.setText("supprimer ami");   
            }
        } catch (Exception ex) {
            Logger.getLogger(SearchProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        afficherFriends();
        afficherMesPublications();
        afficherProfileInterests();
        
    }    

    private boolean button() throws Exception
    {
        FriendsService fs = new FriendsService();
      if (fs.checkFriends())
        {
            return true;
        } 
      return false;
    }
    
    
    @FXML
    private void ajouterAmi(ActionEvent event) throws SQLException, IOException
    {
        FriendsService fs = new FriendsService();
        
        if (!fs.checkFriends())
        {
          System.out.println("before aj");
         fs.ajouterAmi();
        }    
    }

    @FXML
    private void acceuil(ActionEvent event) throws Exception
    {
       Parent root = FXMLLoader.load(getClass().getResource("/gui/profile/Profile.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene); 
    }
    
    private void afficherProfileInterests() 
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        ObservableList<Myinterests> interests = FXCollections.observableArrayList();
        MyinterestsService mis = new MyinterestsService();
        mis.getAllMyInterests(cu.targetId).forEach(i -> interests.add(i));
        myinterests1.setItems(interests);
    }
    
    private void afficherFriends() 
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        ObservableList<Friends> myFriends = FXCollections.observableArrayList();
        FriendsService fs = new FriendsService();
        fs.getMyFriends(cu.targetId).forEach(f -> myFriends.add(f));
        tablefriends.setItems(myFriends);
    }
    
    private void afficherMesPublications() 
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        ObservableList<Publication> publications = FXCollections.observableArrayList();
        PublicationService ps = new PublicationService();
        ps.getAllMyPublications(cu.targetId).forEach(p -> publications.add(p));
        tablepublications.setItems(publications);
    }

    @FXML
    private void selectionuserinterest(MouseEvent event) {
    }
}
