package main

import (
	"fmt"
	"runtime"
)

/**
要求统计 1-200000 的数字中，哪些是素数？这个问题在本章开篇就提出了，
现在我们有 goroutine 和 channel 的知识后，就可以完成了 [测试数据: 80000]
*/
func main() {

	// 测试数字
	testNum := 80000

	// cpu个数
	numCPU := runtime.NumCPU()

	// 初始化
	allNumChan := make(chan int, 1000)
	primeNumChan := make(chan int, testNum)
	exitChan := make(chan bool, numCPU)

	// 写入数据
	go putNum(allNumChan, testNum)

	// 根据cpu个数来确定创建几个goroutine，用来并行操作
	for i := 0; i < numCPU; i++ {
		go primeNum(allNumChan, primeNumChan, exitChan)
	}

	// 阻塞
	for {
		exitCount := len(exitChan)
		if exitCount == numCPU {
			break
		}
	}

	// 关闭
	close(primeNumChan)
	close(exitChan)

	// 打印
	for v := range primeNumChan {
		fmt.Println(v)
	}

}

// 写入数据
func putNum(allNumChan chan int, testNum int) {
	for i := 1; i <= testNum; i++ {
		allNumChan <- i
	}
	close(allNumChan)
}

// 提取素数
func primeNum(allNumChan chan int, primeNumChan chan int, exitChan chan bool) {
	for {
		v, ok := <-allNumChan
		if ok {
			flag := true
			for i := 1; i <= v; i++ {
				if i != 1 && i != v && v%i == 0 {
					flag = false
					break
				}
			}
			if flag {
				primeNumChan <- v
			}
		} else {
			break
		}
	}
	exitChan <- true
}
