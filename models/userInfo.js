const mongoose = require('mongoose')

//user Info schema
const userInfoSchema = new mongoose.Schema({
    myname : {
        type : String,
        required : true
    },
    phone : {
        type : String,
        required : true,
        unique : true
    },
    photo : {
        type : String,
        required : true
    },
    desc : {
        type : String
    },
    address :[{
        name : {
            type : String,
            required : true
        },
        phonenum : {
            type : String,
            required : true
        }
    }],
    gallery : {
        type : Array,
        required : true
    }
})


module.exports = mongoose.model('userinfo', userInfoSchema)