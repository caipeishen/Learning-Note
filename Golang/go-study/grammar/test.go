package main

import (
	"fmt"
)

type Name struct{}

func dome1() {
	fs := [4]func(){}
	for i := 0; i < 4; i++ {
		defer fmt.Println("defer i =", i)
		defer func() { fmt.Println("defer_closure i = ", i) }()
		fs[i] = func() {
			fmt.Println("closuer i =", i)
		}
	}
	for _, f := range fs {
		f()
	}
}

func dome3() {
	a, b, c, d := 1, 2, 3, 4
	A3(a, b, c, d)
	fmt.Println(a, b, c, d)

	s1 := []int{1, 2, 3, 4}
	B3(s1)
	fmt.Println(s1)
	fmt.Printf("%T", s1)

}

func A3(a ...int) {
	a[0] = 5
	a[1] = 6
	a[2] = 7
	a[3] = 8
	fmt.Println(a)
}

func B3(a []int) {
	a[0] = 5
	a[1] = 6
	a[2] = 7
	a[3] = 8

	fmt.Println(a)
}

func func1(int) {
	fmt.Println()
}

func point() {
	//var num int = 1
	//fmt.Printf("num = %v, &num = %v\n", num, &num)
	//var number *int = &num
	//fmt.Printf("number = %v, *number = %v \n", number, *number)
	//*number = 2
	//fmt.Printf("num = %v, &num = %v\n", num, &num)
	//fmt.Printf("number = %v, *number = %v \n", number, *number)
}

func testClass() {
	//var sum float32
	//for i := 1; i <= 3; i++ {
	//	var score float32
	//	for j := 1; j <= 5; j++ {
	//		var input float32
	//		fmt.Printf("%d-%d：", i, j)
	//		fmt.Scanln(&input)
	//		score = score + input
	//	}
	//	sum = sum + score
	//	fmt.Printf("%d班 平均分:%v\n", i, score/5)
	//}
	//fmt.Printf("所有班级平均分:%v", sum/15)
}

func sliceMap() {
	var soces []int
	if soces == nil {
		fmt.Println("为空")
	}
	fmt.Println(soces, " ", len(soces), " ", cap(soces))

	var nameMap map[string]string = make(map[string]string, 2)
	nameMap["CaiPeishen"] = "身高182，体重140"
	nameMap["ferris"] = "身高182，体重140"
	nameMap["ferris2"] = "身高182，体重140"
	nameMap["ferris3"] = "身高182，体重140"

	for k, _ := range nameMap {
		fmt.Println(k)
	}

	fmt.Println(nameMap)
}

func main() {
	//dome3()

	var num *int = new(int)

	*num = 200
	fmt.Printf("str=%d, *str=%v", *num, num)

}
