package com.example.kevpoker.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ObjectService {

    //public void swap(Card[] cards, int a, int b){                 // upgrading to generic object array swapper
    public void swap(Object[] obj, int a, int b){
        // Java is always pass by value. cant just swap references to A and B,
        //  BUT we can change their positions if we pass in the whole array
        Object temp = obj[a];
        obj[a] = obj[b];
        obj[b] = temp;
    }

    public void swap(List<Object> obj, int a, int b){
        /*Object temp = obj.get(a);
        obj.set(a,obj.get(b));
        obj.set(b,temp);
    */
        Collections.swap(obj,a,b);
    }

    public List<Object> ArrayToArrayList(Object[] obj){
        List<Object> L = new ArrayList<>( Arrays.asList(obj));
        return L;
    }

}
