package main

import (
	"fmt"
	"math/rand"
	"time"
)

func main(){
	var intArr [10]int

	// 同一纳秒会生成相同的数据
	rand.Seed(time.Now().UnixNano())
	for i := 0; i< len(intArr); i++ {
		intArr[i] = rand.Intn(100)
	}
	fmt.Println(intArr)


	for i := 0; i< len(intArr) / 2; i++ {
		temp := intArr[i]
		intArr[i] = intArr[len(intArr) - i - 1]
		intArr[len(intArr) - i - 1] = temp
	}
	fmt.Println(intArr)

}