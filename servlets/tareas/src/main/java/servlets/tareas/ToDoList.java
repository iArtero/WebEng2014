package servlets.tareas;

import java.util.ArrayList;
import java.util.List;

public class ToDoList {

	private List<Task> list = new ArrayList<Task>();

	public List<Task> getList() {
		return list;
	}

	public void setList(List<Task> tasks) {
		this.list = tasks;
	}

	public void addTask(Task task) {
		list.add(task);
	}
}
