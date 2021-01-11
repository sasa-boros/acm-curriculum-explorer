package uns.ac.rs.metahandserver.ontology;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.sdb.SDBFactory;
import org.apache.jena.sdb.Store;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.IOException;

public class AcmOntologyCreator {

    private static final String SDB_CONFIGURATION_FILE = "sdb.ttl";
    private static final String ACM_ONTOLOGY_FILE = "sec_ontology.owl";

    public static Store connectToStore() throws IOException {
        Store store = SDBFactory.connectStore(new ClassPathResource(SDB_CONFIGURATION_FILE).getFile().getAbsolutePath());
        store.getTableFormatter().create();

        return store;
    }

    public static OntModel createOntology(Store store) throws IOException {
        Model model = SDBFactory.connectDefaultModel(store);
        model.read(new FileInputStream(new ClassPathResource(ACM_ONTOLOGY_FILE).getFile()), null);
        OntModel om = ModelFactory.createOntologyModel( OntModelSpec.OWL_DL_MEM, model );
        om.setStrictMode(false);

        return om;
    }
}
