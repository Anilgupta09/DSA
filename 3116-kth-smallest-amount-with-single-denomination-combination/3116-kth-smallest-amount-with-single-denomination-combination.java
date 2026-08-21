class Solution {

    public long findKthSmallest(int[] coins, int k) {

        long low = 1;
        long high = (long) coins[0] * k;

        for (int coin : coins) {
            high = Math.min(high, (long) coin * k);
        }

        while (low < high) {

            long mid = low + (high - low) / 2;

            long count = countAmounts(coins, mid, k);

            if (count >= k) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        return low;
    }

    private long countAmounts(int[] coins, long amount, long k) {
        return dfs(coins, 0, 1L, 0, amount, k);
    }

    private long dfs(int[] coins,
                     int index,
                     long lcm,
                     int selected,
                     long amount,
                     long k) {

        long result = 0;

        for (int i = index; i < coins.length; i++) {

            long gcd = gcd(lcm, coins[i]);

            // Avoid overflow while calculating LCM
            long newLcm = lcm / gcd;

            if (newLcm > amount / coins[i]) {
                continue;
            }

            newLcm *= coins[i];

            long contribution = amount / newLcm;

            if ((selected + 1) % 2 == 1) {
                result += contribution;
            } else {
                result -= contribution;
            }

            // We only need to know whether count >= k
            if (result >= k) {
                return result;
            }

            result += dfs(
                coins,
                i + 1,
                newLcm,
                selected + 1,
                amount,
                k
            );

            if (result >= k) {
                return result;
            }
        }

        return result;
    }

    private long gcd(long a, long b) {

        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }

        return a;
    }
}