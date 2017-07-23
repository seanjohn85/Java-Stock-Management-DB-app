/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methods;

import MainClasses.Branch;
import MainClasses.Customer;
import MainClasses.Purchase;
import MainClasses.Stock;
import MainClasses.CustStock;
import MainClasses.Sale;
import MainClasses.Transaction;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import static methods.MenuOptions.brModel;

/**
 *
 * @author johnkenny
 * This is the menu to be used by a branch to impact their customers
 * 
 */

public class BranchMenuMethods
{
    //This method logs to user into a branch
    
    public static void branchLogin(int branchNo) throws ParseException, SQLException, FileNotFoundException, IOException
    {
        //int branchNo = 0;
        BranchMethods.setBranchCust(brModel);
        String branchName = "";
        boolean found = false;
        Branch current = null;
        
        //This loop finds a branch and asks the use if they would like to enter this branch
        
        while(!found)
        {
            if(branchNo == 0)
            {      
                System.out.println("Please enter a valid branch Number");
                branchNo =Input.convertToNumberInt();
                boolean temp = BranchMethods.vaildBranch(brModel, branchNo);
                if(temp)
                {
                    //Branch check = brModel.getBranch().get(branchNo);
                    current = brModel.branchFinder(branchNo);
                    branchName = current.getBranchName();
                    System.out.println("Is " + branchName + " the correct branch \n");
                    found = Input.yesNo();
                }
                
            }
            else
            {
                boolean temp = BranchMethods.vaildBranch(brModel, branchNo);
 
                //Branch check = brModel.getBranch().get(branchNo);
                current = brModel.branchFinder(branchNo);
                branchName = current.getBranchName();
                found = true;

            }
        }//close while
        
        //System.out.println(branchNo);
        
        int selected = 0;
        
        //this is the loop menu when the user enters a branch
        
        while(selected != 4)
        {
            Prompts.branchStaff(branchName);
            selected = Input.convertToNumberInt();
            //System.out.println(i);
            //gets all the customers from this branch and sets it to a list
            List<Customer> cust = current.getCust();
            switch(selected)
            {
                case 1:
                     /*
                     loops through this Branch and prints its customers
                      */
                    for (Customer cu : cust) 
                    {
                        System.out.println(cu.customer() + "\n");
                        
                    }
                    System.out.println("--------------------------------------");
                    break;
                case 2:
                    /*
                    This adds a customer to this branch
                    */
                    Customer cu = CustMethods.create(branchNo);
                    
                    brModel.addCust(cu);
                    current.addCust(cu);
                  
                    System.out.println("--------------------------------------");
                    break;
                case 3:
                    //opens stock manager
                    stockManager(cust);
                    break;
                case 4:
                    //works EXIT System.out.println("You are here exit this menu" + selected);
                    break;
                default:
                    System.out.println("You have not entered a valid menu option \n Please enter a valid option \n");      
            
            }//close switch
            
        }//close while
        
    } //close branchLogin
    
    //This is the stock manager menu allows the user to enter a customers potofolio
    
    public static void stockManager(List<Customer> cust) throws SQLException, ParseException, FileNotFoundException, IOException
    {
        int customer;
        boolean exit = true;
        Customer loadCust = null;
        
        //looks in until exit is true
        while(exit)
        {
            //gets a customer number
            customer = Input.convertToNumberInt(Prompts.client());
            if(customer == 0)
            {
                //ends loop
                exit = false;
            }
            else
            {
                //checks each customer in this branch
                for (Customer cu : cust) 
                {
                    //if the customer no is the same as the entered customer
                    if(cu.getCustNo() == customer)
                    {
                        //change loadCust to this customer
                        loadCust = cu;
                        //System.out.println("match");
                        //ents this loop
                        break;
                    }   
                }
                //if the loadcust 
                if(loadCust ==  null)
                {
                    System.out.println("This is an invalid customer");
                }
                else
                {
                    //enters the stockmenu with this customer
                    stockMenu(loadCust);
                    //exits this loop
                    exit = false;
                }
            }
        }//close while loop  
    }
    
    //this is a menu to impact or view a customers stock an purchases
    
