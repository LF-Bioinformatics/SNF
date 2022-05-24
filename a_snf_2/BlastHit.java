
package a_snf_2;

public class BlastHit {
    
    private String IR_ID;
    private String hitID;
    private String hitString;
    
    private String qseqid; 	 //query (e.g., IR) sequence id
    private String sseqid; 	 //subject (e.g., referene genes) sequence id
    private String pident; 	 //percentage of identical matches
    private String length; 	 //alignment length (sequence overlap)
    private String mismatch; 	 //number of mismatches
    private String gapopen; 	 //number of gap openings
    private String qstart; 	 //start of alignment in query
    private String qend; 	 //end of alignment in query
    private String sstart; 	 //start of alignment in subject
    private String send; 	 //end of alignment in subject
    private String evalue; 	 //expect value
    private String bitscore; 	 //bit score
    private double overlap;      //The amount the amount of the subject covered by the query.
    
    public BlastHit(String hitString){
        this.processHitString(hitString);
    }
    
    public void calculateOverlap(int geneLength) {
        int mappedLength = Math.abs(Integer.parseInt(this.getSstart()) - Integer.parseInt(this.getSend()));
        this.overlap = (double)mappedLength/(double)geneLength; 
    }
    
    public double getOverlap(){
        return this.overlap;
    }
    
    private void processHitString(String hitString){
        this.hitString = hitString;
        String[] toks = hitString.split("\t");
        qseqid      = toks[0];
        sseqid      = toks[1];
        pident      = toks[2];
        length      = toks[3];
        mismatch    = toks[4];
        gapopen     = toks[5];
        qstart      = toks[6];
        qend        = toks[7];
        sstart      = toks[8];
        send        = toks[9];
        evalue      = toks[10];
        bitscore    = toks[11];
        this.IR_ID = toks[0].split(":")[0]+":"+toks[0].split(":")[1]+":"+toks[0].split(":")[2];
        this.hitID = qseqid+"_"+sseqid;
    }

    public String getIR_ID() {
        return IR_ID;
    }

    public String getHitString() {
        return hitString;
    }

    public String getQseqid() {
        return qseqid;
    }

    public String getSseqid() {
        return sseqid;
    }

    public String getPident() {
        return pident;
    }

    public String getLength() {
        return length;
    }

    public String getMismatch() {
        return mismatch;
    }

    public String getGapopen() {
        return gapopen;
    }

    public String getQstart() {
        return qstart;
    }

    public String getQend() {
        return qend;
    }

    public String getSstart() {
        return sstart;
    }

    public String getSend() {
        return send;
    }

    public String getEvalue() {
        return evalue;
    }

    public String getBitscore() {
        return bitscore;
    }

    public String getHitID() {
        return hitID;
    }
    
    public static String getHitStringForPrintingHeader() {
        StringBuilder b = new StringBuilder();
        b.append("IR ID"); b.append("\t");
        b.append("Gene ID"); b.append("\t");
        b.append("Evalue"); b.append("\t");
        b.append("Overlap"); b.append("\t");
        return b.toString();
                
    }
    
    public String getHitStringForPrinting() {
        StringBuilder b = new StringBuilder();
        b.append(this.IR_ID); b.append("\t");
        b.append(this.hitID); b.append("\t");
        b.append(this.getEvalue()); b.append("\t");
        b.append(this.getOverlap()); b.append("\t");
        return b.toString();
                
    }
 
 
}
