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
 * 
 * This matches the Fluctuation table on the database and creates current
 * stock prices every time software is ran
 */
public class Fluctuation 
{
    //variables for this class
    private int recordNo,
                stockid,
                percent;
    private double price,
                amount;
    private Date date;

    //constructors
    
    public Fluctuation(int recordNo, int stockid, double amount, int percent, double price, Date date) {
        this.recordNo = recordNo;
        this.stockid = stockid;
        this.amount = amount;
        this.percent = percent;
        this.price = price;
        this.date = date;
    }

    public Fluctuation(int stockid, double amount, int percent, double price) {
        
        this.stockid = stockid;
        this.amount = amount;
        this.percent = percent;
        this.price = price;
    }
    
    //getter and setter methods 

    public int getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(int recordNo) {
        this.recordNo = recordNo;
    }

    public int getStockid() {
        return stockid;
    }

    public void setStockid(int stockid) {
        this.stockid = stockid;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}//close Fluctuation 
