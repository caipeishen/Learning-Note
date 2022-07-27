package main

import "fmt"

func main() {

	// var number int
	
	// fmt.Print("请输入号码：")
	// fmt.Scanln(&number)

	// if number == 2 {
	// 	fmt.Print("你是幸运玩家")
	// } else {
	// 	fmt.Print("你没有中奖")
	// }

	// for i := 1; i <= 10; i++ {
	// 	fmt.Println("Cai Peishen")
	// }


	//打印 1~100 之间所有是 9 的倍数的整数的个数及总和

	// var sum int
	// var count int
	// for i:= 1; i <= 100; i++ {
	// 	if i % 9 == 0 {
	// 		sum += i
	// 		count++
	// 	}
	// }
	// fmt.Printf("sum:%d, count:%d \n", sum, count)


	/**

	####*#### 	1	1
	###***###	2	3
	##*****##	3	5
	#*******#	4	7
	*********	5	9

	*/
	// var num int
	// fmt.Print("请输入行数:")
	// fmt.Scanln(&num)
	// for i := 1; i <= num; i++ {
	// 	for j := 0; j < num - i; j++ {
	// 		fmt.Print(" ")
	// 	}
	// 	for j := 0; j < 2 * i - 1; j++ {
	// 		fmt.Print("*")
	// 	} 
	// 	for j := 0; j < num - i; j++ {
	// 		fmt.Print(" ")
	// 	}
	// 	fmt.Println()
	// }


	/**
		1 * 1 = 1
		1 * 2 = 2	2 * 2 = 4
		1 * 3 = 3	2 * 3 = 6	3 * 3 = 9
		1 * 4 = 4	2 * 4 = 8	3 * 4 = 12
	*/

	for i := 1; i <= 9; i++ {
		for j := 1; j <= i; j++ {
			fmt.Printf("%v * %v = %v\t", j, i, i * j)
		}
		fmt.Println()
	}

	/**
		有一堆桃子，猴子第一天吃了其中的一半，并再多吃了一个！
		以后每天猴子都吃其中的一半，然后再多吃一个。
		当到第十天时，想再吃时（还没吃），发现只有 1 个桃子了。问题：最初共多少个桃子？
	*/




}
