package com.example.cq2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.lang.reflect.Type;
import java.net.Socket;
import java.net.UnknownHostException;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class Server {	
	private static InputStream in;
	private static OutputStream out;
	private static Socket socket;
	private static Gson gson = new Gson();
	
	private static final int port = 13337;
	//Subject to change:
	private static final String ip = "192.168.0.199";
	
	
	public static void connect() throws UnknownHostException, IOException{
		socket = new Socket(ip, port);
		in = socket.getInputStream();
		out = socket.getOutputStream();
	}
	
	public static void write(String json) throws IOException{
		DataOutputStream dOut = new DataOutputStream(out);
		dOut.writeUTF(json);
	}
	
	public static Response read() throws IOException {
		DataInputStream dIn = new DataInputStream(in);
		String responseJson = dIn.readUTF();
		Type listType = new TypeToken<Response>(){}.getType(); 
		Response response = gson.fromJson(responseJson, listType);

		return response;
	}
	
	public static void write(Message message) throws IOException {
		String jsonString = gson.toJson(message);
		DataOutputStream dOut = new DataOutputStream(out);
		dOut.writeUTF(jsonString);
	}
	
	public static void disconnect() {
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				// Ignored
			}
			in = null;
		}
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
				// Ignored
			}
			out = null;
		}
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				// Ignored
			}
			socket = null;
		}
	}
	
}
