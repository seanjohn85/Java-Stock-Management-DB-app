/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainClasses;

import java.util.Date;
import java.util.List;

/**
 * @author John Kenny n00145905
 */

public class Customer 
{
    /*
    Customer object variables
    */
    private int custNo ;
    private int branchNo ;
    private String fName;
    private String lName;
    private String gender;
    private long mobile;
    private String address;
    private String email;
    private Date accountOpenDate;
    
    //Customer list objects form other classes
    
    private List<Stock> st;
    private List<Purchase> pur;
    private List<Sale> sale;
    
    
    ///Constructors
    
    public Customer()
    {
        
    }
    
    //adds a customer with all the paramaters
    public Customer(int custNo, int branchNo, String fName, String lName, String gender, String address, long mobile, String email, Date accountOpenDate) 
    {
        this.custNo = custNo;
        this.branchNo = branchNo;
        this.fName = fName;
        this.lName = lName;
        this.gender = gender;
        this.address = address;
        this.mobile = mobile;
        this.email = email;
        this.accountOpenDate = accountOpenDate;
    }
    
    //used to load a customer into the database as the custNo is created by the database
    
    public Customer(int branchNo, String fName, String lName, String gender, String address, long mobile, String email) 
    {
        
        this.branchNo = branchNo;
        this.fName = fName;
        this.lName = lName;
        this.gender = gender;
        this.address = address;
        this.mobile = mobile;
        this.email = email;
        
    }
    
    public Customer(String fName, String lName, String address, long mobile, String email) 
    {
        this.fName = fName;
        this.lName = lName;
        this.address = address;
        this.mobile = mobile;
        this.email = email;
    }//last constructor
   
    //set methods
    
    //used to set values of a customer
    
    public void setFName(String name)
    {
        fName = name;
    }
    
    public void setLName(String name)
    {
        lName = name;
    }
    
    public void setAddress(String home)
    {
        address = home;
    }
    
    public void setMobile(long mob)
    {
        mobile = mob;
    }
    public void setEmail(String em)
    {
        email = em;
    }

    public void setCustNo(int custNo) 
    {
        this.custNo = custNo;
    }

    public void setBranchNo(int branchNo) 
    {
        this.branchNo = branchNo;
    }

    public void setGender(String gender) 
    {
        this.gender = gender;
    }

    public void setAccountOpenDate(Date accountOpenDate) 
    {
        this.accountOpenDate = accountOpenDate;
    }
    
    //used to get list objects of this class
    
    public void setSt(List<Stock> st) 
    {
        this.st = st;
    }

    public void setPur(List<Purchase> pur) 
    {
        this.pur = pur;
    }
    
    public void setSale(List<Sale> sale) 
    {
        this.sale = sale;
    }
    
    //get methods
    
    //used to get varaibles from a customer
    
    public String getFName()
    {
        return fName;
    }
    
    public String getLName()
    {
        return lName;
    }
    
    public String getAddress()
    {
        return address;
    }
    
    public long getMobile()
    {
        return mobile;
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public int getCustNo()
    {
        return custNo;
    }

    public int getBranchNo() 
    {
        return branchNo;
    }

    public String getGender() 
    {
        return gender;
    }

    public Date getAccountOpenDate() 
    {
        return accountOpenDate;
    }

      //used to set list objects of the customer
    
    public List<Stock> getSt() 
    {
        return st;
    }

    public List<Purchase> getPur() 
    {
        return pur;
    }
    public List<Sale> getSale() 
    {
        return sale;
    }
    
    //Used to add one item to a list object of an object of this class
    
    public void addPur(Purchase pur) 
    {
        this.pur.add(pur);
    }
    public void addStock(Stock st) 
    {
        this.st.add(st);
    }
    public void addSales(Sale sale) 
    {
        this.sale.add(sale);
    }
    
    //print methods
    
    public void printCustomer()
    {
        System.out.println("Name: " + fName + " " + lName +
                "\n" + "address: " + address +
                "\n" + "mobile: " + mobile +
                "\n" + "email: " + email + 
                "\n"  + "Customer account number: " + custNo);
        
    } 

    /*
        return the customer details
    */

    @Override
    public String toString() 
    {
        return "Customer Number: " + custNo + ", Branch Number: " + branchNo + ", Name" + fName + " " + lName + ", Gender " + gender + ", Mobile no" + mobile + ", Address " + address + ", Email " + email + ", Account Open " + accountOpenDate;
    }
    
    public String customer() 
    {
        return "Customer Number: " + custNo +  ", Name " + fName + " " + lName + ", Gender " + gender + ", Mobile no" + mobile + ", Address " + address + ", Email " + email + ", Account Open " + accountOpenDate;
    }
    
    public String detail()
    {
        //prints name in correct format first letter uppercase(I also use library to do this in other classes)
        String name = (fName.substring(0,1).toUpperCase() + fName.substring(1).toLowerCase()) +
                " " + (lName.substring(0,1).toUpperCase() + lName.substring(1).toLowerCase());
        return "Customer Number: " + custNo + ", Name " + name;
    }
    
}//close Customer
