package it.unibo.boundaryWalk;

import org.json.JSONObject;
import org.json.JSONWriter;

import javax.websocket.*;
import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.Semaphore;
import java.net.URI;

@ClientEndpoint
public class WebsocketCommunication implements ICommunicationStrategy{
    private int port;
    private String hostname;
    private String URL;
    private Session userSession;
    private String response;
    private final Semaphore semEvento = new Semaphore(0);

    public WebsocketCommunication(String hostname, int port){
        this.hostname = hostname;
        this.port = port;
        this.URL = "ws://"+this.hostname+":"+this.port+"/api/move";

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
        return null;
    }

    @OnOpen
    private void onOpen(Session session){
        this.userSession = session;
    }
}
