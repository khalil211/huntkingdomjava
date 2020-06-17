/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.reclamation;

import entities.Reclamation.reclamation;
import static gui.produit.AjouterProduitController.saveToFileImageNormal;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import static java.util.Collections.list;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import org.apache.commons.lang3.RandomStringUtils;
import services.Reclamation.reclamationService;
import services.produit.ProduitService;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import utils.MyDB;

/**
 * FXML Controller class
 *
 * @author marwe
 */
public class frontController implements Initializable {
     private Connection con;
        private Statement ste;
    @FXML
    private ImageView imgButton;
    @FXML
    private TextField type;
    @FXML
    private TextArea descP;
    @FXML
    private Button bValider;
    @FXML
    private Button bAnnuler;
    @FXML
    private Button img;
    @FXML
    private ComboBox<?> categorie;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    
    }    

     @FXML
    private void addImage(MouseEvent event) {
         FileChooser fc = new FileChooser();

        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fc.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
        File selectedFile = fc.showOpenDialog(null);
        try {
            BufferedImage bufferedImage = ImageIO.read(selectedFile);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            imgButton.setImage(image);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    public static String saveToFileImageNormal(Image image)throws SQLException  {

        String ext = "jpg";
        File dir = new File("C:/wamp64/www/HuntKingdomjava/uploads/");
        String name = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(8), ext);
        File outputFile = new File(dir, name);
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(bImage, "png", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return name;
    }
     public void ajouterR(ActionEvent event) throws ParseException, SQLException {
        
        if (descP.getText().isEmpty()){
            Alert alert=new Alert(AlertType.WARNING);
            alert.setTitle("Ajout impossible");
            alert.setHeaderText("Remplir les champs");
            alert.showAndWait();
        }
        else {
            //Image image1=null;
         //    image1 = imgButton.getImage();
          //  String photo = null;
//             photo = saveToFileImageNormal(image1);
            reclamation ca  =  new reclamation();
            reclamationService sca = new reclamationService();
            ca.setUser_id(5);
            ca.setType(type.getText());
            ca.setDescription(descP.getText());
            ca.setResponse("null");
             
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            // Timestamp ts = Timestamp.valueOf(.atTime(LocalTime.MIDNIGHT));

            ca.setDate_creation(timestamp);
            ca.setFichier("D");
            ca.setStatus(0);
            
            
         
           
   
           
            sca.ajoutA(ca);
                    

    }
}}
