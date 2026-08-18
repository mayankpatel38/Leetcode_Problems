class Solution {
    public int largestInteger(int[] nums, int k) {
        
            int n = nums.length;
        Map<Integer, Integer> freq = new HashMap<>(); // number -> in how many windows it appears

        // use a frequency map for the current sliding window to track distinct elements
        Map<Integer, Integer> window = new HashMap<>();

        // build first window [0, k-1]
        for (int i = 0; i < k; i++) {
            window.put(nums[i], window.getOrDefault(nums[i], 0) + 1);
        }
        for (int num : window.keySet()) {
            freq.put(num, 1);
        }

        // slide the window
        for (int i = k; i < n; i++) {
            int out = nums[i - k];
            int in = nums[i];

            // remove outgoing
            window.put(out, window.get(out) - 1);
            if (window.get(out) == 0) window.remove(out);

            // add incoming
            window.put(in, window.getOrDefault(in, 0) + 1);

            // mark all distinct nums in current window as seen in another window
            for (int num : window.keySet()) {
                freq.put(num, freq.getOrDefault(num, 0) + 1);
            }
        }

        // find largest number that appears in <= 1 window
        int ans = -1;
        for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
            if (entry.getValue() <= 1) {
                ans = Math.max(ans, entry.getKey());
            }
        }
        return ans;
    }
}