/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2fixed;

import java.util.Comparator;

/**
 *
 * @author dan
 */
public class MediaDateComparator implements Comparator<Media> {

    @Override
    public int compare(Media o1, Media o2) {

        // Had to create a way to deal with the null pointer values and separate them from items that weren't null
        
        if (o1.getOnLoan() == false && o2.getOnLoan() == false){ // This sorts alphabetically if both items being compared aren't loaned out
            return o1.getItemName().toUpperCase().compareTo(o2.getItemName().toUpperCase());
        }
        if(o1.getDateLoaned() == null){
            return 1;
        }
        
        if((o1.getDateLoaned() == null && o2.getDateLoaned() == null)){
            return 1;
        }
        
        if ((o1.getDateLoaned() != null && o2.getDateLoaned() == null) && (o1.getOnLoan() == true && o2.getOnLoan() == false)){
            return -1;
        }
        if((o1.getOnLoan() == false && o2.getOnLoan() == true))
        {
            return 1;
        }
        if((o1.getOnLoan() == true && o2.getOnLoan() == false))
        {
            return -1;
        }
        

        return -(o1.getDateLoaned().compareTo(o2.getDateLoaned()));

    }

}
