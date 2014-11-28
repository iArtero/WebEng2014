package formats.json;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import com.google.gson.Gson;


public class AddTask {

	public final static String DEFAULT_FILE_NAME = "ToDo_list.json";

	// This function fills in a task message based on user input.
	static Task PromptForAddress(BufferedReader stdin, PrintStream stdout)
			throws IOException {
		Task task = new Task();

		stdout.print("Enter task: ");
		task.setTask(stdin.readLine());

		stdout.print("Enter context: ");
		task.setContext(stdin.readLine());

		stdout.print("Enter project: ");
		task.setProject(stdin.readLine());


		stdout.print("Enter a priority: ");
		task.setPriority(Integer.parseInt(stdin.readLine()));
			

		return task;
	}

	// Main function: Reads the entire ToDo list from a file,
	// adds one task based on user input, then writes it back out to the same
	// file.
	public static void main(String[] args) throws Exception {
		String filename = DEFAULT_FILE_NAME;
		if (args.length > 0) {
			filename = args[0];
		}

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
		toDoList.addTask(PromptForAddress(new BufferedReader(
				new InputStreamReader(System.in)), System.out));

		// Write the new ToDo list back to disk.
		FileWriter output = new FileWriter(filename);
		output.write(gson.toJson(toDoList));
		output.close();
	}
}
