import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class myMedications {
    static ArrayList<Medication> currentMedications;
    static ArrayList<Medication> archivedMedications;
    
    public static void main(String[] args) {
        currentMedications = new ArrayList<Medication>();
        archivedMedications = new ArrayList<Medication>();

        //Temp Medications until file system and managing support in Sprint 2.
        currentMedications.add(new Medication("failure", -1, Unit.mg));
        currentMedications.add(new Medication("testDrug", 15, Unit.mg));
        currentMedications.add(new Medication("liquid drug test", new Date(), null, 100, Unit.ml, Frequency.Daily, null));
        currentMedications.add(new Medication("Melatonin", new Date(), new Date(), 5, Unit.mg, Frequency.Evenings, "Only take as needed."));
        currentMedications.add(new Medication("failure 2", -2, Unit.mg));

        viewCurrentMedications();
    }

    private static void viewCurrentMedications() {
        int currentMedicationIndex = 0;
        Scanner userInput = new Scanner(System.in);
        String userChoice;
        boolean looping = true;
        do {
            System.out.println("1. Edit");
            printMedication(currentMedicationIndex, 3, currentMedications);
            System.out.println("2. Next Page");
            System.out.println("3. History");
            System.out.println("9. Exit");
            userChoice = userInput.nextLine();

            switch (userChoice) {
                case "1":
                    System.out.println("Choice unimplemented, TODO in Sprint 2"); // TODO in Sprint 2
                    break;
                case "2":
                    currentMedicationIndex += 3;
                    break;
                case "3":
                    viewArchivedMedications();
                    break;
                case "9":
                    looping = false;
                    break;
                default:
                    System.out.println("Unknown choice, please try again.");
                    break;
            }
        } while (looping);
    }

    private static void viewArchivedMedications() {
        int currentMedicationIndex = 0;
        Scanner userInput = new Scanner(System.in);
        String userChoice;
        boolean looping = true;
        do {
            System.out.println("1. Export");
            printMedication(currentMedicationIndex, 3, archivedMedications);
            System.out.println("2. Next Page");
            System.out.println("9. Back");
            userChoice = userInput.nextLine();

            switch (userChoice) {
                case "1":
                    System.out.println("Choice unimplemented, TODO in Sprint 3"); // TODO in Sprint 3
                    break;
                case "2":
                    currentMedicationIndex += 3;
                    break;
                case "9":
                    looping = false;
                    break;
                default:
                    System.out.println("Unknown choice, please try again.");
                    break;
            }
        } while (looping);
    }

    // TODO in Sprint 2
    private static void manageMedications() {
    }

    // TODO in Sprint 3
    private static void exportMedications() {
    }

    static int printMedication(int startIndex, int length, ArrayList<Medication> medications) {
        for (int i = startIndex; i < startIndex + length; i++) {
            medications.get(i).print();
            System.out.println();
        }
        return startIndex + length;
    }
}