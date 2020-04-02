/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.produit;
/**
 *
 * @author user
 */
public class ProduitAffi {
    
     public int id;
    public String image;
    public String nom;
    public String description;
    public int quantite;
    public int prix;
    public String categorie;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public ProduitAffi(int id, String image, String nom, String description, int quantite, int prix, String categorie) {
        this.id = id;
        this.image = image;
        this.nom = nom;
        this.description = description;
        this.quantite = quantite;
        this.prix = prix;
        this.categorie = categorie;
    }

    public ProduitAffi(String image, String nom, String description, int quantite, int prix, String categorie) {
        this.image = image;
        this.nom = nom;
        this.description = description;
        this.quantite = quantite;
        this.prix = prix;
        this.categorie = categorie;
    }

    
    public String getPhoto1() {
        return "http://localhost/pi/image/"+image;
    }
    
}
