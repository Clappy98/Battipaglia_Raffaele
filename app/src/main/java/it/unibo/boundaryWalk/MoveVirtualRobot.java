package it.unibo.boundaryWalk;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.net.URI;

public class MoveVirtualRobot {
    private ICommunicationStrategy strategy;

    public MoveVirtualRobot() {
        strategy = new HTTPCommunication("localhost", 8090);
    }

    public MoveVirtualRobot(ICommunicationStrategy strategy){
        this.strategy = strategy;
    }

    protected boolean sendCmd(String move, int time)  {
        JSONObject response = strategy.sendRequest(move, time);

        if(response == null){
            return true;
        }

        try{
            return checkCollision(response);
        }catch (Exception e){
            return true;
        }
    }

    protected boolean checkCollision(JSONObject response) throws Exception {
        try{
            boolean collision = false;

            if( response.get("endmove") != null ) {
                collision = ! response.get("endmove").toString().equals("true");
                System.out.println("MoveVirtualRobot | checkCollision_simple collision=" + collision);
            }

            return collision;
        }catch(Exception e){
            System.out.println("MoveVirtualRobot | checkCollision_simple ERROR:" + e.getMessage());
            throw(e);
        }
    }

    /*
    * Le mosse ritornano "true" se c'è stata una collisione
    */
    public boolean moveForward(int duration)  { return sendCmd("moveForward", duration);  }
    public boolean moveBackward(int duration) { return sendCmd("moveBackward", duration); }
    public boolean moveLeft(int duration)     { return sendCmd("turnLeft", duration);     }
    public boolean moveRight(int duration)    { return sendCmd("turnRight", duration);    }
    public boolean moveStop(int duration)     { return sendCmd("alarm", duration);        }
}


