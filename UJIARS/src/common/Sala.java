package common;

import modelo.Pregunta;
import modelo.Proyector;
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
    private IProfesor profesor;
    private Map<String, Integer> resultadosPregunta;  // Guardar los datos de las respuestas de la pregunta actual
    private Map<String, Boolean> alumnoAciertoActual; // Guarda la lista de alumnos y si han acertado o no la pregunta
    // TODO Cambiar resultadosPregunta a List para guardar todos los datos de la preguntas y sus respuestas y mostrarlo al final (estadisticas, pregunta mas acertada, pregunta mas fallada...)
    private Map<String, Integer> resultadosAlumnos;   // Guardar los datos de los aciertos de los alumnos (nombreAlumno, numAciertos)


    public Sala(Sesion miSesion, int codSala, IProyector proyector, IProfesor profesor) throws RemoteException {
        super();
        this.miSesion = miSesion;
        this.codSala = codSala;
        alumnos = new HashMap<>();
        resultadosPregunta = new HashMap<>();
        resultadosAlumnos = new HashMap<>();
        alumnoAciertoActual = new HashMap<>();
        this.proyector = proyector;
        this.profesor = profesor;
        System.out.println("(Sala) Nombre de la sesion: " + miSesion.getNombre().get());
    }

    @Override
    public int getCodSala() throws RemoteException {
        return codSala;
    }

    @Override
    public void muestraRanking() throws RemoteException {
        proyector.verRanking(resultadosAlumnos, false);
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

    @Override
    public void terminaPartida() throws RemoteException {
        for (IAlumno alumno : alumnos.values()){
            alumno.finalizaPartida();
        }
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
        System.out.println("(Sala.alumnoResponde) El alumno " + nombreAlumno + " responde: " + respuesta);
        for (Respuesta respuestas : preguntaActual.getRespuestas()) {
            if (respuestas.getRespuesta().equals(respuesta) && respuestas.isCorrecta()) {
                acierto = true;
                break;
            }
        }
        alumnoAciertoActual.put(nombreAlumno,acierto);
        proyector.alumnoResponde();

        // Actualizamos la cantidad de veces que se ha respondido a la pregunta
        resultadosPregunta.put(respuesta, resultadosPregunta.get(respuesta) +1 );
        if (acierto)
            // Actualizamos la cantidad de aciertos del alumno
            resultadosAlumnos.put(nombreAlumno, resultadosAlumnos.get(nombreAlumno) + preguntaActual.getPuntos());
        if (alumnoAciertoActual.size() == alumnos.size()) {
            finDeTiempo();
        }
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
                    finDeTiempo();
                }
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    private void finDeTiempo() {
        timer.cancel();
        for (IAlumno alumno : alumnos.values()) {
            try {
                alumno.verResultadoPregunta(alumnoAciertoActual.get(alumno.getNombre()));
                // TODO mostrar los resultados de la pregunta en el proyector
                List<Boolean> correctas = new ArrayList<>();
                for (Respuesta respuesta : preguntaActual.getRespuestas()){
                    correctas.add(respuesta.isCorrecta());
                }
                List<Integer> numRespuestas = new ArrayList<>(resultadosPregunta.values());
                proyector.muestraResultadoPregunta(correctas, numRespuestas);
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
        alumnoAciertoActual = new HashMap<>();
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

        timer.cancel();
        numPreguntaActual++;
        if (miSesion.getListaPreguntas().size() <= numPreguntaActual) {
            verResultadosPartida();
            return;
        }
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
        profesor.muestraEnunciado(enunciado);
    }

    @Override
    public void verResultadosPartida() throws RemoteException {
        // TODO Enviar al proyector los datos de los alumnos con mas respuestas acertadas
        System.out.println("Mostrando resultados finales");
        proyector.verRanking(resultadosAlumnos, true);
    }


}
