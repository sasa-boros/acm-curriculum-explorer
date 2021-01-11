# ACM Computer Science Curriculum Explorer

This application allows to explore the world of Computer Science through ACM Computer Science curriculum. The main goal is to provide students as well as teachers with a platform that answers questions on how they can reach their Computer Science education goals.

## Starting the application

In the future it will be started as a Docker container. At the moment, server is started as a standard Spring Boot application. The client is started on port 4200 using the `ng serve --poll=2000` command. The applicaton requires a running PSQL instance (port  5432) with database called 'metahanddb' created which contains the public schema.

## Architecture Overview

The application is built using the tools from the domain of Semantic Web which allows us to provide answers to the complex questions that might occur in this world. We have created ACM Computer Science curriculum ontology using OWL (Web Ontology Language) which we save to the Apache Jena's SDB. The answers are provided by executing SPARQL queries on the mentioned ontology. The ontology is populated with individuals that we have scrapped from the public document a ["Computer Science Curricular Guidance for Associate-Degree Transfer Programs"](https://dl.acm.org/doi/pdf/10.1145/3108241), issued by CCECC, which describes the curriculum deeply.

The application is created as client-server based architecture. The client (UI) allows you to execute SPARQL queries and look at the response. The SDB is connected to PostgreSQL database which hosts the ontology and individuals.

## Ontology

The following image showcases the ontology:

![Failed to load the image](https://github.com/ndakic/semantic-web/blob/main/metahand-server/src/main/resources/acm-curriculum-ontology.png)

You can also see the ontology in RDF syntax [here](https://github.com/ndakic/semantic-web/blob/main/metahand-server/src/main/resources/sec_ontology.owl).

## Usage Examples

*Question 1*: Which courses do I need to take to gain knowledge from knowledge area "Algorithms and Complexity"?

```
PREFIX acm: <http://www.semanticweb.org/sasaboros/ontologies/2020/11/sec_ontology#>

SELECT DISTINCT ?name
WHERE {
    ?ka acm:name "Algorithms and Complexity" .
    ?ka acm:consistsOf ?ku .
    ?ku acm:includes ?lo .
    ?course acm:teaches ?lo .
    ?course acm:name ?name .
}
```
*Answer*: "Introduction to Software Engineering" and "Semantic Web".

*Question 2*: Which learning outcomes will I gain if I use video materials from course "Database Systems"?

```
PREFIX acm: <http://www.semanticweb.org/sasaboros/ontologies/2020/11/sec_ontology#>

SELECT DISTINCT ?lod
WHERE {
    ?course acm:name "Database Systems" .
    ?course acm:isTaughtUsing ?lr .
    ?lr acm:format ?lrf .
    FILTER (?lrf = 'wav')
    ?lo acm:obtainedBy ?lr .
    ?lo acm:description ?lod
}
```
*Answer*: "Describe security as a continuous process of tradeoffs, balancing between protection mechanisms and availability", "Exemplify different types of simulations", ....


