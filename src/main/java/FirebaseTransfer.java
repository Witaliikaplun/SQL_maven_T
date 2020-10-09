import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
//Класс для работы с Fitebase
public class FirebaseTransfer {
    private FileInputStream serviceAccount;
    private FirebaseOptions options = null;
    private DocumentReference docRef;
    private String readDataSrt;

    public FirebaseTransfer(){
        try {
            serviceAccount = new FileInputStream("./ServiceAccountKey.json");
            options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://testfirebase3-24075.firebaseio.com")
                    .build();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FirebaseApp.initializeApp(options);
        Firestore db = FirestoreClient.getFirestore();
        docRef = db.collection("users1").document("alovelace");
    }

    public String readDataMap(String key){
        try {
            readDataSrt = docRef.get().get().getData().get(key).toString();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return readDataSrt;
    }

    public void writeDataMap(Map<String, Object> map){
        ApiFuture<WriteResult> result = docRef.update(map);//обновить данные
        try {
            System.out.println("susesfull: " + result.get().getUpdateTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }



}
