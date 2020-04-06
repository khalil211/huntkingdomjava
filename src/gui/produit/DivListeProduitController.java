/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.produit;

import entities.commande.ProduitCommande;
import entities.produit.Produit;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import services.commande.CommandeService;
import services.commande.ProduitCommandeService;
import huntkingdom.HuntKingdom;

/**
 * FXML Controller class
 *
 * @author user
 */
public class DivListeProduitController implements Initializable {

    @FXML
    private AnchorPane ap;
    @FXML
    private Label name;
    @FXML
    private Label souscat;
    @FXML
    private Label id;
    @FXML
    private Rectangle rectangle;
    @FXML
    private Label prixmoy;
    @FXML
    private Button ajouter;
    @FXML
    private Spinner<Integer> qte;
    @FXML
    private Label notifPanier;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ajouterP(ActionEvent event) {
    }
    
    
    Connection con;
    Statement ste;
     public void LoadValues(Produit e) throws IOException {
   
         name.setText(e.getNom());
        
       // souscat.setText(e.getCateorie()+"");
        

        prixmoy.setText(String.valueOf(e.getPrix())+" DT");
        id.setText(String.valueOf(e.getId()));
        

      
//        sq.setPadding(new Insets(-10, -10, -10, -10));

            Image imageURI = new Image("file:C:/wamp64/www/HuntKingdomjava/uploads/" + e.getImage());
         rectangle.setFill(new ImagePattern(imageURI));
        
      
        ap.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                   // doubleclick(event, e);
                }

            }
        });
    
        qte.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000, 1));
        CommandeService cs=new CommandeService();
        ajouter.setOnMouseClicked(me-> {
            ProduitCommandeService pcs=new ProduitCommandeService();
            ProduitCommande pc=new ProduitCommande();
            pc.setCommandeId(cs.getPanier().getId());
            pc.setProduitId(e.getId());
            pc.setQuantite(qte.getValue());
            pcs.ajouter(pc);
            notifPanier.setVisible(true);
            Timer timer=new Timer();
            timer.schedule(new TimerTask(){
                @Override
                public void run() {
                    notifPanier.setVisible(false);
                    timer.cancel();
                    timer.purge();
                }
            }, 3000);
            ((Button)HuntKingdom.stage.getScene().lookup("#panier")).setText("Panier ("+cs.getPanier().getNbProduits()+")");
        });
      }
   /*  public void doubleclick(MouseEvent event, Produit selectedetab) {
        if (event.getClickCount() == 2) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailsProduit.fxml"));
                Parent root = loader.load();
                ProfilProduitController DDC = loader.getController();
                DDC.Profil(selectedetab);
                
           
                Stage ss=new Stage();
                Scene sc = new Scene(root);
                ss.setScene(sc);
                ss.setWidth(1288);
                ss.setHeight(750);
                
                
                ss.show();

            } catch (IOException ex) {
                Logger.getLogger(DivListeProduitController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }*/
    
}
