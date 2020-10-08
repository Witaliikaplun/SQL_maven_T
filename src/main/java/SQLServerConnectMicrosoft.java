import java.sql.*;

public class SQLServerConnectMicrosoft {
    private  final String connectionUrl = "jdbc:sqlserver://CLK5101N:55319;" +
            "databaseName=DATA_WINCC_TEST;user=LoginSQL;password=12345";
    private  Connection con;
    private  Statement statement;
    private  ResultSet rs;
    private  String dannie = "";

    public  void initialConnectSQL() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
        try {
            con = DriverManager.getConnection(connectionUrl);
            //statement = con.createStatement(); //только для чтения
            statement = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE); // чтение и запись
            System.out.println("Инициализация сервера прошла успешно!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  String readSQLData(String sqlQuere, String columnLabel) {
        try {
            rs = statement.executeQuery(sqlQuere);
            //System.out.println("rs read = "+ rs);
            if (rs.next()) {
                dannie = String.valueOf(rs.getInt(columnLabel));
            } else{
                dannie = "нет данных";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dannie;
    }

    public void writeSQL(String sqlQuere, String columnLabel, int value){
        try {
            rs = statement.executeQuery(sqlQuere);
            //System.out.println("rs write = "+ rs);
            rs.next();
            rs.updateInt(columnLabel, value);
            rs.updateRow();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
