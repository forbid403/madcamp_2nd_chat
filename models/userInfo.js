const mongoose = require('mongoose')

//user Info schema
const userInfoSchema = new mongoose.Schema({
    name : String,
    phone : String,
    photo : String,
    desc : String,
    address : Array,
    gallery : Array
})


module.exports = mongoose.model('userinfo', userInfoSchema)