
package a_snf_2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.IntPredicate;

public class InvertedRepeat {
    
    /** DEV Extras. **/
    private String devExtra = "";
    
    /** ID is chrom:start:end. **/
    private final String ID;
    /** Header of IR (excluding '>' prefix). **/
    private final String header;
    /** Inverted repeat sequence. **/
    private final String sequence;
    /** Length of inverted repeat. **/
    private final int length;
    /** Chromosome. **/
    private final String chromosome;
    /** Start coordiante. **/
    private final int start;
    /** End coordinate. **/
    private final int end;
    /** Small RNAs aligned to this inverted repeat. **/
    private final ArrayList<PatmanAlignment> sRNAsAlignments;
    /** total raw sRNA reads aligned. **/
    private int totalRawSrnaReadsAlignedCount;
    /** Small RNAs aligned to this inverted repeat - NR MAP. **/
    private final HashMap<String, ArrayList<PatmanAlignment>> sRNAsAlignmentsNrMap;
    /** Average complexity of IR sRNA mapping. **/
    private double averageComplexity;
    /** total weighted sRNA reads aligned. **/
    private double totalWeightedSrnaReadsAlignedCount;
    /** mode length of sRNAs mapping. **/
    private int modeMappedSrnaLength;
    /** weighted mode length of sRNAs mapping. **/
    private int weightedModeMappedSrnaLength;
    /** mean length of sRNAs mapping. **/
    private double meanMappedSrnaLength;
    /** weighted mean length of sRNAs mapping. **/
    private double weightedMeanMappedSrnaLength;
    /** sRNA nt coverage list for the length of the IR. **/
    private int[] coverageList_plus;
    private int[] coverageList_minus;
    /** blastn hits for this IR. **/
    private final ArrayList<BlastHit> blastHits;
    /** has srna which maps to known miRNA hairpin. **/
    private boolean hasSrnaMappingToMirnaHairpin;
    /** blast eval threshold (default value 10^-6). **/
    private final double blastEvalThreshold;
    /** IR coverage of blast hit threshold (default 0.5). **/
    private final double blastHitCoverageThreshold;
    /**Overlaps another IR**/
    boolean isOverlappingAboveThresh = false;
    /**Percent overlapping with another IR**/
    double percentOverlapping = 0.0;
    /**Top Blast Hit**/
    BlastHit topBlastHit;
    
    public InvertedRepeat(String id, String header, String sequence, double blastEvalThreshold, double blastHitCoverageThreshold){
        if(A_SNF_2.DEV){
            if(sequence.contains(A_SNF_2.SULF_GRF_SEQ)){
                this.devExtra += "SULF";
            }
        }
        this.ID = id;
        this.header = header.replace(header, header);
        this.sequence = sequence;
        this.length = sequence.length();
        String[] toks = this.header.split(":");
        this.chromosome = toks[0];
        this.start = Integer.parseInt(toks[1]);
        this.end = Integer.parseInt(toks[2]);
        this.sRNAsAlignments = new ArrayList<>();
        this.totalRawSrnaReadsAlignedCount = 0;
        this.sRNAsAlignmentsNrMap = new HashMap<>();
        this.totalWeightedSrnaReadsAlignedCount = 0.0f;
        this.modeMappedSrnaLength = 0;
        this.weightedModeMappedSrnaLength = 0;
        this.meanMappedSrnaLength = 0.0;
        this.weightedMeanMappedSrnaLength = 0.0;
        this.blastHits = new ArrayList<>();
        this.hasSrnaMappingToMirnaHairpin = false;
        this.blastEvalThreshold = blastEvalThreshold;
        this.blastHitCoverageThreshold = blastHitCoverageThreshold;
    }
    
