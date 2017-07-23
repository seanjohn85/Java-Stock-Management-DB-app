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
public enum AccountTypes 
{
    /*
    an employee object needs to have one of the following accounts 
    this is used to set its user account access
    */
    ADMIN("admin"), HR("HR"), MANAGER("Manager"),
    BRANCHADMIN("branch admin"), BRANCHFRONT("branch front end"),
    TECH("Tech Support");
    
    private String access;
    
    //this allows us to set an account using the non caps paramters above
    AccountTypes(String access)
    {
        this.access = access;
    }
    
}//close AccountTypes 
