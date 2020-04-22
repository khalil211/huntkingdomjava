/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.profile;

import entities.user.CurrentUser;
import entities.publication.Publication;
import entities.user.User;
import entities.friends.Friends;
import entities.group.Group;
import entities.interests.Interests;
import entities.interests.Myinterests;
import entities.group.Membership;
import huntkingdom.HuntKingdom;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import services.user.UserService;
import services.friends.FriendsService;
import services.group.GroupService;
import services.group.MembershipService;
import services.interests.InterestsService;
import services.interests.MyinterestsService;




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
    @FXML
    private Label usernameLabel;
    @FXML
    private Label aboutLabel;
    @FXML
    private TextArea tfsearch;
    @FXML
    private PasswordField changepassword;
    @FXML
    private PasswordField confirmpassword;
    @FXML
    private TextField changeabout;
    @FXML
    private Label erreur;
    @FXML
    private TableView<Friends> tableinvitations;
    @FXML
    private TableColumn<?, ?> names;
    @FXML
    private TableColumn<?, ?> hiddenid;
    @FXML
    private TableView<Publication> tablepublications;
    @FXML
    private TableColumn<?, ?> publicationtext;
    @FXML
    private TableView<Publication> tableallpublications;
    @FXML
    private TableColumn<?, ?> usernamepublication;
    @FXML
    private TableColumn<?, ?> allpublications;
    @FXML
    private TableView<Membership> tablegroup;
    @FXML
    private TableColumn<?, ?> groupname;
    @FXML
    private TableColumn<?, ?> role;
    @FXML
    private TableView<Friends> tablefriends;
    @FXML
    private TableColumn<?, ?> friendsnumber;
    @FXML
    private TableView<Myinterests> myinterests;
    @FXML
    private TableColumn<?, ?> colmyinterests;
    @FXML
    private TableView<Interests> allinterests;
    @FXML
    private TableColumn<?, ?> colallinterests;
    @FXML
    private Label labelinterest;
    @FXML
    private TableView<Myinterests> myinterests1;
    @FXML
    private TableColumn<?, ?> colmyinterests1;
    @FXML
    private Label labelabout;

    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        UserService us = new UserService();
        FriendsService fs = new FriendsService();
        MyinterestsService mi = new MyinterestsService();
        User user = new User();
        CurrentUser cu = CurrentUser.CurrentUser();
        user=us.GetProfile(cu.id);
        
        friendsnumber.setCellValueFactory(new PropertyValueFactory<>("secondusername"));
        friendsnumber.setText(fs.nbrFriends(cu.id)+" Amis");
        
        names.setCellValueFactory(new PropertyValueFactory<>("secondusername"));
        hiddenid.setCellValueFactory(new PropertyValueFactory<>("seconduser"));
        
        publicationtext.setCellValueFactory(new PropertyValueFactory<>("mypublication"));
        
        groupname.setCellValueFactory(new PropertyValueFactory<>("stringGroup"));
        role.setCellValueFactory(new PropertyValueFactory<>("stringRole"));
        
        allpublications.setCellValueFactory(new PropertyValueFactory<>("mypublication"));
        usernamepublication.setCellValueFactory(new PropertyValueFactory<>("usernamep"));
        
        colallinterests.setCellValueFactory(new PropertyValueFactory<>("interest"));
        
        colmyinterests.setCellValueFactory(new PropertyValueFactory<>("myinterest"));
        
        colmyinterests1.setCellValueFactory(new PropertyValueFactory<>("myinterest"));
        colmyinterests1.setText(mi.nbrInterests(cu.id)+" Interets");
        
        usernameLabel.setText(user.getUsername());
        labelabout.setText(user.getAbout());
        
        afficherMesPublications();
        afficherAllPublications();
        afficherInvitations();
        afficherMesGroupes();
        afficherFriends();
        afficherInterests();
        afficherMyInterests();
        afficherProfileInterests();
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
    private void selectionInvitations(MouseEvent event) throws Exception{
        CurrentUser cu = CurrentUser.CurrentUser();
        Friends f = tableinvitations.getSelectionModel().getSelectedItem();
        cu.targetId=f.getSeconduser();
        System.out.println("id target = "+cu.targetId);
       
    }
    
    @FXML
    private void publier(ActionEvent event) throws SQLException, IOException 
    {
        CurrentUser cu = CurrentUser.CurrentUser(); 
        if( !tfpublication.getText().isEmpty())
        {
            PublicationService ps = new PublicationService();
           System.out.println(cu.id);
            Publication p = new Publication(cu.id,tfpublication.getText());
            ps.ajouterPublication(p);   
        }
        refresh();
    }

    @FXML
    private void chercher(ActionEvent event)throws Exception
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        if( !tfsearch.getText().isEmpty())
        {
            cu.search=tfsearch.getText();
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/search/search.fxml")); 
           Parent root = loader.load();
           tfsearch.getScene().setRoot(root);
        }
        
       
    }

    @FXML
    private void aboutUpdate(ActionEvent event) 
    {
        UserService us = new UserService();
       if( !changeabout.getText().isEmpty())
        {
          us.updateAbout(changeabout.getText());
          erreur.setText("Description modifié !");
        } 
    }

    @FXML
    private void passwordUpdate(ActionEvent event) 
    {
        UserService us = new UserService();
        CurrentUser cu= CurrentUser.CurrentUser();
        
        if( !changepassword.getText().isEmpty() && !confirmpassword.getText().isEmpty())
        {
            if (changepassword.getText().equals(confirmpassword.getText()))
            {
                us.updatePassword(changepassword.getText(),cu.id); 
                erreur.setText("mot de passe modifié !");  
            }
            else
            {
               erreur.setText("Erreur : Les deux mots de passe ne sont pas identiques !"); 
            }
          
        } 
        
    }
    
    private void afficherInvitations() 
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        ObservableList<Friends> invitations = FXCollections.observableArrayList();
        FriendsService fs = new FriendsService();
        fs.getAllInvitations().forEach(f -> invitations.add(f));
        tableinvitations.setItems(invitations);
    }
    
    private void afficherInterests() 
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        ObservableList<Interests> interests = FXCollections.observableArrayList();
        InterestsService is = new InterestsService();
        is.getAllInterests().forEach(i -> interests.add(i));
        allinterests.setItems(interests);
    }
    
    private void afficherMyInterests() 
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        ObservableList<Myinterests> interests = FXCollections.observableArrayList();
        MyinterestsService mis = new MyinterestsService();
        mis.getAllMyInterests(cu.id).forEach(i -> interests.add(i));
        myinterests.setItems(interests);
    }
    
    private void afficherProfileInterests() 
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        ObservableList<Myinterests> interests = FXCollections.observableArrayList();
        MyinterestsService mis = new MyinterestsService();
        mis.getAllMyInterests(cu.id).forEach(i -> interests.add(i));
        myinterests1.setItems(interests);
    }
    
    private void afficherMesPublications() 
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        ObservableList<Publication> publications = FXCollections.observableArrayList();
        PublicationService ps = new PublicationService();
        ps.getAllMyPublications(cu.id).forEach(p -> publications.add(p));
        tablepublications.setItems(publications);
    }
    
    private void afficherAllPublications() 
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        ObservableList<Publication> publications2 = FXCollections.observableArrayList();
        PublicationService ps2 = new PublicationService();
        ps2.getAllPublications().forEach(p2 -> publications2.add(p2));
        tableallpublications.setItems(publications2);
    }

    private void afficherMesGroupes() 
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        ObservableList<Membership> myGroups = FXCollections.observableArrayList();
        MembershipService ms = new MembershipService();
        ms.getMyGroups().forEach(f -> myGroups.add(f));
        tablegroup.setItems(myGroups);
    }
    
    private void afficherFriends() 
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        ObservableList<Friends> myFriends = FXCollections.observableArrayList();
        FriendsService fs = new FriendsService();
        fs.getMyFriends(cu.id).forEach(f -> myFriends.add(f));
        tablefriends.setItems(myFriends);
    }
    
    @FXML
    private void accepterAmi(ActionEvent event) 
    {
     CurrentUser cu = CurrentUser.CurrentUser(); 
     FriendsService fs = new FriendsService();
     fs.accept();
    }

    @FXML
    private void refuserAmi(ActionEvent event) 
    {
     CurrentUser cu = CurrentUser.CurrentUser();
     FriendsService fs = new FriendsService();
     fs.refuse();
    }

    @FXML
    private void selectionPublication(MouseEvent event) throws Exception
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        Publication p = tableallpublications.getSelectionModel().getSelectedItem();
        cu.targetPubId=p.getId();
        cu.targetId=p.getIdUser();
        System.out.println("id pub target = "+cu.targetPubId);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/profile/publication.fxml")); 
        Parent root = loader.load();
        tfsearch.getScene().setRoot(root);
        
    }

    @FXML
    private void creergroupe(ActionEvent event) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/group/CreateGroup.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene); 
    }

    @FXML
    private void selectionGroup(MouseEvent event) throws Exception // here
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        Membership m = tablegroup.getSelectionModel().getSelectedItem();
        cu.targetGroupId=m.getGroupId();
        System.out.println("aa"+cu.targetGroupId);
        Parent root = FXMLLoader.load(getClass().getResource("/gui/group/Group.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene); 
    }

    public void refresh()
    {
        tableallpublications.refresh();
        tablepublications.refresh();
        System.out.println("pls");
    }

    @FXML
    private void selectionmyinterests(MouseEvent event) throws Exception
    {
        //deleteinterest.setDisable(false);
        //addinterest.setDisable(true);
        
        CurrentUser cu = CurrentUser.CurrentUser();
        MyinterestsService ms = new MyinterestsService();
        Myinterests m = myinterests.getSelectionModel().getSelectedItem();
        ms.unlike(cu.id,m.getMyinterest());
        labelinterest.setText("interest supprimé");
        myinterests.refresh();
        
    }

    @FXML
    private void selectionInterest(MouseEvent event) throws Exception
    {
        //deleteinterest.setDisable(true);
        //addinterest.setDisable(false);
        
        CurrentUser cu = CurrentUser.CurrentUser();
        MyinterestsService ms = new MyinterestsService();
        Myinterests mi = new Myinterests();
        Interests i = allinterests.getSelectionModel().getSelectedItem();
        mi.setMyinterest(i.getInterest());
        if(ms.interestExist(cu.id, i.getInterest()))
        {
          
         labelinterest.setText("Existe déja");
        }
        else
        {
          ms.ajouterMyinterests(mi);
          labelinterest.setText("intérêt ajouté !");
        }
        
        
    }

    @FXML
    private void selectionuserinterest(MouseEvent event) throws Exception
    {
    CurrentUser cu = CurrentUser.CurrentUser();
    Myinterests mi = myinterests1.getSelectionModel().getSelectedItem();
    cu.search=mi.getMyinterest();
    Parent root = FXMLLoader.load(getClass().getResource("/gui/search/InterestSearch.fxml"));
    Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
    HuntKingdom.stage.setScene(scene); 
    }

    
}
