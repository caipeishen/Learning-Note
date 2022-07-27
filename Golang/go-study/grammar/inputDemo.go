package main

import (
	"fmt"
)

func main(){

	fmt.Print(-5^-6)

	var name string
	var age int
	var salary float32
	var isPass bool
	// fmt.Print("请输入姓名：")
	// fmt.Scanln(&name)
	
	// fmt.Print("请输入年龄：")
	// fmt.Scanln(&age)
	
	// fmt.Print("请输入薪水：")
	// fmt.Scanln(&salary)
	
	// fmt.Print("请输入是否通过考试：")
	// fmt.Scanln(&isPass)
	
	fmt.Println("请输入你的姓名，年龄，薪水，是否通过考试，使用空格隔开")
	fmt.Scanf("%s %d %f %t", &name, &age, &salary, &isPass)

	fmt.Printf(" 姓名：%s \n 年龄：%d \n 薪水：%f \n 是否通过考试：%t", name, age, salary, isPass)

}