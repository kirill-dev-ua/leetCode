package ConteinsDuplicate;

import java.util.Arrays;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        System.out.println(containsDuplicate(new int[]{1, 2, 3, 1}));
    }

    public static boolean containsDuplicate(int[] nums) {
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == nums[i + 1]) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsNearbyDuplicate2(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < nums.length - 1; i++){
            if(map.containsKey(nums[i])){
                if(nums[i] == k){
                    return true;
                }
            }
            map.put(nums[i], i);
        }
        return false;
    }
}
