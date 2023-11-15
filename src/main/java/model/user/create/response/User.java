package model.user.create.response;

public class User
{
    private String email;
    private String name;

    public User(String email, String name)
    {
        this.email = email;
        this.name = name;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }


    public String getName() {
        return name;
    }

}
