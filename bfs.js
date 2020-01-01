const queueUtil = require('./queue.js')
const disjointSetUtil = require('./disjoint_set.js')
const disjoint_set = new disjointSetUtil.DisjointSet()
const queue = new queueUtil.Queue()
const checked = new Set()
function bfs(source, target, map, callback){
    //q에 source 넣기
    queue.push(source)
    //bfs
    while (!queue.empty()) {
        
        //dequeue top
        const top = queue.pop()
    
        //map에서 dest 얻기
        map.forEach(dest => {
            //찾으면
            if(dest === target){
                console.log("FOUND!" + target)
                //경로 출력
                console.log(disjoint_set.getSet())
                
                //callback
                //callback(disjoint_set.getSet())
                return
            }
            //set에 없으면
            if(!checked.has(dest)){
                //q 삽입
                queue.push(dest)
                
                //disjoint 삽입
                disjoint_set.make_set(dest)
                disjoint_set.union(dest, top)
                
                //set update
                checked.add(dest)
            }
            
        });
        
    
    
    }
}