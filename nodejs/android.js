var http = require('http');
var socketio = require('socket.io');

var server = http.createServer(function(req, res){
     
}).listen(8081, function(){
    console.log('Server is now running at the port 8080');
});

var io = socketio.listen(server);
io.sockets.on('connection', function (socket){
    console.log('Socket ID ' + socket.id + ', Connect');
    socket.on('query', function(data) {
        console.log('Client Message : ' + data);

        //do Something
        var debugMessage = [
            {name : 'Ahn Jun Ho', message : 'Hi~'},
            {name : 'Kim min jae', message : 'I am okay!'},
        ];

        socket.emit('result',debugMessage);
    });
});

