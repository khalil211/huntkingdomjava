/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.produit;


import static gui.produit.AjouterProduitController.saveToFileImageNormal;
import entities.produit.Produit;
import services.produit.ProduitService;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import org.apache.commons.lang3.RandomStringUtils;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import utils.MyDB;



/**
 * FXML Controller class
 *
 * @author user
 */
public class AjouterProduitController implements Initializable {

    private Connection con;
        private Statement ste;
   
    @FXML
    private TextField nomP;
    @FXML
    private TextArea descP;
    @FXML
    private TextField qtP;
    @FXML
    private TextField prixP;
    @FXML
    private Button bValider;
    @FXML
    private Button bAnnuler;
    @FXML
    private ImageView imgButton;
    @FXML
    private Button img;
    @FXML
    private ComboBox<String> categorie;
  
     ObservableList<String> list = FXCollections.observableArrayList();
    @FXML
    private Label catP;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         try { con = MyDB.getInstance().getConnection();
        ste=con.createStatement();
        ResultSet rs=ste.executeQuery("select nom_cat from categorie_produit");
        
        while (rs.next())
        {list.add(rs.getString("nom_cat"));}
        
        categorie.setItems(list);
        } catch(SQLException e) {}
        qtP.addEventFilter(KeyEvent.KEY_TYPED , numeric_Validation(5));
        prixP.addEventFilter(KeyEvent.KEY_TYPED , numeric_Validation(2));
        nomP.addEventFilter(KeyEvent.KEY_TYPED , letter_Validation(15));

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
        File dir = new File("C:/wamp64/www/huntkingdom/web/images/");
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

    @FXML
    private void ajouterProd(ActionEvent event) {
        
         try {
             Image image1=null;
             image1 = imgButton.getImage();
            String photo = null;
             photo = saveToFileImageNormal(image1);
             
            String nom = nomP.getText();
            String desc = descP.getText();
           
            int quan = Integer.parseInt(qtP.getText());
            int pr = Integer.parseInt(prixP.getText());
            String cat = (String) categorie.getValue();
            
            ResultSet rs=ste.executeQuery("SELECT `id` FROM `categorie_produit` WHERE `nom_cat`='"+cat+"'");

             if(rs.next()){
           ProduitService ps = new ProduitService();
           Produit p = new Produit(photo, nom, desc, quan, pr,rs.getInt(1));
            ps.ajouterProduit(p);
           }
         
    FXMLLoader loader = new FXMLLoader
                        (getClass()
                         .getResource("ListeProduit.fxml"));
            try {
                Parent root = loader.load();
               ListeProduitController apc = loader.getController();

                
                nomP.getScene().setRoot(root);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
          TrayNotification tray =new TrayNotification();
            tray.setTitle("Succès");
        tray.setMessage("Ajout avec succès !");
        tray.setAnimationType(AnimationType.POPUP);
        tray.setNotificationType(NotificationType.INFORMATION);
        tray.showAndWait();
     //   SendMail.sendMail("eya.elhachemi@esprit.tn",nomP.getText(),.getText());
    }

    @FXML
    private void Annuler(ActionEvent event) throws IOException {
        
         FXMLLoader loader = new FXMLLoader(getClass().getResource("ListeProduit.fxml"));
        Parent root = loader.load();
        nomP.getScene().setRoot(root);
    }
    
    public EventHandler<KeyEvent> numeric_Validation(final Integer max_Lengh) {
    return new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent e) {
            TextField txt_TextField = (TextField) e.getSource();                
            if (txt_TextField.getText().length() >= max_Lengh) {                    
                e.consume();
            }
          /*  if (txt_TextField.getText()) < 0) {                    
                e.consume();
            }*/
            if(e.getCharacter().matches("[0-9.]") ){ 
                if(txt_TextField.getText().contains(".") && e.getCharacter().matches("[.]")){
                    e.consume();
                }else if(txt_TextField.getText().length() == 0 && e.getCharacter().matches("[.]")){
                    e.consume(); 
                }
            }else{
                e.consume();
            }
        }
    };
}
    
    public EventHandler<KeyEvent> letter_Validation(final Integer max_Lengh) {
    return new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent e) {
            TextField txt_TextField = (TextField) e.getSource();                
            if (txt_TextField.getText().length() >= max_Lengh) {                    
                e.consume();
            }
            if(e.getCharacter().matches("[A-Za-z]")){ 
            }else{
                e.consume();
            }
        }
    };
}   
    
}
 
    

