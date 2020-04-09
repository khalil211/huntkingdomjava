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
public class Produit {
    public int id;
    public String image;
    public String nom;
    public String description;
    public int quantite;
    public int prix;
    public int categorie;

    public Produit(int id,String image,String nom, String description, int quantite, int prix, int categorie) {
        this.id=id;
        this.image=image;
        this.nom = nom;
        this.description = description;
        this.quantite = quantite;
        this.prix = prix;
        this.categorie = categorie;
    }

    public Produit() {
        this.quantite=0;
        this.prix=0;
        this.nom="";
        this.description="";
        this.categorie=0;
    }

   public Produit(int id ,int categorie ,String image ,String nom, int prix, String description , int quantite) {
        this.id = id;
        this.image = image;
        this.nom = nom;
        this.description = description;
        this.quantite = quantite;
        this.prix = prix;
        this.categorie = categorie;
    }

    public Produit(String image, String nom, String description, int quantite, int prix, int categorie) {
        this.image = image;
        this.nom = nom;
        this.description = description;
        this.quantite = quantite;
        this.prix = prix;
        this.categorie = categorie;
    }

 
   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getCategorie() {
        return categorie;
    }

    public void setCategorie(int categorie) {
        this.categorie = categorie;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Produit other = (Produit) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    
}
