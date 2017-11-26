package lab.pkg.lab8b;

import java.util.ArrayDeque;
import java.util.Queue;

class OrderQueue {

    private Queue<Task> orders;
    private boolean moreOrdersComing;

    public OrderQueue() {
        orders = new ArrayDeque<>();
        moreOrdersComing = true;
    }

    public synchronized void createTask(String label, int timeToComplete) {

        while(orders.size() > 4){
            try {
                wait();
            } catch (InterruptedException ex) {

            }
        }

        notifyAll();
        orders.offer(new Task(label, timeToComplete));
    }

    public synchronized Task acceptTask() {
        while (orders.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException ex) {

            }
            // looping until there is a task in the queue to accept
        }
       
        notifyAll();
        return orders.poll();
    }

    public void setNoMoreOrders() {
        moreOrdersComing = false;
    }

    public boolean weAreDone() {
        return orders.isEmpty() && !moreOrdersComing;
    }
}
