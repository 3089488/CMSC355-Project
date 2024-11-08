package main;

import java.util.HashMap;
import java.util.Scanner;

public class Main
{
    private static HashMap<Integer, Medication> currentMedicationList;
    private static User user;

    public static void main(String[] args)
    {
        menuLoop();
    }

    private static void menuLoop()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Name: ");
        String input = sc.nextLine();

        user = new User(input);
        currentMedicationList = user.getCurrentMedications();

        while(true)
        {
            input = sc.nextLine();
            switch(input)
            {
                case "Exit": case "exit": case "e": case "E":
                    return;
                case "M":
                    printMeds();
            }
        }
    }

    private static void printMeds()
    {
        System.out.printf("Welcome %s!\n\n", user.getName());

        for(int i = 0; i < currentMedicationList.size(); i++)
        {
            currentMedicationList.get(i).print();
        }
    }
}
