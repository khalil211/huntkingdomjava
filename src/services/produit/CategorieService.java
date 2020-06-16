/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.produit;

import entities.produit.Categorie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.MyDB;

/**
 *
 * @author user
 */
public class CategorieService {
      private Connection cnx;
    private Statement st;
    private PreparedStatement pre;

    public CategorieService() {
        cnx = MyDB.getInstance().getConnection();
        
    }
    
    public void ajouterCat (Categorie p) {
         try {
            String req = "INSERT INTO categorie_produit ( `nom_cat`, `description_cat`) VALUES ('"+p.getNom()+"','"+p.getDescription()+"')";


            st = cnx.createStatement();
            st.executeUpdate(req);

            System.out.println("Insertion Reussie!");

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    
}

public ObservableList<Categorie> getListCategorie(){
       ObservableList<Categorie> liste = FXCollections.observableArrayList();
        String requete = "SELECT * FROM categorie_produit";
        try {
             st = cnx.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while(rs.next()) {
                Categorie p = new Categorie();
                p.setId(rs.getInt("id"));
                p.setNom(rs.getString("nom_cat"));
                p.setDescription(rs.getString("description_cat"));
         
                liste.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return liste;
  
     
     
    }
public void deleteCat(int id) throws SQLException {
       String requete = "DELETE FROM categorie_produit WHERE id="+id;
        try{
             st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("Catégorie supprimée");
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
     }

public void updateCat (Categorie a)
     {
           try {
        PreparedStatement PS=cnx.prepareStatement("UPDATE `categorie_produit` SET `nom_cat`=?,`description_cat`=? WHERE `id`=?");
        PS.setString(1,a.getNom());        
        PS.setString(2,a.getDescription()); 

        PS.setInt(3,a.getId());                   

 
        PS.executeUpdate();
            } catch (Exception e) {
                Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE,null,e);
            }

     }

public List<Categorie> afficherAll() {

        List<Categorie> listP = new ArrayList<>();

        try {

            String req = "SELECT * FROM categorie_produit";

            st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);

            while (res.next()) {
                Categorie p = new Categorie();

                p.setId(res.getInt("id"));
                p.setNom(res.getString("nom"));
                p.setDescription(res.getString("description"));

                listP.add(p);
            }
            
            System.out.println(listP);

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return listP;
    }

}
