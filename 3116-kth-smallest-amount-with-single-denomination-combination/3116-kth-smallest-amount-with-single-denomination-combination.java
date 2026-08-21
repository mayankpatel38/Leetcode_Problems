import java.util.*;

class Solution {
    // Helper to store precomputed LCM and its PIE sign (+1 or -1)
    private static class Subset {
        long lcm;
        int sign;

        Subset(long lcm, int sign) {
            this.lcm = lcm;
            this.sign = sign;
        }
    }

    public long findKthSmallest(int[] coins, int k) {
        // Step 1: Sort and filter redundant coins (multiples of smaller coins)
        Arrays.sort(coins);
        List<Integer> filtered = new ArrayList<>();
        for (int c : coins) {
            boolean redundant = false;
            for (int fc : filtered) {
                if (c % fc == 0) {
                    redundant = true;
                    break;
                }
            }
            if (!redundant) {
                filtered.add(c);
            }
        }

        int n = filtered.size();
        List<Subset> subsets = new ArrayList<>();

        // Step 2: Precompute LCM for all 2^n - 1 non-empty subsets
        int totalSubsets = 1 << n;
        for (int mask = 1; mask < totalSubsets; mask++) {
            long currentLcm = 1;
            int bitCount = Integer.bitCount(mask);
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    currentLcm = lcm(currentLcm, filtered.get(i));
                }
            }
            int sign = (bitCount % 2 == 1) ? 1 : -1;
            subsets.add(new Subset(currentLcm, sign));
        }

        // Step 3: Binary Search the range [min_coin, min_coin * k]
        long low = filtered.get(0);
        long high = (long) filtered.get(0) * k;
        long ans = high;

        while (low <= high) {
            long mid = low + (high - low) / 2;
            if (countMultiples(mid, subsets) >= k) {
                ans = mid;
                high = mid - 1; // Try finding a smaller valid amount
            } else {
                low = mid + 1;
            }
        }

        return ans;
    }

    // Counts how many positive integers <= m are divisible by at least one coin
    private long countMultiples(long m, List<Subset> subsets) {
        long count = 0;
        for (Subset s : subsets) {
            count += s.sign * (m / s.lcm);
        }
        return count;
    }

    // Greatest Common Divisor (GCD)
    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // Least Common Multiple (LCM)
    private long lcm(long a, long b) {
        return (a / gcd(a, b)) * b;
    }
}