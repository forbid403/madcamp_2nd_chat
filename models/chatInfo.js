const mongoose = require('mongoose')
//user Info schema
const chatSchema = new mongoose.Schema({
    id : {
        type : String,
        required : true
    },
    message : {
        type : String,
        required : true
    },
    author : {
        type : String,
        required : true
    },
    time : {
        type : Date,
        default : Date.now
    }
    
})
module.exports = mongoose.model('chatting', chatSchema)