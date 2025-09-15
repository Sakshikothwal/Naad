package naad.util;

public class Util {
    // Sleep for given milliseconds
    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("⚠️ Interrupted!");
        }
    }

    // Print divider for console output
    public static void printDivider() {
        System.out.println("-----------------------------");
    }

    // Debug print with prefix
    public static void debug(String message) {
        System.out.println("[DEBUG] " + message);
    }
}
