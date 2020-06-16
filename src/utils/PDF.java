/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;
import entities.produit.Produit;
import services.produit.ProduitService;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class PDF {
    
     private Statement ste;
     private Connection con;
    
    public void GeneratePdf(String filename) throws FileNotFoundException, DocumentException, BadElementException, IOException, InterruptedException, SQLException
    {
        Document document=new  Document();
        PdfWriter.getInstance(document, new FileOutputStream(filename+".pdf"));
        document.open();
        //Image img = Image.getInstance("photo.png");
        //Image img2 = Image.getInstance("logo.png");
        ProduitService us=new ProduitService();
        con = MyDB.getInstance().getConnection();
                ste = con.createStatement();
       List<Produit> arr=new ArrayList<>();
    ste=con.createStatement();
    ResultSet rs=ste.executeQuery("SELECT * from `produit`;");
     while (rs.next()) {                
          /*     int idCommande=rs.getInt(1);
               int idUser=rs.getInt(2);
               float total=rs.getFloat(3);
               String etat=rs.getString(4);*/
          int id = rs.getInt(1);
          String nom_prod = rs.getString(4);
          int prix_prod = rs.getInt(5);
          String description_prod = rs.getString(6);
          int quantite_prod = rs.getInt(7);
          Produit p=new Produit(id, nom_prod,prix_prod, description_prod,quantite_prod);   
               
        arr.add(p);}
        for(Produit h:arr)
        {
        document.add(new Paragraph("Id :"+h.getId()));
        document.add(new Paragraph("Nom :"+ h.getNom()));
        document.add(new Paragraph("Prix:"+h.getPrix()));
        document.add(new Paragraph("Description :"+h.getDescription()));
        document.add(new Paragraph("Quantite :"+h.getQuantite()));
           //document.add(img);
         //document.add(img2);
        document.add(new Paragraph("---------------------------------------------------------------------------------------------------------------------------------- "));
        }//Notification.main(args);
        document.close();
        Process pro=Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+filename+".pdf");
    }
}
