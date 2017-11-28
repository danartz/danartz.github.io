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
public class MediaNameComparator implements Comparator<Media> {

    @Override
    public int compare(Media o1, Media o2) { // Compares uppercase version so B doesn't come before a
        return o1.getItemName().toUpperCase().compareTo(o2.getItemName().toUpperCase());
    }
    
}
