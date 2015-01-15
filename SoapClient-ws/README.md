# toDoRepository! Client
This project contains a demonstration of a web client 

This project requires that running ```toDo``` server. 

One of the first steps is the generation of the code required to invoke the client from
[HellowsCliService.wsdl](src/main/wsdl/HellowsCliService.wsdl) file. Look at [build.gradle](build.gradle) to get a glimpse of the 
complexities of the code generation process. 

The client can be run with the command ```gradle jettyEclipseRun```. Client runs in port 8081.

