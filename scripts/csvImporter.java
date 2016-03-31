import java.util.*;
import javax.swing.JFileChooser;
import java.io.*;
import java.sql.*;

public class csvImportScript {
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch(Exception e) {
			e.printStackTrace();
		}

		JFileChooser filechooser = new JFileChooser();
		filechooser.showOpenDialog(null);
		File file = filechooser.getSelectedFile();
		
		try(Scanner sc = new Scanner(file)) {
			String[] label = sc.nextLine().split(",");

			while(sc.hasNextLine()) {
				String[] col = sc.nextLine().split(",");
				String domain = file.getName().split(".csv")[0];			

				SQLUpdate("CREATE DATABASE Library");
				SQLUpdate("Library","CREATE TABLE BookDetails(AccNo text,Title text,Publisher text,Domain text,Year int,Price int,Edition text)");
				SQLUpdate("Library","CREATE TABLE Author(AccNo text,AuthorName text)");
				SQLUpdate("Library","INSERT INTO BookDetails VALUES('"+col[0]+"','"+col[1]+"','"+col[2]+"','"+domain+"',"+col[3]+","+col[4]+",'"+col[5]+"')");
				for(int i = 6; i < col.length; i++)
					SQLUpdate("Library","INSERT INTO Author VALUES('"+col[0]+"','"+col[i]+"')");
			}
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	static boolean testServerConnection(String ip,String user,String password) throws SQLException {
                try(Connection conn = DriverManager.getConnection("jdbc:mysql://"+ip,user,password)) {
                        return conn != null;
                }
        }

	static void SQLUpdate(String update) {
                try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost","test","test")) {
                        try(Statement stmt = conn.createStatement()) {
                                stmt.executeUpdate(update);
                        } catch(SQLException e) {
                                e.printStackTrace();
                        }
                } catch(SQLException e) {
                        e.printStackTrace();
                }
        }

	static void SQLUpdate(String name,String update) {
                try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/"+name,"test","test")) {
                        try(Statement stmt = conn.createStatement()) {
                                stmt.executeUpdate(update);
                        } catch(SQLException e) {
                                e.printStackTrace();
                        }
                } catch(SQLException e) {
                	e.printStackTrace();
                }
        }
}
