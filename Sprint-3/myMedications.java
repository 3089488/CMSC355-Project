import java.util.ArrayList;
import java.util.Date;

public class myMedications {
    static ArrayList<Medication> currentMedications;

    public static void main(String[] args) {
        currentMedications = new ArrayList<Medication>();

        currentMedications.add(new Medication("failure", -1, Unit.mg));
        currentMedications.add(new Medication("testDrug", 15, Unit.mg));
        currentMedications.add(new Medication("liquid drug test", new Date(), null, 100, Unit.ml, Frequency.Daily, null));
        currentMedications.add(new Medication("Melatonin", new Date(), new Date(), 5, Unit.mg, Frequency.Evenings, "Only take as needed."));
        currentMedications.add(new Medication("failure 2", -2, Unit.mg));

        System.out.println();
        printMedication(1, 3, currentMedications);
    }

    static int printMedication(int startIndex, int length, ArrayList<Medication> medications) {
        for (int i = startIndex; i < startIndex + length; i++) {
            medications.get(i).print();
            System.out.println();
        }
        return startIndex + length;
    }
}