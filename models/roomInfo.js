const mongoose = require('mongoose')
//user Info schema
const roomSchema = new mongoose.Schema({
    id : {
        type : String,
        required : true
    },
    member : {
        type : Array,
        required : true
    },
    creator : {
        type : String,
        required : true
    }
    
})
module.exports = mongoose.model('roominfo', roomSchema)