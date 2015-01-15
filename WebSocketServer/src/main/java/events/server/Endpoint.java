package events.server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.server.ServerEndpoint;





import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

@ServerEndpoint(value = "/todo")
public class Endpoint {
	private final static String DEFAULT_FILE_NAME = "ToDo_list.json";
	private Logger logger = Logger.getLogger(this.getClass().getName());

	@OnOpen
	public void onOpen(Session session) {
		logger.info("Connected ... " + session.getId());
	}

	@OnMessage
	public String onMessage(String message, Session session) {
		String op=message.substring(0,3);
		String resto=message.substring(3, message.length());
		ToDoList toDoList=new ToDoList();
	
		Gson gson = new Gson();
		String respuesta="";
		List<Task> lista;
		switch (op) {
		case "add": //agregar tarea
			try {
				toDoList = gson.fromJson(new FileReader(DEFAULT_FILE_NAME),
						ToDoList.class);
			} catch (JsonSyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JsonIOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				
				
				// Add a task.
				toDoList.addTask((new Gson()).fromJson(resto, Task.class));
				// Write the new ToDo list back to disk.	
				FileWriter output = new FileWriter(DEFAULT_FILE_NAME);
				output.write(gson.toJson(toDoList));
				output.close();
				respuesta="addOK";
			} catch (IOException e) {
				e.printStackTrace();
			}	
			break;
		
		case "bus":	//buscar tarea
			// Read the existing address book.
			try {
				toDoList = gson.fromJson(new FileReader(DEFAULT_FILE_NAME),
						ToDoList.class);
			} catch (FileNotFoundException e) {
				System.out.println(DEFAULT_FILE_NAME + ": File not found.");
			}
			lista = toDoList.getList();
			boolean encontrado=false;
			//String tarea=(new Gson()).fromJson(resto, Task.class).getTask();
			for (int i = 0; i < lista.size()&&!encontrado; i++) {
				if (lista.get(i).getTask().equals(resto)) {
					respuesta="bus"+(new Gson().toJson(lista.get(i)));
					encontrado=true;
				}
			}
			
			break;
		case "del":	//borrar tarea
			try {
				toDoList = gson.fromJson(new FileReader(DEFAULT_FILE_NAME),
						ToDoList.class);
			} catch (FileNotFoundException e) {
				System.out.println(DEFAULT_FILE_NAME + ": File not found.");
			}
			lista = toDoList.getList();
			for (int i = 0; i < lista.size(); i++) {
				if (lista.get(i).getTask().equals(resto)) {
					lista.remove(i);
					FileWriter output;
					try {
						output = new FileWriter(DEFAULT_FILE_NAME);
						output.write(gson.toJson(toDoList));
						output.close();
						respuesta="delOK";

					} catch (IOException e) {

					}
				}
			}
		
			break;
		
		case "get":	//buscar tarea
			
			// Read the existing address book.
			try {
				toDoList = gson.fromJson(new FileReader(DEFAULT_FILE_NAME),
						ToDoList.class);
			} catch (FileNotFoundException e) {
				System.out.println(DEFAULT_FILE_NAME + ": File not found.");
			}
			respuesta= "get"+(new Gson().toJson(toDoList));
			
		}
		return respuesta;
	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		logger.info(String.format("Session %s closed because of %s",
				session.getId(), closeReason));
	}
}
