package travels;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import org.w3c.dom.ls.LSOutput;


public class CrudOperations {
	static Scanner enters =new Scanner(System.in);
	static Map<Integer, String> datashowing =new HashMap<>();
	public static void main(String[] args) 
	{




	}

	public static Map<Integer, String> creatingOperations(String userName, String gender, String userMail, String userPassword, String mobileNumber, Double userAmount) throws SQLException
	{try
	{
		Connection connected = ConnectionObject.getConnecting ();
		PreparedStatement acd = connected.prepareStatement("insert into bata.passengerdata values(?,?,?,?,?,?,?)");
		acd.setString(1, null);
		acd.setString(2, userName);
		acd.setString(3, gender);
		acd.setString(4, userMail);
		acd.setString(5, userPassword);
		acd.setString(6, mobileNumber);
		acd.setDouble(7, userAmount);
		int tablerows = acd.executeUpdate();
		System.out.println(tablerows +"Updated");
		PreparedStatement retrieve = connected.prepareStatement("select * from bata.passengerdata");
		ResultSet shows = retrieve.executeQuery();
		int d = shows.getMetaData().getColumnCount();
		System.out.println(d+"metadata");
		while(shows.next())
		{

			String check=shows.getString(2)+" "+shows.getString(3)+" "+shows.getString(4)
			+" "+shows.getString(5)+" "+shows.getString(6)+" "+shows.getString(7);

			int indexofTable=shows.getInt(1);
			datashowing.put(indexofTable, check);
		}
		return datashowing;
	}
	catch (Exception e) 
	{
		System.out.println(e.getMessage());
		System.out.println("Errors in Inserting New Data Process");
	}
	return datashowing;
	}
	public static boolean dataVerifying(String emailId, String password)
	{
		try {
			Connection datashowing=ConnectionObject.getConnecting();
			PreparedStatement dataCredentials = datashowing.prepareStatement("select * from bata.passengerdata where usermail=? and userPassword=?");
			dataCredentials.setString(1,emailId);
			dataCredentials.setString(2, password);
			ResultSet checkingData= dataCredentials.executeQuery();
			while(checkingData.next())
			{
				System.out.println(checkingData.getString(4)+" "+checkingData.getString(5));
				return true;
			}



		} catch (SQLException e) {
			e.printStackTrace();
		}




		return false; 

	}
	static void changeDate() throws SQLException
	{
		try
		{


			System.out.println("Reschedule Operations\n Enter Random Number");
			int abcd=enters.nextInt();
			enters.nextLine();
			System.out.println(" Please Enter Your Registered Email");
			String mailforEdit=enters.nextLine();


			System.out.println("Enter Your Password");
			String passwordforEdit=enters.nextLine();
			System.out.println(mailforEdit+"mail");
			System.out.println(passwordforEdit+"pass");
			Connection transfer = ConnectionObject.getConnecting();

			PreparedStatement checks = transfer.prepareStatement("select  * from passengerdata where usermail = ? and userPassword = ? ");
			checks.setString(1, mailforEdit);
			checks.setString(2, passwordforEdit);

			ResultSet ifits = checks.executeQuery();
			int idtravel=0;
			String oldBookedDate=null;
			LocalDate hj=LocalDate.now(); 
			Map<Integer, String> abc=new HashMap<>();
			if(ifits.next())
			{
				System.out.println(ifits.getString(1));


				Connection verified=	ConnectionObject.getConnecting();
				PreparedStatement abcd1=verified.prepareStatement("select * from passengerdata join userbookings on passengerdata.id=userbookings.referencekey");
				ResultSet result = abcd1.executeQuery();
				System.out.println("Before Changing Date");
				int routesId=0;

				while(result.next())
				{

					String joiningData=result.getString(2)+" "+result.getString(3)+" "
							+result.getString(4)+" "+result.getString(5)+" "+result.getString(6)+" "+result.getString(7)+" "+result.getString(8)
							+" "+result.getString(9)+" "+result.getString(10)+result.getString(11)+" "+result.getString(12)+" "
							+result.getString(13)+" "+result.getString(14)+" "+result.getString(15)+" "+result.getString(16);
					System.out.println(joiningData);
					routesId=result.getInt(16);
					int first=result.getInt(1);
					abc.put(first, joiningData);

				}
				for (Entry<Integer, String> entry : abc.entrySet())
				{
					Integer key = entry.getKey();
					String val = entry.getValue();
					System.out.println(val);

				}
				System.out.println("Enter Your Route Id to change the Date");

				int changeid=enters.nextInt();
				enters.nextLine();
				LocalDate abn=LocalDate.now();
				System.out.println(abn.toString()+"Present Date");
				System.out.println("Enter New Date like this Format Year-Month-Date");
				String newDate=enters.nextLine();
				LocalDate abnDate=LocalDate.parse(newDate);
				System.out.println(abnDate.toString()+"new date");
				Connection xyz=ConnectionObject.getConnecting();
				PreparedStatement slips = xyz.prepareStatement("update  userbookings set BookedDate = ? , TravelDate = ?  where routeId = ? "
						);
				slips.setString(1, abn.toString());
				slips.setString(2, newDate.toString());
				slips.setInt(3, changeid);
				int datechange = slips.executeUpdate();
				System.out.println("After Changing Date");
				System.out.println(datechange+"Updated");

				
			}
			else
			{
				System.out.println("No Data Found");
			}


			




		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			System.out.println("Errors In Case4");
		}
	}
}






