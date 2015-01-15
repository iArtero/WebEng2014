package rest.client;

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
	public void removeTask(String task){
		boolean borrado=false;
		for(int i=0;i<list.size()&&(!borrado);i++){
			if(list.get(i).getTask().equals(task)){
				list.remove(i);
				borrado=true;
			}
		}
	}
}
