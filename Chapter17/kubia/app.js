const http = require('http');
const os = require('os');

console.log("Kubia server starting...");

var handler = function(request, response) {
  console.log("Received request from " + request.connection.remoteAddress);
  response.writeHead(200);
  response.end("You've hit " + os.hostname() + "\n");
};

var www = http.createServer(handler);

process.on('SIGTERM', function () {
  console.log("Received SIGTERM. Shutting down.");
  www.close(function () {
    console.log("HTTP server has shut down. Process exiting.");
    process.exit(0);
  });
});

www.listen(8080);

