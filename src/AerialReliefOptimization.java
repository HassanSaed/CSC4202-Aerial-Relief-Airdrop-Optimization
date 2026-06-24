import java.util.ArrayList;
import java.util.List;

public class AerialReliefOptimization {

    public static void main(String[] args) {
        // Scenario Data from the image
        String[] itemNames = {
            "Heavy Surgical Equipment",
            "High-Yield Water Purifiers",
            "Concentrated Emergency Rations",
            "Portable Diesel Generators",
            "First Aid Trauma Kits",
            "Satellite Communication Gear"
        };
        
        int[] weights = {600, 500, 500, 400, 100, 100};
        int[] values = {800, 650, 650, 500, 150, 120};
        int capacity = 1000;
        int n = weights.length;

        // --- START TIME TRACKING ---
        long startTime = System.nanoTime();

        // Solve using Dynamic Programming
        int[][] dp = new int[n + 1][capacity + 1];

        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= capacity; w++) {
                if (weights[i - 1] <= w) {
                    dp[i][w] = Math.max(values[i - 1] + dp[i - 1][w - weights[i - 1]], dp[i - 1][w]);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        // Retrieve maximum priority value
        int maxPriorityValue = dp[n][capacity];

        // Backtrack to identify chosen crates
        List<Integer> chosenIndices = new ArrayList<>();
        int w = capacity;
        int totalWeight = 0;

        for (int i = n; i > 0; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                chosenIndices.add(i - 1);
                totalWeight += weights[i - 1];
                w -= weights[i - 1];
            }
        }

        // --- END TIME TRACKING ---
        long endTime = System.nanoTime();
        
        // Calculate total execution time in nanoseconds and milliseconds
        long durationNano = endTime - startTime;
        double durationMilli = durationNano / 1_000_000.0;

        // Print Results
        System.out.println("=== Aerial Relief Airdrop Optimization ===");
        System.out.println("Maximum Survival Priority Value Achieved: " + maxPriorityValue);
        System.out.println("Total Payload Weight Used: " + totalWeight + " kg / " + capacity + " kg\n");
        System.out.println("Selected Supply Crates to Load:");
        System.out.println("----------------------------------------------------------------");
        
        for (int i = chosenIndices.size() - 1; i >= 0; i--) {
            int idx = chosenIndices.get(i);
            System.out.printf("Item Index %d | %-32s | Weight: %d kg | Value: %d\n", 
                    idx, itemNames[idx], weights[idx], values[idx]);
        }
        System.out.println("----------------------------------------------------------------");
        
        // Output Execution Performance
        System.out.printf("Execution Time (Core Algorithm): %d ns (%.4f ms)\n", durationNano, durationMilli);
    }
}