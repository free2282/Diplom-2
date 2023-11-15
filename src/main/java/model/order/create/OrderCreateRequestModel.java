package model.order.create;

public class OrderCreateRequestModel
{
    private String[] ingredients;


    public OrderCreateRequestModel(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }
}
