
package a_snf_1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FileUtils {
    
    public void command(String[] cmd, File directory, String prefix, boolean deleteScript) {
        try {
            this.executeCommands(cmd, directory, prefix, deleteScript);
        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void command(String cmd, File directory, String prefix, boolean deleteScript) {
        try {
            this.executeCommand(cmd, directory, prefix, deleteScript);
        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void executeCommand(String command, File directory, String prefix, boolean deleteScript) throws IOException{
        String[] commands = {command};
        File tempScript = generateScript(commands, directory, prefix);
        try {
            ProcessBuilder pb = new ProcessBuilder("bash", tempScript.toString());
            pb.inheritIO();
            Process process = pb.start();
            process.waitFor();
        } catch (InterruptedException | IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(deleteScript){ 
                tempScript.delete();
            }
        }
    }
    
    private void executeCommands(String[] commands, File directory, String prefix, boolean deleteScript) throws IOException {
        File tempScript = generateScript(commands, directory, prefix);
        try {
            ProcessBuilder pb = new ProcessBuilder("bash", tempScript.toString());
            pb.inheritIO();
            Process process = pb.start();
            process.waitFor();
        } catch (InterruptedException | IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(deleteScript){
                tempScript.delete();
            }
        }
    }

    private File generateScript(String[] commands, File directory, String prefix) throws IOException {
        File tempScript = File.createTempFile(prefix+"_A_SNF_temp_",".sh", directory);
        Writer streamWriter = new OutputStreamWriter(new FileOutputStream(tempScript));
        try (PrintWriter printWriter = new PrintWriter(streamWriter)) {
            printWriter.println("#!/bin/bash");
            for(String command : commands){
                printWriter.println(command);
            }
        }
        return tempScript;
    }
    
    public String getOSGetCommand(){
        if(System.getProperty("os.name").contains("Mac")){
            return "curl";
        }else{
            return "wget";
        }
    }
    
    public String getOS(){
        if(System.getProperty("os.name").contains("Mac")){
            return "MAC";
        }else{
            return "LINUX";
        }
    }
 
}
