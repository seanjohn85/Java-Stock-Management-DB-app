/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methods;

import DataBaseManager.DBConn;
import DataBaseManager.StaffDBLink;
import MainClasses.AccountTypes;
import MainClasses.Branch;
import MainClasses.BranchStaff;
import MainClasses.Employee;
import MainClasses.HeadOfficeStaff;
import MainClasses.Manager;
import MainClasses.Model;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.text.WordUtils;

/**
 *
 * @author johnkenny
 */
public class StaffMethods 
{
    /* This method used a parameter of the Model class
    It gets the list item in this class and uses the for each loop 
    to print all the details from each manager using the toString method from the Branch class
    */
    public static void viewStaff(Model model) 
    {
        //creates a employee list populated by the model class
        List<Employee> e = model.getEmployee();
        
        for (Employee emp: e) 
        {
            System.out.println(emp.details() + "\n");
        }

    }//close viewstaff
    
    //gets a list of all employees including managers
    
    public static List<Employee> allEmployees(Model model)
    {
        List<Employee> e = model.getEmployee();
        List<Manager> m = model.getManager();
        //adds all managers from the model to the employee list with all other employees
        for(Manager ma : m)
        {
            e.add(ma);
        }
        return e;
    }//close allEmployees
    
    /*
    This is an example of a bubblesort 
    it sorts all employees depending on how the user wants them sorted
    */
    
    public static List<Employee> bubbleSort(List<Employee> e, String sort)
    {
        //outer for loop. loops through each employee one
        for (int i = (e.size() - 1); i >= 0; i--)
        {
            //inner for loop  
            // in this loop i will compare each elemnt to the index next to it to 
            //I will swap if need
            for (int element2 = 1; element2 <= i; element2++)
            {
                int compare;
                /*
                  compares both names of the employes sets compare to the positive of negative 
                  difference between both strings.
                  if the second item is greater than the first compare will be positive 
                */
                //sorts in alphabetical order a to z
                if(sort.equalsIgnoreCase("alphabetical"))
                {
                   // compares name strings
                    compare = e.get(element2 - 1).getName().compareToIgnoreCase(e.get(element2).getName());
                }
                else if(sort.equalsIgnoreCase("job"))
                {
                    //compares account type strings
                    compare = e.get(element2 - 1).accountType().toString().compareToIgnoreCase(e.get(element2).accountType().toString());
                }
                else
                {
                    //compares classes
                    compare = e.get(element2 - 1).getClass().getName().substring(12).compareToIgnoreCase(e.get(element2).getClass().getName().substring(12));    
                }
                /* 
                    if compare is positive the elements in the list swap places 
                */
                if (compare > 0) 
                {
                    //System.out.println(compare);
                    //puts the first eleement in a tem object
                    Employee temp = e.get(element2 - 1);
                    //e.get(element2 -1) = e.get(element2);
                    //puts object 2 in the previous items index
                    e.set((element2 - 1), e.get(element2));
                    //puts the temp object(first index) in the second objects place
                    e.set(element2, temp);

                } 
            }//close inner for loop

        }//close outer for loop
        
        //returns sorted list
        return e;
    }//close bubbleSort
    
    //this method puts the sorting together and is called outside this class
    
    public static void sortAllEmp(Model m)
    {
        //gets the sorted list
        List<Employee> e = bubbleSort(allEmployees(m), sortType());
        
        //prints all the objects from the sorted list
        for (Employee emp: e) 
        {
            System.out.println(emp.details() + " Role: " + WordUtils.capitalize(emp.accountType().toLowerCase()) + "\n");
        }
        
    }//close sortAllEmp
    
    //sorts all alphabeticaly
    public static List<Employee> sortAlph(Model m)
    {
        
        List<Employee> e = bubbleSort(allEmployees(m), "alphabetical");
        
        return e;
    }//close sortAlph
    
    //This method allows a new employee to be created
    
    public static Employee createEmployee(Model model) throws ParseException, SQLException
    {
   
        //sets employe to null
        Employee newEmployee = null;
        /*Handles userinput creating new strings which will be later used as 
        parameteres to create a Branch object
        */
        
        int branchNo = 0;
        System.out.println("This this new employee head office staff");
        boolean branch =  Input.yesNo();
        //if the emmployee is a branch staff menber
        if(!branch)
        {
            //gets branch no checks if it excists 
           Branch br = model.branchFinder(Input.convertToNumberInt("Please Enter a valid branch No"));
           if(br == null )
           {
               System.err.println("Invalid branch number this employee can not be added");  
               
           }
           else
           {
               //if it excits gets the branch no 
               branchNo = br.getBranchNo();
           }
        }
        
        //gets employee details
        String name = Input.inputOption("Enter employee Name ");
        
        String password = Input.inputOption("Enter employee Password ");
        
        //for head office staff
        if(branchNo == 0)
        {

            int option = 0;
            boolean valid = true;
            //selects the job role hr or admin creates a headOffice Employee
            while(valid)
            {
                System.out.println("Please Select Job type  \n1)Admin \n2)hr ");
                option = Input.convertToNumberInt();
                if(option == 1)
                {
                    newEmployee =(new HeadOfficeStaff(0, name, password, AccountTypes.ADMIN));
                    return createStaff(newEmployee);
                }
                else if(option == 2)
                {
                    newEmployee =(new HeadOfficeStaff(0, name, password, AccountTypes.HR));
                    return createStaff(newEmployee);
                   
                }
            }
        }
        //if its not a headoffice staff member the branch staff member is created
        else
        {
            newEmployee =(new BranchStaff(0, name, password, branchNo));
            return createStaff(newEmployee);
        }
           
        //new employee is returned
        return newEmployee;
 
        //return the new branch just set> this will be null if it was not added to the database
     
    }//close createManager
    
   // used to connect to the database to create employee
    
    public static  Employee createStaff(Employee temp) throws SQLException
    {
        Employee newEmployee = null;
        //conects to database
        Connection conn = DBConn.getInstance().getConnection();
         StaffDBLink mdl = new StaffDBLink(conn);
         //adds the employee to the database and returns it
        try 
        {
            newEmployee = mdl.addStaff(temp);
        } 
        catch (ParseException ex) 
        {
            Logger.getLogger(StaffMethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //closes connection to the database
        DBConn.getInstance().close();
         
        return newEmployee;
          
    }//close createStaff
    
    //This allows the user to select sorting order for the bubble sort
    
    private static String sortType()
    {
        int selected = 0;
        String sort = null;
        //locks until valid selection
        while(selected != 3)
        {
            //calss the prompt option for this method from the prompt class
            Prompts.sortType();
            //sets selected to a numbered input by the user
            selected = Input.convertToNumberInt();
            //System.out.println(i);
            switch(selected)
            {
                case 1:
                    sort = "alphabetical";
                    selected = 3;
                    break;
                case 2:
                   sort = "job";
                   selected = 3;
                    break;
                case 3:
                   sort = "class";
                    break;

                default:
                    System.out.println("You have not entered a valid menu option \n Please enter a valid option \n");      
            }
        
        }
        //returns sort type
       return sort;
    }//close sortType
 
}//close ManagerMethods
