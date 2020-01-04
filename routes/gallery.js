const express = require('express')
const gallery = express.Router()
const User = require('../models/userInfo')

gallery.get('/:phone/:image', (req, res)=>{
    console.log('/gallery/add/image')
    User.updateOne({'phone' : req.params.phone}, {$addToSet : {'gallery' : req.params.image}}).exec((err, result)=>{
        if(err) return res.status(500).json({"err" : err})
        if(!result.n) return res.status(500).json({"err" : "user not found!"})
        if(!result.nModified) return res.status(500).json({"err" : "gallery already in!"})
                
        res.json({res : 1})
    })
})


module.exports = gallery