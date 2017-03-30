package Leisure;

import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.sql.ResultSet;


public class leisure_DB {

	//sql
	final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	final String DB_URL = "jdbc:mysql://localhost:3306/leisure?useSSL=false";
	final String USER_NAME = "root";
	final String PASSWORD = "password";
	Connection conn = null;

	// Check what you have titled the database and where/what schema it is on
	//"jdbc:mysql://localhost:3306/Leisure?useSSL=false";
	public leisure_DB(){

	}

	// Remember to close in order of Resultset, Prepared Statement, Connection
	//Connection to the database
	private void connectDB(){

		try{
			Class.forName(JDBC_DRIVER);
			System.out.println("Driver Registered");

			conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			System.out.println("Connected");
		}
		catch (ClassNotFoundException cnfe){
			System.out.println("Could not load driver.\n" + cnfe.getMessage());
		}
		catch (SQLException e) {
			System.out.println("Problem with SQL.\n" + e.getMessage());
		}

	}

	//closing of connection to database
	private void disconnectDB(){
		try{
			if(conn != null){
				conn.close();
				System.out.println("Connection closed.");
			}
		}
		catch (Exception e){
			System.out.println("Could not close connection.\n" + e.getMessage());
		}
	}

	
	
	
	public String getClassName(int classID){
		connectDB();
		java.sql.PreparedStatement pState = null;
	
		try {

			String sql = "SELECT class_description FROM class WHERE class.class_id = ? ;";
			pState = conn.prepareStatement(sql);
			pState.setInt(1, classID);
			ResultSet rs = pState.executeQuery();
			rs.next();
			return rs.getString("class_description");
		}
		catch(SQLException se){
			System.out.println(se.getMessage());
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			disconnectDB();
		}
		return "ERROR";
	}
	
	
	

