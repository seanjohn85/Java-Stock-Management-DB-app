/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainClasses;

//imports used by this class
import DataBaseManager.BranchDBLink;
import DataBaseManager.CustStockDBLink;
import DataBaseManager.CustomerDBLink;
import DataBaseManager.DBConn;
import DataBaseManager.ManagerDBLink;
import DataBaseManager.PurchaseDBLink;
import DataBaseManager.SalesDBLink;
import DataBaseManager.StockDBLink;
import DataBaseManager.FluctuationDBLink;
import DataBaseManager.StaffDBLink;
import MainClasses.Fluctuation;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author johnkenny
 */
public class Model 
{
    //creates a static instance of this class
    private static Model instance = null;
    
    //allows access to the instance of this class from other classes
    //before the logger was added to this instance I had lots of errors
    
    public static Model getInstance() 
    {
        if (instance == null) 
        {
            try 
            {
                instance = new Model();
            } 
            catch (ParseException ex) 
            {
                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return instance;
    }


    //creates a List items of used by this class
    
    //the list items match the database tables
    
    private List<Branch> branches;
    private List<Manager> managers;
    private List<Customer> cust;
    private List<Stock> stock;
    private List<CustStock> cuSt;
    private List<Purchase> p;
    private List<Sale> s;
    private List<Employee> e;
  

    /*
        This method uses the dblink classes for each object type and populates arrayLists to match
    the database
    */
    
    private Model() throws ParseException  
    {
       //runs a method to get the current stock prices 
        createFlu();
        //populates all the above arraylsits with a copy of the information on the database tables
        
         try
        {   
            //sets a coonection using the Dbconnection class get connection method
            Connection conn = DBConn.getInstance().getConnection();
            
            //fills the list with a match of the branches on the database
            BranchDBLink bdl = new BranchDBLink(conn);
            this.branches = bdl.getBranches();
            
            //fills the list with a match of the managers on the database
            ManagerDBLink mdl = new ManagerDBLink(conn);
            this.managers = mdl.getManager();
            
            //fills the list with a match of the customers on the database
            CustomerDBLink cdl = new CustomerDBLink(conn);
            this.cust = cdl.getCust();
            
             //fills the list with a match of the customers on the database
            StockDBLink sdl = new StockDBLink(conn);
            this.stock = sdl.getStock();
            
            //fills the list with a match of the customers on the database
            CustStockDBLink csdl = new CustStockDBLink(conn);
            this.cuSt = csdl.getCustStock();
            
            //fills the list with a match of the customers on the database
            PurchaseDBLink pdl = new PurchaseDBLink(conn);
            this.p = pdl.getPurchase();
            //fills the list with a match of the customers on the database
            SalesDBLink sadl = new SalesDBLink(conn);
            this.s = sadl.getSales();
            
            
            //fills the list with a match of the customers on the database
            StaffDBLink stdl = new StaffDBLink(conn);
            this.e = stdl.getStaff();
            
            //System.err.println(e.get(0).getClass().getName().substring(12));
            //System.err.println(e.get(1).getClass().getName().substring(12));
            //System.err.println(e.get(1).getBranchNo());
          
            //closes the database connection uses the close method
            DBConn.getInstance().close();
            
        } 
        catch (SQLException ex) 
        {
            System.err.println(ex);
        }
         
    }//closes model constructor
    
    //Get method to get the list of Branch objects from outside this class
    public List<Branch> getBranch()
    {
        return new ArrayList<Branch>(this.branches);
    }
    
    //Get method to get the list of manager objects from outside this class
    public List<Manager> getManager()
    {
        return new ArrayList<Manager>(this.managers);
    }
    
    //Get method to get the list of employee objects from outside this class
    public List<Employee> getEmployee()
    {
        return new ArrayList<Employee>(this.e);
    }
    
    //Get method to get the list of customer objects from outside this class
    public List<Customer> getCustomer()
    {
        return new ArrayList<Customer>(this.cust);
    }
    
    //Get method to get the list of stock objects from outside this class
    public List<Stock> getStock()
    {
        return new ArrayList<Stock>(this.stock);
    }
    
    //Get method to get the list of purchase objects from outside this class
    public List<Purchase> getPur()
    {
        return new ArrayList<Purchase>(this.p);
    }
    
        //Get method to get the list of sale objects from outside this class
    public List<Sale> getSales()
    {
        return new ArrayList<Sale>(this.s);
    }
    
    //get method to get the stock  and customer relation from the custstock table
    public List<CustStock> getCustStock()
    {
        return new ArrayList<CustStock>(this.cuSt);
    }
    
    //Add method allows other classes to add a Branch object to the list on this class
    public void addBranch(Branch br) 
    {
       if(br != null)
       {
            this.branches.add(br);
       }
    }
    
    //This allows customers to be added to the list in this class
    public void addCust(Customer c) 
    {
       if(c != null)
       {
            this.cust.add(c);
       }
    }
    
        
    //This allows stock to be added to the list in this class
    public void addStock(Stock s) 
    {
       if(s != null)
       {
            this.stock.add(s);
       }
    }
    
    //This allows customers stock to be added to the list in this class
    public void addCustSt(CustStock cs) 
    {
       if(cs != null)
       {
            this.cuSt.add(cs);
       }
    }
    //This allows employees to be added to the list in this class
    public void addEmployees(Employee emp) 
    {
       if(emp!= null)
       {
            this.e.add(emp);
       }
    }
    
    //This allows a user to remove a branch from the list in this class
    public boolean removeBranch(Branch br) 
    {
        return this.branches.remove(br);
    }
    
    //This allows a user to remove a customer from the list in this class
    public boolean removeCustomer(Customer c) 
    {
        return this.cust.remove(c);
    }
    
    //This allows a user to remove a manager from the list in this class
    public boolean removeManager(Manager m) 
    {
        return this.managers.remove(m);
    }
    
    //This allows a user to remove an employee from the list in this class
    public boolean removeEmp(Employee emp) 
    {
        return this.e.remove(emp);
    }
    
    //This a method which allows a user to pass an int parameter and find a branch
    
    public Branch branchFinder(int branchNo)
    {
        //sets a returnType to null
        Branch returnType = null;
        //This loop, loops threw each Branch object in the list 
        for (Branch temp : branches)
        {
            //This repersets the current branch in the loop
            temp.getBranchNo();
            /*If the temp banch no matches the int parameter the return type is 
            changed to that branch
            */
            if(temp.getBranchNo() == branchNo)
            {
                returnType = temp;
            }
        }
        return returnType;
        
    }//close branchfinder   
      
    
    //This a method which allows a user to pass an int parameter and find a customer
    
    public Customer custFinder(int custNo)
    {
        //sets a returnType to null
        Customer returnType = null;
        //This loop, loops threw each customer object in the list 
        for (Customer temp : cust)
        {
            //This repersets the current customer in the loop
            temp.getCustNo();
            /*If the temp customer no matches the int parameter the return type is 
            changed to that customer
            */
            if(temp.getCustNo() == custNo)
            {
                returnType = temp;
            }
        }
        return returnType;
        
    }//close custFinder
    
    //this allows the user to find a stock
    
    public Stock stockFinder(int stockNo, String stockCode)
    {
        //sets a returnType to null
        Stock returnType = null;
        
        //This loop, loops threw each stock object in the list 
    
        for(int i = 0; i < stock.size(); i++)
        {
            /*if the paramter stockNo or the stockcode matches a stock in the system if
            we get a match the result is returned*/
            
            if(stock.get(i).getStockId() == stockNo || stock.get(i).getStockCode().equalsIgnoreCase(stockCode))
            {
                //System.out.println(stock.get(i).getStockCode());
                return stock.get(i);
            }
        }
        return returnType;
        
    }//close stockFinder
    
    /*
    This method is used to simulate the Fluctuation on the stock market
    it goes through all the stock and creates an increate or decrease of
    the stock price (max 20%) and records it in the Fluctuation table
    while updating the current stock price
    */
    
    public void createFlu() throws ParseException
    {
        List<Fluctuation> flu = null;
        List<Stock> stock = null;
        try
        {   
            //sets a coonection using the Dbconnection class get connection method
            Connection conn = DBConn.getInstance().getConnection();
            
             //fills the list with a match of the stock on the database
            StockDBLink sdl = new StockDBLink(conn);
            stock = sdl.getStock();

            //closes the database connection uses the close method
            DBConn.getInstance().close();  
        } 
        catch (SQLException ex) 
        {
            System.err.println(ex);
        }
        System.out.println("Loading  current stock prices \n Please wait\n");
        
        //Loops through all the stock in the system
        for(Stock s : stock)
        {
            int stockId = s.getStockId();
            double price = s.getCurrentPrice();
            
            //gerenates a random number max 20 to repersent the Fluctuation amount
            Random generator = new Random();
            int percent = generator.nextInt(20);
            //System.out.println(percent);

            //random number to see if the  Fluctuation amount is positive or negative
            int i = generator.nextInt(2);
            //System.out.println(i); 
            
            //gets one percent of the current stock value
            double amount = price / 100;
            //sets amount to the percent 
            amount = amount * percent;
            //System.out.println(amount);
            // if its a decrease set amout and precent to minus
            if(i == 1)
            {
                amount = -amount;
                percent = -percent;
                
            }
            //update the stock price 
            price = price + amount;
            //System.out.println(amount);

            //System.out.println(price);
            
            //formats and rounds decimal  
            DecimalFormat df = new DecimalFormat("#.##");      
            price = Double.valueOf(df.format(price));
            
            //System.out.println(price);  ****used for testing****
            
            //updates ths database 
            try
                {   
                    //Creates a connection using the getConnection method from the DBConn class
                    Connection conn = DBConn.getInstance().getConnection();
                    //uses the connection in the FluctuationDBLink class
                    FluctuationDBLink fbl = new FluctuationDBLink(conn);
                    
                    /*
                     undates the database with a fluctuation object
                     */
                    Fluctuation f = fbl.fluctuationGen(stockId,  amount, percent, price);
                    //flu.add(f);

                } 
                //handles sql errors
                catch (SQLException ex) 
                {
                    System.err.println("An error has acured and this may not be added to the database");
                }
           
        }//close for loop
        
    }// close createFlu
    
}//close Model class
