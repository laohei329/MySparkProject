package PoiTest.poi

import java.io.{File, FileOutputStream}

import org.apache.poi.hssf.usermodel.HSSFWorkbook

import scala.io.Source



object PoiTest {
  def main(args: Array[String]): Unit = {

    val source1 = Source.fromFile(new File(  PoiTest.getClass.getResource("/000000_0").getPath), "utf-8")
      val filename = "文件的名字.xls"
      val file = new File(filename)
      val workbook = new HSSFWorkbook()


      val sheetWidth3 = Array[Int](3000, 3000, 3000, 3500, 3500, 3500, 3500, 3500, 3500, 3500, 3500, 3500, 3500)
      val excelTitleValue3 = Array[String]("区号",
          "学制小计", "小计学校数量", "小计学生人数", "小计教职工人数",
          "学制", "学制学校数量", "学制学生人数", "学制教职工人数",
          "性质", "性质学校数量", "性质学生人数", "性质教职工人数")

      val schoolLevel = source1.getLines().map({
          line =>

              val arrs: Array[String] = line.split(" ")
              var temp: Map[String, String] = Map()
              temp += ("区号" -> arrs(0))
              temp += ("学制小计" -> arrs(1))
              temp += ("小计学校数量" -> arrs(2))
              temp += ("小计学生人数" -> arrs(3))
              temp += ("小计教职工人数" -> arrs(4))
              temp += ("学制" -> arrs(5))
              temp += ("学制学校数量" -> arrs(6))
              temp += ("学制学生人数" -> arrs(7))
              temp += ("学制教职工人数" -> arrs(8))
              temp += ("性质" -> arrs(9))
              temp += ("性质学校数量" -> arrs(10))
              temp += ("性质学生人数" -> arrs(11))
              temp += ("性质教职工人数" -> arrs(12))
              temp

      }).toList
      ExportExcelByPoiUtil.createExcel(excelTitleValue3, sheetWidth3,
          Map("学校信息汇总表1" -> schoolLevel), Array(0, 1, 2, 3, 4, 5, 6, 7, 8), workbook,0)



    val stream = new FileOutputStream(file)
    workbook.write(stream)
    stream.close()
  }

}
