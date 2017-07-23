/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBaseManager;

import MainClasses.Purchase;
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
 * This matches the purchaseDBLink table which acts as a record of stocks purchases 
 * to produce invoices
 * 
 * 
 */

public class PurchaseDBLink 
{
    //table details
    
    private static final String TABLE_NAME = "customerpurchase",
        COLUMN_ORDER = "orderId",
        COLUMN_CUST_NO = "customerNo",
        COLUMN_STOCK_ID = "stockId",
        COLUMN_CUST_STOCK_ID = "custStockId",
        COLUMN_PRICE = "price",   
        COLUMN_QTY = "qty",
        COLUMN_DATE = "orderDate";
    
    //sets the connection conn
    private Connection conn;
    
    public PurchaseDBLink(Connection connection) 
    {
        conn = connection;
    }
    
    /* 
    This method gets all the customers from the database and returns them in an
    arrayList of purchase objects.
    */
    public List<Purchase> getPurchase() throws SQLException, ParseException
    {
        //creats empty purchase arrayList
        ArrayList<Purchase> p = new ArrayList<>();
        
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
            //This was used to test comparing dates Date date1 = new Date();

            //sets a loop to set the next results -row
            while (results.next()) 
            {
                //sets the info from this row to varaibles
                
                int orderId = results.getInt(COLUMN_ORDER);
                int stockId = results.getInt(COLUMN_STOCK_ID);
                int potfolioId = results.getInt(COLUMN_CUST_STOCK_ID);
                int cutNo = results.getInt(COLUMN_CUST_NO);
                double price = results.getDouble(COLUMN_PRICE);
                int qty = results.getInt(COLUMN_QTY);
               
                String orderDate = results.getString(COLUMN_DATE);
                
                //formats date
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.m").parse(orderDate);
                //System.out.println(date);
               
                //adds the purchase to the list of purchases
                p.add(new Purchase(orderId, cutNo, stockId, potfolioId, qty, price, date));
            }
        }//end try //end try 
        catch (SQLException e) 
        {
            System.err.println(e);
        }
        //System.out.println("pur");
        return p;
    }//close getPurchase
    
    //This is used when the customer purchases stock to add a purchase record to the table
    
    public Purchase addPur(int customerNo, int stockId, int custSockId, int qty, double price, String customer) throws SQLException, ParseException
    {
        /* 
        created a purchase object and sets it to null, the null object will be returned 
        if there is an issue creating the purchase on the database
        */
        Purchase pur = null;
        //This string is the sql statment
        String type = "INSERT into " + TABLE_NAME + " (" + COLUMN_CUST_NO + ", " +
                COLUMN_STOCK_ID + ", " + COLUMN_CUST_STOCK_ID + ", " +
                COLUMN_PRICE  + ", " + COLUMN_QTY +") " + 
                "VALUES (?, ?, ?, ?, ?)";
        
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
            prestate.setInt(1, customerNo);
            prestate.setInt(2, stockId);
            prestate.setInt(3, custSockId);
            prestate.setDouble(4, price);
            prestate.setInt(5, qty);
            
            /* 
            Sets an int added to check if the purchase is added.
            the int is set to the prestate update(the info above added to the
            database) and if it is added 1 will be returned as 1 row is added. 
            */
           
            int added = prestate.executeUpdate();
            
            //executes if customer added
            if (added == 1)
            {
                System.out.println("Stock sold to " + customer);
                try 
                (
                    Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                    ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
                ) 
                {
                    //goes to the last row in the database the one just created
                    results.last();

                    //pulls auto generated info from the database
                    int order = results.getInt(COLUMN_ORDER);
                    String date = results.getString(COLUMN_DATE);
                    Date newDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.m").parse(date);
                    conn.close();
                    
                    //sets the purchase to the details input to the database
                    pur = (new Purchase(order, customerNo, stockId, custSockId, qty, price, newDate));
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
        //returns the purchase or null if an issue arised adding it to the databse
        return pur;
        
    }//close addCustomer
    
}//close PurchaseDBLink
