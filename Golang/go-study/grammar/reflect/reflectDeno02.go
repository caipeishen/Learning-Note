package main

import (
	"encoding/json"
	"fmt"
	"reflect"
)

type User struct {
	Id   int    `json:"id"`
	Age  int    `json:"age"`
	Name string `json:"name"`
	Num1 int    `json:name1`
	Num2 int    `json:name2`
}

func (user *User) GetSub(name string) {
	result := user.Num1 - user.Num2
	fmt.Printf("%v完成了减法，%v-%v=%v\n", name, user.Num1, user.Num2, result)
}

func main() {

	user := &User{
		Id:   1,
		Name: "Cai Peishen",
		Age:  21,
	}

	reflectFunc(user)
	data, err := json.Marshal(user)
	if err != nil {
		fmt.Printf("序列号错误 err=%v\n", err)
	}
	//输出序列化后的结果
	fmt.Printf("monster 序列化后=%v\n", string(data))
}

func reflectFunc(arg interface{}) {
	rType := reflect.TypeOf(arg)
	rValue := reflect.ValueOf(arg)
	reType := rType.Elem()
	reValue := rValue.Elem()
	for i := 0; i < reType.NumField(); i++ {
		fieldName := reType.Field(i).Name
		fieldValue := reValue.Field(i)
		tagVal := reType.Field(i).Tag.Get("json")
		fmt.Printf("字段名:%v %v 值:%v\n", fieldName, tagVal, fieldValue)
	}

	reValue.Field(0).SetInt(2)
	reValue.Field(1).SetInt(18)
	reValue.FieldByName("Name").SetString("采培珅")
	reValue.Field(3).SetInt(10)
	reValue.Field(4).SetInt(2)

	var params []reflect.Value
	params = append(params, reflect.ValueOf(reValue.Field(2).String()))
	// 传入的参数是 []reflect.Value, 返回[]reflect.Value
	rValue.Method(0).Call(params)

}
