package lab.pkg.lab8b;

class Worker implements Runnable {
    
    private final OrderQueue orders;
    private final int workerId;
    
    public Worker(OrderQueue orders, int workerId) {
        this.orders = orders;
        this.workerId = workerId;
    }

    @Override
    public void run() {
        
        while (!orders.weAreDone()) {
            
            Task task = orders.acceptTask();
            
            System.out.println("Worker " + workerId + " accepted " + task);
            
            try {
                Thread.sleep(task.getTimeToComplete());
            } catch (InterruptedException e) {
                System.out.println("Worker " + workerId + 
                        " failed to complete " + task);
            }
            
            //System.out.println("Worker " + workerId + " finished " + task);
        }
    }
}
