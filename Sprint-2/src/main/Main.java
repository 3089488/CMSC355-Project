package main;

import java.util.Date;
import java.util.Scanner;

public class Main
{
    private static User user;
    private static Scanner sc;

    public static void main(String[] args)
    {
        menuLoop();
    }

    private static void menuLoop()
    {
        sc = new Scanner(System.in);
        System.out.println("Enter Name: ");
        String input = sc.nextLine();

        user = new User(input);
        System.out.printf("\nWelcome %s!\n\nPlease choose an option:\n(V) View Current Medications\n(A) Add New Medication \n(X) Exit\n", user.getName());
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
            }

            System.out.printf("\nWelcome %s!\n\nPlease choose an option:\n(V) View Current Medications\n(A) Add New Medication \n(X) Exit\n", user.getName());
        }
    }

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
    private static void changeMeds() {changeMeds(false, 0);}
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


        Medication newMed = new Medication(drugName, new Date(), new Date(), Integer.parseInt(dosage), medUnit, frequency1, "");

        System.out.printf("Is this correct? (Y/N)\nName: %s\nDosage: %d\nUnit: %s\nFrequency: %s\n",
                drugName, Integer.parseInt(dosage), medUnit.toString(), frequency1.toString());

        String response = sc.nextLine();

        if(response.equals("N") || response.equals("n")) return;

        if(!replace) user.addMedicineCurrent(newMed);
        else user.editMedicineCurrent(index, newMed);
    }

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
}
