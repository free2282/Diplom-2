package model.order.get;

import java.util.ArrayList;

public class OrderGetResponseModel
{
    private boolean success;
    private ArrayList<Order> orders;
    private int total;
    private int totalToday;

    public OrderGetResponseModel() {
    }

    public OrderGetResponseModel(boolean success, ArrayList<Order> orders, int total, int totalToday) {
        this.success = success;
        this.orders = orders;
        this.total = total;
        this.totalToday = totalToday;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalToday() {
        return totalToday;
    }

    public void setTotalToday(int totalToday) {
        this.totalToday = totalToday;
    }
}
