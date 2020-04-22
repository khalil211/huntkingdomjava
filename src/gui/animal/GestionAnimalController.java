/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.animal;

import entities.animal.Animal;
import entities.animal.CategorieAnimal;
import huntkingdom.HuntKingdom;
import java.awt.image.BufferedImage;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import org.apache.commons.lang3.RandomStringUtils;
import static org.apache.commons.lang3.time.FastDateParserSDFTest.data;
import static org.apache.commons.lang3.time.FastDatePrinterTimeZonesTest.data;
import static org.apache.commons.lang3.time.WeekYearTest.data;
import services.animal.ServiceAnimal;
import services.animal.ServiceCategorieAnimal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import utils.MyDB;

/**
 * FXML Controller class
 *
 * @author G I E
 */
public class GestionAnimalController implements Initializable {

    @FXML
    private TextField nomText;
    @FXML
    private TextField descText;
    
    @FXML
    private TextField zoneText;
    @FXML
    private TableView<Animal> listAnimal;
    @FXML
    private TableColumn<Animal, String> nomCol;
    @FXML
    private TableColumn<Animal, String> descriptionCol;
    @FXML
    private TableColumn<Animal, String> mediasCol;
    @FXML
    private TableColumn<Animal, String> zoneCol;
    @FXML
    private TableColumn<Animal, String> saisonCol;
    @FXML
    private ComboBox<CategorieAnimal> categorieA;
    @FXML
    private ComboBox<String> saisonCombo;
    @FXML
    private ImageView imgButton;
    @FXML
    private Button img;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServiceCategorieAnimal sca = new ServiceCategorieAnimal();
        categorieA.setItems(sca.getListCategorieAnimal());
        categorieA.getSelectionModel().selectFirst();
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        mediasCol.setCellValueFactory(new PropertyValueFactory<>("medias"));
        zoneCol.setCellValueFactory(new PropertyValueFactory<>("zone"));
        saisonCombo.getItems().setAll("Printemps", "été", "automne", "hiver");
        saisonCombo.getSelectionModel().selectFirst();
        ServiceAnimal as = new ServiceAnimal();
        listAnimal.setItems(as.getListeAnimaux());
    }    

    @FXML
    private void ajouter(MouseEvent event) {
        if (nomText.getText().isEmpty()||descText.getText().isEmpty()){
            Alert alert=new Alert(AlertType.WARNING);
            alert.setTitle("Ajout impossible");
            alert.setHeaderText("Remplir les champs");
            alert.showAndWait();
        }
        else {
            Animal a  =  new Animal();
            ServiceAnimal sa = new ServiceAnimal();
            a.setNom(nomText.getText());
            a.setDescription(descText.getText());
            Image image1=null;
            image1 = imgButton.getImage();
            String medias = null;
            try {
                medias = saveToFileImageNormal(image1);
            } catch (SQLException ex) {
                Logger.getLogger(GestionAnimalController.class.getName()).log(Level.SEVERE, null, ex);
            }
            a.setMedias(medias);
            a.setZone(zoneText.getText());
            a.setSaison(saisonCombo.getSelectionModel().getSelectedItem());
            a.setCategorie_id(categorieA.getSelectionModel().getSelectedItem().getId());
            sa.ajouterAnimal(a);
            listAnimal.getItems().add(a);
            nomText.setText("");
            descText.setText("");
            imgButton.setAccessibleText(medias);
            zoneText.setText("");
            TrayNotification tray =new TrayNotification();
            tray.setTitle("Succès");
            tray.setMessage("Ajout d'un animal avec succès !");
            tray.setAnimationType(AnimationType.POPUP);
            tray.setNotificationType(NotificationType.INFORMATION);
            tray.showAndWait();
        }
    }

    @FXML
    private void modifier(MouseEvent event) {
        Animal a = listAnimal.getSelectionModel().getSelectedItem();
        if (a!=null){
            ServiceAnimal sa = new ServiceAnimal();
            a.setCategorie_id(categorieA.getSelectionModel().getSelectedItem().getId());
            a.setNom(nomText.getText());
            a.setDescription(descText.getText());
            a.setZone(zoneText.getText());
            a.setSaison(saisonCombo.getSelectionModel().getSelectedItem());
            sa.updateAnimal(a.getId(),a.getCategorie_id(),a.getNom(), a.getDescription(), a.getMedias(),a.getZone(),a.getSaison());
            listAnimal.refresh();
            TrayNotification tray =new TrayNotification();
            tray.setTitle("Succès");
            tray.setMessage("Modification avec succès !");
            tray.setAnimationType(AnimationType.POPUP);
            tray.setNotificationType(NotificationType.INFORMATION);
            tray.showAndWait();
        }
    }

    @FXML
    private void supprimer(MouseEvent event) throws SQLException {
        Animal a = listAnimal.getSelectionModel().getSelectedItem();
        if (a!=null){
            ServiceAnimal sa = new ServiceAnimal();
            sa.deleteAnimal(a.getId());
            listAnimal.getItems().remove(a);
            nomText.setText("");
            descText.setText("");
            zoneText.setText("");
            TrayNotification tray =new TrayNotification();
            tray.setTitle("Succès");
            tray.setMessage("Suppression avec succès !");
            tray.setAnimationType(AnimationType.POPUP);
            tray.setNotificationType(NotificationType.INFORMATION);
            tray.showAndWait();
        }
    }

    @FXML
    private void selection(MouseEvent event){
        Animal a = listAnimal.getSelectionModel().getSelectedItem();
        if (a!=null){
            categorieA.getSelectionModel().select(categorieA.getItems().stream().filter(c->c.getId()==a.getCategorie_id()).findFirst().get());
            nomText.setText(a.getNom());
            descText.setText(a.getDescription());
            zoneText.setText(a.getZone());
            a.setSaison(saisonCombo.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    private void retour(MouseEvent event) throws IOException {        
        Parent root = FXMLLoader.load(getClass().getResource("/gui/animal/MenuAdmin.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene);
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
            Image medias = SwingFXUtils.toFXImage(bufferedImage, null);
            imgButton.setImage(medias);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public static String saveToFileImageNormal(Image medias)throws SQLException  {

        String ext = "jpg";
        File dir = new File("C:/wamp64/www/HuntKingdomjava/uploads/");
        String name = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(8), ext);
        File outputFile = new File(dir, name);
        BufferedImage bImage = SwingFXUtils.fromFXImage(medias, null);
        try {
            ImageIO.write(bImage, "png", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return name;
    }
    
}
