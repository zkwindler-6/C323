import org.junit.Test;
import static org.junit.Assert.*;

public class Testing {

  @Test 
  public void testBasicCut() {
    int[] price, expectedProfit;
    Rod r;
    price = new int[] { 0, 1, 5, 8, 9, 13, 17, 17, 20, 24, 30 };
    expectedProfit = 
      new int[] { 0, 1, 5, 8, 10, 13, 17, 18, 22, 25, 30 };
    r = new Rod(price);
    for (int n = 1; n < price.length; n++)
      assertEquals(expectedProfit[n], r.basicCut(n));
    price = new int[] { 0, 3, 5, 8, 9, 13, 17, 17, 20, 24, 31 };
    expectedProfit = 
      new int[] { 0, 3, 6, 9, 12, 15, 18, 21, 24, 27, 31 };
    r = new Rod(price);
    for (int n = 1; n < price.length; n++)
      assertEquals(expectedProfit[n], r.basicCut(n));
    price = new int[] { 0, 2, 6, 8, 9, 13, 17, 17, 20, 24, 31 };
    expectedProfit = 
      new int[] { 0, 2, 6, 8, 12, 14, 18, 20, 24, 26, 31 };
    r = new Rod(price);
    for (int n = 1; n < price.length; n++)
      assertEquals(expectedProfit[n], r.basicCut(n));
  }

  @Test 
  public void testFancyCut() {
    int[] price, expectedProfit;
    Rod r;
    price = new int[] { 0, 1, 5, 8, 9, 13, 17, 17, 20, 24, 30 };
    expectedProfit = 
      new int[] { 0, 1, 5, 8, 10, 13, 17, 18, 22, 25, 30 };
    r = new Rod(price);
    for (int n = 1; n < price.length; n++) {
      Rod.Result ans = r.fancyCut(n);
      assertEquals(expectedProfit[n], ans.profit);
      assertTrue(isValid(ans, price));
    }
    price = new int[] { 0, 3, 5, 8, 9, 13, 17, 17, 20, 24, 31 };
    expectedProfit = 
      new int[] { 0, 3, 6, 9, 12, 15, 18, 21, 24, 27, 31 };
    r = new Rod(price);
    for (int n = 1; n < price.length; n++) {
      Rod.Result ans = r.fancyCut(n);
      assertEquals(expectedProfit[n], ans.profit);
      assertTrue(isValid(ans, price));
    }
    price = new int[] { 0, 2, 3, 7, 11, 13, 17, 20, 20, 21, 25 };
    expectedProfit = 
      new int[] { 0, 2, 4, 7, 11, 13, 17, 20, 22, 24, 28 };
    r = new Rod(price);
    for (int n = 1; n < price.length; n++) {
      Rod.Result ans = r.fancyCut(n);
      assertEquals(expectedProfit[n], ans.profit);
      assertTrue(isValid(ans, price));
    }
  }

  /**
   * Returns true iff the calculated profit in the result is
   * consistent with the sum of the price of each segment in
   * the list of lengths.
   */

  public static boolean isValid(Rod.Result ans, int[] price) {
    int cost = 0;
    for (Integer len : ans.lengths) 
      cost += price[len];
    return cost == ans.profit;
  }

}