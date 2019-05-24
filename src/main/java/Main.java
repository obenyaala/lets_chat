import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        port(80);
        staticFileLocation("/public"); //index.html is served at localhost:4567 (default port)
        webSocket("/chat", WebSocketHandler.class);
        init();
        System.out.println("server is set.");
    }
}
