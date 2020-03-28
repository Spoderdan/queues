import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SimulationManager extends Thread{

    public static int numberOfClients;
    public static int numberOfServers;
    public static int timeLimit;
    public static int minArrivalTime;
    public static int maxArrivalTime;
    public static int minProcessingTime;
    public static int maxProcessingTime;
    public static float avgWaitTime;
    public static int tmp;

    public Boolean finished = false;

    private Scheduler scheduler;
    private List<Task> generatedTasks;

    public static FileWriter writer;

    public SimulationManager(String path){
        try{
            writer = new FileWriter(path);
        } catch (IOException e){
            System.err.println("Output file could not be found.");
            System.exit(-1);
        }
        generatedTasks = new ArrayList<Task>(numberOfClients);
        generateTasks();
        scheduler = new Scheduler(numberOfServers, numberOfClients);
    }

    @Override
    public void run() {

        int currentTime = 0;

        try{
            while(currentTime <= timeLimit && !finished){
                Iterator<Task> iterator = generatedTasks.listIterator();

                writer.write("\nTime " + currentTime + "\n");
                writer.write("Waiting clients: ");

                while(iterator.hasNext()){
                    Task t = iterator.next();
                    if(t.getArrivalTime() == currentTime){
                        scheduler.dispatchTask(t);
                        iterator.remove();
                        --numberOfClients;
                    }
                    else
                        writer.write(t + ";");
                }

                scheduler.getServerStatus();

                finished = true;
                for(Server s : scheduler.getServers())
                    if(s.isRunning())
                        finished = false;

                currentTime++;

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            avgWaitTime /= tmp;
            writer.write("\nSimulation complete!\n" + "Average waiting time: " + avgWaitTime);
        } catch (IOException e){
            System.err.println("Could not write to output file.");
            System.exit(-1);
        }

        try {
            writer.close();
        } catch (IOException e) {
            System.err.println("Output file could not be closed.");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void generateTasks(){

        int arrivalTime, processingTime;

        for(int i = 0; i < numberOfClients; i++){
            arrivalTime = generateRandomNo(minArrivalTime, maxArrivalTime);
            processingTime = generateRandomNo(minProcessingTime, maxProcessingTime);
            generatedTasks.add(new Task(i, arrivalTime, processingTime));
        }
    }

    public static int generateRandomNo(int min, int max){
        return (int) ((Math.random() * ((max - min) + 1)) + min);
    }

}
