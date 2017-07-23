/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methods;


import DataBaseManager.BranchDBLink;
import DataBaseManager.DBConn;
import MainClasses.Branch;
import MainClasses.Customer;
import MainClasses.Manager;
import MainClasses.Model;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author johnkenny
 */
public class BranchMethods 
{
    //This method allows a new branch to be created
    
    public static Branch createBranch() throws ParseException
    {
        //sets newBranch to null
        Branch newBranch = null;
        /*Handles userinput creating new strings which will be later used as 
        parameteres to create a Branch object
        */
        String branchName = Input.inputOption("Enter Branch Name ");
        String branchAddress = Input.inputOption("Enter Branch Address ");
        long phoneNo = Input.convertToNumberInt("Enter Branch phone number");
        //uses the below openHours method
        String openingHours = openHours();
        try
        {   
            //Creates a connection using the getConnection method from the DBConn class
            Connection conn = DBConn.getInstance().getConnection();
            //uses the connection in the BranchDGLink class
            BranchDBLink bdl = new BranchDBLink(conn);
            /*
            uses the method .addbranch method from the brandDBLink class to add a new
            branch to the database pasing in the above varaibles as paramaters and if 
            this is created it will return the branch and set it to the newBranch object
            */
            newBranch = bdl.addBranches(branchName, branchAddress, openingHours, phoneNo);
            
        } 
        //handles sql errors
        catch (SQLException ex) 
        {
            System.err.println("An error has acured and this may not be added to the database");
        }
        //closes connection to the database
        DBConn.getInstance().close();
        //return the new branch just set> this will be null if it was not added to the database
     return newBranch;  
    }//close createBranch
    
    //read methods
    
    /* This method used a parameter of the Model class
    It gets the list item in this class and uses the for each loop 
    to print all the details from each branch using the toString method from the Branch class
    */
    
    public static void viewBranch(Model brModel) 
    {
        /*
        creates a list of branches from the model passed in using the model class
        */
        List<Branch> branches = brModel.getBranch();
        /*
        loops through each branch using the Branch toString method to print the all the branches
        */
        for (Branch br : branches) 
        {
            System.out.print(br.toString() + "\n");
            try
            {
                 System.out.println("Manager : " + br.getMan().getName() +"\n");
            }
            catch(NullPointerException e)
            {
                //catches error if this branch has no manager
            }
           
        }//close for
        
        System.out.println("--------------------------------------");
        
    }//close viewBranch
    
    /* This method used a parameter of the Model class
    It gets the list item in this class and uses the for each loop 
    to print all the name and number from each branch using the get methods from the Branch class
    */
    
    public static void viewBranchNameNo(Model brModel) 
    {
        /*
        creates a list of branches from the model passed in using the model class
        */
        List<Branch> branches = brModel.getBranch();
        /*
        loops through each branch using the Branch get methods to print the name and number of all the branches
        */
        for (Branch br : branches) 
        {
            System.out.println("Branch no: " + br.getBranchNo() + "--Branch Name " + br.getBranchName());
        }
        System.out.println("--------------------------------------");
        
    }//closeviewBranchNameNo
    
    
    // This allows checks if a branch is valid the model class and an int is passed into this method

    public static boolean vaildBranch(Model brModel, int x) 
    {
        /*This sets a bollean value to false and this will be returned if it is not changed in the below
        for loop
        */
        boolean valid = false;
        //creates a list of branches using the model class
        List<Branch> branches = brModel.getBranch();
        
        //this for loop loops through each branch
        for(Branch br : branches)
        {
            //if a branch number matches a branch number in the list the return boolean is set to true
            if(br.getBranchNo() == x)
            {
                System.out.println("Branch found was " + br.getBranchName() + ".");
                valid = true;
            }
        }
        return valid;
        
    }//close vaildBranch
    
    //This method allows a branch to be deleted
    public static void deleteBranch(Model brModel) 
    {
        //sets user input to the branch no to delete
        int branchNo = Input.convertToNumberInt("Enter the Branch no you would like to delete \n"
                + "If you are not sure of the Branch no please use Read option on"
                + "the previous menu");
        //creates a branch object
        Branch br;
        
        /*
        uses the Branchfinder method in the passed in the paramterer mod to check
        if the branch no is valid and locates it if so.
        */
        String bra = null;
        
        br = brModel.branchFinder(branchNo);
        try
        {
           bra = br.getBranchName();
        }
        catch(NullPointerException e)
        {
            System.err.println("Invaild branch");
            
        }
        
        //System.out.println(br.getBranchName());
         
        // If a branch is loaded into br
        if (bra != null) 
        {
            System.out.println(br.getBranchName());
            boolean delete = false;
            try
            {  
               
                //crestes a new connection
                Connection conn = DBConn.getInstance().getConnection();
               
                BranchDBLink bdl = new BranchDBLink(conn);
                
                delete = bdl.deleteBranche(br);

            } 
            catch (SQLException ex) 
            {
                System.err.println("An error has accured and this may not be deleted from the the database \n Please ensure this branch has no customers ");
            }
            //closes connection
            DBConn.getInstance().close();

                //uses the remove Branch method to delete the branch held in br
            if (brModel.removeBranch(br) && delete) 
            {
                System.out.println("Branch deleted");
            }
            else 
            {
                System.out.println("Branch not deleted");
            }
        }
        //if an invaild branch is entered
        else 
        {
            System.err.println("Branch no invaild ");
        }
    }//close deleteBranch
    
    
    /*
    As open hours is an enum on the database, The below method is called when creating a
    database to ensure valid open hours are selected by the user. The while loop locks the
    user in this option until the valid option is selected
    */
    public static String openHours()
    {
        String openHours = null;
        boolean valid =true;
        
        //locks into this loop to until a valid input
        //when  a valid option in selected  valid would trun to false to end the when loop
        //these options match the 6 enum option on the branch table
        
        while(valid)
        {
            Prompts.openingHours();
            int selected = Input.convertToNumberInt();
            if(selected == 1)
            {
                openHours = "8 to 4";
                valid =false;
            }
            else if(selected == 2)
            {
                openHours = "8 to 6";
                valid =false;
            }
            else if(selected == 3)
            {
                openHours = "9 to 4";
                valid =false;
            }
            else if(selected == 4)
            {
                openHours = "9 to 6";
                valid =false;
            }
            else if(selected == 5)
            {
                openHours = "10 to 6";
                valid =false;
            }
            else if(selected == 6)
            {
                openHours = "10 to 8";
                valid =false;
            }
            else
            {
                System.err.print("Ivalid option selected");
            }
        }
        
        //returns the selected value
        return openHours;
    
    }//close openHours
    
