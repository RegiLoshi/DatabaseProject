import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class Course {
	private String CourseName;
	private String CourseCode;
	private int academic_year;
	private int CourseCredit;
	private int program;
	private ArrayList<Grades> grades = new ArrayList<>();
	private int studentId;
	private String dbUrl = "jdbc:mysql://127.0.0.1:3306/Database_project";
    private String dbUsername = "root";
    private String dbPassword = "11112003";
    private double finalGrade = 0;
	
	public String getCourseName() {
		return CourseName;
	}


	public void setCourseName(String course_name) {
		this.CourseName = course_name;
	}


	public String getCourseCode() {
		return CourseCode;
	}


	public void setCourseCode(String course_Code) {
		this.CourseCode = course_Code;
	}


	public int getAcademic_year() {
		return academic_year;
	}


	public void setAcademic_year(int academic_year) {
		this.academic_year = academic_year;
	}


	public int getCourseCredit() {
		return CourseCredit;
	}


	public void setCourseCredit(int course_credit) {
		this.CourseCredit = course_credit;
	}


	public int getProgram() {
		return program;
	}


	public void setProgram(int program) {
		this.program = program;
	}


	public Course(String courseid , int program2 , int studentid) {
		CourseCode = courseid;
		academic_year = 23;
		program = program2;
		studentId = studentid;
		findCourse(courseid);
		getGrades();
		
	}
	
	public Course(String courseid) {
		CourseCode = courseid;
		academic_year = 23;
		program = 11;
		findCourse(courseid);
		
	}
	
	
	private void getGrades() {
		String query = "SELECT * from Assignments WHERE Student_ID = ? AND Course_Code = ?";
        try {
       	    Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
       	    PreparedStatement statement = connection.prepareStatement(query);
       	    statement.setInt(1, studentId);
       	    statement.setString(2, CourseCode);
       	    ResultSet result = statement.executeQuery();
       	    while(result.next()) {
       	    	double perc = result.getInt("Assignment_percent")/100.0;
       	    	grades.add(new Grades(result.getString("Assignment_type") , result.getInt("Assignment_percent"),result.getInt("Grade")));
       	    	finalGrade += (perc * result.getInt("Grade"));
       	    }
        }catch(SQLException e) {	
        e.printStackTrace();
        }
		
	}


	public int getStudentId() {
		return studentId;
	}


	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}


	public double getFinalGrade() {
		return finalGrade;
	}


	public void setFinalGrade(double finalGrade) {
		this.finalGrade = finalGrade;
	}


	public void setGrades(ArrayList<Grades> grades) {
		this.grades = grades;
	}
	
	public ArrayList<Grades> getGradesList(){
		return grades;
	}


	private void findCourse(String courseid) {
		if(courseid.equals("CEN105")) {
			CourseName = "Linear Algebra";
			CourseCredit = 5;
		}else if(courseid.equals("CEN109")) {
			CourseName =  "Introduction to Algorithms & Programming";
			CourseCredit = 7;
		}else if(courseid.equals("ENG103")) {
			CourseName = "Development of Reading and Writing Skills in English I";
			CourseCredit = 4;
		}else if(courseid.equals("MTH101")) {
			CourseName = "Calculus I";
			CourseCredit = 7;
		}else if(courseid.equals("PHY101")) {
			CourseName = "General Physics I";
			CourseCredit = 7;
		}
	}
}
