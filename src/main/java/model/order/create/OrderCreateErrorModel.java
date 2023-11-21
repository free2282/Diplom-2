package model.order.create;

public class OrderCreateErrorModel {
    private boolean success;
    private String message;

    public OrderCreateErrorModel(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public OrderCreateErrorModel() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
