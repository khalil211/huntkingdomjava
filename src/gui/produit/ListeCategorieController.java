/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.produit;

import entities.produit.Categorie;
import services.produit.CategorieService;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import utils.MyDB;

/**
 * FXML Controller class
 *
 * @author user
 */
public class ListeCategorieController implements Initializable {
    
@FXML
    private AnchorPane ButtonAdd;
@FXML
    private TableView<Categorie> ListeCategorie;
    private final ObservableList<Categorie> data = FXCollections.observableArrayList();

        ObservableList<String> list = FXCollections.observableArrayList("ok","cc");

    ObservableList<Categorie> clist;

    private Categorie categorieTest;
@FXML
    private TableColumn<Categorie, String> resNomC;
@FXML
    private TableColumn<Categorie, String> resDesC;
    @FXML
    private TableColumn<Categorie, Integer> resIdC;
    @FXML
    private Button buttonAjouter;
    @FXML
    private Button buttonSupprimer;
    
    private CategorieService cs = new CategorieService();
    @FXML
    private Button GProd;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Load();
    }    
    private void Load() {      
       try {
                            Connection con = MyDB.getInstance().getConnection();
                            Statement ste = con.createStatement();
                        data.clear();

            ResultSet rs = ste.executeQuery("select * from categorie");
                            //System.out.println(rs);
            while(rs.next()){
                data.add(new Categorie(rs.getInt(1),rs.getString(2),rs.getString(3)));
            }

        } catch (Exception e) {
                //Logger.getLogger(tab)
        }
               
            resIdC.setCellValueFactory(new PropertyValueFactory<>("id"));

            resNomC.setCellValueFactory(new PropertyValueFactory<>("nom"));
            resDesC.setCellValueFactory(new PropertyValueFactory<>("description"));
            
            
            ListeCategorie.setItems(data);
            ListeCategorie.setEditable(true);
            
            resIdC.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
            resNomC.setCellFactory(TextFieldTableCell.forTableColumn());
            resDesC.setCellFactory(TextFieldTableCell.forTableColumn());
            

            




    }

    @FXML
    private void Ajouter(ActionEvent event) throws IOException  {
          
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/Views/AjouterCategorie.fxml"));
        Scene tabbleViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(tabbleViewScene);
        window.show();
     
        
        
    }


    @FXML
    private void Supprimer(ActionEvent event) throws SQLException {
        
         ListeCategorie.setItems(data);
             ObservableList<Categorie> allDemandes,SingleDemandes ;
             allDemandes=ListeCategorie.getItems();
             SingleDemandes=ListeCategorie.getSelectionModel().getSelectedItems();
             Categorie A = SingleDemandes.get(0);
             CategorieService STD = new CategorieService(); // STD = Service TAB DEMANDE
             STD.deleteCat(A.getId());
             SingleDemandes.forEach(allDemandes::remove);    
        
              TrayNotification tray =new TrayNotification();
            tray.setTitle("Succès");
        tray.setMessage("Suppression avec succès !");
        tray.setAnimationType(AnimationType.POPUP);
        tray.setNotificationType(NotificationType.INFORMATION);
        tray.showAndWait();
    }

    @FXML
    private void ChangerNom(TableColumn.CellEditEvent cc) throws SQLException {
           Categorie tab_Demandeselected = ListeCategorie.getSelectionModel().getSelectedItem();
     tab_Demandeselected.setNom(cc.getNewValue().toString());
     cs.updateCat(tab_Demandeselected);
    }

    @FXML
    private void ChangerDesc(TableColumn.CellEditEvent cc) throws SQLException{
        
        Categorie tab_Demandeselected = ListeCategorie.getSelectionModel().getSelectedItem();
     tab_Demandeselected.setDescription(cc.getNewValue().toString());
     cs.updateCat(tab_Demandeselected);
        
        
    }

    @FXML
    private void GProd(ActionEvent event) throws IOException {
        
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("ListeProduit.fxml"));
        Scene tabbleViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(tabbleViewScene);
        window.show();
    }

      


    }
    
      

