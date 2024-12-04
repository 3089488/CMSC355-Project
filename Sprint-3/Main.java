import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.io.*;

public class Main
{
    private static User user;
    private static Scanner sc;

    public static void main(String[] args)
    {
        menuLoop();
    }

    /**
     * Main loop for user menu and input.
     */
    private static void menuLoop()
    {
        sc = new Scanner(System.in);
        System.out.println("Enter Name: ");
        String input = sc.nextLine();

        user = new User(input);
        System.out.printf("\nWelcome %s!\n\nPlease choose an option:\n(V) View Current Medications\n(H) View Medication History\n(A) Add New Medication \n(E) Edit Medication\n(R) Remove Medication\n(C) Archive Medication\n(T) Export Medication\n(I) Import Medication\n(X) Exit\n", user.getName());
        while(true)
        {
            input = sc.nextLine();

            switch(input)
            {
                case "Exit": case "exit": case "x": case "X":
                return;
                case "V": case "v":
                printMedsCurrent();
                break;
                case "A": case "a":
                changeMeds();
                break;
                case "E": case "e":
                System.out.println("Choose a medication you want to edit: ");
                chooseMeds(true);
                break;
                case "R": case "r":
                System.out.println("Choose a medication you want to remove: ");
                chooseMeds(false);
                break;
                case "H": case "h":
                printMedsHistoric();
                break;
                case "C": case "c":
                archiveMeds();
                break;
                case "T": case "t":
                exportMedications();
                break;
                case "I": case "i":
                importMedication();
                break;
            }

            System.out.printf("\nWelcome %s!\n\nPlease choose an option:\n(V) View Current Medications\n(H) View Medication History\n(A) Add New Medication \n(E) Edit Medication\n(R) Remove Medication\n(C) Archive Medication\n(T) Export Medication\n(I) Import Medication\n(X) Exit\n", user.getName());
        }
    }

    /**
     * Prompts the user for which medicine they want to choose to edit or replace
     * @param replace - Boolean, true is if the user is replacing an entry, false for user deleting entry.
     */
    private static void chooseMeds(boolean replace)
    {
        user.printMedicationsCurrent();
        String choice = sc.nextLine();

        for(int i = 0; i < user.getCurrentMedications().size(); i++)
        {
            if(i == Integer.parseInt(choice) && replace)
            {
                System.out.println("Currently Editing: ");
                user.getCurrentMedications().get(i).print();
                changeMeds(true, i);
                break;
            }
            else if(i == Integer.parseInt(choice) && !replace)
            {
                System.out.printf("%s is about to be deleted, are you sure? (Y/N)\n", user.getCurrentMedications().get(i).getDrugName());
                choice = sc.nextLine();

                switch (choice)
                {
                    case "Y": case "y":
                    user.removeMedicineCurrent(i);
                    return;
                    default:
                        return;
                }
            }
        }
    }

    /**
     * See changeMeds(boolean replace, int index);
     */
    private static void changeMeds() {changeMeds(false, 0);}

    /**
     * Asks user for input on medication info to either add to or edit a medicine in Current.json.
     * @param replace - boolean, true is for the user editing a medication, false is for a user adding a medication
     * @param index - The index of what the user wants to change if replacing medication.
     */
    private static void changeMeds(boolean replace, int index)
    {
        System.out.println("Enter Drug Name: ");
        String drugName = sc.nextLine();

        System.out.println("Enter dosage amount: ");
        String dosage = sc.nextLine();

        System.out.println("Choose dosage unit:\n(1) mg\n(2) ml");
        String unit = sc.nextLine();

        Unit medUnit = switch (unit)
        {
            case "ml", "ML", "2" -> Unit.ml;
            default -> Unit.mg;
        };

        System.out.println("Choose frequency:\n(1) Daily\n(2) Weekly\n(3) Mornings\n(4) Evenings\n(5) Twice Daily\n(6) Other\n");
        String frequency = sc.nextLine();
        Frequency frequency1 = switch (frequency)
        {
            case "1" -> Frequency.Daily;
            case "2" -> Frequency.Weekly;
            case "3" -> Frequency.Mornings;
            case "4" -> Frequency.Evenings;
            case "5" -> Frequency.TwiceDaily;
            default -> Frequency.Other;
        };

        System.out.println("Enter a note (optional): ");

        String note = sc.nextLine();

        Medication newMed = new Medication(drugName, new Date(), new Date(), Integer.parseInt(dosage), medUnit, frequency1, note);

        System.out.printf("Is this correct? (Y/N)\nName: %s\nDosage: %d\nUnit: %s\nFrequency: %s\nNote: %s\n",
                drugName, Integer.parseInt(dosage), medUnit.toString(), frequency1.toString(), note);

        String response = sc.nextLine();

        if(response.equals("N") || response.equals("n")) return;

        if(!replace) user.addMedicineCurrent(newMed);
        else user.editMedicineCurrent(index, newMed);
    }

