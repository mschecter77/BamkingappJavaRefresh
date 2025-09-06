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
    public static void deposit (int accNum,float amount) throws SQLException {
        String sql = "INSERT INTO accounts (account_number, balance) VALUES (?, 0.00)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accNum);
            stmt.executeUpdate();
        }
    }
}
