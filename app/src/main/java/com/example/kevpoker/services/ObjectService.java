package com.example.kevpoker.services;

import com.example.kevpoker.model.Card;

import java.util.List;

public class ObjectService {

    //public void swap(Card[] cards, int a, int b){                 // upgrading to generic object array swapper
    public static void swap(Object[] obj, int a, int b){
        // Java is always pass by value. cant just swap references to A and B,
        //  BUT we can change their positions if we pass in the whole array
        Object temp = obj[a];
        obj[a] = obj[b];
        obj[b] = temp;
    }
    public static void swap(List<Card> obj, int a, int b){
        // Java is always pass by value. cant just swap references to A and B,
        //  BUT we can change their positions if we pass in the whole array
        Card temp = obj.get(a);
        obj.set(a,obj.get(b));
        obj.set(b, temp);
    }

  /*                                                    // unused, prob not needed
  public void swap(List<Object> obj, int a, int b){
    //    Object temp = obj.get(a);         // Alt
    //    obj.set(a,obj.get(b));
    //    obj.set(b,temp);

        Collections.swap(obj,a,b);
    }

    public List<Object> ArrayToArrayList(Object[] obj){
        List<Object> L = new ArrayList<>( Arrays.asList(obj));
        return L;
    }
*/

}
