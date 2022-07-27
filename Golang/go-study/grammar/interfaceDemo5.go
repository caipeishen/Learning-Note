package main

import (
	"fmt"
	"math/rand"
	"sort"
)

type Student struct {
	Id    int
	Name  string
	Score float64
}

type StudentSlice []Student

func (ss StudentSlice) Len() int {
	return len(ss)
}

func (ss StudentSlice) Less(i, j int) bool {
	return ss[i].Score > ss[j].Score
}

func (ss StudentSlice) Swap(i, j int) {
	ss[i], ss[j] = ss[j], ss[i]
}

func main() {
	var stus StudentSlice
	for i := 0; i < 10; i++ {
		stu := Student{
			Id:    rand.Intn(100),
			Name:  fmt.Sprintf("学生|%d", rand.Intn(100)),
			Score: float64(rand.Intn(100)),
		}
		//将 stu append 到 stus 切片
		stus = append(stus, stu)
	}
	//看看排序前的顺序
	for _ , v := range stus { 
		fmt.Println(v)
	}

	// 排序
	sort.Sort(stus)
	fmt.Println()

	//看看排序后的顺序
	for _ , v := range stus { 
		fmt.Println(v)
	}
}
