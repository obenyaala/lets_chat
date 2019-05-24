import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@WebSocket
public class WebSocketHandler {
    private static JsonParser jsonParser = new JsonParser();
    private static Collection<Session> sessions = new ArrayList<>();

    @OnWebSocketConnect
    public void onConnect(Session session) throws Exception {
        System.out.println("new user joined in.");
        sessions.add(session);
    }

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        sessions.remove(session);
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        if (jsonParser.parse(message).isJsonObject()){
            for (Session user : sessions) {
                user.getRemote().sendString(message);
            }
        }else {
            session.getRemote().sendString("{\"error\": \"bad JSON request\"}");
        }
    }
}
