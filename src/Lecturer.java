import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Lecturer {
	private int lecturerID;
    private String firstName;
    private String lastName;
    private int accountID;
    private String department;
    private String dbUrl = "jdbc:mysql://127.0.0.1:3306/Database_project";
    private String dbUsername = "root";
    private String dbPassword = "11112003";
    private String course_code;
    private Course course;
    
    public Lecturer(int lecturerID, String firstName, String lastName, int accountID , String departmentt) {
        this.lecturerID = lecturerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountID = accountID;
        this.department = departmentt;
        getCourse();
        course = new Course(course_code);
        
    }
    
	public String getDepartment() {
		return department;
	}
	public String getCourseName() {
		return course.getCourseName();
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getCourse_code() {
		return course_code;
	}

	public void setCourse_code(String course_code) {
		this.course_code = course_code;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	private void getCourse() {
		String query = "SELECT * FROM course_Lecturers WHERE Lecturer_ID = ?";
        try {
       	    Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
       	    PreparedStatement statement = connection.prepareStatement(query);
       	    statement.setInt(1, lecturerID);
       	    ResultSet result = statement.executeQuery();
       	    if (result.next()) {
       	    	course_code = result.getString("Course_Code");
       	    	
       	    }
        }catch (SQLException e) {
       	    e.printStackTrace();
       	}
	}

	public int getLecturerID() {
		return lecturerID;
	}
	public void setLecturerID(int lecturerID) {
		this.lecturerID = lecturerID;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getAccountID() {
		return accountID;
	}
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	public String getDepartment_id() {
		return department;
	}

	public void setDepartment_id(String department_id) {
		this.department = department_id;
	}
}
