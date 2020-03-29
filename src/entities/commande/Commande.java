package entities.commande;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Commande {
    private int id;
    private int userId;
    private LocalDateTime date;
    private int etat;
    
    private double total;
    private int nbProduits;
    private String username;
    
    public Commande(){
        
    }

    public Commande(int userId, LocalDateTime date, int etat) {
        this.userId = userId;
        this.date = date;
        this.etat = etat;
    }

    public Commande(int id, int userId, LocalDateTime date, int etat) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.etat = etat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getNbProduits() {
        return nbProduits;
    }

    public void setNbProduits(int nbProduits) {
        this.nbProduits = nbProduits;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEtatToString(){
        if (etat==1)
            return "En attente";
        if (etat==2)
            return "Confirmée";
        return "Annulée";
    }
    
    public String getDateToString(){
        return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }
    
    @Override
    public String toString() {
        return "Commande{" + "id=" + id + ", userId=" + userId + ", date=" + date + ", etat=" + etat + '}';
    }
}
