
package a_snf_2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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
    private int[] coverageList;
    /** blastn hits for this IR. **/
    private final ArrayList<BlastHit> blastHits;
    /** has srna which maps to known miRNA hairpin. **/
    private boolean hasSrnaMappingToMirnaHairpin;
    
    public InvertedRepeat(String id, String header, String sequence){
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
    }
    
    public void generateCoverageTable() {
        int[] ir = new int[this.getLength()];
        Iterator<String> itr = this.sRNAsAlignmentsNrMap.keySet().iterator();
        while(itr.hasNext()){
            String seq = itr.next();
            ArrayList<PatmanAlignment> alignments = this.sRNAsAlignmentsNrMap.get(seq);
            for(PatmanAlignment alignment : alignments){
                int indexStart = alignment.getStart()-1; //-1 because patman alignment is 1 bassed and index is 0 bassed.
                for(int i = indexStart; i < (indexStart+seq.length()); i++){
                    ir[i] += alignment.getSrnaAbundance();
                }
            }   
        }
        this.coverageList = ir;
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
        if(Double.parseDouble(b.getEvalue()) < 0.01){
            this.blastHits.add(b);
        }else{
            System.out.println("Rejected: "+Double.parseDouble(b.getEvalue()) );
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
        s.append("IR Sequence"); s.append("\t");
        if(A_SNF_2.DEV){
            s.append("DEV EXTRA"); s.append("\t");
        }
        s.append("Position (nt):"); s.append("\t");
        for(int i = 0; i < 2000; i++) {
            s.append(i); s.append("\t");
        }
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
        s.append(this.sequence); s.append("\t");
        if(A_SNF_2.DEV){
            s.append(this.devExtra); s.append("\t");
        }
        s.append("Coverage:"); s.append("\t");
        for(int i = 0; i < this.coverageList.length; i++){
            s.append(this.coverageList[i]); s.append("\t");
        }
        s.append(this.isHasSrnaMappingToMirnaHairpin()); s.append("\t");
        return s.toString();
    }

    public void setHasSrnaMappingToMirnaHairpin(boolean hasSrnaMappingToMirnaHairpin) {
        this.hasSrnaMappingToMirnaHairpin = hasSrnaMappingToMirnaHairpin;
    }

    public boolean isHasSrnaMappingToMirnaHairpin() {
        return hasSrnaMappingToMirnaHairpin;
    }

}
