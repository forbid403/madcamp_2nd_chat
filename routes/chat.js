const express = require('express')
const router = express.Router()
const Room = require('../models/roomInfo')
router.get('/:id', (req, res)=>{
    console.log('/chat')

    Room.find().where('id').equals(req.params.id).exec((err, room)=>{
        if(err) return res.status(500).json({error : 'database failure'})
        
        if(!room) return res.json({result : 0})
        res.json(room)
    })
})

module.exports = router