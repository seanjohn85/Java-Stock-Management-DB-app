
# Java-Stock-Management-DB-app

This app was created in netbeans IDE and is a fully functioning end to end stock management system. This system connects to a mySQl db so a program like Xamp is needed to run the application. This is app also handles multiple different file io read and write types for reporting. This is text based only and contains no UI at present. It is a Java version of the Asset Management website also on my profile. https://github.com/seanjohn85/Asset-Management-WebSite

## Setting up the server (Mac based instructions using Xammp)

This program uses was designed to interact with a MYSQL database.

Install xammp or Mamp on the machine,
Once installed start the Apahce server and the MYSql database on the local machine.

Open phpMyAdmin - http://localhost/phpmyadmin/ - this link is for Xampp only.
Click the import tab and import the 

Set up a server 

Navigate to the project directory using the terminal.

Before started the server application please ensure to use NPM to download and install any library dependencies before starting the server application. This can be done using the NPM in the terminal.

The server app that needs to run is server.js. 

This can be run by simply typing node server into the terminal on the project directory.

The server will start and log Listening on *3000 to indicate the port no of the server app.

### Running on the client device

Once the server is running, open a web browser and type localhost:3000. This will connect to the Node server application and the server will serve the index.html web page. When the page loads you are good to go and the client and server setup is successful. Repeat this process to open multiple clients as the game needs more than one user.

If you navigate back to your terminal you will see a message indicating a new client has connected.


#### Using the application
1. log in
2. click any user to challenge the user to a game 
3. play the game and enjoy 😀
