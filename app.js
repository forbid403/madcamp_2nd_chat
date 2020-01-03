const app = require('express')()
const port = process.env.port || 3000
const socketio = require('socket.io')
const http = require('http')
const server = http.createServer(app)
const io = socketio.listen(server)

let whoIsOn = [];

app.get('/', (req, res)=>{
  res.send("chat server is open")
})

server.listen(port, ()=>{
  console.log("express server open")
})


io.on('connection', function(socket){
  console.log("socket connected")

  socket.on('msg', function(data){
    console.log(data)
  })
})

