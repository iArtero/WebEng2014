var URI = "ws://localhost:8025/websockets/todo";
var socket; 

/* Document Ready */
$(document).ready(function() { 
  
	$("#boton_1").click(function() {
		
		task = JSON.stringify({    
			task: $('#task_1').val(),
			context: $('#context').val(),
			project: $('#project').val(),
			priority: $('#priority').val()
		});
		
		addTask(task);
		
		
		$('#task_1').val("");
		$('#context').val("");
		$('#project').val("");
		$('#priority').val("");
		    
	});
	
	$("#boton_2").click(function() {
		
		if($('#opcion_2').val()==="borrar"){
			deleteTask($('#task_2').val());

		}
		else{
			findTask($('#task_2').val());
		}
		$('#task_2').val("");
		
	});
	
	connectServer();
  
});

function connectServer(){
	socket = new WebSocket(URI);
	socket.onopen = function(){
		getList();
	}
	socket.onmessage = function(respuesta){
		respuesta = respuesta.data;
		var op = respuesta.substring(0, 3);
		switch (op) {
			case "add":	
				getList();
				break;
			case "bus":
				var msg = JSON.parse(respuesta.substr(3, respuesta.length));
				$('#lista').empty();
				showTask(msg);
				break;			
			case "del":	
				getList();
				break;
			case "get":
				var msg = JSON.parse(respuesta.substr(3, respuesta.length));
				$('#lista').empty();
				showList(msg.list);
				break;
		}
	}
	socket.onerror = function(){
		alert("Error connecting to server");
	}
}

/* Shows tasks on table */
function showList(toDoList) {  
	for (var i in toDoList){
		showTask(toDoList[i]);
	}
}

function showTask(task) { 
	$('#lista').append( 'Task:  ' +task.task + '<br>' +
						'Context:  '+ task.context + '<br>' +
						'Project:  ' +task.project + '<br>' +
						'Priority:  ' +task.priority + '<br><br>');
}

function getList() { 
	socket.send("get");
}

function addTask(task) {
	socket.send("add" + task);
}

function findTask(id) {
	socket.send("bus"+id);
}

function deleteTask(id) {
	socket.send("del" + id);
}

