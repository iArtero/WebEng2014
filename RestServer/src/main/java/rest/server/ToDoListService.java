package rest.server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

/**
 * A service that manipulates contacts in an address book.
 *
 */
@Path("/list")
public class ToDoListService {
	private final static String DEFAULT_FILE_NAME = "ToDo_list.json";

	/**
	 * The (shared) address book object.
	 */
	@Inject
	ToDoList toDoList;

	/**
	 * A GET /contacts request should return the address book in JSON.
	 * 
	 * @return a JSON representation of the address book.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ToDoList getToDoList() {
		Gson gson = new Gson();
		// Read the existing address book.
		try {
			toDoList = gson.fromJson(new FileReader(DEFAULT_FILE_NAME),
					ToDoList.class);
		} catch (FileNotFoundException e) {
			System.out.println(DEFAULT_FILE_NAME + ": File not found.");
		}
		return toDoList;
	}

	/**
	 * A GET /list/task/{id} request should return a entry from the
	 * address book
	 * 
	 * @param id
	 *            the unique identifier of a person
	 * @return a JSON representation of the new entry or 404
	 */
	@GET
	@Path("/task/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTask(@PathParam("id") String id) {
		Gson gson = new Gson();
		// Read the existing address book.
		try {
			toDoList = gson.fromJson(new FileReader(DEFAULT_FILE_NAME),
					ToDoList.class);
		} catch (FileNotFoundException e) {
			System.out.println(DEFAULT_FILE_NAME + ": File not found.");
		}
		List<Task> lista = toDoList.getList();
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getTask().equals(id)) {
				return Response.ok(lista.get(i)).build();
			}
		}
		return Response.status(Status.NOT_FOUND).build();
	}

	/**
	 * A PUT /list/task/{id} should update a entry if exists
	 * 
	 * @param info
	 *            the URI information of the request
	 * @param task
	 *            the posted entity
	 * @param id
	 *            the identifier of a task
	 * @return a JSON representation of the new updated entry or 400 if the id
	 *         is not a key
	 */
	@PUT
	@Path("/task/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateTask(@Context UriInfo info,
			@PathParam("id") String id, Task task) {

		Gson gson = new Gson();
		// Read the existing address book.
		try {
			toDoList = gson.fromJson(new FileReader(DEFAULT_FILE_NAME),
					ToDoList.class);
		} catch (FileNotFoundException e) {
			System.out.println(DEFAULT_FILE_NAME + ": File not found.");
		}
		List<Task> lista = toDoList.getList();
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getTask().equals(id)) {
				lista.remove(i);
				lista.add(i, task);
				FileWriter output;
				try {
					output = new FileWriter(DEFAULT_FILE_NAME);
					output.write(gson.toJson(toDoList));
					output.close();

				} catch (IOException e) {
					return Response.status(500).build();

				}
				return Response.ok(task).build();
			}
		}
		return Response.status(Status.BAD_REQUEST).build();

	}

	/**
	 * A DELETE /list/task/{id} should delete a entry if exists
	 * 
	 * @param id
	 *            the unique identifier of a person
	 * @return 204 if the request is successful, 404 if the id is not a key
	 */
	@DELETE
	@Path("/task/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteTask(@PathParam("id") String id) {
		Gson gson = new Gson();
		// Read the existing address book.
		try {
			toDoList = gson.fromJson(new FileReader(DEFAULT_FILE_NAME),
					ToDoList.class);
		} catch (FileNotFoundException e) {
			System.out.println(DEFAULT_FILE_NAME + ": File not found.");
		}
		List<Task> lista = toDoList.getList();
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getTask().equals(id)) {
				lista.remove(i);
				FileWriter output;
				try {
					output = new FileWriter(DEFAULT_FILE_NAME);
					output.write(gson.toJson(toDoList));
					output.close();

				} catch (IOException e) {
					return Response.status(500).build();

				}
				return Response.noContent().build();
			}
		}
		return Response.status(Status.NOT_FOUND).build();
	}

	/**
	 * A POST /list request should add a new entry to the address book.
	 * 
	 * @param info
	 *            the URI information of the request
	 * @param person
	 *            the posted entity
	 * @return a JSON representation of the new entry that should be available
	 *         at /list/task/{id}.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addTask(@Context UriInfo info, Task task) {
		
		Gson gson = new Gson();
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
			
			task.setHref(info.getAbsolutePathBuilder().path("task/{id}")
					.build(task.getTask()));
			// Add a task.
			toDoList.addTask(task);
			// Write the new ToDo list back to disk.	
			FileWriter output = new FileWriter(DEFAULT_FILE_NAME);
			output.write(gson.toJson(toDoList));
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
		return Response.created(task.getHref()).entity(task).build();
	}

}
