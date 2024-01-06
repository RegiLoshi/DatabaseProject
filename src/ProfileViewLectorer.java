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

public class ProfileViewLectorer extends Application{
	Lecturer lectuerer;
	private String dbUrl = "jdbc:mysql://127.0.0.1:3306/Database_project";
    private String dbUsername = "root";
    private String dbPassword = "11112003";
    
	public ProfileViewLectorer(Lecturer lectuererr) {
		this.lectuerer = lectuererr;
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		GridPane pane = new GridPane();
		Label username_label = new Label("First Name:");
        TextField first_name = new TextField();
        first_name.setText(lectuerer.getFirstName());
        first_name.setEditable(false);
        Label surname_label = new Label("Last Name:");
        TextField surname = new TextField();
        surname.setEditable(false);
        surname.setText(lectuerer.getLastName());
        Label department_label = new Label("Department:");
        TextField department = new TextField();
        department.setEditable(false);
        department.setText(lectuerer.getDepartment());
        Label course_name_label = new Label("Course:");
        TextField course_name = new TextField();
        course_name.setEditable(false);
        course_name.setText(lectuerer.getCourseName());
        
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(40);
        pane.setVgap(40);
        pane.addRow(0, username_label, first_name);
        pane.addRow(1, surname_label, surname);
        pane.addRow(2, department_label, department);
        pane.addRow(3, course_name_label, course_name);
        
        
        Scene scene = new Scene(pane, 400, 350);
        primaryStage.setTitle("Profile");
        primaryStage.setScene(scene);
        primaryStage.show();
	}
}