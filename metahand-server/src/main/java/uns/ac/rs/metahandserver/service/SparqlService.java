package uns.ac.rs.metahandserver.service;

import lombok.RequiredArgsConstructor;
import org.apache.jena.query.*;
import org.apache.jena.sdb.SDBFactory;
import org.apache.jena.sdb.Store;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SparqlService {

    public String executeStatement(String statement) {
        Store store = null;
        try {
            store = SDBFactory.connectStore(new ClassPathResource("sdb.ttl").getFile().getAbsolutePath());
        } catch(Exception e) {
            e.printStackTrace();
            return "Failed to open the store: " + e.getMessage();
        }
        Dataset ds = SDBFactory.connectDataset(store);
        try(QueryExecution qe = QueryExecutionFactory.create(statement, ds)) {
            ResultSet rs = qe.execSelect() ;
            return ResultSetFormatter.asText(rs) ;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error executing sparql query: " + e.getMessage();
        } finally {
            store.getConnection().close();
            store.close();
        }
    }
}
