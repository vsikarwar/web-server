# Web Server

A simple file based web server written in Java.

## Features:
- Multithreaded Web Server, using thread pool executor.
- Keep-alive connection
- Etag cache-control
- GET, HEAD and POST support
- SSL support
- Custom Request Handler support
- 301/302 Redirect support

## Build
To build the server run maven clean install goal.

Clone the git repository

go to dir `web-server/web-server/`

execute `mvn clean install`

to run without test cases execute `mvn clean install -DskipTests`

This will generate the package in /target directory.

## Run
To run the server execute the following command.

`java -jar target/web-server-0.0.1-SNAPSHOT.jar`

This will start the web-server on default 8000 port.

## Default Pages
http://localhost:8000/index.html

## Configurations
Server can be custom configured by changing default configurations at `vi src/main/resources/conf/server.conf`

Configurations are maintained in json format
#### Default Configurations
```
{
        "port" : "8000",
        "ssl.port" : "1234",
        "default.threads" : "40",
        "max.threads" : "100",
        "socket.buff.size" : "1000",
        "server.name" : "WS-0.1",
        "doc.root" : "./content",
        "error.400" : "/errors/400.html",
        "error.500" : "/errors/500.html",
        "keep-alive.timeout" : 3000,
        "keep-alive.max": 1000
}
```

## Content
Default doc-root directory configured for server is `/content` directory. Files present under doc-root directory can be served from this server.

## Redirects
Redirects can be managed by adding them in redirect file `src/main/resources/conf/redirect.json`

Redirects can be mapped as follow
```
[
        {
                "source" : "/",
                "destination" : "index.html",
                "type" : "301"
        },
        {
                "source" : "/abc",
                "destination" : "xyz.html",
                "type" : "302"
        }
]
```
## Custom Request Handlers
Custom Request Handlers can be created by extending default `GETHandler` for GET requests or `POSTHandler` for POST requests.

Handler should implement `doGet` or `doPost` method for GET or POST requests respectively.

Handler can be mapped to specific path, for example, I can map `MyHandler` to path `/my/example/path`. Means, my custom handler `MyHandler` will get invoked on mapped path `/my/example/path`.

Default Handlers `GETHandler`, `POSTHandler` and `HEADHandler` can be replaced by custom handlers. In this case, custom handler should extend `AbstractHandler`.

Once the custom handler is ready, it should be registered to `HandlerRegistry` either as a handler mapped to a path or as a default handler for a method.


## Components of Web-Server

`WebServer`: this is the main server class. This creates and init basic core services and start them.

Each core service should extends `AbstractService` class which interns implement `HTTPService`, so that each service will have start and stop behaviour.

`SocketService` is responsible to start `SocketServer` which maintains thread pool executor. ThreadPool executor executes worker thread which is 'ServerSession'.

`ServerSession` reads the input request and pass it to `RequestReader` which parse the request and generate `HTTPRequest`.

This `HTTPRequest` is then passed to handler. Handler based on the configuration in `RequestHandler` and `HandlerRegistry` handles the request, which involved validating protocol version, methods, processing headers and calling the relevant custom/default handler to generate the response. Handler returns `HTTPResponse`.

`HTTPResponse` then passed to `ResponseWriter` to send it to client.

`HTTPResponse` and `HTTPMessage` are both part of `HTTPMessage`.
