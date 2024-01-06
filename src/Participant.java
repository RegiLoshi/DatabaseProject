import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Participant {
	private String name;
	private String surname;
	private int id;
	private int attendace;
	private String retake_status;
	private String dbUrl = "jdbc:mysql://127.0.0.1:3306/Database_project";
    private String dbUsername = "root";
    private String dbPassword = "11112003";
public Participant(int id , String retake_status, int attendace ) {
	this.id = id;
	this.attendace = attendace;
	this.retake_status = retake_status;
	getInfo();
}
	private void getInfo() {
		 String query = "SELECT * FROM Students WHERE Student_ID = ?";
         try {
        	    Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        	    PreparedStatement statement = connection.prepareStatement(query);
        	    statement.setInt(1, id);
        	    ResultSet result = statement.executeQuery();
        	    if (result.next()) {
        	    	name = result.getString("First_name");
        	    	surname = result.getString("Last_name");
        	    }
}catch (SQLException e) {
    e.printStackTrace();
}
}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAttendace() {
		return attendace;
	}
	public void setAttendace(int attendace) {
		this.attendace = attendace;
	}
	public String getRetake_status() {
		return retake_status;
	}
	public void setRetake_status(String retake_status) {
		this.retake_status = retake_status;
	}
}