	public String queryAllAreas(){
		connectDB();
		try{
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			System.out.println("Statement object created");

			StringBuilder full = new StringBuilder();

			ResultSet rs = stmt.executeQuery("SELECT * FROM leisure.area");
			while(rs.next() != false){
				// Can get columns by name, or number which starts at 1
				String id = rs.getString("area_ID");
				String areaName = rs.getString("area_description");
				String cap = rs.getString("area_capacity");
				full.append(id + " " + areaName + " " + cap + '\n');
			}
			String end = full.toString();
			disconnectDB();
			return end;
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		disconnectDB();
		return "NO VALUES";
	}

	public void addArea(String s,int j){
		connectDB();
		try{
			Statement stmt = conn.createStatement();
			System.out.println("Statement object created"); 

			stmt.executeUpdate("USE leisure");
			stmt.executeUpdate("INSERT INTO leisure.area (area_description,area_capacity) VALUES(\'" + s +"\',\'" +j +"\')");
			stmt.close();
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		disconnectDB();	

	}

	
	public void updateArea(String desc, int cap, int a_id){
		connectDB();
		
		java.sql.PreparedStatement pState = null;
		try{
		
			String sql = "UPDATE area SET area_description = ? , area_capacity = ? WHERE area_ID = ?;";
			

			System.out.println("Statement object created"); 

			pState = conn.prepareStatement(sql);
			
			pState.setString(1, desc);
			pState.setInt(2, cap);
			pState.setInt(3, a_id);
			pState.execute();
			
			pState.close();
			
		}
		catch(SQLException se){
			System.out.println(se.getMessage());
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			disconnectDB();
		}
		
	}


	//Remember to thank Hugh J! 
	public String lookAt(int col){
		connectDB();
		try{
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			System.out.println("Statement object created");

			StringBuilder full = new StringBuilder();

			ResultSet rs = stmt.executeQuery("SELECT * FROM leisure.area WHERE area_ID = '" + col + "';");
			if(rs.next()){
				// Can get columns by name, or number which starts at 1
				String id = rs.getString("area_ID");
				String areaName = rs.getString("area_description");
				String cap = rs.getString("area_capacity");
				full.append(id + " " + areaName + " " + cap + '\n');
			}
			String end = full.toString();
			disconnectDB();
			return end;
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		disconnectDB();
		return "NO VALUES";
	}

	
	//Also string date must be in the form of yyyy-mm-dd otherwise CRASHHHHHHHHHH
	
	
	//booking_id booking_date customer_ID bschedule_date bschedule_class_id
	/*public void bookGui(String shceduleDate,int scClass){
		connectDB();
		
		LocalDate p = LocalDate.now();
		String sDate = p.toString();
		
		
		
		java.sql.PreparedStatement pState1 = null;
		java.sql.PreparedStatement pState2 = null;
		java.sql.PreparedStatement pState3 = null;
		java.sql.PreparedStatement pState4 = null;
		
		java.sql.Date sqlDate = java.sql.Date.valueOf(sDate);
		
		String sqlSetValue = "SELECT class_id FROM leisure.schedule WHERE schedule.class_id = ? AND schedule.date = ?";
		
		String sqlUpdate = "INSERT INTO leisure.booking (booking_date, class_ID,area_ID, customer_ID) VALUES (?, ?, ?, ?);";
		
		try{
			
			pState2 = conn.prepareStatement(sqlUpdate);

			pState2.setDate(1, sqlDate);
			

			pState2.execute();
		}
		catch(SQLException se){
			System.out.println(se.getMessage());
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			disconnectDB();
		}
	}*/
	

	public void createCustomer(String name, String phone, int cost,int memID ){
		// cost_ID needed
		// member_ID optional
		// is_A_Member needed
		
		connectDB();
		java.sql.PreparedStatement pState = null;
		
		String sql = "INSERT INTO customer (customer_name, customer_phone, cost_ID, member_ID) VALUES (?, ?, ?, ?);";
		
		//team4_lesiurecentre.booking
		try {

			pState = conn.prepareStatement(sql);

			pState.setString(1, name);
			pState.setString(2, phone);
			pState.setInt(3, cost);
			pState.setInt(4, memID);
			pState.execute();
			pState.close();
		}
		catch(SQLException se){
			System.out.println(se.getMessage());
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			disconnectDB();
		}
	}
	
	public int getCustomerID(String name, String phone, int cost,int memID ){
		
		connectDB();
		java.sql.PreparedStatement pState = null;
		
		String sql = "SELECT customer_ID FROM customer WHERE customer_name = ? AND customer_phone = ? AND cost_ID = ? AND member_ID = ?;";
		int output = -1;
		//team4_lesiurecentre.booking
		try {

			pState = conn.prepareStatement(sql);
			
			pState.setString(1, name);
			pState.setString(2, phone);
			pState.setInt(3, cost);
			pState.setInt(4, memID);
			
			ResultSet rs = pState.executeQuery();
			
			rs.next();
			
			output = rs.getInt("customer_ID");
			rs.close();
			pState.close();
			
			return output;
		}
		catch(SQLException se){
			System.out.println(se.getMessage());
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			disconnectDB();
		}
		return output;
	}

	public String checkBooking(String name, String phone, String date){
		
		connectDB();
		java.sql.PreparedStatement pState = null;
		java.sql.PreparedStatement pState2 = null;
		try {

		
			String sql = "SELECT COUNT(customer_name) FROM customer WHERE customer_name = ? AND customer_phone = ?;";
			
			pState = conn.prepareStatement(sql);
			pState.setString(1, name);
			pState.setString(2, phone);
			ResultSet rs = pState.executeQuery();
			rs.next();
			int out = rs.getInt(1);
			
			rs.close();
			pState.close();
			
			if(out > 1){
			
			String sql2 = "SELECT bschedule_class_id, bschedule_date FROM booking WHERE bschedule_date = ? AND booking.customer_id = customer.customer_id;";
			pState2 = conn.prepareStatement(sql2);
			pState2.setString(1,date);
			ResultSet rs2 = pState.executeQuery();
			StringBuilder sB = new StringBuilder();
			String dOut = new String();
		
			while(rs2.next()){
				sB.append(rs2.getString("bschedule_date") + " ");
				sB.append(rs2.getInt("bschedule_class_id") + "\n" );
			}
			
			dOut = sB.toString();
			
			return "Possible Double booking: " + dOut;
			}
			else{
				return "No double bookings made";
			}
		}
		catch(SQLException se){
			System.out.println(se.getMessage());
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			disconnectDB();
		}
		return "Output ERROR";
	}
		
	

	public void createBooking(int custid, String bScheduleDate, int classid){
		connectDB();
		java.sql.PreparedStatement pState = null;
		java.sql.Date sqlDate = java.sql.Date.valueOf(today());
		java.sql.Date sqlSchedDate = java.sql.Date.valueOf(bScheduleDate);
		
		String sql = "INSERT INTO leisure.booking (booking_date, bschedule_class_id, bschedule_date, customer_ID) VALUES (?, ?, ?, ?);";
		
		//team4_lesiurecentre.booking
		try {

			pState = conn.prepareStatement(sql);

			pState.setDate(1, sqlDate);
			pState.setInt(2, classid);
			pState.setDate(3, sqlSchedDate);
			pState.setInt(4, custid);
			pState.execute();

			pState.close();
		}
		catch(SQLException se){
			System.out.println(se.getMessage());
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			disconnectDB();
		}
	}

	public int checkCapacity(int classNum){
		connectDB();
		java.sql.PreparedStatement pState = null;
		int classSize = -1;
		try {

			String sql = "SELECT capacity FROM class WHERE class.class_id = ? ;";
			//class_ID 
			//capacity

			pState = conn.prepareStatement(sql);
			pState.setInt(1, classNum);
			ResultSet rs = pState.executeQuery();
			while (rs.next()) {
				classSize = rs.getInt("capacity");

			}
			
			rs.close();
			pState.close();
			return classSize;   
		}
		catch(SQLException se){
			System.out.println(se.getMessage());
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			disconnectDB();
		}

		//if this is the value that gets printed something is wrong
		// this is for error checking on the java side
		return -1;
	}

	// Tells us if the class is fully booked
	// by adding the total number of bookings for the class
	// and comparing them to the class' capacity
	// needs another method to prevent adding bookings to fully
	// booked classes
	/**
	 * Don't forget TIME!!!! Pull it from schedule.time column 
	 * it uses the DATETIME format so "yyyy-mm-dd hh:mm:ss"
	 * Space between day and time.
	 * @param classType
	 * @param date
	 * @return
	 */
	public boolean isFull(int classType,String date){
		connectDB();
		java.sql.PreparedStatement pState = null;
		java.sql.PreparedStatement pState2 = null;
		java.sql.PreparedStatement pState3 = null;

		
		int counter = classType;
		int secCo = classType;
		int train = -1;
		
		try {
			String sql = "SELECT capacity FROM class WHERE class.class_id = ? ;";
			String sql2 = "SELECT COUNT(booking.bschedule_class_id) AS totalB FROM booking WHERE booking.bschedule_class_id = ? AND booking.bschedule_date = ?;";
			String sql3 = "SELECT COUNT(schedule.date) AS tDay FROM schedule WHERE schedule.date = ? AND schedule.class_ID = ?;";
			//bschedule_date
			
			pState = conn.prepareStatement(sql);
			pState.setInt(1, classType);
			ResultSet rs = pState.executeQuery();
			while (rs.next()) {
				classType = rs.getInt("capacity");
			}
			pState2 = conn.prepareStatement(sql2);
			pState2.setInt(1,counter);
			pState2.setString(2, date);
			ResultSet rs2 = pState2.executeQuery();
			while (rs2.next()) {
				counter = rs2.getInt("totalB");

			}
			pState3 = conn.prepareStatement(sql3);
			pState3.setString(1, date);
			pState3.setInt(2, secCo);
			ResultSet rs3 = pState3.executeQuery();
			while(rs3.next()){
				train = rs3.getInt("tDay");
			}
			
			rs.close();
			rs2.close();
			rs3.close();
			pState.close();
			pState2.close();
			pState3.close();

		}
		catch(SQLException se){
			System.out.println(se.getMessage());
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			disconnectDB();
		}
		
		if(classType == counter || train == 0){

			return true;
		}
		else{
			return false;
		}

	}
	
	@SuppressWarnings("static-access")
	public String setPassword(String input){
		Encrypt e = new Encrypt();
		String newPassword;
		
		if(input == null || input == ""){
			
			UIManager.put("OptionPane.messageFont", new Font("TimesNewRoman",Font.BOLD,20));
			UIManager.put("OptionPane.buttonFont", new Font("SansSerif",Font.BOLD,20));
			JOptionPane.showMessageDialog(null,"NO PASSWORD ENTERED\nDEFAULT PASSWORD IS SET AS:\npassword","Warning! No Password Entered",JOptionPane.WARNING_MESSAGE);
			
			return e.getMD5("password");		
			}
		else{

			newPassword = e.getMD5(input);
			return newPassword;
		}
	}
		
	@SuppressWarnings("static-access")
	public boolean getPassword(int pID, String password){
		connectDB();
		java.sql.PreparedStatement pState = null;
		
		try {
			String sql = "SELECT password FROM leisure.password WHERE password.password_ID = ? ;";
			
			//bschedule_date
			
			String isCorrect = new String();
			String comp = new String();
			
			pState = conn.prepareStatement(sql);
			pState.setInt(1, pID);
			ResultSet rs = pState.executeQuery();
			while (rs.next()) {
				isCorrect = rs.getString("password");
			}

			rs.close();
			pState.close();
			
			
			Encrypt e = new Encrypt();
			comp = e.getMD5(password);

			
			if(comp.equals(isCorrect)){
				System.out.println("THIS IS THE CORRECT PASSWORD");
				return true;
			}
			else{
				System.out.println("THIS IS NOT THE CORRECT PASSWORD");
				return false;
			}
		}
		catch(SQLException se){
			System.out.println(se.getMessage());
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			disconnectDB();
		}
		return false;
	}
	
	public int getMembershipType(String valIn){
		connectDB();
		java.sql.PreparedStatement pState = null;
		
		int mID = -1;
		try{
			String sql = "SELECT membership_ID FROM membership WHERE type = ? ;";
			
			pState = conn.prepareStatement(sql);
			pState.setString(1, valIn);
			ResultSet rs = pState.executeQuery();
			
			
			while(rs.next() != false){
			
				mID = rs.getInt("membership_ID");
				
			}
			
			
		}
		catch(SQLException se){
			System.out.println(se.getMessage());
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			disconnectDB();
		}
		return mID;
	}
	
	public void addMember(int typeMembership, String fName, String lName, String dateOfBirth, String address, String phone, double balance){
		connectDB();
		java.sql.PreparedStatement pState = null;
		try {
			String sql = "INSERT INTO member (membership_ID, member_forename, member_surname, member_address, member_phone, member_balance, member_DOB) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
			//dates are yyyy-mm-dd
				
			
			pState = conn.prepareStatement(sql);
			pState.setInt(1, typeMembership);
			pState.setString(2, fName);
			pState.setString(3, lName);
			pState.setString(4, address);
			pState.setString(5, phone);
			pState.setDouble(6, balance);
			pState.setString(7, dateOfBirth);
			
			pState.executeUpdate();

			pState.close();
					
		}
		catch(SQLException se){
			System.out.println(se.getMessage());
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			disconnectDB();
		}	
	}
	
	
	public String getMemDetails(int memID){
		connectDB();
		java.sql.PreparedStatement pState = null;
		StringBuilder b = new StringBuilder();
		String out = new String();
		try{
			String sql = "SELECT * FROM member WHERE member_ID = ?";
			pState = conn.prepareStatement(sql);
			pState.setInt(1, memID);
			
			ResultSet rs = pState.executeQuery();
			
			rs.next();
			
			//b.append(rs.getInt(1) + "/");
			b.append(rs.getInt(2) + "/");
			b.append(rs.getString(3) + "/");
			b.append(rs.getString(4) + "/");
			b.append(rs.getString(5) + "/");
			b.append(rs.getString(6) + "/");
			b.append(rs.getString(8));
			
			
			rs.close();
			pState.close();
			out = b.toString();
		}
		catch(SQLException se){
			System.out.println(se.getMessage());
			
				UIManager.put("OptionPane.messageFont", new Font("TimesNewRoman",Font.BOLD,20));
				UIManager.put("OptionPane.buttonFont", new Font("SansSerif",Font.BOLD,20));
				JOptionPane.showMessageDialog(null,"Member does not exist","Member Not Found",JOptionPane.WARNING_MESSAGE);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			disconnectDB();
		}	
		return out;
	}
	
	public void editMember(int mem, int typeMembership, String fName, String lName, String dateOfBirth, String address, String phone){
		connectDB();
		java.sql.PreparedStatement pState = null;
		
		
		try {
			String sql = "UPDATE member SET membership_ID = ?, member_forename = ?, member_surname = ? , member_address = ?, member_phone = ?, member_DOB = ? "
					+ "WHERE member.member_id = ?;";
			//dates are yyyy-mm-dd
			
			pState = conn.prepareStatement(sql);
			pState.setInt(1, typeMembership);
			pState.setString(2, fName);
			pState.setString(3, lName);
			pState.setString(4, address);
			pState.setString(5, phone);
			pState.setString(6, dateOfBirth);
			pState.setInt(7, mem);
			
			pState.executeUpdate();

			pState.close();
					
		}
		catch(SQLException se){
			System.out.println(se.getMessage());
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			disconnectDB();
		}	
	}
	
	public void deleteMember(int memberID){
		connectDB();
		java.sql.PreparedStatement pState = null;
		
		
		try {
			String sql = "DELETE FROM member WHERE member_ID = ?";
			pState = conn.prepareStatement(sql);
			pState.setInt(1, memberID);
			pState.execute();
			pState.close();
		}
		catch(SQLException se){
			System.out.println(se.getMessage());
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			disconnectDB();
		}
	}
	
	
	public String getSchedule(String d){
		
		connectDB();
		java.sql.PreparedStatement pState = null;
		String end = null;
		ResultSet rs = null;
		
		try{
			String sql = "SELECT schedule_description, startTime FROM schedule WHERE date = ?;";
			
			pState = conn.prepareStatement(sql);
			pState.setString(1, d);
			
			rs = pState.executeQuery();
			StringBuilder out = new StringBuilder();
			
			while(rs.next() != false){
				
				String desc = rs.getString("schedule_description");
				//String classID = rs.getString("class_id");
				//java.sql.Date d1 = rs.getDate("time");
				//int h = rs.getDate("time").getHours();
				Timestamp tim = rs.getTimestamp("startTime");
				java.util.Date t = new java.util.Date(tim.getTime());
				
				out.append("Description: " + desc + " Time: " + t + "\n");
				
			}
			end = out.toString();
			
			
			rs.close();
			pState.close();
			
		}
		catch(SQLException se){
			System.out.println(se.getMessage());
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			disconnectDB();
		}	
		
		if(end != null){
			return end;
		}
		else{
			return null;
		}
	}
	
	public JTable makeScheduleTable(String d){
		
		
		JTable t = new JTable();
		connectDB();
		java.sql.PreparedStatement pState = null;
		
		ResultSet rs = null;
		
		
		try{
			@SuppressWarnings("serial")
			TableModel m = new DefaultTableModel(new String[]{"Class","Start Time","End Time"},0){
				public boolean isCellEditable(int rowIndex, int mColIndex) {
			        return false;
			      }
			};
				
			
			
			String sql = "SELECT schedule.startTime, schedule.endTime, class.class_description FROM schedule, class WHERE schedule.date = ? AND schedule.class_id = class.class_id;";
			
			pState = conn.prepareStatement(sql);
			pState.setString(1, d);
			rs = pState.executeQuery();
			
			while(rs.next() != false){
				
				String outputStartTime = rs.getString("schedule.startTime");
				
				String[] oT1 = outputStartTime.split(" ");
				//String p1 = oT1[0]; // yyyy-mm-dd 
				String p2 = oT1[1]; // hh:mm:ss.s
				
				String[] oT2 = p2.split("\\.");
				String p3 = oT2[0];
				
				
				String outputEndTime = rs.getString("schedule.endTime");
				String[] oT3 = outputEndTime.split(" ");
				//String p4 = oT3[0]; // yyyy-mm-dd 
				String p5 = oT3[1]; // hh:mm:ss.s
				
				String[] oT4 = p5.split("\\.");
				String p6 = oT4[0];
				
				
				//class_description
				String outClass = rs.getString("class_description");
				((DefaultTableModel) m).addRow(new Object[]{outClass,p3,p6});
				
			}
		
			t.setModel(m);
			rs.close();
			pState.close();
			
		}
		catch(SQLException se){
			System.out.println(se.getMessage());
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			disconnectDB();
		}
		
		
		t.setFont(new Font("Tahoma",Font.ITALIC,18));
		
		t.getTableHeader().setFont(new Font("SansSerif", Font.ITALIC, 18));
		
		return t;
		
		
	}
	
	
	public String[] getClasses(){
		
		connectDB();
		java.sql.PreparedStatement pState = null;
		ArrayList<String> classList = new ArrayList<String>();
		String[] outClasses = new String[classList.size()];
		ResultSet rs = null;
	
		
		try{
			String sql = "SELECT class_description FROM class;";
			
			pState = conn.prepareStatement(sql);	
			rs = pState.executeQuery();
			while(rs.next() != false){
			
			
			 String s = rs.getString("class_description");
			 
			 classList.add(s);			 
			 System.out.println(s);
			 
			}
			
			outClasses = classList.toArray(outClasses);
			
			rs.close();
			pState.close();
			
		}
		catch(SQLException se){
			System.out.println(se.getMessage());
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			disconnectDB();
		}	
		
		return outClasses;
	}
	
	public String[] getMemberships(){
		
		connectDB();
		java.sql.PreparedStatement pState = null;
		ArrayList<String> memTyList = new ArrayList<String>();
		String[] outMems = new String[memTyList.size()];
		ResultSet rs = null;
	
		
		try{
			String sql = "SELECT type FROM membership;";
			
			pState = conn.prepareStatement(sql);	
			rs = pState.executeQuery();
			while(rs.next() != false){
			
			
			 String s = rs.getString("type");
			 
			 memTyList.add(s);			 
			 //Test to see if it was working
			 //System.out.println(s);
			 
			}
			
			outMems = memTyList.toArray(outMems);
			
			rs.close();
			pState.close();
			
		}
		catch(SQLException se){
			System.out.println(se.getMessage());
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			disconnectDB();
		}	
		
		return outMems;
			
	}
	
	
	public String today(){
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static void main(String[] args) {

		//LocalDate p = LocalDate.now();
		//System.out.print(p.toString());

		leisure_DB l = new leisure_DB();

		
		
		
		
		l.createBooking(16, "2017-03-30", 6);
		
		//System.out.println(l.setPassword(""));
		//    5f4dcc3b5aa765d61d8327deb882cf99 = "password"
		
		//System.out.println(l.getMemDetails(31));
		
		
		//System.out.println(l.getMembershipType("Gym Only Adult"));
		//System.out.println(l.getClasses());
		
		//System.out.println(l.today());
		
		//System.out.println(l.getSchedule("2017-03-11"));
		
		
		//System.out.println("\n\nDo passwords match: " + l.getPassword(1,"Blues"));
		
		//l.addMember(1, "Billy", "Kid", "1859-05-20", "USA USA USA!!!", "014565888", 50.50);
		//l.editMember(30, 1, "Enda", "Kenny", "1945-02-03", "Mayo", "Gov ext 05121", 2000.60);
		
		// 12 Hall_Half2 25 0
		//l.updateArea("Hall_Half2", 25, 12);
		
		//System.out.println(l.queryAllAreas());


		//System.out.println(l.checkCapacity(6));

		//System.out.println("\n" + l.isFull(1,"2017-03-11"));
		
		
		//Get time functions:
		//Date d1 = new Date();
		
		//d1.getHours();
		
		
	//	System.out.println("\nTime: " +d1.getHours() + ":" +d1.getMinutes() + " " +d1.getTime());

		//l.updateBooking("2017-03-28",1,1,1);


		//l.addArea("Blues",20);

		//System.out.println(l.queryAllAreas());


		//System.out.println(l.lookAt(2));


		//l.updateArea("HELELELELE", "Blues");

		//System.out.println(l.queryAllAreas());

	}
}
