package lab.pkg.lab8b;

class TaskMaster implements Runnable {

    private final OrderQueue orders;
    private int ordersCreated;
    
    public TaskMaster(OrderQueue orders) {
        this.orders = orders;
        this.ordersCreated = 0;
    }
    
    @Override
    public void run() {
        while (ordersCreated++ < 100) {
            String orderLabel = "Task " + ordersCreated;
            int orderTime =  (int) (Math.random() * 1000 + 250);
            orders.createTask(orderLabel, orderTime);
        }
        orders.setNoMoreOrders();
    }
}
