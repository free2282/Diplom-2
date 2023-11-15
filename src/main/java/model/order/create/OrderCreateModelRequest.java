package model.order.create;

public class OrderCreateModelRequest
{
    private String[] ingredients;


    public OrderCreateModelRequest(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }
}
