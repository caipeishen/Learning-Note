package main

import(
	"fmt"
	"encoding/json"
)


type Person struct {
	Id int `json: "id"`
	Name string `json: "name"`
}

func (person Person) toString() {
	person.Id = 2
	jsonByte , err := json.Marshal(person)
	if err != nil {
		fmt.Println("出现异常：", err)
		return
	}
	fmt.Println("person：", string(jsonByte))
}

// 引用
func (person *Person) setName(name string) {
	// 底层做了转化 person = (&person)
	person.Name = name
}

// 输出默认调用String()方法
func (person *Person) String() string {
	str := fmt.Sprintf("Id=[%v], Name=[%v]", person.Id, person.Name)
	return str
}

func main(){
	var person Person
	person.Id = 1
	person.Name = "Cai Peishen"
	person.toString()
	fmt.Println("main方法的person.Name = ", person.Name)

	// 底层做了转化 person = (&person)
	person.setName("Ferris")
	fmt.Println("main方法的person.Name = ", person.Name)

	// 5)如果一个类型实现了 String()这个方法，那么 fmt.Println 默认会调用这个变量的 String()进行输出
	fmt.Println("String()：", &person)
}