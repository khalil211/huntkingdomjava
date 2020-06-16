/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.produit;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import entities.produit.Produit;
import entities.produit.ProduitAffi;
import java.io.FileNotFoundException;
import services.produit.ProduitService;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import utils.MyDB;
import javafx.event.ActionEvent;
import utils.PDF;

/**
 * FXML Controller class
 *
 * @author user
 */
public class ListeProduitController implements Initializable {

    @FXML
    private TableView<ProduitAffi> ListeProduits;
    @FXML
    private TableColumn<ProduitAffi, String> resNom;
    @FXML
    private TableColumn<ProduitAffi, String> resDesc;
    @FXML
    private TableColumn<ProduitAffi, Integer> resPx;
    @FXML
    private TableColumn<ProduitAffi, Integer> resQt;
    @FXML
    private TableColumn<ProduitAffi, String> resCat;

      private final ObservableList<ProduitAffi> data = FXCollections.observableArrayList();
    ObservableList<String> list = FXCollections.observableArrayList("ok","bb");
    
    private Statement ste;
    private Connection con;
    @FXML
    private TableColumn<Produit, Image> resImg;
    ImageView img = new ImageView();
    @FXML
    private Button ajouter;
    @FXML
    private Button supprimer;
    
    ProduitService sp = new ProduitService();
    @FXML
    private Button front;
    @FXML
    private Button GCat;
    @FXML
    private TextField rechercher;
    @FXML
    private Button pdf;
    @FXML
    private Button stat;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        afficher();
       RechercheAV();
    }    
    
    public void afficher(){
          List tabProd = new ArrayList();
        List tabNom = new ArrayList();
        
                        try {
                             Connection con = MyDB.getInstance().getConnection();
                            Statement ste = con.createStatement();
                        data.clear();
            //Produit m = new Produit();
            
            ResultSet rs = ste.executeQuery("SELECT * FROM `produit`");
            
            
            //ResultSet rs1 = ste.executeQuery("select nomC from categorie where idC='"+rs.getInt(3)+"'");
            while(rs.next()){
                    Produit A = new Produit(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getString(6),rs.getInt(7));
             
                tabProd.add(A);
            }
            for (int i=0;i<tabProd.size(); i++){
                Produit A = new Produit();
                A=(Produit) tabProd.get(i);
                 ResultSet rs2 = ste.executeQuery("SELECT * FROM categorie_produit WHERE `id`= '" + A.getCategorie() + "';" );
                 while (rs2.next()){
                     tabNom.add((String) rs2.getString(2));
                 }}
             for (int j=0; j<tabNom.size(); j++){
                   Produit A = new Produit();
                A=(Produit) tabProd.get(j);
                String nom= (String) tabNom.get(j);
            data.add(new ProduitAffi(A.getId(),nom,A.getImage(),A.getNom(),A.getPrix(),A.getDescription(),A.getQuantite()));      
             }
            
          
        } catch (Exception e) {
                //Logger.getLogger(tab)
        }
                                     
            resImg.setCellValueFactory(new PropertyValueFactory<Produit, Image>("Image"));
            resCat.setCellValueFactory(new PropertyValueFactory<>("Categorie"));
            resNom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
            resPx.setCellValueFactory(new PropertyValueFactory<>("Prix"));
            resDesc.setCellValueFactory(new PropertyValueFactory<>("Description"));
            resQt.setCellValueFactory(new PropertyValueFactory<>("Quantite"));

            
            
            ListeProduits.setItems(data);
            ListeProduits.setEditable(true);
            
            resCat.setCellFactory(TextFieldTableCell.forTableColumn());
            resNom.setCellFactory(TextFieldTableCell.forTableColumn()); 
            resPx.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
             resDesc.setCellFactory(TextFieldTableCell.forTableColumn());
            resQt.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

         


   
        
        
    }

    @FXML
    private void ajouter(ActionEvent event) throws IOException {
         Parent tableViewParent = FXMLLoader.load(getClass().getResource("AjouterProduit.fxml"));
        Scene tabbleViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(tabbleViewScene);
        window.show();
     
    }

    @FXML
    private void supprimer(ActionEvent event) throws SQLException {

          ListeProduits.setItems(data);

             ObservableList<ProduitAffi> allDemandes,SingleDemandes ;
             allDemandes=ListeProduits.getItems();
             SingleDemandes=ListeProduits.getSelectionModel().getSelectedItems();
             ProduitAffi A = SingleDemandes.get(0);
             ProduitService STD = new ProduitService(); // STD = Service TAB DEMANDE
             STD.deleteProduit(A.getId());
             SingleDemandes.forEach(allDemandes::remove);
                TrayNotification tray =new TrayNotification();
            tray.setTitle("Succès");
        tray.setMessage("Suppression avec succès !");
        tray.setAnimationType(AnimationType.POPUP);
        tray.setNotificationType(NotificationType.INFORMATION);
        tray.showAndWait();
            
        
    }

    @FXML
    private void ChangerNom(TableColumn.CellEditEvent bb) throws SQLException {
        
        ProduitAffi tab_Demandeselected = ListeProduits.getSelectionModel().getSelectedItem();
     tab_Demandeselected.setNom(bb.getNewValue().toString());
     sp.updatetab(tab_Demandeselected);
    }

    @FXML
    private void ChangerDesc(TableColumn.CellEditEvent bb) throws SQLException {
       ProduitAffi tab_Demandeselected = ListeProduits.getSelectionModel().getSelectedItem();
     tab_Demandeselected.setDescription(bb.getNewValue().toString());
     sp.updatetab(tab_Demandeselected);
    }

    @FXML
    private void ChangerQt(TableColumn.CellEditEvent bb) throws SQLException {
           ProduitAffi tab_Demandeselected = ListeProduits.getSelectionModel().getSelectedItem();
      tab_Demandeselected.setQuantite((int) bb.getNewValue());
     sp.updatetab(tab_Demandeselected);
    }

    @FXML
    private void ChangerPx(TableColumn.CellEditEvent bb) throws SQLException {
           ProduitAffi tab_Demandeselected = ListeProduits.getSelectionModel().getSelectedItem();
      tab_Demandeselected.setPrix((int) bb.getNewValue());
     sp.updatetab(tab_Demandeselected);
    }

    @FXML
    private void front(ActionEvent event) throws IOException {
        
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("ListeProduits.fxml"));
        Scene tabbleViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(tabbleViewScene);
        window.show();
    }

    @FXML
    private void Gcat(ActionEvent event) throws IOException {    
        
           Parent tableViewParent = FXMLLoader.load(getClass().getResource("ListeCategorie.fxml"));
        Scene tabbleViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(tabbleViewScene);
        window.show();
    }
    
public void RechercheAV(){
                // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<ProduitAffi> filteredData = new FilteredList<>(data, b -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
		rechercher.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(event -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (event.getNom().toLowerCase().contains(lowerCaseFilter) ) {
					return true; // Filter matches first name.
				} 

                             
                        
                                 
                             
				else if (String.valueOf(event.getId()).contains(lowerCaseFilter))
				     return true;
				     else  
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<ProduitAffi> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(ListeProduits.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		ListeProduits.setItems(sortedData);
    }

    @FXML
    private void pdf(ActionEvent event) throws DocumentException, BadElementException, IOException, FileNotFoundException, InterruptedException, SQLException {
        PDF p = new PDF();
        p.GeneratePdf("Liste des produits en PDF");
    }

    @FXML
    private void stat(ActionEvent event) throws IOException {
        
        
           Parent tableViewParent = FXMLLoader.load(getClass().getResource("Statistique.fxml"));
        Scene tabbleViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(tabbleViewScene);
        window.show();
    }
    
    
}
