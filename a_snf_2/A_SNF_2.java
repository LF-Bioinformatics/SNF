
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

public class A_SNF_2 {

    /** DEVELOPMENT FLAG **/
    public final static boolean DEV = false;
    public static final String SULF_SEQ = "AAATCCAAAATCAAAGCTCTGGTCATCGATTTCTTCTGCATGAACATACCCATTTTTATTCGACGTCAGTGGCGGCAATAGCTCCTCTCCTCTGCACGTTTCTCCACCACCCAAGGCTACACCAAACTGTTCATGGAGACATCGCAGGTTAGAATGATTTTGTTGAGATACCCGGGTGCCCATTGATTCACTCTTCTGATTTGCCAATGAGTTTGTTTTTCCGTAAGAGTAATGTTTACAAACACCTTCTAGACACTTCCGTAAACATGCGCAAATCGAGCGGGATACTCGTGAACGCGTTCGGCGCGCTCGAGTTTCGAGCTAAGGAAGTTTTGTCTAACGGTTTGTACACGGTCCAACTCCCCTCCTTTATTCACTTTCACCTACAACTGCCGAAACACATCGACACTAAAGTGTCCGTAAACCAACACGAATGTCTATCATGGCTTGATTTACAGCCTAGTAAAAGCGTGATTTTCCTTTGTTTCGGAAGAAGAGGAGCGCTCTCGTAGCACAACAATTGAAAGAAATTGCATTAGGGTTGGGGAAGAGTGGATGTCGATTTCTTTGGTCGGAGCACTTTTCACCGGAGTTGGGCTTTAATTCGCTTCTGCAAGAGGGTTTTTTAATCGAGAACTAAAGGAATAGGGTTTTTGATAAACACATGGATGCCGCAGAAGGAGGTGCTTAGTCATGATGCAGTGGGGCCGGGGTTTGTGACTCATTGCGGGTGGAATTCGGTTCTTGAAGCGGTGTTGTTTGGTGTTCCTATATGATTGGCTGGCCGTTGTATGCAGAGCAGAGGATGAATAGAGTGTTTATGGTGGAGGAAATGAAGATGGCGCTGCCGTTGGACGAGGATGAAGATGGATTTGTGACGGCGGAGGAGGTGGAGAAGCGCGTAAGTATCGAGGGTGGTTAAAGTCACTAAGCAAAAAATATTGTTGGAACGTGGTCATCTGAATCAAGACAATGTAGGTATCGCCTACATTGAATAAAATAATATAATAAGTTCGATGATAAATAGAAGAAGATGTGAAGTCTTGTCATATATATGCTCGTTACAAGTTTTGAATGCTATTGAAATTAATAATTTGGAATGTAATACTTCATATATTACTCGAGACTCGTACACACTTATGCTTATTACGCGCTTCTCCGCCTCCTCCGCCGTCACAAATCCGGTGAAATGTGCTCCAACCAAAGAAATCAACATCCACTCTTCCCCAACCCTAATGCAATTTCTTTCAATTGTTGTGCTACGAGCGCTCCTCTTCTTCCGAAACAAAGGAAAATCACGCTTTTACTAGGCTGTGAATCAAGCCATGATAGACATTCGTGTTGATTTACGGACACTTTAGTGTCGGTGTGTTTCGGCAGTTGTAGGTGAAAGTGAATAAACGAGGGGAGTTGGACCGTACAAACCGTTAGACAAAGCTTCCTTAGCTCGAAACTCGAGCGCGTCCAACGCGTTCACCAGTATCCCGCTCGATTTGCGCATGTTTACGTAAGTGTCTAGAAGGTGTTTGTAAACATTACTCTTACAGTAAAACAAACTCATTGGCAAATCAGAAGAGTGAATCAATGGGCACCCGGGTATCTCAACAAAATCATTCTAACCTGCGATGTCTCCATGAACAGTTTCGTGTAGCCTTGGGTGGCGGAGAAACGTGCAGAGGAGAGAAGCTAGCGCCGTCACTGACGTCCAAGTAAGTGGGTATGTTCAAATACTCGAAATTGATGACTAATGCTTTTATTTTGGATTT";
    public static final String SULF_GRF_SEQ = "TAGCTCCTCTCCTCTGCACGTTTCTCCACCACCCAAGGCTACACCAAACTGTTCATGGAGACATCGCAGGTTAGAATGATTTTGTTGAGATACCCGGGTGCCCATTGATTCACTCTTCTGATTTGCCAATGAGTTTGTTTTTCCGTAAGAGTAATGTTTACAAACACCTTCTAGACACTTCCGTAAACATGCGCAAATCGAGCGGGATACTCGTGAACGCGTTCGGCGCGCTCGAGTTTCGAGCTAAGGAAGTTTTGTCTAACGGTTTGTACACGGTCCAACTCCCCTCCTTTATTCACTTTCACCTACAACTGCCGAAACACATCGACACTAAAGTGTCCGTAAACCAACACGAATGTCTATCATGGCTTGATTTACAGCCTAGTAAAAGCGTGATTTTCCTTTGTTTCGGAAGAAGAGGAGCGCTCTCGTAGCACAACAATTGAAAGAAATTGCATTAGGGTTGGGGAAGAGTGGATGTCGATTTCTTTGGTCGGAGCACTTTTCACCGGAGTTGGGCTTTAATTCGCTTCTGCAAGAGGGTTTTTTAATCGAGAACTAAAGGAATAGGGTTTTTGATAAACACATGGATGCCGCAGAAGGAGGTGCTTAGTCATGATGCAGTGGGGCCGGGGTTTGTGACTCATTGCGGGTGGAATTCGGTTCTTGAAGCGGTGTTGTTTGGTGTTCCTATATGATTGGCTGGCCGTTGTATGCAGAGCAGAGGATGAATAGAGTGTTTATGGTGGAGGAAATGAAGATGGCGCTGCCGTTGGACGAGGATGAAGATGGATTTGTGACGGCGGAGGAGGTGGAGAAGCGCGTAAGTATCGAGGGTGGTTAAAGTCACTAAGCAAAAAATATTGTTGGAACGTGGTCATCTGAATCAAGACAATGTAGGTATCGCCTACATTGAATAAAATAATATAATAAGTTCGATGATAAATAGAAGAAGATGTGAAGTCTTGTCATATATATGCTCGTTACAAGTTTTGAATGCTATTGAAATTAATAATTTGGAATGTAATACTTCATATATTACTCGAGACTCGTACACACTTATGCTTATTACGCGCTTCTCCGCCTCCTCCGCCGTCACAAATCCGGTGAAATGTGCTCCAACCAAAGAAATCAACATCCACTCTTCCCCAACCCTAATGCAATTTCTTTCAATTGTTGTGCTACGAGCGCTCCTCTTCTTCCGAAACAAAGGAAAATCACGCTTTTACTAGGCTGTGAATCAAGCCATGATAGACATTCGTGTTGATTTACGGACACTTTAGTGTCGGTGTGTTTCGGCAGTTGTAGGTGAAAGTGAATAAACGAGGGGAGTTGGACCGTACAAACCGTTAGACAAAGCTTCCTTAGCTCGAAACTCGAGCGCGTCCAACGCGTTCACCAGTATCCCGCTCGATTTGCGCATGTTTACGTAAGTGTCTAGAAGGTGTTTGTAAACATTACTCTTACAGTAAAACAAACTCATTGGCAAATCAGAAGAGTGAATCAATGGGCACCCGGGTATCTCAACAAAATCATTCTAACCTGCGATGTCTCCATGAACAGTTTCGTGTAGCCTTGGGTGGCGGAGAAACGTGCAGAGGAGAGAAGCTA";

