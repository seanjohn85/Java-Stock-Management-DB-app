/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainClasses;

import java.util.Objects;
import java.util.Random;
import methods.BranchMenuMethods;


/**
 *
 * @author johnkenny
 * 
 */
//super class of manager BranchStaff and HeadOfficeStaff
public class Employee implements StaffProfile
{
    //varibles of this object
    private AccountTypes ac;
    private int staffNo;
    private int acccoutId;
    private String name,
            userName,
            password;

    //constructor methods
    public Employee(int staffNo, int acccoutId, String name,  String password, AccountTypes ac) 
    {
        this.staffNo = staffNo;
        this.acccoutId = acccoutId;
        this.name = name;
        this.ac = ac;
        this.password = password;
    }
    
    public Employee(int staffNo) 
    {
        this.staffNo = staffNo;
        
    }
    
    public Employee(int acccoutId, String name,  String password, AccountTypes ac) 
    {
        this.acccoutId = acccoutId;
        this.name = name;
        this.ac = ac;
        this.password = password;
    }
    
   //getters and setters/////////

    public int getStaffNo() 
    {
        return staffNo;
    }

    public void setStaffNo(int staffNo) 
    {
        this.staffNo = staffNo;
    }

    public int getAcccoutId() {
        return acccoutId;
    }

    public void setAcccoutId(int acccoutId) {
        this.acccoutId = acccoutId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getPassword() 
    {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String accountType()
    {
        return ac.name();
    }

    public AccountTypes getAc() {
        return ac;
    }
    
    public int getBranchNo() 
    {
        return 0;
    }
    
    ////////////////////////////////////////
    
    //creates userName automatically
    
    public String setUser(String user, int staffNo)
    {  
        try
        {
            //gets the first name of the user and adds the staff no to it to create a new user
            return (user.substring(0, user.indexOf(' ')) + staffNo);
        }
        catch(IndexOutOfBoundsException e)
        {
            return user;
        }
    }
    
    //gets the branch name using the branch number
    public String branchName(int branchNo)
    {
        Branch b = BranchMenuMethods.branchName(branchNo);
        return b.getBranchName();
    }
    
    // prints details of an employee
    public String details() 
    {
        return "Employee{" + "staffNo=" + staffNo + ", acccoutId=" + acccoutId + ", name=" + name + ", userName=" + userName + ", password=" + password ;
    }
    
    
    
}
