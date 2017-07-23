/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methods;

import DataBaseManager.DBConn;
import DataBaseManager.StockDBLink;
import MainClasses.Model;
import MainClasses.Stock;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

/**
 *
 * @author johnkenny
 */
public class StockMethods 
{
    //this method pulls the stock from the model list
    
    public static void viewStock(Model brModel) 
    {
        /*
        creates a list of stock from the model passed in using the model class
        */
        List<Stock> stocks = brModel.getStock();
        /*
        loops through each stock using the stock toString method to print the all the branches
        */
        for (Stock st : stocks) 
        {
            System.out.println(st.toString() + "\n");
        }
        System.out.println("--------------------------------------");
        
    }//close viewStock
    
    //allows a user to create a new stock
    
    public static Stock createStock() throws ParseException
    {
        //sets newStock to null
        Stock newSt = null;
        
        /*Handles userinput creating new strings which will be later used as 
        parameteres to create a stock object
        */
        String stockName = Input.inputOption("Enter stock Name ");
        String stockCode = Input.inputOption("Enter stock code (max 5 char)");
        int qty = Input.convertToNumberInt("Enter the amount of shares for this stock");
        double price = Input.convertToNumberDoub("Enter the price of a share");
     
        try
        {   
            //Creates a connection using the getConnection method from the DBConn class
            Connection conn = DBConn.getInstance().getConnection();
            //uses the connection in the StockDBLink class
            StockDBLink sdl = new StockDBLink(conn);
            /*
            uses the method .addStock method from the stockDBLink class to add a new
            stock to the database pasing in the above varaibles as paramaters and if 
            this is created it will return the stock and set it to the newST object
            */
            newSt = sdl.addStock(stockName, stockCode, qty,  price);
            
        } 
        //handles sql errors
        catch (SQLException ex) 
        {
            System.err.println("An error has acured and this may not be added to the database");
        }
        //closes connection to the database
        DBConn.getInstance().close();
        //return the new stock just set> this will be null if it was not added to the database
     return newSt;  
     
    }//close createStock
    
}//close createStock()
