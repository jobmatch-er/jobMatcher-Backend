package de.wecodeit.jobmatcher;

import de.jakobniklas.util.Exceptions;
import de.jakobniklas.util.Log;
import de.wecodeit.jobmatcher.registry.RequestRegistry;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class APIConnection extends WebSocketServer
{
    private WebSocket apiConnection;
    private boolean connected = false;
    private RequestRegistry requestRegistry;

    public APIConnection(RequestRegistry requestRegistry)
    {
        this.requestRegistry = requestRegistry;
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake)
    {
        if(!connected)
        {
            this.apiConnection = webSocket;


            Log.print("Connection", "A connection was successfully established");

            connected = true;
        }
        else
        {
            webSocket.close(0, "Another connection is already established");

            Log.print("Connection", "Another connection was tried to be  established, but rejected");
        }
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b)
    {
        Log.print("Connection", "Closed");

        connected = false;

        webSocket = null;
    }

    @Override
    public void onMessage(WebSocket webSocket, String s)
    {
        String respond = requestRegistry.handle(s);

        webSocket.send(respond);

        Log.print("Connection", "Handled request '" + s + "' and responded with '" + respond + "'");
    }

    @Override
    public void onError(WebSocket webSocket, Exception e)
    {
        Exceptions.handle(e);
    }

    @Override
    public void onStart()
    {

    }
}
