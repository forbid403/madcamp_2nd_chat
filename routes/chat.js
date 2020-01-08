const express = require('express')
const router = express.Router()
const Room = require('../models/roomInfo')
const Chat = require('../models/chatInfo')

//채팅방 불러오기
router.get('/:id', (req, res)=>{
    console.log('/chat/' + req.params.id)

    Room.find().where('member').in(req.params.id).exec(function(err, room){
        if(err) return res.status(500).json({error : 'database failure'})
        
        if(!room) return res.json({result : 0})
        res.json(room)
        return
    })

})

//모든 채팅 기록 불러오기
router.get('/get/:id', (req, res)=>{
    console.log('/chat/get/' + req.params.id)
    
    Chat.find().where('id').equals(req.params.id).exec( function(err, chat){
        if(err) return res.status(500).json({error : err})
        
        if(!chat) return res.json({result : 0})
        res.json(chat)
        return
    })

})

//메세지 저장
router.post('/save', (req, res)=>{
    console.log('/chat/save')
    
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
        console.log("save complete")
        res.json({result : 1})
        return

    })

})

//채팅방 새로 만들기
router.post('/newchat', (req, res)=>{
    console.log('/gallery/newchat')
    console.log(JSON.stringify(req.body))

    const room = new Room()
    room.id = req.body.id
    room.member = req.body.member
    room.creator = req.body.creator

    room.save((err, result)=>{
        if (err) {
            console.log(err)
            res.json({result : 0})
            return
        }
        res.json({result : 1})
    })

})

module.exports = router