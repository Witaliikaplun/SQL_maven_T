import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws IOException, ExecutionException,
            InterruptedException, ClassNotFoundException, SQLException,
            InstantiationException, IllegalAccessException {
        String dataIN = "";
        String dataOldIN = "";
        final String key = "key";
        final String sqlQuere = "SELECT TOP 10 * FROM Table_888";
        FileInputStream serviceAccount;
        FirebaseOptions options = null;
        DocumentReference docRef;
        SQLServerConnectMicrosoft sqlServerConnectMicrosoft;

        sqlServerConnectMicrosoft = new SQLServerConnectMicrosoft();
        sqlServerConnectMicrosoft.initialConnectSQL();

        serviceAccount = new FileInputStream("./ServiceAccountKey.json");
        options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://testfirebase3-24075.firebaseiok;lkd;lk;lkdd;'lfk;lk;lskdflk;.com")
                .build();
        FirebaseApp.initializeApp(options);
        Firestore db = FirestoreClient.getFirestore();

        docRef = db.collection("users1").document("alovelace");
        HashMap<String, String> message = new HashMap<String, String>();
        while (true){
            dataIN = sqlServerConnectMicrosoft.readSQLData(sqlQuere);
            Thread.sleep(2000);
            if(!dataIN.equals("нет данных")){
                if(!dataOldIN.equals(dataIN)){
                    System.out.println(dataIN);
                    dataOldIN = dataIN;
                    message.put(key, dataIN);
                    ApiFuture<WriteResult> result = docRef.set(message);
                    System.out.println("susesfull: " + result.get().getUpdateTime());
                }
            }
        }
    }
}
