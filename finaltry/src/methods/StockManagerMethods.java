/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methods;

import DataBaseManager.CustStockDBLink;
import DataBaseManager.DBConn;
import DataBaseManager.PurchaseDBLink;
import DataBaseManager.SalesDBLink;
import MainClasses.Branch;
import MainClasses.CustStock;
import MainClasses.Purchase;
import MainClasses.Sale;
import MainClasses.Model;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import static methods.MenuOptions.brModel;

/**
 *
 * @author johnkenny
 */
public class StockManagerMethods 
{
    //updates a the custStock table on the database
    //for when the custoemr adds more of the same stock
    
    public static int updateStock(int id, int qty, int custNo) throws ParseException
    {
        
        Connection conn = DBConn.getInstance().getConnection();
        //create an instance of the CustStockDBLink class passing in a connection
        CustStockDBLink cdl = new CustStockDBLink(conn);
        /*
        sets update to the value of the method update cust from the CustStockDBLink class
        This will try to update  to the database and if sucessiful it will return true
        */
   
        CustStock c = cdl.updateQty(qty, id, custNo);
        int updateid = c.getCustStockId();
        List<CustStock> cs = brModel.getCustStock();
        for(CustStock cu : cs)
        {
            if(cu.getStockId() == id && cu.getCustomerNo() == custNo)
            {
                return cu.getCustStockId();
            }
        }
        //returns update id
        //System.out.print(updateid + "returned id");
        return updateid;
        
    }//closeupdateStock
    
    //adds stock to customer.  adds a new item to the custStock table
    //this is used when a customer buys more stock
    
    public static CustStock addStock(int id, int qty, int custNo) throws ParseException
    {
        CustStock cs = null;
        try
            {   
                Connection conn = DBConn.getInstance().getConnection();
                //create an instance of the CustStockDBLink class passing in a connection
                CustStockDBLink cdl = new CustStockDBLink(conn);
                /*
                   sets update to the value of the method update cs from the CustStockDBLink class
                   This will try to update  to the database and if sucessiful it will return true
                        */
                cs = cdl.addcustStock(custNo, id, qty);
            //returns the update
            return cs;
   
            } 
            //this handels the sql errors
            catch (SQLException ex) 
            {
                System.err.println("An error has acured and this may not be added to the database");
            }
        //returns null
        return cs;
        
    }//close addStock

    //adds a new purchase to the database

    public static Purchase pur(int custNo, int stockId, int custSockId, int qty, double price, String customer) throws ParseException
    {
        Purchase newP = null;
        try
        {   
            //Creates a connection using the getConnection method from the DBConn class
            Connection conn = DBConn.getInstance().getConnection();
            //uses the connection in the PurchaseDBLink class
            PurchaseDBLink pdl = new PurchaseDBLink(conn);
            /*
            uses the method .addpur method from the PurchaseDBLink class to add a new
            purchase to the database pasing in the above varaibles as paramaters and if 
            this is created it will return the purchase and set it to the newp object
            */
            return newP = pdl.addPur(custNo, stockId, custSockId, qty, price, customer);
            
        } 
        //handles sql errors
        catch (SQLException ex) 
        {
            System.err.println("An error has acured and this may not be added to the database");
        }
        //closes connection to the database
        DBConn.getInstance().close();
        return newP;
        
    }//close Purchase

    
    //adds a new sale to the database

    public static Sale sale(int custNo, int stockId, int custSockId, int qty, double price, String customer) throws ParseException
    {
        Sale newS = null;
        try
        {   
            //Creates a connection using the getConnection method from the DBConn class
            Connection conn = DBConn.getInstance().getConnection();
            //uses the connection in the PurchaseDBLink class
            SalesDBLink sdl = new SalesDBLink(conn);
            /*
            uses the method .addpur method from the PurchaseDBLink class to add a new
            purchase to the database pasing in the above varaibles as paramaters and if 
            this is created it will return the purchase and set it to the newp object
            */
            return newS = sdl.addSale(custNo, stockId, custSockId, qty, price, customer);
            
        } 
        //handles sql errors
        catch (SQLException ex) 
        {
            System.err.println("An error has accured and this may not be added to the database");
        }
        //closes connection to the database
        DBConn.getInstance().close();
        return newS;
        
    }//close Purchase
}//close StockManagerMethods
