/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.appointments;

import entities.Appointments.appointments;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import static java.time.Clock.system;
import java.time.Instant;
import java.time.LocalDate;
import static java.time.LocalDateTime.now;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.TemporalQueries.localDate;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import static jdk.nashorn.internal.runtime.Debug.id;
import services.Appointments.appointmentsService;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author marwe
 */
public class AppointmentsController implements Initializable {

    @FXML
    private Label mainLabel;
    @FXML
    private DatePicker start;

    @FXML
    private DatePicker end;
    @FXML
    private TextArea descr;
    @FXML
    private TextField title;
    @FXML
    private DatePicker calendar;
    @FXML
    private ChoiceBox<?> hour;
    @FXML
    private TextField r;
    @FXML
    private ChoiceBox<?> min;
    @FXML
    private DatePicker calendar1;
    @FXML
    private ChoiceBox<?> hour1;
    @FXML
    private ChoiceBox<?> min1;
    @FXML
    private TableView<appointments> events;
    @FXML
    private TableColumn<appointments, String> titre;
    @FXML
    private TableColumn<appointments, Integer> id;
    @FXML
    private TableColumn<appointments, String> description;
    @FXML
    private TableColumn<appointments, Date> start_date;
    @FXML
    private TableColumn<appointments, Date> end_date;
     private final ObservableList<appointments> data = FXCollections.observableArrayList();
      ObservableList<String> list = FXCollections.observableArrayList("ok","bb");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try
    {
            appointmentsService Servcom = new appointmentsService();
            List<appointments> list = Servcom.afficheEvents();
            System.out.println(list);
            ObservableList<appointments> cls = FXCollections.observableArrayList();
            for (appointments aux : list) {
          
                cls.add(new appointments(aux.getId(),aux.getTitle(),aux.getDescription(),aux.getStart_date(),aux.getEnd_date()));
            }   
             id.setCellValueFactory(new PropertyValueFactory<>("id"));

        titre.setCellValueFactory(new PropertyValueFactory<>("Title"));

        description.setCellValueFactory(new PropertyValueFactory<>("Description"));
         start_date.setCellValueFactory(new PropertyValueFactory<>("start_date"));
        end_date.setCellValueFactory(new PropertyValueFactory<>("end_date"));
            

            events.setItems(cls);
           
            }catch(Exception ex){
                    System.out.println(ex);
                   
             }
    }  
     @FXML
    private void supprimer(ActionEvent event) throws SQLException {
   
   appointments tmp=new appointments(); 
        
if(!tmp.equals(events.getSelectionModel().getSelectedItem())){
    
         appointmentsService ser = new appointmentsService();
         
            ObservableList<appointments> SingleDemandes ;
            SingleDemandes=events.getSelectionModel().getSelectedItems();
            appointments C1=SingleDemandes.get(0);
             ser.supprimeEvent(C1.getId());
                TrayNotification tray =new TrayNotification();
            tray.setTitle("Succès");
        tray.setMessage("Suppression avec succès !");
        tray.setAnimationType(AnimationType.POPUP);
        tray.setNotificationType(NotificationType.INFORMATION);
        tray.showAndWait();
                        List<appointments> list = ser.afficheEvents();

           ObservableList<appointments> cls = FXCollections.observableArrayList();
            for (appointments aux : list) {
          
                cls.add(new appointments(aux.getId(),aux.getTitle(),aux.getDescription(),aux.getStart_date(),aux.getEnd_date()));
            }
              id.setCellValueFactory(new PropertyValueFactory<>("id"));
             titre.setCellValueFactory(new PropertyValueFactory<>("Title"));

        description.setCellValueFactory(new PropertyValueFactory<>("Description"));
         start_date.setCellValueFactory(new PropertyValueFactory<>("start_date"));
        end_date.setCellValueFactory(new PropertyValueFactory<>("end_date"));
            

            events.setItems(cls);
           
    }

           
        
    }
    
      

    @FXML
    private void submit(ActionEvent event) throws SQLException {
        if (title.getText().isEmpty()||descr.getText().isEmpty()){
            Alert alert=new Alert(AlertType.WARNING);
            alert.setTitle("Ajout impossible");
            alert.setHeaderText("Remplir les champs");
            alert.showAndWait();
        }
        else {
            appointments ca  =  new appointments();
            appointmentsService sca = new appointmentsService();
            ca.setTitle(title.getText());
            ca.setDescription(descr.getText());
            LocalDate l = start.getValue();
             LocalDate l2 = end.getValue();
            
             Timestamp ts = Timestamp.valueOf(l.atTime(LocalTime.MIDNIGHT));
             Timestamp ts1 = Timestamp.valueOf(l2.atTime(LocalTime.MIDNIGHT));
            ca.setStart_date(ts);
            ca.setEnd_date(ts1);
           
   
           
            sca.ajoutA(ca);
                     appointmentsService ser = new appointmentsService();

            List<appointments> list = ser.afficheEvents();

           ObservableList<appointments> cls = FXCollections.observableArrayList();
            for (appointments aux : list) {
          
                cls.add(new appointments(aux.getId(),aux.getTitle(),aux.getDescription(),aux.getStart_date(),aux.getEnd_date()));
            }
              id.setCellValueFactory(new PropertyValueFactory<>("id"));
             titre.setCellValueFactory(new PropertyValueFactory<>("Title"));

        description.setCellValueFactory(new PropertyValueFactory<>("Description"));
         start_date.setCellValueFactory(new PropertyValueFactory<>("start_date"));
        end_date.setCellValueFactory(new PropertyValueFactory<>("end_date"));
            

            events.setItems(cls);
    }}

    @FXML
    private void cancel(ActionEvent event) {
    }
 @FXML
    void update(ActionEvent event) throws SQLException {
 appointments ca  =  new appointments();
            appointmentsService sca = new appointmentsService();
            
        
if(!ca.equals(events.getSelectionModel().getSelectedItem())){
            ca.setId(events.getSelectionModel().getSelectedItem().getId());
           ca.setTitle(title.getText());
            ca.setDescription(descr.getText());
            LocalDate l = start.getValue();
             LocalDate l2 = end.getValue();
            
             Timestamp ts = Timestamp.valueOf(l.atTime(LocalTime.MIDNIGHT));
             Timestamp ts1 = Timestamp.valueOf(l2.atTime(LocalTime.MIDNIGHT));
            ca.setStart_date(ts);
            ca.setEnd_date(ts1);
            sca.modifierEvent(ca);
                     appointmentsService ser = new appointmentsService();

            List<appointments> list = ser.afficheEvents();

           ObservableList<appointments> cls = FXCollections.observableArrayList();
            for (appointments aux : list) {
          
                cls.add(new appointments(aux.getId(),aux.getTitle(),aux.getDescription(),aux.getStart_date(),aux.getEnd_date()));
            }
              id.setCellValueFactory(new PropertyValueFactory<>("id"));
             titre.setCellValueFactory(new PropertyValueFactory<>("Title"));

        description.setCellValueFactory(new PropertyValueFactory<>("Description"));
         start_date.setCellValueFactory(new PropertyValueFactory<>("start_date"));
        end_date.setCellValueFactory(new PropertyValueFactory<>("end_date"));
            

            events.setItems(cls);
    }}
   
   @FXML
    void Search(ActionEvent event) {
 try {
              System.out.println("Searching");   
                 
                appointmentsService se = new appointmentsService();
                     List<appointments> list = se.displayClause(" WHERE description LIKE '%"+r.getText()+"%' ");
                     ObservableList<appointments> cls = FXCollections.observableArrayList();
                     
             for (appointments aux : list) {
                 System.out.println(aux);
                cls.add(new appointments(aux.getId(),aux.getTitle(),aux.getDescription(),aux.getStart_date(),aux.getEnd_date()));
            }
             id.setCellValueFactory(new PropertyValueFactory<>("id"));
             titre.setCellValueFactory(new PropertyValueFactory<>("Title"));

        description.setCellValueFactory(new PropertyValueFactory<>("Description"));
         start_date.setCellValueFactory(new PropertyValueFactory<>("start_date"));
        end_date.setCellValueFactory(new PropertyValueFactory<>("end_date"));
            

            events.setItems(cls);
                 }
                 catch (SQLException ex) {
                    System.out.println(ex);
                 }
    }
}
