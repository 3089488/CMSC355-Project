package main;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Medication {
    private String drugName;
    private Date endDate;
    private Date startDate;
    private int dosage;
    private Unit unit;
    private Frequency frequency;
    private String notes;

    public Medication(String drugName, Date startDate, Date endDate, int dosage, Unit unit, Frequency frequency, String notes) {
        this.drugName = drugName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dosage = dosage;
        this.unit = unit;
        this.frequency = frequency;
        this.notes = notes;
    }
    public Medication(String drugName, int dosage, Unit unit) {
        this (drugName, new Date(), null, dosage, unit, null, null);
    }

    public String getDrugName() {
        return this.drugName;
    }

    // Sprint 2
    public void setDrugName(String drugName) {this.drugName = drugName;}

    public Date getStartDate() {
        return this.startDate;
    }

    // Sprint 2
    public void setStartDate(Date startDate) {this.startDate = startDate;}

    public Date getEndDate() {
        return this.endDate;
    }

    // Sprint 2
    public void setEndDate(Date endDate) {this.endDate = endDate;}

    public int getDosage() {
        return this.dosage;
    }

    // Sprint 2
    public void setDosage(int dosage) {this.dosage = dosage;}

    public Unit getUnit() {
        return this.unit;
    }

    // Sprint 2
    public void setUnit(Unit drugUnit) {this.unit = drugUnit;}

    public Frequency getFrequency() {
        return this.frequency;
    }

    // Sprint 2
    public void setFrequency(Frequency useFrequency) {this.frequency = frequency;}

    public String getNotes() {
        return this.notes;
    }

    // Sprint 2
    public void setNotes(String notes) {this.notes = notes;}

    public void print() {
        String pattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        System.out.println(drugName + "(" + dosage + " " + unit + ")");
        System.out.print("Started: " + simpleDateFormat.format(startDate));
        if (endDate != null) {
            System.out.print("    End Date: " + simpleDateFormat.format(endDate));
        }
        System.out.println();
        if (frequency != null) {
            System.out.println("Take " + frequency);
        }
        if (notes != null) {
            System.out.println("Notes: " + notes);
        }
    }

}