    /**
     * Prints out current medication list.
     */
    private static void printMedsCurrent()
    {
        if(user.getCurrentMedications().isEmpty())
        {
            System.out.println("There are no medications on this list.");
            return;
        }
        for(int i = 0; i < user.getCurrentMedications().size(); i++)
        {
            user.getCurrentMedications().get(i).print();
            System.out.println();
        }
    }

    /**
     * Prints out the medicine archive.
     */
    private static void printMedsHistoric()
    {
        if(user.getHistoricalMedications().isEmpty())
        {
            System.out.println("There are no medications on this list.");
            return;
        }
        for(int i = 0; i < user.getHistoricalMedications().size(); i++)
        {
            user.getHistoricalMedications().get(i).print();
            System.out.println();
        }
    }

    /**
     * The menu prompting the user for which medication they want to archive.
     */
    private static void archiveMeds()
    {
        System.out.println("Choose a medication to archive: ");
        user.printMedicationsCurrent();

        String choice = sc.nextLine();

        try
        {
            user.archiveMedicineHistory(user.getCurrentMedications().get(Integer.parseInt(choice)));
        }
        catch (Exception e) {e.printStackTrace();}

    }

    public static void importMedication(){
        try {
            // this open a file chooser which only allows the user to select txt and csv files, nothing else.
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Txt and Csv Files", "txt", "csv");
            fileChooser.setFileFilter(filter);
            int response = fileChooser.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] tokens = line.split(",");
                        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
                        Date startDate = new Date();
                        Date endDate = new Date();
                        try {
                            startDate = formatter.parse(tokens[3]);
                            endDate = formatter.parse(tokens[4]);
                        } catch (Exception e) {
                        }
                        Unit medUnit = switch (tokens[2])
                        {
                            case "ml", "ML", "2" -> Unit.ml;
                            default -> Unit.mg;
                        };
                        Frequency frequency1 = switch (tokens[5])
                        {
                            case "1" -> Frequency.Daily;
                            case "2" -> Frequency.Weekly;
                            case "3" -> Frequency.Mornings;
                            case "4" -> Frequency.Evenings;
                            case "5" -> Frequency.TwiceDaily;
                            default -> Frequency.Other;
                        };
                        Medication newMed = new Medication(tokens[0], startDate, endDate, Integer.parseInt(tokens[1]), medUnit, frequency1, tokens[6]);
                        user.addMedicineCurrent(newMed);

                    }
                    System.out.println("Successfully imported the medications.");
                } catch (IOException e){
                    System.err.println("Error reading file");
                }


            } else {
                // this message pops up if the user hit "cancel" on the JFileChooser
                System.out.println("No directory was selected.");
            }
        } catch (Exception e){
            System.out.println("There was a problem importing the medications.");
            //e.printStackTrace();
        }
    }

    public static void exportMedications(){
        // this checks that if there are no medications to export, it cancels.
        if (user.getCurrentMedications().size() == 0){
            System.out.println("There are no medications to export.");
        }
        else {
            try {
                System.out.println("How would you like to save your medications?\n" +
                        "   1. .csv file.\n" +
                        "   2. .txt file.\n" +
                        "   3. Cancel");
                int option = sc.nextInt();
                while(option != 1 & option != 2 & option != 3){
                    System.out.println("Invalid choice. Try Again");
                    option = sc.nextInt();
                }

                // if the user cancels the process, it returns to the main function.
                if(option == 3)
                    return;

                System.out.print("How do wish to name the file: ");
                String fileName = sc.next();

                // this open a file chooser which only allows the user to select files and nothing else.
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int response = fileChooser.showOpenDialog(null);

                if (response == JFileChooser.APPROVE_OPTION) {
                    // this makes sure the file selected is appropriate and saves the path to send to the export method.
                    File selectedPath = fileChooser.getSelectedFile();

                    if(option == 1)
                        writeMedications(selectedPath, ".csv", fileName);
                    else
                        writeMedications(selectedPath, ".txt", fileName);

                } else {
                    // this message pops up if the user hit "cancel" on the JFileChooser
                    System.out.println("No directory was selected.");
                }
            } catch (Exception e) {
                System.out.println("There was a problem exporting the medications.");
                //e.printStackTrace();
            }
        }
    }

    public static void writeMedications(File selectedPath, String typeFile, String fileName){
        try{
            // this whole block of code is to ensure that whenever there already exists a file by the same name in the directory that was selected, it creates it with a different name.
            int num = 1;
            String save = fileName + typeFile;
            File file = new File(selectedPath, save);
            while(file.exists()) {
                save = fileName + "(" + (num++) + ")" + typeFile;
                file = new File(selectedPath, save);
            }

            // this just uses a buffered writer to write the medications on the file.
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));

            for(int i = 0; i < user.getCurrentMedications().size(); i++)
            {
                writer.write(user.getCurrentMedications().get(i).printExport());
            }

            writer.close();
        } catch (Exception e){
            System.out.println("There was a problem exporting the medications.");
        }
    }

}