import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

/**
 * This java class demonstrates how to use Javas' JDBC API in order to communicate & access a database (in this case a
 * MySQL database).
 *
 * Note: DriverManager & Statement classes have been used - ideally in a real-world scenario we opt to use DataSource
 * instead of DriverManager & PreparedStatement instead of Statement.
 *
 * More about prepared statement: https://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html
 */
public class BankingJDBC {

    private final String jdbcUrl;
    private final Properties props = new Properties();

    BankingJDBC(String jdbcUrl){
        this.jdbcUrl = jdbcUrl;
        obtainInput();
    }

    //SQL - To select bank names along with LEI for banks with >50,000 employees
    public static final String SELECT_QUERY_1 = "SELECT b.name, b.lei FROM banks b WHERE b.no_of_employees > 50000;";

    //SQL - To add a new bank to the bank table.
    public static final String INSERT_QUERY_1 = "INSERT INTO banks (name, no_of_employees, is_public, assets_managed, " +
            "lei, avg_salary) VALUES ('NewBank', 200, 0, 50000000.50, '81V0FA7ASNKASD74A8D3', 80000);";


    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/example_jdbc_db";
        BankingJDBC bankingJDBC = new BankingJDBC(jdbcUrl);
        try(Connection dbConnection = bankingJDBC.getDbConnection()){
            System.out.println("Running SELECT query on database: example_jdbc_db | table: banks");
            executeSelectStatementQuery(dbConnection);
            System.out.println("Updating the database: example_jdbc_db | table: banks");
            executeUpdateStatementQuery(dbConnection);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    /**
     * In a real world use-case we would use DataSource instead of DriverManager:
     * https://en.wikipedia.org/wiki/Datasource & https://docs.oracle.com/javase/7/docs/api/javax/sql/DataSource.html
     */
    private Connection getDbConnection() throws SQLException{
        Connection conn = null;
        conn = DriverManager.getConnection(this.jdbcUrl, this.props.getProperty("username"), this.props.getProperty("password"));
        return conn;
    }


    /**
     * When performing a SELECT SQL Query we use the method (executeQuery on our statement instance - a ResultSet is returned).
     * @param dbConnection
     * @throws SQLException
     */
    private static void executeSelectStatementQuery(Connection dbConnection) throws SQLException {
        Statement statement = dbConnection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        ResultSet selectResultSet = statement.executeQuery(SELECT_QUERY_1);
        while(selectResultSet.next()){
            System.out.format("Name: %s | LEI: %s\n", selectResultSet.getString("name"), selectResultSet.getString("lei"));
        }
    }

    /**
     * When executing a SQL query that involves updating a table i.e. UPDATE, DELETE or INSERT - we invoke the
     * executeUpdate() on the statement instance - the returned int shows the number of affected rows.
     */
    private static void executeUpdateStatementQuery(Connection connection) throws SQLException {
        Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        int noOfAffectedRows = statement.executeUpdate(INSERT_QUERY_1);
        System.out.format("%d row(s) have been affected.\n", noOfAffectedRows);
    }


    private void obtainInput(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        props.put("username", scanner.nextLine());
        System.out.println("Enter password: ");
        props.put("password", scanner.nextLine());
    }

}
