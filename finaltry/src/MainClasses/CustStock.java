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
public class CustStock 
{
    //These are the varaibles for the custStock object
    private int custStockId,
        customerNo,
        stockId,
        qty;

    //Constructors
    
    //This constructor is used to load a CustStock obkect from the database
    public CustStock(int custStockId, int customerNo, int stockId, int qty) {
        this.custStockId = custStockId;
        this.customerNo = customerNo;
        this.stockId = stockId;
        this.qty = qty;
    }
    
    //This is used to load a custstock object into the database
    public CustStock(int customerNo, int stockId, int qty) 
    {
        this.customerNo = customerNo;
        this.stockId = stockId;
        this.qty = qty;
    }
    
    //Getter methods
    
    //return valibles of a custsctock object
    
    public int getCustStockId() 
    {
        return custStockId;
    }

    public int getCustomerNo() 
    {
        return customerNo;
    }
 
    public int getStockId() 
    {
        return stockId;
    }
    
    public int getQty() 
    {
        return qty;
    }
    
    //setter methods
    
    //set a varauble in this object
    
    public void setCustomerNo(int customerNo) 
    {
        this.customerNo = customerNo;
    }

    public void setCustStockId(int custStockId) 
    {
        this.custStockId = custStockId;
    }
   

    public void setStockId(int stockId) 
    {
        this.stockId = stockId;
    }

    public void setQty(int qty) 
    {
        this.qty = qty;
    }
    
}//close CustStock
