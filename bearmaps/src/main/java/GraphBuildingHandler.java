import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Set;

/**
 * Parses OSM XML files using an XML SAX parser. Used to construct the graph of roads for
 * pathfinding, under some constraints.
 * See OSM documentation on
 * <a href="http://wiki.openstreetmap.org/wiki/Key:highway">the highway tag</a>,
 * <a href="http://wiki.openstreetmap.org/wiki/Way">the way XML element</a>,
 * <a href="http://wiki.openstreetmap.org/wiki/Node">the node XML element</a>,
 * and the java
 * <a href="https://docs.oracle.com/javase/tutorial/jaxp/sax/parsing.html">SAX parser tutorial</a>.
 * <p>
 * You may find the CSCourseGraphDB and CSCourseGraphDBHandler examples useful.
 * <p>
 * The idea here is that some external library is going to walk through the XML file, and your
 * override method tells Java what to do every time it gets to the next element in the file. This
 * is a very common but strange-when-you-first-see it pattern. It is similar to the Visitor pattern
 * we discussed for graphs.
 *
 * @author Alan Yao, Maurice Lee
 */
public class GraphBuildingHandler extends DefaultHandler {
    /**
     * Only allow for non-service roads; this prevents going on pedestrian streets as much as
     * possible. Note that in Berkeley, many of the campus roads are tagged as motor vehicle
     * roads, but in practice we walk all over them with such impunity that we forget cars can
     * actually drive on them.
     */
    private static final Set<String> ALLOWED_HIGHWAY_TYPES = Set.of(
            "motorway", "trunk", "primary", "secondary", "tertiary", "unclassified", "residential",
            "living_street", "motorway_link", "trunk_link", "primary_link", "secondary_link",
            "tertiary_link"
    );
    private String activeState = "";
    private final GraphDB g;
    private boolean flag = false;
    long id;
    long last = 0;
    String name = "";
    String tempname = "";

    /**
     * Create a new GraphBuildingHandler.
     *
     * @param g The graph to populate with the XML data.
     */
    public GraphBuildingHandler(GraphDB g) {
        this.g = g;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {

        if (qName.equals("node")) {
            /* Encountering a new <node...> tag. */
            activeState = "node";
            // System.out.println("Node id: " + attributes.getValue("id"));
            // System.out.println("Node lon: " + attributes.getValue("lon"));
            // System.out.println("Node lat: " + attributes.getValue("lat"));
            last = Long.parseLong(attributes.getValue("id"));
            //id = Long.parseLong(attributes.getValue("id"));
            Long a = Long.parseLong(attributes.getValue("id"));
            Double b = Double.parseDouble(attributes.getValue("lon"));
            Double c = Double.parseDouble(attributes.getValue("lat"));
            g.addVertex(a, b, c);
            /* Hint: A graph-like structure would be nice. */

        } else if (qName.equals("way")) {
            /* Encountering a new <way...> tag. */
            id = 0;
            activeState = "way";


            // System.out.println("Beginning a way...");
        } else if (activeState.equals("way") && qName.equals("nd")) {
            /* While looking at a way, found a <nd...> tag. */
            // System.out.println("Node id in this way: " + attributes.getValue("ref"));
            if (id != 0) {
                g.addPossibleEdge(id, Long.parseLong(attributes.getValue("ref")));
            }
            id = Long.parseLong(attributes.getValue("ref"));

        } else if (activeState.equals("way") && qName.equals("tag")) {
            String k = attributes.getValue("k");
            String v = attributes.getValue("v");
            if (k.equals("highway")) {
                // System.out.println("Highway type: " + v);

                if (ALLOWED_HIGHWAY_TYPES.contains(v)) {
                    flag = true;
                    name = tempname;
                }
                /* Hint: Set a "flag". */

            } else if (k.equals("name")) {
                // System.out.println("Way Name: " + v);

//                if (flag) {
//                    g.addPossibles(v);
//                    flag = false;
//                    g.clearPossibles();
//                }

                tempname = v;
                id = 0;
            }
            // System.out.println("Tag with k=" + k + ", v=" + v + ".");
        } else if (activeState.equals("node") && qName.equals("tag") && attributes.getValue("k")
                .equals("name")) {
            /* While looking at a node, found a <tag...> with k="name". */


            g.addLocation(attributes.getValue("v"), last);
            // System.out.println("Node's name: " + attributes.getValue("v"));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("way")) {
            /* Done looking at a way. (Finished looking at the nodes, speeds, etc.) */

            /* Hint: If you have stored the possible connections for this way, here's your chance to
             * actually connect the nodes together if the way is valid. */
            if (flag) {
                g.addPossibles(name);
                flag = false;
            } else {
                g.clearPossibles();
            }

            g.clearPossibles();
            // System.out.println("Finishing a way...");
        }
    }

}
