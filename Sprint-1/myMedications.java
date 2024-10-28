import java.util.ArrayList;

public class myMedications {
    
    public static void main(String[] args) {
        ArrayList<Medication> currentMedications = new ArrayList<Medication>();

        currentMedications.set(0, new Medication("testDrug", 15, Unit.mg));
    }

    int printMedication(int startIndex, int length) {


        return startIndex + length;
    }
}