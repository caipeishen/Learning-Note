package main

// import "fmt"
import (
	"fmt"
	"unsafe"
)

func main() {
	var id = 10
	var name string = "Cai Peishen"
	gender := '男'
	isAdmin := true
	
	fmt.Printf("id=%v, name=%v, gender=%c, isAdmin=%v \n", id, name, gender, isAdmin)

	fmt.Printf("gender=%T, 字节大小=%v", id, unsafe.Sizeof(name))

	var num = 97
	week := 91 / 7
	day := num % 7 
	fmt.Printf("还有%v周%v天放假\n", week, day)

	numString := fmt.Sprintf("%v\n", num);
	fmt.Println(numString)

	var addr *string = &numString;
	fmt.Printf("num的地址%v, addr的地址%v, addr的值%v, addr=%v\n", &numString, &addr, *addr, addr)

	number := 10
	var number2 *int = &number
	*number2 = 20
	fmt.Println(number) 

	var inputString string
	fmt.Scanln(&inputString)
	fmt.Println(inputString)
}