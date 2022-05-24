
package a_snf_1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FastaRedundantToNR {
    
    public File makeNR(File fastaRedundant) {
        HashMap<String, Integer> map = new HashMap<>();
        try (BufferedReader r = new BufferedReader(new FileReader(fastaRedundant))) {
            String headder = r.readLine();
            String seq = r.readLine();
            while(headder != null){
                if(map.containsKey(seq)){
                    Integer count = map.get(seq);
                    map.put(seq, count+1);
                }else{
                    map.put(seq, 1);
                }
                headder = r.readLine();
                seq = r.readLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(FastaRedundantToNR.class.getName()).log(Level.SEVERE, null, ex);
        }
        File outFile = new File(fastaRedundant.getAbsolutePath()+".nr.fa");
        try (BufferedWriter w = new BufferedWriter(new FileWriter(outFile))) {
            Iterator<String> itr = map.keySet().iterator();
            while(itr.hasNext()){
                String seq = itr.next();
                Integer count = map.get(seq);
                w.write(">"+seq+"::"+count);
                w.newLine();
                w.write(seq);
                w.newLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(FastaRedundantToNR.class.getName()).log(Level.SEVERE, null, ex);
        }
        return outFile;
    } 
    
}
