/**
 * [read-only]
 *
 * A Location represents an "index" or a "pointer" into a data structure.
 */

interface Location<K> {

  /**
   * Returns the location in the structure of the key preceding the
   * one at this location.
   */

  Location<K> getBefore();
  
  /**
   * Returns the data at this location.
   */

  K get();
  
  /**
   * Returns the location in the structure of the key following the one
   * at this location.
   */

  Location<K> getAfter();
}
