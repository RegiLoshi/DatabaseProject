import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ProfileView extends Application{
	Student student;
	String advisor_name;
	private String dbUrl = "jdbc:mysql://127.0.0.1:3306/Database_project";
    private String dbUsername = "root";
    private String dbPassword = "11112003";
    
	public ProfileView(Student studentt) {
		this.student = studentt;
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		GridPane pane = new GridPane();
		Label username_label = new Label("First Name:");
        TextField first_name = new TextField();
        first_name.setText(student.getFirstName());
        first_name.setEditable(false);
        Label surname_label = new Label("Last Name:");
        TextField surname = new TextField();
        surname.setEditable(false);
        surname.setText(student.getLastName());
        Label personal_id_label = new Label("Personal ID:");
        TextField personal_id = new TextField();
        personal_id.setEditable(false);
        personal_id.setText(student.getPersonalID());
        Label birthday_label = new Label("Birthday:");
        TextField birthday = new TextField();
        birthday.setEditable(false);
        birthday.setText(student.getBirthday());
        Label reg_date_label = new Label("Registration Date:");
        TextField reg_date = new TextField();
        reg_date.setEditable(false);
        reg_date.setText(student.getRegistrationDate());
        Label scholarship_label = new Label("Scholarship:");
        TextField scholarship = new TextField();
        scholarship.setEditable(false);
        scholarship.setText(student.getScholarship());
        Label payment_status_label = new Label("Payment Status:");
        TextField payment_status = new TextField();
        payment_status.setEditable(false);
        payment_status.setText(student.getPaymentStatus());
        Label department_label = new Label("Department:");
        TextField department = new TextField();
        department.setEditable(false);
        department.setText(student.getDepartment_name());
        Label program_label = new Label("Program:");
        TextField program = new TextField();
        program.setEditable(false);
        program.setText(student.getProgram_name());
        Label advisor_label = new Label("Advisor:");
        TextField advisor = new TextField();
        advisor.setEditable(false);
        int advisor_id = student.getAdvisor();
        String query = "SELECT First_name , Last_name FROM Lecturers WHERE Lecturer_ID = ?";
        try {
       	    Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
       	    PreparedStatement statement = connection.prepareStatement(query);
       	    statement.setInt(1, advisor_id);
       	    ResultSet result = statement.executeQuery();
       	    if (result.next()) {
       	    	advisor_name = result.getString("First_name") + " " + result.getString("Last_name");
       	    }
        }catch (SQLException e) {
       	    e.printStackTrace();
       	}
        advisor.setText(advisor_name);
        
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(40);
        pane.setVgap(40);
        pane.addRow(0, username_label, first_name);
        pane.addRow(1, surname_label, surname);
        pane.addRow(2, personal_id_label, personal_id);
        pane.addRow(3, birthday_label, birthday);
        pane.addRow(4, reg_date_label, reg_date);
        pane.addRow(5, scholarship_label, scholarship);
        pane.addRow(6, payment_status_label, payment_status);
        pane.addRow(7, department_label, department);
        pane.addRow(8, program_label, program);
        pane.addRow(9, advisor_label, advisor);
        
        
        Scene scene = new Scene(pane, 400, 650);
        primaryStage.setTitle("Profile");
        primaryStage.setScene(scene);
        primaryStage.show();
	}
}
