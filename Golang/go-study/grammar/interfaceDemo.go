package main

import (
	"fmt"
)

//声明/定义一个接口
type Usb interface {
	//声明了两个没有实现的方法
	Start()
	Stop()
}

type Phone struct {

}

//让 Phone 实现 Usb 接口的方法（所谓实现 Usb 接口，就是指实现了 Usb 接口声明所有方法）
func (p Phone) Start() {
	fmt.Println("手机开始工作")
}

func (p Phone) Stop() {
	fmt.Println("手机停止工作")
}


type Camera struct {

}

//让 Camera 实现	Usb 接口的方法（所谓实现 Usb 接口，就是指实现了 Usb 接口声明所有方法）
func (c Camera) Start() {
	fmt.Println("相机开始工作。。。")
}

func (c Camera) Stop() {
	fmt.Println("相机停止工作。。。")
}


type Computer struct {

}

func (c Computer) Working(usb Usb) { //usb  变量会根据传入的实参，来判断到底是 Phone,还是 Camera
	//通过 usb 接口变量来调用 Start 和 Stop 方法
	usb.Start() 
	usb.Stop()
}

func main() {

	computer := Computer{}
	phone := Phone{}
	camera := Camera{}

	//关键点
	computer.Working(phone)
	computer.Working(camera)
}