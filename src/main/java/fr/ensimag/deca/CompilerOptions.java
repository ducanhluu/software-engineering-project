package fr.ensimag.deca;

import static fr.ensimag.deca.codegen.MemoryManagement.setRMAX;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * User-specified options influencing the compilation.
 *
 * @author gl17
 * @date 01/01/2017
 */
public class CompilerOptions {
    public static final int QUIET = 0;
    public static final int INFO  = 1;
    public static final int DEBUG = 2;
    public static final int TRACE = 3;
    public int getDebug() {
        return debug;
    }

    public boolean getParallel() {
        return parallel;
    }

    public boolean getPrintBanner() {
        return printBanner;
    }
    
    public List<File> getSourceFiles() {
        return Collections.unmodifiableList(sourceFiles);
    }
    private boolean parse=false;
    public boolean getParse() {
        return parse;
    }
    private boolean verif=false;
    public boolean getVerif(){
        return verif;
    }
    private int debug = 0;
    private boolean parallel = false;
    private boolean printBanner = false;
    private List<File> sourceFiles = new ArrayList<File>();
    
    
    public void parseArgs(String[] args) throws CLIException {
        // A FAIRE : parcourir args pour positionner les options correctement.
        Logger logger = Logger.getRootLogger();
        // map command-line debug option to log4j's level.
        switch (getDebug()) {
        case QUIET: break; // keep default
        case INFO:
            logger.setLevel(Level.INFO); break;
        case DEBUG:
            logger.setLevel(Level.DEBUG); break;
        case TRACE:
            logger.setLevel(Level.TRACE); break;
        default:
            logger.setLevel(Level.ALL); break;
        }
        logger.info("Application-wide trace level set to " + logger.getLevel());

        boolean assertsEnabled = false;
        assert assertsEnabled = true; // Intentional side effect!!!
        if (assertsEnabled) {
            logger.info("Java assertions enabled");
        } else {
            logger.info("Java assertions disabled");
        }
	for (String s:args){
            switch (s){
                case  "-b":
                   this.printBanner=true;
                case "-p":
                    if ( this.verif){
                        throw new IllegalArgumentException("you can't use -p and -v at the same time");
                    }
                    this.parse=true;
                    break; 
                case "-v":
                     if (this.parse){
                        throw new IllegalArgumentException("you can't use -p and -v at the same time");
                    }
                    this.verif=true;
                  break;
                case "-n":
                  break;
                case "-r X":
                    //change valeur de RMAX dans MemoryManagement
                    //setRMAX(X - 1);
                    break;
                case "-d":
                    break;
                case "-P":
                    break;
                default:
		    File file=new File(s);
                    if(!this.sourceFiles.contains(file)){
			this.sourceFiles.add(file);
		    }
                    break;
            }
	}
    }

    protected void displayUsage() {
        throw new UnsupportedOperationException("not yet implemented");
    }
}