    public boolean testForOverlapAboveThreshold(InvertedRepeat ir, int maxOverlapThreshold){
        if(ir.getChromosome().equalsIgnoreCase(this.getChromosome())){
            if(this.start >= ir.start && this.start <= ir.start){
                double pcOverlap = this.getPercentOverlapWith(ir);
                if(pcOverlap > maxOverlapThreshold){
                    this.setIsOverlappingAboveThresh(true);
                    this.setPercentOverlapping(pcOverlap);
                    return true;
                }
            }else if(this.end <= ir.end && this.end >= ir.start){
                double pcOverlap = this.getPercentOverlapWith(ir);
                if(pcOverlap > maxOverlapThreshold){
                    this.setIsOverlappingAboveThresh(true);
                    this.setPercentOverlapping(pcOverlap);
                    return true;
                }
            }else if(ir.start >= this.start && ir.start <= this.end){
                double pcOverlap = this.getPercentOverlapWith(ir);
                if(pcOverlap > maxOverlapThreshold){
                    this.setIsOverlappingAboveThresh(true);
                    this.setPercentOverlapping(pcOverlap);
                    return true;
                }
            }else if(ir.end <= this.end && ir.end >= this.start){
                double pcOverlap = this.getPercentOverlapWith(ir);
                if(pcOverlap > maxOverlapThreshold){
                    this.setIsOverlappingAboveThresh(true);
                    this.setPercentOverlapping(pcOverlap);
                    return true;
                }
            }
        }
        return false;
    } 
    
    private double getPercentOverlapWith(InvertedRepeat ir){ 
        int basePositionsShared = 0;
        InvertedRepeat thisIR = this;
        InvertedRepeat thatIR = ir;
        for(int basePosition = thisIR.getStart(); basePosition <= thisIR.getEnd(); basePosition++){
            if(basePosition >= thatIR.getStart() && basePosition <= thatIR.getEnd()){
                basePositionsShared++;
            }
        }
        double pcOverlap = ((double)basePositionsShared/(double)thisIR.getLength())*100.00;
        return pcOverlap;
    }
    
    
    
    public BlastHit getTopBlastHit(){
        if(!this.blastHits.isEmpty()){
            BlastHit top = this.blastHits.get(0);
            for(BlastHit hit : this.blastHits){
                if(Double.parseDouble(hit.getEvalue()) < Double.parseDouble(top.getEvalue())){
                    top = hit;
                }
            }
            return top;
        }else{
            return null;
        }
    }
    
    public void generateCoverageTable() {
        int[] ir_plus = new int[this.getLength()];
        int[] ir_minus = new int[this.getLength()];
        Iterator<String> itr = this.sRNAsAlignmentsNrMap.keySet().iterator();
        while(itr.hasNext()){
            String seq = itr.next();
            ArrayList<PatmanAlignment> alignments = this.sRNAsAlignmentsNrMap.get(seq);
            for(PatmanAlignment alignment : alignments){
                int indexStart = alignment.getStart()-1; //-1 because patman alignment is 1 bassed and index is 0 bassed.
                for(int i = indexStart; i < (indexStart+seq.length()); i++){
                    if(alignment.getStrand().equalsIgnoreCase("+")) {
                        ir_plus[i] += alignment.getSrnaAbundance();
                    }else {
                        ir_minus[i] += alignment.getSrnaAbundance();
                    }
                }
            }   
        }
        this.coverageList_plus = ir_plus;
        this.coverageList_minus = ir_minus;
    }
    
    public void addSrnaAlignment(PatmanAlignment alignment){
        this.totalRawSrnaReadsAlignedCount += alignment.getSrnaAbundance();
        this.totalWeightedSrnaReadsAlignedCount += alignment.getWeightedAbundance();
        this.sRNAsAlignments.add(alignment);
        if(this.sRNAsAlignmentsNrMap.containsKey(alignment.getSrnaSequence())){
            this.sRNAsAlignmentsNrMap.get(alignment.getSrnaSequence()).add(alignment);
        } else {
            ArrayList<PatmanAlignment> list = new ArrayList<>();
            list.add(alignment);
            this.sRNAsAlignmentsNrMap.put(alignment.getSrnaSequence(), list);
        }
    }
    
