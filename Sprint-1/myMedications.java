import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class myMedications {
    static ArrayList<Medication> currentMedications;
    static ArrayList<Medication> archivedMedications;
    static Scanner userInput;
    
    public static void main(String[] args) {
        currentMedications = new ArrayList<Medication>();
        archivedMedications = new ArrayList<Medication>();
        userInput = new Scanner(System.in);

        //Temp Medications until file system and managing support in Sprint 2.
        currentMedications.add(new Medication("testDrug", 15, Unit.mg));
        currentMedications.add(new Medication("liquid drug test", new Date(), null, 100, Unit.ml, Frequency.Daily, null));
        currentMedications.add(new Medication("Melatonin", new Date(), new Date(), 5, Unit.mg, Frequency.Evenings, "Only take as needed."));
        currentMedications.add(new Medication("drug 1", -2, Unit.mg));
        currentMedications.add(new Medication("drug 2", 2, Unit.mg));
        currentMedications.add(new Medication("drug 3", 3, Unit.mg));
        currentMedications.add(new Medication("drug 4", 4, Unit.mg));

        archivedMedications.add(new Medication("arch drug 1", -2, Unit.mg));
        archivedMedications.add(new Medication("arch drug 2", 2, Unit.mg));
        archivedMedications.add(new Medication("arch drug 3", 3, Unit.mg));
        archivedMedications.add(new Medication("arch drug 4", 4, Unit.mg));
        archivedMedications.add(new Medication("arch drug 5", 5, Unit.mg));
        archivedMedications.add(new Medication("arch drug 6", 6, Unit.mg));

        // Start with the view medications screen.
        viewCurrentMedications();

        userInput.close();
    }

    // Method for the current medications screen.
    private static void viewCurrentMedications() {
        int currentMedicationIndex = 0;
        String userChoice;
        boolean looping = true;
        do {
            System.out.println("-- Current Medications Screen --\n");
            System.out.println("1. Edit\n");
            printMedication(currentMedicationIndex, 3, currentMedications);
            System.out.println("2. Previous Page");
            System.out.println("3. Next Page");
            System.out.println("4. History");
            System.out.println("9. Exit\n");

            System.out.print("Choice: ");
            userChoice = userInput.nextLine();
            System.out.println();

            switch (userChoice) {
                case "1":
                    manageMedications();
                    break;
                case "2":
                    currentMedicationIndex -= 3;
                    if (currentMedicationIndex < 0) {
                        currentMedicationIndex = 0;
                    }
                    break;
                case "3":
                    currentMedicationIndex += 3;
                    if (currentMedicationIndex > currentMedications.size() - 4) {
                        // Set position to be at the end of the arrayList, minus the length of what is printed.
                        currentMedicationIndex = currentMedications.size() - 4;
                    }
                    break;
                case "4":
                    viewArchivedMedications();
                    break;
                case "9":
                    looping = false;
                    break;
                default:
                    System.out.println("Unknown choice, please try again.");
                    break;
            }

            System.out.println();
        } while (looping);

        System.out.println("Exiting the program.");
    }

    // Method for the archived medications screen.
    private static void viewArchivedMedications() {
        int archivedMedicationIndex = 0;
        String userChoice;
        boolean looping = true;
        do {
            //Output Screen
            System.out.println("-- Archived Medications Screen --\n");
            System.out.println("1. Export");
            printMedication(archivedMedicationIndex, 3, archivedMedications);
            System.out.println("2. Previous Page");
            System.out.println("3. Next Page");
            System.out.println("9. Back\n");

            //Accept user's choice
            System.out.print("Choice: ");
            userChoice = userInput.nextLine();
            System.out.println();

            //Process user's choice
            switch (userChoice) {
                case "1":
                    exportMedications();
                    break;
                case "2":
                    archivedMedicationIndex -= 3;
                    if (archivedMedicationIndex < 0) {
                        archivedMedicationIndex = 0;
                    }
                    break;
                case "3":
                    archivedMedicationIndex += 3;
                    if (archivedMedicationIndex > archivedMedications.size() - 4) {
                        // Set position to be at the end of the arrayList, minus the length of what is printed.
                        archivedMedicationIndex = archivedMedications.size() - 4;
                    }
                    break;
                case "9":
                    looping = false;
                    break;
                default:
                    System.out.println("Unknown choice, please try again.");
                    break;
            }

            System.out.println();
        } while (looping);
    }

    // TODO in Sprint 2
    private static void manageMedications() {
        System.out.println("Choice unimplemented, TODO in Sprint 2");
    }

    // TODO in Sprint 3
    private static void exportMedications() {
        System.out.println("Choice unimplemented, TODO in Sprint 3");
    }

    // Method to show medication information from the given arrayList
    public static void printMedication(int startIndex, int length, ArrayList<Medication> medications) {
        if (medications.size() == 0) {
            System.out.println("No medications to display.");
            return;
        }
        for (int i = startIndex; i < startIndex + length; i++) {
            if (i >= medications.size() || i < 0) {
                System.out.println("Invalid index: " + i);
                return;
            }
            medications.get(i).print();
            System.out.println();
        }
    }
}