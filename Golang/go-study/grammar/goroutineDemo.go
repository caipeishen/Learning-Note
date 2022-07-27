package main

import (
	"fmt"
	"math/rand"
	"runtime"
	"time"
)

type Person3 struct {
	Id   int    `json: "id"`
	Name string `json: "name"`
}

func main() {

	numCPU := runtime.NumCPU()
	fmt.Printf("cpu：%v", numCPU)

	runtime.GOMAXPROCS(6)
	numCPU = runtime.NumCPU()

	intChan := make(chan int, 2)
	intChan <- 10
	intChan <- 10
	fmt.Printf("intChan 的值=%v intChan 本身的地址=%p\n", intChan, &intChan)

	fmt.Printf("channel len=%v cap=%v \n", len(intChan), cap(intChan))

	int1 := <-intChan
	int2 := <-intChan
	fmt.Printf("int1=%v\n", int1)
	fmt.Printf("int2=%v\n", int2)
	fmt.Printf("channel len=%v cap=%v \n", len(intChan), cap(intChan))

	personChan := make(chan Person3, 10)

	// 放入数据
	rand.Seed(time.Now().UnixNano())
	for i := 1; i <= 10; i++ {
		per1 := Person3{Id: rand.Intn(100), Name: "name"}
		personChan <- per1
	}

	// 传统的方法在遍历管道时，如果不关闭会阻塞而导致 deadlock
	//for i := 0; i < len(personChan); i++ {
	//	v := <- personChan
	//	fmt.Println(v)
	//}

	// 关闭后不能再进行写入数据，但是可以读取数据
	close(personChan)
	for v := range personChan {
		fmt.Println(v)
	}

}
