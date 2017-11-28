/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2fixed;

import java.io.Serializable;

import java.time.LocalDate;


/**
 *
 * @author dan
 * @param <E>
 */
public class Media implements Serializable{

    private String itemName;
    private String itemFormat;
    private String loanedTo;
    private boolean onLoan;
    private LocalDate date;
    private String dayConv;
    private String dayEnd;
    private String dayFinal;
    private String monthConv;
    private String monthEnd;
    private String monthFinal;
   

    // Main constructor
    Media(String iName, String iFormat) {
        this.itemName = iName;
        this.itemFormat = iFormat;
        this.onLoan = false;
    }

    public boolean getOnLoan() {
        return this.onLoan;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemFormat() {
        return itemFormat;
    }

    public LocalDate getDateLoaned() {
        return date;
    }
    
    // Allows loan status to be set
    public void setLoan(boolean loan) {
        this.onLoan = loan;
    }

    //after date is set then mark isOnLoan as true
    public void setDateLoaned(LocalDate setDate, String loanedName) {
        this.date = setDate;
        this.loanedTo = loanedName;
        if (this.date != null) { // I had issues using a formatter so I did it manually
            this.dayConv = date.getDayOfWeek().toString();
            this.dayConv = dayConv.substring(0, 3);
            this.dayEnd = dayConv.substring(1, 3).toLowerCase();
            this.dayFinal = dayConv.substring(0, 1) + this.dayEnd;
            
            this.monthConv = date.getMonth().toString();
            this.monthConv = monthConv.substring(0, 3);
            this.monthEnd = monthConv.substring(1, 3).toLowerCase();
            this.monthFinal = monthConv.substring(0, 1) + this.monthEnd;
        }

    }

    // To string override
    @Override
    public String toString() {

        if (onLoan == false) {
            return itemName + " - " + itemFormat;
        } else {
            return itemName + " - " + itemFormat + " ("
                    + loanedTo + " on " + dayFinal + " " + monthFinal + " " + date.getDayOfMonth() + " " + date.getYear() + ")";
        }
    }


}

