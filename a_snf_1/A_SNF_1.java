package a_snf_1;

import java.io.File;

public class A_SNF_1 {

    /** Tool version.**/
    public static final String VERSION = "1.3.1";
    /** Tools installation. **/
    public Tools tools;

    public void start(String[] args){
        this.installation(); 
        this.analysis(args);
    }
    
    private void installation() {
        this.tools = new Tools();
        this.tools.setUpUserProperties();
        this.tools.installCondaEnvironmentAsRequired();
        this.tools.installCondaTools();
        this.tools.installPatmanAsRequested();
        this.tools.installComplete();
    }
    
    private void analysis(String[] args){
        String runID = args[0];
        tools.setUpRunDir(runID);
        File irs = tools.runGRF(new File(args[2]), Float.parseFloat(args[6]));
        tools.runFastQCPre(runID, args[1]);
        File uncompressedSingleFastq = tools.runFastqsToSingleUncompressedFastq(args[1]);
        File trimmedFastq = tools.runTrimAdapters(uncompressedSingleFastq, Boolean.parseBoolean(args[4]), null);
        File fasta = tools.runFastqToFasta(trimmedFastq);
        File fastaNR = new FastaRedundantToNR().makeNR(fasta);
        File alignments = tools.runPatman(fastaNR, irs);
        tools.runBuildBlastGeneDatabase(new File(args[3]));
        tools.runBlastn();
    }
    
    public static void main(String[] args) {
        System.out.println("A SNF version " + A_SNF_1.VERSION);
        A_SNF_1 a = new A_SNF_1();
        if(a.validateParameters(args)) {
            a.start(args);
        } else {
            System.out.println("Usage: java -jar A_SNF.jar <ID> <FASTQ | FASTQ,FASTQ...> <REFERENCE-FASTA> <hd=true/false> <adapter|-> <ratio>");
            System.out.println("ID = a unique identifier for this analysis/run (mandatory).");
            System.out.println("FASTQ = small RNAs in fastq format, or a comer separated list of small RNA in fastq format (mandatory).");
            System.out.println("REFERENCE-FASTA = genome in fasta format (mandatory).");
            System.out.println("ANNOTATION-FASTA = genes in fasta format (mandatory).");
            System.out.println("boolean = false if sRNAs were not obtained using High Definition Adapters (mandatory).");
            System.out.println("Adapter = adapter sequence to be trimmed (first 9 bases Mandatory if provided) (mandatory). If no adapter is supplied or - provided, trim_galore auto detect adapter will be used.");
            System.out.println("float = Maximum length ratio of spacer/total sequence (mandatory)\n");
        }
    }
    
    public boolean validateParameters(String[] args){
        if(args == null || args.length < 7){
            System.err.println("Error: please check the number of arguments provided to the software.");
            return false;
        }
        String[] fastqs = args[1].split(",");
        for (String fastq : fastqs) {
            File f = new File(fastq);
            if(!f.exists() || !f.canRead()){
                System.err.println("Error: can not read file "+f.getAbsolutePath());
                return false;
            }
        }
        File ref = new File(args[2]);
        if(!ref.exists() || !ref.canRead()){
            System.err.println("Error:  can not read reference file "+ref.getAbsolutePath());
            return false;
        }
        File anno = new File(args[3]);
        if(!ref.exists() || !ref.canRead()){
            System.err.println("Error:  can not read annotation file "+anno.getAbsolutePath());
            return false;
        }
        if(args[5].length() < 9){
            System.err.println("Parameters info: adapter provided is less than 9 bases so using TrimGalore! auto detection method.");
        }
        Float f = Float.parseFloat(args[6]);
        if(f.isNaN()){
            System.err.println("Error: Argument "+args[6]+" is NaN.");
            return false;
        }
        return true;
    }
    
}
