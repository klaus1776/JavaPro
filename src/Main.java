//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        CustomThreadPool customThreadPool = new CustomThreadPool(4);

        for (int i = 0; i < 8; i++) {
            customThreadPool.execute(() -> {
                SleepThread.printStart();
                SleepThread.sleepMs(3000L);
                SleepThread.printStop();
            });

        }
        //customThreadPool.shutdown();
        customThreadPool.awaitTermination();
        customThreadPool.shutdown();
     }
}