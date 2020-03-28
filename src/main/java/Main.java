import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){

        if(args.length != 2){
            System.err.println("Invalid number of arguments.");
            System.exit(-1);
        }

        int numberOfClients = 0;
        int numberOfQueues = 0;
        int timeLimit = 0;
        int minArrivalTime = 0;
        int maxArrivalTime = 0;
        int minProcessingTime = 0;
        int maxProcessingTime = 0;

        String aux;
        String[] buf;

        try{
            File in = new File(args[0]);
            Scanner scanner = new Scanner(in);

            numberOfClients = Integer.parseInt(scanner.nextLine());
            numberOfQueues = Integer.parseInt(scanner.nextLine());
            timeLimit = Integer.parseInt(scanner.nextLine());

            aux = scanner.nextLine();
            buf = aux.split(",");

            minArrivalTime = Integer.parseInt(buf[0]);
            maxArrivalTime = Integer.parseInt(buf[1]);

            aux = scanner.nextLine();
            buf = aux.split(",");

            minProcessingTime = Integer.parseInt(buf[0]);
            maxProcessingTime = Integer.parseInt(buf[1]);

            scanner.close();
        } catch (FileNotFoundException e){
            System.err.println("File not found.");
            System.exit(-1);
        }

        SimulationManager.numberOfClients = numberOfClients;
        SimulationManager.numberOfServers = numberOfQueues;
        SimulationManager.timeLimit = timeLimit;
        SimulationManager.minArrivalTime = minArrivalTime;
        SimulationManager.maxArrivalTime = maxArrivalTime;
        SimulationManager.minProcessingTime = minProcessingTime;
        SimulationManager.maxProcessingTime = maxProcessingTime;
        SimulationManager.tmp = numberOfClients;

        SimulationManager x = new SimulationManager(args[1]);
        x.start();

    }

}
