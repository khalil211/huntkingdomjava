/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.Appointments;

import entities.Appointments.Participant;
import entities.Appointments.appointments;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static java.time.Clock.system;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.MyDB;

/**
 *
 * @author marwe
 */
public class appointmentsService {
    private Connection cnx;
    private Statement st;
    private PreparedStatement pre;
    
    public appointmentsService(){
                        cnx = MyDB.getInstance().getConnection();
    }
    public void ajoutA(appointments app) throws SQLException{

        PreparedStatement pre = cnx.prepareStatement("INSERT INTO `appointments` (`title`,`description`, `start_date`, `end_date`) VALUES (?, ?, ?, ?);");
        pre.setString(1,app.getTitle());
        pre.setString(2, app.getDescription());
        pre.setTimestamp(3, app.getStart_date());
        pre.setTimestamp(4, app.getEnd_date());
        pre.executeUpdate();
                    System.out.println("Insertion 2 Reussie!");

}
    public List<appointments> afficheEvents() throws SQLException {
    List<appointments> arr=new ArrayList<>();
    st=cnx.createStatement();
    ResultSet rs=st.executeQuery("select * from appointments");
     while (rs.next()) {    
                int id=rs.getInt("id");

               String Title=rs.getString("title");
               String description=rs.getString("description");
               
               Timestamp Start_date=rs.getTimestamp("Start_date");
               Timestamp End_date=rs.getTimestamp("End_date");

               
               appointments p=new appointments (id,Title , description,  Start_date, End_date);
                arr.add(p);
                               System.out.println(p);

             }
     return arr;
                }
    public void supprimeEvent(int id) {
        try {
            String req = "DELETE FROM appointments WHERE id=?";
            pre = cnx.prepareStatement(req);
            pre.setInt(1, id);
            pre.executeUpdate();//exucute query select kahaw
            System.out.println("deleted");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());

        }
        
    }
    public void modifierEvent(appointments a) throws SQLException { 
         
        
            PreparedStatement pre;
            String requete="Update appointments set   Title=?,description=?,Start_date=?,End_date=? where id='" + a. getId()+ "'" ;
           
            pre=cnx.prepareStatement(requete);
            pre.setString(1,a.getTitle());
            pre.setString(2,a.getDescription());
            pre.setTimestamp(3,a.getStart_date());
            pre.setTimestamp (4,a.getEnd_date());
            System.out.println("Modifié");
            
            pre.executeUpdate();
            System.err.println("event modifiée");
       
    }
    public List<appointments> displayClause(String cl) throws SQLException {
        String requeteInsert = "Select * from appointments "+cl+";";
        List<appointments> emp=new ArrayList<>();
        pre = cnx.prepareStatement(requeteInsert);
        ResultSet rs=pre.executeQuery(requeteInsert);
        while(rs.next())
        {
            int id=rs.getInt("id");
               System.out.println(id);
               String Title=rs.getString("Title");
               String description=rs.getString("description");
               Timestamp Start_date=rs.getTimestamp("Start_date");
               Timestamp End_date=rs.getTimestamp("End_date");

           
               appointments p=new appointments ( id,  Title , description,  Start_date, End_date);
                emp.add(p);
                               System.out.println(p);
            System.out.println(emp.isEmpty());
        }
        return emp;
        
    }

    public void modifierEvent(int id, String title, String description, Timestamp start_date, Timestamp end_date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void ajoutP(Participant app) throws SQLException{

        PreparedStatement pre = cnx.prepareStatement("INSERT INTO `participant` (`user_id`,`id_event`) VALUES (?, ?);");
        pre.setInt(1,app.getUser_id());
        pre.setInt(2, app.getId_event());
       
        pre.executeUpdate();
                    System.out.println("Insertion 2 Reussie!");

}
}
