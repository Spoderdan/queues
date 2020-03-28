import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Scheduler {

    private List<Server> servers;
    private int maxNoServers;
    private int maxTasksPerServer;

    public Scheduler(int maxNoServers, int maxTasksPerServer){
        this.servers = new ArrayList<Server>(maxNoServers);
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;

        for(int i = 0; i < maxNoServers; i++)
            newThread();
    }

    /**
     * This method creates a server object, adds it to the list, creates a thread from it and starts it.
     */
    private void newThread(){
        Server s = new Server(maxTasksPerServer);
        servers.add(s);
        Thread t = new Thread(s);
        t.start();
    }

    public void dispatchTask(Task t){
        int globalMin = Integer.MAX_VALUE;
        int localIndex = 0, globalIndex = 0;

        for(Server s : servers){
            if(s.getWaitingPeriod() < globalMin) {
                globalMin = s.getWaitingPeriod();
                globalIndex = localIndex;
            }
            localIndex++;
        }

        servers.get(globalIndex).addTask(t);
    }

    public void getServerStatus(){
        int i = 0;
        try{
            for(Server s : servers){
                if(i == 0)
                    SimulationManager.writer.write("\nQueue " +  i + ":");
                else
                    SimulationManager.writer.write("\nQueue " +  i + ":");

                int nrOfTasks = s.getTasks().size();

                if(nrOfTasks == 0)
                    SimulationManager.writer.write(" closed");
                else
                    for(Task t : s.getTasks())
                        SimulationManager.writer.write(t + ";");

                SimulationManager.writer.write("");
                i++;
            }
            SimulationManager.writer.write("\n");
        } catch (IOException e){
            System.err.println("Could not write to output file.");
            System.exit(-1);
        }
    }

    public List<Server> getServers(){
        return servers;
    }

}
