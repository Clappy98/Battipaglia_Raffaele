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

public class HTTPCommunication implements ICommunicationStrategy{
    private final int port = 8090;
    private String hostname;
    private String URL;

    public HTTPCommunication(String hostname){
        this.hostname = hostname;

        this.URL = "http://"+this.hostname+":"+this.port+"/api/move";
    }


    @Override
    public JSONObject sendRequest(String move, int time) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            // costruisco la stringa JSON
            String json         = "{\"robotmove\":\"" + move + "\" , \"time\": " + time + "}";

            // la rendo accettabile per il RequestBuilder
            StringEntity entity = new StringEntity(json);

            // costruisco un messaggio POST
            HttpUriRequest httppost = RequestBuilder.post()
                    .setUri(new URI(URL))
                    .setHeader("Content-Type", "application/json")
                    .setHeader("Accept", "application/json")
                    .setEntity(entity)
                    .build();

            // invio e attendo la risposta
            CloseableHttpResponse response = httpclient.execute(httppost);

            // estraggo il JSONObject
            String jsonStr = EntityUtils.toString(response.getEntity());

            return new JSONObject(jsonStr);
        } catch(Exception e){
            System.out.println("ERROR:" + e.getMessage());
            return null;
        }
    }
}
