    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.produit;

import entities.produit.Produit;
import entities.produit.ProduitAffi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.MyDB;

/**
 *
 * @author user
 */
public class ProduitService {
    private Connection cnx;
    private Statement st;
    private PreparedStatement pre;

    public ProduitService() {
        cnx = MyDB.getInstance().getConnection();
        
    }
    
     public void ajouterProduit(Produit p) throws SQLException{
       try {
            String req = "INSERT INTO produit ( `cat_id`, `image_prod`, `nom_prod`, `prix_prod`, `description_prod`, `quantite_prod`) VALUES ('"+p.getCategorie()+"','"+p.getImage()+"','"+p.getNom()+"','"+p.getPrix()+"','"+p.getDescription()+"','"+p.getQuantite()+"')";


            st = cnx.createStatement();
            st.executeUpdate(req);

            System.out.println("Insertion Reussie!");

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    
}
  
     public ObservableList<Produit> getListeProduits(){
        ObservableList<Produit> liste = FXCollections.observableArrayList();
        String requete = "SELECT * FROM produit";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while(rs.next()) {
                Produit p = new Produit();
                p.setImage(rs.getString("Image"));
                p.setCategorie(rs.getInt("Categorie"));
                p.setNom(rs.getString("Nom"));
                p.setPrix(rs.getInt("Prix"));
                p.setDescription(rs.getString("Description")); 
                p.setQuantite(rs.getInt("Quantite"));
                
               
                
                liste.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return liste;
    }
     
     
     public void deleteProduit(int id) throws SQLException {
       String requete = "DELETE FROM produit WHERE id="+id;
        try{
             st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("Produit supprimé");
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     
      public ObservableList<Produit> rechercheProduitByNom(String nom){
               ObservableList<Produit> liste = FXCollections.observableArrayList();
        String requete = "SELECT * FROM produit WHERE nom_prod="+(char)34+nom+(char)34;
        try {
             st = cnx.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while(rs.next()) {
                Produit p = new Produit();
                p.setNom(rs.getString("Nom"));
                p.setDescription(rs.getString("Description"));
                p.setQuantite(rs.getInt("Quantite"));
                p.setPrix(rs.getInt("Prix"));
                p.setCategorie(rs.getInt("Categorie"));
                liste.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return liste;
    } 
      
      
      public void updateProduit (Produit a)
     {
             try {
        PreparedStatement PS=cnx.prepareStatement("UPDATE `produit` SET `nom_prod`=? ,`description_prod`=?,`quantite_prod`=?,`prix_prod`=? WHERE `id`=?");
        PS.setString(1,a.getNom());
        PS.setString(2, a.getDescription());
        PS.setInt(3,a.getQuantite());                   
        PS.setInt(4,a.getPrix());                   
        PS.setInt(5,a.getId());                   
        PS.executeUpdate();
            } catch (Exception e) {
                Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE,null,e);
            }
     } 
      
      public void updatetab(ProduitAffi a) throws SQLException {
            try {
        PreparedStatement PS=cnx.prepareStatement("UPDATE `produit` SET `nom_prod`=? ,`description_prod`=?,`quantite_prod`=?,`prix_prod`=? WHERE `id`=?");
        PS.setString(1,a.getNom());
        PS.setString(1,a.getNom());
        PS.setString(2, a.getDescription());
        PS.setInt(3,a.getQuantite());                   
        PS.setInt(4,a.getPrix());                   
        PS.setInt(5,a.getId());                   
        PS.executeUpdate();
     
            } catch (Exception e) {
                Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE,null,e);
            }

    }
      public  ArrayList<Produit> getAllProduit() throws SQLException {
       ArrayList<Produit> retour = new ArrayList<>();
       Statement stm = cnx.createStatement();
        String req = "SELECT * FROM produit";
        ResultSet resultat = stm.executeQuery(req);
        while(resultat.next()){
           int id= resultat.getInt(1);
           int cat= resultat.getInt("cat_id");
            String img = resultat.getString("image_prod");
           String nom= resultat.getString("nom_prod");
            int prx =resultat.getInt("prix_prod");
            String desc= resultat.getString("description_prod");
            int qt=resultat.getInt("quantite_prod");
           retour.add(new Produit(id, img,nom,desc,qt, prx,cat));
            
        }
            
        return retour;
    }
}
