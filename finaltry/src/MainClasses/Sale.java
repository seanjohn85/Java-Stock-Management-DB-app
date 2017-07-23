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

public class Sale extends Transaction implements Transact
{
    //varaibles for this object
    
    private int salesId;
            
    private Date saleDate;

    //constructors
    
    public Sale(int orderId, int customerNo, int stockId, int custSockId, int qty, double price, Date oderDate) 
    {
        super(customerNo, stockId, custSockId, qty, price);
        this.salesId = orderId;
       
        this.saleDate = oderDate;
    }

    //load to database constructor
    
    public Sale(int customerNo, int stockId, int custSockId, int qty, double price, Date oderDate) 
    {
        super(customerNo, stockId, custSockId, qty, price);
        this.saleDate = oderDate;
    }
    
     //load from filereader constructor
    
    public Sale(int customerNo, int stockId, int qty) 
    {
        super(customerNo, stockId, 0, qty, 0);
        
    }
   
    //get methods
    
    //return varaibles of this constructor

    public int getSaleId() 
    {
        return salesId;
    }


    
    public Date getSaleDate() 
    {
        return saleDate;
    }
    public int getCustomerNo()
    {
        return super.getCustomerNo();
    }
    
    //set methods
    
    //used to change or set values in an object of this class
    
    public void setSaleId(int orderId) 
    {
        this.salesId = orderId;
    }

    public void setOderDate(Date oderDate) 
    {
        this.saleDate = oderDate;
    }

    //print method
    
    //This overrides the super class deatils and is part of the interface
    @Override
    public String details()
    {
        return "Sale Id: " + salesId +  super.details() + " Date Sold " + saleDate;
    }
            
}//close class
