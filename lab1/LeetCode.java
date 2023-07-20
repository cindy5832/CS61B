import java.util.HashMap;
import java.util.Map;

public class LeetCode {
    public static void main(String[] args) {
        int[] nums = {8, 1, 2, 2, 3};
        int n = 14;
        System.out.println(smallerNumbersThanCurrent(nums));

    }

    public static int[] smallerNumbersThanCurrent(int[] nums) {
        int[] small = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            int count = 0;
            for (int a : nums) {
                if (nums[i] > a) count++;
            }
            small[i] = count;
        }
        return small;
    }
}
