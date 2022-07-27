package main

import (
	"fmt"
	"strconv"
	"strings"
)

func main(){
	str2 := "hello北京"

	fmt.Println(str2,"长度为",len(str2))

	r := []rune(str2)
	for i := 0; i< len(r); i++ {
		fmt.Printf("字符=%c\n", r[i])
	}

	n, err := strconv.Atoi("hello")
	if err != nil {
		fmt.Println("转换错误", err)
	} else {
		fmt.Println("转成的结果是", n)
	}

	var bytes = []byte("hello go")

	str3 := string(bytes)

	fmt.Printf("bytes=%v\n", bytes)
	fmt.Printf("str3=%v\n", str3)


	// 查找子串是否在指定的字符串中
	flag := strings.Contains("seafood", "foo")
	fmt.Println("是否包含foo", flag)

	// 统计一个字符串有几个指定的子串
	eCount := strings.Count("cheese", "e")
	fmt.Println("e在cheese中出现了", eCount, "次")

	// 不区分大小写的字符串比较(== 是区分字母大小写的)
	isEqual := strings.EqualFold("abc", "ABC")
	fmt.Println("abc和ABC不区分大小写是否相等：", isEqual)

	// 返回子串在字符串第一次出现的 index 值，如果没有返回-1
	firstIndex := strings.Index("qweabcabc","ab")
	fmt.Println("ab首次出现在qweabcabc的索引为：", firstIndex)

	// 返回子串在字符串最后一次出现的 index，如没有返回-1
	lastIndex := strings.LastIndex("qweabcabc","ab")
	fmt.Println("ab最后一次出现在qweabcabc的索引为：", lastIndex)

	// 将指定的子串替换成 另外一个子串: strings.Replace("go go hello", "go", "go 语言", n) n 可以指定你希望替换几个，如果 n=-1 表示全部替换
	str4 := strings.Replace("你好，世界，你好", "你好", "hello", -1)
	fmt.Println(str4)


	// 按 照 指 定 的 某 个 字 符 ， 为 分 割 标 识 ， 将 一 个 字 符 串 拆 分 成 字 符 串 数 组
	strArr := strings.Split("hello,wrold,ok", ",")
	for i:= 0; i< len(strArr); i++ {
		fmt.Printf("hello,wrold,ok拆分，strArr[%v]=%v\n", i, strArr[i])
	}

	// 将字符串的字母进行大小写的转换
	str5 := strings.ToLower("goLang")
	str6 := strings.ToUpper("goLang")
	fmt.Println("goLang转小写：", str5)
	fmt.Println("goLang转大写：", str6)

	// 将字符串左右两边的空格去掉
	str7 := strings.TrimSpace(" I am a student. ")
	fmt.Println("【 I am a student. 】去除前后空格：", str7)

	// 判断字符串是否以指定的字符串开头
	str8 := strings.HasPrefix("www.baidu.com", "www")
	fmt.Println("www.baidu.com是以www开头：", str8)

	// 判断字符串是否以指定的字符串结束
	str9 := strings.HasSuffix("www.baidu.com", "com")
	fmt.Println("www.baidu.com是以com结束：", str9)
}