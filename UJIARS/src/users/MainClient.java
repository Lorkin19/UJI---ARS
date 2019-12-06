package users;

import common.IServidorInicio;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class MainClient {

    private IServidorInicio servidorInicio = null;
    Scanner sc = new Scanner(System.in);
    private Alumno alumno = null;
    private Profesor profesor = null;
    private Proyector proyector = null;

    public static void main(String[] args) throws IOException
    {
        MainClient cliente = new MainClient();
        System.out.println("Iniciando cliente\n");
        cliente.ejecuta();
    }

    private void ejecuta(){
        // Instancia la primera clienteFlotaSockets
        String registryURL = "rmi://localhost:1099/UJIARS";

        // Busca el objeto devuelto por el servidor (IntServJuego)
        try {
            servidorInicio = (IServidorInicio) Naming.lookup(registryURL);
        } catch (MalformedURLException | RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        // Preguntamos si es alumno o profesor:
        boolean incorrecto = true;
        while (incorrecto) {
            System.out.println("Eres alumno o profesor?\n(1 --> alumno)\n(2 --> profesor)");
            int respuesta = sc.nextInt();
            if (respuesta == 1){
                try {
                    int codigoSala;
                    String nombre;
                    System.out.println("Introduce el codigo de la sala:");
                    codigoSala = Integer.parseInt(sc.next());
                    if (servidorInicio.compruebaSala(codigoSala)){
                        System.out.println("Introduce tu nombre");
                        nombre = sc.next();
                        alumno = new Alumno(nombre, servidorInicio);
                        alumno.unirseASala(codigoSala);
                        incorrecto = false;
                    } else {
                        System.out.println("Codigo de sala incorrecto.");
                    }
                }catch (RemoteException e){
                    e.printStackTrace();
                }
            }
            if (respuesta == 2){
                try {
                    String usuario;
                    String contrasenya;
                    int accion = 0;
                    while (accion != 3) {
                        System.out.println("Introduce:\n(1 --> Iniciar sesion)\n(2 --> Registrarse)\n(3 --> Volver)");
                        accion = sc.nextInt();
                        if (accion != 1 && accion != 2) {
                            System.out.println("Dato no valido");
                        } else {
                            // recogemos los datos:
                            System.out.println("Introduce usuario:");
                            usuario=sc.next();
                            System.out.println("Introduce contrasenya:");
                            contrasenya = sc.next();
                            // el Profesor pretende iniciar sesion
                            if (accion == 1){
                                try {
                                    profesor = servidorInicio.iniciaProfesor(usuario, contrasenya);
                                } catch (NullPointerException e) {
                                    //System.out.println("No existe el usuario");
                                }
                                if (profesor == null){
                                    System.out.println("Nombre de usuario o contrasenya incorrectos");
                                } else {
                                    System.out.println("Sesion iniciada correctamente");
                                }
                            }
                            // el Profesor pretende registrarse
                            if  (accion == 2){
                                if (servidorInicio.registraProfesor(usuario, contrasenya)){
                                    profesor = servidorInicio.iniciaProfesor(usuario, contrasenya);
                                    System.out.println("Registrado correctamente");
                                } else{
                                    System.out.println("El nombre de usuario ya existe");
                                }
                            }
                        }

                    }
                }catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        // Ya tenemos la sesion iniciada

        if (profesor==null){
            ejecutaAlumno();
        } else {
            ejecutaProfesor();
        }
    }

    private void ejecutaProfesor(){

    }

    private void ejecutaAlumno(){

    }
}
