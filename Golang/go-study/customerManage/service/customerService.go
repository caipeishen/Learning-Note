package service

import (
	"fmt"
	model "go-study/customerManage/model"
)

type CustomerService struct { 
	Customers []model.Customer //声明一个字段，表示当前切片含有多少个客户
}

// 客户service工厂
func NewCustomerService() *CustomerService {
	customerService := &CustomerService{}
	customer := model.NewCustomer(1, "采培珅", "男", 21, "18848848551", "peishen.cai@foxmail.com")
	customerService.Customers = append(customerService.Customers, customer)
	return customerService
}

// 添加客户
func (cs *CustomerService) AddCustomer(name string, gender string, age int, phone string, email string) {
	cs.Customers = append(cs.Customers, model.NewCustomer(len(cs.Customers)+1, name, gender, age, phone, email))
	return
}

// 返回列表数据
func (cs *CustomerService) List() []model.Customer {
	return cs.Customers
}

// 根据id获取客户
func (cs *CustomerService) GetById(id int) int {
	index := -1
	for i, v := range cs.Customers {
		if id == v.GetId() {
			index = i
			break
		}
	}
	return index
}

// 根据id删除客户
func (cs *CustomerService) DeleteId(id int) {
	index := cs.GetById(id)
	if index == -1 {
		fmt.Println("改用户不存在,无法删除")
	} else {
		// 0 1 2 3 ，删除2的话
		// 先保留 0 1 数据，然后再将3数据添加到0 1 数据中，返回新数据，覆盖原有数据
		cs.Customers = append(cs.Customers[:index], cs.Customers[index+1:]...)

	}
}