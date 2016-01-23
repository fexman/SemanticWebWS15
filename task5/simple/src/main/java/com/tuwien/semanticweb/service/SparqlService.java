package com.tuwien.semanticweb.service;

import org.apache.jena.query.*;
import org.apache.jena.sparql.engine.http.QueryExceptionHTTP;

import java.util.Scanner;

public class SparqlService {

    private static String sparqlService = "http://localhost:3030/SemanticWebWS15_G05/sparql";
    private static Scanner scanner = new Scanner(System.in);

    public static void listCoursesOfTeacher() {
        System.out.print("Enter name of teacher you want the courses of: ");
        String name = "\"" + scanner.nextLine() + "\"";

        String sparqlQuery = "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" +
                "prefix g05: <http://ifs.tuwien.ac.at/tulid/group05#>\n" +
                "\n" +
                "SELECT ?courseName\n" +
                "WHERE {\n" +
                "  ?person foaf:name " + name + ".\n" +
                "  ?course g05:taughtBy ?person.\n" +
                "  ?course g05:courseTitle ?courseName\n" +
                "}";

        QueryExecution qe = null;

        try {
            Query query = QueryFactory.create(sparqlQuery);

            qe = QueryExecutionFactory.sparqlService(sparqlService, query);

            ResultSet results = qe.execSelect();

            System.out.println("Get courses that are taught by " + name + ":");


            while (results.hasNext()) {

                QuerySolution solution = results.nextSolution();
                System.out.println("    " + solution.get("?courseName"));
            }

        } catch (QueryExceptionHTTP e) {
            System.out.println("Query Exception occured! Cause: " + e.getCause());
        } finally {
            if (qe != null)
                qe.close();
        }
    }

    public static void listEmailByMember() {
        System.out.print("Enter name of member you want the email of: ");
        String name = "\"" + scanner.nextLine() + "\"";

        String sparqlQuery = "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" +
                "prefix g05: <http://ifs.tuwien.ac.at/tulid/group05#>\n" +
                "\n" +
                "SELECT ?email\n" +
                "WHERE {\n" +
                "  ?member foaf:name " + name + ".\n" +
                "  ?member foaf:email ?email\n" +
                "}";

        QueryExecution qe = null;

        try {
            Query query = QueryFactory.create(sparqlQuery);

            qe = QueryExecutionFactory.sparqlService(sparqlService, query);

            ResultSet results = qe.execSelect();

            System.out.print("The email of " + name + " is ");


            while (results.hasNext()) {

                QuerySolution solution = results.nextSolution();
                System.out.println(solution.get("?email"));
            }

        } catch (QueryExceptionHTTP e) {
            System.out.println("Query Exception occured! Cause: " + e.getCause());
        } finally {
            if (qe != null)
                qe.close();
        }
    }

    public static void listFaculties() {

        String sparqlQuery = "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" +
                "prefix g05: <http://ifs.tuwien.ac.at/tulid/group05#>\n" +
                "\n" +
                "SELECT ?facultyName\n" +
                "WHERE {\n" +
                "  ?faculty a g05:Faculty.\n" +
                "  ?faculty foaf:name ?facultyName.\n" +
                "}";

        QueryExecution qe = null;
        try {
            Query query = QueryFactory.create(sparqlQuery);

            qe = QueryExecutionFactory.sparqlService(sparqlService, query);

            ResultSet results = qe.execSelect();

            System.out.println("Name of available faculties: ");


            while (results.hasNext()) {

                QuerySolution solution = results.nextSolution();
                System.out.println("    " + solution.get("?facultyName"));
            }

        } catch (QueryExceptionHTTP e) {
            System.out.println("Query Exception occured! Cause: " + e.getCause());
        } finally {
            if (qe != null)
                qe.close();
        }
    }


    public static void listInstitutesByFaculty() {

        System.out.print("Enter name of faculty you want the institutes of: ");
        String facultyName = "\"" + scanner.nextLine() + "\"";


        String sparqlQuery = "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" +
                "        PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "        prefix g05: <http://ifs.tuwien.ac.at/tulid/group05#>\n" +
                "\n" +
                "        SELECT ?instituteName\n" +
                "        WHERE {\n" +
                "            ?institute a g05:Institute.\n" +
                "                    ?faculty foaf:name "  + facultyName + ".\n" +
                "                    ?institute rdfs:member ?faculty.\n" +
                "                    ?institute foaf:name ?instituteName.\n" +
                "        }";


        QueryExecution qe = null;

        try {
            Query query = QueryFactory.create(sparqlQuery);

            qe = QueryExecutionFactory.sparqlService(sparqlService, query);

            ResultSet results = qe.execSelect();

            System.out.println("Name of institutes for " + facultyName + " :");


            while (results.hasNext()) {

                QuerySolution solution = results.nextSolution();
                System.out.println("    " + solution.get("?instituteName"));
            }

        } catch (QueryExceptionHTTP e) {
            System.out.println("Query Exception occured! Cause: " + e.getCause());
        } catch(QueryParseException e) {
            System.out.println("Failed parsing Query!" + e.getCause());
        } finally {
            if (qe != null)
                qe.close();
        }
    }

