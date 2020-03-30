package services.commande;

import entities.commande.Commande;
import entities.commande.ProduitCommande;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import utils.MyDB;

public class CommandeService {
    private Connection cnx;
    
    public CommandeService(){
        cnx=MyDB.getInstance().getConnection();
    }
    
    public ArrayList<Commande> getAllCommandes(){
        ArrayList<Commande> commandes=new ArrayList<>();
        try{
            String request="SELECT c.id, c.user_id, c.date, c.etat, u.username FROM commande c JOIN fos_user u ON u.id = c.user_id WHERE etat <> 0";
            Statement s=cnx.createStatement();
            ResultSet result=s.executeQuery(request);
            while(result.next()){
                Commande c=new Commande();
                c.setId(result.getInt("c.id"));
                c.setUserId(result.getInt("c.user_id"));
                c.setDate(result.getTimestamp(3).toLocalDateTime());
                c.setEtat(result.getInt("c.etat"));
                c.setUsername(result.getString("u.username"));
                ProduitCommandeService pcs=new ProduitCommandeService();
                ArrayList<ProduitCommande> pcal=pcs.getProduitCommande(c);
                c.setNbProduits(pcal.stream().mapToInt(ProduitCommande::getQuantite).sum());
                c.setTotal(pcal.stream().mapToDouble(pc->pc.getPrixUnitaire()*pc.getQuantite()).sum());
                commandes.add(c);
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }
        return commandes;
    }
    
    public void modifierEtatCommande(Commande c, int etat) {
        try {
            String request="UPDATE commande SET etat = ? WHERE id = ?";
            PreparedStatement pre=cnx.prepareStatement(request);
            pre.setInt(1, etat);
            pre.setInt(2, c.getId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public void supprimerCommande(Commande c) {
        try {
            String request="DELETE FROM commande WHERE id = ?";
            PreparedStatement pre=cnx.prepareStatement(request);
            pre.setInt(1, c.getId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}