    /** Tool version.**/
    public static final String VERSION = "1.3.1";
    /** Map of Inverted Repeats key = chrom:start:end **/
    private final HashMap<String,InvertedRepeat> IRs;
    /**IRs in bed format.**/
    private Bed irsInBedFormat;
    /** Map of the patman generated small RNA alignments using sRNA sequence as the key. **/
    private final HashMap<String, ArrayList<PatmanAlignment>> alignments;
    /**This patman alignment file in Bed6 format.**/
    private Bed patmanAlignmentInBed;
    /** Map of the patman generated small RNA alignments to conserved mirna hairpins using patman. **/
    private final HashMap<String, ArrayList<PatmanAlignment>> alignmentsToHairpins;
    /** Total number of sRNA reads mapped. **/
    private int totalMappedReads;
    /** Provided IRs file. **/
    private File irsFile;
    /** Provided patman alignments file. **/
    private File patmanFile;
    
    public A_SNF_2() {
        this.IRs = new HashMap<>();
        this.alignments = new HashMap<>();
        this.alignmentsToHairpins = new HashMap<>();
    }
    
    public void start(File invertedRepeats, File patmanAlignments, File blastn, File outDir){
        this.irsFile = invertedRepeats;
        this.patmanFile = patmanAlignments;
        System.out.print("Loading IRs...");
        this.loadInvertedRepeats(invertedRepeats);
        System.out.println("done.");
        System.out.print("Loading alignments...");
        this.loadPatmanAlignments(patmanAlignments);
        System.out.println("done.");
        System.out.print("Loading blast-results...");
        this.loadBlastResults(blastn);
        System.out.println("done.");
        System.out.print("Processing sRNA alignments...");
        this.calculateWeightedSrnaAbundance();
        this.assignAlignmentsToIRs();
        this.calculateAverageSrnaLength();
        this.calculateAverageComplexity();
        this.calculateRawSrnaCoverage();
        System.out.println("done.");
        System.out.print("Output to file...");
        this.printCountsSummaryTable(outDir);
        this.printIRsTable(outDir); 
        System.out.println("done.");
        System.out.println("Making Venn lists");
        this.makeVennLists(outDir);
        System.out.println("done.");
        System.out.println("Generate bed file for alignments");
        this.irsInBedFormat = this.getIRsInBed("IRsInBedFormat", "Ensemble");
        this.outputBedToFile("irs.bed", irsInBedFormat, outDir);
        this.patmanAlignmentInBed = this.getPatmanAlignmentsInBed(patmanAlignments.getName(), "Ensembl");
        this.outputBedToFile(this.patmanFile.getName()+".bed", patmanAlignmentInBed, outDir);
        System.out.println("done.");
        System.out.println("Processing completed.");  
    }
    
