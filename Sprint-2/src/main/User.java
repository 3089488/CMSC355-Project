package main;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;
import org.json.simple.JSONObject;

public class User{

    UUID uuid;
    File userDirectory;

    User(String name){
        this.uuid = UUID.nameUUIDFromBytes(name.getBytes());

        this.userDirectory = new File(this.uuid.toString());

        if(!this.userDirectory.exists()){
            this.userDirectory.mkdir();
            System.out.println("Dir created.");

            JSONObject currentMeds = new JSONObject();
            currentMeds.put("Name", name);

            try {
                FileWriter currentMedsFile = new FileWriter(userDirectory.getPath() + "/Current.json");
                currentMedsFile.write(currentMeds.toJSONString());
                currentMedsFile.close();
            } catch(IOException e){
                e.printStackTrace();
            }
        }
        else System.out.println("Dir already exists.");
    }

    public static void main(String[] args)
    {
        User testUser = new User("Test User");
    }
}