import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Predicate;

/**
 * lab12: starter
 * <p>
 * A Dictionary is an iterable collection of strings. The main
 * operations on a Dictionary are insert(), contains(), remove(), and
 * startsWith().
 * <p>
 * TODO: Implement the startsWith() method. Do this completely within
 * the context of Dictionary, i.e., you should not make any changes to
 * the Trie class.
 * <p>
 * Note: The iterator() method is provided for you. It depends on a
 * correct implementaiton of startsWith().
 */

class Dictionary extends Trie implements Iterable<String> {
    public static final String FILENAME = "data/big-dictionary.txt";

    /**
     * Creates an empty dictionary.
     */

    public Dictionary() {
        super();
    }

    /**
     * Reads the words from the file and creates a dictionary populated
     * with only those words that satisfy the given predicate.
     */

    public Dictionary(Predicate<String> pred) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(FILENAME));
            String word;
            while ((word = in.readLine()) != null)
                if (pred.test(word))
                    insert(word);
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("The dictionary file, " + FILENAME +
                    ", is not found.");
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    /**
     * TODO #4
     * <p>
     * Returns a list of all keys in this dictionary with the prefix s.
     * <p>
     * Hints:
     * <p>
     * (1) Define and make use of a helper method that does exactly the
     * same thing as this method, except that it takes one or more
     * additional arguments.
     * <p>
     * (2) Create an empty list here and save it in a variable. Pass the
     * list to your helper and let it fill the list with all words in
     * the trie starting with s. Return the list.
     * <p>
     * (3) There are two stages to solving this problem. The first stage
     * is to follow the path specified by s to a subtree. Once at the
     * subtree, the second stage is to add all the words in the subtree
     * to the list.
     */

    public List<String> startsWith(String s) {

        int i = 0;
        Node p = root;
        List<String> holder = new DoublyLinkedList<>();
        while (i != s.length()) {
            p = p.children.get(s.charAt(i++));
        }
        return startsWithHelper(p, holder);
    }

    public List<String> startsWithHelper(Node p, List<String> l) {

        Iterator it = p.children.values();
        while (it.hasNext())
            startsWithHelper((Node) it.next(), l);
        if (p.word != null)
            l.add(p.word);
        return l;
    }

    /**
     * Returns an iterator for traversing the words in this
     * dictionary. Note that removing via the returned iterator will not
     * remove the word from the dictionary.
     */

    public Iterator<String> iterator() {
        return startsWith("").iterator();
    }

    /**
     * Returns a textual representation of the words in this dictionary.
     */

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        Iterator<String> it = iterator();
        if (it.hasNext())
            sb.append(it.next());
        int i = 1;
        while (it.hasNext()) {
            if (i % 4 == 0) // 4 words per "row"
                sb.append(",\n ");
            else
                sb.append(", ");
            sb.append(it.next());
            i++;
        }
        sb.append(")");
        return sb.toString();
    }

}
