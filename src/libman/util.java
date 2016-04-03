package libman;

import java.util.*;
import java.text.*;
import java.io.*;
import java.sql.*;

public class util {
	static String getServerData(String choice) {
		String temp = "";
		File file = new File("server.cfg");

                try(Scanner sc = new Scanner(file)) {
                	while(sc.hasNextLine()) {
                        	temp = sc.nextLine();

                                if(!temp.startsWith("#") && temp.split("=")[0].equals(choice)) {
                                        temp = temp.split("=")[1];
                                        break;
                                }
                        }
                } catch(IOException e) {
                	e.printStackTrace();
                }

		return temp;
	}

	static String[] SQLQuery(String name,String query) {
		List<String> list=new ArrayList<String>();

		try(Connection conn=DriverManager.getConnection("jdbc:mysql://"+util.getServerData("Server IP")+"/"+name,util.getServerData("Username"),util.getServerData("Password"))) {
			try(Statement stmt=conn.createStatement()) {
				try(ResultSet rs=stmt.executeQuery(query)) {
					String[] temp=query.split(" ");

					if(temp[1].equals("*")) {
						String table="";

						for(int i=0;i<temp.length;i++)
							if(temp[i].equals("FROM")) {
								table=temp[i+1];
								break;
							}

						String[] column=util.listColumns(name,table);
						while (rs.next()) {
							for(int i=0;i<column.length;i++)
    							list.add(rs.getString(column[i]));
						}
					}

					else {
						int i;
    						for(i=0;i<temp.length;i++)
    							if(temp[i].equals("AS"))
    								break;

    						while (rs.next()) {
							if(i<temp.length)
    								list.add(rs.getString(temp[i+1]));

    							else {
    								if(temp[1].equals("DISTINCT"))
    									list.add(rs.getString(temp[2]));

    								else
    									list.add(rs.getString(temp[1]));
    							}
    						}
					}
				} catch(SQLException e) {
					e.printStackTrace();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		} catch(SQLException e) {
    			e.printStackTrace();
		}

		return list.toArray(new String[list.size()]);
	}

	static void SQLUpdate(String update) {
		try(Connection conn=DriverManager.getConnection("jdbc:mysql://"+util.getServerData("Server IP"),util.getServerData("Username"),util.getServerData("Password"))) {
			try(Statement stmt=conn.createStatement()) {
				stmt.executeUpdate(update);
			} catch(SQLException e) {
				e.printStackTrace();
			}
		} catch(SQLException e) {
    			e.printStackTrace();
		}
	}

	static void SQLUpdate(String name,String update) {
		try(Connection conn=DriverManager.getConnection("jdbc:mysql://"+util.getServerData("Server IP")+"/"+util.getClass(name),util.getServerData("Username"),util.getServerData("Password"))) {
			try(Statement stmt=conn.createStatement()) {
				stmt.executeUpdate(update);
			} catch(SQLException e) {
				e.printStackTrace();
			}
		} catch(SQLException e) {
    			e.printStackTrace();
		}
	}
	static String getDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

                return dateFormat.format(Calendar.getInstance().getTime());
	}

	static String getDate(String date, int days) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                Calendar calendar = new GregorianCalendar();
                Date theDate = null;

                try {
                        theDate = dateFormat.parse(date);
                } catch(ParseException e) {
                        e.printStackTrace();
                }

                calendar.setTime(theDate);
		calendar.add(Calendar.DATE, days);

		return dateFormat.format(calendar.getTime());
	}

	static String getDay() {
		Calendar time=Calendar.getInstance();
		int i=time.get(Calendar.DAY_OF_WEEK);

		if(i==1)
			return "Sunday";
		if(i==2)
			return "Monday";
		if(i==3)
			return "Tuesday";
		if(i==4)
			return "Wednesday";
		if(i==5)
			return "Thursday";
		if(i==6)
			return "Friday";
		if(i==7)
			return "Saturday";
		return null;
	}

	static String getDay(int i) {
		if(i==1)
			return "Monday";
		if(i==2)
			return "Tuesday";
		if(i==3)
			return "Wednesday";
		if(i==4)
			return "Thursday";
		if(i==5)
			return "Friday";
		if(i==6)
			return "Saturday";
		if(i==7)
			return "Sunday";
		return null;
	}

	static String getDay(String date) {
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Calendar calendar=new GregorianCalendar();
		Date theDate=null;

		try {
    			theDate=format.parse(date);
		} catch(ParseException e) {
			e.printStackTrace();
		}
    	
    		calendar.setTime(theDate);
		int i=calendar.get(Calendar.DAY_OF_WEEK);

		if(i==1)
			return "Sunday";
		if(i==2)
			return "Monday";
		if(i==3)
			return "Tuesday";
		if(i==4)
			return "Wednesday";
		if(i==5)
			return "Thursday";
		if(i==6)
			return "Friday";
		if(i==7)
			return "Saturday";
		return null;
	}

	static int dayNumber(String day) {
        	if(day.equals("Monday"))
    			return 0;
		if(day.equals("Tuesday"))
        		return 1;
        	if(day.equals("Wednesday"))
        		return 2;
        	if(day.equals("Thursday"))
        		return 3;
        	if(day.equals("Friday"))
        		return 4;
        	if(day.equals("Saturday"))
        		return 5;
        	if(day.equals("Sunday"))
        		return 6;
        	return -1;
	}

	static String sysTime() {
		int h,m;
		String hh,mm,a;
		Calendar time=Calendar.getInstance();

		h=time.get(Calendar.HOUR_OF_DAY);
		m=time.get(Calendar.MINUTE);

		if(h<=9)
			hh="0"+Integer.toString(h);
		else
			hh=Integer.toString(h);
		if(m<=9)
			mm="0"+Integer.toString(m);
		else
			mm=Integer.toString(m);
		
		return (hh+":"+mm);
	}

	static void setLookAndFeel() {
	String osName=System.getProperty("os.name").toLowerCase();

		if(osName.startsWith("windows"))
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

		else
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
	}
}
