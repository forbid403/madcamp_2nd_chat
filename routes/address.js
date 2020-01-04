const express = require('express')
const address = express.Router()
const User = require('../models/userInfo')

//add one address
address.get('/:phone/:address', (req, res)=>{
    console.log("/address/"+req.params.phone + "/" +req.params.address)

    User.updateOne({"phone" : req.params.phone}, {$addToSet : {'address' : req.params.address}}).exec((err, result)=>{
        if(err) return res.status(500).json({"err" : err})
        if(!result.n) return res.status(500).json({"err" : "user not found!"})
        if(!result.nModified) return res.status(500).json({"err" : "address already in!"})
                
        res.json({res : 1})
    })
})




module.exports = address
