package DuplicateZeros;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(duplicateZeros(new int[]{1, 0, 2, 3, 0, 4, 5, 0})));
    }

    public static int[] duplicateZeros(int[] arr) {
        int n = arr.length;
        int countZero = 0;

        for (int i = 0; i < n; i++) {
            if (arr[i] == 0) {
                countZero++;
            }
        }

        for(int i = n - 1; i >= 0; i--){
            if (i + countZero < n) {
                arr[i + countZero] = arr[i];
            }

            if(arr[i] == 0){
                countZero--;
                if(i + countZero < n){
                    arr[i + countZero] = 0;
                }
            }
        }
        return arr;
    }
}

