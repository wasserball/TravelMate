var express = require("express");
var app = express();
var http = require('http').Server(app);
var io = require('socket.io')(http);
var body_parser = require('body-parser');
app.use(body_parser.json());


app.use("/css", express.static(__dirname + '/css'));
app.use("/js", express.static(__dirname + '/js'));
app.use("/font", express.static(__dirname + '/font'));
app.use("/img", express.static(__dirname + '/img'));
app.use("/sounds", express.static(__dirname + '/sounds'));


app.get('/', function (req, res) {
  res.sendFile(__dirname + '/index.html');
});

app.post("/update", function(req, res, next) {
  io.sockets.emit("update");
  res.send(req.body);
});

app.post("/add", function(req, res, next) {
  io.sockets.emit("add");
  res.send(req.body.data);
});

app.post("/remove", function(req, res, next) {
  io.sockets.emit("remove");
  res.send(req.body);
});

io.on('connection', function(socket){
  console.log('a user connected');
  
  socket.on('disconnect', function(){
    console.log('user disconnected');
  });

});

http.listen(80, function(){
  console.log('listening on *:80');
});



