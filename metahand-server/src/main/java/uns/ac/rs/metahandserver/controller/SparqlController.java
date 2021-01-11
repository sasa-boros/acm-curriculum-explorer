package uns.ac.rs.metahandserver.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.jena.base.Sys;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uns.ac.rs.metahandserver.service.SparqlService;


@RestController
@RequestMapping("/sparql")
@RequiredArgsConstructor
public class SparqlController {

    @NonNull
    private SparqlService sparqlService;

    @RequestMapping(value = "/execute-statement", method = RequestMethod.POST)
    public ResponseEntity<String> executeStatement(@RequestBody String statement) {
        return ResponseEntity.ok(sparqlService.executeStatement(statement));
    }

}
