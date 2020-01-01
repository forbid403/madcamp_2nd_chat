const bfs = require('./bfs.js')
//안드로이드 서버 오픈
var http = require('http');
var socketio = require('socket.io');
const loadBFS = require('./bfs.js')
var Json
var server = http.createServer(function(req, res){
    
}).listen(8081, function(){
    console.log('Server is now running at the port 8080');
});
//몽고 디비에 연결
var mongoose = require('mongoose');
mongoose.connect("mongodb+srv://ahn9807:wnsgh8546**@cluster0-7lhcw.mongodb.net/test");
var db = mongoose.connection;
db.on('error', function() {
    console.log('Connection Failed!');
});
var relativeDBMap;
db.once('open', function() {
    console.log('Connected to MONGODB');
    LoadData(relativeModel).then(function(result) {
        relativeDBMap = result;
        console.log("Loading relative information completed!");
        console.log(result);
        bfs.bfs("10","17",relativeDBMap,()=>{})
    })

});
var information = mongoose.Schema({
    name : 'string', //이름
    phone : 'string', //휴대폰 번호
    message : 'string', //상태 메세지
    open : 'bool', //상대에게 노출할 것인가?
    profilePicture : 'string', //profile Picture
    //relatives  폰 번호로 서치
});
var relative = mongoose.Schema({
    source : {
        type: String,
        require:true
    },
    dest : [String],
})
var informationModel = mongoose.model('Address',information);
var relativeModel = mongoose.model('Relative',relative);
exports.relativeModel = relativeModel
//안드로이드 정보 처리
var io = socketio.listen(server);
io.sockets.on('connection', function (socket){
    console.log('Socket ID ' + socket.id + ', Connect');
    var phoneNum = "";
    socket.on('clientPhoneNumber', function(data) {
        console.log('phoneNum:' + data);
        phoneNum = data;
    });
    socket.on('query', async function(data) {
        console.log('Client Message : ' + data);
        var source = JSON.parse(data).source; //애플리케이션을 실행한 사람의 번호
        var dest = JSON.parse(data).dest; //찾고자 하는 번호
        bfs.bfs(source, dest, relativeDBMap, queryFunction);
        async function queryFunction(inputArray, outputResultArray) {
            outputResultArray = new Array(inputArray.length);
            for(let i=0;i<inputArray.length;i++) {
                try {
                    await informationModel.findOne({phone : inputArray[i]}).exec(function(err, res) {
                        if(res == null) {
                            console.log("Address 에 등록 되지 않은 사용자 입니다.")
                            outputResultArray[i] = { name : "", message : "", profilePicture : ""}
                        }
                        else {
                            let mProfile = res.profilePicture;
                            let mName = res.name;
                            let mMessage = res.message;
        
                            outputResultArray[i] = { name : mName, message : mMessage, profilePicture : mProfile }
                        }
                    })
                } catch(error) {
                    console.log(errgor);
                }
            }
            console.log("outside");
            socket.emit('result', outputResultArray);
        }
    });
    socket.on('changeInfo',async function(data) {
        checkClient(phoneNum);
        await informationModel.deleteMany({phone : phoneNum}, function(err, result) {});
        await informationModel.create(JSON.parse(data));
        console.log("Informationed is saved");
        console.log(data);
    });
    socket.on('changeRelative',async function(data) {
        checkClient(phoneNum);
        await relativeModel.deleteMany({source : phoneNum}, function(err, result) {});
        await relativeModel.create(JSON.parse(data));
        relativeDBMap.set(data.source, data.dest);
        console.log("relative is saved")
        console.log(JSON.parse(data));
    });
});
//데이터 베이스 정보 처리
function checkClient(phoneNum) {
    if(phoneNum == "") {
        console.log("Invalid Cleint Number!");
    }
}
function LoadData(relativeModel) {
    return new Promise(function(resolve, reject) {
        let mMap = new Map();
        relativeModel.find(function(err, result) {
            for(let i =0; i< result.length; i++) {
                mMap.set(result[i].source, result[i].dest);
            }
        }).then(function(result) {
            resolve(mMap);
        })
    })
}
