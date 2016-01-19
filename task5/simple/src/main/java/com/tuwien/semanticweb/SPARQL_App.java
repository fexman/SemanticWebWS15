package com.tuwien.semanticweb;

import org.apache.jena.query.*;

public class SPARQL_App {

    private static String sparqlService = "http://localhost:3030/SemanticWebWS15_G05/sparql";

    public static void main( String[] args ) {

        String name = "\"Rauber Andreas\"";

        String sparqlQuery =
                "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "prefix owl: <http://www.w3.org/2002/07/owl#>\n" +
                "prefix g05: <http://ifs.tuwien.ac.at/tulid/group05#>\n" +
                "\n" +
                "SELECT ?courseName ?course\n" +
                "WHERE {\n" +
                "  ?person foaf:name " + name + ".\n" +
                "  ?course g05:taughtBy ?person.\n" +
                "  ?course g05:courseTitle ?courseName\n" +
                "}";
        
        Query query = QueryFactory.create(sparqlQuery);

        QueryExecution qe = QueryExecutionFactory.sparqlService(sparqlService, query);

        try {
            ResultSet results = qe.execSelect();

            System.out.println("Get courses that are taught by " + name + ":");

            while (results.hasNext()) {

                QuerySolution solution = results.nextSolution();
                System.out.println("    " + solution.get("?courseName"));
            }

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            qe.close();
        }
    }
}
