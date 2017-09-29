
# Java-Stock-Management-DB-app

This app was created in netbeans IDE and is a fully functioning end to end stock management system. This system connects to a mySQl db so a program like Xamp is needed to run the application. This is app also handles multiple different file io read and write types for reporting. This is text based only and contains no UI at present. It is a Java version of the Asset Management website also on my profile. https://github.com/seanjohn85/Asset-Management-WebSite

## Setting up the server (Mac based instructions using Xammp)

This program uses was designed to interact with a MYSQL database.

Install xammp or Mamp on the machine,
Once installed start the Apahce server and the MYSql database on the local machine.

Open phpMyAdmin - http://localhost/phpmyadmin/ - this link is for Xampp only.

Click the import tab and import the database file N00145905.sql in this repo.

Once the database is uploaded the server side set up is complete.

Please note the server needs to be running at all times when the client application is running to access the database.


## Setting up The project  (Instructions using Netbeans)
Download the final try folder and all the jar files in this repo.
Import the project into netbeans at this point or when you try to run the project you will be notified of the missing jar libraries used in the project simply download add the downloaded jar files to the netbeans project and click run.



#### Using the application
The server will need to be running at all times. Once the server is running and the application is open in netbeans click the run button. Then log in.
This application is text based and is designed as a back end to match the stock management website. Please note their are different types of user accounts and below are some sample accounts

MaJohn1
secret
MANAGER

Joe1
secret
BRANCHADMIN


HQAlice2
secret
ADMIN

HQClare3
secret
HR
