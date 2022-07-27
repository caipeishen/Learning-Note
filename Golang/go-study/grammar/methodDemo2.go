package main

import (
	"fmt"
)

type Integer int 

// 打印
func (i Integer) println() {
	fmt.Println("i=", i)
}

// 赋值
func (i *Integer) change(num Integer) {
	*i = num
}

func main() {

	var num Integer = 888
	num.println()

	var num2 Integer = 999
	// 底层做了转化 num = (&num)
	num.change(num2)

	fmt.Println(num)
}