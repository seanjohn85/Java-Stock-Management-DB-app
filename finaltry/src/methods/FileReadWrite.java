/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methods;

import DataBaseManager.DBConn;
import DataBaseManager.ManagerDBLink;
import MainClasses.AccountTypes;
import MainClasses.Branch;
import MainClasses.BranchStaff;
import MainClasses.Customer;
import MainClasses.Employee;
import MainClasses.HeadOfficeStaff;
import MainClasses.Manager;
import MainClasses.Sale;
import MainClasses.Stock;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import static methods.MenuOptions.brModel;
import org.apache.commons.lang3.text.WordUtils;
import MainClasses.Purchase;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.*; 
import jxl.write.Label;
import jxl.write.Number;
import static methods.Input.inputOption;
import static methods.StaffMethods.createStaff;

/**
 *
 * @author johnkenny
 * These methods are used for file io handelling
 * input csv, excel 
 * output, html, excel
 */
public class FileReadWrite 
{
    //this method reads data from an rexcel file using the jxl library 
    
    public static void readExcelFile() throws IOException, SQLException, ParseException
    {
        System.out.println("File must be excel file \n"
                + "With the following format: \n"
                + "Starting in sheet 1 on row 3 \n"
                + "name: branchNo: password: role:\n"
                + "head office have can have null or 0 for branch no \n"
                + "\n*****NB START FROM ROW 3 AND LEAVE NO BLANK ROWS****\n");
        String inputFileName = inputOption("Enter File location");
        //File inputFile = new File(inputFileName);
        try {
                Workbook workbook = Workbook.getWorkbook(new File(inputFileName));
                //int sheetNo = Input.convertToNumberInt(inputOption("Enter File location"));
                Sheet sheet = workbook.getSheet(0);
                //method to sread rows
                rowCheck(sheet); 
                //closes doc
                workbook.close();
            } 
        //handels errors reading the file stopping the application crashing
        catch (BiffException ex) 
        {
            Logger.getLogger(FileReadWrite.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (FileNotFoundException ex)
        {
          System.err.println("No such file exists\n"); 
        }
        
    }//close readExcelFile
    
    //reads rows of data on an excel file of employees
    
    public static void rowCheck(Sheet sheet) throws SQLException, ParseException
    {
        boolean hasRows = true;
        //allows a header and filer row
        int row = 2;
        
        //keeps reading until a row is empty
        while(hasRows)
        {
            //varibles needed to create an employee
            String name = null, password = null, role = null;
            int branchNo = -1;
            try 
            {  
                //sets data from cell 1 sets it to name
                Cell cname = sheet.getCell(0, row);
                //System.out.println(cname.getContents());
                name = cname.getContents();
            }
            catch(ArrayIndexOutOfBoundsException e)
            {
                //if cell one if empty breaks loop as employee has to have a name
                hasRows = false;
            }
            try 
            {    
                //sets branch no to cell 2 data
                Cell cbranchNo = sheet.getCell(1, row);
                //System.out.println(cbranchNo.getContents());
                if(Input.isNumber(cbranchNo.getContents()))
                {
                    branchNo = Input.convertToNum(cbranchNo.getContents());
                }
            }
            catch(ArrayIndexOutOfBoundsException e)
            {
                branchNo = 0;
            }
            try 
            {    
                //sets password to cell 3 data
                Cell cpassword = sheet.getCell(2, row);
                //System.out.println(cpassword.getContents());
                password = cpassword.getContents();
            }
            catch(ArrayIndexOutOfBoundsException e)
            {
                
            }
            try 
            {    
                //sets row to cell 3 data
                Cell crole = sheet.getCell(3, row);
                //System.out.println(crole.getContents());
                role  = crole .getContents();
            }
            catch(ArrayIndexOutOfBoundsException e)
            {
                
            }
           // System.out.println(name + branchNo + password + role);
            if(name != null && password != null && role != null)
            {
                //creates employee using the cell data
                createEmployee(name, branchNo, password, role);
            }
            //moves to next row
            row++;
        }
                
    }//close rowCheck
    
    //used to read transactions from a csv file
    
    public static void readTrans() throws IOException
    {
        //gets the file info from user
        String inputFileName = inputOption("Enter File location");
        File inputFile = new File(inputFileName);
        try
        {
            //trys to open the file
            Scanner in = new Scanner(inputFile);
           //loops through all lines in the file
            while (in.hasNextLine()) 
            {
                //sets each line to a string of data
                String line = in.nextLine();
                //used to idenify a purchase
                if(line.startsWith("p"))
                {
                    //System.out.println("line");
                    try 
                    {
                        //feeds the data to the make pur method
                        makePur(line);
                    } 
                    catch (ParseException ex) 
                    {
                        Logger.getLogger(FileReadWrite.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //used to idenify a sale
                else if(line.startsWith("s"))
                {
                    try 
                    {
                        //feeds the data to the make sale method
                        //System.out.println(line);
                        makeSale(line);
                    } 
                    catch (ParseException ex) 
                    {
                        Logger.getLogger(FileReadWrite.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //invalid indication key
                else
                {
                    System.err.println("This is not a valid transaction \n Transactions need to start with p for purchases and s for sales");
                }

            }//closes while no more lines in the file
            
            //closes csv file
            in.close();
     
        }//close try
        //if the file cant be located or opened
        catch (FileNotFoundException ex)
        {
          System.err.println("No such file exists"); 
        }
        
    } //closes readTrans
    
    //used to create a customer  report on there stocks and transactions HTML format
    
    public static void custReport(Customer cust) throws FileNotFoundException, IOException
    {
       //for file
        DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_HH_mm");
        DateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");

	//gets the current date and time in Java format
	Date date = new Date();
        //formats date using above format 
        String newDate =dateFormat.format(date);
        //gets custName
        String custNameDate = (cust.getFName() +"_" + cust.getLName() + "_" + newDate);
        
        //This is what the file will be safed as customers name and the current date
        String outFileName = (custNameDate +".html");
        //creates the file
        PrintWriter out = new PrintWriter(outFileName);
        //prints opening html tags to the file using the htmlStart method
        out.println(htmlStart(custNameDate));
        //prints customer name as the header. Formats name using a library
        out.println("\t<h1>" + WordUtils.capitalize(cust.getFName()) +  " " + WordUtils.capitalize(cust.getLName()) + "</h1>");
        //prints date to the file
        out.println("\t<h2> Report Date " + dateFormat2.format(date) + "</h2>" );
        if(hasStock(cust))
        {
            //out.println("\t<p>This customer has stock</p>");
            
            //prints the customers stock portfolio in a table using the stockTable method
            out.println(stockTable(cust.getSt()));
            //prints header
            out.println("\t<h3>Transaction History</h3>");
            //prints purchases table using the purTable method
            out.println(purTable(cust.getPur()));
            //check if the customer ever sold stock shares
            if(hasSales(cust))
            {
                //prints a table with sold shares using the saleTable method
                out.println(saleTable(cust.getSale()));
            }
            //if the customer never sold any stock
            else
            {
                out.println("\t<p>This customer has not sold any shares to date.<p>");
            }
        }
        //if the customer has an empty portfolio
        else
        {
            out.println("\t<p>This customer has no stock</p<");
        }
        //prints end html tags
        out.println(htmlEnd());
        //closes document
        out.close();
        
        //advises report complete
        System.out.println("The report for " + WordUtils.capitalize(cust.getFName()) +  " " + WordUtils.capitalize(cust.getLName()) +
                            "\nThe file name is " + outFileName +"\nThank you!");
        
    }
    
    //opening html tags
    
    public static String htmlStart(String title)
    {
        String open = "<!DOCTYPE html>" +
                      "\n<html>\n<head>\n\t" +  
                       "<meta charset='utf-8'> \n\t" +
                       "<title>" + title + "</title>\n "+
                    "</head>\n<body>";
                
        return open;
 
    }// close htmlStart
    
    //closing html tags
    
    public static String htmlEnd()
    {
        String close = "</body>\n</html>";
                
        return close;
    }//close htmlEnd
    
    //checks if the cust has stock
    
    public static boolean hasStock(Customer c)
    {
        if(c.getSt().isEmpty())
        {
            return false;
        }
        else
        {
            return true;
        }
        
    }//close hasStock
    
    //checks if the customer has sales
    
    public static boolean hasSales(Customer c)
    {
        if(c.getSale().isEmpty())
        {
            return false;
        }
        else
        {
            return true;
        }
    }//closes hasSales
    
    //creates html stockTable
    
    public static String stockTable(List<Stock> sto)
    {
        String table;
        //creates a string to return to the html out file
        StringBuilder createTable = new StringBuilder();
        createTable.append("\t<h3>My Stocks</h3>");
        createTable.append("\t<table> \n\t\t<tr>\n\t\t\t<td>Stock Code</td>" +
                "\n\t\t\t<td>Stock Name</td>" +
                "\n\t\t\t<td>Share Qty</td>" +
                "\n\t\t\t<td>Price</td> \n\t\t</tr>");
        /*
        loops through all this customers stocks appending their details to the 
        stringBuilder and adding the table tags 
        */
        for(Stock s : sto)
        {
            createTable.append("\n\t\t<tr>\n\t\t\t<td>").append(s.getStockCode()).append("</td>");
            createTable.append("\n\t\t\t<td>").append(s.getStockName()).append("</td>");
            createTable.append("\n\t\t\t<td>").append(s.getQty()).append("</td>");
            createTable.append("\n\t\t\t<td>&#8364;").append(s.getCurrentPrice()).append("</td>");
            createTable.append("\n\t\t</tr>");
            
        }
        //closes table
        createTable.append("\t<table>");
        table = createTable.toString();
        //returns all the data in html table format
        return table;
    }//close stockTable
    
    //creates html purchase table
    
    public static String purTable(List<Purchase> pur)
    {
       //creates a string to return to the html out file
        StringBuilder createTable = new StringBuilder();
        
        createTable.append("\t<h4>My Purchases</h4>");
        createTable.append("\t<table> \n\t\t<tr>\n\t\t\t<td>Order No</td>" +
                "\n\t\t\t<td>Stock Name</td>" +
                "\n\t\t\t<td>Qty Bought</td>" +
                "\n\t\t\t<td>Price</td> \n\t\t" +
                "\n\t\t\t<td>Date</td> \n\t\t"
                + "</tr>");
         /*
        loops through all this customers purchases appending their details to the 
        stringBuilder and adding the table tags 
        */
        for(Purchase p : pur)
        {
            createTable.append("\n\t\t<tr>\n\t\t\t<td>").append(p.getOrderId()).append("</td>");
            createTable.append("\n\t\t\t<td>").append(stockName(p.getStockId())).append("</td>");
            createTable.append("\n\t\t\t<td>").append(p.getQty()).append("</td>");
            createTable.append("\n\t\t\t<td>&#8364;").append(p.getPrice()).append("</td>");
            createTable.append("\n\t\t\t<td>").append(p.getOderDate()).append("</td>");
            createTable.append("\n\t\t</tr>");
            
        }
        //closes table
        createTable.append("\t<table>");
        //returns table
        return createTable.toString();
    }//close purTable
    
    //records all customers sales in a html table 
    
    public static String saleTable(List<Sale> sale)
    {
        //creates a string to return to the html out file
        StringBuilder createTable = new StringBuilder();
        createTable.append("\t<h4>My Sales</h4>");
        createTable.append("\t<table> \n\t\t<tr>\n\t\t\t<td>Sale No</td>" +
                "\n\t\t\t<td>Stock Name</td>" +
                "\n\t\t\t<td>Qty Sold</td>" +
                "\n\t\t\t<td>Price</td> \n\t\t" +
                "\n\t\t\t<td>Date</td> \n\t\t"
                + "</tr>");
         /*
        loops through all this customers sales appending their details to the 
        stringBuilder and adding the table tags 
        */
        for(Sale s : sale)
        {
            createTable.append("\n\t\t<tr>\n\t\t\t<td>").append(s.getSaleId()).append("</td>");
            createTable.append("\n\t\t\t<td>").append(stockName(s.getStockId())).append("</td>");
            createTable.append("\n\t\t\t<td>").append(s.getQty()).append("</td>");
            createTable.append("\n\t\t\t<td>&#8364;").append(s.getPrice()).append("</td>");
            createTable.append("\n\t\t\t<td>").append(s.getSaleDate()).append("</td>");
            createTable.append("\n\t\t</tr>");
            
        }
        //closes table
        createTable.append("\t<table>");
        //returns table
        return createTable.toString();
    }//closes saleTable
    
    //gets the stock name
    
    public static String stockName(int id)
    {
        //gets all stock from the list in the model
        List<Stock> st = brModel.getStock();
        String name = "";
        //searches for the stock cbranchNo to print with the purchase
        for(Stock s : st)
        {
            //when the stock is found get the cbranchNo print it and end this loop
            if(s.getStockId() == id)
            {
                name = s.getStockName();
                break;
            }
        }
        return name;
    }//closes stockName
    
    //used to make purchase
    
    public static void makePur(String line) throws ParseException
    {
       //in.useDelimiter(",");
        String[] columns = line.split(",");
       
        // io is nothing custno i1 stockno i2 qty i3
        int []details = new int[4];
        for(int i = 0; i < columns.length; i++)
        {
            
            if(Input.isNumber(columns[i]))
            {
                details[i] = Input.convertToNum(columns[i]);
            }
            //System.out.println(columns[i]);
            //System.out.println("break");
            System.out.println(details[i]);
        }
       
        Customer c = brModel.custFinder(details[1]);
        if (c != null)
        {
            System.out.println(c.getFName());
            //method here
        }
        else
        {
            System.err.println("Invaild customer Purchase must have a valid customer");
            return;
        }
        String stockCode = columns[2].trim();
        System.out.println(stockCode);
        Stock sto = brModel.stockFinder(details[2], columns[2].trim());
        if (sto != null)
        {
            System.out.println(sto.getStockName());
            //method here
        }
        else
        {
            System.err.println("Invaild stock Customers can only purchase stock in the system please check this stock \n"
                    + c.getFName() + " " + c.getLName() + " has not had stock added in this transaction  no stock code or number matching ");
            //Ends this method as read i
            return; 
        }
        
        /* 
            This is the method to add a purchase to a customer.
            It updates the purchase table in the database and the stock portfolio table.
            This only happens if the stock and customer are vaild
        
        */
        methods.BranchMenuMethods.makePurchase(c, details[3], sto);
       
    }//close makePur
    
    //used to record a sale from a csv file
    
    public static void makeSale(String line) throws ParseException
    {
       //in.useDelimiter(",");
        String[] columns = line.split(",");
       
        // io is nothing custno i1 stockno i2 qty i3
        int []details = new int[4];
        for(int i = 0; i < columns.length; i++)
        {
            
            if(Input.isNumber(columns[i]))
            {
                details[i] = Input.convertToNum(columns[i]);
            }
            //System.out.println(columns[i]);
            //System.out.println("break");
            //System.out.println(details[i]);
        }
       
        Customer c = brModel.custFinder(details[1]);
        if (c != null)
        {
            System.out.println(c.getFName());
            //method here
        }
        else
        {
            System.err.println("Invaild customer Sale must have a valid customer");
            return;
        }
        String stockCode = columns[2].trim();
        System.out.println(stockCode);
        Stock sto = brModel.stockFinder(details[2], columns[2].trim());
        if (sto != null)
        {
            System.out.println(sto.getStockName());
            //method here
        }
        else
        {
            System.err.println("Invaild stock Customers can only sell stock in the system please check this stock \n"
                    + c.getFName() + " " + c.getLName() + " has not had stock added in this transaction  no stock code or number matching ");
            //Ends this method as read i
            return; 
        }
        
        /* 
            This is the method to add a sale to a customer.
            It updates the purchase table in the database and the stock portfolio table.
            This only happens if the stock and customer are vaild
        
        */
        methods.BranchMenuMethods.makeSale(c, details[3], sto);

       
    }//close makeSale
    
    //used to write an employee to a excel file
    
    public static void employeeWrite() throws IOException, WriteException
    {
        //gets file name from user
        String fileName = inputOption("Enter File name to be created dont add extention\n");
        //creates excel file
        WritableWorkbook file = Workbook.createWorkbook(new File(fileName +".xls"));
        //creates a new sheet in the file 
        WritableSheet wsheet = file.createSheet("Employees", 0);
        //adds format head using format
        format(wsheet);
        //writes employees from the database using writeEmp
        writeEmp(wsheet);
        //writes data
        file.write();
        //closes file
        file.close();
        //advises user file created
        System.out.println("Your file " +  fileName + " has been created \n");
        
    }//closes employeeWrite
    
    ///adds headers and labels
    
    public static void format(WritableSheet wsheet) throws WriteException
    {
      
      int row = 0;
      int col = 0;
      //hearder in cell 1 row 1
      Label header = new Label(col, row, "Employees");
      wsheet.addCell(header);
      //skips a row
      row =+2;
      //col= +2;
      
      //creates a lable row for the content after each label col increments to move to the next column
      Label staffNo = new Label(col, row, "Staff No");
      wsheet.addCell(staffNo);
      col++;
      Label name = new Label(col, row, "Name");
      wsheet.addCell(name);
      col++;
      Label userName = new Label(col, row, "UserName");
      wsheet.addCell(userName);
      col++;
      Label branch = new Label(col, row, "Branch");
      wsheet.addCell(branch);
      col++;
      Label password = new Label(col, row, "Password");
      wsheet.addCell(password);
      col++;
      Label job = new Label(col, row, "Role");
      wsheet.addCell(job);
      row++;
       
    }//closes format
    
    //used to insert the branch name instead of branch no
    
    public static void writeEmp(WritableSheet wsheet) throws WriteException
    {
        //all employess
        List<Employee> e = StaffMethods.sortAlph(brModel);
        int row = 3;
        //each employee branch name is found or head office is used 
        for(Employee em : e)
        {
            String branchName = "Head Office";
            if(!em.getClass().getName().substring(12).equalsIgnoreCase("HeadOfficeStaff"))
            {
               branchName = em.branchName(em.getBranchNo());
            }
            writeDetails(wsheet, row,  em, branchName);
            row ++;
            
        }
    }//close writeEmp
    
    //write and employee to a row in the excel file
    
    public static void writeDetails(WritableSheet wsheet, int row,  Employee m, String branchName) throws WriteException
    {
        //gets all the requred info from the employee object and adds it to a row in the excel file
        int col = 0;
        Number staffNo = new Number(col, row, m.getStaffNo());
        wsheet.addCell(staffNo);
        col++;
        Label name = new Label(col, row, WordUtils.capitalize(m.getName()));
        wsheet.addCell(name);
        col++;
        Label userName = new Label(col, row, m.getUserName());
        wsheet.addCell(userName);
        col++;
        Label branch = new Label(col, row, branchName);
        wsheet.addCell(branch);
        col++;
        Label password = new Label(col, row, m.getPassword());
        wsheet.addCell(password);
        col++;
        Label job = new Label(col, row, WordUtils.capitalize(m.getAc().toString().toLowerCase()));
        wsheet.addCell(job);   
        
    }//close writeDetails
    
    //creates an employee from excel file

    private static void createEmployee(String name, int branchNo, String pwd, String role) throws SQLException, ParseException 
    {
        //checks if they are a branch asmin staff
       if(role.equalsIgnoreCase("Branchadmin"))
       {
           //finds branch
           Branch br = brModel.branchFinder(branchNo);
           //if the branch is invalid advises user
           if(br == null )
           {
               System.err.println("Invalid branch number this employee can not be added");
           }
           //adds branch staff employee to the database
           else
           {
              Employee newEmployee =(new BranchStaff(0, name, pwd, branchNo));
              StaffMethods.createStaff(newEmployee);
           }
        }
       //if they are a manager
       else if (role.equalsIgnoreCase("Manager"))
        {
            //finds the branch
            Branch br = brModel.branchFinder(branchNo);
            //System.err.println(br.getBranchName());
            //if the branch cant be found
            if(br == null)
            {
                System.err.println("Invalid branch number can't add manager");
            }
            //if they already have a manager
            else if(br.getMan() != null)
            {
                System.err.println("This branch aready has a manager");
            }
            //connects to database adds a maanger
            else if(br != null && br.getMan() == null)
            {
                //conects to database
                Connection conn = DBConn.getInstance().getConnection();
                ManagerDBLink mdl = new ManagerDBLink(conn);
                //adds the maanger to the database and returns it
                Manager temp =  new Manager(0, name,  pwd, branchNo);
                Manager newManager = mdl.addManager(temp);
                //closes connection to the database
                DBConn.getInstance().close();
            }
        }
       //this means the user is a head office staff meneber
       else if(branchNo == 0)
       {
           //checks if they are an admin staff
            if(role.equalsIgnoreCase("Admin"))
            {
                //creates employee
                Employee newEmployee =(new HeadOfficeStaff(0, name, pwd, AccountTypes.ADMIN));
                StaffMethods.createStaff(newEmployee);
            }
            ///checks if they are hr staff
            else if(role.equalsIgnoreCase("HR"))
            {
                //creates employee
                Employee newEmployee =(new HeadOfficeStaff(0, name, pwd, AccountTypes.HR));
                StaffMethods.createStaff(newEmployee);
                   
            }
            //if the job type doesnt match the system
            else
            {
                System.err.println("Invalid job role can't add " + name);
            }
       }
       //if info is missing advises this staff member could not be created 
       else
       {
            System.err.println("Incomplete information this staff member " + name +" could not be created"); 
       }
    }//close createEmployee
    
}//close FileReadWrite 