    public static void stockMenu(Customer cust) throws SQLException, ParseException, FileNotFoundException, IOException
    {
        int selected = 0;
        //locks in this loop until valid option
        while(selected != 7)
        {
            selected = Input.convertToNumberInt(Prompts.stock(cust.detail()));
            switch(selected)
            {
                case 1:
                    //loads this customers stock
                    viewCustStock(cust);
                    break;
                case 2:
                    //loads this customers orders
                    viewCustOrderHistory(cust);
                    break;
                case 3:
                    //loads sales
                    viewCustSales(cust);
                    break;
                case 4:
                    //adds stock to a customer
                    addStock(cust);
                    selected = 6;
                    break;
                case 5:
                    //adds stock to a customer
                    sellStock(cust);
                    selected = 6;
                    break;
                case 6:
                    FileReadWrite.custReport(cust);
                    break;
                case 7:
                    //closes this loop
                    break;
                default:
                    System.out.println("You have not entered a valid menu option \n Please enter a valid option \n");      
            }
        }//ends while
               
    }//stockMenu

    //if the customer has stock print each ssock they have
    
    private static void viewCustStock(Customer cust) 
    {
        //gets list of stock this customer has
        List<Stock> st = cust.getSt();
        //if the customer has no stock
        if(st.isEmpty())
        {
            System.err.println("This customer currently has no stock");
        }
        //else print the stock
        else
        {
            //for each stock the customer has print it
            for(Stock s : st)
            {
                //uses the stock to string method to print
                System.out.println(s.toString() + "\n");
            }
        }
        
    }//close viewCustStock
    
    //if the customer has any purchases print them
    
    private static void viewCustOrderHistory(Customer cust) 
    {
        //creates a list of purchases
        List<Purchase> pur = cust.getPur();
        //if they have none
        if(pur.isEmpty())
        {
            System.out.println("This customer currently has no stock \n");
        }
        else
        {
            //print all this customers purchases
            for(Transaction p : pur)
            {
                int id = p.getCustSockId();
                //gets all stock from the list in the model
                List<Stock> st = brModel.getStock();
                String name = "";
                //searches for the stock name to print with the purchase
                for(Stock s : st)
                {
                    //when the stock is found get the name print it and end this loop
                    if(s.getStockId() == id)
                    {
                        name = s.getStockName();
                        break;
                    }
                }
                System.out.println(p.details() + name + "\n");
            }
        }//close else
        
    }//close viewCustOrderHistory
    
    //This method calls other methods to add stock and a new purchase to a customer

    private static void addStock(Customer cust) throws SQLException, ParseException
    {
        int stockNo = 0;
        
        //alows the user input the stock they would like to add
        String stockName = Input.inputOption("Please enter the stock number or stock code \nyou would like to add to " + cust.detail() + "\n");
       //checks if stock exsists
        boolean check = Input.isNumber(stockName);
        //if it exsits
        if(check)
        {
            //sets the stock number 
            stockNo = Input.convertToNum(stockName);
        }
        //checks if the stock is valid using the stock list in the model
        Stock allStock = brModel.stockFinder(stockNo, stockName);
        //if the stock was found in the model
        if(allStock != null)
        {
            System.out.print("Would you like to sell " + allStock.stockDet() + " to " + cust.detail() + "\n");
            
            //if the user wants to add the stock 
            if(Input.yesNo())
            {
                //sets the shares
                int shares = shares();
                //if the customer has the stock this method finds it
                Stock temp = shareCheck(cust, allStock.getStockId());
                //System.out.println(temp.getQty());
                //sets new purchase
                makePurchase(cust, shares, allStock);
                
            }//close if
            
        }
        else
        {
            System.err.println("Invalid stock cannot assign to customer \n returning to previous menu");
        }
        
    }//close addStock
    
    //looks the max amount of shares to 100 (cant be negative)
                    
    public static int shares()
    {
        boolean endLoop = true;
        int shares = 0;
        
        //looks in loop until amount is valid 
        while(endLoop)
         {
             //gets a number input by user
            shares = Input.convertToNumberInt("How many shares would you like to add max 100");
            
            //checks if in shares amount is between 1 and 100
            
            if(shares > 0 && shares <= 100)
            {
                //exits this loop
                endLoop = false;
            }
            else
            {
                System.err.println("Invalid amount of shares");
            }
            
        }//close while
        
        //returns amout of shares to add
        return shares;
        
    }//closes shares
    
    //returns a stock if the customer has it 
    
