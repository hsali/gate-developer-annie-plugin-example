import gate.*;
import gate.creole.ANNIEConstants;
import gate.creole.Plugin;
import gate.creole.ResourceReference;
import gate.creole.SerialAnalyserController;
import gate.util.persistence.PersistenceManager;
import java.io.File;
import java.net.URL;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        Gate.setGateHome(new File("C:/Program Files/GATE_Developer_8.6.1"));
        Gate.init();
        // load the ANNIE plugin
//        Plugin anniePlugin = new Plugin.Maven("uk.ac.gate.plugins", "annie", gate.Main.version);
        Plugin anniePlugin = new Plugin.Maven("uk.ac.gate.plugins", "annie", "8.6");
        Gate.getCreoleRegister().registerPlugin(anniePlugin);

         URL annieFile = new ResourceReference(anniePlugin, "resources/" + ANNIEConstants.DEFAULT_FILE).toURL();

        // load ANNIE application from inside the plugin
//        SerialAnalyserController controller = (SerialAnalyserController) PersistenceManager.loadObjectFromUrl();

//        LanguageAnalyser controller = (LanguageAnalyser) PersistenceManager.loadObjectFromFile(annieFile);
        LanguageAnalyser controller = (LanguageAnalyser) PersistenceManager.loadObjectFromUrl(annieFile);

        Corpus corpus = Factory.newCorpus("corpus");
        Document document = Factory.newDocument("Michael Jordan is a professor at the University of California, Berkeley.");
        corpus.add(document); controller.setCorpus(corpus);
        controller.execute();

//        document.getAnnotations().get(new HashSet<String>(Arrays.asList("Person", "Organization", "Location")))
//                .forEach(a -> System.err.format("%s - \"%s\" [%d to %d]\n",
//                        a.getType(), Utils.stringFor(document, a),
//                        a.getStartNode().getOffset(), a.getEndNode().getOffset()));


        for(Annotation obj : document.getAnnotations().get(new HashSet<String>(Arrays.asList("Person", "Organization", "Location")))){
            System.out.print("type : " + obj.getType());
            System.out.print("\t data : " + Utils.stringFor(document, obj));
            System.out.print("\t start : " + obj.getStartNode().getOffset());
            System.out.println("\t end : " + obj.getEndNode().getOffset());
//            System.err.format("%s - \"%s\" [%d to %d]\n"
        }

        //Don't forget to release GATE resources 
        Factory.deleteResource(document); Factory.deleteResource(corpus); Factory.deleteResource(controller);
    }
}