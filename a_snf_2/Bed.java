
package a_snf_2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bed {
    
    private String ID;
    private String convention;
    private int totalRecordsCount = 0;
    private HashMap<String, ArrayList<BedRecord>> records; 
      
    public Bed(String id, String convention){
        this.ID = id;
        this.convention = convention;
        this.records = new HashMap<>();
    }
    
    public Bed(File f, String id, String convention){
        this.ID = id;
        this.convention = convention;
        this.records = new HashMap<>();
        try (BufferedReader r = new BufferedReader(new FileReader(f))) {
            String line = r.readLine();
            while(line != null){
                BedRecord rec = new BedRecord(line.split("\t"), convention, line);
                if(records.containsKey(rec.getChrom())){
                    records.get(rec.getChrom()).add(rec);
                    this.totalRecordsCount++;
                }else{
                    records.put(rec.getChrom(), new ArrayList<>());
                    records.get(rec.getChrom()).add(rec);
                    this.totalRecordsCount++;
                }
                line = r.readLine();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Bed.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Bed.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addBedRecord(BedRecord rec){
        if(records.containsKey(rec.getChrom())){
            records.get(rec.getChrom()).add(rec);
            this.totalRecordsCount++;
        }else{
            records.put(rec.getChrom(), new ArrayList<>());
            records.get(rec.getChrom()).add(rec);
            this.totalRecordsCount++;
        }
    }

    public ArrayList<BedRecord> getRecords(String chrom) {
        return this.records.get(chrom);
    }

    public HashMap<String,ArrayList<BedRecord>> getAllRecords() {
        return this.records;
    }

    public int getTotalRecordsCount() {
        return totalRecordsCount;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
     
}