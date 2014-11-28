package servlets.tareas;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.FileWriter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/insert" })
public class Insertar extends HttpServlet {
	
	private final static String DEFAULT_FILE_NAME = "ToDo_list.json";
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Task task = new Task();
		task.setTask(req.getParameter("task"));
		task.setContext(req.getParameter("context"));
		task.setProject(req.getParameter("project"));
		task.setPriority(Integer.parseInt(req.getParameter("priority")));
			
		
		
		String filename = DEFAULT_FILE_NAME;

		ToDoList toDoList = new ToDoList();
		Gson gson = new Gson();

		// Read the existing address book.
		try {
			toDoList = gson.fromJson(new FileReader(filename),
					ToDoList.class);
		} catch (FileNotFoundException e) {
			System.out.println(filename
					+ ": File not found.  Creating a new file.");
		}

		// Add a task.
		toDoList.addTask(task);

		// Write the new ToDo list back to disk.
		FileWriter output = new FileWriter(filename);
		output.write(gson.toJson(toDoList));
		output.close();
		ToDoList toDoList2 = gson.fromJson(new FileReader(filename),	ToDoList.class);

		

		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println(Buscar.principioHTML + Buscar.List(toDoList2,null,null) + Buscar.finHTML);
	}

}
