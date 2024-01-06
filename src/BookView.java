import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;


public class BookView extends Application {

	private String dbUrl = "jdbc:mysql://127.0.0.1:3306/Database_project";
    private String dbUsername = "root";
    private String dbPassword = "11112003";
    private int studentid;
    public BookView(int id) {
    	this.studentid = id;
    }
    @Override
    public void start(Stage primaryStage) {
        TableView<Book> tableView = new TableView<>();
     
        TableColumn<Book, Long> isbnCol = new TableColumn<>("ISBN");
        isbnCol.setCellValueFactory(
                new PropertyValueFactory<>("ISBN"));
        

        TableColumn<Book, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(
                new PropertyValueFactory<>("title"));

        TableColumn<Book, String> authorCol = new TableColumn<>("Author");
        authorCol.setCellValueFactory(
                new PropertyValueFactory<>("author"));

        TableColumn<Book, Integer> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(
                new PropertyValueFactory<>("quantity"));

        tableView.getColumns().add(isbnCol);
        tableView.getColumns().add(titleCol);
        tableView.getColumns().add(authorCol);
        tableView.getColumns().add(quantityCol);

        VBox root = new VBox(tableView);
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Book Table View");
        primaryStage.show();

        ArrayList<Book> books = fetchBooksFromDB();
        tableView.getItems().addAll(books);
        
        tableView.setOnMouseClicked(event -> {
       	 Book selectedCourse = tableView.getSelectionModel().getSelectedItem();
       	showConfirmationDialog(selectedCourse);
    });}

    private ArrayList<Book> fetchBooksFromDB() {
        ArrayList<Book> books = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Books");

            while (resultSet.next()) {
                long isbn = resultSet.getLong("ISBN");
                String title = resultSet.getString("Title");
                String author = resultSet.getString("Author");
                int quantity = resultSet.getInt("Quantity");

                Book book = new Book(isbn, title, author, quantity);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
    
    private void showConfirmationDialog(Book selectedBook) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("You've selected: " + selectedBook.getTitle());
        alert.setContentText("Do you want to proceed?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
                 Statement statement = connection.createStatement()) {

                statement.execute("SET FOREIGN_KEY_CHECKS=0");

                try (PreparedStatement reserveStatement = connection.prepareStatement("INSERT INTO Reserves VALUES(?, ?)");
                     PreparedStatement updateQuantityStatement = connection.prepareStatement("UPDATE Books SET Quantity = Quantity - 1 WHERE ISBN = ?")) {

                    connection.setAutoCommit(false);

                    reserveStatement.setInt(1, studentid);
                    reserveStatement.setLong(2, selectedBook.getISBN());

                    updateQuantityStatement.setLong(1, selectedBook.getISBN());

                    int rowsReserved = reserveStatement.executeUpdate();
                    int rowsUpdated = updateQuantityStatement.executeUpdate();

                    if (rowsReserved > 0 && rowsUpdated > 0) {
                        connection.commit();
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Success");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("Book '" + selectedBook.getTitle() + "' reserved successfully!");
                        successAlert.showAndWait();
                    } else {
                        connection.rollback();
                        Alert failedAlert = new Alert(Alert.AlertType.ERROR);
                        failedAlert.setTitle("Failed");
                        failedAlert.setHeaderText(null);
                        failedAlert.setContentText("Operation failed: Book reservation canceled.");
                        failedAlert.showAndWait();
                    }
                } finally {
                    statement.execute("SET FOREIGN_KEY_CHECKS=1");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("SQL Error: " + e.getMessage());
                errorAlert.showAndWait();
            }
        }else {
            alert.close();
        }

}
}

