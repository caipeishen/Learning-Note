package main

import(
	"fmt"
	"encoding/json"
)

type Cat struct {
	Id int `json:"id"`
	Name string `json:"name"`
}

func main(){
	var myCat Cat
	myCat.Id = 10
	myCat.Name = "喵咪"
	fmt.Println(myCat)
	myFun1(myCat)
	fmt.Println("值传递：", myCat)
	myFun2(&myCat)
	fmt.Println("引用传递：", myCat)
	jsonByte, err :=  json.Marshal(myCat)
	if err != nil {
		fmt.Println(err)
	}
	fmt.Println(string(jsonByte))
}
// 值传递
func myFun1(myCat Cat) {
	myCat.Name = "我改变了喵咪名字，喵呜~"
}
// 引用传递
func myFun2(myCat *Cat) {
	//(*myCat) = myCat  底层进行了转化 让程序员更方便
	//(*myCat).Name = "我改变了喵咪名字，喵呜~"
	myCat.Name =  "我改变了喵咪名字，喵呜~"
}