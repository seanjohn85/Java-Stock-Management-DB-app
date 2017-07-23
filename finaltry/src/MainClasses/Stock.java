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
public class Stock 
{
    //varaibles of a stock object
    
    private int stockId;
    private String stockName;
    private String stockCode;
    private int qty;
    private double openPrice;
    private double currentPrice;

    //constructors
    
    public Stock(int stockId, String stockName, String stockCode, int qty, double openPrice, double currentPrice) 
    {
        this.stockId = stockId;
        this.stockName = stockName;
        this.stockCode = stockCode;
        this.qty = qty;
        this.openPrice = openPrice;
        this.currentPrice = currentPrice;
    }

    //used to create stock in the database
    
    public Stock(String stockName, String stockCode, int qty, double openPrice, double currentPrice) 
    {
        this.stockName = stockName;
        this.stockCode = stockCode;
        this.qty = qty;
        this.openPrice = openPrice;
        this.currentPrice = currentPrice;
    }
    
    public Stock()
    {
        
    }
    
    //get methods used to get a property of a stock object
    
    public int getStockId() 
    {
        return stockId;
    }

    public String getStockName() 
    {
        return stockName;
        
    }
    
    public String getStockCode() 
    {
        return stockCode;
    }
    
    public int getQty() 
    {
        return qty;
    }
    
    public double getCurrentPrice() 
    {
        return currentPrice;
    }

    public double getOpenPrice() 
    {
        return openPrice;
    }
    
    //setters used to set properties or changge properies of a stock object

    public void setStockId(int stockId) 
    {
        this.stockId = stockId;
    }
    
    public void setStockName(String stockName) 
    {
        this.stockName = stockName;
    }

    public void setStockCode(String stockCode) 
    {
        this.stockCode = stockCode;
    }

    public void setQty(int qty) 
    {
        this.qty = qty;
    }

    public void setOpenPrice(double openPrice) 
    {
        this.openPrice = openPrice;
    }

    public void setCurrentPrice(double currentPrice) 
    {
        this.currentPrice = currentPrice;
    }

    //print methods
    @Override
    public String toString() 
    {
        return "Stock Name " + stockName + ", Stock Code " + stockCode + " qty " + qty + " open Price \u20AC" + openPrice + " current Price \u20AC" + currentPrice + '\n';
    }
    public String stockDet() 
    {
        return "Stock Name " + stockName + " Stock Code " + stockCode +  " Price \u20AC" + currentPrice;
    }
    
}//close class
