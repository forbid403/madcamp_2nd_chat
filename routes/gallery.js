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

gallery.get('/find/:phone', (req, res)=>{
    
    console.log(req.body)
    console.log("/gallery/find/" + req.params.phone)
    User.find().select('gallery').where('phone').equals(req.params.phone).exec((err, user)=>{
        if(err) return res.status(500).json({error : 'database failure'})
        
        if(!user) return res.json({result : 0})
        res.json(user)
    })

})

module.exports = gallery