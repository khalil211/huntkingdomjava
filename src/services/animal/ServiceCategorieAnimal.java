/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.animal;

import entities.animal.CategorieAnimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.MyDB;

/**
 *
 * @author G I E
 */
public class ServiceCategorieAnimal {
    private Connection cnx;
    private Statement st;
    private PreparedStatement pre;

    public ServiceCategorieAnimal() {
        cnx = MyDB.getInstance().getConnection();
        
    }
    public void ajouterCategorieAnimal (CategorieAnimal a) {
         try {
            String req = "INSERT INTO categorie_animal ( `nom`, `description`) VALUES ('"+a.getNom()+"','"+a.getDescription()+"')";

            st = cnx.createStatement();
            st.executeUpdate(req);
            
            System.out.println("Insertion Reussie!");

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    
    }
    public ObservableList<CategorieAnimal> getListCategorieAnimal(){
        ObservableList<CategorieAnimal> liste = FXCollections.observableArrayList();
        String requete = "SELECT * FROM categorie_animal";
        try {
             st = cnx.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while(rs.next()) {
                CategorieAnimal a = new CategorieAnimal();
                a.setId(rs.getInt("Id"));
                a.setNom(rs.getString("Nom"));
                a.setDescription(rs.getString("Description"));
                liste.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return liste;
    }
    public void update (int id,String nom,String description){
          String requete="UPDATE categorie_animal SET nom='"+nom+"',description='"+description+"' WHERE id="+id;
         try{
            st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("Catégorie animal modifiée");
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void deleteCategorieAnimal(int id) throws SQLException {
       String requete = "DELETE FROM categorie_animal WHERE id="+id;
        try{
            st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("Catégorie animal supprimée");
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
