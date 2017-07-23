/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainClasses;

/**
 *
 * @author johnkenny
 */
public class BranchStaff extends Employee implements StaffProfile
{
    //varaibles for this object
   
    private int branchNo;

    //constructors for this class

    public BranchStaff(int staffNo, int acccoutId, String name,  String password, int branchNo) 
    {
        super(staffNo, acccoutId,  name,  password, AccountTypes.BRANCHADMIN);
        //creates a user account see the super class format namestaffNo eg john5
        super.setUserName(setUser(name, staffNo));
        this.branchNo = branchNo;
    }

    public BranchStaff(int acccoutId, String name,  String password, int branchNo) 
    {
        super(acccoutId,  name, password, AccountTypes.BRANCHADMIN);
        //super.setUserName(setUser(name));
        this.branchNo = branchNo;
    }
    
    //setters
    
    //sets the branch no

    public void setBranchNo(int branchNo) 
    {
        this.branchNo = branchNo;
    }

    //geters
    
    //gets  the branchNo of this employee oversides the employee super class
    @Override 
    public int getBranchNo() 
    {
        return branchNo;
    }
    
    @Override
    public String accountType()
    {
        return AccountTypes.BRANCHADMIN.name();
    }
    
    //returns all info from an object of this class
    //overides from the employee superclass
    @Override
    public String details() 
    {
        return "Branch Staff " + " staff No " + super.getAcccoutId() + ", Branch Name " + super.branchName(branchNo) +  ", Name " + super.getName();
    }    
}
