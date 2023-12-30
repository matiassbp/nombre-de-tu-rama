
//main java
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Cliente {

    public static String host = "localhost";
    public static int port = 1234;

    public static void main(String[] args) throws Exception {

        String input = "";
        do {
            Socket clientSocket = new Socket(host, port);

            System.out.println("------------------------Cachipun------------------------");
            System.out.println(
                    "Reglas:\n a) Piedra gana a Tijera\n b) Tijera gana a papel\n c) Papel gana a piedra\n d) Si ambos jugadores eligen la misma opción, es un empate\n");
            System.out.println("Opciones:\nTIPEA\n '1' para piedra\n '2' para papel\n '3' para tijera\n");

            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String response1 = inFromServer.readLine();
            System.out.println(response1);

            System.out.println("Ingrese su opción: ");
            input = inFromUser.readLine();

            // si input es 1,2 o 3 que vuelva a escribir el usuario una respuesta
            while (!input.equals("1") && !input.equals("2") && !input.equals("3")) {
                System.out.println("Ingrese su opción: ");
                input = inFromUser.readLine();
            }

            outToServer.writeBytes(input + "\n");

            String response2 = inFromServer.readLine();

            //clear en consola
            System.out.print("\033[H\033[2J");

            System.out.println("Respuesta: " + response2);
            clientSocket.close();

            //sleep
            Thread.sleep(2000);


        } while (!input.equals("q"));

    }
}