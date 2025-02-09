import java.util.LinkedList;

public class CustomThreadPool {
    private final int capacity;                   // емкость Pool-а (количество рабочих потоков)
    private final LinkedList<Runnable> taskQueue; // очередь задач на исполнение
    private final Thread[] workerThreads;         // массив рабочих потоков, инициализируемый на размер capacity Pool-а
    private boolean isShutdown = false;           // флаг завершения работы Pool-а (если isShutdown = true новые задачи в очередь не принимаются, а находящиеся в очереди доводятся до завершения, после чего Pool прекращает работу)
    private boolean isWaiting = false;            // флаг указывающий был запущен метод awaitTermination() у Pool-а bили нет

    // Конструктор Pool-а потоков
    public CustomThreadPool(int capacity) {
        this.capacity = capacity;
        this.taskQueue = new LinkedList<>();       // инициализируем очередь
        this.workerThreads = new Thread[capacity]; // инициализируем массив рабочих потоков

        // запускаем рабочие потоки на исполнение
        for (int i = 0; i < capacity; i++) {
            workerThreads[i] = new Thread(new Worker());
            workerThreads[i].start();
        }
    }

    // добавление новой задачи в очередь
    public synchronized void execute(Runnable task) {
        if (isShutdown) {
            throw new IllegalStateException("ThreadPool is shut down");
        }
        taskQueue.add(task);
        notify();
    }

    // завершение работы Pool-а потоков
    public synchronized void shutdown() {
        System.out.println("SHUTDOWN SET");
        isShutdown = true;
        notifyAll();
    }

    // блокирует Poll потоков до завершения всех задач в очереди ?
    public synchronized void awaitTermination() {
        while ((!taskQueue.isEmpty() || !isShutdown) && !isWaiting) {
            try {
                System.out.println("AWAIT START");
                isWaiting = true;
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("AWAIT STOP");
        isWaiting = false;
    }

    // Класс, реализующий интерфейс Runnable, осуществляющий разбор по потокам заданий из очереди taskQueue и их запуск на выполнение
    private class Worker implements Runnable {
        // Метод выводящий Pool из сотояния ожидания после того, как бяла запущен метод awaitTermination()
        private void waitingNotify() {
            if ( CustomThreadPool.this.isWaiting ) {
                CustomThreadPool.this.notifyAll();
            }
        }

        @Override
        public void run() {
            while (true) {
                Runnable task;
                synchronized (CustomThreadPool.this) {
                    while (taskQueue.isEmpty() && !isShutdown) {
                        try {
                            waitingNotify();
                            CustomThreadPool.this.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    if (isShutdown && taskQueue.isEmpty()) {
                        waitingNotify();
                        return;
                    }
                    task = taskQueue.poll();
                }
                if (task != null) {
                    task.run();
                }
            }
        }
    }
}