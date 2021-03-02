package it.unibo.boundaryWalk;

import org.json.JSONObject;
import org.json.JSONWriter;

import javax.websocket.*;
import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.Semaphore;
import java.net.URI;

@ClientEndpoint(encoders = {CommandEncoder.class}, decoders = {CommandDecoder.class})
public class WebsocketCommunication implements ICommunicationStrategy{
    private int port;
    private String hostname;
    private String URL;
    private Session userSession;
    private JSONObject response;
    private final Semaphore semEvento = new Semaphore(0);

    public WebsocketCommunication(String hostname, int port){
        this.hostname = hostname;
        this.port = port;
        this.URL = "ws://"+this.hostname+":"+this.port;

        try{
            _connectToServer(this.URL);
        }catch(Exception e){}
    }

    private void _connectToServer(String url) throws RuntimeException{
        try{
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();

            container.connectToServer(this, new URI(url));
        }catch(Exception e){
            throw new RuntimeException();
        }
    }

    @Override
    public JSONObject sendRequest(String move, int time) {
        // l'encoder che ho scritto si aspetta una stringa del
        // tipo "robotmoveCommand-time"
        String command = move + "-" + time;

        try{
            this.userSession.getBasicRemote().sendObject(command);
        }catch(Exception e){}

        // attende una risposta
        try{
            semEvento.acquire();
        }catch(InterruptedException e){}

        return this.response;
    }

    @OnOpen
    private void onOpen(Session session){
        System.out.println("Sessione aperta");
        this.userSession = session;
    }

    @OnMessage
    private void onMessage(JSONObject response){
        System.out.println("Arrivato mex: " + response);
        this.response = response;
        semEvento.release();
    }
}
