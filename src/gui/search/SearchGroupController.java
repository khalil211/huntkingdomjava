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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import entities.group.Group;
import entities.user.CurrentUser;
import entities.user.User;
import huntkingdom.HuntKingdom;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import services.group.GroupService;

/**
 * FXML Controller class
 *
 * @author moez
 */
public class SearchGroupController implements Initializable {

    @FXML
    private TableView<Group> tablesearchgrp;
    @FXML
    private TableColumn<?, ?> groups;
    @FXML
    private Button usersbutton;
    @FXML
    private Button groupsbutton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        groups.setCellValueFactory(new PropertyValueFactory<>("name"));
        groupsbutton.setDisable(true);
        
        afficherSearch();
    }    


    private void afficherSearch() 
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        ObservableList<Group> groups = FXCollections.observableArrayList();
        GroupService gs = new GroupService();
        gs.searchGroups(cu.search).forEach(g -> groups.add(g));
        tablesearchgrp.setItems(groups);
    }
    
    @FXML
    private void retour(ActionEvent event) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/profile/Profile.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene);
    }

    @FXML
    private void searchusers(ActionEvent event) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/search/search.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene);
    }

    @FXML
    private void searchgroups(ActionEvent event) 
    {
        
    }

    @FXML
    private void selectionGroup(MouseEvent event) throws Exception 
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        Group g = tablesearchgrp.getSelectionModel().getSelectedItem();
        cu.targetGroupId=g.getId();
       
        Parent root = FXMLLoader.load(getClass().getResource("/gui/group/Group.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene);
    }
    
}
