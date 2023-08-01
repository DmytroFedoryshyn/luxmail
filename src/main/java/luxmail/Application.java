package luxmail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        copyH2Database();
        SpringApplication.run(Application.class, args);
    }

    private static void copyH2Database() {
        String newDatabasePath = "new_database.mv.db";
        File newDatabaseFile = new File(newDatabasePath);

        if (!newDatabaseFile.exists()) {
            try {
                newDatabaseFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (
                InputStream inputStream = Application.class.getResourceAsStream("/database.mv.db");
                OutputStream outputStream = new FileOutputStream(newDatabaseFile);
        ) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}