package travels;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;



import java.util.Scanner;


public class ExploreUser  extends CrudOperations {

	static String userName;

	static String gender;
	static String userMail;
	static String userPassword;
	static String mobileNumber;
	static Double userAmount;
	static Map<String, String> bookingHistory = new HashMap<>();
	// For Continuation
	static boolean view = true;
	// Login Attempts
	static int failedCountLimit = ConstantValues.count;
	static int userfailedcount = 0;
	static Scanner enter = new Scanner(System.in);

	// taking Runtime Data by using Scanner

	public static void main(String[] args) 
	{
		while(view=true)
		{

			try {
				ExploreUser.enteringIntoProcess();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}
	private  static void enteringIntoProcess() throws SQLException 
	{


		System.out.println("Welcome To Explore Travels");
		System.out.println(
				"1.New Admin User Registration  2.Login Account 3.Plan Journey 4.Reschedule Ticket Operations \nPlease Enter Your Option");
		int options =enter.nextInt();
		switch(options)
		{
		//New User Registration and Adding Run Time Data to DataBAse Through JDBC
		case 1:
			try {
				System.out.println("New User Registration");
				System.out.println(
						"Please Enter\n1.Name \n2.Gender  \n3.Mobile Number\n4.EmailId \n5.Password\n6.Amount like this Format");
				System.out.println("Please Enter Random Number");
				int number=enter.nextInt();
				System.out.println(number);
				enter.nextLine();
				System.out.println("Please Enter  Name");
				ExploreUser.userName = enter.nextLine();

				System.out.println(" Please Enter Gender");
				ExploreUser.gender = enter.nextLine();
				ExploreUser.mobileNumber= mobileNumber();




				if (mobileNumber.length() < 10)
				{

					try {
						Mistakes customisedErrors=new Mistakes("Mobile Number should be 10 Digits ");
						throw customisedErrors;
					} catch (Exception e)
					{
						System.out.println(e.getMessage());
						enteringIntoProcess();

					}


				}
				else 
				{



					System.out.println("Please Enter Email id\n "
							+ "Kindly use all small letters  \nPlease Use This Pattern:xyz145@gmail.com");
					ExploreUser.userMail = enter.nextLine();
					userMail= userMail.toLowerCase();

					System.out.println("Please Enter password\n");
					ExploreUser.userPassword = enter.nextLine();



					System.out.println("Please Enter UserWallet Amount");
					ExploreUser.userAmount= enter.nextDouble();

					//Adding New Data to DataBase Through JDBC
					//					 CRUDOperations addData=new CRUDOperations();
					Map<Integer, String> retrievingData = ExploreUser.creatingOperations(userName,gender,userMail,userPassword,mobileNumber,userAmount);

					System.out.println(retrievingData);
				}
			}
			catch (Exception e) 
			{
				System.out.println(e.getMessage());
				System.out.println("Errors In Case1");
			}
			break;

		case 2:
			try
			{
				//Login USing Username and Credentials
				System.out.println("Login Operation");
				System.out.println("Please Enter Random  Number");
				int randoms=enter.nextInt();
				enter.nextLine();
				System.out.println("Enter Your EmailId");
				String emailId = enter.nextLine();
				System.out.println("Password");
				String password = enter.nextLine();

				if (userfailedcount < failedCountLimit)
				{


					boolean checking = ExploreUser.dataVerifying(emailId,password);
					System.out.println(checking);
					//If Data  Exists it enters into If Statement
					if(checking)
					{
						System.out.println("UserMail and User Password  Found In DataBase");
						System.out.println(emailId);
						System.out.println("entering into account");
						System.out.println("Welocome To Explore Travels PLease Book Your Tickets\n");
						System.out.println("Explore Travles Top Tourist Bus Routes");
						System.out.println("1.Vijayawada ->Hyderabad\t   2. Hyderabad-> Vijayawada\n"
								+ "3.Vijayawada -> Chennai\t  4.Chennai-> Vijayawada\n"
								+ "5.Vijayawada -> Bangalore\t  6.Bangalore-> Vijayawada\n"
								+ "7.Vijayawada -> Kochi\t  8.Kochi -> Vijayawada\n"
								+ "9.Vijayawada -> Tirupathi\t   10.Tirupathi-> Vijayawada\n "
								+ "11.Vijayawada -> Vishakapatnam\t  12.Vishakapatnam-> Vijayawada\n"
								+ "13.Vijayawada -> Mysore\t  14.Mysore-> Vijayawada\n"
								+ "15.Vijayawada -> Ooty\t  16.Ooty-> Vijayawada\n"
								+ "17.Hyderabad -> Ooty\t  18.Ooty-> Hyderabad\n" + "19.Bangalore -> Ooty\t  20.Ooty-> Bangalore\n"
								+ "21.Bangalore -> Chennai\t  22.Chennai-> Bangalore\n"
								+ "23.Bangalore -> Tirupati\t  24.Tirupati-> Bangalore\n" + "25.Chennai -> Ooty\t  26.Ooty-> Chennai\n"
								);
						System.out.println("1.My Bookings 2.My Profile 3. Logout ");
					}
					else
					{
						//Returns the Main Page and Count the Limit
						userfailedcount++;
						System.out.println("You Have Only"+(failedCountLimit-userfailedcount)+"times\n"
								+ "Please Enter Correct Credentials");
						enteringIntoProcess();

					}
				}
				else
				{


					//If Limit Exceeds Then Account is Blocked
					System.out.println(
							"Your Account Is Blocked ,Please Visit Our Main Branch OF Explore Travels Or Try Again After 24 hours");
					System.exit(0);
				}
			}
			catch (Exception e) 
			{
				System.out.println(e.getMessage());
				System.out.println("Errors In Case2");
			}


			break;
		case 3:
			try
			{
				//Book Tickets

				System.out.println("Welcome To Explore Travels, PLease Book Your Tickets");
				System.out.println("Explore Travles Top Tourist Bus Routes");
				//Connecting Java To DataBase through JDBC with the help of Driver Manager ,Connection,Statement Classes
				Connection connected = ConnectionObject.getConnecting();
				PreparedStatement statement= connected.prepareStatement("select *   from touristplaces");
				ResultSet x = statement.executeQuery();

				while (x.next())
				{
					System.out.println(x.getString(1)+"   "+x.getString(2)+"->  "+x.getString(3)+"  "+x.getString(4)+"  "+x.getString(5));
				}
				System.out.println("Enter Your Route");
				int busOption = enter.nextInt();
				System.out.println("Enter Your Data in this format\n" + "Year-Month-Date like 2023-05-10");
				String bookingdate = enter.next();

				LocalDate date = LocalDate.now();

				LocalDate checkingDate = LocalDate.parse(bookingdate);

				DayOfWeek t = checkingDate.getDayOfWeek();
				System.out.println(checkingDate.getMonth());
				System.out.println(checkingDate.getDayOfMonth()+"date");
				System.out.println(checkingDate.getDayOfYear()+"out of  365days");
				System.out.println(checkingDate.getMonthValue()+"month");
				System.out.println(checkingDate.getDayOfWeek()+"day");
				System.out.println(checkingDate.getYear()+"year");
				if (checkingDate.getDayOfYear() >= date.getDayOfYear()
						& checkingDate.getMonthValue() >= date.getMonthValue()) {
					System.out.println("Entering into month");
					String checkdate = t.toString();
					System.out.println(checkdate);
					//Checking the Day is Weekend Or Normal Days
					if (checkdate.equalsIgnoreCase("Saturday") | checkdate.equalsIgnoreCase("Sunday")) {
						System.out.println("True+hi");

						try {
							ExploreUser.weekendsBooking(busOption, checkingDate.toString());
						} catch (Exception e) 
						{
							e.printStackTrace();
						}

					}


					else {


						System.out.println("Normal Day");

						try {
							ExploreUser.normalDayBooking(busOption, checkingDate.toString());
						} catch (Exception e) {
							e.printStackTrace();
						} 
					}
				} else {
					try {

						throw new InputMismatchException(
								"Please Enter Current Date Or Future Dates with current year and current month or Upcoming Months ");
					} catch (Exception e) {
						System.out.println(e.getMessage());

					}

					break;
				}
			}
			catch (Exception e)
			{
				System.out.println(e.getMessage());
				System.out.println("Errors In Case3");
			}
			break;
		case 4:
			try
			{


				ExploreUser.changeDate();
			}
			catch (Exception e) 
			{

				System.out.println(e.getMessage());
				System.out.println("Errors In Case4");
			}
			break;
		}





	}
	static 
	{
		String paylog=ConstantValues.logo;
		File ab=new File(paylog);
		FileInputStream logopay = null;
		try 
		{
			logopay = new FileInputStream(ab);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		int logs;
		try {

			while((logs=logopay.read())!=-1)
			{
				char logos=(char) logs;
				System.out.print(logos);
			}
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();

		}

	}
	private static String mobileNumber() {
		System.out.println("Enter Mobile Number");
		ExploreUser.mobileNumber = enter.nextLine();
		return mobileNumber;
	}

	protected static void weekendsBooking(int busOption, String checkingDate)
			throws FileNotFoundException, IOException, SQLException {

		try
		{


			System.out.println( "before seats booking");


			System.out.println("Book Tickets");
			System.out.println("Enter Number Of Tickets");
			int numbers=0;

			Connection connected = ConnectionObject.getConnecting();
			PreparedStatement statement= connected.prepareStatement("select *   from touristplaces where id=?");
			statement.setInt(1, busOption);
			ResultSet x = statement.executeQuery();
			int rate=0;

			int name=0;
			String fromplaces =null;
			String toDestination=null;
			while (x.next())
			{
				String routes=x.getString(1)+"   "+x.getString(2)+"->  "+x.getString(3)+"  "+x.getString(4)+"  "+x.getString(5);
				numbers=x.getInt(5);
				System.out.println(routes);
				System.out.println(numbers+"seats Available");
				rate=x.getInt(4);
				name=x.getInt(1);

				fromplaces=x.getString(2);
				toDestination=x.getString(3);
			}
			int seatnumber = enter.nextInt();

			if(numbers==0)
			{
				System.out.println("No Seats Available Choose Another Bus");


			}
			else
			{

				PreparedStatement seatUpdate= connected.prepareStatement("update touristplaces set SeatsAvailable = ? where id = ?");
				seatUpdate.setInt(1, numbers-seatnumber);
				seatUpdate.setInt(2, name);
				int dfg=seatUpdate.executeUpdate();
				System.out.println(dfg+"seatsupdated");




				System.out.println(
						"Choose Your  Amount Transaction Option\n " + "1.ExploreTravlesWallet 2.Debit Card 3.UPI Payments");
				System.out.println(
						"4.If You are a New User Then Create Your Account In Explore Travels OR PayThrough Debit Card OR UPI");
				int planoptions=enter.nextInt();
				switch(planoptions)
				{
				case 1:
					System.out.println("Enter Your Email Id While Creating Explore Travels Account");
					String correctemail = enter.next();
					PreparedStatement userDataStatement=connected.prepareStatement("select * from passengerdata where usermail=?");
					userDataStatement.setString(1, correctemail);
					ResultSet rest = userDataStatement.executeQuery();
					double totalamount=0;
					while(rest.next())
					{

						Connection newConnections = ConnectionObject.getConnecting();
						Statement y = newConnections.createStatement();
						ResultSet i=y.executeQuery("select count(*) from passengerdata");

						System.out.println(rest.getString(1)+"   "+rest.getString(2)+"->  "+rest.getString(3)+"  "+rest.getString(4)+"  "+rest.getString(5));
						totalamount=((ConstantValues.gst*rate)+rate)*seatnumber;
						System.out.println(totalamount+"for tickets"+seatnumber+"seats");
						double total=rest.getInt(7)-totalamount;
						System.out.println(totalamount+"TicketRate");

						System.out.println(total+"available amount");

						PreparedStatement seting= connected.prepareStatement("update passengerdata set userAmount=? where usermail=?");
						seting.setDouble(1, total);
						seting.setString(2, correctemail);
						int r = seting.executeUpdate();
						System.out.println(r +"rows updated");
						LocalDateTime dated = LocalDateTime.now();
						PreparedStatement setings= connected.prepareStatement("INSERT INTO userbookings VALUES (? , ?, ?, ?, ?, ?, ?, ?,?);");
						setings.setString(1, null);
						setings.setString(2,fromplaces );
						setings.setString(3, toDestination);
						setings.setString(4,dated.toString());
						setings.setString(5,checkingDate );
						setings.setDouble(6, totalamount);
						setings.setString(7, rest.getString(1));
						setings.setInt(8, seatnumber);
						setings.setInt(9, busOption);
						int b = setings.executeUpdate();

						System.out.println(b+"updated");



					}

					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;

				}

			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("Errors in Case3");
		}
	}
	public static void normalDayBooking(int busOption, String checkingDate) throws SQLException 
	{
		try
		{



			System.out.println( "before seats booking");


			System.out.println("Book Tickets");
			System.out.println("Enter Number Of Tickets");
			int numbers=0;
			Connection connected = ConnectionObject.getConnecting();
			PreparedStatement statement= connected.prepareStatement("select *   from touristplaces where id=?");
			statement.setInt(1, busOption);

			ResultSet checkingroute = statement.executeQuery();
			int rate=0;
			int name=0;
			String fromPlace =null;
			String toDestination=null;
			while (checkingroute.next())
			{
				String routes=checkingroute.getString(1)+"   "+checkingroute.getString(2)+"->  "+checkingroute.getString(3)+"  "+checkingroute.getString(4)+"  "+checkingroute.getString(5);
				System.out.println(routes);
				numbers=checkingroute.getInt(5);
				System.out.println(numbers+"seats Available");
				rate=checkingroute.getInt(4);
				name=checkingroute.getInt(1);
				fromPlace=checkingroute.getString(2);
				toDestination=checkingroute.getString(3);
			}
			int seatnumber = enter.nextInt();

			if(numbers==0)
			{
				System.out.println("No Seats Available Choose Another Bus");


			}
			else
			{
				System.out.println("entering booking");

				PreparedStatement seatUpdate= connected.prepareStatement("update touristplaces set SeatsAvailable = ? where id = ? ");
				seatUpdate.setInt(1, numbers-seatnumber);
				seatUpdate.setInt(2, name);
				int dfg=seatUpdate.executeUpdate();
				System.out.println(dfg+"seatsupdated");





				System.out.println(
						"Choose Your  Amount Transaction Option\n " + "1.ExploreTravlesWallet 2.Debit Card 3.UPI Payments");
				System.out.println(
						"4.If You are a New User Then Create Your Account In Explore Travels OR PayThrough Debit Card OR UPI");
				int planoptions=enter.nextInt();
				switch(planoptions)
				{
				case 1:
					System.out.println("Enter Your Email Id While Creating Explore Travels Account");
					System.out.println("enter Random Number");
					int x=enter.nextInt();
					enter.nextLine();
					System.out.println("Enter Your Email Id While Creating Explore Travels Account");
					String correctemail = enter.nextLine();
					PreparedStatement userDataStatement=connected.prepareStatement("select * from passengerdata where usermail=?");
					userDataStatement.setString(1, correctemail);
					ResultSet rest = userDataStatement.executeQuery();
					double totalamount=0;
					while(rest.next())
					{

						System.out.println(rest.getString(1)+"   "+rest.getString(2)+"->  "+rest.getString(3)+"  "+rest.getString(4)+"  "+rest.getString(5));
						totalamount=rate*seatnumber;
						System.out.println(totalamount+"for tickets"+seatnumber+"seats");
						double total=rest.getInt(7)-totalamount;
						System.out.println(totalamount+"TicketRate");
						System.out.println("Payment Successfull\n");
						System.out.println(total+"available amount");

						PreparedStatement seting= connected.prepareStatement("update passengerdata set userAmount=? where usermail=?");
						seting.setDouble(1, total);
						seting.setString(2, correctemail);
						int r = seting.executeUpdate();
						System.out.println(r +"rows updated");
						LocalDate dated = LocalDate.now();
						PreparedStatement setings= connected.prepareStatement("INSERT INTO userbookings VALUES (?,?,?,?,?,?,?,?,?);");
						setings.setString(1, null);
						setings.setString(2,fromPlace );
						setings.setString(3, toDestination);
						setings.setString( 4,dated.toString());
						setings.setString(5,checkingDate);
						setings.setDouble(6, totalamount);
						setings.setInt(7, rest.getInt(1));
						setings.setInt(8, seatnumber);
						setings.setInt(9, busOption);
						int b = setings.executeUpdate();

						System.out.println(b+"updated");


					}
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("Errors in Case3");
		}
	}
}