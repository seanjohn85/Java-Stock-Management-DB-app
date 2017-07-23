/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBaseManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author johnkenny
 */
public class DBConn 
{
    //creates an instance varaible of this class
    private static DBConn instance = null;
    
        //sets varaibles to the sql connection methods
        //These match the database
    
    private final String USERNAME = "N00145905";
    private final String PASSWORD = "N00145905";
    private final String M_CONN_STRING = "jdbc:mysql://daneel/n00145905";
    //private final String M_CONN_STRING = "jdbc:mysql://localhost/n00145905";
    private Connection conn = null;

    //default contructor
        
    private DBConn() 
    {
            
    }
        
    //creates a public instance of this class to be used by other classes
        
    public static DBConn getInstance() 
    {
        if (instance == null) 
        {
            instance = new DBConn();
        }
        
        return instance;
    }
        
        //This method is used to open a connection to the database
        
    public Connection getConnection()
    {
        //the try block 
            
        try 
        {
            conn = DriverManager.getConnection(M_CONN_STRING, USERNAME, PASSWORD);
             
        } 
        //This catches sql connection errors
        catch (SQLException ex) 
        {
            Logger.getLogger(DBConn.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (conn != null)
        {
            //System.out.println("Now connected to my Database");
            //returns a connection if connected
            return conn;
        } 
        else 
        {
            //returns null if no connection
            return null;
        }
    }

    //This close the connection to the database
    public void close() 
    {   
        //System.out.println("Closing connection");
        
        try 
        {
            //closes the connection
            conn.close();
            //sets conn to null
            conn = null;
        } 
        //if the connection cant be closed catches error and prints message
        catch (Exception e) 
        {
           System.err.println("Could not close connection");
        }
    }
    
}//close dbconn
