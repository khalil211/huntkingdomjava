/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.Reclamation;

import java.sql.Connection;
import entities.Reclamation.reclamation;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import utils.MyDB;

/**
 *
 * @author marwe
 */
public class reclamationService {
    private Connection cnx;
    private Statement st;
    private PreparedStatement pre;
   
    public reclamationService(){
                cnx = MyDB.getInstance().getConnection();

    }
   public void ajoutA(reclamation app) throws SQLException{

        PreparedStatement pre = cnx.prepareStatement("INSERT INTO `reclamation` (`user_id`,`type`,`Description`,`Date_creation`, `reponse`, `status`,`fichier`) VALUES (?, ?, ?,?, ? ,? ,?);");
        pre.setInt(1,app.getUser_id());
        pre.setString(2, app.getType());
        pre.setString(3, app.getDescription());
        pre.setTimestamp(4, (Timestamp) app.getDate_creation());
        pre.setString(5, app.getResponse());
        pre.setInt(6, app.getStatus());
        pre.setString(7, app.getFichier());
        

        pre.executeUpdate();
                    System.out.println("Insertion 2 Reussie!");

}
   
    
    
    public ArrayList<reclamation> getAllreclamation(){
        ArrayList<reclamation> arr=new ArrayList<>();
        try{
            String request="SELECT c.id, c.user_id, c.Date_creation, c.type ,c.description, c.fichier, u.username FROM reclamation c JOIN fos_user u ON u.id = c.user_id WHERE status = 0";
            Statement s=cnx.createStatement();
            ResultSet rs=s.executeQuery(request);
            while(rs.next()){
                
                
                
                
                int id=rs.getInt("c.id");
                String username=rs.getString("u.username");
               int user_id=rs.getInt("c.user_id");
               Date Date_creation=rs.getDate("c.Date_creation");
               String description=rs.getString("c.description");
               String type=rs.getString("c.type");
               String fichier=rs.getString("c.fichier");
                //DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
              //  String strDate = dateFormat.format(Date_creation);
               
               reclamation p=new reclamation ( id,  user_id ,username, Date_creation, description,  type, fichier);
                arr.add(p);
                               System.out.println(p);
                               System.out.println("done");
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }
        return arr;
    }
    public void supprimereclamation(int id) {
        try {
            String req = "DELETE FROM reclamation WHERE id=?";
            pre = cnx.prepareStatement(req);
            pre.setInt(1, id);
            pre.executeUpdate();//exucute query select kahaw
            System.out.println("deleted");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());

        }
        
    }
    public void modifierEtatReclamation(int id, int status) {
        try {
            String request="UPDATE reclamation SET STATUS = ? WHERE id = ?";
            PreparedStatement pre=cnx.prepareStatement(request);
            pre.setInt(1, status);
            pre.setInt(2, id);
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    public List<reclamation> displayClause(String cl) throws SQLException {
        String requeteInsert = "Select c.id, c.user_id, c.Date_creation, c.type ,c.description, c.fichier, u.username FROM reclamation c JOIN fos_user u ON u.id = c.user_id "+cl+";";
        List<reclamation> emp=new ArrayList<>();
        pre = cnx.prepareStatement(requeteInsert);
        ResultSet rs=pre.executeQuery(requeteInsert);
        while(rs.next())
        {
            int id=rs.getInt("c.id");
                String username=rs.getString("u.username");
               int user_id=rs.getInt("c.user_id");
               Date Date_creation=rs.getDate("c.Date_creation");
               String description=rs.getString("c.description");
               String type=rs.getString("c.type");
               String fichier=rs.getString("c.fichier");
                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
                String strDate = dateFormat.format(Date_creation);
               
               reclamation p=new reclamation ( id,  user_id ,username, strDate, description,  type, fichier);
                emp.add(p);
                               System.out.println(p);
            System.out.println(emp.isEmpty());
        }
        return emp;
        
    }
}