    public void outputBedToFile(String name, Bed bed, File outDir){
        try (BufferedWriter w = new BufferedWriter(new FileWriter(new File(outDir.getAbsolutePath()+"/"+name)))) {
            Iterator<String> itr = bed.getAllRecords().keySet().iterator();
            while(itr.hasNext()){
                String id = itr.next();
                ArrayList<BedRecord> recordList = bed.getAllRecords().get(id);
                for(BedRecord record :  recordList){
                    w.write(record.getRecord()); w.newLine();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(A_SNF_2.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    public Bed getPatmanAlignmentsInBed(String id, String convention){
        Bed aligns = new Bed(id, convention);
        Iterator<String> itr = this.IRs.keySet().iterator();
        while(itr.hasNext()){
            String irID = itr.next();
            ArrayList<PatmanAlignment> alignmentsList = this.IRs.get(irID).getsRNAsAlignments();
            for(PatmanAlignment p : alignmentsList){
                for(int i = 0; i < p.getSrnaAbundance(); i++){
                    //String[] tokens, String convention, String originalRecordLine
                    String[] tokens = {
                        this.IRs.get(irID).getChromosome().replace(">", ""),
                        ""+(this.IRs.get(irID).getStart()+p.getStart()),
                        ""+(this.IRs.get(irID).getStart()+p.getEnd()),
                        p.getSrnaSequence(),
                        "1",
                        p.getStrand()};
                    BedRecord rec = new BedRecord(tokens, "Ensembl", ".");
                    aligns.addBedRecord(rec);
                }      
            }
        }
        return aligns;    
    }
    
    public Bed getIRsInBed(String id, String convention){
        Bed irsInBed = new Bed(id, convention);
        Iterator<String> itr = this.IRs.keySet().iterator();
        while(itr.hasNext()){
            String irID = itr.next();
            String[] tokens = {
                this.IRs.get(irID).getChromosome().replace(">", ""),
                ""+this.IRs.get(irID).getStart(),
                ""+this.IRs.get(irID).getEnd(),
                irID,
                "0",
                "+"};
            BedRecord rec = new BedRecord(tokens, "Ensembl", ".");
            irsInBed.addBedRecord(rec);
        }
        return irsInBed;    
    }
    
    private void loadBlastResults(File blastn){
        try (BufferedReader r = new BufferedReader(new FileReader(blastn))) {
            String line = r.readLine();
            while(line != null) {
                BlastHit b = new BlastHit(line);
                InvertedRepeat ir = this.IRs.get(b.getIR_ID());
                if(this.IRs.containsKey(b.getIR_ID())){
                    this.IRs.get(b.getIR_ID()).addBlastHit(b);
                }else {
                    System.err.println("Error: blast hit for an unknown IR found in file "+blastn.getName());
                    System.exit(1);
                }
                line = r.readLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(A_SNF_2.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    private String getIdFromIrHeader(String header){
        return (header.split(":")[0]+":"+header.split(":")[1]+":"+header.split(":")[2]).replace(">", "");
    }
    
    private void loadInvertedRepeats(File invertedRepeats){
        try (BufferedReader r = new BufferedReader(new FileReader(invertedRepeats))) {
            String head = r.readLine();
            String seq = r.readLine();
            while(head != null){
                String id = this.getIdFromIrHeader(head);
                if(!this.IRs.containsKey(id)){
                    InvertedRepeat ir = new InvertedRepeat(id, head, seq);
                    this.IRs.put(id, ir);
                } else {
                    System.err.println("WARNING: duplicate inverted repeat ID within IRs fasta file. Record with duplicate fasta ID ignored.");
                }
                head = r.readLine();
                seq = r.readLine();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(A_SNF_2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(A_SNF_2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadPatmanAlignments(File patmanAlignments) {
        int alignmentsCount = 0;
        try (BufferedReader r = new BufferedReader(new FileReader(patmanAlignments))) {
            String line = r.readLine();
            while(line != null){
                PatmanAlignment p = new PatmanAlignment(line);
                if(!this.alignments.containsKey(p.getSrnaSequence())){
                    ArrayList<PatmanAlignment> pList= new ArrayList<>();
                    pList.add(p);
                    this.alignments.put(p.getSrnaSequence(), pList);
                    alignmentsCount++;
                } else {
                    this.alignments.get(p.getSrnaSequence()).add(p);
                    alignmentsCount++;
                }
                line = r.readLine();
            }
            this.totalMappedReads = alignmentsCount;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(A_SNF_2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(A_SNF_2.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    private void calculateWeightedSrnaAbundance() {
        Iterator<String> itr = this.alignments.keySet().iterator();
        while(itr.hasNext()){
            String srna = itr.next();
            ArrayList<PatmanAlignment> alignList = this.alignments.get(srna);
            //The abundance of the sRNA (shown in any record in list) divided by the total number of positions this sRNA aligned to.
            double weightedAbundance = (double) alignList.get(0).getSrnaAbundance() / (double) alignList.size();
            for(PatmanAlignment align : alignList){
                align.setWeightedAbundance(weightedAbundance);
            }
        }
    }
    
    private void calculateAverageSrnaLength() {
        Iterator<String> itr = this.IRs.keySet().iterator();
        while(itr.hasNext()){
            this.IRs.get(itr.next()).calculateAverageSrnaLength();
        }
    }
    
    private void calculateAverageComplexity() {
        Iterator<String> itr = this.IRs.keySet().iterator();
        while(itr.hasNext()){
            this.IRs.get(itr.next()).calculateAverageComplexity();
        }
    }
    
    private void calculateRawSrnaCoverage() {
        Iterator<String> itr = this.IRs.keySet().iterator();
        while(itr.hasNext()){
            this.IRs.get(itr.next()).generateCoverageTable();
        }
    }
    
    private void printIRsTable(File outDir) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(new File(outDir.getAbsolutePath()+"/IRsTable.tsv")))) {
            Iterator<String> itr = this.IRs.keySet().iterator();
            w.write(InvertedRepeat.printStringHeader());
            w.newLine();
            while(itr.hasNext()){
                w.write(this.IRs.get(itr.next()).printString());
                w.newLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(A_SNF_2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void assignAlignmentsToIRs() {
        Iterator<String> itr = this.alignments.keySet().iterator();
        while(itr.hasNext()){
            String srna = itr.next();
            ArrayList<PatmanAlignment> srnaList = this.alignments.get(srna);
            for(PatmanAlignment p : srnaList){
                String irID = this.getIdFromIrHeader(p.getReferenceIdentifier());
                if(this.IRs.containsKey(irID)) {
                    this.IRs.get(irID).addSrnaAlignment(p);
                    if(this.alignmentsToHairpins.containsKey(p.getSrnaSequence())){
                        this.IRs.get(irID).setHasSrnaMappingToMirnaHairpin(true);
                    }
                } else {
                    System.err.println("WARNING: A srna alignment contains a reference identifier not found within the inverted repeats collection. Ignorning the following sRNA alignment.");
                    System.err.println(irID);
                    System.err.println(p.getReferenceIdentifier());
                }
            }
        }
    }
    
    public void printCountsSummaryTable(File outDir){
        try (BufferedWriter w = new BufferedWriter(new FileWriter(new File(outDir.getAbsolutePath()+"/summaryTable.tsv")))) {
            w.write("IRs file:\t"+this.irsFile.getAbsolutePath());
            w.newLine();
            w.write("Patman alignments file:\t"+this.patmanFile.getAbsolutePath());
            w.newLine();
            w.write("Total IRs:\t"+this.IRs.size());
            w.newLine();
            w.write("Total reads (aligned):\t"+this.totalMappedReads);
            w.newLine();
            w.write("Total unique sRNAs (aligned):\t"+this.alignments.size());
            w.newLine();
            w.newLine();
            w.write("Expression and frequency distribution of inverted repeats (expression using weighted abundance).");
            w.newLine();
            String[] srnaThresholds = {"total IRs","IRs with a mapped sRNA abundance >=5","IRs with a mapped sRNA abundance >=10","IRs with a mapped sRNA abundance >=15","IRs with a mapped sRNA abundance >=20","IRs with a mapped sRNA abundance >=25","IRs with a mapped sRNA abundance >=50","IRs with a mapped sRNA abundance >=100","IRs with a mapped sRNA abundance >=500"};
            this.printList(" ",srnaThresholds, w);
            this.printList("100", this.getWeightedCountForRange(0, 100), w);
            this.printList("200", this.getWeightedCountForRange(100, 200), w);
            this.printList("300", this.getWeightedCountForRange(200, 300), w);
            this.printList("400", this.getWeightedCountForRange(300, 400), w);
            this.printList("500", this.getWeightedCountForRange(400, 500), w);
            this.printList("600", this.getWeightedCountForRange(500, 600), w);
            this.printList("700", this.getWeightedCountForRange(600, 700), w);
            this.printList("800", this.getWeightedCountForRange(700, 800), w);
            this.printList("900", this.getWeightedCountForRange(800, 900), w);
            this.printList("1000", this.getWeightedCountForRange(900, 1000), w);
            this.printList("1100", this.getWeightedCountForRange(1000, 1100), w);
            this.printList("1200", this.getWeightedCountForRange(1100, 1200), w);
            this.printList("1300", this.getWeightedCountForRange(1200, 1300), w);
            this.printList("1400", this.getWeightedCountForRange(1300, 1400), w);
            this.printList("1500", this.getWeightedCountForRange(1400, 1500), w);
            this.printList("1600", this.getWeightedCountForRange(1500, 1600), w);
            this.printList("1700", this.getWeightedCountForRange(1600, 1700), w);
            this.printList("1800", this.getWeightedCountForRange(1700, 1800), w);
        } catch (IOException ex) {
            Logger.getLogger(A_SNF_2.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    private void printList(String label, int[] list, BufferedWriter w) throws IOException{
        w.write(label+"\t");
        for(int i = 0; i < list.length; i++){
            w.write(list[i]+"\t");
        }
        w.newLine();
    }
    
    private void printList(String label, String[] list, BufferedWriter w) throws IOException{
        w.write(label+"\t");
        for(String item : list) {
            w.write(item + "\t");
        }
        w.newLine();
    }
    
    private int[] getWeightedCountForRange(int irLengthLowerLimit, int irLengthUpperLimit) {
        int[] countsTable = new int[9];
        Iterator<String> itr = this.IRs.keySet().iterator();
        while(itr.hasNext()){
            InvertedRepeat ir = this.IRs.get(itr.next());
            if(ir.getLength() > irLengthLowerLimit && ir.getLength() <= irLengthUpperLimit){
                countsTable[0] += 1.0f;
                countsTable[1] += this.passFilterThresh(this.getSumWeightedAbundance(ir.getsRNAsAlignments()), 5);
                countsTable[2] += this.passFilterThresh(this.getSumWeightedAbundance(ir.getsRNAsAlignments()), 10);
                countsTable[3] += this.passFilterThresh(this.getSumWeightedAbundance(ir.getsRNAsAlignments()), 15);
                countsTable[4] += this.passFilterThresh(this.getSumWeightedAbundance(ir.getsRNAsAlignments()), 20);
                countsTable[5] += this.passFilterThresh(this.getSumWeightedAbundance(ir.getsRNAsAlignments()), 25);
                countsTable[6] += this.passFilterThresh(this.getSumWeightedAbundance(ir.getsRNAsAlignments()), 50);
                countsTable[7] += this.passFilterThresh(this.getSumWeightedAbundance(ir.getsRNAsAlignments()), 100);
                countsTable[8] += this.passFilterThresh(this.getSumWeightedAbundance(ir.getsRNAsAlignments()), 500);
            }
        }
        return countsTable;
    }
    
    private double passFilterThresh(double toTest, int threshold){
        if(toTest >= (double) threshold){
            return 1;
        }else{
            return 0;
        }
    }
    
    private double getSumWeightedAbundance(ArrayList<PatmanAlignment> alignments){
        double sum = 0.0f;
        for(PatmanAlignment align : alignments){
            sum += align.getWeightedAbundance();
        }
        return sum;
    }
    
    public static void main(String[] args) {
        
        A_SNF_2 a = new A_SNF_2();

        if(a.validateParameters(args)) {
            a.start(new File(args[0]), new File(args[1]), new File(args[2]), new File(args[3]));
        } else {
            System.out.println("A SNF CountTables version " + A_SNF_2.VERSION);
            System.out.println("Usage: java -jar A_SNF_CountTables.jar <IRs> <alignments> <blastn-table> <out-directory>");
            System.out.println("IRs = inverted repeats (mandatory).");
            System.out.println("alignments = small RNAs aligned to IRs using PatMaN (mandatory).");
            System.out.println("blastn-table = output from blastn IRs");
            System.out.println("out-directory = directory for the output of the tables generated (mandatory).");
        }
    }
    
    public void makeVennLists(File outDir){
        Iterator<String> itr = this.IRs.keySet().iterator();
        ArrayList<String> hasSrna = new ArrayList<>();
        ArrayList<String> hasTarget = new ArrayList<>();
        ArrayList<String> hasModeSrnaLength21 = new ArrayList<>();
        try (BufferedWriter w = new BufferedWriter(new FileWriter(new File(outDir.getAbsolutePath()+"/VennList_ALL_IRs.txt")))) {
            while(itr.hasNext()){
                InvertedRepeat ir = this.IRs.get(itr.next());
                
                boolean[] filter = new boolean[4];
                if(ir.getsRNAsAlignments().size() > 0){
                    hasSrna.add(ir.getID());
                    filter[0] = true;
                }
                if(ir.getBlastHits().size() > 0){
                    hasTarget.add(ir.getID());
                    filter[1] = true;
                }
                if(ir.getModeMappedSrnaLength() == 21){
                    hasModeSrnaLength21.add(ir.getID());
                    filter[2] = true;
                }
                w.write(ir.getID()); w.newLine();

                if(filter[0] && filter[1] && filter[2] && !filter[3]){
                    System.out.println(ir.getChromosome().replace(">", "")+"\t"+ir.getStart()+"\t"+ir.getEnd()+"\t"+ir.getID()+"\t"+"0"+"\t"+"+");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(A_SNF_2.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try (BufferedWriter w = new BufferedWriter(new FileWriter(new File(outDir.getAbsolutePath()+"/VennList_HasSrna.txt")))) {
            for(String entry : hasSrna){ w.write(entry); w.newLine();} 
        } catch (IOException ex) {
            Logger.getLogger(A_SNF_2.class.getName()).log(Level.SEVERE, null, ex);
        }  
        
        try (BufferedWriter w = new BufferedWriter(new FileWriter(new File(outDir.getAbsolutePath()+"/VennList_HasTarget.txt")))) {
            for(String entry : hasTarget){ w.write(entry); w.newLine();}
        } catch (IOException ex) {
            Logger.getLogger(A_SNF_2.class.getName()).log(Level.SEVERE, null, ex);
        }  
        
        try (BufferedWriter w = new BufferedWriter(new FileWriter(new File(outDir.getAbsolutePath()+"/VennList_HasModeSrnaLength21.txt")))) {
            for(String entry : hasModeSrnaLength21){ w.write(entry); w.newLine();}
        } catch (IOException ex) {
            Logger.getLogger(A_SNF_2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean validateParameters(String[] args){

        if(args == null || args.length < 2){
            System.err.println("Error: please check the number of arguments provided to the software.");
            return false;
        }
        for (int i = 0; i < 2; i++) {
            File f = new File(args[i]);
            if(!f.exists() || !f.canRead()){
                System.err.println("Error: can not read file "+f.getAbsolutePath());
                return false;
            }
        }
        return true;
    }    
}
