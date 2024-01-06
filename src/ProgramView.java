import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ProgramView extends Application{
	int program; 
	int studentId;
	private String dbUrl = "jdbc:mysql://127.0.0.1:3306/Database_project";
    private String dbUsername = "root";
    private String dbPassword = "11112003";
    
	public ProgramView(int program , int studentIdd) {
		this.program = program;
		this.studentId = studentIdd;
	}
	
	public void start(Stage primaryStage) {
		
        TableView<Course> tableView = new TableView<>();

        TableColumn<Course, String> column1 = 
        new TableColumn<>("Course Name");
        
        column1.setCellValueFactory(
            new PropertyValueFactory<>("CourseName"));


        TableColumn<Course, String> column2 = 
        new TableColumn<>("Course Code ");
        
        column2.setCellValueFactory(
            new PropertyValueFactory<>("CourseCode"));
        
        TableColumn<Course, String> column3 = 
                new TableColumn<>("Course Credit");
                
                column3.setCellValueFactory(
                    new PropertyValueFactory<>("CourseCredit"));
                
                TableColumn<Course, String> column4 = 
                        new TableColumn<>("Final Grade");
                        
                        column4.setCellValueFactory(
                            new PropertyValueFactory<>("finalGrade"));
                

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);
        
        tableView.setOnMouseClicked(event -> {
        	 Course selectedCourse = tableView.getSelectionModel().getSelectedItem();
        	 DetailedView detailedView = new DetailedView(selectedCourse.getGradesList());
     				Stage stagee = new Stage();
     				try {
						detailedView.start(stagee);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            });
        
        String query = "SELECT Course_Code FROM Courses WHERE Program_ID = ?";
        try {
       	    Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
       	    PreparedStatement statement = connection.prepareStatement(query);
       	    statement.setInt(1, program);
       	    ResultSet result = statement.executeQuery();
       	    while(result.next()) {
       	    	Course course = new Course((result.getString("Course_Code")), program , studentId);
       	    	tableView.getItems().add(course);

       	    	
       	    }
        }catch(SQLException e) {	
        e.printStackTrace();
        }
        
        StackPane root = new StackPane();
        root.getChildren().add(tableView);
        // Create Scene
        Scene scene = new Scene(root, 600, 400);

        // Set scene to stage
        primaryStage.setTitle("Course Table");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
