
package a_snf_1;

import java.io.File;

public class SmallRNAs {
    
    /** Raw fastq file. **/
    private final File fastq;
    /** fasta file converted from fastq. **/
    private File fasta;
    /** fasta file with adapters removed. **/
    private File trimmedFasta;
    /** Alignments reported by patman. **/
    private File patmanAlignments;
    
    public SmallRNAs(String path){
        this.fastq = new File(path);
    }

    public File getFastq() {
        return fastq;
    }

    public File getFasta() {
        return fasta;
    }

    public void setFasta(File fasta) {
        this.fasta = fasta;
    }

    public File getTrimmedFasta() {
        return trimmedFasta;
    }

    public void setTrimmedFasta(File trimmedFasta) {
        this.trimmedFasta = trimmedFasta;
    }

    public File getPatmanAlignments() {
        return patmanAlignments;
    }

    public void setPatmanAlignments(File patmanAlignments) {
        this.patmanAlignments = patmanAlignments;
    }
  
}
