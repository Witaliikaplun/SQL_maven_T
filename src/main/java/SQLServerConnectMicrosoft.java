import io.grpc.internal.JsonUtil;

import java.sql.*;

public class SQLServerConnectMicrosoft {
    private static final String connectionUrl = "jdbc:sqlserver://CLK5101N:55319;" +
            "databaseName=DATA_WINCC_TEST;user=LoginSQL;password=12345";
    private static final String sqlQuere = "SELECT TOP 10 * FROM Table_888";

    private static Connection con;
    private static Statement statement;
    private static ResultSet rs;
    private static String dannie = "";


    public static void initialConnect() throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            con = DriverManager.getConnection(connectionUrl);
            statement = con.createStatement();
            System.out.println("Инициализация сервера прошла успешно!");
    }

    public static String read() {
        try {
            rs = statement.executeQuery(sqlQuere);
            if (rs.next()) {
                dannie = String.valueOf(rs.getInt("data"));
            } else{
                dannie = "нет данных";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dannie;
    }

}
