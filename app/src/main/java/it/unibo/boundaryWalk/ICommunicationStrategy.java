package it.unibo.boundaryWalk;

import org.json.JSONObject;

public interface ICommunicationStrategy {
    public JSONObject sendRequest(String move, int time);
}
