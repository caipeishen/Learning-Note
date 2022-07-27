package main

import (
	"fmt"
)

func main(){

	/**
		0 0 0 0 0 0
		0 0 1 0 0 0
		0 2 0 3 0 0
		0 0 0 0 0 0
	*/

	var multiArr [4][6]int 

	multiArr[1][2] = 1
	multiArr[2][1] = 2
	multiArr[2][3] = 3


	for _,v := range multiArr {
		for _,w := range v {
			fmt.Print(w, " ")
		}
		fmt.Println()
	}

	var multiArr2 = [2][3]int{{1,2,3},{4,5,6}}

	for _,v := range multiArr2 {
		for _,w := range v {
			fmt.Print(w, " ")
		}
		fmt.Println()
	}

}