/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.search;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import entities.interests.Myinterests;
import entities.user.CurrentUser;
import entities.user.User;
import huntkingdom.HuntKingdom;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import services.interests.MyinterestsService;
import services.user.UserService;

/**
 * FXML Controller class
 *
 * @author moez
 */
public class InterestSearchController implements Initializable {

    @FXML
    private TableView<Myinterests> tablesearch;
    @FXML
    private TableColumn<?, ?> users;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
        users.setCellValueFactory(new PropertyValueFactory<>("userString"));
        
        afficherSearch();
    }    

    
    private void afficherSearch() 
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        ObservableList<Myinterests> Users = FXCollections.observableArrayList();
        MyinterestsService mi = new MyinterestsService();
        mi.searchUsers(cu.search).forEach(u -> Users.add(u));
        tablesearch.setItems(Users);
    }
    @FXML
    private void selectionUser(MouseEvent event) throws Exception 
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        Myinterests mi = tablesearch.getSelectionModel().getSelectedItem();
        cu.targetId=mi.getUserId();
        Parent root = FXMLLoader.load(getClass().getResource("/gui/profile/SearchProfile.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene);
    }

    @FXML
    private void retour(ActionEvent event) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/profile/Profile.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene);
    }
    
}
