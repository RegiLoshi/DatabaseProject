import java.io.FileInputStream;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MainView extends Application {
	private Student student;
	private ImageView[] buttonImages = new ImageView[5];
	private BorderPane frame;
	private GridPane options;
	private Scene scene;
	private Button profile_button;
	private Button grades_button;
	private Button bookstore_button;
	private Button message_button;
	private FileInputStream image;
	private Image img;
	private Alert alert;
	private Label welcome_user;
	private Button log_out_button;
	
	
	public MainView(Student studentt) {
		this.student = studentt;
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		stage.setX(0); 
		stage.setY(0); 

		frame = new BorderPane(); 
		frame.setStyle("-fx-background-color: #556b2f;");
		
		getButtonImages();
		
		
		options = new GridPane();
		options.setHgap(50);
		options.setVgap(50);
		
		profile_button = new Button("",buttonImages[0]);
		profile_button.setPrefHeight(120);
		profile_button.setPrefWidth(150);
		profile_button.setBackground(null);
		profile_button.setOnMouseExited(e->{
		profile_button.setCursor(Cursor.HAND);
		});
		profile_button.setOnAction(e ->{
			ProfileView profile = new ProfileView(student);
			try {
				Stage stagee = new Stage();
				profile.start(stagee);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		grades_button = new Button("",buttonImages[4]);
		grades_button.setPrefHeight(170);
		grades_button.setPrefWidth(170);
		grades_button.setBackground(null);
		
		grades_button.setOnAction(e ->{
			ProgramView program = new ProgramView(student.getProgramID() , student.getStudentID());
			try {
				Stage stagee = new Stage();
				program.start(stagee);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		bookstore_button = new Button("",buttonImages[2]);
		bookstore_button.setPrefHeight(120);
		bookstore_button.setPrefWidth(150);
		bookstore_button.setBackground(null);
		bookstore_button.setOnAction(e ->{
			BookView bookView = new BookView(student.getAccountID());
			try {
				Stage stagee = new Stage();
				bookView.start(stagee);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		
		message_button = new Button("",buttonImages[3]);
		message_button.setPrefHeight(120);
		message_button.setPrefWidth(150);
		message_button.setBackground(null);
		message_button.setOnAction(e ->{
			MessageView messageview = new MessageView(student.getAccountID());
			try {
				Stage stagee = new Stage();
				messageview.start(stagee);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		log_out_button = new Button("",buttonImages[1]);
		log_out_button.setPrefHeight(120);
		log_out_button.setPrefWidth(150);
		log_out_button.setBackground(null);
		log_out_button.setOnAction(e ->{
			LoginView loginview = new LoginView();
			try {
				loginview.start(stage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		options.addRow( 0 , profile_button, grades_button);
		options.addRow(1, bookstore_button  , message_button );
		options.addRow(2, log_out_button);
		options.setAlignment(Pos.CENTER);
	
		welcome_user = new Label("Welcome " + student.getFirstName() + " " + student.getLastName() + "!");
		welcome_user.setStyle(
		        "-fx-font-size: 40px;" +
		        "-fx-font-family: 'Arial';" +
		        "-fx-text-fill: #FFFFFF;"
		);
		HBox container = new HBox();
		container.getChildren().add(welcome_user);
		container.setAlignment(Pos.CENTER);
		frame.setTop(container);
		frame.setCenter(options);
		
		// create scene
		scene = new Scene(frame, 700, 700); 
		stage.setScene(scene);
		stage.setTitle("School App");
		stage.show();
	}
	
	private void getButtonImages() {
	
		for(int i=0; i<5; i++) {
			try {
				switch(i) {
					case 0:
						image = new FileInputStream("Images/output-onlinepngtools-8-2-modified.png");
						img = new Image(image);
						buttonImages[0] = new ImageView(img);
						buttonImages[0].setFitHeight(100);
						buttonImages[0].setFitWidth(100);
						break;
					case 1:
						image = new FileInputStream("Images/log_out_final_logo-2-modified-2.png");
						img = new Image(image);
						buttonImages[1] = new ImageView(img);
						buttonImages[1].setFitHeight(100);
						buttonImages[1].setFitWidth(100);
						break;
					case 2:
						image = new FileInputStream("Images/output-onlinepngtools-9-2-modified-2.png");
						img = new Image(image);
						buttonImages[2] = new ImageView(img);
						buttonImages[2].setFitHeight(120);
						buttonImages[2].setFitWidth(120);
						break;
					case 3: 
						image = new FileInputStream("Images/messaging_logo-2.png");
						img = new Image(image);
						buttonImages[3] = new ImageView(img);
						buttonImages[3].setFitHeight(140);
						buttonImages[3].setFitWidth(140);
						break;
					case 4: 
						image = new FileInputStream("Images/output-onlinepngtools-11-modified.png");
						img = new Image(image);
						buttonImages[4] = new ImageView(img);
						buttonImages[4].setFitHeight(100);
						buttonImages[4].setFitWidth(100);
						break;
				}
			}catch(java.io.FileNotFoundException e) {					
				 alert = new Alert(AlertType.ERROR);
				 alert.setContentText("Resources Missing!Contact your Admin!\nErrorCode:104");
				 alert.show();
			}
	}
	}
}