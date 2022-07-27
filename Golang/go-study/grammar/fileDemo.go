package main

import (
	"fmt"
	"os"
)

func main() {

	file, err := os.Open("E:\\Administrator\\Go\\src\\go-study\\grammar\\fileDemo.go")
	if err != nil {
		fmt.Println("打开文件异常，", err)
	}

	fmt.Println("file=", &file)

	err = file.Close()
	if err != nil {
		fmt.Println("关闭文件异常，", err)
	}
}
