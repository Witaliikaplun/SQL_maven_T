import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws IOException, ExecutionException,
            InterruptedException, ClassNotFoundException, SQLException,
            InstantiationException, IllegalAccessException {
        String dataIN_SQL = "1";
        String dataInOld_SQL = "2";
        String dataIN_Firebase = "3";
        String dataInOld_Firebase = "4";
        final String key = "key";
        //final String sqlQuereRead = "SELECT TOP 10 * FROM Table_888";
        final String sqlQuere = "SELECT * FROM Table_888";
        final String columnLabel = "data";
        FirebaseTransfer firebaseTransfer;
        SQLServerConnectMicrosoft sqlServerConnectMicrosoft;
        Map<String, Object> message = new HashMap<String, Object>();

        //Настройка соединения с SQL сервером----------------------------------
        sqlServerConnectMicrosoft = new SQLServerConnectMicrosoft();
        //Настройка соединения с Firebase--------------------------------------
        firebaseTransfer = new FirebaseTransfer();

        while (true){ //основной цикл
            dataIN_SQL = sqlServerConnectMicrosoft.readSQLData(sqlQuere, columnLabel);
            Thread.sleep(2000);

            if(!dataIN_SQL.equals("нет данных")){
                if(!dataInOld_SQL.equals(dataIN_SQL)){
                    System.out.println("dataIN_SQL " + dataIN_SQL);
                    dataInOld_SQL = dataIN_SQL;
                    message.put(key, dataIN_SQL);
                    firebaseTransfer.writeDataMap(message);//обновить данные
                }
            }
            dataIN_Firebase = firebaseTransfer.readDataMap("dritte");
            if(!dataIN_Firebase.equals(dataInOld_Firebase)){
                System.out.println("dataIN_Firebase " + dataIN_Firebase);
                dataInOld_Firebase = dataIN_Firebase;
                //чтение из Firebase и запись в SQL server
                sqlServerConnectMicrosoft.writeSQL(sqlQuere, "data2", Integer.valueOf(dataIN_Firebase));
            }
        }
    }
}
