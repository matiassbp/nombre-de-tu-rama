import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {

    public static int port = 1234;

    public static void main(String[] args) throws Exception {
        ServerSocket socket = new ServerSocket(port);
        while (true) {

            try {

                String resClient_1 = "";
                String resClient_2 = "";
                String inputClient_1;
                String inputClient_2;

                System.out.println("\nServer running in " + socket.getLocalPort());

                // Player one
                Socket client_1 = socket.accept();
                if (client_1.isConnected()) {
                    System.out.println("\nJugador 1 (" + (client_1.getLocalAddress().toString()).substring(1) + ":" + client_1.getLocalPort() + ") se unio ...");
                }
                DataOutputStream outClient_1 = new DataOutputStream(client_1.getOutputStream());
                BufferedReader inClient_1 = new BufferedReader(new InputStreamReader(client_1.getInputStream()));

                // Player two
                Socket client_2 = socket.accept();
                if (client_2.isConnected()) {
                    System.out.println("Jugador 2(" + (client_2.getLocalAddress().toString()).substring(1) + ":" + client_1.getLocalPort() + ") se unio...");
                }
                DataOutputStream outClient_2 = new DataOutputStream(client_2.getOutputStream());
                BufferedReader inClient_2 = new BufferedReader(new InputStreamReader(client_2.getInputStream()));

                // mostrar contra quien juega
                outClient_1.writeBytes("Juegas contra: " + (client_2.getLocalAddress().toString()).substring(1) + ":"
                        + client_2.getLocalPort() + "\n");
                outClient_2.writeBytes("Juegas contra: " + (client_1.getLocalAddress().toString()).substring(1) + ":"
                        + client_1.getLocalPort() + "\n");

                // Get client inputs
                inputClient_1 = inClient_1.readLine();
                inputClient_2 = inClient_2.readLine();

                // 1 para piedra 2 para papel 3 para tijera
                if (inputClient_1.equals("1") && inputClient_2.equals("3")
                        || inputClient_1.equals("2") && inputClient_2.equals("1")
                        || inputClient_1.equals("3") && inputClient_2.equals("2")) { // piedra gana a tijera
                    resClient_1 = "Jugador 1: Ganaste";
                    resClient_2 = "Jugador 2: Perdiste";
                    System.out.println("Jugador 1 gana");
                } else if (inputClient_1.equals(inputClient_2)) {
                    resClient_1 = "Jugador 1: Empate";
                    resClient_2 = "Jugador 2: Empate";
                    System.out.println("Empate");
                } else {
                    resClient_1 = "Jugador 1: Perdiste";
                    resClient_2 = "Jugador 2: Ganaste";
                    System.out.println("Jugador 2 gana");
                }

                // Send results to clients
                outClient_1.writeBytes(resClient_1.toUpperCase());
                outClient_2.writeBytes(resClient_2.toUpperCase());
                client_1.close();
                client_2.close();

                System.out.println("\nEsperando jugadores nuevos\n");

            } catch (SocketException se) {
                System.out.println("Usuario desconectado");

            } catch (IOException e) {
                System.out.println(e);
                socket.close();
            }
        }
    }
}
