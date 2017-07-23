/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBaseManager;

import MainClasses.Purchase;
import MainClasses.Sale;
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

public class SalesDBLink 
{
    //table details
    
    private static final String TABLE_NAME = "customersale",
        COLUMN_ORDER = "saleId",
        COLUMN_CUST_NO = "customerNo",
        COLUMN_STOCK_ID = "stockId",
        COLUMN_CUST_STOCK_ID = "custStockId",
        COLUMN_PRICE = "price",   
        COLUMN_QTY = "qty",
        COLUMN_DATE = "saleDate";
    
     //sets the connection conn
    private Connection conn;
    
    
    public SalesDBLink(Connection connection) 
    {
        conn = connection;
    }
    
    /* 
    This method gets all the customers from the database and returns them in an
    arrayList of purchase objects.
    */
    
    public List<Sale> getSales() throws SQLException, ParseException
    {
        //creats empty sale arrayList
        ArrayList<Sale> s = new ArrayList<>();
        
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
                
                //adds the sale to the list of sale
                s.add(new Sale(orderId, cutNo, stockId, potfolioId, qty, price, date));
            }
        }//end try
        catch (SQLException e) 
        {
            System.err.println(e);
        }
        //returns sale s
        return s;
    }//close getSales
    
    //This is used when the customer sells stock to add a sale record to the table
    //The row info is passed as paramaters and a sale object is returned
    
    public Sale addSale(int customerNo, int stockId, int custSockId, int qty, double price, String customer) throws SQLException, ParseException
    {
        /* 
        created a purchase object and sets it to null, the null object will be returned 
        if there is an issue creating the sale on the database
        */
        Sale sa = null;
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
                    
                    //cretes a new sale object to return
                    sa = (new Sale(order, customerNo, stockId, custSockId, qty, price, newDate));
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
        //returns sale object  sa
        return sa;
        
    }//close addSale
    
}//close SaleDBLink
