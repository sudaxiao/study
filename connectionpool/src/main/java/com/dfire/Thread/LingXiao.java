package com.dfire.Thread;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaosuda on 2018/1/8.
 */
public class LingXiao {


    public static boolean[]           b   = new boolean[100];
    public static List<List<Integer>> ans = new ArrayList<List<Integer>>();

    public static void robot(int index, int[] nums) {
        if(index == nums.length) {
            ArrayList<Integer> sub = new ArrayList<Integer>();
            for(int i = 0; i < index; ++i) {
                if(b[i]) {
                    sub.add(nums[i]);
                }
            }
            ans.add(sub);
            return ;
        }
        b[index] = true;
        robot(index + 1, nums);
        b[index] = false;
        robot(index + 1, nums);
    }

    public static List<List<Integer>> subsets(int[] nums) {
        ans.clear();
        robot(0, nums);
        return ans;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2 , 3};
        List<List<Integer>> lists = subsets(nums);

        int size = lists.size();
        System.out.println(size);
        for (int i = 0; i < size; i++) {
            List<Integer> integers = lists.get(i);
            for (Integer integer : integers) {
                System.out.print(integer + " ");
            }
            System.out.println("-------------------");
        }
    }

}
