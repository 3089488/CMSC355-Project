import java.util.Calendar;
import java.util.Date;

enum Unit {
    mg,
    ml
}

enum Frequency {
    Daily,
    Weekly,
    Mornings,
    Evenings,
    TwiceDaily,
    Other
}

public class Medications {
    private String drugName
    private Date endDate
    private Date startDate
    private int dosage
    private Unit unit
    private Frequency frequency
    private String notes

    public Medications(String drugName, Date startDate, Date endDate, int dosage, Unit unit, Frequency frequency, String notes) {
        this.drugName = drugName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dosage = dosage;
        this.unit = unit;
        this.frequency = frequency;
        this.notes = notes;
    }
    public Medications(String drugName, int dosage, Unit unit) {
        this (drugName, Date(), null, dosage, unit, null, "N/A")
    }

    public String getDrugName() {
        return this.drugName;
    }

    // Sprint 2
    public void setDrugName(String drugName) {}

    public Date getStartDate() {
        return this.startDate;
    }

    // Sprint 2
    public void setStartDate(Date startDate) {}

    public Date getEndDate() {
        return this.endDate;
    }

    // Sprint 2
    public void setEndDate(Date endDate) {}

    public int getDosage() {
        return this.dosage;
    }

    // Sprint 2
    public void setDosage(int dosage) {}

    public Unit getUnit() {
        return this.unit;
    }

    // Sprint 2
    public void setUnit(Unit drugUnit) {}

    public Frequency getFrequency() {
        return this.frequency;
    }

    // Sprint 2
    public void setFrequency(Frequency useFrequency) {}

    public String getNotes() {
        return this.notes;
    }

    // Sprint 2
    public void setNotes(String notes) {}

}