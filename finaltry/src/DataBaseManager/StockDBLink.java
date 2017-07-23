/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBaseManager;

import MainClasses.Stock;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author johnkenny
 * 
 * link up to stock table on the database
 * 
 */

public class StockDBLink 
{
    //These match the properties of the database for this table
    private static final String TABLE_NAME = "stock",
        COLUMN_STOCK_ID =  "stockId",
        COLUMN_STOCKNAME = "stockName", 
        COLUMN_STOCKCODE = "stockCode", 
        COLUMN_QTY = "qty",
        COLUMN_OPEN = "openPrice",
        COLUMN_CURRENT = "currentPrice";
    
    
    private Connection conn;
    
    public StockDBLink(Connection connection) 
    {
        conn = connection;
    }
    
        /* 
    This method gets all the customers from the database and returns them in an
    arrayList of stock objects.
    */
    public List<Stock> getStock() throws SQLException, ParseException
    {
        //creats empty arrayList
        ArrayList<Stock> st = new ArrayList<>();
        
        /*
        This is a database connection that uses a resultSet to select all from
        this table
        */
        try (
                Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            ) 
        {
            //sets a loop to set the next results -row(Branch)
            while (results.next()) 
            {
                //gets contents of a row
                String name = results.getString(COLUMN_STOCKNAME);
                String code = results.getString(COLUMN_STOCKCODE); 
                double open = results.getDouble(COLUMN_OPEN);
                double current = results.getDouble(COLUMN_CURRENT);
                int stockId = results.getInt(COLUMN_STOCK_ID);
                int qty = results.getInt(COLUMN_QTY);
     
                
                //adds the stock to the list of stockes
                st.add(new Stock(stockId, name, code, qty, open, current));
            }
            //System.out.println(st.toString());
            
        }//clsoe try 
        catch (SQLException e) 
        {
            System.err.println(e);
        }
        //returns the list of stocks on the database
        return st;
        
    } //close getStock
    
    //This is used to add a new stock to the stock table 
    
    public Stock addStock(String stockName, String stockCode, int qty, double openPrice) throws SQLException, ParseException
    {
        /* 
        created a stock object and sets it to null, the null object will be returned 
        if there is an issue creating the stock on the database
        */
        Stock st = null;
        //This string is the sql statment
        String type = "INSERT into " +TABLE_NAME + " ("  + COLUMN_STOCKNAME + ", " + COLUMN_STOCKCODE + ", " + 
             COLUMN_QTY + ", " +  COLUMN_OPEN + ", " + COLUMN_CURRENT + ") "  + "VALUES (?, ?, ?, ?, ?)";
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
            prestate.setString(1, stockName);
            prestate.setString(2, stockCode);
            prestate.setInt(3, qty);
            prestate.setDouble(4, openPrice);
            prestate.setDouble(5, openPrice);
            /* 
            Sets an int added to check if the stock is added.
            the int is set to the prestate update(the info above added to the
            database) and if it is added 1 will be returned as 1 row is added. 
            */
            int added = prestate.executeUpdate();
            
            /* 
            Sets an int added to check if the stock is added.
            the int is set to the prestate update(the info above added to the
            database) and if it is added 1 will be returned as 1 row is added. 
            */
            if (added == 1)
            {
                System.out.println("stock added");
                //connects to the database with a reslut set of stock no
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
                    
                    int stockId = results.getInt(COLUMN_STOCK_ID);
                    
                    conn.close();
                    
                    //stock object to be returned
                    st = (new Stock(stockId, stockName, stockCode, qty, openPrice, openPrice));
                }
                catch (SQLException e) 
                {
                    System.err.println(e);
                }
            }//close if

        }//close try
        catch(SQLException e)
        {
        System.err.println("Error: " + e);
        }
        //returns a the stock object
        return st;
        
    }//close addStock
    
    
     
}//close StockDBLink
