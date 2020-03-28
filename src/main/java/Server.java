import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{

    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private Boolean running = true;

    public Server(int maxTasksPerServer){
        this.tasks = new ArrayBlockingQueue<Task>(maxTasksPerServer);
        this.waitingPeriod = new AtomicInteger();
    }

    public void addTask(Task newTask){
        tasks.add(newTask);
        waitingPeriod.addAndGet(newTask.getProcessingTime());
        SimulationManager.avgWaitTime += waitingPeriod.get();
    }

    @Override
    public void run() {
        while(SimulationManager.numberOfClients != 0 || !tasks.isEmpty()){
            try {
                if(!tasks.isEmpty()){
                    Task t = tasks.peek();
                    int pt = t.getProcessingTime();
                    Thread.sleep(pt * 1000);
                    waitingPeriod.addAndGet(-pt);
                    tasks.take();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        running = false;
    }

    public List<Task> getTasks(){
        return new ArrayList<Task>(tasks);
    }

    public int getWaitingPeriod(){
        return waitingPeriod.get();
    }

    public Boolean isRunning(){
        return running;
    }

}
