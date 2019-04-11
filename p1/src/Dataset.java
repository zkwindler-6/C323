import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Random;

public class Dataset extends LinkedList<Digit> {

    private Digit[] neighbors;

    /**
     * Creates an empty dataset.
     */

    public Dataset() {
        super();
    }

    /**
     * Initializes this dataset by loading digits from the given file.
     */

    public Dataset(String filename) {
        load(filename);
    }

    /**
     * Runs knn on each entry in the given test set, using k as the number of
     * nearest neighbors and this dataset as the training set.
     */

    public void classify(int k, Dataset unknowns) {
        int i = 1;
        for (Digit d : unknowns) {
            d.classify(knn(k, d).getLabel());
            System.out.println(i++ + ", " + d.getLabel());
        }
    }

    class Pair<Digit, Integer> {
        Digit dig;
        Integer dis;

        Pair(Digit dig, Integer dis) {
            this.dig = dig;
            this.dis = dis;
        }

        Digit getDig() {
            return dig;
        }

        Integer getDis() {
            return dis;
        }
    }

    /**
     * TODO
     * <p>
     * Searches this dataset to find the k nearest neighbors to the given
     * unknown, and then returns the closest neighbor with the majority label.
     * <p>
     * Assume k is a positive integer less than the size of this dataset.
     */

    public Digit knn(int k, Digit unknown) {
        assert k > 0;
        assert k < size();

        // TODO

        Pair[] distance_holder = new Pair[size()];
        Pair<Digit, Integer> dig_dis;

        Dataset myData = this;
        int nn = 0;

        for (Digit d : myData) {
            int distance = unknown.distance(d);
            dig_dis = new Pair<>(d, distance); //gives the pair the digit and integer values

            distance_holder[nn] = dig_dis; //puts digits with distances inside array
            nn++;
        }

        heapify(distance_holder);
        neighbors = new Digit[k];

        //need to do something more here
        //phase 2
        //siftdown
        for (int i = 0; i < k; i++) {
            neighbors[i] = (Digit) distance_holder[i].getDig();
            siftDown(distance_holder, 0, distance_holder.length);
        }

        int mode = mode(neighbors);

        for (int i = 0; i < neighbors.length; i++) {
            if (neighbors[i].getLabel() == mode) {
                return neighbors[i];
            }
        }

        return null;
    }

    public int mode(Digit[] neighbors) { // return int or digit?

        int[] occur = new int[10];
        int ans = 0;

        for (int i = 0; i < neighbors.length; i++) {
            int val = neighbors[i].getLabel();
            occur[val] += 1;
        }

        ans = indexOfMax(occur);

        return ans;
    }

    public static int indexOfMax(int[] arr) {
        if (arr.length == 0) {
            return -1;
        }

        int max = arr[0];
        int index = 0;

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                index = i;
                max = arr[i];
            }
        }

        return index;
    }


    // might not have for statement when using siftdown iteratively
    public static void heapify(Pair[] a) {
        int n = a.length;
        int last = getParent(n - 1);
        for (int p = last; p >= 0; p--) {
            siftDown(a, p, n);
        }
    }

    // needs to be done iteratively
    public static void siftDown(Pair<Digit, Integer>[] a, int p, int n) {

        int left = getLeft(p);
        while (left < n) {
            int minChild = left;
            int right = getRight(p);

            if (right < n && a[right].getDis() < a[minChild].getDis()) {
                minChild = right;
            }

            if (a[minChild].getDis() < a[p].getDis()) { //min heap?
                swap(a, p, minChild);
                p = minChild;
            } else
                return;
            left = getLeft(p);
        }
    }


    //use Digit[] instead of int[]?
    public static void swap(Pair[] a, int i, int j) { //error here?
        Pair temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static int getLeft(int p) {
        p = (2 * p) + 1;
        return p;
    }

    public static int getRight(int p) {
        p = getLeft(p) + 1;
        return p;
    }

    public static int getParent(int p) {
        p = (p - 1) / 2;
        return p;
    }

    /**
     * Returns the array of nearest neighbors found by the most
     * recent call to knn.
     */

    public Digit[] getNeighbors() {
        return neighbors;
    }

    /**
     * Loads the digits from the given filename into this dataset.
     */

    public void load(String filename) {
        if (!filename.endsWith(".csv"))
            filename += ".csv";
        try {
            BufferedReader in = new BufferedReader(new FileReader(new File(filename)));
            in.readLine(); // get rid of header
            in.lines().forEachOrdered(line -> add(new Digit(line)));
            in.close();
        } catch (IOException ex) {
            System.out.println("Problem reading file: [" + filename + "]\n");
        }
    }

    /**
     * Saves this dataset into the specified file using the MNIST format.
     */

    public void save(String filename) {
        if (!filename.endsWith(".csv"))
            filename += ".csv";
        StringBuilder header = new StringBuilder();
        header.append("label,");
        int last = Constants.DIM * Constants.DIM - 1;
        for (int i = 0; i < last - 1; i++)
            header.append("pixel").append(i).append(",");
        header.append("pixel").append(last);
        try {
            PrintWriter out = new PrintWriter(new File(filename));
            out.println(header);
            for (Digit d : this)
                out.println(d.getCode());
            out.close();
        } catch (IOException e) {
            System.out.println("Problem creating the file: [" + filename + "]\n.");
        }
    }

    /**
     * Returns the accuracy of knn on m random digits in this dataset. Takes a
     * long time if this dataset is large.
     */

    public double validate(int k, int m) {
        int n = size(), matches = 0;
        Random rand = new Random();
        for (int i = 0; i < m; i++) {
            int pos = rand.nextInt(n);
            // Select a sample and remove it (temporarily) from this dataset to avoid
            // matching with itself.
            Digit sample = remove(pos);
            Digit match = knn(k, sample);
            if (sample.getLabel() == match.getLabel()) {
                matches++;
                System.out.print(".");
            } else
                System.out.print("x");
            add(pos, sample);
        }
        System.out.println();
        return (100.0 * matches) / m;
    }

    /**
     * Simple testing.
     */

    public static void main(String[] args) {
        Dataset tiny = new Dataset(Constants.DATA_DIR + "/tiny.csv");
        Dataset train = new Dataset(Constants.DATA_DIR + "/menzel.csv");
        train.classify(3, tiny);
        System.out.println("\nSize of dataset: " + train.size());
        int m = 50;
        for (int k = 3; k <= 11; k += 2, m += 10)
            System.out.format("k = %2d: %.1f%%%n%n", k, train.validate(k, m));
    }

}