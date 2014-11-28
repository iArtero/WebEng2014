package formats.json;

import java.io.FileReader;

import com.google.gson.Gson;

class ListTasks {
	public final static String DEFAULT_FILE_NAME = "ToDo_list.json";

	// Iterates though all tasks in the ToDoList and prints info about them.
	static void Print(ToDoList toDoList) {
		for (Task task : toDoList.getList()) {
			System.out.println("Task: " + task.getTask());
			System.out.println("  Context: " + task.getContext());
			System.out.println("  Project " + task.getProject());
			System.out.println("  Priority " + task.getPriority());
		}
	}
	

	// Main function: Reads the entire t ToDo List from a file and prints all
	// the information inside.
	public static void main(String[] args) throws Exception {
		Gson gson = new Gson();
		String filename = DEFAULT_FILE_NAME;
		if (args.length > 0) {
			filename = args[0];
		}

		// Read the existing ToDo List.
		ToDoList toDoList = gson.fromJson(new FileReader(filename),
				ToDoList.class);

		Print(toDoList);
	}
}