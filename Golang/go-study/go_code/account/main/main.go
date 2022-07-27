package main

import (
	"fmt"
	"go-study/go_code/account/utils"
)

func main() {
	fmt.Println("面向对象的方式来完成.....")
	utils.NewMyFamilyAccount().MainMenu()
}
