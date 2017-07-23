/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainClasses;

import java.util.Date;
import java.util.List;

/**
 *
 * @author johnkenny
 */
public class Branch 
{
    /*
        This is the varibles that a Branch object will have they are set to
    private so they can only be access for each object using the get and set methods
    
    */
    private String branchName;
    private String branchAddress;
    //This will have its own class --private String branchManager;
    private Date branchCreateDate;
    private String branchOpeningHours;
    private Date branchOpenDate;
    private int branchNo;
    private long phoneNo;
    private List<Customer> cust;
    private Manager man;
    //Not needed in database version private static int autoIncrementSim = 1;

    
    /*This is the default constructor to set up a branch
    This and all other constructors will get the time the branch was created
    using the LocalDateTime now method imported from the LocalDateTime class
    It will also asign a branch number to the autoIncrementSim value
    The autoIncrementSim value will then increment by one so the next branch created
    is 1 greated that the previous branch(This will be done by the database in the final version.
    */
    
    public Branch()
    {
        //branchOpenDate = LocalDateTime.now();
        //branchNo = autoIncrementSim;
        //autoIncrementSim ++;

    }
    
    /* This constructor is the same as the previous constuctor but it allows the user to pass in values
    (parameters) when calling this method to set all the varaibles in a new branch object
    */
    public Branch(String branchName, String branchAddress, String branchOpeningHours, long phoneNo)
    {
        this.branchName =branchName;
        this.branchAddress = branchAddress;
        this.phoneNo = phoneNo;
        this.branchOpeningHours = branchOpeningHours;
        //branchOpenDate = LocalDateTime.now();
        //branchNo = autoIncrementSim;
        //autoIncrementSim ++;
    }

    public Branch(String branchName, String branchAddress, Date branchCreateDate, String branchOpeningHours, int branchNo, long phoneNo) {
        this.branchName = branchName;
        this.branchAddress = branchAddress;
        this.branchCreateDate = branchCreateDate;
        this.branchOpeningHours = branchOpeningHours;
        this.branchNo = branchNo;
        this.phoneNo = phoneNo;
    }

     public Branch(String branchName, String branchAddress,  String branchOpeningHours, int branchNo, Date branchCreateDate)
    {
        this.branchName =branchName;
        this.branchAddress = branchAddress;
        this.branchOpeningHours = branchOpeningHours;
        this.branchNo = branchNo;
        this.branchCreateDate = branchCreateDate;
        //branchNo = autoIncrementSim;
        //autoIncrementSim ++;
    }//last constructor
    /*
    Below are the the get methods for each variable of a branch
    these allow us to get the value of an item from a branch object. This
    will be retuned when this method is called
    */
    public String getBranchName() 
    {
        return branchName;
    }

    public int getBranchNo() 
    {
        return branchNo;
    }

    public String getBranchAddress() 
    {
        return branchAddress;
    }

    public Date getBranchCreateDate() 
    {
        return branchCreateDate;
    }

    public String getBranchOpeningHours() 
    {
        return branchOpeningHours;
    }
    
    public long getPhoneNo() 
    {
        return phoneNo;
    }

    public List<Customer> getCust() 
    {
        return cust;
    }
    

    public Manager getMan() 
    {
        return man;
    }
    
    
    /*
    These are the seter methods of an branch object.
    Please not there is no set method for the branchCreateDate and the 
    branchNo as the user will not be able to change these once created
    */

    public void setBranchName(String branchName)
    {
        this.branchName = branchName;
    }
 

    public void setBranchAddress(String branchAddress) 
    {
        this.branchAddress = branchAddress;
    }

    public void setBranchOpeningHours(String branchOpeningHours) 
    {
        this.branchOpeningHours = branchOpeningHours;
    }

    public void setBranchCreateDate(Date branchCreateDate) 
    {
        this.branchCreateDate = branchCreateDate;
    }

    public void setBranchNo(int branchNo) 
    {
        this.branchNo = branchNo;
    }

    public void setPhoneNo(long phoneNo) 
    {
        this.phoneNo = phoneNo;
    }

    public void setCust(List<Customer> cust) 
    {
        this.cust = cust;
    }

    public void setMan(Manager man) {
        this.man = man;
    }
    
    public void addCust(Customer c)
    {
        this.cust.add(c);
    }
    /*
    This method is used to return all the values a branch object may have
    */
    
    @Override
    public String toString() 
    {
        return "Branch Name: " + branchName + " Branch No: " + branchNo + 
                " Address: " + branchAddress + " Phone No: " + phoneNo +
                " Create Date: " + branchCreateDate + " Opening Hours: " + branchOpeningHours;
    }
}
