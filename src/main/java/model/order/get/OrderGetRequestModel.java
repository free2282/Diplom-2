package model.order.get;

public class OrderGetRequestModel {
    private boolean success;
    private int total;
    private int totalToday;

    public OrderGetRequestModel(boolean success, int total, int totalToday) {
        this.success = success;
        this.total = total;
        this.totalToday = totalToday;
    }

    public OrderGetRequestModel() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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
