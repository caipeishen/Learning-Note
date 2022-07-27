package main

import (
	"fmt"
	"strings"
)

var num1, num2 int = test()

func test() (int, int) {
	fmt.Println("test")
	return 100, 200
}

func init() {
	fmt.Println("init")
}

func main() {
	fmt.Println("main")

	f2 := makeSuffix(".jpg")
	fmt.Println(f2("文件"))
	fmt.Println(f2("文件2.jpg2"))
	
}


func makeSuffix(suffix string) func ( string) string {
	return func (fileName string) string {
		if (!strings.HasSuffix(fileName, suffix)) {
			return fileName + suffix
		}
		return fileName
	}
}