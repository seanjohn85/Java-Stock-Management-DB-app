/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methods;

import MainClasses.Branch;
import MainClasses.Customer;
import MainClasses.Employee;
import MainClasses.Manager;
import MainClasses.Model;
import MainClasses.Purchase;
import MainClasses.Stock;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import jxl.write.WriteException;
import static methods.FileReadWrite.readTrans;

/**
 *
 * @author johnkenny
 */
public class MenuOptions 
{
    /*Creates a static Instance for the Model Class for
    all methods in this class to use
    */
    static Model brModel = Model.getInstance();
    static Branch br;
    static Manager m;
    static Customer c;
    static Stock s;
    //private static Object Promopts;
    
    /* 
    This method is used to run the main menu at the start of the
    programme. The commented out print methods where used for testing
    */
    public static void menuHandler() throws ParseException, SQLException, FileNotFoundException, IOException, WriteException
    {
        
        //sets the maangers with the branches
        ManagerMethods.setBranchManager(brModel);
        
        //Logon menu
        
        //set username and password
        String username = Input.inputOption("Enter Username");
        String password = Input.inputOption("Enter Password");
        //used to find a valid logon
        boolean found = false;
        //gets all employyes from the model
        List <Employee> e = brModel.getEmployee();
        //loop through all employees looking for a user match
        
        for(Employee temp :  e)
        {
            //System.out.println(temp.getPassword());
            //System.out.println(temp.getUserName());
            
            //checks to see if an employee username and password match 
            //what the user entered
            if(temp.getUserName().equals(username) && temp.getPassword().equals(password))
            {
                //happens if the employee if a head office hr staff
               if(temp.getBranchNo() == 0 && temp.accountType().equalsIgnoreCase("hr"))
               {
                   System.out.println("hr");
                   //build new menu
                   hrMenu();
                   
               }
               //happens if the employee if a head office admin staff
               else if(temp.getBranchNo() == 0 && temp.accountType().equalsIgnoreCase("admin"))
               {
                   
                   CustMethods.custPotfolio(brModel);
                   //loads admin menu
                    menuAdmin();
               }
               //this happens if the employee is a branch staff memeber
               else
               {
                   CustMethods.custPotfolio(brModel);
                   //Enters the menu of the branch the employee is a member of
                   branchStaffMenu(temp.getBranchNo());
               }
               //sets to found ending appication after menu runs
               found = true;
               //ends this loop
               break;
            }
        }
        //if its not a regular employee check the managers
        if(!found)
        {
            //loads all managers from the model
            List <Manager> m  = brModel.getManager();
            //loops though all managers checking for a match to the logon details
            for(Manager temp : m)
            {
                
                if(temp.getUserName().equals(username) && temp.getPassword().equals(password))
                {
                    CustMethods.custPotfolio(brModel);
                    //goes to the branch manager menu
                    branchStaffMenu(0);
                    //sets to found ending appication after menu runs
                    found = true;
                    break;
                }
            }
        }
        //this happens if no staff memeber or manager matches the input username and password
        if(!found)
        {
            System.out.println("Invalid UserName or Password");
        }
        
       
    }//close menuHandler()
    
 
    
    //this the menu for the super admin user
    public static void menuAdmin() throws ParseException, SQLException, IOException
    {
        int selected = 0;
        /*
        This while loop is locked in until the selected variable is 5 (Exit option)
        As this is the main menu the exit will terminate this application
        */
        while(selected != 6)
        {
            Prompts.options();
            /*The method convertToNumberInt is called from the input class
            to return an int option from user input and sets it to the variable selected
            */
            selected = Input.convertToNumberInt();
            
            //USED FOR TESTING ONLY  System.out.println(i);
           /* 
            This switch method handles the user input. The first for options call other methods in
            this class and case 5 is the exit terminales this programme
            */
            switch(selected)
            {
                case 1:
                    //System.out.println("You are here" + selected);
                    createSubMenu();
                    
                    break;
                case 2:
                    //System.out.println("You are here" + selected);
                    readSubMenu();
                    break;
                case 3:
                    updateSubMenu();
                    //System.out.println("You are here" + selected);
                    break;
                case 4:
                    deleteSubMenu();
                    //System.out.println("You are here" + selected);
                    break;
                case 5:
                    fileHandeler();
                    //reports and file handeling
                    break;
                case 6:
                    //System.out.println("You are here" + selected);
                    readTrans();
                    break;
                default:
                    System.err.println("You have not entered a valid menu option \n Please enter a valid option \n");      
            }
        //USED FOR TESTING ONLY String ans = inputOption();
        } 
    }//close menuAdmin()
    
