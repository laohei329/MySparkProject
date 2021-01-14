import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.spark.sql.sources.In;

import javax.xml.transform.Result;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class HiveServerTest {
    public static final Map<String, String> schDepartmentIdToNameMap = new LinkedHashMap<String, String>() {{
        put("1034109300011520", "黄浦区教育局");
        put("1034109300011521", "静安区教育局");
        put("1034109300011522", "徐汇区教育局");
        put("1034109300011523", "长宁区教育局");
        put("1034109300011524", "普陀区教育局");
        put("1034109300011525", "虹口区教育局");
        put("1034109300011526", "杨浦区教育局");
        put("1034109300011527", "闵行区教育局");
        put("1034109300011528", "嘉定区教育局");
        put("1034109300011529", "宝山区教育局");
        put("1034109300011530", "浦东新区教育局");
        put("1034109300011531", "松江区教育局");
        put("1034109300011532", "金山区教育局");
        put("1034109300011533", "青浦区教育局");
        put("1034109300011534", "奉贤区教育局");
        put("1034109300011535", "崇明区教育局");
        put("1034109300011519", "市教委");
        put("1034109300011537 ", "市经信委");


    }};

    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    final static String IP="172.20.105.147";
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {

        HashMap<String, String> map = new HashMap<>();
        map.put("committeeCode","1034109300011526");
        map.put("canteenModeCode","1");
        map.put("school_nature","1");

        Workbook workbook = getSchoolInfoExecl(map);
        workbook.write(new FileOutputStream(new File("school.xlsx")));

    }


    /**
     * 已经测试过  能正常的通过
     * todo 需要地址改成生产的
     * @param map
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */

    @SneakyThrows
    public static Workbook getCustomerInfoExecl(Map<String, String> map) throws ClassNotFoundException, SQLException {
        Class.forName(driverName);
        Connection con = DriverManager.getConnection("jdbc:hive2://" + IP + ":10000/ads");
        Statement stmt = con.createStatement();
        //查询学校的基础信息表

        Workbook workbook = new HSSFWorkbook();
        StringBuffer sb = new StringBuffer();
        StringBuffer sb2 = new StringBuffer();
        StringBuffer sb3 = new StringBuffer();
        ArrayList<Map<String, String>> dareList = new ArrayList<>();
        ArrayList<Map<String, String>> dareList2 = new ArrayList<>();
        ArrayList<Map<String, String>> dareList3 = new ArrayList<>();
        HashMap<String, List<Map<String, String>>> listHashMap = new HashMap<>();
        HashMap<String, List<Map<String, String>>> listHashMap2 = new HashMap<>();
        HashMap<String, List<Map<String, String>>> listHashMap3 = new HashMap<>();
        // String projectType  1:学校 2医院 3：养老
        String projectType = map.getOrDefault("projectType", null);

        if ("1".equals(projectType) || projectType == null) {
            //1.团餐公式的明细表
            sb.append("SELECT row_number()over() rn,school_name,area,level,school_nature,committee_name,canteen_mode,student_amount,staff_count,customer_name from ads_report_school_info");
            sb2.append("SELECT  name,sch_num,sup_num from ads.ads_company_collect_school");
            sb3.append("SELECT row_number() over(ORDER BY school_committee_name,sch_num DESC)rn,school_committee_name,name,sch_num,sup_num from ads.ads_catering_company_collect");
            String committeeCode = map.getOrDefault("committeeCode", null);
            //0全部 1：数据报表 然而目前没什么用
            String repType = map.getOrDefault("repType", "0");

            if (committeeCode != null) {
                sb.append(" where  1 = 1 ");
                sb2.append(" where  1 = 1 ");
                sb3.append(" where  1 = 1 ");
                String committee_name = AppModConfig.schDepartmentIdToNameMap.get(committeeCode);
                sb.append("  and committee_name = \'" + committee_name + "\'");
                //     sb2.append("and school_committee_name = \'" + committee_name + "\'");
                sb3.append(" and school_committee_name = \'" + committee_name + "\'");
            }
//            sb2.append(" GROUP BY name,sch_num,sup_num  ORDER BY sch_num DESC");

            Thread thread2 = new Thread() {
                @SneakyThrows
                @Override
                public void run() {
                    Connection con = DriverManager.getConnection("jdbc:hive2://172.20.105.147:10000/ads");
                    Statement stmt = con.createStatement();
                    ResultSet resultSet2 = stmt.executeQuery(sb2.toString());
                    //2团餐公司汇总表
                    //设置表格的宽度和表头 团餐公司的明细表

                    int sch2num = 0;
                    int sup2num = 0;
                    int i = 1;
                    while (resultSet2.next()) {
                        HashMap<String, String> dataMap = new HashMap<>();
                        dataMap.put("序号", i + "");
                        dataMap.put("企业名称", resultSet2.getString("name"));
                        Integer schStr = resultSet2.getInt("sch_num");
                       // sch2num += Integer.parseInt(schStr);
                        sch2num += schStr;
                        dataMap.put("服务学校数量", schStr+"");
                       // String supstr = resultSet2.getString("sup_num");
                        Integer supstr = resultSet2.getInt("sup_num");
                        //sup2num += Integer.parseInt(supstr);
                        sup2num += supstr;
                        dataMap.put("关联供应商数量", supstr + "");
                        i++;
                        dareList2.add(dataMap);

                    }

                    HashMap<String, String> lastdataMap2 = new HashMap<>();
                    lastdataMap2.put("序号", "总计");
                    lastdataMap2.put("企业名称", "");
                    lastdataMap2.put("服务学校数量", sch2num + "");
                    lastdataMap2.put("关联供应商数量", sup2num + "");
                    dareList2.add(lastdataMap2);

                    con.close();
                    listHashMap2.put("汇总", dareList2);

                }
            };

            Thread thread3 = new Thread() {
                @lombok.SneakyThrows
                @Override
                public void run() {
                    Connection con = DriverManager.getConnection("jdbc:hive2://172.20.105.147:10000/ads");
                    Statement stmt = con.createStatement();
                    ResultSet resultSet3 = stmt.executeQuery(sb3.toString());

                    //3团餐公司按区汇总表

                    String oldCommittee = "";
                    String newCommittee = "";
                    int sch_num = 0;
                    int supp_num = 0;
                    boolean isfirst = true;
                    while (resultSet3.next()) {

                        if (isfirst) {
                            oldCommittee = resultSet3.getString("school_committee_name");
                            newCommittee = resultSet3.getString("school_committee_name");
                            isfirst = false;
                        }

                        HashMap<String, String> dataMap = new HashMap<>();
                        oldCommittee = newCommittee;
                        newCommittee = resultSet3.getString("school_committee_name");
                        //如果出现区不一样
                        if (!oldCommittee.equals(newCommittee)) {
                            HashMap<String, String> dataMap2 = new HashMap<>();
                            dataMap2.put("主管部门", "总计");
                            dataMap2.put("企业名称", "");
                            dataMap2.put("服务学校数量", sch_num + "");
                            dataMap2.put("关联供应商数量", supp_num + "");
                            sch_num = 0;
                            supp_num = 0;
                            dareList3.add(dataMap2);
                        }
                        dataMap.put("主管部门", newCommittee);
                        dataMap.put("企业名称", resultSet3.getString("name"));
                        String schnum = resultSet3.getString("sch_num");
                        sch_num += Integer.parseInt(schnum);
                        String supnum = resultSet3.getString("sup_num");
                        supp_num += Integer.parseInt(supnum);
                        dataMap.put("服务学校数量", schnum);
                        dataMap.put("关联供应商数量", supnum);
                        dareList3.add(dataMap);

                    }

                    HashMap<String, String> lastdataMap3 = new HashMap<>();
                    lastdataMap3.put("主管部门", "总计");
                    lastdataMap3.put("企业名称", "");
                    lastdataMap3.put("服务学校数量", sch_num + "");
                    lastdataMap3.put("关联供应商数量", supp_num + "");
                    dareList3.add(lastdataMap3);
                    con.close();
                    listHashMap3.put("按主管部门统计", dareList3);


                }
            };

            thread2.start();
            thread3.start();

            ResultSet resultSet = stmt.executeQuery(sb.toString());
            //设置表格的宽度和表头 团餐公司的明细表
            int[] sheetWidth = {3000, 10500, 5500, 6500, 3500, 5500, 3500, 3500, 3500, 10500};
            String[] excelTitleValue = {"序号", "学校", "所在区", "学制",
                    "性质", "主管部门", "供餐模式", "学生人数", "教职工人数", "关联单位名称"};
            int stuNum = 0;
            int staffNum = 0;
            int n = 0;
            while (resultSet.next()) {

                HashMap<String, String> dataMap = new HashMap<>();
                dataMap.put("序号", resultSet.getString("rn"));
                dataMap.put("学校", resultSet.getString("school_name"));
                dataMap.put("所在区", resultSet.getString("area"));
                dataMap.put("学制", resultSet.getString("level"));
                dataMap.put("性质", resultSet.getString("school_nature"));
                dataMap.put("主管部门", resultSet.getString("committee_name"));
                dataMap.put("供餐模式", resultSet.getString("canteen_mode"));
                String stu_num = resultSet.getString("student_amount");
                stuNum += Integer.parseInt(stu_num);
                dataMap.put("学生人数", stu_num);
                String staffnum = resultSet.getString("staff_count");
                staffNum += Integer.parseInt(staffnum);
                dataMap.put("教职工人数", staffnum);
                dataMap.put("关联单位名称", resultSet.getString("customer_name"));
                n++;
                dareList.add(dataMap);

            }
            //最后一行
            HashMap<String, String> lasedataMap = new HashMap<>();
            lasedataMap.put("序号", "总计");
            lasedataMap.put("学校", "");
            lasedataMap.put("所在区", "");
            lasedataMap.put("学制", "");
            lasedataMap.put("性质", "");
            lasedataMap.put("主管部门", "");
            lasedataMap.put("供餐模式", "");
            lasedataMap.put("学生人数", stuNum + "");
            lasedataMap.put("教职工人数", staffNum + "");
            lasedataMap.put("关联单位名称", n + "");
            dareList.add(lasedataMap);

            listHashMap.put("团餐公式明细表", dareList);
            int[] mer = {};

            thread2.join();
            thread3.join();
            con.close();


            ExportExcelByPoiUtil.createExcel(excelTitleValue, sheetWidth, listHashMap, mer, workbook, 0);
            int[] mer2 = {};
            int[] sheetWidth2 = {3000, 13500, 5500, 6500};
            String[] excelTitleValue2 = {"序号", "企业名称", "服务学校数量", "关联供应商数量"};
            ExportExcelByPoiUtil.createExcel(excelTitleValue2, sheetWidth2, listHashMap2, mer2, workbook, 0, 1);
            int[] mer3 = {0};
            int[] sheetWidth3 = {5000, 13500, 5500, 6500};
            String[] excelTitleValue3 = {"主管部门", "企业名称", "服务学校数量", "关联供应商数量"};
            ExportExcelByPoiUtil.createExcel(excelTitleValue3, sheetWidth3, listHashMap3, mer3, workbook, 0, 2);


        }


        return workbook;
    }


    /**
     * 学校的供应关系表  最后的供应商数量太多  出现超出最大的问题
     * todo 需要地址改成生产的
     * @param map
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Workbook getSchoolSupplierExecl(Map<String, String> map) throws ClassNotFoundException, SQLException {

        Class.forName(driverName);
        Connection con = DriverManager.getConnection("jdbc:hive2://" + IP + ":10000/ads");
        Statement stmt = con.createStatement();
        //查询学校的基础信息表
        StringBuffer sb = new StringBuffer();
        //sb.append("SELECT  school_name,level2,school_nature,school_committee_name,catering_new_type,authorise,name, supplier_name from ads.ads_school_supplier_collect");
        sb.append("SELECT  school_name,level2,school_nature,school_committee_name,catering_new_type,authorise,name,supplier_name from ads_school_supplier ");
        ResultSet resultSet = null;
        Workbook workbook = new SXSSFWorkbook();

        String schoolNature = AppModConfig.schoolNatureIdToNameMap.getOrDefault(map.getOrDefault("schoolNatureCode", null), null);
        String level2 = map.getOrDefault("level2Code", null);
        String canteenModel = map.getOrDefault("canteenModeCode", null);
        String department = AppModConfig.schDepartmentIdToNameMap.get(map.getOrDefault("committeeCode", null));
        ArrayList<Map<String, String>> dataList = new ArrayList<>();

        if (map.size() != 0) {
            sb.append(" where 1=1 ");
            if (schoolNature != null) {
                sb.append("and school_nature = \'" + schoolNature + "\'");
            }
            if (level2 != null) {
                sb.append("and level2 = \'" + level2 + "\'");
            }
            if (canteenModel != null) {
                sb.append("and catering_new_type=\'" + canteenModel + "\'");
            }
            if (department != null) {
                sb.append("and school_committee_name=\'" + department + "\'");
            }
        }
        // sb.append(" GROUP BY  school_name,level2,school_nature,school_nature_code,school_committee_id,school_committee_name,catering_new_type,authorise,name,supplier_name");
        System.out.println("开始查询"+new Date());
        resultSet = stmt.executeQuery(sb.toString());


        /**
         * 查询学校的基础信息机表
         */
        int[] sheetWidth = {12500, 5500, 4500, 5500, 3500, 5500, 10500, 12000};
        String[] excelTitleValue = {"学校名称", "学制", "性质", "主管部门",
                "供餐模式", "是否授权交易平台", "关联单位", "供应商"};
        while (resultSet.next()) {
            HashMap<String, String> dateMap = new HashMap<>();
            dateMap.put("学校名称", resultSet.getString("school_name"));
            dateMap.put("学制", AppModConfig.schTypeIdToNameMap.get(Integer.parseInt(resultSet.getString("level2"))));
            dateMap.put("性质", resultSet.getString("school_nature"));
            dateMap.put("主管部门", resultSet.getString("school_committee_name"));
            dateMap.put("供餐模式", AppModConfig.canteenModeIdToNameMap.get(Integer.parseInt(resultSet.getString("catering_new_type"))));
            dateMap.put("是否授权交易平台", AppModConfig.isMealIdToNameMap.get(Integer.parseInt(resultSet.getString("authorise"))));
            dateMap.put("关联单位", resultSet.getString("name"));
            dateMap.put("供应商", resultSet.getString("supplier_name"));
            dataList.add(dateMap);
        }

        con.close();

        HashMap<String, List<Map<String, String>>> stringListHashMap = new HashMap<>();
        System.out.println("查询结束 开始execl 单元格操作"+new Date());
        stringListHashMap.put("供应关系明细表", dataList);
        int[] mer = {0, 1, 2, 3, 4, 5, 6};
        ExportExcelByPoiUtil.createExcelMergeByIndex(excelTitleValue, sheetWidth, stringListHashMap, mer, workbook, 0, 0, 0);
        System.out.println("单元格处理结束"+new Date());
        con.close();
        return workbook;
    }

    /**
     * 多线程实现 测试能通过
     * todo 需要地址改成生产的
     * @param map
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @SneakyThrows
    public static Workbook getSchoolInfoExecl(Map<String, String> map) throws ClassNotFoundException, SQLException {


        Class.forName(driverName);
        Connection con = DriverManager.getConnection("jdbc:hive2://" + IP + ":10000/ads");
        Statement stmt = con.createStatement();


        //查询学校的基础信息表
        StringBuffer sb = new StringBuffer();
        sb.append("select rn,area,school_name,social_credit_code,school_nature,level,committee_name,canteen_mode,student_amount,staff_count,corporation," +
                "corporation_phone,department_head,department_mobilephone,authorise,province,city,address,customer_name from ads.ads_report_school_info");
        StringBuffer sb2 = new StringBuffer();
        sb2.append("select area,area_sch_count,area_stu_sum,area_staff_sum,school_nature,natu_sch_count,natu_stu_sum,natu_staff_sum,level_code,code_sch_count,code_stu_sum,code_staff_sum,level,level_sch_count,level_stu_sum,level_staff_sum from ads.ads_report_school_group_nature");
        StringBuffer sb3 = new StringBuffer();
        sb3.append("select area,level_code,code_sch_count,code_stu_sum,code_staff_sum,level,level_sch_count,level_stu_sum,level_staff_sum,school_nature,natu_sch_count,natu_stu_sum,natu_staff_sum from ads.ads_report_school_group_level ");
        HashMap<String, List<Map<String, String>>> stringListHashMap2 = new HashMap<>();
        HashMap<String, List<Map<String, String>>> stringListHashMap = new HashMap<>();
        HashMap<String, List<Map<String, String>>> stringListHashMap3 = new HashMap<>();
        Workbook workbook = new HSSFWorkbook();


        String schoolNature = AppModConfig.schoolNatureIdToNameMap.getOrDefault(map.getOrDefault("schoolNatureCode", null), null);
        String level2 = AppModConfig.schTypeIdToNameMap.get(map.getOrDefault("level2Code", null));
        String canteenModel = AppModConfig.canteenModeIdToNameMap.get(map.getOrDefault("canteenModeCode", null));
        String department = AppModConfig.schDepartmentIdToNameMap.get(map.getOrDefault("committeeCode", null));
        ArrayList<Map<String, String>> dataList = new ArrayList<>();
        ArrayList<Map<String, String>> dataList2 = new ArrayList<>();
        ArrayList<Map<String, String>> dataList3 = new ArrayList<>();
        if (map.size() != 0) {
            sb.append(" where 1=1 ");
            sb2.append("  where 1=1 ");
            sb3.append("  where 1=1 ");
            if (!CommonUtil.isEmpty(schoolNature)) {
                sb.append("and  school_nature = \'" + schoolNature + "\'");
                sb2.append("and school_nature =  \'" + schoolNature + "\'");
                sb3.append("and school_nature =  \'" + schoolNature + "\'");
            }

            if (!CommonUtil.isEmpty(level2)) {
                sb.append("and level = \'" + level2 + "\'");
                sb2.append("and level =\'" + level2 + "\'");
                sb3.append("and level =\'" + level2 + "\'");
            }
            if (!CommonUtil.isEmpty(canteenModel)) {
                sb.append("and canteen_mode=\'" + canteenModel + "\'");
            }
            if (!CommonUtil.isEmpty(department)) {
                sb.append("and committee_name=\'" + department + "\'");
                sb2.append("and area=\'" + department + "\'");
                sb3.append(" and area=\'" + department + "\'");
            }
        }

        Thread thread = new Thread() {
            @SneakyThrows
            @Override
            public void run() {

                Connection con = DriverManager.getConnection("jdbc:hive2://172.20.105.147:10000/ads");
                Statement stmt = con.createStatement();
                ResultSet resultSet = stmt.executeQuery(sb.toString());
                int rn=1;
                while (resultSet.next()) {
                    HashMap<String, String> dateMap = new HashMap<>();
                    dateMap.put("序号", rn + "");
                    dateMap.put("区", resultSet.getString("area"));
                    dateMap.put("学校名称", resultSet.getString("school_name"));
                    dateMap.put("社会统一信用代码", resultSet.getString("social_credit_code"));
                    dateMap.put("办学性质", resultSet.getString("school_nature"));
                    dateMap.put("学制", resultSet.getString("level"));
                    dateMap.put("主管部门", resultSet.getString("committee_name"));
                    dateMap.put("供餐模式", resultSet.getString("canteen_mode"));
                    dateMap.put("学生人数", resultSet.getLong("student_amount") + "");
                    dateMap.put("教职工人数", resultSet.getLong("staff_count") + "");
                    dateMap.put("法定代表人", resultSet.getString("corporation"));
                    dateMap.put("法定代表人手机", resultSet.getString("corporation_phone"));
                    dateMap.put("联系人", resultSet.getString("department_head"));
                    dateMap.put("联系手机", resultSet.getString("department_mobilephone"));
                    dateMap.put("授权交易平台", resultSet.getString("authorise"));
                    dateMap.put("所在省", resultSet.getString("province"));
                    dateMap.put("所在市", resultSet.getString("city"));
                    dateMap.put("详细地址", resultSet.getString("address"));
                    dateMap.put("关联单位名称", resultSet.getString("customer_name"));
                    dataList.add(dateMap);
                    rn ++;
                }
                con.close();
                stringListHashMap.put("学校基础表", dataList);

            }
        };

        Thread thread2 = new Thread() {
            @SneakyThrows
            @Override
            public void run() {
                Connection con = DriverManager.getConnection("jdbc:hive2://172.20.105.147:10000/ads");
                Statement stmt = con.createStatement();
                ResultSet resultSet2 = stmt.executeQuery(sb2.toString());

                while (resultSet2.next()) {
                    HashMap<String, String> dateMap = new HashMap<>();
                    dateMap.put("主管部门", resultSet2.getString("area"));
                    dateMap.put("学校总数量", resultSet2.getLong("area_sch_count") + "");
                    dateMap.put("学生总人数", resultSet2.getLong("area_stu_sum") + "");
                    dateMap.put("教职工总人数", resultSet2.getLong("area_staff_sum") + "");
                    dateMap.put("办学性质", resultSet2.getString("school_nature"));
                    dateMap.put("学校数量", resultSet2.getLong("natu_sch_count") + "");
                    dateMap.put("学生数量", resultSet2.getLong("natu_stu_sum") + "");
                    dateMap.put("教职数量", resultSet2.getLong("natu_staff_sum") + "");
                    dateMap.put("学制小计", resultSet2.getString("level_code"));
                    dateMap.put("小计学校数量", resultSet2.getLong("code_sch_count") + "");
                    dateMap.put("小计学生人数", resultSet2.getLong("code_stu_sum") + "");
                    dateMap.put("小计教职工人数", resultSet2.getLong("code_staff_sum") + "");
                    dateMap.put("学制", resultSet2.getString("level"));
                    dateMap.put("学制学校数量", resultSet2.getLong("level_sch_count") + "");
                    dateMap.put("学制学生人数", resultSet2.getLong("level_stu_sum") + "");
                    dateMap.put("学制教职工人数", resultSet2.getLong("level_staff_sum") + "");
                    dataList2.add(dateMap);
                }
                con.close();

                stringListHashMap2.put("学校信息按性质汇总", dataList2);
            }
        };

        thread.start();
        thread2.start();

        ResultSet resultSet3 = stmt.executeQuery(sb3.toString());
        int[] sheetWidth3 = {4500, 3000, 3500, 3500, 3500, 6500, 3500, 3500, 3500, 4500, 3500, 3500, 3500};
        String[] excelTitleValue3 = {"主管部门",
                "学制小计", "小计学校数量", "小计学生人数", "小计教职工人数",
                "学制", "学制学校数量", "学制学生人数", "学制教职工人数",
                "性质", "性质学校数量", "性质学生人数", "性质教职工人数"};
        while (resultSet3.next()) {
            // select area,level_code,code_sch_count,code_stu_sum,code_staff_sum,level,
            // level_sch_count,level_stu_sum,level_staff_sum,school_nature,
            // natu_sch_count,natu_stu_sum,natu_staff_sum from ads.ads_report_school_group_level
            HashMap<String, String> dateMap = new HashMap<>();
            dateMap.put("主管部门", resultSet3.getString("area"));
            dateMap.put("学制小计", resultSet3.getString("level_code"));
            dateMap.put("小计学校数量", resultSet3.getLong("code_sch_count") + "");
            dateMap.put("小计学生人数", resultSet3.getLong("code_stu_sum") + "");
            dateMap.put("小计教职工人数", resultSet3.getLong("code_staff_sum") + "");
            dateMap.put("学制", resultSet3.getString("level"));
            dateMap.put("学制学校数量", resultSet3.getLong("level_sch_count") + "");
            dateMap.put("学制学生人数", resultSet3.getLong("level_stu_sum") + "");
            dateMap.put("学制教职工人数", resultSet3.getLong("level_staff_sum") + "");
            dateMap.put("性质", resultSet3.getString("school_nature"));
            dateMap.put("性质学校数量", resultSet3.getLong("natu_sch_count") + "");
            dateMap.put("性质学生人数", resultSet3.getLong("natu_stu_sum") + "");
            dateMap.put("性质教职工人数", resultSet3.getLong("natu_staff_sum") + "");
            dataList3.add(dateMap);
        }

        stringListHashMap3.put("学校信息按学制汇总", dataList3);
        int[] mer3 = {0, 1, 2, 3, 4, 5, 6, 7, 8};


        thread.join();
        thread2.join();
        con.close();

        ExportExcelByPoiUtil.createExcel(excelTitleValue3, sheetWidth3, stringListHashMap3, mer3, workbook, 0, 0);
        int[] mer = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        int[] sheetWidth2 = {4000, 3000, 3000, 3500, 3500, 3500, 3500, 3500, 3500, 3500, 3500, 3500, 5500, 3500, 3500, 3500};
        String[] excelTitleValue2 = {"主管部门", "学校总数量", "学生总人数", "教职工总人数",
                "办学性质", "学校数量", "学生数量", "教职数量",
                "学制小计", "小计学校数量", "小计学生人数", "小计教职工人数",
                "学制", "学制学校数量", "学制学生人数", "学制教职工人数"};
        ExportExcelByPoiUtil.createExcel(excelTitleValue2, sheetWidth2, stringListHashMap2, mer, workbook, 0, 1);


        int[] mer2 = {};
        int[] sheetWidth = {1500, 3500, 8000, 12500, 5500, 5500, 6500, 3500, 3500, 3500, 3500, 9500, 4500, 9500, 3500, 3500, 3500, 13500, 13500};
        String[] excelTitleValue = {"序号", "区", "学校名称", "社会统一信用代码",
                "办学性质", "学制", "主管部门", "供餐模式",
                "学生人数", "教职工人数", "法定代表人", "法定代表人手机",
                "联系人", "联系手机", "授权交易平台", "所在省", "所在市",
                "详细地址", "关联单位名称"};
        ExportExcelByPoiUtil.createExcel(excelTitleValue, sheetWidth, stringListHashMap, mer2, workbook, 0, 2);


        return workbook;
    }
}



