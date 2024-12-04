import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * The following class creates a new directory "Users" and a new subfolder for each new user using UUID based off their
 * name. Inside this subfolder there are two .json files: Current.json, which stores active medications the user is taking
 * and, History.json, which stores all the medicine a user has ever taken.
 *
 * @author Steffen Niemeyer
 */
public class User{

    private String username;

    private UUID uuid; //Unique User ID.
    private File userDirectory; //This stores the directory of the current user: ./Users/{UUID}
    private JSONObject medicationObjectCurrent = new JSONObject(); //Helper JSONObject class, used to help with json structure.
    private JSONObject medicationObjectHistory = new JSONObject();

    private HashMap<Integer, Medication> currentMedications = new HashMap<>();
    private HashMap<Integer, Medication> historicalMedications = new HashMap<>();

    /**
     * Generates a UUID for each user, generated by name. Also ensures that "Users" folder exists.
     * @param name  -   A User's full name.
     */
    User(String name)
    {
        this.username = name;
        this.uuid = UUID.nameUUIDFromBytes(name.getBytes());

        File usersDir = new File("Users");

        //Creates "Users" directory if it doesn't exist.
        if (!usersDir.exists())
        {
            usersDir.mkdir();
        }

        //Creates directory for specified user.
        this.userDirectory = new File("Users/" + this.uuid.toString());

        if (!this.userDirectory.exists())
        {
            this.userDirectory.mkdir();
        }

        if(!(new File(userDirectory.getPath() + "/Current.json").isFile()))
        {
            try
            {
                FileWriter currentMedsFile = new FileWriter(userDirectory.getPath() + "/Current.json", false);
                currentMedsFile.write(medicationObjectCurrent.toJSONString());
                currentMedsFile.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        if(!(new File(userDirectory.getPath() + "/History.json").isFile()))
        {
            try
            {
                FileWriter historyMedsFile = new FileWriter(userDirectory.getPath() + "/History.json", false);
                historyMedsFile.write(medicationObjectHistory.toJSONString());
                historyMedsFile.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        //Gets current and medication history from file.
        try
        {
            JSONParser parser = new JSONParser();
            JSONObject med = (JSONObject) parser.parse(new FileReader("Users/" + this.uuid.toString() + "/Current.json"));

            for(int i = 0; i < med.size(); i++)
            {
                JSONObject medDetails = (JSONObject) med.get( "" + i); //Converts int to string, otherwise it'll return null.

                SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

                this.currentMedications.put(i, new Medication((String) medDetails.get("Name"), (Date) format.parse((String) medDetails.get("Start Date")),
                        (Date) format.parse((String) medDetails.get("End Date")), ((Long) medDetails.get("Dosage")).intValue(), Unit.valueOf((String)medDetails.get("Unit")),
                        Frequency.valueOf((String) medDetails.get("Frequency")), (String) medDetails.get("Notes")));
            }

            med = (JSONObject) parser.parse(new FileReader("Users/" + this.uuid.toString() + "/History.json"));

            for(int i = 0; i < med.size(); i++)
            {
                JSONObject medDetails = (JSONObject) med.get( "" + i); //Converts int to string, otherwise it'll return null.

                SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

                this.historicalMedications.put(i, new Medication((String) medDetails.get("Name"), (Date) format.parse((String) medDetails.get("Start Date")),
                        (Date) format.parse((String) medDetails.get("End Date")), ((Long) medDetails.get("Dosage")).intValue(), Unit.valueOf((String)medDetails.get("Unit")),
                        Frequency.valueOf((String) medDetails.get("Frequency")), (String) medDetails.get("Notes")));
            }
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        catch (IOException | ParseException e)
        {
            e.printStackTrace();
        }
        catch (java.text.ParseException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the name of the user.
     * @return String
     */
    public String getName(){return this.username;}

    /**
     * Prints out a numerical list of medications by name.
     */
    public void printMedicationsCurrent()
    {
        for(int i = 0; i < this.currentMedications.size(); i++)
        {
            System.out.printf("[%d] %s\n", i, this.currentMedications.get(i).getDrugName());
        }
    }

    /**
     * Edits the desired medication with new medication.
     * @param index - Index of the medication we want to edit.
     * @param medication - The modified medicine data to replace it with.
     */
    public void editMedicineCurrent(int index, Medication medication)
    {
        this.currentMedications.replace(index, medication);
        saveCurrentMedications();
    }

    /**
     * Adds a new medication to our current medication list.
     * @param medication - Medication to add.
     */
    public void addMedicineCurrent(Medication medication)
    {
        this.currentMedications.put(this.currentMedications.size(), medication);
        saveCurrentMedications();
    }

    /**
     * Removes the desired medication by index.
     * @param index - The index of the medication we want to remove.
     */
    public void removeMedicineCurrent(int index)
    {
        HashMap<Integer, Medication> newList = this.currentMedications;
        this.currentMedications = new HashMap<>();

        for(int i = 0; i < newList.size(); i++)
        {
            if(i == index) continue;
            else this.currentMedications.put(this.currentMedications.size(), newList.get(i));
        }

        saveCurrentMedications();
    }

    /**
     * Gives us the list of current medications.
     * @return HashMap
     */
    public HashMap<Integer, Medication> getCurrentMedications() {return this.currentMedications;}

    /**
     * Saves the values of our currentMedications list to Current.json. Always overwrites the file.
     */
    public void saveCurrentMedications()
    {
        //This is JSONObject for the medicine we need to add.
        JSONObject currentMeds;

        for(int i = 0; i < this.currentMedications.size(); i++)
        {
            currentMeds = new JSONObject();
            currentMeds.put("Name", this.currentMedications.get(i).getDrugName());
            currentMeds.put("Dosage", this.currentMedications.get(i).getDosage());
            currentMeds.put("Unit", this.currentMedications.get(i).getUnit().toString());
            currentMeds.put("Frequency", this.currentMedications.get(i).getFrequency().toString());
            currentMeds.put("Start Date", this.currentMedications.get(i).getStartDate().toString());
            currentMeds.put("End Date", this.currentMedications.get(i).getEndDate().toString());
            currentMeds.put("Notes", this.currentMedications.get(i).getNotes());

            medicationObjectCurrent.put(i, currentMeds); //This enforces the structure {"0": {Drug 1}, "1": {Drug 2}}
        }
        try {
            FileWriter currentMedsFile = new FileWriter(userDirectory.getPath() + "/Current.json", false);
            currentMedsFile.write(medicationObjectCurrent.toJSONString());
            currentMedsFile.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Adds a medicine to the medication archive and removes it from the current medication list.
     * @param medication - Medication to archive.
     */
    public void archiveMedicineHistory(Medication medication)
    {
        for(int i = 0; i < this.currentMedications.size(); i++)
        {
            if(medication.equals(this.currentMedications.get(i)))
            {
                this.historicalMedications.put(this.historicalMedications.size(), medication);

                removeMedicineCurrent(i);
            }
        }

        saveHistoricalMedications();
    }

    /**
     * Returns the medication archive for the user.
     * @return HashMap
     */
    public HashMap<Integer, Medication> getHistoricalMedications() {return this.historicalMedications;}

    /**
     * Saves the History.json file with the medicines in the historicalMedications list.
     */
    public void saveHistoricalMedications()
    {
        //This is JSONObject for the medicine we need to add.
        JSONObject historicMeds;

        for(int i = 0; i < this.historicalMedications.size(); i++)
        {
            historicMeds = new JSONObject();
            historicMeds.put("Name", this.historicalMedications.get(i).getDrugName());
            historicMeds.put("Dosage", this.historicalMedications.get(i).getDosage());
            historicMeds.put("Unit", this.historicalMedications.get(i).getUnit().toString());
            historicMeds.put("Frequency", this.historicalMedications.get(i).getFrequency().toString());
            historicMeds.put("Start Date", this.historicalMedications.get(i).getStartDate().toString());
            historicMeds.put("End Date", this.historicalMedications.get(i).getEndDate().toString());
            historicMeds.put("Notes", this.historicalMedications.get(i).getNotes());

            medicationObjectHistory.put(i, historicMeds); //This enforces the structure {"0": {Drug 1}, "1": {Drug 2}}
        }
        try {
            FileWriter historyMedsFile = new FileWriter(userDirectory.getPath() + "/History.json", false);
            historyMedsFile.write(medicationObjectHistory.toJSONString());
            historyMedsFile.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}