
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.*;

public class LoginView extends Application {
    private Stage loginOrSignUpStage;
    private TextField email;
    private PasswordField password;
    private Button signIn;
    private Button reset;
    private Label emailLabel;
    private Label passwordLabel;
    private Label welcomeLabel;
    private String role;
    private ImageView logo;
    private Label slogan;
    private String dbUrl = "jdbc:mysql://127.0.0.1:3306/Database_project";
    private String dbUsername = "root";
    private String dbPassword = "11112003";
    private int studentId;
    private String name;
    private String surname;
    private String department;
    private int user_id;
    private String lecturer_name;
    private String lecturer_surname;
    private int lecturer_id;
    private String lecturer_department;
    private Date birthday;
    private Date reg_date;
    private float scholarship;
    private String payment_status;
    private int department_id;
    private int Program_ID;
    private int Advisor;
    private String program;
    private int fee;
	private String personal_id;

    public static void show() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        loginOrSignUpStage = primaryStage;
        initializeComponents();
        setComponentPropertiesAndListeners();
        addComponentsAndConfigureStage();
    }

    private void initializeComponents() {
    	Image image = new Image("logo.png");
        logo = new ImageView(image);
        logo.setFitHeight(120);
        logo.setFitWidth(154);

        email = new TextField();
        email.setPromptText("Email");
        password = new PasswordField();
        password.setPromptText("Password");
        signIn = new Button("Log In");
        reset = new Button("Reset");

        emailLabel = new Label("Email:");
        passwordLabel = new Label("Password:");
        welcomeLabel = new Label("WELCOME");
        welcomeLabel.setStyle("-fx-font-size: 31px; -fx-text-fill: #556b2f;");

        slogan = new Label("Connecting Classrooms, Bridging Futures");
        slogan.setStyle("-fx-font-weight: bold; -fx-text-fill: #FFFFFF;");

        VBox panel = new VBox();
        panel.setStyle("-fx-background-color: #556b2f;");
        panel.getChildren().addAll(logo, slogan);
        panel.setAlignment(Pos.CENTER);
        panel.setSpacing(10);
        panel.setPrefWidth(400);
        panel.setPrefHeight(500);

        VBox formLayout = new VBox();
        formLayout.setSpacing(10);
        formLayout.setAlignment(Pos.CENTER);
        formLayout.getChildren().addAll(
                welcomeLabel, emailLabel, email, passwordLabel, password, signIn, reset
        );

        HBox root = new HBox(panel, formLayout);
        root.setSpacing(120);
        root.setStyle("-fx-background-color: #FFFFFF;");
        loginOrSignUpStage.setScene(new Scene(root, 800, 500));
        loginOrSignUpStage.setTitle("Connect!");
        loginOrSignUpStage.setResizable(false);
    }

    private void setComponentPropertiesAndListeners() {
        signIn.setOnAction(e -> {
			try {
				performLogin();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
        reset.setOnAction(e -> {
            email.clear();
            password.clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Reset Successful!");
            alert.showAndWait();
        });  
    }

    private void addComponentsAndConfigureStage() {
        loginOrSignUpStage.show();
    }

    private void performLogin() throws Exception {
        	String user_email = email.getText();
        	String user_password = password.getText();      
             try {
             	Class.forName("com.mysql.cj.jdbc.Driver");
             }catch(ClassNotFoundException e) {
            	 Alert alert = new Alert(Alert.AlertType.WARNING);
            	 alert.setContentText("Error connecting to Database!");
                 alert.showAndWait();
             }
             String query = "SELECT * FROM Accounts WHERE Email = ? AND Password = ?";
             try {
            	    Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            	    PreparedStatement statement = connection.prepareStatement(query);
            	    statement.setString(1, user_email);
            	    statement.setString(2, user_password);
            	    ResultSet result = statement.executeQuery();
            	    if (result.next()) {
            	    	Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Signed In Successful!");
                        alert.showAndWait();
                        user_id = result.getInt("Account_ID");
                        role = result.getString("Account_type");
                        getAccountInfo(user_id , role);
            	    }
            	} catch (SQLException e) {
            	    e.printStackTrace();
            	}
    	}

	private void getAccountInfo(int id , String role) throws Exception {
		if(role.equals("Lecturer")) {
          String query = "SELECT * FROM Lecturers WHERE Account_ID = ?";
          try {
         	    Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
         	    PreparedStatement statement = connection.prepareStatement(query);
         	    statement.setInt(1, id);
         	    ResultSet result = statement.executeQuery();
         	    if (result.next()) {
         	    	lecturer_name = result.getString("First_name");
        	    	lecturer_surname = result.getString("Last_name");
        	    	lecturer_id = result.getInt("Lecturer_ID");
                    String new_query = "SELECT Department_name FROM Departments  JOIN Lecturers ON Departments.Head_of_department = Lecturers.Lecturer_ID WHERE Lecturers.Lecturer_ID = ?;";
         	    	PreparedStatement statementt = connection.prepareStatement(new_query);
             	    statementt.setInt(1, lecturer_id);
             	    ResultSet resultt = statementt.executeQuery();
             	    if(resultt.next()) {
             	    	lecturer_department = resultt.getString("Department_name");
             	    }
             	    Lecturer lecturer = new Lecturer(lecturer_id , lecturer_name , lecturer_surname , id , lecturer_department);
             	   TeacherView teacherView = new TeacherView(lecturer);
             	  teacherView.start(loginOrSignUpStage);
         	    }
         	} catch (SQLException e) {
         	    e.printStackTrace();
         	}
	}else {
		String query = "SELECT * FROM Students WHERE Account_ID = ?";
        try {
       	    Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
       	    PreparedStatement statement = connection.prepareStatement(query);
       	    statement.setInt(1, id);
       	    ResultSet result = statement.executeQuery();
       	    if (result.next()) {
       	    	name = result.getString("First_name");
      	    	surname = result.getString("Last_name");
      	    	studentId = result.getInt("Student_ID");
      	    	birthday = result.getDate("Birthday");
      	    	reg_date = result.getDate("Registration_date");
      	    	scholarship = result.getFloat("Scholarship");
      	    	payment_status = result.getString("Payment_status");
      	    	department_id = result.getInt("Department_ID");
      	    	Program_ID = result.getInt("Program_ID");
      	    	Advisor = result.getInt("Advisor");
      	    	personal_id = result.getString("Personal_ID");
                String new_query = "SELECT Department_name FROM Departments WHERE Department_ID = ?;";
       	    	PreparedStatement statementt = connection.prepareStatement(new_query);
           	    statementt.setInt(1, department_id);
           	    ResultSet resultt = statementt.executeQuery();
           	    if(resultt.next()) {
           	    	department = resultt.getString("Department_name");
           	    }
           	    new_query = "SELECT * FROM programs WHERE Program_ID = ?;";
           	    statementt = connection.prepareStatement(new_query);
        	    statementt.setInt(1, Program_ID);
        	    resultt = statementt.executeQuery();
        	    if(resultt.next()) {
        	    	program = resultt.getString("Program_name");
        	    	fee = resultt.getInt("Tuition_fee");
        	    }
           	    Student student = new Student(studentId , name , surname , personal_id , birthday , reg_date ,scholarship , payment_status , department_id , Program_ID , id , Advisor ,  department ,program , fee );
           	    MainView mainview = new MainView(student);
           	    mainview.start(loginOrSignUpStage);
       	    }
       	} catch (SQLException e) {
       	    e.printStackTrace();
       	}
		
	}
	}
}

