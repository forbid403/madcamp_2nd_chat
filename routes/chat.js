const express = require('express')
const router = express.Router()
const Room = require('../models/roomInfo')
const Chat = require('../models/chatInfo')

//채팅방 불러오기
router.get('/:id', (req, res)=>{
    console.log('/chat/' + req.params.id)

    Room.find().where('member').in(req.params.id).exec((err, room)=>{
        if(err) return res.status(500).json({error : 'database failure'})
        
        if(!room) return res.json({result : 0})
        res.json(room)
    })
})

//모든 채팅 기록 불러오기
router.get('/get/:id', (req, res)=>{
    console.log('/chat/get/' + req.params.id)
    
    Chat.find().where('id').equals(req.params.id).exec((err, chat)=>{
        if(err) return res.status(500).json({error : err})
        
        if(!chat) return res.json({result : 0})
        console.log(chat)
        res.json(chat)
    })
})

//메세지 저장
router.post('/save', (req, res)=>{
    console.log('/chat/save')
    console.log(JSON.stringify(req.body))
    
    const chat = new Chat()
    chat.id = req.body.id
    chat.message = req.body.message
    chat.author = req.body.author
    chat.time = req.body.time

    chat.save((err, result)=>{
        if(err) {
            console.log(err)
            res.json({result : 0})
            return
        }
        res.json({result : 1})

    })

})

module.exports = router