import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        String host = "127.0.0.1"; // Или "127.0.0.1"
        int port = 8080;
        String message = "Привет от клиента!";

        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println(message); // Отправляем сообщение на сервер

            String response = in.readLine(); // Читаем ответ от сервера

            System.out.println("Ответ сервера: " + response); // Исправлено: Выводим ответ сервера на консоль

        } catch (IOException e) {
            System.err.println("Ошибка при подключении к серверу: " + e.getMessage());
        }
    }
}
