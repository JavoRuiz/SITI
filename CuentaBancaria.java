// Importación de las clases necesarias
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

// Definición de la clase CuentaBancaria
public class CuentaBancaria {
    // Atributos de la clase
    private String titular; // Nombre del titular de la cuenta
    private double saldo; // Saldo actual de la cuenta
    private ArrayList<String> historial; // Lista para almacenar el historial de transacciones

    // Constructor de la clase
    public CuentaBancaria(String titular, double saldoInicial) {
        // Inicializa los atributos con los valores proporcionados
        this.titular = titular;
        this.saldo = saldoInicial;
        this.historial = new ArrayList<>();
        this.historial.add("Saldo inicial: " + saldoInicial);
    }

    // Métodos para obtener el titular y el saldo
    public String getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    // Método para realizar un depósito en la cuenta
    public void depositar(double monto) {
        // Aumenta el saldo con el monto depositado
        saldo += monto;
        // Registra la transacción en el historial y muestra un mensaje
        historial.add("Deposito de: " + monto + ". Saldo actual: " + saldo + ". Titular: " + titular);
        System.out.println("Deposito exitoso. Saldo actual: " + saldo);
    }

    // Método para realizar un retiro de la cuenta
    public void retirar(double monto) {
        // Verifica si hay saldo suficiente para el retiro
        if (monto <= saldo) {
            // Reduce el saldo con el monto retirado
            saldo -= monto;
            // Registra la transacción en el historial y muestra un mensaje
            historial.add("Retiro de: " + monto + ". Saldo actual: " + saldo + ". Titular: " + titular);
            System.out.println("Retiro exitoso. Saldo actual: " + saldo);
        } else {
            // Registra el intento de retiro sin saldo suficiente en el historial y muestra un mensaje
            historial.add("Intento de retiro de: " + monto + ". Saldo insuficiente. Saldo actual: " + saldo + ". Titular: " + titular);
            System.out.println("Saldo insuficiente para realizar el retiro de " + monto);
        }
    }

    // Método para obtener el historial de transacciones
    public ArrayList<String> obtenerHistorial() {
        return historial;
    }

    // Método para generar un hash seguro de un monto (simulando un depósito seguro)
    public String obtenerHash(double monto) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(Double.toString(monto).getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            // Convierte el hash en una cadena hexadecimal
            for (byte b : hash) {
                String hex = String.format("%02x", b);
                hexString.append(hex);
            }

            return hexString.toString(); // Retorna el hash en formato hexadecimal
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Algoritmo de encriptación no encontrado.");
        }
        return null;
    }

    // Método para realizar un depósito seguro con registro del hash y saldo previo
    public void depositarSeguro(double monto) {
        double saldoPrevio = saldo; // Almacena el saldo previo a la transacción segura
        String hash = obtenerHash(monto); // Obtiene el hash del monto
        if (hash != null) {
            // Registra la transacción segura en el historial y muestra un mensaje
            historial.add("Deposito seguro de: " + monto + ". Hash: " + hash + ". \nSaldo previo: " + saldoPrevio);
            System.out.println("Deposito seguro exitoso. Hash: " + hash + ". \nSaldo previo: " + saldoPrevio);
        } else {
            System.out.println("Error al realizar el depósito seguro.");
        }
    }

    // Método principal (main) para realizar pruebas con la clase CuentaBancaria
    public static void main(String[] args) {
        // Creación de una instancia de CuentaBancaria con un titular y un saldo inicial
        CuentaBancaria cuenta = new CuentaBancaria("Fernando Rodriguez", 1500);

        // Realización de transacciones (depósitos, retiros, depósito seguro)
        cuenta.depositarSeguro(0); // Depósito seguro simulado con un monto de 0
        cuenta.retirar(300); // Retiro de 200 unidades de saldo
        cuenta.depositar(100); // Depósito de 100 unidades de saldo
        cuenta.retirar(2000); // Intento de retiro de 1500 unidades (que debería mostrar un mensaje de saldo insuficiente)

        // Mostrar el historial de transacciones de la cuenta
        ArrayList<String> historial = cuenta.obtenerHistorial();
        System.out.println("\nHistorial de transacciones:");
        for (String transaccion : historial) {
            System.out.println(transaccion);
        }
    }
}
