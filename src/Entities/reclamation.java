/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.Date;

/**
 *
 * @author marwe
 */
public class reclamation {
   public int id ;
   public int user_id; 
   public Date Date_creation;
   public String type;
   public String description;
   public String response;
   public int status;
   public String fichier;
    private String username;
    private String strDate;

    public reclamation(int id, int user_id, Date Date_creation, String type, String description, String response, int status, String fichier) {
        this.id = id;
        this.user_id = user_id;
        this.Date_creation = Date_creation;
        this.type = type;
        this.description = description;
        this.response = response;
        this.status = status;
        this.fichier = fichier;
    }

    public reclamation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public reclamation(int id, String Title, String description, String Start_date, String End_date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

    public reclamation(int id, int user_id, String username, Date Date_creation, String description, String type, String fichier) {
        this.id = id;
        this.user_id = user_id;
        this.username=username;
        this.Date_creation = Date_creation;
        this.type = type;
        this.description = description;
        this.response = response;
        this.fichier = fichier;
    }

    public reclamation(int id, int user_id, String username, String strDate, String description, String type, String fichier) {
this.id = id;
        this.user_id = user_id;
        this.username=username;
        this.strDate = strDate;
        this.type = type;
        this.description = description;
        this.response = response;
        this.fichier = fichier;    }

    @Override
    public String toString() {
        return "reclamation{" + "id=" + id + ", user_id=" + user_id + ", Date_creation=" + Date_creation + ", type=" + type + ", description=" + description + ", response=" + response + ", status=" + status + ", fichier=" + fichier + ", username=" + username + ", strDate=" + strDate + '}';
    }
   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getDate_creation() {
        return Date_creation;
    }

    public void setDate_creation(Date Date_creation) {
        this.Date_creation = Date_creation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFichier() {
        return fichier;
    }

    public void setFichier(String fichier) {
        this.fichier = fichier;
    }


}
