package gui.commande;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfWriter;
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
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    @FXML
    private TextField clientField;
    @FXML
    private CheckBox attenteCheck;
    @FXML
    private CheckBox passeeCheck;
    @FXML
    private CheckBox annuleeCheck;
    @FXML
    private PieChart etatPieChart;
    @FXML
    private BarChart<?, ?> produitBarChart;

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
        
        //etat commande pie chart
        CommandeService cs=new CommandeService();
        ArrayList<Commande> commandes=cs.getAllCommandes();
        ObservableList<PieChart.Data> etatPieChartData = FXCollections.observableArrayList(
                new PieChart.Data("En attente ("+commandes.stream().filter(c->c.getEtat()==1).count()+")",commandes.stream().filter(c->c.getEtat()==1).count()),
                new PieChart.Data("Confirmée ("+commandes.stream().filter(c->c.getEtat()==2).count()+")",commandes.stream().filter(c->c.getEtat()==2).count()),
                new PieChart.Data("Annulée ("+commandes.stream().filter(c->c.getEtat()==3).count()+")",commandes.stream().filter(c->c.getEtat()==3).count())
        );
        etatPieChart.setData(etatPieChartData);
        
        //produit bar chart
        ProduitCommandeService pcs=new ProduitCommandeService();
        ArrayList<ProduitCommande> produits=pcs.getTopVente();
        XYChart.Series produitBarChartData=new XYChart.Series<>();
        produits.forEach(p->produitBarChartData.getData().add(new XYChart.Data(p.getNom(), p.getQuantite())));
        produitBarChart.getData().addAll(produitBarChartData);
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
    
    private void trierCommande() {
        listeProduitCommande.getItems().clear();
        listeCommandes.getItems().clear();
        CommandeService cs=new CommandeService();
        ObservableList<Commande> commandesObs = FXCollections.observableArrayList(cs.trierCommande(clientField.getText(), attenteCheck.isSelected(), passeeCheck.isSelected(), annuleeCheck.isSelected()));
        listeCommandes.setItems(commandesObs);
    }

    @FXML
    private void rechercheClient(ActionEvent event) {
        trierCommande();
    }

    @FXML
    private void triCommande(MouseEvent event) {
        trierCommande();
    }
    
    public String getFactureHTML(Commande c) {
        String factureHTML="";
        try {
            factureHTML = new String(Files.readAllBytes(Paths.get("res/commande/facture.html")));
        } catch (IOException ex) {
            Logger.getLogger(CommandeAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ProduitCommandeService pcs=new ProduitCommandeService();
        ArrayList<ProduitCommande> listeProduits=pcs.getProduitCommande(c);
        factureHTML=factureHTML.replace("NOMCLIENTJAVA", c.getUsername());
        factureHTML=factureHTML.replace("DATEJAVA", c.getDateToString());
        factureHTML=factureHTML.replace("TOTALJAVA", Double.toString(c.getTotal()));
        String listeProduitsHTML="";
        for (ProduitCommande pc : listeProduits) {
            listeProduitsHTML+="<tr><td class=\"service\"> "+pc.getNom()+" </td><td class=\"unit\"> "+pc.getPrixUnitaire()+" </td><td class=\"qty\"> "+pc.getQuantite()+" </td><td class=\"total\"> "+pc.getPrixTotal()+" </td></tr>\n";
        }
        factureHTML=factureHTML.replace("LISTEPRODUITSJAVA",listeProduitsHTML);
        return factureHTML;
    }

    @FXML
    private void facturePdf(MouseEvent event) {
        Commande c=listeCommandes.getSelectionModel().getSelectedItem();
        File facturePDF=new File("Facture"+c.getId()+".pdf");
        try {
            HtmlConverter.convertToPdf(getFactureHTML(c), new PdfWriter(facturePDF));
            Desktop.getDesktop().open(facturePDF);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
