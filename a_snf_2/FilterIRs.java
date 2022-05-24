
package a_snf_2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FilterIRs {
    
    
    public HashMap<String, InvertedRepeat> filterATRichIRs(HashMap<String,InvertedRepeat> IRs, int maxThreshold, String discardLogPath){
    
        HashMap<String, InvertedRepeat> map = new HashMap<>();
        try (BufferedWriter w = new BufferedWriter(new FileWriter(new File(discardLogPath)))) {
            Iterator<String> itr = IRs.keySet().iterator();
            while(itr.hasNext()){
                boolean discarded = false;
                String id = itr.next();
                InvertedRepeat currentIR = IRs.get(id);  
                double pcAT = currentIR.getATContent();
                if(pcAT > ((double)maxThreshold/100.0)){
                    discarded = true;
                }
                if(discarded){
                    w.write("DISCARDED AT RICH IR:\t"+currentIR.getHeader()+"\tREASON:\tAT content of "+pcAT+" (threshold="+maxThreshold+"%).");
                    w.newLine();
                }else{
                    map.put(id, currentIR);
                }
            } 
        } catch (IOException ex) {
            Logger.getLogger(FilterIRs.class.getName()).log(Level.SEVERE, null, ex);
        }
        return map;
    }
    
    
    public HashMap<String, InvertedRepeat> filterOverlappingIRs(HashMap<String,InvertedRepeat> IRs, int maxOverlapThreshold, String discardLogPath){
        ArrayList<InvertedRepeat> IRs_acceptedWithinThreshold = new ArrayList<>();
        HashMap<String, InvertedRepeat> map = new HashMap<>();
        try (BufferedWriter w = new BufferedWriter(new FileWriter(new File(discardLogPath)))) {
            Iterator<String> itr = IRs.keySet().iterator();
            while(itr.hasNext()){
                boolean discarded = false;
                String id = itr.next();
                InvertedRepeat currentIR = IRs.get(id);
                for(InvertedRepeat nonOverlappingIR : IRs_acceptedWithinThreshold){
                    if(currentIR.testForOverlapAboveThreshold(nonOverlappingIR, maxOverlapThreshold)){
                        discarded = true;
                    }
                }
                if(discarded){
                    w.write("DISCARDED OVERLAPPING IR:\t"+currentIR.isIsOverlappingAboveThresh()+" "+currentIR.getHeader()+"\tREASON:\toverlaps another IR by "+currentIR.getPercentOverlapping()+" percent (threshold="+maxOverlapThreshold+"%).");
                    w.newLine();
                }else{
                    IRs_acceptedWithinThreshold.add(currentIR);
                    map.put(id, currentIR);
                }
            } 
        } catch (IOException ex) {
            Logger.getLogger(FilterIRs.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        return map;
    }
    
    
//    public void testFilter() throws FileNotFoundException, IOException{
//        HashMap<String, InvertedRepeat> map = new HashMap<>();
//        BufferedReader r = new BufferedReader(new FileReader(new File("/Users/csy06dmu/NetBeansProjects/A_SNF_CountsTable/testIRs/testingATFilter.fa")));
//        String headder = r.readLine();
//        String sequence = r.readLine();
//        int id = 0;
//        while(headder != null){
//            InvertedRepeat ir = new InvertedRepeat(String.valueOf(id), headder, sequence, 0.0, 0.0);
//            map.put(String.valueOf(id), ir);
//            id++;
//            headder = r.readLine();
//            sequence = r.readLine();
//        }
//        this.filterATRichIRs(map, 70, "/Users/csy06dmu/NetBeansProjects/A_SNF_CountsTable/testIRs/testingATFilter.fa.log");
//        System.out.println("map size="+map.size());
//        r.close();
//    }
//    
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) throws FileNotFoundException, IOException {
//        FilterIRs f = new FilterIRs();
//        f.testFilter();
//        
//    }
     
}
