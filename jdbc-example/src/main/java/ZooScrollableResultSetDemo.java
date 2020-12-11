import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

/**
 * The following java class will demonstrate how we traverse through a Scrollable Result set, this requires our
 * Statement instance to be created using a ResultSet where the type allows for scrolling, i.e.
 * ResultSet.TYPE_SCROLL_INSENSITIVE or ResultSet.TYPE_SCROLL_SENSITIVE.
 *
 * Scrolling is the act of moving the cursor on the ResultSet up or down.
 *
 * Traversing through a Scrollable ResultSet, requires invocation of methods such as:
 * -boolean absolute(int n)
 * -void afterLast()
 * -void beforeFirst()
 * -boolean next()
 * -boolean previous()
 * -boolean relative(int n)
 * -boolean first()
 * -boolean last()
 * The example sql database file can be located in the resources directory (zoo.sql)
 */
public class ZooScrollableResultSetDemo {

    //JDBC url convention: <jdbc>:<vendor_name/product>:<<locationofDb>/<dbName>>
    public static final String JDBC_URL = "jdbc:mysql://localhost:3306/zoo";

    public static void main(String[] args){
        Properties props = authoriseUser();
        //try-with-resource closes resources in reverse order in which they are declared + closing Connection closes
        // all other JDBC interface resources
        try(Connection conn = DriverManager.getConnection(JDBC_URL,
                props.getProperty("username"),
                props.getProperty("password")
        )){
            //In order to scroll through a dataset we require: ResultSet.TYPE_SCROLL_(IN)SENSITIVE
            Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //Writing a SELECT SQL query hence we use statement.execute(sql-string) or statement.executeQuery(sql-string)
            ResultSet rs = statement.executeQuery("SELECT * FROM animals;");

            //Before demonstrating how to move the cursor we will show all data within the ResultSet
            System.out.println(showAllData(rs));

            //We now begin illustrating how we traverse through a result set of type where scrolling is enabled.
            //ToDo: Demonstrate how traversal of the cursor occurs.

        }catch(SQLException exception){
            System.out.println(exception.getCause());
            System.out.println(exception.getSQLState());
            System.out.println(exception.getErrorCode());
        }
    }

    static Properties authoriseUser(){
        Properties props = new Properties();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        props.put("username", scanner.nextLine());
        System.out.print("Enter password: ");
        props.put("password", scanner.nextLine());
        return props;
    }

    static String showAllData(ResultSet rs) throws SQLException, IllegalStateException{
        StringBuilder rowData = new StringBuilder();
        int noOfRows = rs.last() ? rs.getRow() : -1;
        rs.beforeFirst();
        if(noOfRows != -1) {
            for (int j = 0; j < noOfRows; j++) {
                rs.next();
                for (int i = 1; i <= 4; i++) {
                    rowData.append(rs.getString(i) + "|");
                }
                rowData.append("\n");
            }
        }else{
            throw new IllegalStateException("Unable to determine number of rows in ResultSet.");
        }
        rs.beforeFirst(); //Puts the cursor before the first row - just as if we were obtaining an initial result set.
        return rowData.toString();
    }

}
