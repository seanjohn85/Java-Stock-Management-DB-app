/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainClasses;

/**
 *
 * @author johnkenny
 * Interface for employees
 */
public interface StaffProfile 
{
    //uses enum for accountType(This will set access to the system)
    public String accountType();
    
    //used to create userName 
    public String setUser(String user, int staffNo);
    
    //returns details of this aobject
    public String details();
    
    //gets branch no of staff member
    public int getBranchNo();
    
    //gets branch name of staff member using branch no
    public String branchName(int branchNo);
    
}
