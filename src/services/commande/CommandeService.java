package services.commande;

import entities.commande.Commande;
import entities.commande.ProduitCommande;
import entities.user.CurrentUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.stream.Collectors;
import utils.MyDB;

public class CommandeService {
    private Connection cnx;
    
    public CommandeService(){
        cnx=MyDB.getInstance().getConnection();
    }
    
    public ArrayList<Commande> getAllCommandes(){
        ArrayList<Commande> commandes=new ArrayList<>();
        try{
            String request="SELECT c.id, c.user_id, c.date, c.etat, u.username FROM commande c JOIN user u ON u.id = c.user_id WHERE etat <> 0";
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
    
    public Commande getPanier() {
        Commande c=new Commande();
        try {
            String request="SELECT c.id, u.username FROM commande c JOIN user u ON u.id = c.user_id WHERE c.user_id = ? and c.etat = 0";
            PreparedStatement pre=cnx.prepareStatement(request);
            pre.setInt(1, CurrentUser.CurrentUser().id);
            ResultSet result=pre.executeQuery();
            if (result.first()) {
                c.setEtat(0);
                c.setId(result.getInt("c.id"));
                c.setUserId(CurrentUser.CurrentUser().id);
                c.setUsername(result.getString("u.username"));
                c.setDate(null);
                ProduitCommandeService pcs=new ProduitCommandeService();
                ArrayList<ProduitCommande> pcal=pcs.getProduitCommande(c);
                c.setNbProduits(pcal.stream().mapToInt(ProduitCommande::getQuantite).sum());
                c.setTotal(pcal.stream().mapToDouble(pc->pc.getPrixUnitaire()*pc.getQuantite()).sum());
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return c;
    }
    
    public void passerCommande(Commande c) {
        try {
            String request="UPDATE commande SET etat = ?, date = STR_TO_DATE(?,'%d-%m-%Y %H:%i:%s') WHERE id = ?";
            PreparedStatement pre=cnx.prepareStatement(request);
            pre.setInt(1, c.getEtat());
            pre.setString(2,c.getDateToString());
            pre.setInt(3, c.getId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public void ajouter(int userId) {
        try {
            if (userId == -1) {
                ResultSet result=cnx.createStatement().executeQuery("SELECT LAST_INSERT_ID()");
                if (result.first())
                    userId=result.getInt(1);
            }
            String request="INSERT INTO commande(user_id,etat) VALUES(?,0)";
            PreparedStatement pre=cnx.prepareStatement(request);
            pre.setInt(1, userId);
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public ArrayList<Commande> getCommandeClient() {
        ArrayList<Commande> liste=new ArrayList<>();
        liste.addAll(getAllCommandes().stream().filter(c->c.getEtat()!=0 && c.getUserId()==CurrentUser.CurrentUser().id).collect(Collectors.toList()));
        return liste;
    }
    
    public ArrayList<Commande> trierCommande(String client, boolean attente, boolean passee, boolean annulee) {
        ArrayList<Commande> liste=new ArrayList<>();
        String request="SELECT c.id, c.user_id, c.date, c.etat, u.username FROM commande c JOIN user u ON u.id = c.user_id WHERE";
        if (attente || passee || annulee) {
            request+=" ( ";
            if (attente)
                request+="etat = 1";
            if (passee && attente)
                request+=" OR etat = 2";
            else if (passee)
                request+="etat = 2";
            if (annulee && (attente || passee))
                request+=" OR etat = 3";
            else if (annulee)
                request+="etat = 3";
            request+=" ) ";
        } else
            request+="etat <> 0";
        if (!client.isEmpty())
            request+=" AND u.username LIKE ?";
        try {
            PreparedStatement pre=cnx.prepareStatement(request);
            if (!client.isEmpty())
                pre.setString(1, "%"+client+"%");
            ResultSet result=pre.executeQuery();
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
                liste.add(c);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return liste;
    }
}
