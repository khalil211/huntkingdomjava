package services.commande;

import entities.commande.Commande;
import entities.commande.ProduitCommande;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import utils.MyDB;

public class ProduitCommandeService {
    private Connection cnx;
    
    public ProduitCommandeService() {
        cnx=MyDB.getInstance().getConnection();
    }
    
    public ArrayList<ProduitCommande> getProduitCommande(Commande c){
        ArrayList<ProduitCommande> pcs=new ArrayList<>();
        try {
            String request="SELECT pc.id, pc.produit_id, pc.quantite, p.prix_prod, p.nom_prod FROM produit_commande pc JOIN produit p ON p.id = pc.produit_id WHERE pc.commande_id = ?";
            PreparedStatement pre=cnx.prepareStatement(request);
            pre.setInt(1, c.getId());
            ResultSet result=pre.executeQuery();
            while(result.next()) {
                ProduitCommande pc=new ProduitCommande();
                pc.setId(result.getInt("pc.id"));
                pc.setCommandeId(c.getId());
                pc.setProduitId(result.getInt("pc.produit_id"));
                pc.setQuantite(result.getInt("pc.quantite"));
                pc.setPrixUnitaire(result.getDouble("p.prix_prod"));
                pc.setNom(result.getString("p.nom_prod"));
                pcs.add(pc);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return pcs;
    }
    
    public void supprimerProduits(Commande c) {
        try {
            String request="DELETE FROM produit_commande WHERE commande_id = ?";
            PreparedStatement pre=cnx.prepareStatement(request);
            pre.setInt(1, c.getId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public boolean produitDisponible(ProduitCommande pc) {
        try {
            String request="SELECT pc.quantite, p.quantite_prod FROM produit_commande pc JOIN produit p ON p.id = pc.produit_id WHERE pc.id = ?";
            PreparedStatement pre=cnx.prepareStatement(request);
            pre.setInt(1, pc.getId());
            ResultSet result=pre.executeQuery();
            if (result.first())
                return result.getInt("pc.quantite")<=result.getInt("p.quantite_prod");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return false;
    }
    
    public void diminuerQuantiteProduit(ProduitCommande pc) {
        try {
            String request="UPDATE produit SET quantite_prod = quantite_prod - ? WHERE id = ?";
            PreparedStatement pre=cnx.prepareStatement(request);
            pre.setInt(1, pc.getQuantite());
            pre.setInt(2, pc.getProduitId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}
