package modelo;

import common.IAlumno;
import common.IAlumnoSala;
import common.IServidorInicio;
import controlador.alumno.ZonaRespondeController;
import javafx.application.Platform;
import vista.Main;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class Alumno extends UnicastRemoteObject implements IAlumno {

    private String nombre;
    private IServidorInicio servidor;
    private IAlumnoSala sala;
    private Main main;
    private ZonaRespondeController zonaRespondeController;

    public Alumno(String nombre, Main main, IServidorInicio servidor) throws RemoteException {
        super();
        this.nombre = nombre;
        this.servidor = servidor;
        this.main = main;
    }

    @Override
    public String getNombre() throws RemoteException {
        return nombre;
    }

    @Override
    public boolean unirseASala(int codSala) throws RemoteException {
        sala = servidor.entrarSala(codSala, (IAlumno) this);
        if (sala == null) {
            System.out.println("(Alumno.unirseASala) No has podido unirte a la sala");
            return false;
        }
        System.out.println("Has entrado en la sala. Esperando a que empiece la partida");
        return true;
    }

    @Override
    public void muestraPregunta(List<String> respuestas) throws RemoteException {
        Platform.runLater(() -> main.alumnoMuestraPregunta());
        Platform.runLater(() -> zonaRespondeController.respuesta1.setText(respuestas.get(0)));
        Platform.runLater(() -> zonaRespondeController.respuesta2.setText(respuestas.get(1)));
        Platform.runLater(() -> zonaRespondeController.respuesta3.setText(respuestas.get(2)));
        Platform.runLater(() -> zonaRespondeController.respuesta4.setText(respuestas.get(3)));
    }

    @Override
    public void verResultadoPregunta(boolean acierto) throws RemoteException {
        Platform.runLater(() -> main.alumnoMuestraResultadoPregunta(acierto));
        if (acierto) {
            System.out.println("Respuesta acertada");
        } else {
            System.out.println("Haber estudiao");
        }
    }

    @Override
    public void setTimer(String timer) throws RemoteException {
        Platform.runLater(() -> zonaRespondeController.setTimer(timer));
    }

    @Override
    public void finalizaPartida() throws RemoteException {
        this.sala = null;
        //Platform.runLater(() -> main.error("Se ha cerrado la sala.\nVolveras a la pagina inicial."));
        //Platform.runLater(() -> main.reiniciaLanding());
        Platform.runLater(this::vuelveAInicial);
    }

    private void vuelveAInicial(){
        main.error("Se ha cerrado la sala.\nVolveras a la pagina inicial.");
        main.reiniciaLanding();
    }

    @Override
    public void respondePregunta(String respuestaSeleccionada) throws RemoteException {
        sala.alumnoResponde(nombre, respuestaSeleccionada);
    }

    public void setRespondeController(ZonaRespondeController controller) {
        this.zonaRespondeController = controller;
    }
}