    /* This is the sub menu for create it has 3 options Branch customer(for future use) and 3 to exit.
    The user input is handled by a switch method and a while loop neeps this menu running until 3 is entered
    */
    public static void createSubMenu() throws ParseException
    {
        int selected = 0;
        
        while(selected != 4)
        {
            Prompts.optionsCRUD2("Create");
            selected = Input.convertToNumberInt();
            //System.out.println(i);
            switch(selected)
            {
                case 1:
                    System.out.println("Enter the following details to create your Branch");
                    /*This method allows the user to add a new branch to the list in the Model class
                    it calles the static brModel and applies the createBranch()from the BranchMethods class
                    */
                    brModel.addBranch(BranchMethods.createBranch());
                    break;
                case 2:
                    brModel.addCust(CustMethods.createCustomer());
                    //System.out.println("You are here in the customer \nThis option is not available to you at this time");
                    break;
                case 3:
                    brModel.addStock(StockMethods.createStock());
                    //works System.out.println("You are here exit this menu" + selected);
                    break;
                case 4:
                    break;

                default:
                    System.out.println("You have not entered a valid menu option \n Please enter a valid option \n");      
            }
            
        }
    }//close createSubMenu()
    
   /* This is the sub menu for read it has 3 options Branch customer(for future use) and 3 to exit.
    The user input is handled by a switch method and a while loop neeps this menu running until 3 is entered
    */
    
    public static void readSubMenu()
    {
        int selected = 0;
        
        while(selected != 4)
        {
            Prompts.optionsCRUD2("Read");
            selected = Input.convertToNumberInt();
            //System.out.println(i);
            switch(selected)
            {
                case 1:
                    //calls the readBranch menu
                    readBranchSubMenu();
                    Prompts.optionsRBranch();
                    break;
                case 2:
                    //ManagerMethods.viewManager(brModel); 
                    CustMethods.viewCustomer(brModel);
                    //System.out.println("You are here in the customer \nThis option is not available to you at this time");
                    break;
                case 3:
                    //allows to read stock
                    StockMethods.viewStock(brModel);
                    //works System.out.println("You are here exit this menu" + selected);
                    break;
                case 4:
                //works System.out.println("You are here exit this menu" + selected);
                break;
                    
                default:
                    System.out.println("You have not entered a valid menu option \n Please enter a valid option \n");      
            }
            
        }   
    }//close readSubMenu()

    /* This is the sub menu for update it has 3 options Branch, customer(for future use) and 3 to exit.
    The user input is handled by a switch method and a while loop neeps this menu running until 3 is entered
    */
    
    public static void updateSubMenu() throws ParseException, SQLException
    {
        int selected = 0;
        
        while(selected != 3)
        {
            Prompts.optionsCRUD("Update");
            selected = Input.convertToNumberInt();
            //System.out.println(i);
            switch(selected)
            {
                case 1:
                    //System.out.println("You are here in the branch" + selected);
                    //update branch menu
                    updateBrmenu();
                    break;
                case 2:
                    //allows to update a customer
                    CustMethods.updateCust(brModel);
                    //System.out.println("You are here in the customer \nThis option is not available to you at this time");
                    break;
                case 3:
                    //worksSystem.out.println("You are here exit this menu" + selected);
                    break;

                default:
                    System.out.println("You have not entered a valid menu option \n Please enter a valid option \n");      
            }
            
        }
    }//close updateSubMenu()
    
    /* This is the sub menu for delete it has 3 options Branch, customer(for future use) and 3 to exit.
    The user input is handled by a switch method and a while loop neeps this menu running until 3 is entered
    */
    
    public static void deleteSubMenu()
    {
        int selected = 0;
        
        while(selected != 3)
        {
            Prompts.optionsCRUD("Delete");
            selected = Input.convertToNumberInt();
            //System.out.println(i);
            switch(selected)
            {
                case 1:
                    //allows to delete a branch
                    BranchMethods.deleteBranch(brModel);
                    break;
                case 2:
                    //allows to delete a customer
                    CustMethods.deleteCust(brModel);
                    //System.out.println("You are here in the customer \nThis option is not available to you at this time");
                    break;
                case 3:
                    //works System.out.println("You are here exit this menu" + selected);
                    break;

                default:
                    System.out.println("You have not entered a valid menu option \n Please enter a valid option \n");      
            }//close while 
        }
    }//close deleteSubMenu()
    
    //***************Branch menues*************
    
