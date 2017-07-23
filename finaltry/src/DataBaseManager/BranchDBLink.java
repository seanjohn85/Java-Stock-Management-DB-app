/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBaseManager;

import MainClasses.Branch;
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
 * This class is used to hook up to the branch table on my database
 * 
 */

public class BranchDBLink 
{
    //These varaibles repersent the table name and colums on the database
    
    private static final String TABLE_NAME = "branch",
        COLUMN_BRANCH_NO = "branchNo",
        COLUMN_BRANCH_NAME = "branchName",
        COLUMN_ADDRESS = "address",
        COLUMN_PHONE = "phoneNo",
        COLUMN_CREATED = "createdDate",   
        COLUMN_OPEN_DATE = "createdDate",
        COLUMN_OPEN_HOURS = "openHours";
    
    //sets the connection conn
    private Connection conn;
    
    //allows a connection to be passed into this object as a pramater
    
    public BranchDBLink(Connection connection) 
    {
        conn = connection;
    }
    
    /* 
    This method gets all the customers from the database and returns them in an
    arrayList of Branch objects.
    */
    public List<Branch> getBranches() throws SQLException, ParseException
    {
        //creats empty arrayList
        ArrayList<Branch> br = new ArrayList<>();
        
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

            //sets a loop to set the next results -row(Branch)
            while (results.next()) 
            {
                String branchName = results.getString(COLUMN_BRANCH_NAME);
                String branchAddress = results.getString(COLUMN_ADDRESS); 
                String branchOpeningHours = results.getString(COLUMN_OPEN_HOURS);
                
                int branchNo = results.getInt(COLUMN_BRANCH_NO);
                long phoneNo = results.getLong(COLUMN_PHONE);
                
                //USE FOR REPORT
                //Gets the date and time from the 
                //Timestamp timestamp = results.getTimestamp(COLUMN_OPEN_DATE);

                //java.util.Date date = timestamp;
                
  
                //System.out.println(date);
                String branchOpenDate = results.getString(COLUMN_OPEN_DATE);
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.m").parse(branchOpenDate);
                //System.out.println(date);
                
                //To compare dates for later use
                //if(date1.after(date))
                //{
                //   System.out.println("before"); 
                //}
                //LocalDate ld = LocalDate.parse(branchOpenDate);
                //System.out.println(branchOpenDate);
                
                //adds the branch to the list of Branches
                br.add(new Branch(branchName, branchAddress, date, branchOpeningHours, branchNo, phoneNo));
                //closes connection
                
                
            }//close while loop
        } 
        catch (SQLException e) 
        {
            System.err.println(e);
        }
       // conn.close();
        //returns the branch that was added to the database
        return br;
    } 

    /*
    adds a branch to the database and returns the a branch object so it can be added
    to the branch arraylist in the model class. This limits database connections. The 
    paramaters passed in are used to create a branch
    */
    
    public Branch addBranches(String name, String address, String hours, long phoneNo) throws SQLException, ParseException
    {
        /* 
        created a branch object and sets it to null, the null object will be returned 
        if there is an issue creating the branch on the database
        */
        Branch br = null;
        
        //This string is the sql statment
        String type = "INSERT into branch (branchName, address, openHours, phoneNo) "  +
        "VALUES (?, ?, ?, ?)";
        
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
            prestate.setString(1, name);
            prestate.setString(2, address);
            prestate.setString(3, hours);
            prestate.setLong(4, phoneNo);
            
            /* 
            Sets an int added to check if the branch is added.
            the int is set to the prestate update(the info above added to the
            database) and if it is added 1 will be returned as 1 row is added. 
            */
            int added = prestate.executeUpdate();
            
            /* 
            Sets an int added to check if the branch is added.
            the int is set to the prestate update(the info above added to the
            database) and if it is added 1 will be returned as 1 row is added. 
            */
            if (added == 1)
            {
                System.out.println("branch added");
                //connects to the database with a reslut set of branchNo and openDate
                try 
                (
                    Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                    ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
                ) 
                {
                    //goes to the last row in the database the one just created
                    results.last();
                    
                    //gets autogenetated values to return to the new branch
                    String branchOpeningHours = results.getString(COLUMN_OPEN_HOURS);
                    int branchNo = results.getInt(COLUMN_BRANCH_NO);
                    String branchOpenDate = results.getString(COLUMN_OPEN_DATE);
                    //handels date for java
                    Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.m").parse(branchOpenDate);
                    
                    //closes connection
                    conn.close();
                    //LocalDate ld = LocalDate.parse(branchOpenDate);
                    //System.out.println(branchOpenDate);
                    br = (new Branch(name, address, branchOpeningHours, branchNo, date));
                }//close try
                //handels sql errors
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
        //returns a the br Branch
        return br;
        
    }//close add branch
    
    
    /*
    This method is to delete a branch and returns a boolean so other methods can see if 
    the branch was deleted or not. The branch number to be deleted is passed into this method
    */
    
    public boolean deleteBranche(Branch b) throws SQLException
    {
        int br = b.getBranchNo();
     
        //sets the return type to false 
        boolean branchDeleted = false;
        /*
        This is the sql statement used to delete a branch by row number(branch no)
        */
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_BRANCH_NO + "  = ?";  
        //Coonects to the database using the conn and a prepared statement to exectute the sql command
        try (
                PreparedStatement stmt = conn.prepareStatement(sql);
            )
        {
            
            //sets the delete to the paramater number passed in.
            stmt.setInt(1, br);
            //executes the delete
            stmt.executeUpdate();
            //changes the return to true if a branch is deleted.
            //System.out.print("fin try");
            branchDeleted = true;
            //System.out.print("issue");
        }
       
        //returns the boolean set above
        return branchDeleted;
        
    }//close delete branch
    
     /*this method updates a branch row a branch object is passed in
    and a boolean value is returned depending on if it is updated
    */
    public boolean updateBranch(Branch b) throws SQLException
    {
        //sets returnt to false, this will change if the update is sucessiful
        boolean updated = false;
        
        //This is the sql to update a branch row
        String sql
                = "UPDATE  " + TABLE_NAME + " SET "
                + COLUMN_BRANCH_NAME + " = ?, " + COLUMN_ADDRESS + " = ?, "
                + COLUMN_PHONE + " = ?, " + COLUMN_OPEN_HOURS + " = ? "
                + "WHERE " + COLUMN_BRANCH_NO + " = ?";
        
        try (
                
                //creates a prepared statement called state and passes in the sql string
                PreparedStatement state = conn.prepareStatement(sql);
            ) 
        {
            /*
            gets all the values from the branch object passed in at the start of the 
            method and passes them to the state object, when the execute is performed this
            is what will be wrote to the database*/
            state.setString(1, b.getBranchName());
            state.setString(2, b.getBranchAddress());
            state.setLong(3, b.getPhoneNo());
            state.setString(4, b.getBranchOpeningHours());
 
            
            /*
            uses the branch number to reference the row to update
            this is set in the where part of the sql
            */
            state.setInt(5, b.getBranchNo());
            
            //preforms the update
            int sucess = state.executeUpdate();
            
            //if the update was sucessiful
            if(sucess == 1)
            {
                System.out.println("Database updated");
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
        
    }//close updateBranch
    
}//close BranchDBLink