package it.unibo.boundaryWalk;

import org.json.JSONObject;

import javax.json.JsonReader;
import javax.json.spi.JsonProvider;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;
import java.io.Reader;

public class CommandDecoder implements Decoder.TextStream<JSONObject> {

    @Override
    public JSONObject decode(Reader reader) throws DecodeException, IOException {
        JsonProvider provider = JsonProvider.provider();
        JsonReader jsonReader = provider.createReader(reader);
        return new JSONObject(jsonReader.readObject().toString());
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }
}
