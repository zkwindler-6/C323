package com.company;

import org.junit.Test;

import static org.junit.Assert.*;

public class UtilTest {
    @Test
    public void search() {
        int[] a = {0,1,2};
        int[] b1 = {0,1,2,3,4,5};
        int[] b2 = {1,2,3,0,1,0,1,2};
        assertEquals("can not find an array in the beginning", 0, Util.search(a,b1));
        assertEquals("can not find an array at the end", 5, Util.search(a,b2));
    }

}