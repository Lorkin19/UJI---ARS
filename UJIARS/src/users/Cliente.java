package users;

public abstract class Cliente implements IAlumno {
    private String password;

    public void startClient() //Método para iniciar el cliente
    {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
