/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainClasses;

/**
 *
 * @author johnkenny
 * interface for transaction classes
 * 
 */
public interface Transact 
{
    //Displays a String of a transcaction(Used for purchases and sales)
    public String details();
    
    //Used to update stock quanity for transaction subclasses(purchases and sales)
    //When a new order happers 
    public void setQty(int qty);
}
