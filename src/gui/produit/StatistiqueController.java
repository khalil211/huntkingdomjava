/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.produit;

import entities.produit.Produit;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import utils.MyDB;

/**
 * FXML Controller class
 *
 * @author user
 */
public class StatistiqueController implements Initializable {

    @FXML
    private BarChart<String, Integer> barChart;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private CategoryAxis xAxis;
    
     private final ObservableList<String> data = FXCollections.observableArrayList();
        private final ObservableList<Produit> data1 = FXCollections.observableArrayList();
        private ObservableList<String> categorieNames = FXCollections.observableArrayList();
    
         private Statement ste;
        private Connection con;
    @FXML
    private Button retour;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         int n=0;
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        
        try {
            con = MyDB.getInstance().getConnection();
            ste = con.createStatement();
                        data.clear();

            ResultSet rs = ste.executeQuery("select * from categorie_produit");
                            
            while(rs.next()){
                
                
                
                        try {
            con = MyDB.getInstance().getConnection();
            ste = con.createStatement();
                        data.clear();

            ResultSet rs1 = ste.executeQuery("select cat_id from produit");
                            //System.out.println(rs);
            while(rs1.next()){
                //data.add(rs.getString(2));
                if(rs1.getInt(1)==rs.getInt(1))
                    n++;
                series.getData().add(new XYChart.Data(rs.getString(2),n));
                
                    

            }

        } catch (Exception e) {
               
        }
                        n=0;
                
                
                
                
            }

        } catch (Exception e) {
               
        }
        
        
       
        barChart.getData().addAll(series);
        // TODO
    }    

    @FXML
    private void retour(ActionEvent event) throws IOException {
        
           Parent tableViewParent = FXMLLoader.load(getClass().getResource("ListeProduit.fxml"));
        Scene tabbleViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(tabbleViewScene);
        window.show();
    }
    
}
