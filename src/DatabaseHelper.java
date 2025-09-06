import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseHelper {
    private static final Dotenv dotenv = Dotenv.load();

    private static final String URL = dotenv.get("DB_URL");
    private static final String USER = dotenv.get("DB_USER");
    private static final String PASS = dotenv.get("DB_PASS");


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }


    public static void createAccount(int accNum) throws SQLException {
        String sql = "INSERT INTO accounts (account_number, balance) VALUES (?, 0.00)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accNum);
            stmt.executeUpdate();
        }
    }


    public static void deposit(int accNum, float amount) throws SQLException {
        String sql1 = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
        String sql2 = "INSERT INTO transactions (account_id, type, amount) VALUES (?, 'Deposit', ?)";

        try (Connection conn = getConnection()) {
            try (
                    PreparedStatement stmt1 = conn.prepareStatement(sql1);
                    PreparedStatement stmt2 = conn.prepareStatement(sql2)
            ) {
                conn.setAutoCommit(false); // start transaction

                // 1. Update balance
                stmt1.setFloat(1, amount);
                stmt1.setInt(2, accNum);
                stmt1.executeUpdate();

                // 2. Insert transaction record
                stmt2.setInt(1, accNum);
                stmt2.setFloat(2, amount);
                stmt2.executeUpdate();

                conn.commit(); // commit both
            } catch (SQLException ex) {
                conn.rollback(); // rollback if either fails
                throw ex;        // rethrow so caller knows
            } finally {
                conn.setAutoCommit(true); // restore default
            }
        }
    }

    }

