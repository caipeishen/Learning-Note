package main

import (
	"fmt"
	"time"
)

func main() {
	//看看日期和时间相关函数和方法使用
	//1．获取当前时间
	now := time.Now()
	fmt.Printf("now=%v now type=%T\n", now, now)
	//2.通过now可以获取到年月日,时分秒
	fmt.Printf("年=%v\n", now.Year())
	fmt.Printf("月=%v\n", now.Month())
	fmt.Printf("月=%v\n", int(now.Month()))
	fmt.Printf("日=%v\n", now.Day())
	fmt.Printf("时=%v\n", now.Hour())
	fmt.Printf("分=%v\n", now.Minute())
	fmt.Printf("秒=%v\n", now.Second())

	fmt.Println(now.Format("2006/01/02 15:04:05"))

	time.Sleep(10 * time.Millisecond)

	// 编写一个函数:输出100以内的所有素数(素数就只能被1和本身整除的数,每行显示5个;并求和
	var sum int
	var lineCount int
	for i := 1; i <= 100; i++ {
		flag := true
		for j := i; j >= 1; j-- {
			if j != 1 && j != i && (i%j == 0) {
				flag = false
				break
			}
		}
		if flag {
			sum += i
			lineCount++
			fmt.Print(i, "\t")
			if lineCount%5 == 0 {
				fmt.Println()
			}
		}
	}
	fmt.Println()
	fmt.Println("总和为：", sum)

	weight := 0.0
	fmt.Printf("%T", weight)
	for {
	}
}
