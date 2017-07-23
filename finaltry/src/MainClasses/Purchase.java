/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainClasses;

import java.util.Date;

/**
 *
 * @author johnkenny
 */

public class Purchase extends Transaction implements Transact
{
    //varaibles for this object
    
    private int orderId;  
    private Date oderDate;

    //constructors
    
    public Purchase(int orderId, int customerNo, int stockId, int custSockId, int qty, double price, Date oderDate) 
    {
        super(customerNo, stockId, custSockId, qty, price);
        this.orderId = orderId;
       
        this.oderDate = oderDate;
    }

    //load to database constructor
    
    public Purchase(int customerNo, int stockId, int custSockId, int qty, double price, Date oderDate) 
    {
        super(customerNo, stockId, custSockId, qty, price);
        this.oderDate = oderDate;
    }
    
    //load from filereader constructor
    
    public Purchase(int customerNo, int stockId, int qty) 
    {
        super(customerNo, stockId, 0, qty, 0);
        
    }
   
    //get methods
    
    //return varaibles of this constructor

    public int getOrderId() 
    {
        return orderId;
    }
    
    public Date getOderDate() 
    {
        return oderDate;
    }
    public int getCustomerNo()
    {
        return super.getCustomerNo();
    }
    
    //set methods
    
    //uesed to change or set values in an object of this class
    
    public void setOrderId(int orderId) 
    {
        this.orderId = orderId;
    }


    public void setOderDate(Date oderDate) 
    {
        this.oderDate = oderDate;
    }

    //print method
    
    //This overrides the super class deatils and is part of the interface
    @Override
    public String details()
    {
        return "Purchase order Id: " + orderId +  super.details() + " order Date " + oderDate;
    }
            
}//close Purchase class 
