import java.util.ArrayList;
import java.util.Scanner;   

class Cancha {
    private int numero;
    private String tipo;
    private int capacidad;
    private double costoPorHora;

    public Cancha(int numero, String tipo, int capacidad, double costoPorHora) {
        this.numero = numero;
        this.tipo = tipo;
        this.capacidad = capacidad;
        this.costoPorHora = costoPorHora;
    }

    public int getNumero() {
        return numero;
    }

    public boolean estaDisponible(String fecha, String horario) {
        return true;
    }
}

class Evento {
    private String responsable;
    private String nombreEvento;
    private String tipoEvento;
    private String fecha;
    private String horario;

    public Evento(String responsable, String nombreEvento, String tipoEvento, String fecha, String horario) {
        this.responsable = responsable;
        this.nombreEvento = nombreEvento;
        this.tipoEvento = tipoEvento;
        this.fecha = fecha;
        this.horario = horario;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHorario() {
        return horario;
    }
}

class Reserva {
    private Cancha cancha;
    private Evento evento;
    private boolean depositoPagado;

    public Reserva(Cancha cancha, Evento evento, boolean depositoPagado) {
        this.cancha = cancha;
        this.evento = evento;
        this.depositoPagado = depositoPagado;
    }

    public void confirmarReserva() {
        depositoPagado = true;
    }

    public Cancha getCancha() {
        return cancha;
    }

    public Evento getEvento() {
        return evento;
    }
}

class Complejo {
    private ArrayList<Cancha> canchas = new ArrayList<>();
    private ArrayList<Reserva> reservas = new ArrayList<>();
    private ArrayList<Evento> listaEspera = new ArrayList<>();

    public void agregarCancha(Cancha cancha) {
        canchas.add(cancha);
    }

    public void recibirSolicitud(Evento evento) {
        boolean asignado = false;
        for (Cancha cancha : canchas) {
            if (cancha.estaDisponible(evento.getFecha(), evento.getHorario())) {
                Reserva reserva = new Reserva(cancha, evento, false);
                reservas.add(reserva);
                asignado = true;
                break;
            }
        }
        if (!asignado) {
            listaEspera.add(evento);
        }
    }

    public void asignarCancha() {
        ArrayList<Evento> asignados = new ArrayList<>();
        for (Evento evento : listaEspera) {
            for (Cancha cancha : canchas) {
                if (cancha.estaDisponible(evento.getFecha(), evento.getHorario())) {
                    Reserva reserva = new Reserva(cancha, evento, false);
                    reservas.add(reserva);
                    asignados.add(evento);
                    break;
                }
            }
        }
        listaEspera.removeAll(asignados);
    }

    public ArrayList<Reserva> getReservas() {
        return reservas;
    }

    public ArrayList<Evento> getListaEspera() {
        return listaEspera;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Complejo complejo = new Complejo();

        while (true) {
            System.out.println("1. Registrar cancha");
            System.out.println("2. Solicitar reserva");
            System.out.println("3. Asignar cancha");
            System.out.println("4. Ver reservas");
            System.out.println("5. Ver lista de espera");
            System.out.println("6. Salir");
            int opcion = sc.nextInt();
            sc.nextLine();

            if (opcion == 1) {
                System.out.print("Número: ");
                int numero = sc.nextInt();
                sc.nextLine();
                System.out.print("Tipo: ");
                String tipo = sc.nextLine();
                System.out.print("Capacidad: ");
                int capacidad = sc.nextInt();
                System.out.print("Costo por hora: ");
                double costo = sc.nextDouble();
                sc.nextLine();
                Cancha cancha = new Cancha(numero, tipo, capacidad, costo);
                complejo.agregarCancha(cancha);
            }

            if (opcion == 2) {
                System.out.print("Responsable: ");
                String responsable = sc.nextLine();
                System.out.print("Nombre del evento: ");
                String nombre = sc.nextLine();
                System.out.print("Tipo de evento: ");
                String tipo = sc.nextLine();
                System.out.print("Fecha: ");
                String fecha = sc.nextLine();
                System.out.print("Horario: ");
                String horario = sc.nextLine();
                Evento evento = new Evento(responsable, nombre, tipo, fecha, horario);
                complejo.recibirSolicitud(evento);
            }

            if (opcion == 3) {
                complejo.asignarCancha();
            }

            if (opcion == 4) {
                for (Reserva r : complejo.getReservas()) {
                    System.out.println("Evento: " + r.getEvento().getNombreEvento() + " - Cancha: " + r.getCancha().getNumero());
                }
            }

            if (opcion == 5) {
                for (Evento e : complejo.getListaEspera()) {
                    System.out.println("Evento en espera: " + e.getNombreEvento());
                }
            }

            if (opcion == 6) {
                break;
            }
        }
    }
}