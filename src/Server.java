import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        int port = 8080; // Выбираем порт

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен на порту: " + port);

            while (true) { // Бесконечный цикл для приема нескольких соединений
                try {
                    Socket clientSocket = serverSocket.accept(); // Ждем подключения
                    System.out.println("Новое подключение принято");

                    // Создаем новый поток для обработки каждого клиента
                    new Thread(() -> handleClient(clientSocket)).start();

                } catch (IOException e) {
                    System.err.println("Ошибка при подключении клиента: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.err.println("Не удалось запустить сервер на порту " + port + ": " + e.getMessage());
        }
    }


    private static void handleClient(Socket clientSocket) {
        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            String name = in.readLine(); // Читаем строку от клиента

            if (name != null) {
                System.out.println("Получено сообщение: " + name + " от клиента с портом: " + clientSocket.getPort());

                out.println(String.format("Привет, %s! Твой порт: %d", name, clientSocket.getPort())); // Отправляем ответ клиенту
            } else {
                System.out.println("Клиент отключился.");
            }

        } catch (IOException e) {
            System.err.println("Ошибка при обработке клиента: " + e.getMessage());
        } finally {
            try {
                clientSocket.close(); // Закрываем сокет
            } catch (IOException e) {
                System.err.println("Ошибка при закрытии сокета: " + e.getMessage());
            }
        }
    }
}