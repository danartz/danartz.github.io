package lab.pkg.lab8b;

class Task {
    private String label;
    private int timeToComplete;
    
    public Task(String label, int timeToComplete) {
        this.label = label;
        this.timeToComplete = timeToComplete;
    }
    
    public int getTimeToComplete() {
        return timeToComplete;
    }
    
    @Override
    public String toString() {
        return label + ": " + timeToComplete + " ms";
    }
}