    //this method is used to assign a manager to a branch
    
    public static void updateBr(Model brModel) 
    {
        //sets user input to the branch no to delete
        int brNo = Input.convertToNumberInt("Enter the Branch no you would like to update \n"
                + "If you are not sure of the Branch no please use Read option on"
                + "the previous menu");
        
        Branch br;
        
        /*uses the branchFinder method in the passed in the paramterer mod to check
        if the br no is valid and locates it if so.
        */
        
        br = brModel.branchFinder(brNo);
 
        //If the br object is not null
        if (br != null) 
        {
            /*updated is set to the return value from the sellectBranchCol method
            to true if the update is sucessiful
            */
           boolean updated = selectBranchCol(br);
           //if the update is sucessiful
           if(updated)
           {
               System.out.println("Branch " + br.getBranchName() + " had been updated");
           }
        }
            
    }  //close updateCust
       
    //Update method checks which feild to update and writes to the database
    
    public static boolean selectBranchCol(Branch br)
    {
        //sets the return type value to false
        boolean update = false;
        //this is used for the while loop below. The loop will not break unless this is false
        boolean selected = true;
        //gets the branch number from the branch object passed in
        int branchNo = br.getBranchNo();
        //this asks the branch what they would like to update
        while(selected)
        {
            //gets a valid int
            int choice = Input.convertToNumberInt("Please select one of the following to update \n1)Branch name "+
                "\n2)address \n3)phone number \n4)opening hours ");
            
            if(choice == 1)
            {
               //sets a new first name based on user input
                br.setBranchName(Input.inputOption("Enter new Branch name"));
                //sets return value to true
                update = true;
                //breaks this loop
                selected = false;
            }
            else if(choice == 2)
            {
                //sets a new last name based on user input
                br.setBranchAddress(Input.inputOption("Enter new address"));
                //sets return value to true
                update = true;
                //breaks this loop
                selected = false;
            }
            else if(choice == 3)
            {
                //
                br.setPhoneNo(Input.convertToNumberLong("Enter new phone number"));
                //sets return value to true
                update = true;
                //breaks this loop
                selected = false;
            }
            else if(choice == 4)
            {
                String hours = openHours();
                br.setBranchOpeningHours(hours);
                //sets return value to true
                update = true;
                //breaks this loop
                selected = false;
            }

        } 
        
        //if the update boolean is true
        if(update)
        {
            //try to connect to the database
            try
            {   
                Connection conn = DBConn.getInstance().getConnection();
                //create an instance of the CustomerDBLink class passing in a connection
                BranchDBLink bdl = new BranchDBLink(conn);
                /*
                   sets update to the value of the method updateBranch from the BranchDBLink class
                   This will try to update  to the database and if sucessiful it will return true
                        */
                update = bdl.updateBranch(br);

                
            } 
            //this handels the sql errors
            catch (SQLException ex) 
            {
                System.err.println("An error has accured and this may not be deleted to the database \n Please check if this Branch has a maanger or customers");
            }
        }
        //returns if update was sucessiful or not
        return update;
    }
    
    //This allows the user to assign a branch manager to a branch
    //note each branch can only have one manager
    public static void updateBrManager(Model brModel) throws ParseException, SQLException
    {
        int branchNo = Input.convertToNumberInt("Enter the Branch no you would like to update the manager on \n"
               + "If you are not sure of the Branch no please use Read option on"
               + "the previous menu");
        Manager m = ManagerMethods.createManager(brModel, branchNo);
        if(m != null)
        {
          List<Branch> branches = brModel.getBranch();
          for(int i = 0; i <branches.size(); i++)
          {
              if(branches.get(i).getBranchNo() == branchNo)
              {
                  branches.get(i).setMan(m);
              }
          }
                  
        }    
    }//close updateBrManager
    
    public static void setBranchCust(Model brModel) 
    {
        /*
        creates a list of branches from the model passed in using the model class
        */
        List<Branch> branches = brModel.getBranch();
        List<Customer> cust = brModel.getCustomer();
        /*
        loops through each branch using the Branch get methods to print the name and number of all the branches
        */
        for(int i = 0; i < branches.size(); i++)
        {
            //System.out.println(i);
            ArrayList<Customer> customer = new ArrayList<Customer>();
            for (Customer c : cust) 
            {
               // System.out.println(2);
                if(c.getBranchNo() == branches.get(i).getBranchNo()) 
                {
                    //System.out.println("added");
                    
                    customer.add(c);
                } 
            }
            
            branches.get(i).setCust(customer);
            List <Customer> c = branches.get(i).getCust();

        }//closeviewBranchNameNo
        
    } //close setBranchCust
    
}//close BranchMethods

