package com.angualrspringapp.jena.model;

/* CVS $Id: $ */

import com.hp.hpl.jena.rdf.model.*;

/**
 * Vocabulary definitions from dive.rdf
 * @author Auto-generated by schemagen on 06 Nov 2013 19:40
 */
public class DiveModel {
    /** <p>The RDF model that holds the vocabulary terms</p> */
    private static Model m_model = ModelFactory.createDefaultModel();

    /** <p>The namespace of the vocabulary as a string</p> */
    public static final String NS = "http://scubadive.networld.to/dive.rdf";

    /** <p>The namespace of the vocabulary as a string</p>
     *  @see #NS */
    public static String getURI() {return NS;}

    /** <p>The namespace of the vocabulary as a resource</p> */
    public static final Resource NAMESPACE = m_model.createResource( NS );

    /** <p>The ontology's owl:versionInfo as a string</p> */
    public static final String VERSION_INFO = "Revision: 2011-01-02";

}