
package a_snf_2;

public class PatmanAlignment {
    
    /** The alignment record in file in patman format. **/
    private final String patmanAlignmentRecord;
    /** Reference identifier as provided in the patman file. **/
    private final String referenceIdentifier;
    /** The small RNA sequence. **/
    private final String srnaSequence;
    /** The raw abundance of the small RNA. **/
    private final int srnaAbundance;
    /** Start coordinated of the first base on the reference sequence. **/
    private final int start;
    /** End coordinate of the last base on the reference sequence. **/
    private final int end;
    /** The strand the small RNA mapped to. **/
    private final String strand;
    /** The weighted abundance of sRNA = abundance/number of alignments. **/
    private double weightedAbundance;
    
    public PatmanAlignment(String record){
        this.patmanAlignmentRecord = record;
        String[] toks = this.patmanAlignmentRecord.split("\t");
        this.referenceIdentifier = toks[0];
        this.srnaSequence = toks[1].split("::")[0];
        this.srnaAbundance = Integer.parseInt(toks[1].split("::")[1]);
        this.start = Integer.parseInt(toks[2]);
        this.end = Integer.parseInt(toks[3]);
        this.strand = toks[4];
        //NOTE: final value in bed6 record is not assigned as not used.
    }

    public double getWeightedAbundance() {
        return weightedAbundance;
    }

    public void setWeightedAbundance(double weightedAbundance) {
        this.weightedAbundance = weightedAbundance;
    }

    public String getPatmanRecord() {
        return patmanAlignmentRecord;
    }

    public String getReferenceIdentifier() {
        return referenceIdentifier;
    }

    public String getSrnaSequence() {
        return srnaSequence;
    }

    public int getSrnaAbundance() {
        return srnaAbundance;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public String getStrand() {
        return strand;
    }
    
    public int getSrnaLength(){
        return this.srnaSequence.length();
    }
    
}
