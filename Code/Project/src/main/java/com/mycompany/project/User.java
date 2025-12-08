package com.mycompany.project;

import java.util.ArrayList;


public class User {
    private int id;
    private String username;
    private String password;
    private String role;
    private boolean loggedIn = false;

    public boolean login(String username, String pass){
        ArrayList<String> details = FileHandler.findByField("Files\\Users.txt", 1, username);   
        for(String detail : details){
            String[] parts = detail.split(",");
            // Check if the line has enough parts before accessing them
            if(parts.length > 3 && parts[2].equals(pass)){
                System.out.println("Logged in Successfully");
                this.id = Integer.parseInt(parts[0]);
                this.username = parts[1];
                this.password = parts[2];
                this.role = parts[3];
                this.loggedIn = true;
                // Once we've found the user and logged in, we can stop searching
               return true;
            }
        }
        
    System.out.println("Wrong username or password");
    return false;
        
         }
    
    public void logout(){
        this.id = 0;
        this.username = null;
        this.password = null;
        this.role = null;
        this.loggedIn = false;
    }


}