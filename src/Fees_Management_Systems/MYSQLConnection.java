/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fees_Management_Systems;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Admin
 */
public class MYSQLConnection {
    
    
     public static Connection getmysqlConnection()
    {
        Connection con = null;
        
        try 
        {
             Class.forName("com.mysql.cj.jdbc.Driver");
             String url ="jdbc:mysql://localhost:3306/ntfmsdatabase?zeroDateTimeBehavior=CONVERT_TO_NULL";
             con=DriverManager.getConnection(url,"root","system");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return con;
    }

}
