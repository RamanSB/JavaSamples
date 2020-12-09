import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class BankingJDBC {

    private String jdbcUrl;
    private Properties props = new Properties();

    BankingJDBC(String jdbcUrl){
        this.jdbcUrl = jdbcUrl;
        obtainInput();
    }

    //SQL - To select bank names along with LEI for banks with >50,000 employees
    public static final String SELECT_QUERY_1 = "SELECT b.name, b.lei FROM banks b WHERE b.no_of_employees > 50000;";

    //SQL - To add a new bank to the bank table.
    public static final String INSERT_QUERY_1 = "INSERT INTO banks (name, no_of_employees, is_public, assets_managed, " +
            "lei, avg_salary) VALUES ('NewBank', 200, 0, 50000000.50, '81V0FA7ASNKASD74A8D3', 80000);";


    public static void main(String[] args) throws SQLException{
        String jdbcUrl = "jdbc:mysql:///localhost:3306/example_jdbc_db";
        BankingJDBC bankingJDBC = new BankingJDBC(jdbcUrl);
        Connection dbConnection = bankingJDBC.getDbConnection();
        //Statement statement = dbConnection.createStatement();
        Statement statement = dbConnection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery(SELECT_QUERY_1);
        System.out.println(resultSet);
        while(resultSet.next()){
            System.out.println(resultSet.getObject("name"));
        }

    }

    private Connection getDbConnection(){
        Connection conn = null;
        try {
            //In a real world use-case we would use DataSource instead of DriverManager:
            // https://en.wikipedia.org/wiki/Datasource & https://docs.oracle.com/javase/7/docs/api/javax/sql/DataSource.html
            conn = DriverManager.getConnection(this.jdbcUrl, this.props.getProperty("username"), this.props.getProperty("password"));
            System.out.println(conn);
        } catch(SQLException exception){
            exception.printStackTrace();
        }
        return conn;
    }


    private void obtainInput(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        props.put("username", scanner.nextLine());
        System.out.println("\nEnter password: ");
        props.put("password", scanner.nextLine());
    }

}
