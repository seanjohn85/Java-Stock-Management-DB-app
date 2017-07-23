/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methods;

import DataBaseManager.ManagerDBLink;
import DataBaseManager.DBConn;
import MainClasses.Branch;
import MainClasses.Manager;
import MainClasses.Model;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

/**
 *
 * @author johnkenny
 */
public class ManagerMethods 
{
    /* This method used a parameter of the Model class
    It gets the list item in this class and uses the for each loop 
    to print all the details from each manager using the toString method from the Branch class
    */
    public static void viewManager(Model model) 
    {
        //creates a managers list populated by the model class
        List<Manager> managers = model.getManager();
        
        for (Manager m : managers) 
        {
            System.out.println(m.details() + "\n");
        }

    }//close viewManager
    
        //This method allows a new manahger to be created
    
    public static Manager createManager(Model model, int branchNo) throws ParseException, SQLException
    {
   
        //sets newBranch to null
        Manager newManager = null;
        /*Handles userinput creating new strings which will be later used as 
        parameteres to create a Branch object
        */
       
        //checks if branch already has a manager
        Branch br = model.branchFinder(branchNo);
        
        if(br == null)
        {
            System.err.println("Invalid branch number ");
        }
        else if(br.getMan() != null)
        {
            System.err.println("This branch aready has a manager");
        }
        else
        {
            String name = Input.inputOption("Enter manager Name ");
            String password = Input.inputOption("Enter manager Password ");
            //conects to database
            Connection conn = DBConn.getInstance().getConnection();
            ManagerDBLink mdl = new ManagerDBLink(conn);
            //adds the maanger to the database and returns it
            Manager temp =  new Manager(0, name,  password, branchNo);
            newManager = mdl.addManager(temp);
            //closes connection to the database
            DBConn.getInstance().close();
          
        }
        
       return newManager;
 
        //return the new manager,this will be null if it was not added to the database
     
    }//close createManager
    
    //This method asigns a manger to its branch
    
   public static void setBranchManager(Model model)
   {
       //gets a list of all maangers and branches
        List<Branch> branches = model.getBranch();
        List<Manager> man = model.getManager();
        
        //loops through each branch
        for(int i = 0; i<branches.size(); i++)
        {
            //loops through each manager
            for(Manager m : man)
            {
                //when the match and manage rmatch
                if(m.getBranchNo() == branches.get(i).getBranchNo())
                {
                    //add the manager to this branch object
                    branches.get(i).setMan(m);
                }
            }
        }
   }//close setBranchManager
    
}//close ManagerMethods
