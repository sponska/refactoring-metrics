package refactoring.catalog.patterns.creation.moveCreationKnowledgeToFactory.after;

/**
 * @author mdrozdz
 */
public class StringParser {
    private Parser parser;
    private StringBuffer textBuffer;
    private int textBegin;
    private int textEnd;

    public Node findNode(/* ... */){
        /* ... */

        NodeFactory nodeFactory = new NodeFactory();
        return nodeFactory.createStringNode(textBuffer, textBegin, textEnd, parser.shouldDecodeNodes());
        /* ... */
    }
}
