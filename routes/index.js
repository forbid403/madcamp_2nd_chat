
module.exports = function (app, User) {

    //sign up
    app.post('/user/signup', (req, res)=> {

        console.log("/user/signup")
        console.log(req.body)

        const newUser = new User()

        newUser.name = req.body.name
        newUser.phone = req.body.phone
        newUser.photo = req.body.photo
        newUser.desc = req.body.desc
        newUser.address = req.body.address
        newUser.gallery = req.body.gallery

        newUser.save((err, user) =>{
            if (err) {
                console.err(err)
                res.json({result : 0})
                return
            }
            res.json({result : 1})
        })
    })


    //return all user
    app.get('/user/find', (req, res)=>{
        console.log("/user/find")

        User.find(function(err, users){
            if(err) return res.status(500).send({err : 'database failure'})
            res.json(users)
        })
    })

    //find user by phone number
    app.get('/user/find/:phone', (req, res)=>{
        console.log("/user/find/" + req.params.phone)
        User.findOne({"phone": req.params.phone}, (err, user)=>{
            if(err) return res.status(500).json({error : 'database failure'})
            if(!user) return res.status(404).json({error : 'user not found'})

            res.json(user)
        })

    })


    
}