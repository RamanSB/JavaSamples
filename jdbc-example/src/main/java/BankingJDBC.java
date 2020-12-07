import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class BankingJDBC {

    private String jdbcUrl;
    private Properties props = new Properties();

    BankingJDBC(String jdbcUrl){
        this.jdbcUrl = jdbcUrl;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        props.put("username", scanner.nextLine());
        System.out.println("\nEnter password: ");
        props.put("password", scanner.nextLine());
    }

    public static void main(String[] args){
        BankingJDBC bankingJDBC = new BankingJDBC("jdbc:mysql://localhost:3306/example_jdbc_db");
        bankingJDBC.getDbConnection();
    }

    private Connection getDbConnection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(this.jdbcUrl, this.props);
        } catch(SQLException exception){
            exception.printStackTrace();
        }
        return conn;
    }
}
