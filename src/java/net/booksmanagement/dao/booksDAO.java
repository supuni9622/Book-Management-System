
package net.booksmanagement.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.booksmanagement.model.Books;

// DAO class provides CRUD database operations for the table books in our database
public class booksDAO {
    //jdbc url,username and password
    private final String jdbcURL = "jdbc:mysql://localhost:3306/bookcrud";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = "";
    
    //books table operations (sql quries)
    // Create book
    private static final String INSERT_BOOKS_SQL = "INSERT INTO books (name, price, author) VALUES (?,?,?);";  
    // Select book by id
    private static final String SELECT_BOOK_BY_ID = "SELECT id, name, price, author FROM books WHERE id = ?";
    // Select all books
    private static final String SELECT_ALL_BOOKS = "SELECT * FROM books";
    // Delete book
    private static final String DELETE_BOOKS_SQL = "DELETE FROM books where id = ? ;";
    // Update book
    private static final String UPDATE_BOOKS_SQL = "UPDATE books SET name =? , price = ?, author = ? WHERE id = ? ;" ;

    public booksDAO(){
        
    }
    
    // Create JDBC connection with the mysql database
    protected Connection getConnection(){
       Connection connection = null; 
        
       try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return connection;
    }

    //insert books method
    public boolean insertBook (Books books) throws SQLException{
        boolean rowInserted = false;
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BOOKS_SQL);)
        {
            preparedStatement.setString(1, books.getName());
            preparedStatement.setString(2, books.getPrice());
            preparedStatement.setString(3, books.getAuthor());
            
            rowInserted = preparedStatement.executeUpdate() > 0;
            
        } catch (SQLException e) {
            printSQLException(e);
        }  
        
        return rowInserted;
    }
    
   //update books method
    public boolean updateBook (Books books) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BOOKS_SQL))
        {
            preparedStatement.setString(1, books.getName());
            preparedStatement.setString(2, books.getPrice());
            preparedStatement.setString(3, books.getAuthor());
            preparedStatement.setInt(4,books.getId());
            
            rowUpdated = preparedStatement.executeUpdate() > 0;
            
        }
        
        return rowUpdated;
    }
    
    //select book from an id method
    public Books selectBook(int id) {
        Books books = null;
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOK_BY_ID);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                String name = rs.getString("name");
                String price = rs.getString("price");
                String author = rs.getString("author");
                books = new Books (id, name, price, author);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return books;
    }
    
    //delete book from ID
    public boolean deleteBook (int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); 
                PreparedStatement statement = connection.prepareStatement(DELETE_BOOKS_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }
    
    //list all the books
    public List < Books > selectAllBooks() {

        // using try-with-resources to avoid closing resources (boiler plate code)
        List < Books > books = new ArrayList < > ();
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BOOKS);) {
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String price = rs.getString("price");
                String author = rs.getString("author");
                books.add(new Books(id, name, price, author));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return books;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    
}
