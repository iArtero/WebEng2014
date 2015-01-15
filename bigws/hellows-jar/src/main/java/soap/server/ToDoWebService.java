package soap.server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

@WebService
public class ToDoWebService {

	private final static String DEFAULT_FILE_NAME = "ToDo_list.json";

	@WebMethod()
	public void addToDo(String toDo, String context, String project, int priority) {
		Task task = new Task();
		task.setTask(toDo);
		task.setContext(context);
		task.setProject(project);
		task.setPriority(priority);
		ToDoList toDoList = new ToDoList();
		Gson gson = new Gson();
		// Read the existing address book.
		try {
			toDoList = gson.fromJson(new FileReader(DEFAULT_FILE_NAME), ToDoList.class);
		} catch (FileNotFoundException e) {
			System.out.println(DEFAULT_FILE_NAME + ": File not found.");
		}
		// Add a task.
		toDoList.addTask(task);
		// Write the new ToDo list back to disk.
		try {
			FileWriter output = new FileWriter(DEFAULT_FILE_NAME);
			output.write(gson.toJson(toDoList));
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@WebMethod()
	public List<Task> listToDo() {
		Gson gson = new Gson();
		try {
			return (gson.fromJson(new FileReader(DEFAULT_FILE_NAME),	ToDoList.class)).getList();
			
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			return null;
		}
	}
	@WebMethod()
	public void removeToDo(String toDo) {
		ToDoList toDoList = new ToDoList();
		Gson gson = new Gson();
		// Read the existing address book.
		try {
			toDoList = gson.fromJson(new FileReader(DEFAULT_FILE_NAME), ToDoList.class);
		} catch (FileNotFoundException e) {
			System.out.println(DEFAULT_FILE_NAME + ": File not found.");
		}
		toDoList.removeTask(toDo);
		try {
			FileWriter output = new FileWriter(DEFAULT_FILE_NAME);
			output.write(gson.toJson(toDoList));
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
