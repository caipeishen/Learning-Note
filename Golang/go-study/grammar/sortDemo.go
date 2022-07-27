package main

import (
	"fmt"
)

func main() {

	// 冒泡排序
	var arr [6]int= [...]int{3,0,6,5,2,4}
	BubbleSort(&arr)

	// 二分查找 冒泡排序过的数租 [0 2 3 4 5 6]
	BinaryFind(&arr, 6, 0, len(arr) - 1)

}


// 冒泡排序 从小到大
/**
	3 0 6 5 2 4
*/
func BubbleSort(arr *[6]int) {
	fmt.Println("排序前：", *arr)
	for i:= 0; i < len(*arr) -1; i++  {
		for j:= 0; j < len(*arr) - i - 1; j++ {
			if (*arr)[j] > (*arr)[j+1] {
				temp := (*arr)[j]
				(*arr)[j] = (*arr)[j+1]
				(*arr)[j+1] = temp
			}
		}
	}
	fmt.Println("排序后：", *arr)
}


func BinaryFind(arr *[6]int, findVal int, leftIndex int, rightIndex int) {

	if leftIndex > rightIndex {
		fmt.Println("找不到") 
		return
	}

	// 中间的数据
	middle := (leftIndex + rightIndex) / 2

	if findVal == (*arr)[middle] {
		fmt.Printf("数字【%v】找到了，索引为：%v", findVal, middle)
		return
	} else if findVal < (*arr)[middle] {
		// leftIndex --- middel-1 
		rightIndex = middle -1
	} else if findVal > (*arr)[middle] {
		// middel+1 --- rightIndex
		leftIndex = middle + 1
	}

	// 递归
	BinaryFind(arr, findVal, leftIndex, rightIndex)

}