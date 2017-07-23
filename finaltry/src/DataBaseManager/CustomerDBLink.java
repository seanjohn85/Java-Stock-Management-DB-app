/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBaseManager;

import MainClasses.Customer;
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
 */
public class CustomerDBLink 
{
    //These varaibles repersent the table name and rows on the database
    private static final String TABLE_NAME = "customer",
        COLUMN_CUST_NO =  "customerNo", 
        COLUMN_BRANCH_NO = "branchNo", 
        COLUMN_FNAME = "fName", 
        COLUMN_LNAME = "lName", 
        COLUMN_GENDER = "gender",
        COLUMN_EMAIL = "email",
        COLUMN_ADDRESS ="address",
        COLUMN_DATE = "openDate",
        COLUMN_MOBILE = "mobileNo";
    
    //sets the connection conn
    private Connection conn;
    
    public CustomerDBLink(Connection connection) 
    {
        conn = connection;
    }
    
    /* 
    This method gets all the customers from the database and returns them in an
    arrayList of Customer objects.
    */
    public List<Customer> getCust() throws SQLException, ParseException
    {
        //creats empty arrayList
        ArrayList<Customer> c = new ArrayList<>();
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
            //sets a loop to set the next results -row(Customer)
            while (results.next()) 
            {
                //sets varaibles to all the columns in this row
                int custNo = results.getInt(COLUMN_CUST_NO);
                int BranchNo = results.getInt(COLUMN_BRANCH_NO);
                String fName = results.getString(COLUMN_FNAME); 
                String lName = results.getString(COLUMN_LNAME); 
                String gender = results.getString(COLUMN_GENDER);
                String address = results.getString(COLUMN_ADDRESS);
                long mobile = results.getInt(COLUMN_MOBILE);
                String email= results.getString(COLUMN_EMAIL); 
                String custOpenDate = results.getString(COLUMN_DATE);
                //forms the date to a date object so java can hangle it as a date
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.m").parse(custOpenDate);
                //Used for testing the date works System.out.println(date);
                //adds the customer to the arraylist
                c.add(new Customer(custNo, BranchNo, fName, lName, gender, address, mobile, email, date));
            }
        } 
        catch (SQLException e) 
        {
            System.err.println(e);
        }
        /*
        returns the arrayslist c provided there is no database errors it will return all customers 
        currently on the database
        */
        return c;
    }//close getCust
    
    /*
    adds a cutomer to the database and returns the a customer object so it can be added
    to the customer arraylist in the model class. This limits database connections. The 
    paramaters passed in are used to create the customer
    */
    
    public Customer addCustomer(int branchNo, String fName, String lName, String gender, String address, long mobile, String email, String branchName) throws SQLException, ParseException
    {
        /* 
        created a customer object and sets it to null, the null object will be returned 
        if there is an issue creating the customer on the database
        */
        Customer cust = null;
        //This string is the sql statment
        String type = "INSERT into customer (" + COLUMN_BRANCH_NO + ", " +
                COLUMN_FNAME + ", " + COLUMN_LNAME + ", " +
                COLUMN_GENDER  + ", " + COLUMN_ADDRESS + ", " + 
                COLUMN_MOBILE + ", " +  COLUMN_EMAIL +") " + 
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        //This is the connection to the database
        try
        (
            PreparedStatement prestate = conn.prepareStatement(type, Statement.RETURN_GENERATED_KEYS);
        )
        {
           /*
            Uses the above preparedStament and sets the columns to match the above sql statement. Uses the
            paramaters passed in to set colums
            */
            prestate.setInt(1, branchNo);
            prestate.setString(2, fName);
            prestate.setString(3, lName);
            prestate.setString(4, gender);
            prestate.setString(5, address);
            prestate.setLong(6, mobile);
            prestate.setString(7, email);
            
            /* 
            Sets an int added to check if the customer is added.
            the int is set to the prestate update(the info above added to the
            database) and if it is added 1 will be returned as 1 row is added. 
            */
           
            int added = prestate.executeUpdate();
            
            //executes if customer added
            if (added == 1)
            {
                System.out.println("Customer added to " + branchName);
                //connects to the database with a reslut set of customerNo and openDate
                try 
                (
                    Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                    ResultSet results = statement.executeQuery("SELECT customerNo, openDate FROM " + TABLE_NAME);
                ) 
                {
                    //goes to the last row in the database the one just created
                    results.last();
                    //sets custNo and openDate vars depending on the info retreved from the database
                    int custNo = results.getInt(COLUMN_CUST_NO);
                    String openDate = results.getString(COLUMN_DATE);
                    //converts date to java date
                    Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.m").parse(openDate);
                    /*
                        creates a Customer object and sets it to the cust object (The return
                        Type) using all the the varaibles above to repersent the new customer created on the 
                        database
                    */
                    cust = (new Customer(custNo, branchNo, fName, lName, gender,  address,  mobile,  email, date));
                    //closes this connection to the database
                    conn.close();
                }
                catch (SQLException e) 
                {
                    System.err.println(e);
                }
            }
        }
        catch(SQLException e)
        {
        System.err.println("Error: " + e);
        }
        return cust;
        
    }//close addCustomer
    
    //this method deletes customers
    public boolean deleteCust(int no) throws SQLException
    {
        
        //sets the return type to false 
        boolean deleted = false;
        /*
        This is the sql statement used to delete a branch by row number(branch no)
        */
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_CUST_NO + "  = ?";  
        //Coonects to the database using the conn and a prepared statement to exectute the sql command
        try (
                PreparedStatement stmt = conn.prepareStatement(sql);
            )
        {
            //sets the delete to the paramater number passed in.
            stmt.setInt(1, no);
            //executes the delete
            stmt.executeUpdate();
            //changes the return to true if a branch is deleted.
            deleted = true;
        }
        conn.close();
        //returns the boolean set above
        return deleted;
        
    }//close delteCust
 
    /*this method updates a customer row a customer object is passed in
    and a boolean value is returned depending on if it is updated
    */
    public boolean updateCust(Customer c) throws SQLException
    {
        //sets returnt to false, this will change if the update is sucessiful
        boolean updated = false;
        
        //This is the sql to update a customer row
        String sql
                = "UPDATE  " + TABLE_NAME + " SET "
                + COLUMN_FNAME + " = ?, " + COLUMN_LNAME + " = ?, "
                + COLUMN_BRANCH_NO + " = ?, " + COLUMN_EMAIL + " = ?, "
                + COLUMN_ADDRESS + " = ?, " + COLUMN_MOBILE + " = ? "
                + "WHERE " + COLUMN_CUST_NO + " = ?";
        
        try (
                
                //creates a prepared statement called state and passes in the sql string
                PreparedStatement state = conn.prepareStatement(sql);
            ) 
        {
            /*
            gets all the values from the customer object passed in at the start of the 
            method and passes them to the state object, when the execute is performed this
            is what will be wrote to the database*/
            state.setString(1, c.getFName());
            state.setString(2, c.getLName());
            state.setInt(3, c.getBranchNo());
            state.setString(4, c.getEmail());
            state.setString(5, c.getAddress());
            state.setLong(6, c.getMobile());
            
            /*
            uses the customer nuber to reference the row to update
            this is set in the where part of the sql
            */
            state.setInt(7, c.getCustNo());
            
            //preforms the update
            int sucess = state.executeUpdate();
            
            //if the update was sucessiful
            if(sucess == 1)
            {
                System.out.println("Database updated");
                //changes returntype to true
                updated = true; 
            }
        }
        //handels sql errors
        catch(SQLException e)
        {
            System.err.println("Database update fail \n" +e);
        }
        conn.close();
        return updated;
        
    }//close updateCust
    
}//close CustomerDBLink
