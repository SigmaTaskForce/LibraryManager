import java.util.*;
import javax.swing.JFileChooser;
import java.io.*;
import java.sql.*;

public class csvImporter {
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch(Exception e) {
			e.printStackTrace();
		}

		Credentials c = null;

		if(args.length == 3) {
			c = new Credentials(args[0],args[1],args[2]);
		}

		else {
			System.out.println("Usage:\n\njava -cp /path/to/mysql-connector.jar:. csvImporter <MySQL-ipaddr> <MySQL-username> <MySQL-password>");
			System.exit(0);
		}

		JFileChooser filechooser = new JFileChooser();
		filechooser.showOpenDialog(null);
		File file = filechooser.getSelectedFile();

		try(Scanner sc = new Scanner(file)) {
			sc.nextLine();

			SQLUpdate("CREATE DATABASE Library");
			SQLUpdate("Library","CREATE TABLE BookDetails(AccNo text,Title text,Publisher text,Domain text,Year int,Price int)");
			SQLUpdate("Library","CREATE TABLE Author(AccNo text,AuthorName text)");

			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] col = line.split(";");
				System.out.println(col[0]);

				SQLUpdate("Library","INSERT INTO BookDetails VALUES('"+col[0]+"','"+col[1]+"','"+col[2]+"','"+col[3]+"',"+col[4]+","+col[5]+")");
				for(int i = 6; i < col.length; i++) {
					if(!col[i].equals(""))
						SQLUpdate("Library","INSERT INTO Author VALUES('"+col[0]+"','"+col[i]+"')");
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	static void SQLUpdate(String update) {
                try(Connection conn = DriverManager.getConnection("jdbc:mysql://"+Credentials.ip,Credentials.username,Credentials.password)) {
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
                try(Connection conn = DriverManager.getConnection("jdbc:mysql://"+Credentials.ip+"/"+name,Credentials.username,Credentials.password)) {
                        try(Statement stmt = conn.createStatement()) {
                                stmt.executeUpdate(update);
                        } catch(SQLException e) {
                                System.out.println("--------");
				try(FileWriter fw = new FileWriter(new File("log"), true)) {
					fw.write(update);
				} catch(Exception ex) {}
				//e.printStackTrace();
                        }
                } catch(SQLException e) {
                	System.out.println("--------");
			//e.printStackTrace();
                }
        }
}

class Credentials {
	Credentials(String ipaddr, String user, String pass) {
		ip = ipaddr;
		username = user;
		password = pass;
	}

	public static String ip;
	public static String username;
	public static String password;
}
