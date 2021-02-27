package it.unibo.boundaryWalk;

import org.json.JSONObject;

import javax.websocket.*;
import java.net.URI;

@ClientEndpoint
public class WebsocketCommunication implements ICommunicationStrategy{
    private final int port = 8091;
    private String hostname;
    private String URL;

    public WebsocketCommunication(String hostname){
        this.hostname = hostname;

        this.URL = "ws://"+this.hostname+":"+this.port+"/api/move";

        try{
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();

            container.connectToServer(this, new URI(this.URL));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    @Override
    public JSONObject sendRequest(String move, int time) {
        return null;
    }

}
