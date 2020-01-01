const queueUtil = require('./queue.js')
const disjointSetUtil = require('./disjoint_set.js')

function bfs(source, target, map, callback) {
    const queue = new queueUtil.Queue()
    const disjoint_set = new disjointSetUtil.DisjointSet()
    const checked = new Set()
    let flag = false

    console.log("Start finding");
    //q에 source 넣기
    queue.push(source)
    disjoint_set.make_set(source)
    //bfs
    while (!queue.empty()) {
    
        if(flag == true){
            break
        }
        //dequeue top
        const top = queue.pop()

        //map에서 dest 얻기
        if (map.get(top) !== undefined) {

            map.get(top).some((v) => {
                //찾으면
                if (v === target) {
                    console.log("FOUND!" + target)

                    flag = true
                    disjoint_set.make_set(v)
                    disjoint_set.union(top, v)
                    
                    //경로 출력
                    //disjoint_set.getSet()                   

                    callback(disjoint_set.find(target));

                    return true;
                }
                //set에 없으면
                if (!checked.has(v)) {
                    //q 삽입
                    queue.push(v)

                    //disjoint 삽입
                    disjoint_set.make_set(v)
                    disjoint_set.union(top, v)

                }


            });

        }
        checked.add(top)


    }
}

exports.bfs = bfs