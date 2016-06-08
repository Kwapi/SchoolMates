package SchoolMatesPackage;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBAccessor {

    private Connection connection;
    
    DBAccessor() {}
    
    public void createConnection() throws SQLException {
        String jdbcDriver = "org.postgresql.Driver";
        
        try {
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBAccessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String jdbcUrl = "jdbc:postgresql:postgres";
        String username = "dbuser";
        String password = "";
        
        connection = DriverManager.getConnection(jdbcUrl, username, password);
    }
       public Connection getConnection() throws SQLException {
        String jdbcDriver = "org.postgresql.Driver";
        
        try {
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBAccessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String jdbcUrl = "jdbc:postgresql:postgres";
        String username = "dbuser";
        String password = "";
        
        connection = DriverManager.getConnection(jdbcUrl, username, password);
        return connection;
    }
    
    public ResultSet executeQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);    
        return resultSet; 
    }
    
    public void executeUpdate(String update) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(update);    
    }
    
    public void close() throws SQLException {
        connection.close();
    }
   
}
