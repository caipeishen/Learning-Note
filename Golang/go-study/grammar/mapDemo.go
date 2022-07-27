package main

import(
	"fmt"
)

func main(){
	
	/**
		1)使用 map[string]map[string]sting 的 map 类型
		2)key: 表示用户名，是唯一的，不可以重复
		3)如果某个用户名存在，就将其密码修改"888888"，如果不存在就增加这个用户信息,（包括昵称
		nickname 和 密码 pwd）。
		4)编写一个函数 modifyUser(users map[string]map[string]sting, name string) 完成上述功能
	*/

	users := make(map[string]map[string]string, 10) 
	users["smith"] = make(map[string]string) 
	users["smith"]["pwd"] = "999999"
	users["smith"]["nickname"] = "小花猫"

	modifyUser(users, "tom") 

	fmt.Println(users)
}

func modifyUser(users map[string]map[string]string, name string) {
	if users[name] != nil {
		users[name]["pwd"] = "88888888"
	} else {
		users[name] = make(map[string]string)
		users[name]["nickname"] = name
		users[name]["pwd"] = "999999999"

	}
}