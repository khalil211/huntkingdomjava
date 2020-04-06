package gui.commande;

import entities.commande.Commande;
import entities.commande.ProduitCommande;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import huntkingdom.HuntKingdom;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.commande.CommandeService;
import services.commande.ProduitCommandeService;

public class CommandeAdminController implements Initializable {

    //table commande
    @FXML private TableView<Commande> listeCommandes;
    @FXML private TableColumn<Commande, String> clientCol;
    @FXML private TableColumn<Commande, Integer> nbProduitsCol;
    @FXML private TableColumn<Commande, Double> totalCol;
    @FXML private TableColumn<Commande, String> dateCol;
    @FXML private TableColumn<Commande, String> etatCol;

    //table produit commande
    @FXML private TableView<ProduitCommande> listeProduitCommande;
    @FXML private TableColumn<ProduitCommande, String> nomProdCol;
    @FXML private TableColumn<ProduitCommande, Double> prixProdCol;
    @FXML private TableColumn<ProduitCommande, Integer> quantiteProdCol;
    @FXML private TableColumn<ProduitCommande, Double> totalProdCol;

    //boutons commande
    @FXML private Button confirmerB;
    @FXML private Button annulerB;
    @FXML private Button factureB;
    @FXML private Button supprimerB;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //table commande
        clientCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        nbProduitsCol.setCellValueFactory(new PropertyValueFactory<>("nbProduits"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("dateToString"));
        etatCol.setCellValueFactory(new PropertyValueFactory<>("etatToString"));

        //table produit commande
        nomProdCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prixProdCol.setCellValueFactory(new PropertyValueFactory<>("prixUnitaire"));
        quantiteProdCol.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        totalProdCol.setCellValueFactory(new PropertyValueFactory<>("prixTotal"));

        afficherCommande();
    }

    @FXML
    private void toMenuAdmin(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/MenuAdmin.fxml"));
        Scene scene = new Scene(root, HuntKingdom.stage.getScene().getWidth(), HuntKingdom.stage.getScene().getHeight());
        HuntKingdom.stage.setScene(scene);
    }

    @FXML
    private void selectionCommande(MouseEvent event) {
        Commande c = listeCommandes.getSelectionModel().getSelectedItem();
        if (c != null) {
            changerEtatBoutons(c.getEtat());
            afficherProduitCommande(c);
        }
    }

    private void changerEtatBoutons(int etatC) {
        switch (etatC) {
            case 1:
                //en attente
                confirmerB.setDisable(false);
                annulerB.setDisable(false);
                factureB.setDisable(true);
                supprimerB.setDisable(false);
                break;
            case 2:
                //confirmee
                confirmerB.setDisable(true);
                annulerB.setDisable(true);
                factureB.setDisable(false);
                supprimerB.setDisable(false);
                break;
            case 3:
                //annulee
                confirmerB.setDisable(true);
                annulerB.setDisable(true);
                factureB.setDisable(true);
                supprimerB.setDisable(false);
                break;
            default:
                confirmerB.setDisable(true);
                annulerB.setDisable(true);
                factureB.setDisable(true);
                supprimerB.setDisable(true);
                break;
        }
    }

    private void afficherCommande() {
        CommandeService cs = new CommandeService();
        ObservableList<Commande> commandesObs = FXCollections.observableArrayList(cs.getAllCommandes());
        listeCommandes.setItems(commandesObs);
    }

    private void afficherProduitCommande(Commande c) {
        ProduitCommandeService pcs = new ProduitCommandeService();
        ObservableList<ProduitCommande> produitCommandeObs = FXCollections.observableArrayList(pcs.getProduitCommande(c));
        listeProduitCommande.setItems(produitCommandeObs);
    }

    @FXML
    private void confirmerCommande(MouseEvent event) {
        ArrayList<String> produitNonDisponible=new ArrayList<>();
        Commande c=listeCommandes.getSelectionModel().getSelectedItem();
        CommandeService cs=new CommandeService();
        ProduitCommandeService pcs=new ProduitCommandeService();
        pcs.getProduitCommande(c).forEach(pc -> {
            if (!pcs.produitDisponible(pc))
                produitNonDisponible.add(pc.getNom());
        });
        if (!produitNonDisponible.isEmpty()) {
            Alert alert=new Alert(AlertType.WARNING);
            alert.setTitle("Confirmation impossible");
            alert.setHeaderText("Vérifiez la quantitée des produits suivants:");
            alert.setContentText(produitNonDisponible.stream().reduce("", (noms,nom)->noms+nom+"\n"));
            alert.showAndWait();
        } else {
            pcs.getProduitCommande(c).forEach(pc -> pcs.diminuerQuantiteProduit(pc));
            cs.modifierEtatCommande(c, 2);
            c.setEtat(2);
            changerEtatBoutons(2);
            listeCommandes.refresh();
        }
    }

    @FXML
    private void annulerCommande(MouseEvent event) {
        CommandeService cs=new CommandeService();
        Commande c=listeCommandes.getSelectionModel().getSelectedItem();
        cs.modifierEtatCommande(c, 3);
        c.setEtat(3);
        changerEtatBoutons(3);
        listeCommandes.refresh();
    }

    @FXML
    private void supprimerCommande(MouseEvent event) {
        CommandeService cs=new CommandeService();
        ProduitCommandeService pcs=new ProduitCommandeService();
        Commande c=listeCommandes.getSelectionModel().getSelectedItem();
        pcs.supprimerProduits(c);
        cs.supprimerCommande(c);
        listeCommandes.getItems().remove(c);
        c=listeCommandes.getSelectionModel().getSelectedItem();
        if (c!=null) {
            afficherProduitCommande(c);
            changerEtatBoutons(c.getEtat());
        } else {
            listeProduitCommande.getItems().removeAll(listeProduitCommande.getItems());
            changerEtatBoutons(-1);
        }
    }
}
