
package a_snf_2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Average {
    
    public static int mode(ArrayList<Integer> list){
        HashMap<Integer,Integer> map = new HashMap<>();
        for(Integer val : list){
            if(map.containsKey(val)){
                map.put(val, map.get(val)+1);
            }else {
                map.put(val, 1);
            }
        }
        int mode = 0;
        int maxCount = 0;
        Iterator<Integer> itr = map.keySet().iterator();
        while(itr.hasNext()){
            int val = itr.next();
            int currentCount = map.get(val);
            if(currentCount > maxCount){
                maxCount = currentCount;
                mode = val;
                
            }
        }
        return mode;
    }
    
    public static double mean(ArrayList<Integer> list){
        int sum = 0;
        int count = list.size();
        for(Integer i : list){
            sum += i;
        }
        return (double) sum / (double) count;
    }
}
