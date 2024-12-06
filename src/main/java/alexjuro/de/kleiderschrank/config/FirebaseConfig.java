package alexjuro.de.kleiderschrank.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;
import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init(){
        try {
            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("./kleiderschrank-d19d4-firebase-adminsdk.json"));
            FirebaseOptions firebaseOptions = FirebaseOptions
                    .builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(firebaseOptions);
        } catch (IOException e){
            e.printStackTrace(); // TODO: replace with more robust logging
        }
    }
}
