/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.commentaire;

import java.sql.Connection;
import java.sql.Statement;
import utils.MyDB;
import entities.commentaire.Commentaire;
import entities.user.CurrentUser;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author moez
 */
public class CommentaireService 
{
     private Connection cnx;
 private Statement st;
    
    public CommentaireService() {
        cnx=MyDB.getInstance().getConnection();
    }
    
    public void ajouterCommentaire(String text) throws SQLException
     {
         CurrentUser cu = CurrentUser.CurrentUser();
        String requete = "INSERT INTO commentaire (`text`, `datecommentaire`, `publication_id`, `user_id`) "
                + "VALUES ('"+text+"','2020-02-26 10:07:44',"+cu.targetPubId+","+cu.id+")";
        try{
             st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("commentaire ajouté");
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public ArrayList<Commentaire> getAllCommentaires()
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        ArrayList<Commentaire> commentaires=new ArrayList<>();
        try{
            String request="SELECT id, user_id, publication_id, text from commentaire where publication_id="+cu.targetPubId;
            Statement s=cnx.createStatement();
            ResultSet result=s.executeQuery(request);
            while(result.next()){
                Commentaire c = new Commentaire();
                c.setText(result.getString("text"));
                c.setUserId(result.getInt("user_id"));
                c.setPublicationId(result.getInt("publication_id"));
                c.setUsername(convertToString(result.getInt("user_id")));
                commentaires.add(c);
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }
        return commentaires;
    }
    
    public String convertToString(int userid)
    {
        String str="";
        
        try{
            String request="SELECT username from fos_user where id="+userid;
            Statement s=cnx.createStatement();
            ResultSet result=s.executeQuery(request);
            while(result.next()){
             str=result.getString("username");
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }
        return str;
    }
}
