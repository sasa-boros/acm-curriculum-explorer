package uns.ac.rs.metahandserver;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.sdb.Store;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

import static uns.ac.rs.metahandserver.ontology.AcmOntologyCreator.connectToStore;
import static uns.ac.rs.metahandserver.ontology.AcmOntologyCreator.createOntology;
import static uns.ac.rs.metahandserver.ontology.AcmOntologyPopulator.populateOntology;

@SpringBootApplication
public class MetahandServerApplication {

    private static final Boolean populateOntology = true;

    public static void main(String[] args) {
        SpringApplication.run(MetahandServerApplication.class, args);

        Store store = null;
        try {
            store = connectToStore();
            OntModel om = createOntology(store);
            if (populateOntology) {
                populateOntology(om);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (store != null) {
                store.close();
            }
        }
    }
}
