package com.example.androidhttpserver;

import android.content.Context;
import android.util.Log;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response.Status;

import java.io.IOException;
import java.io.InputStream;
public class AndroidHttpServer extends NanoHTTPD {
    private Context context;
    private static final String TAG = "AndroidHttpServer";
    public AndroidHttpServer(int port, Context context) {
        super(port);
        this.context = context;
    }

    @Override
    public Response serve(IHTTPSession session){
        try {
            InputStream inputStream = context.getAssets().open("html/index.html");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String response = new String(buffer, "UTF-8");
            return newFixedLengthResponse(Response.Status.OK, "text/html", response);
        } catch (IOException ioe) {
            Log.e(TAG, "Error serving the HTML page", ioe);
            return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, "text/plain", "Internal Server Error");
        }
    }
//    {
//        if (session.getUri().equals("/")) {
//            try {
//                InputStream inputStream = context.getAssets().open("html/index.html");
//                int size = inputStream.available();
//                byte[] buffer = new byte[size];
//                inputStream.read(buffer);
//                inputStream.close();
//                String response = new String(buffer, "UTF-8");
//
//                return NanoHTTPD.newFixedLengthResponse(Status.OK, "text/html", response);
//            } catch (IOException ioe) {
//                Log.e(TAG, "Could not load page", ioe);
//                return NanoHTTPD.newFixedLengthResponse(Status.INTERNAL_ERROR, "text/plain", "Internal server error: could not load page");
//            }
//        }
//
//        return NanoHTTPD.newFixedLengthResponse(Status.NOT_FOUND, "text/plain", "Not found");
//    }
    public void startServer() throws IOException {
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        Log.d(TAG, "Server has been started");
    }

//    public static void main(String[] args) {
//        try {
//            new AndroidHttpServer(8080).start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
//            System.out.println("Server has been started");
//        } catch (IOException ioe) {
//            System.err.println("Couldn't start server:\n" + ioe);
//        }
//    }
}