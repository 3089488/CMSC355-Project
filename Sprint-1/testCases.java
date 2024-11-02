import java.util.ArrayList;
import java.util.Date;

public class testCases {
    //Main method to make running test cases easy.
    public static void main(String[] args) {
        testCases test = new testCases();

        //Run only a single test case at a time.
        test.testCase4();
    }
    // Test case method for automated execution of the third test case.
    public void testCase3() {
        ArrayList<Medication> currentMedications = new ArrayList<Medication>();

        //Add random medication objects to the array.
        currentMedications.add(new Medication("testDrug", 15, Unit.mg));
        currentMedications.add(new Medication("liquid drug test", new Date(), null, 100, Unit.ml, Frequency.Daily, null));
        currentMedications.add(new Medication("Melatonin", new Date(), new Date(), 5, Unit.mg, Frequency.Evenings, "Only take as needed."));
        currentMedications.add(new Medication("drug 1", -2, Unit.mg));

        // Check if error is caught when negative index is input.
        System.out.println("Output:");
        myMedications.printMedication(-1, 3, currentMedications);
    }

    // Test case method for automated execution of the fourth test case.
    public void testCase4() {
        ArrayList<Medication> currentMedications = new ArrayList<Medication>();

        //Add 7 medication objects to the array.
        currentMedications.add(new Medication("testDrug", 15, Unit.mg));
        currentMedications.add(new Medication("liquid drug test", new Date(), null, 100, Unit.ml, Frequency.Daily, null));
        currentMedications.add(new Medication("Melatonin", new Date(), new Date(), 5, Unit.mg, Frequency.Evenings, "Only take as needed."));
        currentMedications.add(new Medication("drug 4", -2, Unit.mg));
        currentMedications.add(new Medication("drug 5", 2, Unit.mg));
        currentMedications.add(new Medication("drug 6", 3, Unit.mg));
        currentMedications.add(new Medication("drug 7", 4, Unit.mg));

        // Check if 4, 5, and 6 medications in arrayList are printed.
        System.out.println("Output:");
        myMedications.printMedication(3, 3, currentMedications);
    }
}
