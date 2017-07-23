/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methods;

import DataBaseManager.CustomerDBLink;
import DataBaseManager.CustStockDBLink;
import DataBaseManager.DBConn;
import MainClasses.CustStock;
import MainClasses.Customer;
import MainClasses.Model;
import MainClasses.Purchase;
import MainClasses.Sale;
import MainClasses.Stock;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import static methods.MenuOptions.brModel;

/**
 *
 * @author johnkenny
 */
public class CustMethods 
{

     /* This method used a parameter of the Model class
    It gets the list item in this class and uses the for each loop 
    to print all the name and number from each customer using the get methods from the Branch class
    */
    
    public static void viewCustomer(Model model) 
    {
        //creates a customer list populated by the model class
        List<Customer> cust = model.getCustomer();
        
        //Uses the print method for each customer object
        for (Customer c : cust) 
        {
            System.out.println(c.toString());
        }
        System.out.println("--------------------------------------");
        
    }//close viewCustomer
     
    //allows a new customer to be added to a branch
    
    public static Customer createCustomer() throws ParseException
    {
        Customer cust = null;
        //gets a branch number
        int brNo = Input.convertToNumberInt("Please enter a vaild branch no? ");
        //checks if branch is valid
        boolean check = BranchMethods.vaildBranch(brModel, brNo);
        //if the branch no is not that of a branch
        if (!check)
        {
            System.err.println("This is not a valid branch plase check the branch no.");
        }
        else
        {
            //prompts user if they would like to adda customer
            System.out.println("Would you like to add a customer to this branch ");
            boolean ans = Input.yesNo();
            
            if(ans)
            {
                //created the customer using the create method
                cust = create(brNo);
              
            }
            
        }//close else
        
        //returns the customer so its added to this branch
        return cust;
        
    }//close createCustomer
    
    //creates a new customer
    
    public static Customer create(int custNo) throws ParseException
    {
        Customer cust = null;
        //varbiles needed for a new customer set here
                String fName = Input.inputOption("Please enter the customers first name");
                String lName = Input.inputOption("Please enter the customers last name");
                String gender = Input.inputOption("Please enter the customers gender");
                long mobile = Input.convertToNumberLong("Please enter the customers phone number");
                String address = Input.inputOption("Please enter this customers address"); 
                String email = Input.inputOption("Please enter the customers email");
                String branchName = null;
                
                try
                {   
                    //Creates a connection using the getConnection method from the DBConn class
                    Connection conn = DBConn.getInstance().getConnection();
                    //uses the connection in the CustomerDBLink class
                    CustomerDBLink cbl = new CustomerDBLink(conn);
                    
                    /*
                    uses the method .addbranch method from the CustoemrBLink class to add a new
                    customer to the database pasing in the above varaibles as paramaters and if 
                    this is created it will return the branch and set it to the cust object
                     */
                    cust = cbl.addCustomer(custNo, fName, lName, gender, address, mobile, email, branchName);

                } 
                //handles sql errors
                catch (SQLException ex) 
                {
                    System.err.println("An error has acured and this may not be added to the database");
                }
                //closes connection to the database
                DBConn.getInstance().close();
                //return the new branch just set> this will be null if it was not added to the database
                return cust;
    }
    
    //This method allows a customer to be deleted
    public static void deleteCust(Model brModel)
    {
          //sets user input to the customer no to delete
        int custNo = Input.convertToNumberInt("Enter the Customer no you would like to delete \n"
                + "If you are not sure of the Customer no please use Read option on the previous menu");
        /*
        creates a customer object useing the model class the method cust finder is applied
        to check if vaild
        */
        
        Customer c = brModel.custFinder(custNo);
        try
        {
            //prevents the customer been deleted if they have stock
            if(!c.getSt().isEmpty())
            {
                System.out.println("This customer has stock so it can't be deleted as its portfolio is still active\nPlease sell stock before deleting customer");
            }
            else
            {
                //ensures the customer number is valide
                if(c != null)
                {
                    //prompts user to confirm
                    System.out.println("Are you sure you would like to delete " + c.getFName() + " " + c.getLName());
                    boolean cont = Input.yesNo();

                    if(cont)
                    {
                        boolean delete = false;
                        //opens a connection and deletes cust from database
                        try
                        {   
                            Connection conn = DBConn.getInstance().getConnection();
                            CustomerDBLink cdl = new CustomerDBLink(conn);
                            delete = cdl.deleteCust(custNo);

                        } 
                        catch (SQLException ex) 
                        {
                            System.err.println("An error has acured and this may not be added to the database");
                        }
                        //closes connection
                        DBConn.getInstance().close();

                        //uses the remove customer method to delete the customer held in c
                        if (brModel.removeCustomer(c) && delete) 
                        {
                            System.out.println("Customer deleted");
                        }
                        else 
                        {
                            System.out.println("Customer not deleted");
                        }
                    }
                    //if an invaild customer is entered
                    else 
                    {
                        System.err.println("Customer no invaild ");
                    }
                }
                
            }
        }//close try
        catch(NullPointerException e)
        {
            System.err.println("This task could not be preformed invaild customer");     
        }
        
    }//close deleteCust
    
    //This method is used to update a customer
    
