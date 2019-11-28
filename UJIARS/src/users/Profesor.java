package users;

public class Profesor {
    private String usuario;
    private String password;
    private IProyector proyector;

    public String getPassword() {
        return password;
    }

    public Profesor(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
    }

    public IProyector getProyector() {
        return proyector;
    }

    public void setProyector(IProyector proyector) {
        this.proyector = proyector;
    }
}
