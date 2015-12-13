package bfbc.fifteens;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import bfbc.fifteens.core.Direction;

@WebSocket
public class FifteensWebSocket {

    // Store sessions if you want to, for example, broadcast a message to all users
    private static final Queue<Session> sessions = new ConcurrentLinkedQueue<>();
    
    private void broadcastString(String str) {
    	for (Session s : sessions) {
    		try {
				s.getRemote().sendString(str);
			} catch (IOException e) {
				e.printStackTrace();
				// TODO Maybe lags here
			}
    	}
    }
    
    @OnWebSocketConnect
    public void connected(Session session) {
        sessions.add(session);
        UpdateRequest uReq = new UpdateRequest(Starter.field.createInitUpdateRequest());
        try {
			session.getRemote().sendString(uReq.toJson());
		} catch (IOException e) {
			e.printStackTrace();
			// TODO Maybe lags here
		}
    }

    @OnWebSocketClose
    public void closed(Session session, int statusCode, String reason) {
        sessions.remove(session);
    }
    
    @OnWebSocketMessage
    public void message(Session session, String message) throws IOException {
    	ChangeRequest req = ChangeRequest.fromJson(message);
    	
    	if (req.getCommand().equals("hit")) {
    		int index = req.getIndex();
    		Direction dir = Starter.field.findWayAndMove(index, true);
    		if (dir != null) {
    			UpdateRequest uReq = new UpdateRequest(new MoveUpdateRequest(index, dir));
    			broadcastString(uReq.toJson());
    		}
    	} else if (req.getCommand().equals("newgame")) {
    		Starter.field.init();
            UpdateRequest uReq = new UpdateRequest(Starter.field.createInitUpdateRequest());
            broadcastString(uReq.toJson());
    	}
    }

}
