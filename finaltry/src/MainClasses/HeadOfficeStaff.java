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
public class HeadOfficeStaff extends Employee
{
    //varaibles for this object
   
    private int headOffice;
    
    //constructors for this class

    public HeadOfficeStaff(int staffNo, int acccoutId, String name, String password, AccountTypes ac) 
    {
        super(staffNo, acccoutId,  name, password, ac);
        super.setUserName(setUser(name, staffNo));
        this.headOffice = 0;
       
    }

    public HeadOfficeStaff(int acccoutId, String name,  String password,AccountTypes ac) 
    {
        super(acccoutId,  name, password, ac);
        this.headOffice = 0;
       
    }
    
    /*
    sets the username adding HQ to the start of the user name created from the 
    supper class setUser method. example HQnameNo - HDJoe2
    */
    @Override
    public String setUser(String user, int staffNo)
    {
        return "HQ" + super.setUser(user, staffNo) ;
    }
  
    
    //returns all info from an object of this class 
    @Override
    public String details() 
    {
        return "Head Office Staff " + "Staff No " + super.getAcccoutId() +  " Name " + super.getName();
    }  
    
}// close HeadOfficeStaff
