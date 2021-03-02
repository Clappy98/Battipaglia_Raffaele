package it.unibo.boundaryWalk;

import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.spi.JsonProvider;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;
import java.io.Writer;

public class CommandEncoder implements Encoder.TextStream<String> {
    @Override
    public void encode(String object, Writer writer) throws EncodeException, IOException {
        // stringa in input "robotmoveCommand-time"
        String[] commands = object.split("-");

        JsonProvider provider = JsonProvider.provider();

        // costruisce il comando in formato JSON
        JsonObject jsonCommand = provider.createObjectBuilder()
                .add("robotmove", commands[0])
                .add("time", commands[1])
                .build();

        // scrive il dizionario JSON su uno stream
        try (JsonWriter jsonWriter = provider.createWriter(writer)) {
            jsonWriter.write(jsonCommand);
        }

        System.out.println("Inviato mex: " + jsonCommand);
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }
}
