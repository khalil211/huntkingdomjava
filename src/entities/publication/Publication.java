/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.publication;

/**
 *
 * @author moez
 */
public class Publication {
    int id;
    int idUser;
    int groupid;
    String usernamep;
    String mypublication;
    
    public Publication ()
    {
        
    }

    public void setUsernamep(String usernamep) {
        this.usernamep = usernamep;
    }

    public String getUsernamep() {
        return usernamep;
    }
    
    public Publication (int idUser, String mypublication)
    {
        this.idUser = idUser;
        this.mypublication = mypublication;
        this.groupid=0;
    }
    
    public Publication (int idUser, String mypublication, int groupid)
    {
        this.idUser = idUser;
        this.mypublication = mypublication;
        this.groupid=groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public int getGroupid() {
        return groupid;
    }

    public int getId() {
        return id;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getMypublication() {
        return mypublication;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setMypublication(String mypublication) {
        this.mypublication = mypublication;
    }
    
}
