/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.profile;

import entities.commentaire.Commentaire;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import services.publication.PublicationService;
import services.commentaire.CommentaireService;

/**
 * FXML Controller class
 *
 * @author moez
 */
public class PublicationController implements Initializable {

    @FXML
    private Label usernamelabel;
    @FXML
    private Label publicationlabel;
    @FXML
    private TableView<Commentaire> commenttable;
    @FXML
    private TableColumn<?, ?> username;
    @FXML
    private TableColumn<?, ?> comment;
    @FXML
    private TextArea tfcommentaire;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        CurrentUser cu = CurrentUser.CurrentUser(); 
        PublicationService ps = new PublicationService();
        publicationlabel.setText(ps.publicationToString(cu.targetPubId));
        usernamelabel.setText(ps.convertToString(cu.targetId));
        
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        comment.setCellValueFactory(new PropertyValueFactory<>("text"));
        
        afficherCommentaires();
    }    

    private void backToProfile(ActionEvent event) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/profile/Profile.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene); 
    }

    @FXML
    private void ajouterCommentaire(ActionEvent event) throws Exception
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        if( !tfcommentaire.getText().isEmpty())
        {
          CommentaireService cs = new CommentaireService();
          cs.ajouterCommentaire(tfcommentaire.getText());
        }
        
    }
    
    private void afficherCommentaires() 
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        ObservableList<Commentaire> commentaires = FXCollections.observableArrayList();
        CommentaireService cs = new CommentaireService();
        cs.getAllCommentaires().forEach(c -> commentaires.add(c));
        commenttable.setItems(commentaires);
    }
    
}
