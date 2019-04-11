/**
 * There's nothing for you to do here.
 */

public class Assoc<K, V> {
  K key;
  V value;

  public Assoc(K key, V value) {
    this.key = key;
    this.value = value;
  }

  public String toString() {
    return "(" + key + "," + value + ")";
  }
}
