
package a_snf_2;

public class BedRecord {
          
    private String chrom;
    private int start;
    private int end;
    private String name;
    private String score;
    private String strand;
    private String signalValue;
    private String pValue;
    private String qValue;
    private Integer peakSummitOffset;
    private String originalRecordLine;

    private Integer peakSummit;
    private boolean pointInIntergenic = false;
    private boolean pointInIntron = false;
    private boolean pointInExon = false;
    private boolean pointInPromoter = false;
    private boolean pointInTSS = false;
    private boolean pointInGene = false;
    private boolean pointInUnassigned = false;

    public BedRecord(String chrom, int start, int end, String name, String score, String strand, String signalValue, String pValue, String qValue, Integer peakSummitOffset, String convention, String originalRecordLine){
        this.chrom = chromConventionConsistency(chrom, convention);
        this.start = start;
        this.end =  end;
        this.name = name;
        this.score = score;
        this.strand = strand;
        this.signalValue = signalValue;
        this.pValue = pValue;
        this.qValue = qValue;
        this.peakSummitOffset = peakSummitOffset;
        this.peakSummit = this.start+this.peakSummitOffset;
        this.originalRecordLine = originalRecordLine;
    }

    public BedRecord(String chrom, int start, int end, String name, String score, String strand, String convention, String originalRecordLine){
        this.chrom = chromConventionConsistency(chrom, convention);
        this.start = start;
        this.end =  end;
        this.name = name;
        this.score = score;
        this.strand = strand;
        this.signalValue = ".";
        this.pValue = "-1";
        this.qValue = "-1";
        this.peakSummitOffset = -1;
        this.peakSummit = -1;
        this.originalRecordLine = originalRecordLine;
    }
    
    public BedRecord(String[] tokens, String convention, String originalRecordLine){
        this.chrom = chromConventionConsistency(tokens[0], convention);
        this.start = Integer.parseInt(tokens[1]);
        this.end = Integer.parseInt(tokens[2]);
        this.name = tokens[3];
        this.score = tokens[4];
        this.strand = tokens[5];
        if(tokens.length > 6){
            this.signalValue = tokens[6];
            this.pValue = tokens[7];
            this.qValue = tokens[8];
            this.peakSummitOffset = Integer.parseInt(tokens[9]);
            this.peakSummit = this.start+this.peakSummitOffset;
        }else{
            this.signalValue = ".";
            this.pValue = "-1";
            this.qValue = "-1";
            this.peakSummitOffset = -1;
            this.peakSummit = -1;
        }
        this.originalRecordLine = originalRecordLine;
    }

    public String getChrom() {
        return chrom;
    }

    public void setChrom(String chrom) {
        this.chrom = chrom;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getStrand() {
        return strand;
    }

    public void setStrand(String strand) {
        this.strand = strand;
    }

    public String getSignalValue() {
        return signalValue;
    }

    public void setSignalValue(String signalValue) {
        this.signalValue = signalValue;
    }

    public String getpValue() {
        return pValue;
    }

    public void setpValue(String pValue) {
        this.pValue = pValue;
    }

    public String getqValue() {
        return qValue;
    }

    public void setqValue(String qValue) {
        this.qValue = qValue;
    }

    public Integer getPeakSummitOffset() {
        return peakSummitOffset;
    }

    public void setPeakSummitOffset(Integer peakSummitOffset) {
        this.peakSummitOffset = peakSummitOffset;
    }

    public Integer getPeakSummit() {
        return peakSummit;
    }

    public void setPeakSummit(Integer peakSummit) {
        this.peakSummit = peakSummit;
    }

    public boolean isPointInIntergenic() {
        return pointInIntergenic;
    }

    public void setPointInIntergenic(boolean pointInIntergenic) {
        this.pointInIntergenic = pointInIntergenic;
    }

    public boolean isPointInIntron() {
        return pointInIntron;
    }

    public void setPointInIntron(boolean pointInIntron) {
        this.pointInIntron = pointInIntron;
    }

    public boolean isPointInExon() {
        return pointInExon;
    }

    public void setPointInExon(boolean pointInExon) {
        this.pointInExon = pointInExon;
    }

    public boolean isPointInPromoter() {
        return pointInPromoter;
    }

    public void setPointInPromoter(boolean pointInPromoter) {
        this.pointInPromoter = pointInPromoter;
    }

    public boolean isPointInTSS() {
        return pointInTSS;
    }

    public void setPointInTSS(boolean pointInTSS) {
        this.pointInTSS = pointInTSS;
    }

    public boolean isPointInUnassigned() {
        return pointInUnassigned;
    }

    public void setPointInUnassigned(boolean pointInUnassigned) {
        this.pointInUnassigned = pointInUnassigned;
    }

    public boolean isPointInGene() {
        return pointInGene;
    }

    public void setPointInGene(boolean pointInGene) {
        this.pointInGene = pointInGene;
    }

    public String getOriginalRecordLine() {
        return originalRecordLine;
    }
    
    public boolean isPointWithin(int point){
        return point >= this.getStart() && point <= this.getEnd();
    }
    
    public static final String chromConventionConsistency(String chrom, String convention){
        String replacement = chrom;
        if(convention.equalsIgnoreCase("UCSC")){
            if(replacement.equalsIgnoreCase("MT")){
                replacement = "M";
            }
            if(!replacement.startsWith("chr")){
                replacement = "chr"+replacement;
            }
        } else {
            if(replacement.startsWith("chr")){
                replacement = replacement.replace("chr", "");
            }
            if(replacement.equalsIgnoreCase("M")){
                replacement = "MT";
            }
        }
        return replacement;    
    }

    @Override
    public String toString() {
        return this.getChrom() + "\t" + this.getStart() + "\t" + this.getEnd() + "\t" + this.getName()  + "\t" + this.getScore() + "\t" + 
                this.getStrand() + "\t" + this.getSignalValue() + "\t" + this.getpValue() + "\t" + this.getqValue() + "\t" + 
                this.getPeakSummitOffset() + "\t" + this.getPeakSummit() + "\t" + this.isPointInIntergenic() + "\t" + this.isPointInIntron() + "\t" + 
                this.isPointInExon() + "\t" + this.isPointInPromoter() + "\t" + this.isPointInTSS()+ "\t" + this.isPointInGene();
    }
    
    public String getRecord(){
        return this.chrom +"\t"+ this.start +"\t"+  this.end +"\t"+ this.name +"\t"+ this.score +"\t"+ this.strand;
    }
}
