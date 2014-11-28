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
@WebServlet(urlPatterns = { "/buscar" })
public class Buscar extends HttpServlet {
	
	private final static String DEFAULT_FILE_NAME = "ToDo_list.json";
	
	protected final static String principioHTML="<html>"
		+"<head>"
		+"<title>Tareas</title>"
		+"</head>"
		+"<body>"
		+"	<h1>Tareas</h1>"
		+"	<table>"
		+"      <tr>"
		+"			<td valign=\"top\">"
		+"				<table>"
		+"					<tr>"
		+"							<form method=\"get\" action=\"buscar\">"
		+"								<fieldset>"
		+"									<legend>Buscar Tarea:</legend>"
		+"										<input type=\"text\" name=\"text\">"
		+"										<select name=\"opcion\">"
		+"											 <option value=\"task\">Task</option>"
		+"										 	<option value=\"context\">Context</option>"
		+"										 	<option value=\"project\">Project</option>"
		+"											 <option value=\"priority\">Priority</option>"
		+"										</select>"
		+"										<input type=\"submit\" value=\"Buscar\">"
		+"								</fieldset>"
		+"							</form>"	
		+"							<form method=\"get\" action=\"insert\">"
		+"								<fieldset>"
		+"									<legend>Introducir Tarea:</legend>"
		+"										Task: <br><input type=\"text\" name=\"task\"><br>" 
		+"										Context: <br><input type=\"text\" name=\"context\"><br>" 
		+"										Project: <br><input type=\"text\" name=\"project\"><br> "
		+"										Priority: <br><input type=\"number\" name=\"priority\"><br>" 
		+"										<input type=\"submit\" value=\"Introducir\">"
		+"								</fieldset>"
		+"							</form>"
		+"					</tr>"
		+"				</table>"
		+"			</td>"
		+"			<td>";
protected final static String finHTML= "</td>"
+"				</tr>"
+"			</table>"
+"		</body>"
+"</html>";

			
			
			
			
	
	protected static String List(ToDoList toDoList, String texto, String opcion) {
		String resultado="";
		if(texto==null || opcion==null){
			for (Task task : toDoList.getList()) {
					resultado += "Task: " + task.getTask()+ "<br>";
					resultado +="  Context: " + task.getContext()+ "<br>";
					resultado +="  Project: " + task.getProject()+ "<br>";
					resultado +="  Priority: " + task.getPriority()+ "<br><br>";
			}
		}else{
			switch(opcion){
				case "task":
					for (Task task : toDoList.getList()) {
						if(task.getTask().contains(texto)){
							resultado += "Task: " + task.getTask()+ "<br>";
							resultado +="  Context: " + task.getContext()+ "<br>";
							resultado +="  Project: " + task.getProject()+ "<br>";
							resultado +="  Priority: " + task.getPriority()+ "<br><br>";
					
						}
					}
					break;
				case "context":
					for (Task task : toDoList.getList()) {
						if(task.getContext().contains(texto)){
							resultado += "Task: " + task.getTask()+ "<br>";
							resultado +="  Context: " + task.getContext()+ "<br>";
							resultado +="  Project: " + task.getProject()+ "<br>";
							resultado +="  Priority: " + task.getPriority()+ "<br><br>";
						}
					}
					break;
				case "project":
					for (Task task : toDoList.getList()) {
						if(task.getProject().contains(texto)){
							resultado += "Task: " + task.getTask()+ "<br>";
							resultado +="  Context: " + task.getContext()+ "<br>";
							resultado +="  Project: " + task.getProject()+ "<br>";
							resultado +="  Priority: " + task.getPriority()+ "<br><br>";
						}
					}
					break;
				case "priority":
					try{
						int numero= Integer.parseInt(texto);
						for (Task task : toDoList.getList()) {
							if(task.getPriority()==numero){
								resultado += "Task: " + task.getTask()+ "<br>";
								resultado +="  Context: " + task.getContext()+ "<br>";
								resultado +="  Project: " + task.getProject()+ "<br>";
								resultado +="  Priority: " + task.getPriority()+ "<br><br>";
							}
						}
					}catch(NumberFormatException e){
						return List(toDoList,null,null);
					}
					break;
			}
		}
		return resultado;
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		String opcion=req.getParameter("opcion");
		String texto=req.getParameter("text");		
		String filename = DEFAULT_FILE_NAME;
		ToDoList toDoList = new ToDoList();
		Gson gson = new Gson();
		ToDoList toDoList2 = gson.fromJson(new FileReader(filename),	ToDoList.class);
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println(principioHTML + List(toDoList2,texto,opcion)+finHTML);
	}
	
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
