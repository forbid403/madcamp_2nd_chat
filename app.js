const express = require('express')
const app = express()
const bodyParser = require('body-parser')
const mongoose = require('mongoose')
const dbUrl = 'mongodb://localhost:27017/mdcamp2'
const port = process.env.port || 3000

// router
const userRouter = require('./routes/user')
const addresRouter = require('./routes/address')

// configure app to use bodyparser
app.use(bodyParser.urlencoded({extended:true}))
app.use(bodyParser.json())
app.use('/user', userRouter)
app.use('/address', addresRouter)

//connect to db
const db = mongoose.connection
db.on('error', console.error)
db.once('open', ()=>console.log("connected to mongodb"))
app.get('/', (req, res)=>res.send("hey, I am server"))

mongoose.connect(dbUrl, {dbName:"user"}).then(()=>{console.log("ready to use"), err=>{console.log(err)}})

var server = app.listen(port, function(){
    console.log("Express server start")
})
