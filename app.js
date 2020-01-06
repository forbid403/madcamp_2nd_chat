const app = require('express')()
const port = process.env.port || 4000
const mongoose = require('mongoose')
const dbUrl = 'mongodb://localhost:27017/mdcamp2'
const socketio = require('socket.io')
const http = require('http')
const server = http.createServer(app)
const io = socketio.listen(server)
const bodyparser = require('body-parser')
const chatRouter = require('./routes/chat')

app.use(bodyparser.json())
app.use(bodyparser.urlencoded({extended : true}))
app.use('/chat', chatRouter)

//connect to db
const db = mongoose.connection
db.on('error', console.error)
db.once('open', ()=>console.log("connected to mongodb"))
app.get('/', (req, res)=>res.send("hey, I am server"))

mongoose.connect(dbUrl, {dbName:"user"}).then(()=>{console.log("ready to use"), err=>{console.log(err)}})


server.listen(port, ()=>{
  console.log("express server open")
})

io.on('connection', function(socket){
  console.log("socket connected")

  socket.on('msg', function(data){
    
    //return data
    let message = {"author" : "a", "content" : data}
    io.emit('message', message)
  })
})

