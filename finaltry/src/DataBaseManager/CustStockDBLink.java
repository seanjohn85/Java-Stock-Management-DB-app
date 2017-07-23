/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBaseManager;

import MainClasses.CustStock;
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
 * table that links stock to customers
 * 
 */

public class CustStockDBLink 
{
   //varaibles to reperesent table name and rows on the custstock table
    
   private  static final String TABLE_NAME = "customerStock",
        COLUMN_STOCK_NO =  "custStockId", 
        COLUMN_CUST_NO = "customerNo", 
        COLUMN_STOCKID = "stockId",
        COLUMN_QTY = "qty";
           
    //sets the connection conn
   private Connection conn;
    
   public CustStockDBLink(Connection connection) 
   {
       conn = connection;
   }
   
   //creates a list of the data from this table used to copy the database info
   
    public List<CustStock> getCustStock() throws SQLException, ParseException
    {
        //creates a list of the data from this table
        ArrayList<CustStock> cs = new ArrayList<>();
        try (
                //sets connection type to read all from the CustStock table
                Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            ) 
        {
            
            //loops through each row on this table and sets its contents to varaibles
                    
            while (results.next()) 
            {
                int custStockId = results.getInt(COLUMN_STOCK_NO);
                int custNo = results.getInt(COLUMN_CUST_NO);
                int stockId = results.getInt(COLUMN_STOCKID);
                int qty = results.getInt(COLUMN_QTY);
                
              
                //adds a new custstock object
                cs.add(new CustStock (custStockId, custNo, stockId, qty));
            }
        } //close try
        //handels errors
        catch (SQLException e) 
        {
            System.err.println(e);
        }
        //System.out.println("done");
        //returns all custStock from the database
        return cs;
        
    } //closegetCustStock
    
    /* 
    this method is used to add a custStock object it will be used when a customer buys stock
    */
    
    public CustStock addcustStock(int no, int id, int qty ) throws SQLException, ParseException
    {
        /* 
        created a cusrStock object and sets it to null, the null object will be returned 
        if there is an issue creating this on the database
        */
        CustStock c = null;
        //This string is the sql statment
        String type = "INSERT into " + TABLE_NAME +" (" + COLUMN_CUST_NO + ", " +
                COLUMN_STOCKID + ", " + COLUMN_QTY + ") "  +
        "VALUES (?, ?, ?)";
        //This is the connection to the database
        try
        (
            PreparedStatement prestate = conn.prepareStatement(type, Statement.RETURN_GENERATED_KEYS);
        )
        {
            /*
            Uses the above preparedStament and sets the rows to match the above sql statement. Uses the
            paramaters passed in to set rows
            */
            prestate.setInt(1, no);
            prestate.setInt(2, id);
            prestate.setInt(3, qty);

            
            /* 
            Sets an int added to check if this is added.
            the int is set to the prestate update(the info above added to the
            database) and if it is added 1 will be returned as 1 row is added. 
            */
            int added = prestate.executeUpdate();
            
            /* 
            Sets an int added to check if the custstock is added is added.
            the int is set to the prestate update(the info above added to the
            database) and if it is added 1 will be returned as 1 row is added. 
            */
            if (added == 1)
            {
                //System.out.println("Stock  added");
                
                /*connects to the database with a reslut set of to fill in the auto generated key to
                return object*/
                try 
                (
                    Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                    ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
                ) 
                {
                    //goes to the last row in the database the one just created
                    results.last();

                    //gets the auto generated id 
                    int aiid = results.getInt(COLUMN_STOCK_NO);
                  
                    //closes connection
                    conn.close();
                    
                    //creats the new custstock object just added to the database
                    c = (new CustStock(aiid, no, id, qty));
                }
                catch (SQLException e) 
                {
                    System.err.println(e);
                }
            }

        }//close try
        //handels connection errors
        catch(SQLException e)
        {
        System.err.println("Error: " + e);
        }
        //returns a the c custstock object which will be used to assign stock to a customer object
        return c;
        
    }//close addcustStock
    
    //this is used to update a custstock row
    
    //this will happen if a  customer buys or sells stock they currectly have shares of
    
    public CustStock updateQty(int qty, int stockId, int custNo)
    {
        CustStock c = null;
                
        String sql = "UPDATE  " + TABLE_NAME + " SET "
                + COLUMN_QTY + " = ? "
                + "WHERE " + COLUMN_STOCKID + " = ? AND " + COLUMN_CUST_NO + " = ? ";
        
      
        try (
                
                //creates a prepared statement called state and passes in the sql string
                PreparedStatement state = conn.prepareStatement(sql);
            ) 
        {
            /*
            gets all the values from the custstock object passed in at the start of the 
            method and passes them to the state object, when the execute is performed this
            is what will be wrote to the database*/
            state.setInt(1, qty);

            /*
            uses the customer number  and stockid to reference the row to update
            this is set in the where part of the sql
            */
            state.setInt(2, stockId);
            state.setInt(3, custNo);
            
            //preforms the update
            int sucess = state.executeUpdate();
            
            //if the update was sucessiful
            if(sucess == 1)
            {
                System.out.println("Database updated");
                //changes returntype to true
                
                CustStock temp = finder(stockId, custNo);
                int id = temp.getCustStockId();
                System.out.println("id" + id);
                c = (new CustStock(temp.getCustStockId(), custNo, stockId, qty));
            
            }//close if 
            
        }//close try
        //handels sql errors
        catch(SQLException e)
        {
            System.err.println("Database update fail \n" +e);
        }
        //returns custstock object
        return c;
        
    }//close updateQty
    
    //finds a stock belonging to a customer
    //users the cust no and stock id ints as paramenters
    
    public CustStock finder(int stockId, int custNo) throws SQLException
    {
        //if no match a null object is returned
        CustStock cs= null;
        //sql selects all where stock id and cust no match the parameters
        String sql = "SELECT * FROM customerStock WHERE `stockId` = " + stockId + " AND `customerNo`= "+ custNo;
                
        ResultSet rs = null;
        
          try (
                
                PreparedStatement state = conn.prepareStatement(sql); 
            ) 
          {
              //this where the sql is executed
              state.setInt(1, stockId);
              state.setInt(2, custNo);
              rs = state.executeQuery();
              //if found the row is returned
              if(rs.next())
              {
                  int id = rs.getInt(COLUMN_STOCK_NO);
                  cs =(new CustStock(id, 1,1,1));
              }
              
          }
          catch (SQLException e)
          {
              cs =(new CustStock(1, 1,1,1));
              //System.err.println("Cound not find this");
          }
         
          return cs;

    }///close finder
    
}//close CustStockDBLink