    public static Stock shareCheck(Customer c, int stockNo)
    {
        //creates a empty stock return type
        Stock check = new Stock();
        
        //check.setStockId(0);
        List<Stock> s = c.getSt();
        
        //checks all this customers stocks
        for(int i =0; i<s.size(); i++)
        {
            //if the current stock id matches the stockNo parameter
            
            if(s.get(i).getStockId() == stockNo)
            {
                //used for testing System.out.println(s.get(i).getQty());
                
                //returns this stock breaks loop
                return s.get(i);
            }
        }
        //if null returns empty stock
        return check;
        
    }//close shareCheck
    
    //returns the price of a stock
    //updates the stock a customer has by changing the qty
    
    public static double custStockUpdate(Customer c, int stockNo, int qty)
    {
        //sets price to 0
        double price = 0.0;
        
        //creates a list of stock using the customer parameter
        List<Stock> s = c.getSt();
        for(int i =0; i<s.size(); i++)
        {
            //checks if the customer has the stock passed into this method
            if(s.get(i).getStockId() == stockNo)
            {
                //sets the new qty to the customers stock
                s.get(i).setQty(qty);
                
                //returns the price from the current stock and breaks the loop
                return s.get(i).getCurrentPrice();
            }
        }
        
        return price;
        
    }//close custStockUpdate
    
    //adds a purchase to a customer (the customer and purchase are passed into this method)
    
    public static void addPurchase(Customer cust, Purchase p)
    {
        //adds the purchase to this cust
        cust.addPur(p);
        System.out.println("Purchase added");
        
    }//close addPurchase
    
    //This method finds a stock and assigns it to a customer returning the price of a share
    
    public static double add2Portfolio(Customer cust, int id, int qty)
    {
        double price = 0;
        //gets all stock from the model class
        List <Stock> st = brModel.getStock();
        
        //loops trhrough all the stock
        for(Stock s : st)
        {
            //if the stock id matches the id paramater
            
            if(s.getStockId() == id)
            {
                //sets the qty of shares the customer owns
                s.setQty(qty);
                //adds this stock to this customer object
                cust.addStock(s);
                //bracks loop reurning the stock price
                return s.getCurrentPrice();

            }
            
        }//close for
        
        //returns the stock price
        return price;
        
    }//close add2Portfolio
    
    //displays sales made my a customer

    private static void viewCustSales(Customer cust) 
    {   
        //creates a list of sales
        List<Sale> sale = cust.getSale();
        //if they have none
        if(sale.isEmpty())
        {
            System.out.println("This customer currently has no sales \n");
        }
        else
        {
            //print all this customers sales
            for(Transaction s : sale)
            {
                int id = s.getCustSockId();
                //gets all stock from the list in the model
                List<Stock> st = brModel.getStock();
                String name = "";
                //searches for the stock name to print with the sale
                for(Stock sto : st)
                {
                    //when the stock is found get the name print it and end this loop
                    if(sto.getStockId() == id)
                    {
                        name = sto.getStockName();
                        break;
                    }
                }
                System.out.println(s.details() + " " + name + "\n");
            }
        }//close else
        
    }//close viewCustSales

    //allows a customer to sell stock
    
    private static void sellStock(Customer cust) throws ParseException 
    {
        int stockNo = 0;
        
        //alows the user input the stock they would like to add
        String stockName = Input.inputOption("Please enter the stock number or stock code \nyou would like to sell from " + cust.detail() + "\n");
       //checks if stock exsists
        boolean check = Input.isNumber(stockName);
        //if it exsits
        if(check)
        {
            //sets the stock number 
            stockNo = Input.convertToNum(stockName);
        }
        //seacrhs for the stock in the model
        Stock allStock = brModel.stockFinder(stockNo, stockName);
        
        //ensures the customer has shares of this stock to sell
        Stock st= shareCheck(cust, allStock.getStockId());
        
        //if the customer has shares
        if(st.getQty()> 0)
        {
            
            System.out.println("The selected stock is " + st.getStockName() +
                    "/n The current price is " + st.getCurrentPrice());
            boolean continued = Input.yesNo();
            if(continued)
            {
                //asks the user how many shares they want to sell
                int qty = Input.convertToNumberInt("The selected stock is " + st.getStockName() +
                    "\nThis customer has " +st.getQty() + "shares of " + st.getStockName() + "\n" +
                    "Please select how many shares you would like to sell");
                //if the amount they want to less matches their share qty
                if(qty <= st.getQty())
                {
                    //used to  set the customers remaining shares
                    int remaining = st.getQty() - qty;
                    
                    int csId  = StockManagerMethods.updateStock(st.getStockId(), remaining, cust.getCustNo());
                    //gets the price of the stock
                    double price = custStockUpdate(cust, st.getStockId(), remaining);
                    //System.out.println(csId + "csId");
                    //System.out.println(qty +"qty");
                    //adds a sale to the database
                    Sale s = StockManagerMethods.sale(cust.getCustNo(), st.getStockId(), csId, qty,   price, cust.detail());
                    //adds this sale to this customer
                    addSale(cust, s); 
                    
                }
                //if the customer is trying to sell more shares than they own
                else
                {
                    System.out.println("The max amount of shares of " + st.getStockName() +
                            " is " + st.getQty());
                }
                
            }
            
        }//close if(continued)
        //if the customer does not have shares of this stock
        else
        {
            System.out.println("The customer has no shares of this stock \n");
        }
        
    }//close sellStock
    
