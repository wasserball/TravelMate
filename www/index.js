var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);
var body_parser = require('body-parser');
app.use(body_parser.json());


app.get('/', function (req, res) {
  res.sendFile(__dirname + '/index.html');
});
app.post("/hotels", function(req, res, next) {
  io.sockets.emit("hotel message", req.body.data.type);
  res.send(req.body.data);
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



