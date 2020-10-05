import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        SQLServerConnectMicrosoft.initialConnect();


        final FirebaseDatabase myDB;
        DatabaseReference myDBRef;
        FileInputStream serviceAccount;
        final String MY_DATABASE = "mydatabase";
        FirebaseOptions options = null;
        Scanner scanner = new Scanner(System.in);
        InputStreamReader sr = new InputStreamReader(System.in, "utf8");


        serviceAccount = new FileInputStream("./ServiceAccountKey.json");
        options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://testfirebase3-24075.firebaseio.com")
                .build();
        FirebaseApp.initializeApp(options);

        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection("users1").document("alovelace");
        String in = "";
        String oldin = "";
        String key = "key";
        int count = 0;
        HashMap<String, String> message = new HashMap<String, String>();
        while (true){

            in = SQLServerConnectMicrosoft.read();

            Thread.sleep(2000);
            if(!in.equals("нет данных")){
                if(!oldin.equals(in)){
                    System.out.println(in);
                    oldin = in;
                    message.put(key + count, in);
                    ApiFuture<WriteResult> result = docRef.set(message);
                    System.out.println("susesfull: " + result.get().getUpdateTime());
                }
            }
        }

    }
}
