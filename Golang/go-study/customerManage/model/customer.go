package model

import (

)

// 声明一个 Customer 结构体，表示一个客户信息
type Customer struct {
	Id int
	Name string 
	Gender string 
	Age int 
	Phone string 
	Email string
}

// 使用工厂模式，返回一个 Customer 的实例
func NewCustomer(id int, name string, gender string, age int, phone string, email string ) Customer { 
	return Customer{
		Id : id,
		Name : name, 
		Gender : gender, 
		Age : age, 
		Phone : phone, 
		Email : email,
	}
}

// 获取客户信息
func (c *Customer) GetInfo() (int, string, string, int, string, string) {
	return c.Id, c.Name, c.Gender, c.Age, c.Phone, c.Email
}

// 根据Id获取客户
func (c *Customer) GetId() int {
	return c.Id
}

// 修改客户信息
func (c *Customer) Reset(name string, gender string, age int, phone string, email string) {
	if name != "" {
		c.Name = name
	}
	if gender != "" {
		c.Gender = gender
	}
	if age != 0 {
		c.Age = age
	}
	if phone != "" {
		c.Phone = phone
	}
	if email != "" {
		c.Email = email
	}
}
