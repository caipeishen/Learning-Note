package main

// import "fmt"
import (
	"fmt"
	"unsafe"
	"strconv"
)

func main() {

	var n1 int

	var id, name, age, word , word2 = 1, "张三", 18, 'a', 'A'

	var (
		gender = "男"
		birthday = "1999-12-12"
	)

	fmt.Println(id,name,age)
	fmt.Println(gender,birthday)
	fmt.Println("Hello World!\r你")

	fmt.Println("n1=", n1)

	// Printf 格式化输出
	fmt.Printf("\nid的类型 %T ", id)

	fmt.Printf("\nid的类型%T %d,", name, unsafe.Sizeof(name))

	// 字节大小和数据类型
	fmt.Printf("\nword的类型%T %d,", word, unsafe.Sizeof(word))

	fmt.Printf("\nword2的类型%T %d,", word2, unsafe.Sizeof(word2))

	var money = 10000000000000;

	fmt.Printf("%T %d", money, unsafe.Sizeof(money))

	chat1 := '0'
	fmt.Println("\n", chat1)

	myName := "采培珅：Cai Peishen"
	myName = "123"
	fmt.Println(`我的名字：`+myName)


	var n2 int8 = 10 
	n2 = n2+118
	fmt.Println(n2)

	var n3 string = "你好";
	var n4 bool = true;

	n4, _ = strconv.ParseBool(n3)
	fmt.Println(n4)

	n5 := 10
	n5--
	fmt.Println(n5)

}