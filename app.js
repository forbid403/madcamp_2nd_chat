const express = require('express')
const app = express()
const bodyParser = require('body-parser')
const mongoose = require('mongoose')
const dbUrl = 'mongodb://localhost:27017/mdcamp2'
const port = process.env.port || 3000

const http = require('http')
const server = http.createServer(app)
const socketio = require('socket.io')
const io = socketio.listen(server)

// router
const userRouter = require('./routes/user')
const addresRouter = require('./routes/address')
const galleryRouter = require('./routes/gallery')
const chatRouter = require('./routes/chat')

// configure app to use bodyparser
app.use(bodyParser.urlencoded({ extended: true }))
app.use(bodyParser.json({ limit: '50mb' }))
app.use('/user', userRouter)
app.use('/address', addresRouter)
app.use('/gallery', galleryRouter)
app.use('/chat', chatRouter)

//connect to db
const db = mongoose.connection
db.on('error', console.error)
db.once('open', () => console.log("connected to mongodb"))
app.get('/', (req, res) => res.send("hey, I am server"))

mongoose.connect(dbUrl, { dbName: "user" }).then(() => { console.log("ready to use"), err => { console.log(err) } })

server.listen(port, () => {
    console.log("express server open")
})


io.sockets.on('connection', function (socket) {
    console.log("socket connected")

    console.log("socket : " + socket.handshake.query.token);
    socket.join(socket.handshake.query.token)
    socket.on('msg', function (author, data, roomId) {
        console.log("get message : " + author + " " + data + " " + roomId)
        let message = {"author": author, "content": data}
        
        io.to(roomId).emit('message', message)
    })

    /*
    socket.on('login', function(roomId){
        console.log("LOGINED!: " + roomId)
        socket.join(roomId)
    })

    socket.on('leaveRoom', function(roomId){
        console.log("leave room : " + roomId)
        socket.leave(roomId)
    })

    socket.disconnect('disconnect', function(){
        console.log("disconnected : " + socket.id)
    })*/

})
 