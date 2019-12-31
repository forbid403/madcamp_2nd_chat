const listUtil = require('./linkedlist.js')
const queueUtil = require('./queue.js')
const relativeModel = require('./android.js')
const queue = new queueUtil.Queue()
const checked = new Set()
exports.bfs = bfs
const tempData1 = {
    "_id": "5e0997d5b6cde30684cf8816",
    "dest": [
        "01012341234",
        "01012349876",
        "01031241057",
        "01031241234",
        "01032411234",
        "01033241123",
        "01043211262",
        "01044231134",
        "010472817283",
        "01058853342",
        "01066763393",
        "01068851234",
        "01090982192",
        "123141212312",
        "273327332373"
    ],
    "source": "01031241057",
    "__v": 0
}
const tempData2 = {
    "_id": "5e0997d5b6cde30684cf8816",
    "dest": [
        "01012341234",
        "01012349876",
        "01031241057",
        "01031241234",
        "01032411234",
        "01033241123",
        "01043211262",
        "01044231134",
        "010472817283",
        "01058853342",
        "01066763393",
        "01068851234",
        "01090982192",
        "123141212312",
        "273327332373"
    ],
    "source": "01012345678",
    "__v": 0
}
const tempData3 = {
    "_id": "5e0997d5b6cde30684cf8816",
    "dest": [
        "01012341234",
        "01012349876",
        "01031241057",
        "01031241234",
        "01032411234",
        "01033241123",
        "01043211262",
        "01044231134",
        "010472817283",
        "01058853342",
        "01066763393",
        "01068851234",
        "01090982192",
        "123141212312",
        "273327332373"
    ],
    "source": "01099999999",
    "__v": 0
}
const tempData4 = {
    "_id": "5e0997d5b6cde30684cf8816",
    "dest": [
        "01012341234",
        "01012349876",
        "01031241057",
        "01031241234",
        "01032411234",
        "01033241123",
        "01043211262",
        "01044231134",
        "010472817283",
        "01058853342",
        "01066763393",
        "01068851234",
        "01090982192",
        "123141212312",
        "273327332373"
    ],
    "source": "01088888888",
    "__v": 0
}
const tempData5 = {
    "_id": "5e0997d5b6cde30684cf8816",
    "dest": [
        "01012341234",
        "01012349876",
        "01031241057",
        "01031241234",
        "01032411234",
        "01033241123",
        "01043211262",
        "01044231134",
        "010472817283",
        "01058853342",
        "01066763393",
        "01068851234",
        "01090982192",
        "123141212312",
        "273327332373"
    ],
    "source": "01077777777",
    "__v": 0
}
//make linked list
const dummyData = [tempData1, tempData2, tempData3, tempData4, tempData5]

/* await informationModel.findOne({phone : "01031241057"}).exec(function(err,res) {
     profilePictureDebug = json array => 친구들 휴대폰 번호
 */

async function bfs(relativeModel, psource, pdest) {
    MergeRelative(relativeModel, psource).then(function (list) {
        console.log(list.dest);

        queue.push(list)

        var index = 0;
        var loopCount = 1;
        var depth = 0;

        al(queue);

        function al(queue) {
            index++;
            if (loopCount <= index) {
                depth = depth + 1
                index = 0;
            }
            const top = queue.pop()

            console.log("current node " + top.source + "  index: " + index);

            if (checked.has(top.source)) {
                return;
            }

            loopCount = top.dest.length;

            var adjacent = new Array();
            var currentHead = top.currentHead;

            forloop(0, top.dest.length).then(function (resolve, reject) {
                if (queue.empty()) {

                }
                else {
                    al(queue);
                }
            });

            function forloop(index, length) {
                return new Promise(function (resolve, reject) {
                    if (top.dest[index] == pdest) {
                        console.log("FOUND!" + pdest)
                        resolve();
                    }
                    MergeRelative(relativeModel, top.dest[index]).then(function (result) {
                        if (result != null) {
                            queue.push(result);
                            resolve();
                        }
                        if (index => length) {
                            resolve();
                        }

                        index++;
                        forloop(index, length);
                    })
                })

            }

            checked.add(top.dest);
        }

        if (depth > 0) {
            console.log("촌 수 : " + depth)
        }
    })
}

function MergeRelative(prelativeModel, psource) {
    return new Promise(async function (resolve, reject) {
        prelativeModel.findOne({ source: psource }).exec(async function (err, res) {
            resolve(res);
        })
    })
}
