针对2018年北京高校校园大数据竞赛数据预处理部分的代码

（https://bigdata.bupt.edu.cn/index.html）

1、输入为原始.csv文件,用python脚本导入到数据库中（单个文件过大，直接处理文件速度过慢，报out of meomory）

2、运行CountbaseDatabase文件，对每个月份的数据进行统计

3、运行numcount对每个月份的每个地点的数据进行统计，共33个地点

4、有星期参与统计的数据处理，运行WeekCountResult

5、没有星期参与统计的数据处理,运行noWeekCountResult

6、nextResult：按照比赛要求生成相应格式的数据，进行了重新拍讯
