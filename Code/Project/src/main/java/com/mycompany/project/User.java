package com.mycompany.project;

import java.util.ArrayList;

/* 
This class mainly for login an logout functionalities and defining main attributes of users

## Login:
    it will be done by checking the ID and the password of the user,
    I have used ID for login rather than the username, as the username could be repeated,
    So, We have to implement the Logic of login by using the unique key (ID)
*/

public class User {
    private int id;
    private String username;
    private String password;
    private String role;
    private boolean loggedIn = false;

    public boolean login(String username, String pass){
        ArrayList<String> details = FileHandler.findByField("C:\\Users\\lenovo\\Documents\\GitHub\\billing-system\\Code\\Project\\Files\\Users.txt", 1, username);
        boolean loggedIn = false;
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
                break; 
            }
            else {
            System.out.println("Wrong username or password");
        }
        }
        
        return loggedIn;
    }
    
    public void logout(){
        this.id = 0;
        this.username = null;
        this.password = null;
        this.role = null;
        this.loggedIn = false;
    }
public static void main(String[] args) {
    User omar = new User();
    System.out.println(omar.loggedIn);
    omar.login("omar", "pass_omar");
    System.out.println(omar.loggedIn);
    omar.logout();
    System.out.println(omar.loggedIn);
}

}