    /* 
    This is the read menu for a branch read options. There are 3 options in this feild
    option 3 returns to the previous menu by ending the while loop.
    the swhich statement handles what happens post user input
    */
    public static void readBranchSubMenu()
    {
        int selected = 0;
        while(selected != 3)
        {
            //calss the prompt option for this method from the prompt class
            Prompts.optionsRBranch();
            //sets selected to a numbered input by the user
            selected = Input.convertToNumberInt();
            //System.out.println(i);
            switch(selected)
            {
                case 1:
                    /*This calls the viewBranch method from the brModel from the BranchMethod class
                    this method displays all data from all branches
                    */
                    BranchMethods.viewBranch(brModel); 
                    break;
                case 2:
                    /*This calls the viewBranchNameNo method from the brModel from the BranchMethod class
                    this method displays all the name and number for all branches
                    */
                    BranchMethods.viewBranchNameNo(brModel);
                    break;
                case 3:
                    //works EXIT System.out.println("You are here exit this menu" + selected);
                    break;

                default:
                    System.out.println("You have not entered a valid menu option \n Please enter a valid option \n");      
            }
            
        }   
    }//close readBranchSubMenu
    
    //This menu if for preforming updates on branches

    public static void updateBrmenu() throws ParseException, SQLException
    {
        int selected = 0;
        //locks until valid selection
        while(selected != 3)
        {
            //calss the prompt option for this method from the prompt class
            Prompts.branchUpdateMenu();
            //sets selected to a numbered input by the user
            selected = Input.convertToNumberInt();
            //System.out.println(i);
            switch(selected)
            {
                case 1:
                    /*
                    This allows a branch to be updated
                    */
                    BranchMethods.updateBr(brModel);
                    break;
                case 2:
                    /*
                      this allows a manager      
                    */
                    BranchMethods.updateBrManager(brModel);
                    break;
                case 3:
                    //works EXIT System.out.println("You are here exit this menu" + selected);
                    break;

                default:
                    System.out.println("You have not entered a valid menu option \n Please enter a valid option \n");      
            }
        
        }
    }//close updateBrmenu
    
    //used by head office admin for file io processes
    
    private static void fileHandeler() throws IOException 
    {
        int selected = 0;
        //locks until valid selection
        while(selected != 3)
        {
            //calss the prompt option for this method from the prompt class
            Prompts.fileHandelMenu();
            //sets selected to a numbered input by the user
            selected = Input.convertToNumberInt();
            //System.out.println(i);
            switch(selected)
            {
                case 1:
                    /*
                    This transactions to be added in batches
                    */
                    FileReadWrite.readTrans();
                    break;
                case 2:
                    //gets customer no from user 
                    int custNo = Input.convertToNumberInt("Please enter a valid Customer Number \n");
                    //locates customer
                    Customer c = brModel.custFinder(custNo);
                    //if found report is created 
                    if(c != null)
                    {
                        FileReadWrite.custReport(c);
                    }
                    //else error not found
                    else
                    {
                        System.err.println(custNo +" is not a valid customer \n");
                    }    
                    break;
                case 3:
                    //works EXIT System.out.println("You are here exit this menu" + selected);
                    break;

                default:
                    System.out.println("You have not entered a valid menu option \n Please enter a valid option \n");      
            }
        
        }
    }//close fileHandeler()
    
    //hr staff only can access this menu
    // allows to view/ add and file io excel add/ output employees
    
    private static void hrMenu() throws IOException, ParseException, SQLException, WriteException 
    {
        int selected = 0;
        //locks until valid selection
        while(selected != 5)
        {
            //calss the prompt option for this method from the prompt class
            Prompts.hrMenu();
            //sets selected to a numbered input by the user
            selected = Input.convertToNumberInt();
            //System.out.println(i);
            switch(selected)
            {
                case 1:
//                    System.out.println("Staff");
//                    StaffMethods.viewStaff(brModel);
//                    System.out.println("Managers");
//                    ManagerMethods.viewManager(brModel);
                    //view all staff with sort
                    StaffMethods.sortAllEmp(brModel);
                    break;
                case 2:
                    //adds a staff memeber in the console
                    brModel.addEmployees(StaffMethods.createEmployee(brModel));
                    break;
                case 3:
                    //reads employees from excel file/ writes to the database
                    FileReadWrite.readExcelFile();
                    //works EXIT System.out.println("You are here exit this menu" + selected);
                    break;
                case 4:
                    //writes employees to excel file
                    FileReadWrite.employeeWrite();
                    //works EXIT System.out.println("You are here exit this menu" + selected);
                    break;
                case 5:
                    //works EXIT System.out.println("You are here exit this menu" + selected);
                    break;
                default:
                    System.out.println("You have not entered a valid menu option \n Please enter a valid option \n");      
            }
        
        }
    }//close hrMenu()
 
    
    
    //This method is for the menu to be used by a branch
    
    public static void branchStaffMenu(int x) throws ParseException, SQLException, FileNotFoundException, IOException
    {
        //loads the branchMenu
        BranchMenuMethods.branchLogin(x);
    }

}//close MenuOptions class
