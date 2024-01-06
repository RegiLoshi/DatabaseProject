import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ParticipantsView {
	private String dbUrl = "jdbc:mysql://127.0.0.1:3306/Database_project";
    private String dbUsername = "root";
    private String dbPassword = "11112003";
    private String course_code;
    public ParticipantsView(String course_code) {
    	this.course_code = course_code;
    }
    public void start(Stage primaryStage) {
        TableView<Participant> tableView = new TableView<>();
     
        TableColumn<Participant, String> isbnCol = new TableColumn<>("Name");
        isbnCol.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        

        TableColumn<Participant, String> titleCol = new TableColumn<>("Surname");
        titleCol.setCellValueFactory(
                new PropertyValueFactory<>("surname"));

        TableColumn<Participant, Integer> authorCol = new TableColumn<>("Attendance");
        authorCol.setCellValueFactory(
                new PropertyValueFactory<>("attendace"));

        TableColumn<Participant, Character> quantityCol = new TableColumn<>("Retake Status");
        quantityCol.setCellValueFactory(
                new PropertyValueFactory<>("retake_status"));

        tableView.getColumns().add(isbnCol);
        tableView.getColumns().add(titleCol);
        tableView.getColumns().add(authorCol);
        tableView.getColumns().add(quantityCol);

        VBox root = new VBox(tableView);
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Participant View");
        primaryStage.show();

        ArrayList<Participant> participants = fetchParticipantsFromDB(course_code);
        tableView.getItems().addAll(participants);
    }

    private ArrayList<Participant> fetchParticipantsFromDB(String courseCode) {
        ArrayList<Participant> participants = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
            String query = "SELECT * FROM Participants where Course_Code = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, courseCode);
            ResultSet resultSet = statement.executeQuery(); // Execute the query

            while (resultSet.next()) {
                int studentId = resultSet.getInt("Student_ID");
                String retake = resultSet.getString("Retake_status");
                int att = resultSet.getInt("Attendance_percent");
                Participant participant = new Participant(studentId , retake , att); 
                participants.add(participant); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return participants;
    }

    

}
