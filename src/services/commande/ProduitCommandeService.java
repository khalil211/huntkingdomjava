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
        ArrayList<ProduitCommande> pcs=new ArrayList<ProduitCommande>();
        try {
            String request="SELECT pc.id, pc.produit_id, pc.quantite, p.prix_prod FROM produit_commande pc JOIN produit p ON p.id = pc.produit_id WHERE pc.commande_id = ?";
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
                pcs.add(pc);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return pcs;
    }
}
