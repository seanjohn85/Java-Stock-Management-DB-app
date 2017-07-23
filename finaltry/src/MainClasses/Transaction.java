/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainClasses;

/**
 *
 * @author johnkenny
 * 
 * this is a super class used by purchases and sales
 * 
 */
public class Transaction implements Transact
{
    //object properties
    private int customerNo,
            stockId,
            custSockId,
            qty;
    private double price;
    //To use the Euro Sign
    private static final String EURO = "\u20AC";
    
    //constructor
    public Transaction(int customerNo, int stockId, int custSockId, int qty, double price) 
    {
        this.customerNo = customerNo;
        this.stockId = stockId;
        this.custSockId = custSockId;
        this.qty = qty;
        this.price = price;

    }
    
    //get methods
    public int getStockId() 
    {
        return stockId;
    }
    
    public int getCustomerNo() 
    {
        return customerNo;
    }

    public int getCustSockId() 
    {
        return custSockId;
    }
    
    public int getQty() 
    {
        return qty;
    }
    
    public double getPrice() 
    {
        return price;
    }
    
    
    //set methods
    public void setStockId(int stockId) 
    {
        this.stockId = stockId;
    }

    public void setCustSockId(int custSockId) 
    {
        this.custSockId = custSockId;
    }

    public void setQty(int qty) 
    {
        this.qty = qty;
    }

    public void setPrice(double price) 
    {
        this.price = price;
    }
    
    public void setCustomerNo(int customerNo) 
    {
        this.customerNo = customerNo;
    }
    
    //This is an example of a method that is used for polymorphism in the sub classes
    
    //object deatils string returned
    public String details() 
    {
        return  " Qty " + getQty() + " price " + EURO + getPrice();    
    }
    
}
