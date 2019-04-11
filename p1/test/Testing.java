import static org.junit.Assert.*;

import org.junit.Test;

public class Testing {

    private static Dataset training = new Dataset(Constants.TRAINING_DATA);
    private static Dataset unknowns = new Dataset(Constants.TEST_DATA);

    @Test
    //passed
    /**
     * Start with this test.
     */
    public void createDigit() {
        Digit three = new Digit(Constants.THREE);
        assertEquals(3, three.getLabel());
        assertEquals(25, three.get(6, 5));
        assertEquals(152, three.get(7, 5));
    }

    @Test
    //passed
    /**
     * When you're ready to run this test, remove the // from the above line.
     */
    public void readTrainingData() {
        Dataset ds = new Dataset(Constants.DATA_DIR + "/tiny.csv");
        assertEquals(3, ds.size());
        assertEquals(1, ds.get(0).getLabel());
        assertEquals(0, ds.get(1).getLabel());
        assertEquals(1, ds.get(2).getLabel());
        assertEquals(0, ds.get(0).distance(ds.get(0)));
        assertEquals(3307, ds.get(0).distance(ds.get(1)));
        assertEquals(2217, ds.get(0).distance(ds.get(2)));
        assertEquals(3224, ds.get(1).distance(ds.get(2)));
    }

    @Test
    //passed
    public void testReadingDatasets() {
        assertEquals(42_000, training.size());
        assertEquals(28_000, unknowns.size());
    }

    @Test
    //failed: ArrayIndexOutOfBoundsException: 3
    // at Dataset.swap(Dataset.java:177)
    // at Dataset.knn(Dataset.java:86)
    // at Dataset.validate(Dataset.java:259)
    // at Testing.quickValidation(Testing.java:49)
    public void quickValidation() {
        assertNotNull(training);
        System.out.println(training.validate(3, 10));  // slow
    }

    @Test
    //failed: ArrayIndexOutOfBoundsException: 5
    // at Dataset.swap(Dataset.java:177)
    // at Dataset.knn(Dataset.java:86)
    // at Testing.biggerNotBetter(Testing.java:64)
    public void biggerNotBetter() {
        /**
         * For fun, let's look for samples that classify correctly for k = 5, but
         * incorrectly for k = 7.
         */
        Dataset test = new Dataset(Constants.DATA_DIR + "/menzel.csv");
        for (Digit d : test) {
            Digit d5 = test.knn(5, d);
            Digit d7 = test.knn(7, d);
            if (d5.getLabel() != d7.getLabel() && d5.getLabel() == d.getLabel())
                System.out.format("k=7 classifies this as %d, not %d.%n%s%n",
                        d7.getLabel(), d5.getLabel(), d);
        }
    }

    @Test
    //failed: ArrayIndexOutOfBoundsException: 3
    // at Dataset.swap(Dataset.java:177)
    // at Dataset.knn(Dataset.java:86)
    // at Testing.classifyUnknowns(Testing.java:73)
    public void classifyUnknowns() {
        int[] expected = new int[]{2, 0, 9, 9, 3, 7, 0, 3, 0, 3, 5, 7, 4, 0, 4};
        for (int i = 0; i < expected.length; i++)
            assertEquals(expected[i], training.knn(3, unknowns.get(i)).getLabel());
    }
}
  