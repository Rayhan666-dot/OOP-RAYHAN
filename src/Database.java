import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class Database {
    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String url = "jdbc:mysql://localhost:3306/kampus";
    private final String user = "root";
    private final String pass = "";

    Connection conn;
    Statement stmt;
    ResultSet rs;

    public Database() {
        try {
            // register driver 
            Class.forName(JDBC_DRIVER);
            
            // koneksi database
            conn = DriverManager.getConnection(url, user, pass);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
