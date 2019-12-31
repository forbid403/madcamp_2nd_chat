class DisjointSet{

    constructor(){
        this.array = []
        this.ret = []
    }
    make_set(x){
        this.array[x] = x
    }
    union(x, y){      
        if(x == y) return
        this.array[y] = x
    }
    find(x){
        this.ret.push(x)
        if(this.array[x] == x) {
            return this.ret
        }
        return this.find(this.array[x])
    }
    getSet(){
        console.log(this.array) 
    }

}

exports.DisjointSet = DisjointSet
