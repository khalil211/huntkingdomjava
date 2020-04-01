/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.animal;

import entities.animal.Animal;
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
public class ServiceAnimal {
    private Connection cnx;
    private Statement st;
    private PreparedStatement pre;

    public ServiceAnimal() {
        cnx = MyDB.getInstance().getConnection();
    }

    public void ajouterAnimal(Animal a) {

        try {
            String req = "INSERT INTO animal (categorie_id, nom, description, medias, zone, saison) VALUES "
                    + "('" + a.getCategorie_id() + "', '" + a.getNom() + "', '" + a.getDescription() +"', '" + a.getMedias() +"', '" + a.getZone() +"', '" + a.getSaison() +"')";

            st = cnx.createStatement();

            st.executeUpdate(req);

            System.out.println("Insertion Reussie!");

        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }
    public ObservableList<Animal> getListeAnimaux(){  
            ObservableList<Animal> liste = FXCollections.observableArrayList();
            String requete = "SELECT * FROM animal";
            try {
                Statement st = cnx.createStatement();
                ResultSet rs = st.executeQuery(requete);
                while(rs.next()) {
                    Animal a = new Animal();
                    a.setNom(rs.getString("Nom"));
                    a.setDescription(rs.getString("Description"));
                    a.setMedias(rs.getString("Medias"));
                    a.setZone(rs.getString("Zone"));
                    a.setSaison(rs.getString("Saison"));

                    liste.add(a);
                }
            } catch (SQLException ex) {
                Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return liste;
    }
     public void updateAnimal (int id,String nom,String description,String zone, String saison){
         String requete="UPDATE produit SET nom='"+nom+"',description='"+description+"',zone='"+zone+"',saison='"+zone+"' WHERE id="+id;
         try{
             st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("Animal modifié");
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     public void deleteAnimal(int id) throws SQLException {
       String requete = "DELETE FROM animal WHERE id="+id;
        try{
             st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("Animal supprimé");
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
     }

    public void update(int id, String nom, String description) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void update(int id, String nom, String description, String medias, String zone, String saison) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
