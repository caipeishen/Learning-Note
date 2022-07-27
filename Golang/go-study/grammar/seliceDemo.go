package main

import (
	"fmt"
)

func main(){

	var arr = [...]int{1,2,3,4,5}
	arrSlice := arr[:]

	// 不想吐槽了，
	arrSlice2 := append(arrSlice, 100)

	fmt.Println(arr)
	fmt.Println(arrSlice)
	fmt.Println(arrSlice2)
	
	


	// var slice []int = []int{5,4,1,10,2,65,3}

	// for i := 0; i < len(slice); i++ {
	// 	fmt.Print(slice[i], "\t")
	// }

	// fmt.Println()

	// for i, v := range slice {
	// 	fmt.Printf("i=%v, v=%v\n", i, v)
	// }

	// fmt.Println(slice)



	slice := fbn(100)
	fmt.Println(slice)


	var slice2 []int
	fmt.Println(slice2)
	arr[0] = 1
	fmt.Println(slice2)
}



// 将斐波那契的数列放到切片中
func fbn (n int) []uint64 {
	// slice[0] = 1; slice[1] = 1; slice[2]=2; slice[3] = 3; slice[4]=5; slice[5]=8
	slice := make([]uint64, n)

	slice[0] = 1
	slice[1] = 1

	for i:= 2; i < n; i++ {
		slice[i] = slice[i-1] + slice[i-2]
	}
	return slice
}