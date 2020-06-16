/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.coach;

import entities.chien.Chien;
import entities.coach.Coach;
import entities.user.CurrentUser;
import huntkingdom.HuntKingdom;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import services.chien.ChienService;
import services.coach.CoachService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import static javax.swing.Spring.height;
import static javax.swing.Spring.width;

/**
 * FXML Controller class
 *
 * @author Louay
 */
public class CoachClientController implements Initializable {
     @FXML private TableView<Chien> listechien;
    @FXML private TableColumn<Chien, String> clientCol;
    @FXML private TableColumn<Chien, String> nomCol;
    @FXML private TableColumn<Chien, Integer> ageCol;
    @FXML private TableColumn<Chien, Integer> noteCol;
    

    @FXML private TableColumn<Chien, String> maladieCol;
    @FXML private TableColumn<Chien, String> dateCol;
    @FXML private TableColumn<Chien, String> typeCol;
    @FXML private TableColumn<Chien, String> etatCol;
    @FXML
    private TextField noteText;
    @FXML private Button changeretat;
    @FXML private Button generateCode;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CoachService sca = new CoachService();
       
        
                ChienService cs=new ChienService();
       
        clientCol.setCellValueFactory(new PropertyValueFactory<Chien, String>("username"));
        nomCol.setCellValueFactory(new PropertyValueFactory<Chien, String>("nom"));
        maladieCol.setCellValueFactory(new PropertyValueFactory<Chien, String>("maladie"));
        noteCol.setCellValueFactory(new PropertyValueFactory<Chien, Integer>("note"));
        ageCol.setCellValueFactory(new PropertyValueFactory<Chien, Integer>("age"));
        dateCol.setCellValueFactory(new PropertyValueFactory<Chien, String>("dateToString"));
        etatCol.setCellValueFactory(new PropertyValueFactory<Chien, String>("etat"));
        typeCol.setCellValueFactory(new PropertyValueFactory<Chien, String>("typeChase"));

        ObservableList<Chien> chiensObs=FXCollections.observableArrayList();
        
         try {
             for(Chien c : cs.getAllChiensOfCoach())
                 chiensObs.add(c);
         } catch (SQLException ex) {
             Logger.getLogger(CoachClientController.class.getName()).log(Level.SEVERE, null, ex);
         }
        listechien.setItems(chiensObs);
        // TODO
            Coach co=new Coach();
       CurrentUser cu = CurrentUser.CurrentUser();
       co= sca.getC(cu.id);    
      if("Disponible".equals(co.getEtat()))
        {
           
            changeretat.setStyle("-fx-background-color: #37cf42");
        }
        else{
            changeretat.setStyle("-fx-background-color: #FF0000");
        }
    } 
    
    
    
    
    
    @FXML
    private void noterChien(MouseEvent event) {
        Chien c=listechien.getSelectionModel().getSelectedItem();
        ChienService cs=new ChienService();

        int note = Integer.parseInt(noteText.getText());
     c.setNote(note);
           
            cs.update(c,note);
            
          
            listechien.refresh();
        }
    private void changerEtatBoutons(Coach co) {
         
        if("Non Disponible".equals(co.getEtat()))
        {
           
            changeretat.setStyle("-fx-background-color: #37cf42");
        }
        else{
            changeretat.setStyle("-fx-background-color: #FF0000");
        }
       
    }
    @FXML
    private void changeStatus(MouseEvent event) throws SQLException {
                CoachService sca = new CoachService();

        Coach co=new Coach();
       CurrentUser cu = CurrentUser.CurrentUser();
       co= sca.getC(cu.id);
        Coach c=new Coach();
        c.setUserId(cu.id);
        CoachService cs=new CoachService();
           
            cs.ChangeStatus(c);
            
          changerEtatBoutons(co);
            listechien.refresh();
        }
    @FXML
    private void retour(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/profile/Profile.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene);
    }
     @FXML
     private void generateCode(MouseEvent event) throws Exception {
                  CoachService sca = new CoachService();
                  Coach co=new Coach();
                  CurrentUser cu = CurrentUser.CurrentUser();
                   co= sca.getC(cu.id);
                   String text="Name: "+co.getNom()+"\nEmail: "+co.getEmail()+"\nSpécialité: "+co.getRace()+"\nExperience: "+co.getExperienceYears()+"\nStatus: "+co.getEtat();
       Stage primaryStage=new Stage();
           
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        String myWeb = text;
        int width = 300;
        int height = 300;
        String fileType = "png";
         
        BufferedImage bufferedImage = null;
        try {
            BitMatrix byteMatrix = qrCodeWriter.encode(myWeb, BarcodeFormat.QR_CODE, 300, 300);
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            bufferedImage.createGraphics();
             
            Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, width, height);
            graphics.setColor(Color.BLACK);
             
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
             
            System.out.println("Success...");
             
        } catch (WriterException ex) {
           
        }
         
        ImageView qrView = new ImageView();
        qrView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
         
        StackPane root = new StackPane();
        root.getChildren().add(qrView);
         
        Scene scene = new Scene(root, 350, 350);
         
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
     
    
    }
}
