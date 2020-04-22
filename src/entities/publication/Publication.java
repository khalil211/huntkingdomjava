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
    String mypublication;
    
    public Publication ()
    {
        
    }
    
    public Publication (int idUser, String mypublication)
    {
        this.idUser = idUser;
        this.mypublication = mypublication;
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
