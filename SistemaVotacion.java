import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SistemaVotacion {
    private List<String> candidatos;
    private Map<String, Boolean> votantesRegistrados;
    private Map<String, String> votantesContrasenas;
    private Map<String, String> votos;

    public SistemaVotacion() {
        this.candidatos = new ArrayList<>();
        this.votantesRegistrados = new HashMap<>();
        this.votantesContrasenas = new HashMap<>();
        this.votos = new HashMap<>();
    }

    public void registrarVotante(String nombre, String contrasena) {
        if (!votantesRegistrados.containsKey(nombre)) {
            votantesRegistrados.put(nombre, false);
            votantesContrasenas.put(nombre, contrasena);
            System.out.println("Votante registrado: " + nombre);
        } else {
            System.out.println("El votante ya está registrado.");
        }
    }

    public boolean autenticarVotante(String nombre, String contrasena) {
        return votantesContrasenas.containsKey(nombre) && votantesContrasenas.get(nombre).equals(contrasena);
    }

    public void agregarCandidato(String nombre) {
        candidatos.add(nombre);
    }

    public void realizarVoto(String votante, String contrasena, String candidato) {
        if (votantesRegistrados.containsKey(votante) && !votantesRegistrados.get(votante)) {
            if (!votos.containsKey(votante) && autenticarVotante(votante, contrasena)) {
                if (candidatos.contains(candidato)) {
                    votos.put(votante, candidato);
                    votantesRegistrados.put(votante, true);
                    System.out.println("Voto de " + votante + " registrado por " + candidato);
                } else {
                    System.out.println("El candidato no está registrado.");
                }
            } else if (votos.containsKey(votante)) {
                System.out.println("El votante ya ha emitido su voto.");
            } else {
                System.out.println("La contraseña es incorrecta.");
            }
        } else {
            System.out.println("El votante no está registrado.");
        }
    }

    public void contarVotos() {
        Map<String, Integer> contadorVotos = new HashMap<>();
        for (String candidato : candidatos) {
            contadorVotos.put(candidato, 0);
        }

        for (String voto : votos.values()) {
            contadorVotos.put(voto, contadorVotos.get(voto) + 1);
        }

        for (Map.Entry<String, Integer> entry : contadorVotos.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " votos");
        }
    }

    public static void main(String[] args) {
        SistemaVotacion sistema = new SistemaVotacion();

        sistema.registrarVotante("VotanteA", "contrasenaA");
        sistema.registrarVotante("VotanteB", "contrasenaB");
        sistema.registrarVotante("VotanteC", "contrasenaC");
        sistema.registrarVotante("VotanteD", "contrasenaD");
        sistema.agregarCandidato("Candidato1");
        sistema.agregarCandidato("Candidato2");
        sistema.agregarCandidato("Candidato3");
        sistema.agregarCandidato("Candidato4");

        sistema.realizarVoto("VotanteA", "contrasenaA", "Candidato1");
        sistema.realizarVoto("VotanteB", "contrasenaB", "Candidato2");
        sistema.realizarVoto("VotanteC", "contrasenaC", "Candidato3");
        sistema.realizarVoto("VotanteD", "contrasenaD", "Candidato4");
        sistema.realizarVoto("VotanteB", "contrasenaB", "Candidato2");

        sistema.contarVotos();
    }
}
