package model.user.create.response;

public class UserCreate
{
    private String email;
    private String name;

    public UserCreate(String email, String name)
    {
        this.email = email;
        this.name = name;
    }

    public UserCreate() {
    }

    public String getEmail() {
        return email;
    }


    public String getName() {
        return name;
    }

}
