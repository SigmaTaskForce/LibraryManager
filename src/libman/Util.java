package libman;


import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author root
 */
public class Util {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost/Library";
    static final String USER = "root";
    static final String PASS = "sken";
    static String getDate()
	{
		int d,m,y;
		String dd,mm,yy;
		Calendar time=Calendar.getInstance();
		
		d=time.get(Calendar.DAY_OF_MONTH);
		m=time.get(Calendar.MONTH)+1;
		y=time.get(Calendar.YEAR);
		
		if(d<=9)
			dd="0"+Integer.toString(d);
		else
			dd=Integer.toString(d);
		if(m<=9)
			mm="0"+Integer.toString(m);
		else
			mm=Integer.toString(m);
		if(y<=9)
			yy="0"+Integer.toString(y);
		else
			yy=Integer.toString(y);
		
		return (yy+"_"+mm+"_"+dd);
	}
    
    public static ResultSet getResult(String q){
        Connection conn = null;
        Statement stmt = null;
            try {
		Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                stmt = conn.createStatement();
                //String sql = "SELECT * FROM Author";
                ResultSet rs = stmt.executeQuery(q);
                //fun(rs);
                return rs;
                //rs.close();
            }catch(ClassNotFoundException e) {
        	e.printStackTrace();
        }catch(SQLException se){
      //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
      //Handle errors for Class.forName
            e.printStackTrace();
        }
        return null;
    }
    public static String sqlInsert(String q){
        Connection conn = null;
        Statement stmt = null;
            try {
		Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                stmt = conn.createStatement();
                stmt.executeUpdate(q);
                if(stmt!=null){
                    stmt.close();
                }else{
                    return "Error";
                }
            }catch(ClassNotFoundException e) {
        	e.printStackTrace();
                return "Error classnotfound exception";
        }catch(SQLException se){
      //Handle errors for JDBC
            se.printStackTrace();
            return "Error sqlexception";
        }catch(Exception e){
      //Handle errors for Class.forName
            e.printStackTrace();
            return "Error exception";
        }
        return "done";
    }
    
    
    public static void main(String[] args){
       
    }
}
