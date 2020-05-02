import gate.*;
import gate.creole.ANNIEConstants;
import gate.creole.Plugin;
import gate.creole.ResourceReference;
import gate.util.persistence.PersistenceManager;

import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;

public class Main {

    public static void main(String[] args) throws Exception {
        Gate.init();
        Plugin anniePlugin = new Plugin.Maven("uk.ac.gate.plugins", "annie", "8.6");
        Gate.getCreoleRegister().registerPlugin(anniePlugin);
        URL annieFile = new ResourceReference(anniePlugin, "resources/" + ANNIEConstants.DEFAULT_FILE).toURL();

        LanguageAnalyser controller = (LanguageAnalyser) PersistenceManager.loadObjectFromUrl(annieFile);

        Corpus corpus = Factory.newCorpus("corpus");
        Document document = Factory.newDocument("Michael Jordan is a professor at the University of California, Berkeley.");
        corpus.add(document);
        controller.setCorpus(corpus);
        controller.execute();


        for (Annotation obj : document.getAnnotations().get(new HashSet<String>(Arrays.asList("Person", "Organization", "Location")))) {
            System.out.print("type : " + obj.getType());
            System.out.print("\t data : " + Utils.stringFor(document, obj));
            System.out.print("\t start : " + obj.getStartNode().getOffset());
            System.out.println("\t end : " + obj.getEndNode().getOffset());
        }
        Factory.deleteResource(document);
        Factory.deleteResource(corpus);
        Factory.deleteResource(controller);
    }
}