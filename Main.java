import com.google.gson.Gson;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        Gson gson = new Gson();
        for (int i=1; i<=10; i++) {
            Writer writer = Files.newBufferedWriter(Paths.get("Level"+i+".json"));
        }
    }
}
