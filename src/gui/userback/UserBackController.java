/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.userback;

import java.net.URL;
import entities.user.User;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.user.UserService;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * FXML Controller class
 *
 * @author moez
 */
public class UserBackController implements Initializable {

    //@FXML
    //private TableColumn<?, ?> userIdCol;
    @FXML
    private TableColumn<?, ?> usernameCol;
    @FXML
    private TableColumn<?, ?> emailCol;
    @FXML
    private TableColumn<?, ?> roleCol;
    @FXML
    private TableColumn<?, ?> aboutCol;
    @FXML
    private TableView<User> userTable;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //userIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        aboutCol.setCellValueFactory(new PropertyValueFactory<>("about"));
        afficherUsers();
    }    
    
    private void afficherUsers() 
    {
        ObservableList<User> Users = FXCollections.observableArrayList();
        UserService us = new UserService();
        us.getAllUsers().forEach(u -> Users.add(u));
        userTable.setItems(Users);
    }
}
