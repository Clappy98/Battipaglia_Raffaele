package it.unibo.boundaryWalk;

import org.json.JSONObject;
import org.json.JSONWriter;

import javax.websocket.*;
import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.Semaphore;
import java.net.URI;

//@ClientEndpoint(encoders = {CommandEncoder.class}, decoders = {CommandDecoder.class})
@ClientEndpoint
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
        this.URL = "ws://"+this.hostname+":"+this.port+"/";

        try{
            this._connectToServer(this.URL);

            System.out.println("Connected with the server at " + this.URL);
        }catch(Exception e){
            System.out.println("Error with server connection: " + e.getMessage());
        }
    }

    private void _connectToServer(String url) throws RuntimeException{
        try{
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI(url));
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public JSONObject sendRequest(String move, int time) {
        // {"robotmove":"move", "time":time}
        String command = "{\"robotmove\":\"" + move + "\", \"time\":" + time + "}";

        // attende la creazione della sessione
        if(this.userSession == null){
            try{
                semEvento.acquire();
            }catch(Exception e){}
        }

        System.out.println("Invio comando: " + command);

        try{
            this.userSession.getBasicRemote().sendText(command);
        }catch(Exception e){
            System.out.println("Invio comando fallito: " + e.getMessage());
        }

        // attende una risposta
        try{
            semEvento.acquire();
        }catch(InterruptedException e){}

        return this.response;
    }

    @OnOpen
    public void onOpen(Session session){
        System.out.println("Sessione aperta");

        // bisogna attendere che la sessione venga creata
        if(semEvento.getQueueLength() > 0){
            semEvento.release();
        }
        this.userSession = session;
    }

    @OnMessage
    public void onMessage(String response){
        System.out.println("Arrivato mex: " + response);
        if(response.contains("sonarName") || response.contains("collision")){
            return;
        }
        this.response = new JSONObject(response);
        semEvento.release();
    }
}
