const express = require('express')
const address = express.Router()

//add one address
address.get('/address/:address', (req, res)=>{
    console.log("/address/"+req.params.address)
    

})

module.exports = address
