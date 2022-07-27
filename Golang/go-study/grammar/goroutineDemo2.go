package main

import (
	"fmt"
)

/**
请完成goroutine和channel协同工作的案例,具体要求:
1)开启一个writeData协程,向管道intChan中写入10个整数.
2)开启一个readData协程，从管道intChan中读取writeData写入的数据。3)注意:writeData和readDate操作的是同一个管道
4)主线程需要等待writeData和readDate协程都完成工作才能退出【管道】
*/
func main() {
	intChan := make(chan int, 10)
	go writeData(intChan)

	exitChan := make(chan bool, 1)
	go readData(intChan, exitChan)

	for {
		_, ok := <-exitChan
		if !ok {
			break
		}
	}
}

// 写入数据
func writeData(intChan chan int) {
	// 即使写入和读取频率不一样，也没问题，只是在写入时，可能会阻塞
	for i := 1; i <= 20; i++ {
		//放入数据
		intChan <- i
		fmt.Println("writeData ", i)
	}
	close(intChan) //关闭
}

// 读取数据
func readData(intChan chan int, exitChan chan bool) {
	for {
		num, ok := <-intChan
		if !ok {
			break
		}
		fmt.Printf("取出数据:%v\n", num)
	}
	exitChan <- true
	close(exitChan)
}
