const express = require('express')
const app = express()
const bodyParser = require('body-parser')
const mongoose = require('mongoose')
const dbUrl = 'mongodb://localhost:27017/mdcamp2'
const port = process.env.port || 3000
const User = require('./models/userInfo')
const router = require('./routes')(app, User)

// configure app to use bodyparser
app.use(bodyParser.urlencoded({extended:true}))
app.use(bodyParser.json())

//connect to db
const db = mongoose.connection
db.on('error', console.error)
db.once('open', function(){
    console.log("connected to mongodb")
})

mongoose.connect(dbUrl, {dbName:"user"}).then(()=>{console.log("ready to use"), err=>{console.log(err)}})

var server = app.listen(port, function(){
    console.log("Express server start")
})
