/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.search;

import entities.user.CurrentUser;
import entities.user.User;
import huntkingdom.HuntKingdom;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.user.UserService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author moez
 */
public class SearchController implements Initializable {

    @FXML
    private TableView<User> tablesearch;
    @FXML
    private TableColumn<?, ?> users;
    @FXML
    private Button usersbutton;
    @FXML
    private Button groupsbutton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        CurrentUser cu = CurrentUser.CurrentUser();
       users.setCellValueFactory(new PropertyValueFactory<>("username"));
       usersbutton.setDisable(true);
       afficherSearch();
       
    }    
    
     @FXML
    private void selectionUser(MouseEvent event) throws Exception{
        CurrentUser cu = CurrentUser.CurrentUser();
        User u = tablesearch.getSelectionModel().getSelectedItem();
        cu.targetId=u.getId();
        if (u != null) {
            //System.out.println("id target = "+cu.targetId);
        Parent root = FXMLLoader.load(getClass().getResource("/gui/profile/SearchProfile.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene);
            
        }
    }
    
    private void afficherSearch() 
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        ObservableList<User> Users = FXCollections.observableArrayList();
        UserService us = new UserService();
        us.searchUsers(cu.search).forEach(u -> Users.add(u));
        tablesearch.setItems(Users);
    }

    @FXML
    private void retour(ActionEvent event) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/profile/Profile.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene);
    }

    @FXML
    private void searchusers(ActionEvent event) {
    }

    @FXML
    private void searchgroups(ActionEvent event) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/search/SearchGroup.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene);
    }
}
