import io.github.cdimascio.dotenv.Dotenv;
import java.sql.*;

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


    public static float getBalance(int accNum) throws SQLException {
        String sql = "SELECT balance FROM accounts WHERE account_number = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accNum);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getFloat("balance");
                } else {
                    throw new SQLException("Account not found: " + accNum);
                }
            }
        }
    }

    // Deposit
    public static void deposit(int accNum, float amount) throws SQLException {
        String updateSql = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
        String insertSql = "INSERT INTO transactions (account_id, type, amount, timestamp) " +
                "VALUES ((SELECT id FROM accounts WHERE account_number = ?), 'Deposit', ?, NOW())";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                 PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {


                updateStmt.setFloat(1, amount);
                updateStmt.setInt(2, accNum);
                updateStmt.executeUpdate();


                insertStmt.setInt(1, accNum);
                insertStmt.setFloat(2, amount);
                insertStmt.executeUpdate();

                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    // Withdraw
    public static boolean withdraw(int accNum, float amount) throws SQLException {
        String checkSql = "SELECT balance FROM accounts WHERE account_number = ?";
        String updateSql = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
        String insertSql = "INSERT INTO transactions (account_id, type, amount, timestamp) " +
                "VALUES ((SELECT id FROM accounts WHERE account_number = ?), 'Withdraw', ?, NOW())";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, accNum);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    float currentBalance = rs.getFloat("balance");
                    if (currentBalance >= amount) {
                        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                             PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {


                            updateStmt.setFloat(1, amount);
                            updateStmt.setInt(2, accNum);
                            updateStmt.executeUpdate();


                            insertStmt.setInt(1, accNum);
                            insertStmt.setFloat(2, amount);
                            insertStmt.executeUpdate();

                            conn.commit();
                            return true;
                        }
                    } else {
                        conn.rollback();
                        return false;
                    }
                } else {
                    conn.rollback();
                    throw new SQLException("Account not found: " + accNum);
                }
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }
}
