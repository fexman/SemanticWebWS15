package com.tuwien.semanticweb;


import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.VCARD;

import java.io.InputStream;

public class RDF_App {
    public static void main( String[] args ) {
        // some definitions
        String personURI    = "http://somewhere/JohnSmith";
        String familyName = "Smith";
        String givenName = "John";
        String fullName = givenName + " " + familyName;

        // create an empty Model
        Model model = ModelFactory.createDefaultModel();

        // create resource and add properties cascading style
        Resource johnSmith = model.createResource(personURI)
                .addProperty(VCARD.FN, fullName)
                .addProperty(VCARD.N, model.createResource()
                    .addProperty(VCARD.Given, givenName)
                    .addProperty(VCARD.Family, familyName));

        // list the statements in the model
        StmtIterator iter = model.listStatements();

        // print out the predicate, subject and object of each statement
        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement();
            Resource subject = stmt.getSubject();
            Resource predicate = stmt.getPredicate();
            RDFNode object = stmt.getObject();

            System.out.print(subject.toString());
            System.out.print(" " + predicate.toString() + " ");
            if(object instanceof Resource) {
                System.out.print(object.toString());
            } else {
                // object is a literal
                System.out.print(" \"" + object.toString() + "\"");
            }

            System.out.println(" .");
        }
        // Jena's "dumb" writer
        model.write(System.out);

        // now write the model in xml form to a file
        model.write(System.out, "RDF/XML-ABBREV");

        // now write the model in N-Triples form to a file
        model.write(System.out, "N-TRIPLES");


        // System.out.println("Reading from RDF File...");
        readingRDF();
        // explicitPrefixDefinitions();

        // retrieve the John Smith vcard resource from the model
        Resource vcard = model.getResource(personURI);

        // retrieve the value of the N property
        Resource name = (Resource) vcard.getProperty(VCARD.N).getObject();

        System.out.println("Resource: " + name.toString());

        String fullName2 = vcard.getProperty(VCARD.FN).getString();

        System.out.println("Got fullName: " + fullName2);

        // add two nickname properties to vcard
        vcard.addProperty(VCARD.NICKNAME, "Smithy").addProperty(VCARD.NICKNAME, "Adam");

        // set up the output
        System.out.println("The nicknames of \"" + fullName2 + "\" are: ");
        // list the nicknames
        StmtIterator iter2 = vcard.listProperties(VCARD.NICKNAME);
        while (iter2.hasNext()) {
            System.out.println("   " + iter2.nextStatement().getObject().toString());
        }

        //queryingAModel(model);

        Resource r = model.createResource();

        // ad property
        r.addProperty(RDFS.label, model.createLiteral("chat", "en"))
                .addProperty(RDFS.label, model.createLiteral("chat", "fr"))
                .addProperty(RDFS.label, model.createLiteral("<em>chat</em>", true));

        model.write(System.out);

        Resource r2 = model.createResource();
        r2.addProperty(RDFS.label, "11")
                .addProperty(RDFS.label, "11");

        model.write(System.out, "N-Triple");

    }

    private static void readingRDF() {
        String inputFileName = "vc-db-1.rdf";

        Model model = ModelFactory.createDefaultModel();

        InputStream in = FileManager.get().open(inputFileName);

        if (in == null) {
            throw new IllegalArgumentException("File: " + inputFileName + " not found");
        }

        model.read(in, null);

        model.write(System.out);

        // select all the resources with a VCARD.FN property
        ResIterator iter = model.listSubjectsWithProperty(VCARD.FN);

        if (iter.hasNext()) {
            System.out.println("The database contains vcards for: ");
            while (iter.hasNext()) {
                System.out.println(" " + iter.nextResource().getProperty(VCARD.FN).getString());
            }
        } else {
            System.out.println("No vcards were found in the database");
        }

        // select all resources with VCARD.FN property whose value ends with "Smith"
        StmtIterator iter2 = model.listStatements(new SimpleSelector(null, VCARD.FN, (RDFNode) null) {
            public boolean selects(Statement s) {
                return s.getString().endsWith("Smith");
            }
        });

        // containers ------
        // create a bag
        Bag smiths = model.createBag();

        while (iter2.hasNext()) {
            smiths.add(iter2.nextStatement().getSubject());
        }

        // print out members of the bag
        NodeIterator iterNode = smiths.iterator();
        if (iterNode.hasNext()) {
            System.out.println("The bag contains: ");
            while (iterNode.hasNext()) {
                System.out.println("  " + ((Resource) iterNode.next()).getProperty(VCARD.FN).getString());
            }
        } else {
            System.out.println("The bag is empty");
        }
    }

    private static void explicitPrefixDefinitions() {
        Model m = ModelFactory.createDefaultModel();
        String nsA = "http://somewhere/else#";
        String nsB = "http://nowhere/else#";
        Resource root = m.createResource( nsA + "root" );
        Property P = m.createProperty(nsA + "P");
        Property Q = m.createProperty(nsB + "Q");
        Resource x = m.createResource(nsA + "x");
        Resource y = m.createResource(nsA + "y");
        Resource z = m.createResource(nsA + "z");

        m.add(root, P, x).add(root, P, y).add(y, Q, z);

        System.out.println("# -- no special prefixed defined");
        m.write(System.out);
        System.out.println("# -- nsA defined");
        m.setNsPrefix("nsA", nsA);
        m.write(System.out);
        System.out.println("# -- nsA and cat defined");
        m.setNsPrefix("cat", nsB);
        m.write(System.out);

        Model m2 = ModelFactory.createDefaultModel();
        m2.read("fragment.rdf");
        m2.write(System.out);

    }

    // create the resource

    private static void queryingAModel(Model model) {
        // list vcards
        ResIterator iter = model.listSubjectsWithProperty(VCARD.FN);
        while(iter.hasNext()) {
            Resource r = iter.nextResource();
        }
    }
}