    //adds a sale to this customer object
    
    public static void addSale(Customer cust, Sale s)
    {
        //adds the purchase to this cust
        cust.addSales(s);
        System.out.println("Sale complete");
        
    }//close addSale
    
    //used to create a customer purchase
    
    public static void makePurchase(Customer cust,int shares, Stock allStock) throws ParseException
    {
        Stock temp = shareCheck(cust, allStock.getStockId());
        //System.out.println(temp.getQty());
        //sets new purchase
        Purchase p = null;
        //
        if (temp.getStockId() != 0) 
        {
            System.out.println(temp.getStockName());
            //System.out.println("here" + temp.getQty());
            int qty = shares + temp.getQty();
            //System.out.println(qty);
            //updates the database custstock table
            int csId = StockManagerMethods.updateStock(allStock.getStockId(), qty, cust.getCustNo());
            //gets the price of the stock
            double price = custStockUpdate(cust, allStock.getStockId(), shares + temp.getQty());
            //adds a purchase to the database
            p = StockManagerMethods.pur(cust.getCustNo(), allStock.getStockId(), csId, shares, price, cust.detail());
            //adds this purchase to this customer
            addPurchase(cust, p);
        } 
        else 
        {
            //adds a stock to the custstock table
            CustStock cs = StockManagerMethods.addStock(allStock.getStockId(), shares, cust.getCustNo());
            //sets the price
            double price = add2Portfolio(cust, cs.getStockId(), shares);
            //adds a purchase to the database
            p = StockManagerMethods.pur(cust.getCustNo(), allStock.getStockId(), cs.getCustStockId(), shares, price, cust.detail());
            //adds the purchase to this customer
            addPurchase(cust, p);
        }
        
    }
    
    //used to generate a sale my the file input class ***not user input file io*******
    
    public static void makeSale(Customer cust,int qty, Stock allStock) throws ParseException
    {
        //gets a stock object returned from the shareCheck method
        Stock st= shareCheck(cust, allStock.getStockId());
        
        //checks if the customer has shares of this stock
        if(st.getQty()> 0)
        {
            //if the amount they want to less matches their share qty
            if (qty <= st.getQty()) 
            {
                //changes the remaining amout of shares of this stock
                int remaining = st.getQty() - qty;

                int csId = StockManagerMethods.updateStock(st.getStockId(), remaining, cust.getCustNo());
                //gets the price of the stock
                double price = custStockUpdate(cust, st.getStockId(), remaining);
                System.out.println(csId + "csId");
                System.out.println(qty + "qty");
                //adds a sale to the database

                Sale s = StockManagerMethods.sale(cust.getCustNo(), st.getStockId(), csId, qty, price, cust.detail());
                //adds this sale to this customer object
                addSale(cust, s);
            } 
            //if the customer is trying to sell more shares than they own 
            else 
            {
                System.out.println("The max amount of shares of " + st.getStockName()
                        + " is " + st.getQty());
            }
            
        }
        //if the customer does not have shares of this stock
        else
        {
            System.out.println("The customer has no shares of this stock \n");
        }
        
    } //close makeSale

    //finds branch and returns it
    
    public static Branch branchName(int branchNo)
    {
        Branch b = brModel.branchFinder(branchNo);
        return b;
    }//close branchName
    
}//close class