    public void addBlastHit(BlastHit b){ 
        if(Double.parseDouble(b.getEvalue()) <= this.blastEvalThreshold){
            if(b.getOverlap() >= this.blastHitCoverageThreshold) {
                this.blastHits.add(b);
            }
        }
    }
    
    public void calculateAverageSrnaLength(){
        ArrayList<Integer> listRaw = new ArrayList<>();
        ArrayList<Integer> listWeighted = new ArrayList<>();
        for(PatmanAlignment aln : this.getsRNAsAlignments()){
            int rawAbundance = aln.getSrnaAbundance();
            double weightedAbundance = aln.getWeightedAbundance();
            for(int i = 0; i < rawAbundance; i++){
                listRaw.add(aln.getSrnaLength());
            }
            for(int i = 0; i < Math.ceil(weightedAbundance); i++){
                listWeighted.add(aln.getSrnaLength());
            }
        }
        this.modeMappedSrnaLength = Average.mode(listRaw);
        this.weightedModeMappedSrnaLength = Average.mode(listWeighted);
        if(this.getTotalRawSrnaReadsAlignedCount() > 0) { //solves NaN reporting if zero reads mapped.
            this.meanMappedSrnaLength = Average.mean(listRaw);
            this.weightedMeanMappedSrnaLength = Average.mean(listWeighted);
        }
    }
    
    public void calculateAverageComplexity() {
        if(this.getTotalRawSrnaReadsAlignedCount() > 0){
            this.averageComplexity = (double) this.getTotalNrSrnaReadsAlignedCount() / (double) this.getTotalRawSrnaReadsAlignedCount();
        }
    }

    public String getID() {
        return ID;
    }

    public String getHeader() {
        return header;
    }

    public String getSequence() {
        return sequence;
    }

    public int getLength() {
        return length;
    }

    public ArrayList<PatmanAlignment> getsRNAsAlignments() {
        return sRNAsAlignments;
    }

    public int getTotalRawSrnaReadsAlignedCount() {
        return totalRawSrnaReadsAlignedCount;
    }
    
    public int getTotalNrSrnaReadsAlignedCount(){
        return this.sRNAsAlignmentsNrMap.size();
    }

    public double getTotalWeightedSrnaReadsAlignedCount() {
        return totalWeightedSrnaReadsAlignedCount;
    }

    public int getModeMappedSrnaLength() {
        return modeMappedSrnaLength;
    }

    public int getWeightedModeMappedSrnaLength() {
        return weightedModeMappedSrnaLength;
    }

