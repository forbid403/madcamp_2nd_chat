const express = require('express')
const address = express.Router()

//add one address
address.get('/address/:address', (req, res)=>{
    console.log("/address/"+req.params.address)
    
    //find user
    /*
        do something
    */

    //save address
    

})

module.exports = address
