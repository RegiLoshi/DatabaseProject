import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class DetailedView extends Application{
	ArrayList<Grades> gradesList;
	private String dbUrl = "jdbc:mysql://127.0.0.1:3306/Database_project";
    private String dbUsername = "root";
    private String dbPassword = "11112003";
    
	public DetailedView(ArrayList<Grades> gradesLists) {
		gradesList = gradesLists;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		GridPane pane = new GridPane();
		int i = 0;
		for(Grades grade : gradesList) {
			pane.addRow(i++ , new Label(grade.getType()) , new Label(String.valueOf(grade.getPerc()) + "%") , new Label(String.valueOf(grade.getGrade())) );
		}
        
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(40);
        pane.setVgap(40);     
        Scene scene = new Scene(pane, 300, 450);
        primaryStage.setTitle("Grades");
        primaryStage.setScene(scene);
        primaryStage.show();
}
}
