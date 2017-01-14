package fr.ensimag.deca;

import fr.ensimag.deca.context.EnvironmentType;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tree.AbstractProgram;
import java.io.File;
import java.io.PrintStream;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 * Main class for the command-line Deca compiler.
 *
 * @author gl17
 * @date 01/01/2017
 */
public class DecacMain {
    private static Logger LOG = Logger.getLogger(DecacMain.class);
    
    public static void main(String[] args)  {
        // example log4j message.
        LOG.info("Decac compiler started");
        boolean error = false;
        final CompilerOptions options = new CompilerOptions();
        try {
            options.parseArgs(args);
        } catch (CLIException e) {
            System.err.println("Error during option parsing:\n"
                    + e.getMessage());
            options.displayUsage();
            System.exit(1);
        }
        if (options.getPrintBanner()) {
          System.out.println("equipegl17");
        }
        else if (options.getSourceFiles().isEmpty()) {
           throw new UnsupportedOperationException("decac without argument not yet implemented");
        }
        else if (options.getParallel()) {
            // A FAIRE : instancier DecacCompiler pour chaque fichier à
            // compiler, et lancer l'exécution des méthodes compile() de chaque
            // instance en parallèle. Il est conseillé d'utiliser
            // java.util.concurrent de la bibliothèque standard Java.
            throw new UnsupportedOperationException("Parallel build not yet implemented");
        }else {
            for (File source : options.getSourceFiles()) {
                DecacCompiler compiler = new DecacCompiler(options, source);
                /*  if (compiler.getCompilerOptions().getParallel()){
                    PrintStream err=""; // a verifier
                    AbstractProgram prog=compiler.doLexingAndParsing(source,err);
                    prog.decompile()
                }*/
                // a completer pour decac -p 
                if( options.getParse()){
                    PrintStream err=System.err;
                    try {
                        AbstractProgram prog=compiler.doLexingAndParsing(source.toString(), err);
                        //IndentPrintStream s=new IndentPrintStream(err);
                        if (prog == null) {
                            LOG.info("Parsing failed");
                            error=true;
                        }else{
                            String s=prog.decompile();
                            System.out.println(s);
                        }
                    } catch (DecacFatalError ex) {
                        java.util.logging.Logger.getLogger(DecacMain.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (DecacInternalError ex) {
                        java.util.logging.Logger.getLogger(DecacMain.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }else if (compiler.compile()) {
                    error = true;
                }
            }
        }
        System.exit(error ? 1 : 0);
    }
}
