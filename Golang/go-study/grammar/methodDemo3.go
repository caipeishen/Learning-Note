package main

import (
	"fmt"
)

type MethodUtils struct {

}

func (mu MethodUtils) multiplicationTable(num int) {
	for i:= 1; i <= num; i++ {
		for j:= 1; j <= i; j++ {
			fmt.Printf("%v * %v = %v \t", j, i, i * j)
		}
		fmt.Println()
	}
}

func main() {
	//var mu MethodUtils
	//var mu *MethodUtils = new(MethodUtils)
	//var mu *MethodUtils = &MethodUtils{}
	mu := MethodUtils{}
	mu.multiplicationTable(9)

	/**
		1 2 3
		4 5 6
		7 8 9

		0,1 -> 1,0
		0,2 -> 2,0
		1,2 -> 2,1
	*/
	arr := [3][3]int{ {1,2,3}, {4,5,6}, {7,8,9} }
	fmt.Println(arr)
	arrPoint := &arr

	for i := 0; i < len(arr); i++ {
		for j := 0; j < len(arr[j]); j++ {
			if i < j && (*arrPoint)[i][j] != (*arrPoint)[j][i] {
				fmt.Println("(*arrPoint)[i][j]", (*arrPoint)[i][j], ",(*arrPoint)[j][i]", (*arrPoint)[j][i])
				temp := (*arrPoint)[i][j]
				(*arrPoint)[i][j] = (*arrPoint)[j][i]
				(*arrPoint)[j][i] = temp
			}
		}
	}

	fmt.Println(arr)

}