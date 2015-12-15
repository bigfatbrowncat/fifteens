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
import bfbc.fifteens.core.FifteensField;

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
    
    private void hit(int num) {
    	synchronized (Starter.game) {
	    	Direction dir = Starter.game.findWayAndMove(num, true);
			if (dir != null) {
				UpdateRequest uReq = new UpdateRequest(new MoveUpdateRequest(num, dir));
				broadcastString(uReq.toJson());
			}
    	}
    }
    
    private void shuffle() {
        // Shuffling
        int iZero = 0;
        int k = 0;
    	int dPrevOpp = -1;
    	
    	// We are going to move pieces 60 times to shuffle the field
        while (k < 60) {
        	
        	// Let's calculate the step weight based on the distance between
        	// the field state after the step and the solution.
        	// Our target is to maximize such distance -- to make a position
        	// that is as far from the solution as possible 
        	double[] weights = new double[4];
        	int[] indices = new int[4];
        	for (int d = 0; d < 4; d++) {
            	Direction dir = Direction.fromId(d);
            	
            	Integer index = Starter.game.getIndexForDirection(dir, iZero);
            	if (index != null) {
            		FifteensField fCopy = Starter.game.cloneField();
            		fCopy.findWayAndMove(fCopy.getNum(index), true);
	            	double newWeight = fCopy.distanceFromSolution();
	            	weights[d] = newWeight; 
	            	indices[d] = index;
            	}
        	}
        	
        	// Now, as we have calculated the weights, let's decide the
        	// direction with a weighted random
        	double weightMin = weights[0];
        	for (int d = 1; d < 4; d++) {
        		if (weightMin > weights[d]) {
        			weightMin = weights[d];
        		}
        	}

        	boolean chosen = false;	// This flag is for avoiding forward-back movement
        	do {
            	double rnd = GlobalServices.getRandom().nextDouble();
            	float wSum = 0;
            	for (int d = 0; d < 4; d++) {
            		weights[d] -= weightMin;
            		wSum += weights[d];
            	}
            	rnd *= wSum;
            	float t = 0;
            	for (int d = 0; d < 4; d++) {
            		t += weights[d];
            		if (rnd <= t) {
            			if (dPrevOpp != d) {
	            			iZero = indices[d];
	            			dPrevOpp = (d + 2) % 4;	// Moving the opposite side to the previous move - we should avoid it
	            			chosen = true;
	            			break;
            			} else {
            				// This case means we are trying to step back
	            			chosen = false;
	            			break;
            			}
            		}
            	}
        	} while (!chosen);
        	
        	// Moving actually
			hit(Starter.game.getNum(iZero));
			// Playing an animation to the user -- let him see our movement
			waitALittle(150);
			k++;
        }
    }
    
    @OnWebSocketConnect
    public void connected(Session session) {
        sessions.add(session);
    	synchronized (Starter.game) {
	        if (Starter.game.isStarted()) {
		        UpdateRequest uReq = new UpdateRequest(Starter.game.createInitUpdateRequest());
		        try {
					session.getRemote().sendString(uReq.toJson());
				} catch (IOException e) {
					e.printStackTrace();
					// TODO Maybe lags here
				}
	        }
    	}
    }

    @OnWebSocketClose
    public void closed(Session session, int statusCode, String reason) {
        sessions.remove(session);
    }
    
    private void waitALittle(int pause) {
    	try {
			Thread.sleep(pause);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    @OnWebSocketMessage
    public void message(Session session, String message) throws IOException {
    	ChangeRequest req = ChangeRequest.fromJson(message);
    	
    	if (req.getCommand().equals("hit")) {
    		int num = req.getNum();
    		hit(num);
    	} else if (req.getCommand().equals("newgame")) {
    		synchronized (Starter.game) {
        		Starter.game.startNew();
    		
	            UpdateRequest uReq = new UpdateRequest(Starter.game.createInitUpdateRequest());
	            broadcastString(uReq.toJson());
	            waitALittle(500);
	            
	            shuffle();
    		}
    	}
    }

}
