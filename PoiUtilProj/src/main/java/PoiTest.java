import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PoiTest {
    public static void main(String[] args) {

         InputStream INPUTSTREAM = new PoiTest().getClass().getClassLoader().getResourceAsStream("000000_0");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(INPUTSTREAM));
        String str=null;
        ArrayList arrayList = new ArrayList<Map<String,String>>();

            try {
                while ((str=bufferedReader.readLine()) != null) {
                    String[] strs = str.split(" ");
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("区号",strs[0]);
                    map.put("学制小计",strs[1]);
                    map.put("小计学校数量",strs[2]);
                    map.put("小计学生人数",strs[3]);
                    map.put("小计教职工人数",strs[4]);
                    map.put("学制",strs[5]);
                    map.put("学制学校数量",strs[6]);
                    map.put("学制学生人数",strs[7]);
                    map.put("学制教职工人数",strs[8]);
                    map.put("性质",strs[9]);
                    map.put("性质学校数量",strs[10]);
                    map.put("性质学生人数",strs[11]);
                    map.put("性质教职工人数",strs[12]);
                    arrayList.add(map);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        String dd = "xxxxx.xls";

        File file2 = new File(dd);

        Workbook workbook = new HSSFWorkbook();


        int [] sheetWidth3 = {4000, 3000, 4500, 3500, 3500, 4500, 3500, 3500, 3500, 4500, 3500, 3500, 3500};
        int [] merInt={0, 1, 2, 3, 4, 5, 6, 7, 8};
        HashMap<String, List<Map<String, String>>> titleMap = new HashMap<String, List<Map<String, String>>>();
        titleMap.put("学校信息汇总表1",arrayList);
        String[] excelTitleValue3 = {"区号", "学制小计", "小计学校数量", "小计学生人数", "小计教职工人数",
                "学制", "学制学校数量", "学制学生人数", "学制教职工人数",
                "性质", "性质学校数量", "性质学生人数", "性质教职工人数"};
        ExportExcelByPoiUtil.createExcel(excelTitleValue3, sheetWidth3,
                titleMap,merInt , workbook,0);
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file2);
            workbook.write(stream);

            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }




    }
}
