/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.reclamation;

import entities.Reclamation.reclamation;
import entities.user.CurrentUser;
import java.io.IOException;
import services.Reclamation.reclamationService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.A;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import static org.apache.commons.lang3.time.FastDateParserSDFTest.data;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author marwe
 */
public class reclamationController implements Initializable {

    @FXML
    private AnchorPane parent;
    @FXML
    private Button supp;
    @FXML
    private Button rep;
    @FXML
    private TextField recherche;
    @FXML
    private TableColumn<reclamation, Integer> id;
    @FXML
    private TableColumn<reclamation, String> username;
    @FXML
    private TableColumn<reclamation, Date> date_creation;
    @FXML
    private TableColumn<reclamation, String> description;
    @FXML
    private TableColumn<reclamation, Integer> type;
    @FXML
    private TableColumn<reclamation, Image> image;
        ImageView img = new ImageView();

    @FXML
    private TableColumn<reclamation, Integer> id_user;
    @FXML private TableView<reclamation> listerec;
     private final ObservableList<reclamation> data = FXCollections.observableArrayList();
    ObservableList<String> list = FXCollections.observableArrayList("ok","bb");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try
    {
            reclamationService Servcom = new reclamationService();
            List<reclamation> list = Servcom.getAllreclamation();
            System.out.println(list);
            ObservableList<reclamation> cls = FXCollections.observableArrayList();
            for (reclamation aux : list) {
          
                cls.add(new reclamation(aux.getId(),aux.getUser_id(),aux.getUsername(),aux.getDate_creation(),aux.getDescription(),aux.getType(),aux.getFichier()));
            }
             id.setCellValueFactory(new PropertyValueFactory<>("id"));
        id_user.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        username.setCellValueFactory(new PropertyValueFactory<>("Username"));
        date_creation.setCellValueFactory(new PropertyValueFactory<>("date_creation"));

        description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        image.setCellValueFactory(new PropertyValueFactory<>("fichier"));
            

            listerec.setItems(cls);
           
            }catch(Exception ex){
                    System.out.println(ex);
                   
             }
    }    
   @FXML
    private void supprimer(ActionEvent event) throws SQLException {
        reclamation tmp=new reclamation(); 
        
if(!tmp.equals(listerec.getSelectionModel().getSelectedItem())){
    
         reclamationService ser = new reclamationService();
         
            ObservableList<reclamation> SingleDemandes ;
            SingleDemandes=listerec.getSelectionModel().getSelectedItems();
            reclamation C1=SingleDemandes.get(0);
             ser.supprimereclamation(C1.getId());
                TrayNotification tray =new TrayNotification();
            tray.setTitle("Succès");
        tray.setMessage("Suppression avec succès !");
        tray.setAnimationType(AnimationType.POPUP);
        tray.setNotificationType(NotificationType.INFORMATION);
        tray.showAndWait();
                        List<reclamation> list = ser.getAllreclamation();

           ObservableList<reclamation> cls = FXCollections.observableArrayList();
            for (reclamation aux : list) {
          
                cls.add(new reclamation(aux.getId(),aux.getUser_id(),aux.getUsername(),aux.getDate_creation(),aux.getDescription(),aux.getType(),aux.getFichier()));
            }
             id.setCellValueFactory(new PropertyValueFactory<>("id"));
        id_user.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        username.setCellValueFactory(new PropertyValueFactory<>("Username"));
        date_creation.setCellValueFactory(new PropertyValueFactory<>("date_creation"));

        description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        image.setCellValueFactory(new PropertyValueFactory<>("image"));
            

            listerec.setItems(cls);
           
    }
        
    }
 @FXML
    public void Search(){
       
        try {
                 
                 
                reclamationService se = new reclamationService();
                     List<reclamation> list = se.displayClause(" WHERE username LIKE '%"+recherche.getText()+"%' or description LIKE '%"+recherche.getText()+"%' ");
                     ObservableList<reclamation> cls = FXCollections.observableArrayList();
                     
             for (reclamation aux : list) {
          
                cls.add(new reclamation(aux.getId(),aux.getUser_id(),aux.getUsername(),aux.getDate_creation(),aux.getDescription(),aux.getType(),aux.getFichier()));
            }
             id.setCellValueFactory(new PropertyValueFactory<>("id"));
        id_user.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        username.setCellValueFactory(new PropertyValueFactory<>("Username"));
        date_creation.setCellValueFactory(new PropertyValueFactory<>("date_creation"));

        description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        image.setCellValueFactory(new PropertyValueFactory<>("image"));
            

            listerec.setItems(cls);
                 }
                 catch (SQLException ex) {
                    System.out.println(ex);
                 }
        }
   
    @FXML
    private void mail(ActionEvent event) throws IOException {
           reclamation tmp=new reclamation(); 
        if(!tmp.equals(listerec.getSelectionModel().getSelectedItem())){
        CurrentUser cu = CurrentUser.CurrentUser();
        reclamation p = listerec.getSelectionModel().getSelectedItem();
        cu.cid=p.getUser_id() ;
        //String request ="SELECT email FROM fos_user WHERE id="+cu.cid;
        }
         FXMLLoader loader = new FXMLLoader(getClass().getResource("mailer.fxml"));
        Parent root = loader.load();
        listerec.getScene().setRoot(root);
    
    }
       
}