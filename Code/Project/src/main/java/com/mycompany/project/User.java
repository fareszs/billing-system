package com.mycompany.project;
import java.util.ArrayList;
public class User {
    private int id;
    private String username;
    private String password;
    private String role;
    private boolean loggedIn = false;
    
    public User(){
}
    public int getId() { return id; }
    public String getRole() { return role; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public boolean isLoggedIn() { return loggedIn; }
    public int setter(int id, String username, String password, String role, boolean loggedIn){
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.loggedIn = loggedIn;
        return 0; 
    }

    public boolean login(String username, String pass){
        ArrayList<String> details = FileHandler.findByField("Files\\Users.txt", 1, username);   
        for(String detail : details){
            String[] parts = detail.split(",");
            // Check if the line has enough parts before accessing them
            if(parts.length > 3 && parts[2].equals(pass)){
                User user = new User();
//                user.setter(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], true);
                // Once we've found the user and logged in, we can stop searching
               
                this.id = Integer.parseInt(parts[0]);
                this.username = parts[1];
                this.password = parts[2];
                this.role = parts[3];
                this.loggedIn = true;
            return loggedIn;
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
}