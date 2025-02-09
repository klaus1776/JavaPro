public class SleepThread {
    public static void sleepMs(Long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sleepSec(Long sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void printMark(int mark) {
        System.out.println(Thread.currentThread().getName() + " - " + mark);
    }

    public static void printStart() {
        System.out.println(Thread.currentThread().getName() + " - START TASK ");
    }

    public static void printStop() {
        System.out.println(Thread.currentThread().getName() + " - STOP TASK ");
    }
}