public class Task {

    private int ID;
    private int arrivalTime;
    private int processingTime;

    public Task(int id, int ad, int pt){
        this.ID = id;
        this.arrivalTime = ad;
        this.processingTime = pt;
    }

    @Override
    public String toString() {
        return "(" + ID + "," + arrivalTime + "," + processingTime + ")";
    }

    public int getArrivalTime(){
        return arrivalTime;
    }

    public int getProcessingTime(){
        return processingTime;
    }

}