    public static void listMembersByInstitute() {

        System.out.print("Enter name of institute you want the names of the members of: ");
        String instituteName = "\"" + scanner.nextLine() + "\"";


        String sparqlQuery = "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "prefix g05: <http://ifs.tuwien.ac.at/tulid/group05#>\n" +
                "\n" +
                "SELECT ?memberName\n" +
                "WHERE {\n" +
                "  ?institute a g05:Institute.\n" +
                "  ?institute foaf:name " + instituteName + ".\n" +
                "  ?member rdfs:member ?institute.\n" +
                "  ?member foaf:name ?memberName\n" +
                "}";


        QueryExecution qe = null;

        try {
            Query query = QueryFactory.create(sparqlQuery);

            qe = QueryExecutionFactory.sparqlService(sparqlService, query);

            ResultSet results = qe.execSelect();

            System.out.println("Name of members of institute " + instituteName + " :");


            while (results.hasNext()) {

                QuerySolution solution = results.nextSolution();
                System.out.println("    " + solution.get("?memberName"));
            }

        } catch (QueryExceptionHTTP e) {
            System.out.println("Query Exception occured! Cause: " + e.getCause());
        } catch(QueryParseException e) {
            System.out.println("Failed parsing Query!" + e.getCause());
        } finally {
            if (qe != null)
                qe.close();
        }
    }

    public static void listCoursesBySemester() {

        System.out.print("List courses by which semester (e.g. 2015W): ");
        String semester = scanner.nextLine();


        String sparqlQuery = "prefix g05: <http://ifs.tuwien.ac.at/tulid/group05#>\n" +
                "\n" +
                "SELECT ?courseTitle\n" +
                "WHERE {\n" +
                "  ?course g05:inSemester <http://localhost:3333/semester/" + semester + ">.\n" +
                "  ?course g05:courseTitle ?courseTitle\n" +
                "}";


        QueryExecution qe = null;

        try {
            Query query = QueryFactory.create(sparqlQuery);

            qe = QueryExecutionFactory.sparqlService(sparqlService, query);

            ResultSet results = qe.execSelect();

            System.out.println("List Courses by semester " + semester + " :");


            while (results.hasNext()) {

                QuerySolution solution = results.nextSolution();
                System.out.println("    " + solution.get("?courseTitle"));
            }

        } catch (QueryExceptionHTTP e) {
            System.out.println("Query Exception occured! Cause: " + e.getCause());
        } catch(QueryParseException e) {
            System.out.println("Failed parsing Query!" + e.getCause());
        } finally {
            if (qe != null)
                qe.close();
        }
    }

    public static void listCourseDetails() {
        System.out.print("Enter course title to list the details of: ");
        String courseTitle = "\"" + scanner.nextLine() + "\"";


        String sparqlQuery = "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" +
                "prefix g05: <http://ifs.tuwien.ac.at/tulid/group05#>\n" +
                "\n" +
                "SELECT ?inSemester ?courseDescription ?courseEcts ?name\n" +
                "WHERE {\n" +
                "  ?course g05:courseTitle " + courseTitle + ".\n" +
                "  ?course g05:courseDescription ?courseDescription.\n" +
                "  ?course g05:courseEcts ?courseEcts.\n" +
                "  ?course g05:taughtBy ?person.\n" +
                "  ?course g05:inSemester ?inSemester.\n" +
                "  ?person foaf:name ?name\n" +
                "} LIMIT 1";


        QueryExecution qe = null;

        try {
            Query query = QueryFactory.create(sparqlQuery);

            qe = QueryExecutionFactory.sparqlService(sparqlService, query);

            ResultSet results = qe.execSelect();

            System.out.println("List details of " + courseTitle + " :");


            while (results.hasNext()) {

                QuerySolution solution = results.nextSolution();
                System.out.println("    Description: " + solution.get("?courseDescription"));
                System.out.println("    Ects: " + solution.get("?courseEcts"));
                String semester = solution.get("?inSemester").toString();
                semester = semester.substring(semester.length() - 5, semester.length());
                System.out.println("    Semester: " + semester);
                System.out.println("    Taught by: " + solution.get("?name"));
            }

        } catch (QueryExceptionHTTP e) {
            System.out.println("Query Exception occured! Cause: " + e.getCause());
        } catch(QueryParseException e) {
            System.out.println("Failed parsing Query!" + e.getCause());
        } finally {
            if (qe != null)
                qe.close();
        }
    }
}
