/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBaseManager;

import MainClasses.Manager;
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
 * This connects to the manager table in the database
 */
public class ManagerDBLink 
{
    //These match the properties of the database for this table
    
    private static final String TABLE_NAME = "branchManager",
        COLUMN_MANGER_NO =  "managerNo",
        COLUMN_BRANCH_NO = "branchNo", 
        COLUMN_ACCOUNTID = "accountId", 
        COLUMN_NAME = "name";
    
    
    private Connection conn;
    
    //lets a connection be passed into this class
    public ManagerDBLink(Connection connection) 
    {
        conn = connection;
    }
    
    //List method returns a list all the managers from database
    
    public List<Manager> getManager() throws SQLException, ParseException
    {
        //creates a list of Managers
        ArrayList<Manager> m = new ArrayList<>();
        try (
                //sets connection type to read all from the branchManagerTable
                Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_NAME );
            ) 
        {
            
            //loops through each row on this table and sets its contents to varaibles
                    
            while (results.next()) 
            {
                //gets manaager details by column by row
                int managerNo = results.getInt(COLUMN_MANGER_NO);
                int branchNo = results.getInt(COLUMN_BRANCH_NO);
                int accountId = results.getInt(COLUMN_ACCOUNTID);
                String name = results.getString(COLUMN_NAME);
                String password = null;
                //System.out.println(managerNo + name);
                //adds ot to the arraylist
                
                try 
                    (
                    //sets connection type to read all from the useraccounts table
                    Statement statement2 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                        //gets the password for the matched id
                    ResultSet results2 = statement2.executeQuery("SELECT * FROM useraccounts WHERE accountId = " + accountId);
                    ) 
                {
                    //loops until password found
                    while (results2.next()) 
                    {
                        password = results2.getString("password");
                        //System.out.println(password);
                    }
                }
                catch (SQLException e) 
                {
                    System.err.println(e);
                }
        
                //System.out.print(password);
                m.add(new Manager(managerNo, accountId,  name, password, branchNo));
                
            }//close while
            
        } //close try
        catch (SQLException e) 
        {
            System.err.println(e);
        }
        //conn.close();
        //returns all managers from the database
        
        return m;
        
    }//closes  getManager
    
        /*
    adds a manager to the database and returns the a manager object so it can be added
    to the manager arraylist in the model class and a branch object. This limits database connections. The 
    paramaters passed in are used to create a branch
    */
    
    public Manager addManager(Manager temp) throws SQLException, ParseException
    {
        /* int branchNo,  String name, String password
        created a manager object and sets it to null, the null object will be returned 
        if there is an issue creating the branch on the database
        */
        Manager ma = null;
        //This string is the sql statment
        String type = "INSERT into " +TABLE_NAME + " (" + COLUMN_BRANCH_NO + ", " + COLUMN_NAME + ") "  +
        "VALUES (?, ?)";
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
            prestate.setInt(1, temp.getBranchNo());
            prestate.setString(2, temp.getName());
           
            
            /* 
            Sets an int added to check if the manager is added.
            the int is set to the prestate update(the info above added to the
            database) and if it is added 1 will be returned as 1 row is added. 
            */
            int added = prestate.executeUpdate();
            
            if (added == 1)
            {
                System.out.println("manager added");
                //connects to the database with a result set of managerNo
                try 
                (
                        //sql statement
                    Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                    ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
                ) 
                {
                    //goes to the last row in the database the one just created
                    results.last();
                    
                    //gets the manager no of the manager just created from the database
                    int managerNo = results.getInt(COLUMN_MANGER_NO);
                    
                   //closes connection
                    //conn.close();
                    
                    //sets the ma manager object with the paramaters of the new manager just added to the database
                    
                    ma = (new Manager(managerNo, 0, temp.getName(), temp.getPassword(), temp.getBranchNo()));
                    //creates an account for the manager an returns the manager object to the model
                    return account(ma);
                    
                }//closetry
                //handels sql errors
                catch (SQLException e) 
                {
                    System.err.println(e);
                }
            }//close if

        }//close try
        //handels sql errors
        catch(SQLException e)
        {
            System.err.println("Error: " + e);
        }
        //returns a thema manager object
        return ma;
        
    }//close addManager
    
    //creates a manager user account
    //takes a manager object as a prameter
    
    public Manager account(Manager temp)
    {
        Manager ma = null;
        //This string is the sql statment
        String type = "INSERT into useraccounts"  + " (username, password, accountType) "  +
        "VALUES (?, ?, ?)";
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
            prestate.setString(1, temp.getUserName());
            prestate.setString(2, temp.getPassword());
            prestate.setString(3, temp.accountType());
           
            
            /* 
            Sets an int added to check if the useraccount is added.
            the int is set to the prestate update(the info above added to the
            database) and if it is added 1 will be returned as 1 row is added. 
            */
            int added = prestate.executeUpdate();
            
            if (added == 1)
            {
                System.out.println("Account created");
                //connects to the database with a result set of useraccount
                try 
                (
                    //sql statement
                    Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                    ResultSet results = statement.executeQuery("SELECT * FROM useraccounts");
                ) 
                {
                    //goes to the last row in the database the one just created
                    results.last();
                    
                    //gets the account id of the useraccount just created from the database
                    int id = results.getInt("accountId");
                    System.out.println(id);
                    temp.setAcccoutId(id);
                    System.out.println(temp.getAcccoutId());
                    
                    //sets the ma manager object with the paramaters of the new manager just added to the database
                    ma = (new Manager(temp.getStaffNo(), temp.getAcccoutId(), temp.getName(), temp.getPassword(), temp.getBranchNo()));
                    updateID(ma);
                    
                }//closetry
                //handels sql errors
                catch (SQLException e) 
                {
                    System.err.println(e);
                }
            }//close if

        }//close try
        //handels sql errors
        catch(SQLException e)
        {
            System.err.println("Error: " + e);
        }
        //returns a thema manager object
        return ma;
        
    }////close account
 
    //used to update a manager
    //returns boolean true if updated
    
    public boolean updateID(Manager m) throws SQLException
    {
        //sets returnt to false, this will change if the update is sucessiful
        boolean updated = false;
        
        //This is the sql to update a manager row
        String sql
                = "UPDATE  " + TABLE_NAME + " SET "
                 + COLUMN_BRANCH_NO + " = ?, "
                + COLUMN_ACCOUNTID + " = ?, " + COLUMN_NAME + " = ? "
                + "WHERE " + COLUMN_MANGER_NO + " = ?";
        
        try (
                
                //creates a prepared statement called state and passes in the sql string
                PreparedStatement state = conn.prepareStatement(sql);
            ) 
        {
            /*
            gets all the values from the manager object passed in at the start of the 
            method and passes them to the state object, when the execute is performed this
            is what will be wrote to the database*/
            state.setInt(1, m.getBranchNo());
            state.setInt(2, m.getAcccoutId());
            state.setString(3, m.getName());
            /*
            uses the staff number to reference the row to update
            this is set in the where part of the sql
            */
            state.setInt(4, m.getStaffNo());
            
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
        
    }//close updateID

}//close managerDBLink
