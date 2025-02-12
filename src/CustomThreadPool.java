import java.util.LinkedList;

public class CustomThreadPool {
    private final int capacity;                   // емкость Pool-а (количество рабочих потоков)
    private final LinkedList<Runnable> taskQueue; // очередь задач на исполнение
    private final Thread[] workerThreads;         // массив рабочих потоков, инициализируемый на размер capacity Pool-а
    private boolean isShutdown = false;           // флаг завершения работы Pool-а (если isShutdown = true новые задачи в очередь не принимаются, а находящиеся в очереди доводятся до завершения, после чего Pool прекращает работу)

    // Конструктор Pool-а потоков
    public CustomThreadPool(int capacity) {
        this.capacity = capacity;
        this.taskQueue = new LinkedList<>();       // инициализируем очередь
        this.workerThreads = new Thread[capacity]; // инициализируем массив рабочих потоков

        // запускаем рабочие потоки на исполнение
        for (int i = 0; i < capacity; i++) {
            workerThreads[i] = new Worker();
            workerThreads[i].start();
        }
    }

    // добавление новой задачи в очередь
    public void execute(Runnable task) {
        synchronized (taskQueue) {
            if (isShutdown) {
                throw new IllegalStateException("ThreadPool is shut down");
            }
            taskQueue.add(task);
            taskQueue.notify();
        }
    }

    // завершение работы Pool-а потоков
    public void shutdown() {
            isShutdown = true;
            for (Thread worker : workerThreads) {
                worker.interrupt();
            }
            System.out.println("POOL SHUTDOWN");
    }

    // блокирует Poll потоков до завершения всех задач в очереди ?
    public void awaitTermination() {
        synchronized (taskQueue) {
            while (!taskQueue.isEmpty() && !isShutdown) {
                try {
                    System.out.println("AWAIT START");
                    taskQueue.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("AWAIT STOP");
        }
    }

    // Класс, реализующий интерфейс Runnable, осуществляющий разбор по потокам заданий из очереди taskQueue и их запуск на выполнение
    private class Worker extends Thread  {

       @Override
        public void run() {
            while (true) {
                Runnable task;
                synchronized (taskQueue) {
                    while ( taskQueue.isEmpty() ) {
                        taskQueue.notifyAll();

                        if (isShutdown) {
                            return;
                        }

                        try {
                            taskQueue.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
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