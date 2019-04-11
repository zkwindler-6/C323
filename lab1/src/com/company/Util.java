package com.company;

public class Util {

    public static void main(String[] args) {

        int[] a = new int[] {2,7,9};
        int[] b = new int[] {1,6,7,2,7,9};
        int[] c = new int[] {1,6,7,2,7,3};

        System.out.println(search(a,b));
        System.out.println(search(a,c));

    }

    static int search(int[] a, int[] b){
        assert a.length <= b.length;

        int m = a.length;
        int n = b.length;
        int index = 0;

        for (int i = 0; i < n; i++){
            if (a[0] == b[i]){
                index = i;
                for (int j = 1; j < m; j++){
                    if (a[j] != b[i+j]) {
                        index = -1;
                        break;
                    }
                }
            }
        }
        return index;
    }
}
