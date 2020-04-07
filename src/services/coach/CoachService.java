/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.coach;
import entities.coach.Coach;
import java.sql.PreparedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.MyDB;

/**
 *
 * @author Louay
 */
public class CoachService {
      private Connection cnx;
   
    
    public CoachService(){
        cnx=MyDB.getInstance().getConnection();
    }
     public ArrayList<Coach> getAllCoachs(){
        ArrayList<Coach> coachs=new ArrayList<Coach>();
        try{
            String request="SELECT c.id, c.user_id, c.experience, c.etat , c.code, c.hire_date, u.username, u.email FROM coach c join fos_user u ON u.id = c.user_id";
            Statement s=cnx.createStatement();
            ResultSet result=s.executeQuery(request);
            while(result.next()){
                Coach c=new Coach();
                c.setId(result.getInt("c.id"));
                c.setUserId(result.getInt("c.user_id"));
                c.setHireDate(result.getTimestamp(6).toLocalDateTime());
                c.setEtat(result.getString("c.etat"));
                c.setNom(result.getString("u.username"));
                c.setEmail(result.getString("u.email"));
                c.setCode(result.getString("c.code"));
               
                c.setExperienceYears(result.getInt("c.experience"));
                
                coachs.add(c);
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }
        return coachs;
    }
}
