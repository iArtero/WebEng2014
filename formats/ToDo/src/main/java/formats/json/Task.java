package formats.json;

import java.util.ArrayList;
import java.util.List;

public class Task {
	private String task, context, project;
	private int priority;

	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	

}
