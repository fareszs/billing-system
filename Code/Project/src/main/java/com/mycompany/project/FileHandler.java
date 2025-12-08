
package com.mycompany.project;
import java.util.*;
import java.io.*;
public class FileHandler {
    public static ArrayList<String>read(String filePath){
        ArrayList<String> lines=new ArrayList<>();
        File file=new File(filePath);
       try (Scanner input=new Scanner(file)) {
           while(input.hasNext()){
               lines.add(input.nextLine());
        }
       }catch(FileNotFoundException en){
           
           System.out.println("file doesn't exist");
       }
       return lines;  
    }
    public static void write(String filePath,String line){
      try(FileWriter fw= new FileWriter(filePath,true)){
          fw.write(line);
          fw.write("\n");
      } 
      catch(IOException en){
          System.out.println("file doesn't exist");
      }
        
    }
    public static void overWrite(String filePath,ArrayList<String> lines){
      try(FileWriter fw= new FileWriter(filePath,false)){
         for (String line: lines){
          fw.write(line);
          fw.write("\n");
         }
      } 
      catch(IOException en){
          System.out.println("file doesn't exist");
      }
        
    }
    public static String findById(String filePath, int id){
        ArrayList<String> lines=read(filePath);
        for (String line : lines){
            String[] parts = line.split(",");
            if(parts.length<1)
                continue;
           try{ if(Integer.parseInt(parts[0])==id)
                return line;
            }
           catch(NumberFormatException e){
               continue;
           }
        }
        return null;
        
    }
    public static ArrayList<String> findByField(String filePath,int columnIndex,String value){
        ArrayList<String> lines=read(filePath);
        ArrayList<String> newLines=new ArrayList<>();
         for (String line : lines){
            String[] parts = line.split(",");
            if(parts.length<=columnIndex)
                continue;
            if( parts[columnIndex].equals(value)){
                newLines.add(line);
                
            }}
                
         return newLines;
        
    }
    public static void updateLineById(String filePath,int id ,String newData){
           ArrayList<String> lines=read(filePath);
         for (int i=0;i<lines.size();i++){
            String[] parts = lines.get(i).split(",");
            if(parts.length<1)
                continue;
            try{ if(Integer.parseInt(parts[0])==id)
               lines.set(i, newData);
            }
           catch(NumberFormatException e){
               continue;
           }}
         overWrite(filePath,lines);
                
        
    }
    public static void deleteLineById(String filePath, int id){
         ArrayList<String> lines=read(filePath);
         for (int i=0;i<lines.size();i++){
            String[] parts = lines.get(i).split(",");
            if(parts.length<1)
                continue;
            try{ if(Integer.parseInt(parts[0])==id)
               lines.remove(i);
                i--; 
            }
           catch(NumberFormatException e){
               continue;
           }}
         overWrite(filePath,lines);
    }
}
