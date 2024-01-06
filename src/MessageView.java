import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDateTime;

public class MessageView extends Application {
	private String dbUrl = "jdbc:mysql://127.0.0.1:3306/Database_project";
    private String dbUsername = "root";
    private String dbPassword = "11112003";

    private TextArea messageTextArea;
    private int userid;
	private int friend_Id;
	private TextArea newMessageTextArea;

    public MessageView(int id) {
    	userid = id;
	}

	public static void main(String[] args) {
        launch(args);
    }
	
	public void start(Stage primaryStage) {
        primaryStage.setTitle("Message App");

        // Ask for user ID
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("User ID");
        dialog.setHeaderText(null);
        dialog.setContentText("Please enter user ID to message:");
        dialog.showAndWait().ifPresent(userId -> {
            friend_Id = Integer.parseInt(userId); 
            startM(primaryStage);
        });

    }
    public void startM(Stage primaryStage) {
        primaryStage.setTitle("Message App");
        messageTextArea = new TextArea();
        messageTextArea.setPrefHeight(300);
        messageTextArea.setEditable(false);

        Button refreshButton = new Button("Refresh Messages");
        refreshButton.setOnAction(e -> refreshMessages());

        newMessageTextArea = new TextArea();
        newMessageTextArea.setPromptText("Type your message here...");

        Button sendButton = new Button("Send");
        sendButton.setOnAction(e -> sendMessage(newMessageTextArea.getText()));

        VBox layout = new VBox(10);
        layout.getChildren().addAll(messageTextArea, refreshButton, newMessageTextArea, sendButton);

        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
        refreshMessages();
    }

    private void refreshMessages() {
    	try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    	         PreparedStatement statement = connection.prepareStatement("SELECT * FROM Messages WHERE Sender_ID = ? AND Receiver_ID = ? OR Sender_ID = ? AND Receiver_ID = ? ORDER BY Sent_date ASC")) {

    	        statement.setInt(1, userid);
    	        statement.setInt(2, friend_Id);
    	        statement.setInt(3, friend_Id);
    	        statement.setInt(4, userid);

    	        ResultSet resultSet = statement.executeQuery();

    	        StringBuilder messages = new StringBuilder();
    	        while (resultSet.next()) {
                Date sentDate = resultSet.getDate("Sent_date");
                String content = resultSet.getString("Content");
                String status = resultSet.getString("Status");

                messages.append("Sent Date: ").append(sentDate).append("\n");
                messages.append("Content: ").append(content).append("\n");
                messages.append("----------------------------\n");
            }
            messageTextArea.setText(messages.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String content) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO Messages (Sender_ID, Receiver_ID, Content, Status) VALUES (?, ?, ?, ?)")) {

            LocalDateTime currentDateTime = LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(currentDateTime);

            statement.setInt(1, userid);
            statement.setInt(2, friend_Id);
            statement.setString(3, content);
            statement.setString(4, "UNREAD"); 

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                newMessageTextArea.clear();

                StringBuilder messages = new StringBuilder(messageTextArea.getText());
                messages.append("Sent Date: ").append(currentDateTime.toString()).append("\n");
                messages.append("Content: ").append(content).append("\n");
                messages.append("----------------------------\n");

                messageTextArea.setText(messages.toString());
                System.out.println("Message sent successfully!");
            } else {
                System.out.println("Failed to send the message.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }
