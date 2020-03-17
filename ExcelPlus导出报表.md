### ExcelPlus导出报表

1. 所用到的工具类

   ```
   ExcelMake、ExcelUtilPlus、FileUtil
   ```

   

2. JSON格式参数：sheetName、props、1、2 · · ·（row：1、2代表行数）

   ```json
   sheetName：	导出报表的名称，字符串格式''
   
   props：		导出报表哪些属性，需要与查询的字段匹配（不相同的使用as别名），字符串数组格式['','','']
   
   1:			第一行，一行中有多个单元格，单元格有width,height,name属性，数组对象格式[{},{}]
   2:			第二行，一行中有多个单元格，单元格有width,height,name属性，数组对象格式[{},{}]
   			行示例：[{width:'',height:'',name:''},{width:'',height:'',name:''}]
   ```



3. js代码实例

   ```js
   $("#export").click(function () {
   	var sheetName = "报表";
   	var props = ["id", "userName", "genderName", "createDate"];
   	var row1 = [{"width":"1","height":"3","name":"结算部门"},{"width":"1","height":"3","name":"类型"},{"width":"6","height":"1","name":"公司账户"}];
       var row2 = [{"width":"3","height":"1","name":"点心"},{"width":"3","height":"1","name":"套餐"}];
       var row3 = [{"width":"1","height":"1","name":"刷卡次数"},{"width":"1","height":"1","name":"单价"},{"width":"1","height":"1","name":"总金额"},{"width":"1","height":"1","name":"次数"},{"width":"1","height":"1","name":"单价"},{"width":"1","height":"1","name":"总金额"}];
       var data = {"1":row1,"2":row2,"3":row3,"props":props,"sheetName":sheetName}
       $.ajax({
           url:"/user/export",
           data:JSON.stringify(data),
           type:"post",
           contentType:"application/json",
           success:function(fileName){
               console.log(fileName);
               window.open("../user/downLoad?fileName=" + fileName);
           },error:function(){
           	console.log("失败！");
           }
           })
       })
   } 
   ```

   

4. java代码实例

   ```java
   //存放excel的文件夹
   String excelPath = "excel" + File.separator;
   
   /**
    * 以流的方式导出报表(支持多表头)
    * @return
    */
   @ResponseBody
   @RequestMapping(value = "/export")
   public String export(@RequestBody JSONObject data,
                        HttpServletRequest request, HttpServletResponse response){
   
       System.out.println(data.toJSONString());
       String sheetName = data.get("sheetName").toString();
   
       //获取数据--JSONObject对象
       List<JSONObject> list = userService.getUserListJSONObject();
   
       //excel文件名
       String fileName = sheetName + System.currentTimeMillis() + ".xls";
   
       //绘制单元格对象
       ExcelMake make = new ExcelMake(sheetName);
   
       String dirPath = request.getSession().getServletContext().getRealPath(excelPath);
       String filePath = dirPath + File.separator + fileName;
   
       //上传到服务器位置，用户用的话需要调用下载的方法
       try {
           File file = new File(dirPath);
           if(!file.exists()){file.mkdirs();}
           FileOutputStream fos = new FileOutputStream(filePath);
           FileUtil.setResponseHeader(fileName,response);
           HSSFWorkbook wb = new ExcelUtilPlus().getHSSFWorkbook(make, data, list);
           wb.write(fos);
           fos.flush();
           fos.close();
       } catch (Exception e) {
           e.printStackTrace();
       }finally {
           System.out.println(filePath + "\t" +fileName);
           return fileName;
       }
   
   }
   
   /**
   	下载服务器的Excel文件
   */
   @RequestMapping(value = "/downLoad")
   public void downLoad(String fileName,HttpServletRequest request,HttpServletResponse response) throws IOException {
       String dirPath = request.getSession().getServletContext().getRealPath(excelPath);
       FileUtil.downLoadFile(fileName,dirPath,request,response);
       System.out.println(dirPath+File.separator+fileName);
   }
   ```

5. 实例文件的格式

   

   ![](image\ExcelPlus.png)