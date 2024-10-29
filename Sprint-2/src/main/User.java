package main;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import org.json.simple.JSONObject;

public class User{

    private UUID uuid;
    private File userDirectory;
    private JSONObject meds = new JSONObject();

    private int count = 0;

    User(String name){
        this.uuid = UUID.nameUUIDFromBytes(name.getBytes());

        File usersDir = new File("Users");

        //Creates "Users" directory if it doesn't exist.
        if(!usersDir.exists())
        {
            usersDir.mkdir();
            System.out.println("Dir created.");
        }
        else System.out.println("Dir already exists.");

        //Creates directory for specified user.
        this.userDirectory = new File("Users/" + this.uuid.toString());

        if(!this.userDirectory.exists())
        {
            this.userDirectory.mkdir();
        }
    }

    public void addMedicine(Medication medication)
    {

        JSONObject currentMeds = new JSONObject();
        currentMeds.put("Name", medication.getDrugName());
        currentMeds.put("Dosage", medication.getDosage());
        currentMeds.put("Unit", medication.getUnit().toString());
        currentMeds.put("Frequency", medication.getFrequency().toString());
        currentMeds.put("Start Date", medication.getStartDate().toString());
        currentMeds.put("End Date", medication.getEndDate().toString());
        currentMeds.put("Notes", medication.getNotes());

        meds.put(count, currentMeds);

        try {
            FileWriter currentMedsFile = new FileWriter(userDirectory.getPath() + "/Current.json");
            currentMedsFile.write(meds.toJSONString());
            currentMedsFile.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public Medication getMedicine()
    {
        return null;
    }

    public static void main(String[] args)
    {
        User testUser = new User("Test User");

        testUser.addMedicine(new Medication("Test", new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis() + 1000000), 10, Unit.mg, Frequency.Daily, "This is a note!"));
        testUser.addMedicine(new Medication("Test 2", new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis() + 1000000), 10, Unit.mg, Frequency.Daily, "This is a note!"));
    }
}