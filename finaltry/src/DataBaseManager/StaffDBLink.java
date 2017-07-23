/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBaseManager;

import MainClasses.AccountTypes;
import MainClasses.BranchStaff;
import MainClasses.Employee;
import MainClasses.HeadOfficeStaff;
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

public class StaffDBLink 
{
    //These match the properties of the database for this table
    
    private static final String TABLE_NAME = "staff",
        COLUMN_STAFF_NO =  "staffNo",
        COLUMN_BRANCH_NO = "branchNo", 
        COLUMN_ACCOUNTID = "accountId", 
        COLUMN_NAME = "name";
    
    
    private Connection conn;
    
    //lets a connection be passed into this class
    public StaffDBLink(Connection connection) 
    {
        conn = connection;
    }
    
    //List method returns a list all the employees(headoffice and branch staff) from database
    
    public List<Employee> getStaff() throws SQLException, ParseException
    {
        //creates a list of Managers
        ArrayList<Employee> emp = new ArrayList<>();
      
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
                //gets employee details by column by row
                int staffNo = results.getInt(COLUMN_STAFF_NO);
                int branchNo = results.getInt(COLUMN_BRANCH_NO);
                int accountId = results.getInt(COLUMN_ACCOUNTID);
                String name = results.getString(COLUMN_NAME);
                String password = null;
                String account = null;
                //System.out.println(staffNo + name);
                //adds ot to the arraylist
                
                try 
                    (
                    //sets connection type to read all from the useraccounts
                    Statement statement2 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                    ResultSet results2 = statement2.executeQuery("SELECT * FROM useraccounts WHERE accountId = " + accountId);
                    ) 
                {
                    //loops until employees id is found in user accounts table
                    while (results2.next()) 
                    {
                        account = results2.getString("accountType");
                        password = results2.getString("password");
                       
                        //System.out.println(password);
                    }
                }
                catch (SQLException e) 
                {
                    System.err.println(e);
                }
        
               //if  branch no is 0  the employee object is a headOfficeStaff object
               
                if(branchNo == 0)
                {
                    //used to decide the type of head office staff hr or admin
                    AccountTypes ac = AccountTypes.ADMIN;
                    if(account.equalsIgnoreCase(AccountTypes.HR.name()))
                    {
                        ac = AccountTypes.HR;
                    }
                    //cretes the headoffice staff oject created from the database using the varibles above
                    emp.add(new HeadOfficeStaff(staffNo, accountId,  name, password, ac));
                }
                /*
                   if the employee has a branch no greater than 0 they are a
                    branch staff and a branchStaff object is added to the emp 
                    employee arraylist
                */
                else
                {
                   
                    //BranchStaff employee oject created from the database using the varibles above
                    emp.add(new BranchStaff(staffNo, accountId,  name, password, branchNo));
               
                    //emp.add(new Employee(1));
                }
                
            }//close while
            
        } //close try 
        catch (SQLException e) 
        {
            System.err.println(e);
        }
        //returns all employees(except managers) from the database
        
        return emp;
        
    }//closes  getManager
    
    /*
    adds an employee to the database and returns the a employee object so it can be added
    to the manager arraylist in the model class. This limits database connections. The 
    paramater employee is passed in to create it
    */
    
    public Employee addStaff(Employee temp) throws SQLException, ParseException
    {
        /* int branchNo,  String name, String password
        created a employee object and sets it to null, the null object will be returned 
        if there is an issue creating the employee on the database
        */
        Employee ma = null;
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
            if(temp.getClass().getName().substring(12).equalsIgnoreCase("BranchStaff"))
            {
                prestate.setInt(1, temp.getBranchNo());
            }
            else if(temp.getClass().getName().substring(12).equalsIgnoreCase("HeadOfficeStaff"))
            {
                prestate.setInt(1, 0);
            }
            
            prestate.setString(2, temp.getName());
           
            /* 
            Sets an int added to check if the employee is added.
            the int is set to the prestate update(the info above added to the
            database) and if it is added 1 will be returned as 1 row is added. 
            */
            int added = prestate.executeUpdate();

            if (added == 1)
            {
                System.out.println("employee added");
                //connects to the database with a result set of staffNo
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
                    
                    //gets the employee no of the employee just created from the database
                    int staffNo = results.getInt(COLUMN_STAFF_NO);

                    //checks the employee type by comparing the class name
                    if(temp.getClass().getName().substring(12).equalsIgnoreCase("BranchStaff"))
                    {
                        //sets ma to a branchStaff object
                        ma =(new BranchStaff(staffNo, 0, temp.getName(), temp.getPassword(), temp.getBranchNo()));
                    }
                    else
                    {
                        //sets ma to a HeadOfficeStaff object
                        ma = (new HeadOfficeStaff(staffNo, 0, temp.getName(), temp.getPassword(), temp.getAc()));
                    }
                    //passes the ma object to the account method and returns the employee cretaed by this method
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
        //returns a the ma employee object
        return ma;
        
    }//close addStaff
   
    /*
    creates a useraccount for a new employee
    */
    private Employee account(Employee temp)
    {
        Employee ma = null;
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
            Sets an int added to check if the employee is added.
            the int is set to the prestate update(the info above added to the
            database) and if it is added 1 will be returned as 1 row is added. 
            */
            int added = prestate.executeUpdate();

            if (added == 1)
            {
                System.out.println("Account created");
                //connects to the database with a result set of staffNo
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
                    
                    //gets the manager no of the manager just created from the database
                    int id = results.getInt("accountId");
                    System.out.println(id);
                    temp.setAcccoutId(id);
                    System.out.println(temp.getAcccoutId());
                   //closes connection
                    //conn.close();
                    
                    //sets the ma manager object with the paramaters of the new manager just added to the database
                    
                    temp.setAcccoutId(id);
                    ma = temp;
                    //passes the ma object to the updateId method
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
        //returns a the employee object
        return ma;
        
    }//close account
 
    /*
    updates the useraccount id on an staff table just created as we dont know
    the user id when the staff is added
    */
 
    private boolean updateID(Employee m) throws SQLException
    {
        //sets returnt to false, this will change if the update is sucessiful
        boolean updated = false;
        
        //This is the sql to update a branch row
        String sql
                = "UPDATE  " + TABLE_NAME + " SET "
                 + COLUMN_BRANCH_NO + " = ?, "
                + COLUMN_ACCOUNTID + " = ?, " + COLUMN_NAME + " = ? "
                + "WHERE " + COLUMN_STAFF_NO + " = ?";
        
        try (
                
                //creates a prepared statement called state and passes in the sql string
                PreparedStatement state = conn.prepareStatement(sql);
            ) 
        {
            /*
            gets all the values from the employee object passed in at the start of the 
            method is a brancchStaff of headoffice staff*/
            if(m.getClass().getName().substring(12).equalsIgnoreCase("BranchStaff"))
            {
                state.setInt(1, m.getBranchNo());
            }
            else if(m.getClass().getName().substring(12).equalsIgnoreCase("HeadOfficeStaff"))
            {
                state.setInt(1, 0);
            }
            state.setInt(2, m.getAcccoutId());
            state.setString(3, m.getName());
            
            /*
            uses the branch number to reference the row to update
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

}//close employeeDBLink