    public String getChromosome() {
        return chromosome;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public double getMeanMappedSrnaLength() {
        return meanMappedSrnaLength;
    }

    public double getWeightedMeanMappedSrnaLength() {
        return weightedMeanMappedSrnaLength;
    }

    public double getAverageComplexity() {
        return averageComplexity;
    }

    public ArrayList<BlastHit> getBlastHits() {
        return blastHits;
    }

    public static String printCoverageHeader(){
        StringBuilder s = new StringBuilder();
        s.append("Position (nt):"); s.append("\t");
        for(int i = 0; i < 2000; i++) {
            s.append(i); s.append("\t");
            s.append("etc."); s.append("\t");
        }
        return s.toString();
    }
    
    public static String printStringHeader(){
        StringBuilder s = new StringBuilder();
        s.append("Chromosome"); s.append("\t");
        s.append("Start"); s.append("\t");
        s.append("End"); s.append("\t");
        s.append("(Raw) sRNA Reads Mapped Total Count"); s.append("\t");
        s.append("(Raw) Mode sRNA Length"); s.append("\t");
        s.append("(Raw) Mean sRNA Length"); s.append("\t");
        s.append("(Weighted) sRNA Reads Mapped Total Count"); s.append("\t");
        s.append("(Weighted) Mode sRNA Length"); s.append("\t");
        s.append("(Weighted) Mean sRNA Length"); s.append("\t");
        s.append("(NR - Unique) sRNA Reads Mapped Total Count"); s.append("\t");
        s.append("Average Complexity"); s.append("\t");
        s.append("IR Length"); s.append("\t");
        s.append("Top BlastHit IR"); s.append("\t");
        s.append("Top BlastHit Target"); s.append("\t");
        s.append("Top BlastHit E-val"); s.append("\t");
        s.append("IR Sequence"); s.append("\t");
        
        return s.toString();
    }
    
    public String printString(){
        StringBuilder s = new StringBuilder();
        s.append(this.getChromosome()); s.append("\t");
        s.append(this.getStart()); s.append("\t");
        s.append(this.getEnd()); s.append("\t");
        s.append(this.getTotalRawSrnaReadsAlignedCount()); s.append("\t");
        s.append(this.getModeMappedSrnaLength()); s.append("\t");
        s.append(this.getMeanMappedSrnaLength()); s.append("\t");
        s.append(this.getTotalWeightedSrnaReadsAlignedCount()); s.append("\t");
        s.append(this.getWeightedModeMappedSrnaLength()); s.append("\t");
        s.append(this.getWeightedMeanMappedSrnaLength()); s.append("\t");
        s.append(this.getTotalNrSrnaReadsAlignedCount()); s.append("\t");
        s.append(this.getAverageComplexity()); s.append("\t");
        s.append(this.getLength()); s.append("\t");
        BlastHit b = this.getTopBlastHit();
        if(b != null){
            s.append(b.getIR_ID()); s.append("\t");
            s.append(b.getHitID()); s.append("\t");
            s.append(b.getEvalue()); s.append("\t");
        }else {
            s.append("NA\tNA\tNA\t");
        }
        s.append(this.sequence); s.append("\t");
        return s.toString();
    }
    
    public String printCoveragePlus() {
        StringBuilder s = new StringBuilder();
        s.append(this.ID); s.append("\t");
        s.append("Coverage +:"); s.append("\t");
        for(int i = 0; i < this.coverageList_plus.length; i++){
            s.append(this.coverageList_plus[i]); s.append("\t");
        }
        return s.toString();
    }
    
    public String printCoverageMinus() {
        StringBuilder s = new StringBuilder();
        s.append(this.ID); s.append("\t");
        s.append("Coverage -:"); s.append("\t");
        for(int i = 0; i < this.coverageList_minus.length; i++){
            s.append(this.coverageList_minus[i]); s.append("\t");
        }
        return s.toString();
    }

    public void setHasSrnaMappingToMirnaHairpin(boolean hasSrnaMappingToMirnaHairpin) {
        this.hasSrnaMappingToMirnaHairpin = hasSrnaMappingToMirnaHairpin;
    }

    public boolean isHasSrnaMappingToMirnaHairpin() {
        return hasSrnaMappingToMirnaHairpin;
    }

    public boolean isIsOverlappingAboveThresh() {
        return isOverlappingAboveThresh;
    }

    public void setIsOverlappingAboveThresh(boolean isOverlappingAboveThresh) {
        this.isOverlappingAboveThresh = isOverlappingAboveThresh;
    }

    public double getPercentOverlapping() {
        return percentOverlapping;
    }

    public void setPercentOverlapping(double percentOverlapping) {
        this.percentOverlapping = percentOverlapping;
    }

    public double getATContent() {
        IntPredicate isA = arg -> arg == 'A';
        IntPredicate isT = arg -> arg == 'T';
        long numA = this.getSequence().chars().filter(isA).count();
        long numT = this.getSequence().chars().filter(isT).count();
        double pcAT = (double)(numA+numT)/(double)this.getLength();
        return pcAT;
    }
    
}
