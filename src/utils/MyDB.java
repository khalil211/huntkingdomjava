package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyDB {
    private Connection cnx;
    private final String URL="jdbc:mysql://127.0.0.1:3306/huntkingdom";
    //private final String URL="jdbc:mysql://127.0.0.1:8889/huntkingdom";
    private final String USERNAME="root";
    private final String PASSWORD="root";
    
    private static MyDB instance=null;
    
    private MyDB() {
        try {
            cnx=DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static MyDB getInstance() {
        if (instance==null)
            instance=new MyDB();
        return instance;
    }
    
    public Connection getConnection() {
        return cnx;
    }
}
