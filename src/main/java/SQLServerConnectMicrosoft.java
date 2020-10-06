import java.sql.*;

public class SQLServerConnectMicrosoft {
    private  final String connectionUrl = "jdbc:sqlserver://CLK5101N:55319;" +
            "databaseName=DATA_WINCC_TEST;user=LoginSQL;password=12345";
    private  Connection con;
    private  Statement statement;
    private  ResultSet rs;
    private  String dannie = "";

    public  void initialConnectSQL() throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            con = DriverManager.getConnection(connectionUrl);
            statement = con.createStatement();
            System.out.println("Инициализация сервера прошла успешно!");
    }

    public  String readSQLData(String sqlQuere) {
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
