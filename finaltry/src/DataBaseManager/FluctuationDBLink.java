/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBaseManager;

import MainClasses.Fluctuation;
import MainClasses.Stock;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author johnkenny
 * 
 * link up to fluctuation table on the database
 * 
 */

public class FluctuationDBLink 
{
    //These match the properties of the database for this table
    private static final String TABLE_NAME = "fucuation",
        COLUMN_RECORD_NO =  "recordNo",
        COLUMN_STOCK_ID =  "stockId",
        COLUMN_AMOUNT = "amount", 
        COLUMN_PERCENT = "percent", 
        COLUMN_PRICE = "price",
        COLUMN_DATE = "date";

    
    
    private Connection conn;
    
    public FluctuationDBLink(Connection connection) 
    {
        conn = connection;
    }
    

    //This is used to add a generate a fluctuation for each stock in the database
    
    public Fluctuation fluctuationGen(int stockid, double amount, int percent, double price) throws SQLException, ParseException
    {
        /* 
        created a fluctuation object and sets it to null, the null object will be returned 
        if there is an issue creating the fluctuation on the database
        */
        Fluctuation fl = null;
        //This string is the sql statment
        String type = "INSERT into " +TABLE_NAME + " ("  + COLUMN_STOCK_ID + ", " + COLUMN_AMOUNT + ", " + 
             COLUMN_PERCENT  + ", " + COLUMN_PRICE + ") "  + "VALUES (?, ?, ?, ?)";
        //This is the connection to the database
        try
        (
            PreparedStatement prestate = conn.prepareStatement(type, Statement.RETURN_GENERATED_KEYS);
        )
        {
            /*
            Uses the above preparedStament and sets the columns to match the above sql statement. Uses the
            paramaters passed in to set columns
            */
            prestate.setInt(1, stockid);
            prestate.setDouble(2, amount);
            prestate.setInt(3, percent);
            prestate.setDouble(4, price);
     
            /* 
            Sets an int added to check if the fluctuation is added.
            the int is set to the prestate update(the info above added to the
            database) and if it is added 1 will be returned as 1 row is added. 
            */
            int added = prestate.executeUpdate();

            if (added == 1)
            {
                //System.out.println("fluctuation added");
                //connects to the database with a reslut set of fluctuation no
                try 
                (
                    Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                    ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
                ) 
                {
                    //goes to the last row in the database the one just created
                    results.last();

                    //gets the auto generated key for the just created stock
                    
                    int record = results.getInt(COLUMN_STOCK_ID);
                    String dateString = results.getString(COLUMN_DATE);
                    Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.m").parse(dateString);
                   
                    
                    //fluctuation object to be returned
                    fl = (new Fluctuation(record, stockid,  amount,  percent,  price, date));
                    
                    boolean updated = stockPrice(stockid, price);
                    conn.close();
                }
                catch (SQLException e) 
                {
                    System.err.println(e);
                }
            }//close if

        }//close try//close try
        catch(SQLException e)
        {
        System.err.println("Error: " + e);
        }
        //returns a the stock object
        return fl;
        
    }//close addStock
    
    //updates the currentprice in stock based on the new fluctuation generated price
    
    public boolean stockPrice(int stockId, double newPrice) throws SQLException
    {
        //sets returnt to false, this will change if the update is sucessiful
        boolean updated = false;
        
        //This is the sql to update a stock row
        String sql
                = "UPDATE  " + "stock" + " SET "
               + "currentPrice" + " = ? "
                + "WHERE " + "stockId" + " = ?";
        
        try (
                
                //creates a prepared statement called state and passes in the sql string
                PreparedStatement state = conn.prepareStatement(sql);
            ) 
        {
            /*
            sets the current price in the stock table*/
            state.setDouble(1, newPrice);
            
            /*
            uses the stock id to reference the row to update
            this is set in the where part of the sql
            */
            state.setInt(2, stockId);
            
            //preforms the update
            int sucess = state.executeUpdate();
            
            //if the update was sucessiful
            if(sucess == 1)
            {
                //System.out.println("Database updated");
                //changes returntype to true
                //closes connection
                conn.close();
                updated = true; 
            }
        }
        //handels sql errors
        catch(SQLException e)
        {
            System.err.println("Database update fail \n" +e);
        }

        return updated;
        
    }//close stock price
       
}//close StockDBLink
