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
	name string
}
//让 Phone 实现 Usb 接口的方法
func (p Phone) Start() {
	fmt.Println("手机开始工作。。。")
}
func (p Phone) Stop() {
	fmt.Println("手机停止工作。。。")
}
func (p Phone) Call() { 
	fmt.Println("手机 在打电话..")
}

type Camera struct { 
	name string
}
//让 Camera 实现	Usb 接口的方法
func (c Camera) Start() {
	fmt.Println("相机开始工作。。。")
}
func (c Camera) Stop() {
	fmt.Println("相机停止工作。。。")
}

type Computer struct{

}

func (computer Computer) Working(usb Usb) {
	usb.Start()
	if p, flag := usb.(Phone); flag {
		p.Call()
	} else {
		fmt.Println("Usb转换Phone失败...")
	}
	usb.Stop()
}

func main() {
	var usbArr [3]Usb 
	usbArr[0] = Phone{"vivo"} 
	usbArr[1] = Phone{"小米"}
	usbArr[2] = Camera{"尼康"}

	var computer Computer
	for _, v := range usbArr{ 
		computer.Working(v) 
		fmt.Println()
	}
	//fmt.Println(usbArr)
}
