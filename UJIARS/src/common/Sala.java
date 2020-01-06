package common;

import modelo.Pregunta;
import modelo.Respuesta;
import modelo.Sesion;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Sala extends UnicastRemoteObject implements IServidorSala {
    private int codSala;
    private Map<String, IAlumno> alumnos;
    private Sesion miSesion;
    private Timer timer;
    private int numPreguntaActual;
    private Pregunta preguntaActual;
    private IProyector proyector;
    private Map<String, Integer> resultadosPregunta;  // Guardar los datos de las respuestas de la pregunta actual
    private Map<String, Boolean> alumnoAciertoActual;
    // TODO Cambiar resultadosPregunta a List para guardar todos los datos de la preguntas y sus respuestas y mostrarlo al final (estadisticas, pregunta mas acertada, pregunta mas fallada...)
    private Map<String, Integer> resultadosAlumnos;   // Guardar los datos de los aciertos de los alumnos


    public Sala(Sesion miSesion, int codSala, IProyector proyector) throws RemoteException {
        super();
        this.miSesion = miSesion;
        this.codSala = codSala;
        alumnos = new HashMap<>();
        resultadosPregunta = new HashMap<>();
        resultadosAlumnos = new HashMap<>();
        alumnoAciertoActual = new HashMap<>();
        this.proyector = proyector;
        System.out.println("(Sala) Nombre de la sesion: " + miSesion.getNombre().get());
    }

    @Override
    public int getCodSala() throws RemoteException {
        return codSala;
    }


    /**
     * Un alumno entra a la sala. Si el nombre del alumno ya esta en la sala, no le deja unirse (Solo si los alumnos no tienen que registrarse)
     *
     * @param alumno alumno que se quiere unir a la sala
     * @return Si se ha podido anyadir
     * @throws RemoteException si algo peta
     */
    @Override
    public boolean addAlumno(IAlumno alumno) throws RemoteException {
        if (alumnos.containsKey(alumno.getNombre())) {
            return false;
        }
        alumnos.put(alumno.getNombre(), alumno);
        resultadosAlumnos.put(alumno.getNombre(), 0);
        proyector.anyadeAlumno(alumno.getNombre());
        return true;
    }

    /**
     * La sala analiza la respuesta del alumno y le indica si ha acertado o no
     *
     * @param nombreAlumno nombre del alumno que realiza la respuesta
     * @param respuesta    respuesta seleccionada por el alumno
     * @throws RemoteException si algo peta
     */
    @Override
    public void alumnoResponde(String nombreAlumno, String respuesta) throws RemoteException {
        boolean acierto = false;
        for (Respuesta respuestas : preguntaActual.getRespuestas()) {
            if (respuestas.getRespuesta().equals(respuesta) && respuestas.isCorrecta()) {
                acierto = true;
                break;
            }
        }
        alumnoAciertoActual.put(nombreAlumno,acierto);
        if (acierto)
            actualizaResultados(respuesta);
    }

    private void actualizaResultados(String respuesta) {
        System.out.println("guardando cantidad de veces respondido");
        resultadosPregunta.put(respuesta, resultadosPregunta.get(respuesta) + 1);
    }

    /**
     * Usado por el profesor para empezar la partida
     * Envia a los alumnos que esten en la sala la primera pregunta
     */
    @Override
    public void empezarPartida() throws RemoteException {
        numPreguntaActual = 0;
        preguntaActual = miSesion.getListaPreguntas().get(numPreguntaActual);
        enviarPregunta();
    }


    /**
     * Baraja las opciones
     * Resetea los resultadosPregunta
     * Envia la pregunta a los alumnos y al proyector
     *
     * @throws RemoteException si algo peta
     */
    private void enviarPregunta() throws RemoteException {
        // Barajamos las preguntas.
        barajarPreguntaActual();
        // Resetear los resultadosPregunta
        resetResultados();
        // Enviar la pregunta a los alumnos y al proyector
        notificaPreguntaAlumnos();
        notificaPreguntaProyector();

        // TODO Falta esperar los 15 segundos (o el tiempo que sea para esa pregunta)
        startTimer((int)preguntaActual.getTiempo());
        // Visualizar los resultadosPregunta de la pregunta en el proyector
        //proyector.verResultados(resultadosPregunta);
    }

    private void startTimer(int tiempo) {
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            private int timeCount = tiempo+1;
            @Override
            public void run() {
                timeCount-=1;
                try {
                    proyector.setTimer(Integer.toString(timeCount));
                    for (IAlumno alumno : alumnos.values())
                        alumno.setTimer(Integer.toString(timeCount));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                if (timeCount == 0) {
                    stopTimer();
                }
            }
        };
        timer.schedule(timerTask, 0, 1500);
    }

    private void stopTimer() {
        timer.cancel();
        for (IAlumno alumno : alumnos.values()) {
            try {
                alumno.verResultadoPregunta(alumnoAciertoActual.get(alumno.getNombre()));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Usado para reorganizar las respuestas de la pregunta
     */
    private void barajarPreguntaActual() {
        System.out.println("Barajando las respuesstas de la pregunta: " + preguntaActual.getEnunciado().get());
        Collections.shuffle(preguntaActual.getRespuestas());
    }

    private void resetResultados() {
        for (Respuesta respuesta : preguntaActual.getRespuestas()) {
            resultadosPregunta.put(respuesta.getRespuesta(), 0);
        }
        /*
        for (String alumno : alumnoAciertoActual.keySet()) {
            alumnoAciertoActual.put(alumno, false);
        }*/
    }

    /**
     * El profesor ha indicado que quiere pasar de de pregunta
     * Se barajan las posibles respuestas de la pregunta
     * Se envia la pregunta YA BARAJADA a los alumnos y al profesor
     *
     * @throws RemoteException si algo peta
     */

    @Override
    public void pasarDePregunta() throws RemoteException {
        // Para guardarse una referencia a la pregunta actual de la sesion
        numPreguntaActual++;
        preguntaActual = miSesion.getListaPreguntas().get(numPreguntaActual);

        enviarPregunta();
    }

    /**
     * Notifica a los alumnos que hay una nueva pregunta y se la envia
     *
     * @throws RemoteException si algo peta
     */
    private void notificaPreguntaAlumnos() throws RemoteException {
        List<String> respuestas = new ArrayList<>();
        for (Respuesta respuesta : preguntaActual.getRespuestas()) {
            respuestas.add(respuesta.getRespuesta());
        }
        for (IAlumno alumno : alumnos.values()) {
            alumno.muestraPregunta(respuestas);
        }
    }

    /**
     * Se notifica al proyector de la siguiente pregunta de la partida
     *
     * @throws RemoteException si algo peta
     */
    private void notificaPreguntaProyector() throws RemoteException {
        String enunciado = preguntaActual.getEnunciado().get();
        System.out.println("(sala.notificaProyector)Enunciado: " + enunciado);
        List<String> respuestas = new ArrayList<>();
        for (Respuesta respuesta : preguntaActual.getRespuestas()) {
            respuestas.add(respuesta.getRespuesta());
        }
        proyector.verPregunta(enunciado, respuestas);
    }

    @Override
    public void verResultadosPartida() throws RemoteException {
        // TODO Enviar al proyector los datos de los alumnos con mas respuestas acertadas
    }


}
