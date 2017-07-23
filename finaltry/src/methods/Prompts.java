/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methods;

/**
 *
 * @author johnkenny
 * Menu prompts used for menus only 
 */
public class Prompts 
{
    //main menu propts
    public static void main()
    {
        System.out.println("Please select one of the following options buy inputing the number: \n 1) Branch Menu \n 2) Admin Menu \n 3) To exit this application");
    
    }//close options
    //menu admin menu prints
    public static void options()
    {
        System.out.println("Please select one of the following options buy inputing the number: \n 1) To Create \n 2) To Read \n 3) To Update \n 4) To Delete \n 5) Batch Uploading and Reports \n 6) To Exit");
    
    }//close options
    
    //prints sub menu taking a partamter which is the intended sub menu 
    public static void optionsCRUD(String type)
    {
        System.out.println("Please select what you would like to " + type + " options buy inputing the number: \n 1) To " + type + " Branch\n 2) To " + type + " customer \n 3) To exit this menu and return to previous menu");
    
    }//close optionsCRUD
    
        //prints sub menu taking a partamter which is the intended sub menu 
    public static void optionsCRUD2(String type)
    {
        System.out.println("Please select what you would like to " + type + " options buy inputing the number: \n 1) To " + type + " Branch\n 2) To " + type + 
                " customer \n 3) To "+ type + " stock \n 4)exit this menu and return to previous menu");
    
    }//close optionsCRUD2
    
    public static void optionsRBranch()
    {
        System.out.println("Please select what you would like to view \n 1) To view all Braches and their all details \n 2) To veiw all Branches by Branch Name, Branch id  \n 3) To exit this menu and return to previous menu");
    
    }//close optionsRBranch
    
    public static void branchUpdateMenu()
    {
        System.out.println("Please select one of the following options buy inputing the number: \n 1) To Update a branch \n 2) To update a manager \n 3) To Exit");
    
    }
    public static void openingHours()
    {
        System.out.println("Pick on of the following opening hours: " +
                    "\n1) 8 to 4 " + "\n2) 8 to 6" + "\n3) 9 to 4" + "\n4) 9 to 6" +
                    "\n5) 10 to 6" +"\n6) 10 to 8");
        
    }//close openingHours
    
    public static void branchStaff(String branchName)
    {
       System.out.println("Please select what you would like to view \n 1) To view all customers of " + 
               branchName +" branch and their all details \n 2) To add new a customer to the " + branchName + " branch " +
               " branch \n 3) To enter a customers stock portfolio \n 4) To exit this menu and return to previous menu");
    }//close branchStaff
    
    public static void client(String customer)
    {
       System.out.println("Please select what you would like to view \n 1) To view all stock of " + 
               customer +" \n 2) To view purchase history of " + customer + 
               "\n 3) To add stock to " + customer +" \n 4) To exit this menu and return to previous menu");
    }//close client
    
    public static String client()
    {
       return "Please enter a customer number of a customer belonging to this branch or press 0 to exit this menu and return to previous menu";
    }//close client
    
    public static String stock(String customer)
    {
       return "Please select what you would like to view \n 1) To view all stock of " + 
               customer +" \n 2) To view purchase history of " + customer + 
               " \n 3) To view sales history of " + customer + "\n 4) To add stock to " + customer +
               "\n 5) To sell stock from " + customer + "\n 6) To create a report for this customer  " + customer 
               + "\n 7) To exit this menu and return to previous menu";
    }// close stock

    public static void fileHandelMenu() 
    {
       System.out.println("Please select what file upload or report you would like to excute \n 1) To upload a batch of purchases and sales" + 
               " \n 2) To print a customer report " +
                "\n 3) To exit this menu and return to previous menu");
    }//close fileHandelMenu() 
    public static void sortType() 
    {
       System.out.println("Please select how you would like to sort the employees \n 1) alphabetical order" + 
               " \n 2) Job type  \n 3) Class type" ); 
    }//close sortType

    static void hrMenu() 
    {
         System.out.println("Please select from the following \n 1) view all employees" + 
               " \n 2) Add new employee " +
               "\n 3) Read employees from excel file and add to database " + "\n 4) Write all Employees to excel file "
                +" \n 5) To exit this menu and Log Out");
    }//close hrMenu
    
}//close prompts
