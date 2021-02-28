package it.unibo.boundaryWalk;

import org.json.JSONObject;

// import javax.websocket.*;
import java.net.URI;

//@ClientEndpoint
public class WebsocketCommunication implements ICommunicationStrategy{
    private int port;
    private String hostname;
    private String URL;

    public WebsocketCommunication(String hostname, int port){
        this.hostname = hostname;
        this.port = port;
        this.URL = "ws://"+this.hostname+":"+this.port+"/api/move";
    }

    @Override
    public JSONObject sendRequest(String move, int time) {
        return null;
    }
}
