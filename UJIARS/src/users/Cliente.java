package users;

import java.awt.datatransfer.DataFlavor;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class Cliente implements ICliente{
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
