package soap.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import soap.server.Task;
import soap.server.ToDoWebService;
import soap.server.ToDoWebServiceService;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/Cliente" })
public class Cliente extends HttpServlet {
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Velocity.setProperty("file.resource.loader.path", "./src/main/webapp");
		Velocity.init();
		
		ToDoWebServiceService hwss = new ToDoWebServiceService();
		ToDoWebService hws = hwss.getToDoWebServicePort();
		if(req.getParameter("opcion").equals("agregar")){
			hws.addToDo(req.getParameter("task"), req.getParameter("context"), req.getParameter("project"), Integer.parseInt(req.getParameter("priority")));
		}else if(req.getParameter("opcion").equals("borrar")){
			hws.removeToDo(req.getParameter("task"));
		}
		String resultado="";
		for (Task task : hws.listToDo()) {
				resultado += "Task:   "+task.getTask()+ "<br>";
				resultado +="  Context: " + task.getContext()+ "<br>";
				resultado +="  Project: " + task.getProject()+ "<br>";
				resultado +="  Priority: " + task.getPriority()+ "<br><br>";
		}
		VelocityContext context = new VelocityContext();
		StringWriter writer = new StringWriter();
		context.put("lista", resultado);
		Velocity.mergeTemplate("cliente.vm", "ISO-8859-1", context, writer);
		
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println(writer.toString());
		
		
		
	
	}
}
