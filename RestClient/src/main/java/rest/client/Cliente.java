package rest.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/Cliente" })
public class Cliente extends HttpServlet {

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Velocity.setProperty("file.resource.loader.path", "./src/main/webapp");
		Velocity.init();

		Client cli = ClientBuilder.newClient();

		String resultado = "";
		String exito = "";
		if (req.getParameter("opcion").equals("agregar")) {
			Task toDo = new Task();
			toDo.setTask(req.getParameter("task"));
			toDo.setContext(req.getParameter("context"));
			toDo.setProject(req.getParameter("project"));
			toDo.setPriority(Integer.parseInt(req.getParameter("priority")));
			Response respu = cli.target("http://localhost:8080/list")
					.request(MediaType.APPLICATION_JSON)
					.post(Entity.entity(toDo, MediaType.APPLICATION_JSON));

			if (respu.getStatus() == 201) {
				exito = "SU OPERACIÓN SE HA REALIZADO CORRECTAMENTE!<br>";
			} else {
				exito = "ERROR AL AGREGAR SU TAREA <br>";
			}
			respu = cli.target("http://localhost:8080/list")
					.request(MediaType.APPLICATION_JSON).get();
			ToDoList toDoList = respu.readEntity(ToDoList.class);
			if (toDoList != null) {
				for (Task task : toDoList.getList()) {
					resultado += "Task:   " + task.getTask() + "<br>";
					if (task.getContext() != null)
						resultado += "  Context: " + task.getContext() + "<br>";
					if (task.getProject() != null)
						resultado += "  Project: " + task.getProject() + "<br>";
					resultado += "  Priority: " + task.getPriority()
							+ "<br><br>";
				}
			}

		} else if (req.getParameter("opcion").equals("borrar")) {

			Response respu = cli
					.target("http://localhost:8080/list/task/"
							+ req.getParameter("task"))
					.request(MediaType.APPLICATION_JSON).delete();

			exito = "";

			respu = cli.target("http://localhost:8080/list")
					.request(MediaType.APPLICATION_JSON).get();

			ToDoList toDoList = respu.readEntity(ToDoList.class);
			if (toDoList != null) {
				for (Task task : toDoList.getList()) {
					resultado += "Task:   " + task.getTask() + "<br>";
					if (task.getContext() != null)
						resultado += "  Context: " + task.getContext() + "<br>";
					if (task.getProject() != null)
						resultado += "  Project: " + task.getProject() + "<br>";
					resultado += "  Priority: " + task.getPriority()
							+ "<br><br>";
				}
			}
		} else if (req.getParameter("opcion").equals("buscar")) {
			Response respu = cli
					.target("http://localhost:8080/list/task/"
							+ req.getParameter("task"))
					.request(MediaType.APPLICATION_JSON).get();

			exito = "";
			Task task = respu.readEntity(Task.class);
			if (task != null) {
				resultado += "Task:   " + task.getTask() + "<br>";
				if (task.getContext() != null)
					resultado += "  Context: " + task.getContext() + "<br>";
				if (task.getProject() != null)
					resultado += "  Project: " + task.getProject() + "<br>";
				resultado += "  Priority: " + task.getPriority() + "<br><br>";
			}
		} else if (req.getParameter("opcion").equals("modificar")) {
			Task toDo = new Task();
			toDo.setTask(req.getParameter("task"));
			toDo.setContext(req.getParameter("context"));
			toDo.setProject(req.getParameter("project"));
			toDo.setPriority(Integer.parseInt(req.getParameter("priority")));
			Response respu = cli.target("http://localhost:8080/list/task/"+req.getParameter("task"))
					.request(MediaType.APPLICATION_JSON)
					.put(Entity.entity(toDo, MediaType.APPLICATION_JSON));

			if (respu.getStatus() == 201) {
				exito = "SU OPERACIÓN SE HA REALIZADO CORRECTAMENTE!<br>";
			} else {
				exito = "ERROR AL AGREGAR SU TAREA <br>";
			}
			respu = cli.target("http://localhost:8080/list")
					.request(MediaType.APPLICATION_JSON).get();
			ToDoList toDoList = respu.readEntity(ToDoList.class);
			if (toDoList != null) {
				for (Task task : toDoList.getList()) {
					resultado += "Task:   " + task.getTask() + "<br>";
					if (task.getContext() != null)
						resultado += "  Context: " + task.getContext() + "<br>";
					if (task.getProject() != null)
						resultado += "  Project: " + task.getProject() + "<br>";
					resultado += "  Priority: " + task.getPriority()
							+ "<br><br>";
				}
			}
		}

		VelocityContext context = new VelocityContext();
		StringWriter writer = new StringWriter();
		context.put("lista", resultado);
		context.put("exito", exito);
		Velocity.mergeTemplate("cliente.vm", "ISO-8859-1", context, writer);

		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println(writer.toString());
	}
}