    public static void updateCust(Model brModel) 
    {
        //sets user input to the customer no to delete
        int custNo = Input.convertToNumberInt("Enter the Customer no you would like to update the manager on \n"
                + "If you are not sure of the Customer no please use Read option on"
                + "the previous menu");
        //sets cust
        Customer cust = null;
        
        /*uses the custFinder method in the passed in the paramterer mod to check
        if the cust no is valid and locates it if so.
        */
        
        cust = brModel.custFinder(custNo);
 
        //If the cust object is not null
        if (cust != null) 
        {
            /*updated is set to the return value from the sellectCustCol method
            to true if the update is sucessiful
            */
           boolean updated = selectCustCol(cust);
           //if the update is sucessiful
           if(updated)
           {
               System.out.println("Customer " + cust.getCustNo() + " had been updated");
           }
        }
            
    }  //close updateCust
       
    //Update method checks which feild to update and writes to the database
    public static boolean selectCustCol(Customer cust)
    {
        //sets the return type value to false
        boolean update = false;
        //this is used for the while loop below. The loop will not break unless this is false
        boolean selected = true;
        //gets the customer number from the customer object passed in
        int custNo = cust.getCustNo();
        //this asks the customer what they would like to update
        while(selected)
        {
            //gets a valid int
            int choice = Input.convertToNumberInt("Please select one of the following to update \n1)First name "+
                "\n2)Last name \n3)address \n4)email \n5)mobile number\n6)Move this customer to a new branch ");
            
            if(choice == 1)
            {
               //sets a new first name based on user input
                cust.setFName(Input.inputOption("Enter new first name"));
                //sets return value to true
                update = true;
                //breaks this loop
                selected = false;
            }
            else if(choice == 2)
            {
                //sets a new last name based on user input
                cust.setLName(Input.inputOption("Enter new last name"));
                //sets return value to true
                update = true;
                //breaks this loop
                selected = false;
            }
            else if(choice == 3)
            {
                //sets a new address based on user input
                cust.setAddress(Input.inputOption("Enter new address"));
                //sets return value to true
                update = true;
                //breaks this loop
                selected = false;
            }
            else if(choice == 4)
            {
                //sets a new email based on user input
                cust.setEmail(Input.inputOption("Enter new email address"));
                //sets return value to true
                update = true;
                //breaks this loop
                selected = false;
            }
            else if(choice == 5)
            {
                //sets a new mobile based on user input
                cust.setMobile(Input.convertToNumberLong("Enter new mobile number"));
                //sets return value to true
                update = true;
                //breaks this loop
                selected = false;
            }
            else if(choice == 6)
            {
                //sets a branch number
                int branchNo = Input.convertToNumberInt("Please enter a vaild branch no? ");
                //checks is the branch no inpur is valid
                boolean check = BranchMethods.vaildBranch(brModel, branchNo);
                if (!check)
                {
                    //if invalid branch
                    System.err.println("This is not a valid branch plase check the branch no.");
                }
                else
                {
                    //if valid sets the cust to the new branch
                    cust.setBranchNo(branchNo);
                    //sets return value to true
                    update = true;
                }
                //breaks loop
                selected = false;
                        
            }
            else
            {
                //invalid input reruns this loop
                System.out.println("Invalid selection");
            }
        } //close while
        
        //if the update boolean is true
        if(update)
        {
            //try to connect to the database
            try
            {   
                Connection conn = DBConn.getInstance().getConnection();
                //create an instance of the CustomerDBLink class passing in a connection
                CustomerDBLink cdl = new CustomerDBLink(conn);
                /*
                   sets update to the value of the method update cust from the CustomerDBLink class
                   This will try to update  to the database and if sucessiful it will return true
                        */
                update = cdl.updateCust(cust);
                
            } 
            //this handels the sql errors
            catch (SQLException ex) 
            {
                System.err.println("An error has acured and this may not be added to the database");
            }
        }
        //returns if update was sucessiful or not
        return update;
        
    }//close selectCustCol
    
    //sets a custoemrs stock and purchases
    
    public static void custPotfolio(Model model)
    {
        //gets the lists from the model class
        List<Customer> cust = model.getCustomer();
        List<Purchase> pur = model.getPur();
        List<Sale> sas = model.getSales();
        List<Stock> sto = model.getStock();
        List<CustStock> cs = model.getCustStock();
        
        //loops through each customer
        for(int i = 0; i<cust.size(); i++)
        {
            ArrayList<Purchase> pu = new ArrayList<Purchase>();
            //for each purchase
            for(Purchase p : pur)
            {
                //if the purchase matches this customer add the purchase to the Arraylist
                if(p.getCustomerNo() == cust.get(i).getCustNo())
                {
                    //System.out.println("added");
                    pu.add(p);
                }
            }
            //adds the array list to this customer
            cust.get(i).setPur(pu);
            
            ArrayList<Sale> sal = new ArrayList<Sale>();
            for(Sale temp : sas)
            {
             //if the purchase matches this customer add the purchase to the Arraylist
                if(temp.getCustomerNo() == cust.get(i).getCustNo())
                {
                    //System.out.println("added");
                    sal.add(temp);
                }
            }
            //adds the array list to this customer
            cust.get(i).setSale(sal);
            
            ArrayList<Stock> s = new ArrayList<Stock>();
            
            //loops through each customer
            for(CustStock c : cs)
            {
                //checks for a match with the customer and the custStock table
                if(c.getCustomerNo() == cust.get(i).getCustNo())
                {
                    c.getStockId();
                    c.getQty();
                    //checks each stock for the afave id and adds it to the stock array list
                    for(Stock stock : sto)
                    {
                        if(c.getStockId() == stock.getStockId())
                        {
                            stock.setQty(c.getQty());
                            s.add(stock);
                        }
                    }
                }
            }//close for
            
            //adds the array list s to this customer
            cust.get(i).setSt(s);
        }//close for
        
    }//close custPotfolio

}//close CustMethods
