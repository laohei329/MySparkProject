import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;


import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class AppModConfig {

	
	//消息id
	public static long msgId = 1;
	
	//最大页大小
	public static int maxPageSize = 64*1024;
	

	
	//初始化学校类型名称与ID映射Map
	@SuppressWarnings("serial")
	public static final Map<String, Integer> schTypeNameToIdMap = new HashMap<String, Integer>(){{
		put("托儿所", 0);
        put("托幼园", 1);
        put("托幼小", 2);
        put("幼儿园", 3);
        put("幼小", 4);
        put("幼小初", 5);
        put("幼小初高", 6);
        put("小学", 7);
        put("初级中学", 8);
        put("高级中学", 9);
        put("完全中学", 10);
        put("九年一贯制学校", 11);
        put("十二年一贯制学校", 12);
        put("职业初中", 13);
        put("中等职业学校", 14);
        put("工读学校", 15);
        put("特殊教育学校", 16);
        put("其他", 17);
    }};
    
    
    @SuppressWarnings("serial")
	public static final Map<Integer, String> schTypeIdToNameMap = new LinkedHashMap<Integer, String>(){{
		put(0, "托儿所");
        put(1, "托幼园");
        put(2, "托幼小");
        put(3, "幼儿园");
        put(4, "幼小");
        put(5, "幼小初");
        put(6, "幼小初高");
        put(7, "小学");
        put(8, "初级中学");
        put(9, "高级中学");
        put(10, "完全中学");
        put(11, "九年一贯制学校");
        put(12, "十二年一贯制学校");
        put(13, "职业初中");
        put(14, "中等职业学校");
        put(15, "工读学校");
        put(16, "特殊教育学校");
        put(17, "其他");
    }};
    
    
	//初始化学校类型名称与ID映射Map
	@SuppressWarnings("serial")
	public static final Map<String, String> schDepartmentNameToIdMap = new HashMap<String, String>(){{
		put("黄浦区教育局","1034109300011520");
		put("静安区教育局","1034109300011521");
		put("徐汇区教育局","1034109300011522");
		put("长宁区教育局","1034109300011523");
		put("普陀区教育局","1034109300011524");
		put("虹口区教育局","1034109300011525");
		put("杨浦区教育局","1034109300011526");
		put("闵行区教育局","1034109300011527");
		put("嘉定区教育局","1034109300011528");
		put("宝山区教育局","1034109300011529");
		put("浦东新区教育局","1034109300011530");
		put("松江区教育局","1034109300011531");
		put("金山区教育局","1034109300011532");
		put("青浦区教育局","1034109300011533");
		put("奉贤区教育局","1034109300011534");
		put("崇明区教育局","1034109300011535");
		put("市教委","1034109300011519");
		put("市经信委","1034109300011537 ");
    }};
    @SuppressWarnings("serial")
	public static final Map<String, String> schDepartmentIdToNameMap = new LinkedHashMap<String, String>(){{
		put("1034109300011520","黄浦区教育局");
		put("1034109300011521","静安区教育局");
		put("1034109300011522","徐汇区教育局");
		put("1034109300011523","长宁区教育局");
		put("1034109300011524","普陀区教育局");
		put("1034109300011525","虹口区教育局");
		put("1034109300011526","杨浦区教育局");
		put("1034109300011527","闵行区教育局");
		put("1034109300011528","嘉定区教育局");
		put("1034109300011529","宝山区教育局");
		put("1034109300011530","浦东新区教育局");
		put("1034109300011531","松江区教育局");
		put("1034109300011532","金山区教育局");
		put("1034109300011533","青浦区教育局");
		put("1034109300011534","奉贤区教育局");
		put("1034109300011535","崇明区教育局");
		put("1034109300011519","市教委");
		put("1034109300011537 ","市经信委");
		
		
    }};
    
    @SuppressWarnings("serial")
	public static final Map<String, String> companyAreaListIdToNameMap = new LinkedHashMap<String, String>(){{
		put("7608","上海市");
		put("8627","黄浦区");
		put("8628","徐汇区");
		put("8629","长宁区");
		put("8630","静安区");
		put("8631","普陀区");
		put("8633","虹口区");
		put("8634","杨浦区");
		put("8635","闵行区");
		put("8636","宝山区");
		put("8637","嘉定区");
		put("8638","浦东新区");
		put("8639","金山区");
		put("8640","松江区");
		put("8641","青浦区");
		put("8642","奉贤区");
		put("8643","崇明区");
		put("9685","龙华新区");
    }};
    
    @SuppressWarnings("serial")
   	public static final Map<String, String> schTypeNameToParentTypeNameMap = new HashMap<String, String>(){{
   		put("托儿所","托幼");
           put("托幼园","托幼");
           put("托幼小","托幼");
           put("幼儿园","托幼");
           put("幼小","托幼");
           put("幼小初","托幼");
           put("幼小初高","托幼");
           put("小学","中小学");
           put("初级中学","中小学");
           put("高级中学","中小学");
           put("完全中学","中小学");
           put("九年一贯制学校","中小学");
           put("十二年一贯制学校","中小学");
           put("职业初中","中小学");
           put("中等职业学校","中小学");
           put("工读学校","其他");
           put("特殊教育学校","其他");
           put("其他","其他");
    }};    
    
    
    //初始化经营模式名称与ID映射Map
  	@SuppressWarnings("serial")
  	public static final Map<String, Integer> canteenModeNameToIdMap = new HashMap<String, Integer>(){{
  		put("自营", 0);
  		put("托管", 1);
  		put("外送", 2);
    }};
    @SuppressWarnings("serial")
    public static final Map<Integer, String> canteenModeIdToNameMap = new HashMap<Integer, String>(){{
  		put(0, "自营");
  		put(1, "托管");  		
  		put(2, "外送");
    }};
    
    //初始化经营模式名称与ID映射Map
  	@SuppressWarnings("serial")
  	public static final Map<String, Integer> schoolNatureNameToIdMap = new HashMap<String, Integer>(){{
  		put("公办", 0);
  		put("民办", 1);
  		put("外籍人员子女学校", 2);
  		put("其他", 3);
    }};
    @SuppressWarnings("serial")
    public static final Map<Integer, String> schoolNatureIdToNameMap = new HashMap<Integer, String>(){{
  		put(0, "公办");
  		put(1, "民办");  		
  		put(2, "外籍人员子女学校");
  		put(3, "其他");
    }};
    
    
    
    //初始化经营模式名称与ID映射Map
  	@SuppressWarnings("serial")
  	public static final Map<String, Integer> optModeNameToIdMap = new HashMap<String, Integer>(){{
  		put("学校-自行加工", 0);
  		put("学校-食品加工商", 1);
  		put("外包-现场加工", 2);
  		put("外包-快餐配送", 3);
    }};
    @SuppressWarnings("serial")
    public static final Map<Integer, String> optModeIdToNameMap = new HashMap<Integer, String>(){{
  		put(0, "学校-自行加工");
  		put(1, "学校-食品加工商");  		
  		put(2, "外包-现场加工");
  		put(3, "外包-快餐配送");
    }};
    
    //初始化是否供餐名称与ID映射Map
  	@SuppressWarnings("serial")
  	public static final Map<String, Integer> isMealNameToIdMap = new HashMap<String, Integer>(){{
  		put("否", 0);
  		put("是", 1);
    }};
    @SuppressWarnings("serial")
  	public static final Map<Integer, String> isMealIdToNameMap = new HashMap<Integer, String>(){{
  		put(0, "否");
  		put(1, "是");
    }};
    
    //初始化是否排菜名称与ID映射Map
  	@SuppressWarnings("serial")
  	public static final Map<String, Integer> isDishNameToIdMap = new HashMap<String, Integer>(){{
  		put("未排菜", 0);
  		put("已排菜", 1);
    }};
    @SuppressWarnings("serial")
  	public static final Map<Integer, String> isDishIdToNameMap = new HashMap<Integer, String>(){{
  		put(0, "未排菜");
  		put(1, "已排菜");
    }};
    
    //初始化区域名称与ID映射Map
  	@SuppressWarnings("serial")
  	public static final Map<String, String> distNameToIdMap = new HashMap<String, String>(){{
  		put("黄浦区", "1");
  		put("静安区", "10");
  		put("徐汇区", "11");
  		put("长宁区", "12");
  		put("普陀区", "13");
  		put("虹口区", "14");
  		put("杨浦区", "15");
  		put("闵行区", "16");
  		put("嘉定区", "2");
  		put("宝山区", "3");
  		put("浦东新区", "4");
  		put("松江区", "5");
  		put("金山区", "6");
  		put("青浦区", "7");
  		put("奉贤区", "8");
  		put("崇明区", "9");
    }};
    @SuppressWarnings("serial")
  	public static final Map<String, String> distIdToNameMap = new HashMap<String, String>(){{
  		put("1", "黄浦区");
  		put("10", "静安区");
  		put("11", "徐汇区");
  		put("12", "长宁区");
  		put("13", "普陀区");
  		put("14", "虹口区");
  		put("15", "杨浦区");
  		put("16", "闵行区");
  		put("2", "嘉定区");
  		put("3", "宝山区");
  		put("4", "浦东新区");
  		put("5", "松江区");
  		put("6", "金山区");
  		put("7", "青浦区");
  		put("8", "奉贤区");
  		put("9", "崇明区");
    }};
    
    //初始化区域名称与ID映射Map
  	@SuppressWarnings("serial")
  	public static final Map<String, Integer> distNameToIdMap2 = new HashMap<String, Integer>(){{
  		put("浦东新区", 0);  		
  		put("黄浦区", 1);  		
  		put("静安区", 2);  		
  		put("徐汇区", 3);  		
  		put("长宁区", 4);
  		put("普陀区", 5);
  		put("虹口区", 6);  		
  		put("杨浦区", 7);
  		put("宝山区", 8);  		
  		put("闵行区", 9);  		
  		put("嘉定区", 10);  		
  		put("金山区", 11);  		
  		put("松江区", 12);  		
  		put("青浦区", 13);  		
  		put("奉贤区", 14);  		
  		put("崇明区", 15);  		
    }};
    @SuppressWarnings("serial")
  	public static final Map<Integer, String> distIdToNameMap2 = new HashMap<Integer, String>(){{
  		put(0, "浦东新区");  		
  		put(1, "黄浦区");  		
  		put(2, "静安区");  		
  		put(3, "徐汇区");  		
  		put(4, "长宁区");  		
  		put(5, "普陀区");  		
  		put(6, "虹口区");  		
  		put(7, "杨浦区");  		
  		put(8, "宝山区");
  		put(9, "闵行区");  		
  		put(10, "嘉定区");
  		put(11, "金山区");  		
  		put(12, "松江区");  		
  		put(13, "青浦区");  		
  		put(14, "奉贤区");  		
  		put(15, "崇明区");
    }};
    
    //初始化配送类型名称与ID映射Map
  	@SuppressWarnings("serial")
  	public static final Map<String, Integer> dispTypeNameToIdMap = new HashMap<String, Integer>(){{
  		put("原料", 0);
  		put("成品菜", 1);
    }};
    @SuppressWarnings("serial")
  	public static final Map<Integer, String> dispTypeIdToNameMap = new HashMap<Integer, String>(){{
  		put(0, "原料");
  		put(1, "成品菜");
    }};
    
    //初始化配送方式名称与ID映射Map
  	@SuppressWarnings("serial")
  	public static final Map<String, Integer> dispModeNameToIdMap = new HashMap<String, Integer>(){{
  		put("统配", 0);
  		put("直配", 1);
    }};
    @SuppressWarnings("serial")
  	public static final Map<Integer, String> dispModeIdToNameMap = new HashMap<Integer, String>(){{
  		put(0, "统配");
  		put(1, "直配");
    }};
    
    //初始化学校性质名称与ID映射Map
  	@SuppressWarnings("serial")
  	public static final Map<String, Integer> schPropNameToIdMap = new HashMap<String, Integer>(){{
  		put("公办", 0);
  		put("民办", 1);
  		put("外籍人员子女学校", 2);
  		put("其他", 3);
    }};
    @SuppressWarnings("serial")
  	public static final Map<Integer, String> schPropIdToNameMap = new HashMap<Integer, String>(){{
  		put(0, "公办");
  		put(1, "民办");
  		put(2, "外籍人员子女学校");
  		put(3, "其他");
    }};
    
    //初始化指派状态名称与ID映射Map
  	@SuppressWarnings("serial")
  	public static final Map<String, Integer> assignStatusNameToIdMap = new HashMap<String, Integer>(){{
  		put("未指派", 0);
  		put("已指派", 1);
  		put("已取消", 2);
    }};
    @SuppressWarnings("serial")
  	public static final Map<Integer, String> assignStatusIdToNameMap = new HashMap<Integer, String>(){{
  		put(0, "未指派");
  		put(1, "已指派");
  		put(2, "已取消");
    }};
    
    //初始化配送状态名称与ID映射Map
  	@SuppressWarnings("serial")
  	public static final Map<String, Integer> dispStatusNameToIdMap = new HashMap<String, Integer>(){{
  		put("未派送", 0);
  		put("配送中", 1);
  		put("已配送", 2);
    }};
    @SuppressWarnings("serial")
  	public static final Map<Integer, String> dispStatusIdToNameMap = new HashMap<Integer, String>(){{
  		put(0, "未派送");
  		put(1, "配送中");
  		put(2, "已配送");
    }};
    
    //初始化验收状态名称与ID映射Map
  	@SuppressWarnings("serial")
  	public static final Map<String, Integer> acceptStatusNameToIdMap = new HashMap<String, Integer>(){{
  		put("待验收", 0);
  		put("已验收", 1);
    }};
    @SuppressWarnings("serial")
  	public static final Map<Integer, String> acceptStatusIdToNameMap = new HashMap<Integer, String>(){{
  		put(0, "待验收");
  		put(1, "已验收");
    }};    
    
    //初始化发送状态名称与ID映射Map
  	@SuppressWarnings("serial")
  	public static final Map<String, Integer> sendFlagNameToIdMap = new HashMap<String, Integer>(){{
  		put("未发送", 0);
  		put("已发送", 1);
    }};
    @SuppressWarnings("serial")
  	public static final Map<Integer, String> sendFlagIdToNameMap = new HashMap<Integer, String>(){{
  		put(0, "未发送");
  		put(1, "已发送");
    }};
    
    //初始化是否确认名称与ID映射Map
  	@SuppressWarnings("serial")
  	public static final Map<String, Integer> confirmFlagNameToIdMap = new HashMap<String, Integer>(){{
  		put("未确认", 0);
  		put("已确认", 1);
    }};
    @SuppressWarnings("serial")
  	public static final Map<Integer, String> confirmFlagIdToNameMap = new HashMap<Integer, String>(){{
  		put(0, "未确认");
  		put(1, "已确认");
    }};
    
    //初始化餐别名称与ID映射Map
    @SuppressWarnings("serial")
  	public static final Map<String, Integer> caterTypeNameToIdMap = new HashMap<String, Integer>(){{
  		put("早餐", 0);
  		put("午餐", 1);
  		put("晚餐", 2);
  		put("午点", 3);
  		put("早点", 4);
    }};
    @SuppressWarnings("serial")
  	public static final Map<Integer, String> caterTypeIdToNameMap = new HashMap<Integer, String>(){{
  		put(0, "早餐");
  		put(1, "午餐");
  		put(2, "晚餐");
  		put(3, "午点");
  		put(4, "早点");
    }};
    
    //初始化原料类别名称与ID映射Map
    @SuppressWarnings("serial")
  	public static final Map<String, Integer> matCategoryNameToIdMap = new HashMap<String, Integer>(){{
  		put("主料", 0);
  		put("辅料", 1);
    }};
    @SuppressWarnings("serial")
  	public static final Map<Integer, String> matCategoryIdToNameMap = new HashMap<Integer, String>(){{
  		put(0, "主料");
  		put(1, "辅料");
    }};
    
    //初始化证件类型名称与ID映射Map
    @SuppressWarnings("serial")
  	public static final Map<String, Integer> licTypeNameToIdMap = new HashMap<String, Integer>(){{
  		put("食品经营许可证", 0);
  		put("营业执照", 1);
  		put("健康证", 2);
  		put("餐饮服务许可证", 3);
  		put("A1证", 4);
  		put("A2证", 5);
  		put("B证", 6);
  		put("C证", 7);
    }};
    @SuppressWarnings("serial")
  	public static final Map<Integer, String> licTypeIdToNameMap = new HashMap<Integer, String>(){{
  		put(0, "食品经营许可证");
  		put(1, "营业执照");
  		put(2, "健康证");
  		put(3, "餐饮服务许可证");
  		put(4, "A1证");
  		put(5, "A2证");
  		put(6, "B证");
  		put(7, "C证");
    }};
    
    //初始化证件预警审核状态名称与ID映射Map
    @SuppressWarnings("serial")
  	public static final Map<String, Integer> licAudStatusNameToIdMap = new HashMap<String, Integer>(){{
  		put("未处理", 0);
  		put("审核中", 1);
  		put("已消除", 2);
  		put("已驳回", 3);
    }};
    @SuppressWarnings("serial")
  	public static final Map<Integer, String> licAudStatusIdToNameMap = new HashMap<Integer, String>(){{
  		put(0, "未处理");
  		put(1, "审核中");
  		put(2, "已消除");
  		put(3, "已驳回");
    }};
    
    //初始化证件状况名称与ID映射Map
    @SuppressWarnings("serial")
  	public static final Map<String, Integer> licStatusNameToIdMap = new HashMap<String, Integer>(){{
  		put("逾期", 0);
  		put("到期", 1);
    }};
    @SuppressWarnings("serial")
  	public static final Map<Integer, String> licStatusIdToNameMap = new HashMap<Integer, String>(){{
  		put(0, "逾期");
  		put(1, "到期");
    }};
    
    //初始化总分校标识名称与ID映射Map
    @SuppressWarnings("serial")
  	public static final Map<String, Integer> genBraSchNameToIdMap = new HashMap<String, Integer>(){{
  		put("-", 0);
  		put("总校", 1);
  		put("分校", 2);
    }};
    @SuppressWarnings("serial")
  	public static final Map<Integer, String> genBraSchIdToNameMap = new HashMap<Integer, String>(){{
  		put(0, "-");
  		put(1, "总校");
  		put(2, "分校");
    }};   
    
    //初始化所属名称与ID映射Map
    @SuppressWarnings("serial")
  	public static final Map<String, Integer> subLevelNameToIdMap = new HashMap<String, Integer>(){{
  		put("其他", 0);
  		put("部属", 1);
  		put("市属", 2);
  		put("区属", 3);
    }};
    @SuppressWarnings("serial")
  	public static final Map<Integer, String> subLevelIdToNameMap = new HashMap<Integer, String>(){{
  		put(0, "其他");
  		put(1, "部属");
  		put(2, "市属");
  		put(3, "区属");
    }};    
    
    //初始化其他主管部门0名称与ID映射Map
    @SuppressWarnings("serial")
  	public static final Map<String, String> compDepNameToIdMap0 = new HashMap<String, String>(){{
  		put("其他", "0");
    }};
    @SuppressWarnings("serial")
  	public static final Map<String, String> compDepIdToNameMap0 = new HashMap<String, String>(){{
  		put("0", "其他");
    }};    
    
    //初始化部属主管部门1名称与ID映射Map
    @SuppressWarnings("serial")
  	public static final Map<String, String> compDepNameToIdMap1 = new HashMap<String, String>(){{
  		put("其他", "0");
  		put("教育部", "1");
    }};
    @SuppressWarnings("serial")
  	public static final Map<String, String> compDepIdToNameMap1 = new HashMap<String, String>(){{
  		put("0", "其他");
  		put("1", "教育部");
    }};
    
    //初始化市属主管部门2名称与ID映射Map
    @SuppressWarnings("serial")
  	public static final Map<String, String> compDepNameToIdMap2 = new HashMap<String, String>(){{
  		put("其他", "0");
  		put("市水务局（海洋局）", "1");
  		put("市农委", "2");
  		put("市交通委", "3");
  		put("市科委", "4");
  		put("市商务委", "5");
  		put("市经信委", "6");
  		put("市教委", "7");
    }};
    @SuppressWarnings("serial")
  	public static final Map<String, String> compDepIdToNameMap2 = new HashMap<String, String>(){{
  		put("0", "其他");
  		put("1", "市水务局（海洋局）");
  		put("2", "市农委");
  		put("3", "市交通委");
  		put("4", "市科委");
  		put("5", "市商务委");
  		put("6", "市经信委");
  		put("7", "市教委");
    }};
    
    //初始化区属主管部门3名称与ID映射Map（内部数据库映射）
    @SuppressWarnings("serial")
  	public static Map<String, String> compDepNameToIdMap3bd = new HashMap<String, String>(){{
  		put("黄浦区教育局", "e6ee4acf-2c5b-11e6-b1e8-005056a5ed30");
  		put("静安区教育局", "e6ee4bd5-2c5b-11e6-b1e8-005056a5ed30");
  		put("徐汇区教育局", "e6ee4c4f-2c5b-11e6-b1e8-005056a5ed30");
  		put("长宁区教育局", "e6ee4cb2-2c5b-11e6-b1e8-005056a5ed30");
  		put("普陀区教育局", "e6ee4d17-2c5b-11e6-b1e8-005056a5ed30");
  		put("虹口区教育局", "e6ee4d78-2c5b-11e6-b1e8-005056a5ed30");
  		put("杨浦区教育局", "e6ee4dd1-2c5b-11e6-b1e8-005056a5ed30");
  		put("闵行区教育局", "e6ee4e3f-2c5b-11e6-b1e8-005056a5ed30");
  		put("嘉定区教育局", "e6ee4e97-2c5b-11e6-b1e8-005056a5ed30");
  		put("宝山区教育局", "e6ee4eec-2c5b-11e6-b1e8-005056a5ed30");
  		put("浦东新区教育局", "e6ee4f43-2c5b-11e6-b1e8-005056a5ed30");
  		put("松江区教育局", "e6ee4fa4-2c5b-11e6-b1e8-005056a5ed30");
  		put("金山区教育局", "e6ee4ffa-2c5b-11e6-b1e8-005056a5ed30");
  		put("青浦区教育局", "e6ee5054-2c5b-11e6-b1e8-005056a5ed30");
  		put("奉贤区教育局", "e6ee50ac-2c5b-11e6-b1e8-005056a5ed30");
  		put("崇明区教育局", "e6ee5101-2c5b-11e6-b1e8-005056a5ed30");
  		put("其他", "0");
    }};
    @SuppressWarnings("serial")
  	public static Map<String, String> compDepIdToNameMap3bd = new HashMap<String, String>(){{
  		put("e6ee4acf-2c5b-11e6-b1e8-005056a5ed30", "黄浦区教育局");
  		put("e6ee4bd5-2c5b-11e6-b1e8-005056a5ed30", "静安区教育局");
  		put("e6ee4c4f-2c5b-11e6-b1e8-005056a5ed30", "徐汇区教育局");
  		put("e6ee4cb2-2c5b-11e6-b1e8-005056a5ed30", "长宁区教育局");
  		put("e6ee4d17-2c5b-11e6-b1e8-005056a5ed30", "普陀区教育局");
  		put("e6ee4d78-2c5b-11e6-b1e8-005056a5ed30", "虹口区教育局");
  		put("e6ee4dd1-2c5b-11e6-b1e8-005056a5ed30", "杨浦区教育局");
  		put("e6ee4e3f-2c5b-11e6-b1e8-005056a5ed30", "闵行区教育局");
  		put("e6ee4e97-2c5b-11e6-b1e8-005056a5ed30", "嘉定区教育局");
  		put("e6ee4eec-2c5b-11e6-b1e8-005056a5ed30", "宝山区教育局");
  		put("e6ee4f43-2c5b-11e6-b1e8-005056a5ed30", "浦东新区教育局");
  		put("e6ee4fa4-2c5b-11e6-b1e8-005056a5ed30", "松江区教育局");
  		put("e6ee4ffa-2c5b-11e6-b1e8-005056a5ed30", "金山区教育局");
  		put("e6ee5054-2c5b-11e6-b1e8-005056a5ed30", "青浦区教育局");
  		put("e6ee50ac-2c5b-11e6-b1e8-005056a5ed30", "奉贤区教育局");
  		put("e6ee5101-2c5b-11e6-b1e8-005056a5ed30", "崇明区教育局");
  		put("0", "其他");
    }};
    
    //初始化区属主管部门3名称与ID映射Map
    @SuppressWarnings("serial")
  	public static Map<String, String> compDepNameToIdMap3 = new HashMap<String, String>(){{
  		put("黄浦区教育局", "1");
  		put("静安区教育局", "10");
  		put("徐汇区教育局", "11");
  		put("长宁区教育局", "12");
  		put("普陀区教育局", "13");
  		put("虹口区教育局", "14");
  		put("杨浦区教育局", "15");
  		put("闵行区教育局", "16");
  		put("嘉定区教育局", "2");
  		put("宝山区教育局", "3");
  		put("浦东新区教育局", "4");
  		put("松江区教育局", "5");
  		put("金山区教育局", "6");
  		put("青浦区教育局", "7");
  		put("奉贤区教育局", "8");
  		put("崇明区教育局", "9");
  		put("其他", "0");
    }};
    @SuppressWarnings("serial")
  	public static Map<String, String> compDepIdToNameMap3 = new HashMap<String, String>(){{
  		put("1", "黄浦区教育局");
  		put("10", "静安区教育局");
  		put("11", "徐汇区教育局");
  		put("12", "长宁区教育局");
  		put("13", "普陀区教育局");
  		put("14", "虹口区教育局");
  		put("15", "杨浦区教育局");
  		put("16", "闵行区教育局");
  		put("2", "嘉定区教育局");
  		put("3", "宝山区教育局");
  		put("4", "浦东新区教育局");
  		put("5", "松江区教育局");
  		put("6", "金山区教育局");
  		put("7", "青浦区教育局");
  		put("8", "奉贤区教育局");
  		put("9", "崇明区教育局");
  		put("0", "其他");
    }};
    
    //初始化证件主体名称与ID映射Map
    @SuppressWarnings("serial")
  	public static final Map<String, Integer> fblMbNameToIdMap = new HashMap<String, Integer>(){{
  		put("学校", 0);
  		put("外包", 1);
    }};
    @SuppressWarnings("serial")
  	public static final Map<Integer, String> fblMbIdToNameMap = new HashMap<Integer, String>(){{
  		put(0, "学校");
  		put(1, "外包");
    }};
    
    //初始化学期设置名称与ID映射Map
    @SuppressWarnings("serial")
  	public static final Map<String, Integer> semSetNameToIdMap = new HashMap<String, Integer>(){{
  		put("未设置", 0);
  		put("已设置", 1);
    }};
    @SuppressWarnings("serial")
  	public static final Map<Integer, String> semSetIdToNameMap = new HashMap<Integer, String>(){{
  		put(0, "未设置");
  		put(1, "已设置");
    }};
    
    //初始化账号类型名称与ID映射Map
    @SuppressWarnings("serial")
  	public static final Map<String, Integer> accountTypeNameToIdMap = new HashMap<String, Integer>(){{
  		put("普通账号", 0);
  		put("管理员账号", 1);
    }};
    @SuppressWarnings("serial")
  	public static final Map<Integer, String> accountTypeIdToNameMap = new HashMap<Integer, String>(){{
  		put(0, "普通账号");
  		put(1, "管理员账号");
    }};
    
    //初始化用户状态名称与ID映射Map
    @SuppressWarnings("serial")
  	public static final Map<String, Integer> userStatusNameToIdMap = new HashMap<String, Integer>(){{
  		put("禁用", 0);
  		put("启用", 1);
    }};
    @SuppressWarnings("serial")
  	public static final Map<Integer, String> userStatusIdToNameMap = new HashMap<Integer, String>(){{
  		put(0, "禁用");
  		put(1, "启用");
    }};
    
    //初始化角色类型名称与ID映射Map
    @SuppressWarnings("serial")
  	public static final Map<String, Integer> roleTypeNameToIdMap = new HashMap<String, Integer>(){{
  		put("监管部门", 1);
  		put("学校", 2);
    }};
    @SuppressWarnings("serial")
  	public static final Map<Integer, String> roleTypeIdToNameMap = new HashMap<Integer, String>(){{
  		put(1, "监管部门");
  		put(2, "学校");
    }};    
    
    //初始化时间类型名称与ID映射Map
    @SuppressWarnings("serial")
  	public static final Map<String, Integer> timeTypeNameToIdMap = new HashMap<String, Integer>(){{
  		put("日", 0);
  		put("周", 1);
  		put("月", 2);
  		put("季度", 3);
  		put("年", 4);
    }};
    @SuppressWarnings("serial")
  	public static final Map<Integer, String> timeTypeIdToNameMap = new HashMap<Integer, String>(){{
  		put(0, "日");
  		put(1, "周");  		
  		put(2, "月");
  		put(3, "季度");
  		put(4, "年");
    }};    
        
	//消息id小于0判断
  	public static void msgIdLessThan0Judge() {
  		if(msgId < 0)
  			msgId = msgId & 0x7fffffffffffffffL;
  		if(msgId == 0)
  			msgId = 1;
  	}

  	
    //学校类型解析获取
  	public static String getSchType(String level, Integer level2) {
  		String schTypeName = "其他";
  		int curLevel2 = 0;
  		//依据新字段学制判断
  		if(level2 != null) {
  			curLevel2 = level2.intValue();
  		}
  		else {    //新学制字段为空时，依据旧学制判断
  			curLevel2 = -1;
  			level = level.trim();
  			int len = level.length();
  			int[] flIdxs = new int[10];
  			for(int i = 0; i < flIdxs.length; i++) {
  				String strIdx = String.valueOf(i);
  				flIdxs[i] = level.indexOf(strIdx);
  			}
  			if(len == 1 && flIdxs[7] != -1)
  				curLevel2 = 0;
  			else if(len == 3 && flIdxs[0] != -1 && flIdxs[7] != -1)
  				curLevel2 = 1;
  			else if(len == 5 && flIdxs[0] != -1 && flIdxs[1] != -1 && flIdxs[7] != -1)
  				curLevel2 = 2;
  			else if(len == 1 && flIdxs[0] != -1)
  				curLevel2 = 3;
  			else if(len == 3 && flIdxs[0] != -1 && flIdxs[1] != -1)
  				curLevel2 = 4;
  			else if(len == 5 && flIdxs[0] != -1 && flIdxs[1] != -1 && flIdxs[2] != -1)
  				curLevel2 = 5;
  			else if(len == 7 && flIdxs[0] != -1 && flIdxs[1] != -1 && flIdxs[2] != -1 && flIdxs[3] != -1)
  				curLevel2 = 6;
  			else if(len == 1 && flIdxs[1] != -1)
  				curLevel2 = 7;
  			else if(len == 1 && flIdxs[2] != -1)
  				curLevel2 = 8;
  			else if(len == 1 && flIdxs[3] != -1)
  				curLevel2 = 9;
  			else if(len == 3 && flIdxs[2] != -1 && flIdxs[3] != -1)
  				curLevel2 = 10;
  			else if(len == 3 && flIdxs[1] != -1 && flIdxs[2] != -1)
  				curLevel2 = 11;
  			else if(len == 5 && flIdxs[1] != -1 && flIdxs[2] != -1 && flIdxs[3] != -1)
  				curLevel2 = 12;
  			else if(len == 1 && flIdxs[6] != -1)
  				curLevel2 = 13;
  			else if(len == 3 && ((flIdxs[3] != -1 && flIdxs[6] != -1) || (flIdxs[0] != -1 && flIdxs[6] != -1) || (flIdxs[6] != -1 && flIdxs[8] != -1)))
  				curLevel2 = 14;
  			else if(len == 1 && flIdxs[9] != -1)
  				curLevel2 = 17;
  		}
  		//输出学校类型名称
  		if(curLevel2 == 0)
  			schTypeName = "托儿所";
  		else if(curLevel2 == 1)
  			schTypeName = "托幼园";
  		else if(curLevel2 == 2)
  			schTypeName = "托幼小";
  		else if(curLevel2 == 3)
  			schTypeName = "幼儿园";
  		else if(curLevel2 == 4)
  			schTypeName = "幼小";
  		else if(curLevel2 == 5)
  			schTypeName = "幼小初";
  		else if(curLevel2 == 6)
  			schTypeName = "幼小初高";
  		else if(curLevel2 == 7)
  			schTypeName = "小学";
  		else if(curLevel2 == 8)
  			schTypeName = "初级中学";
  		else if(curLevel2 == 9)
  			schTypeName = "高级中学";
  		else if(curLevel2 == 10)
  			schTypeName = "完全中学";
  		else if(curLevel2 == 11)
  			schTypeName = "九年一贯制学校";
  		else if(curLevel2 == 12)
  			schTypeName = "十二年一贯制学校";
  		else if(curLevel2 == 13)
  			schTypeName = "职业初中";
  		else if(curLevel2 == 14)
  			schTypeName = "中等职业学校";
  		else if(curLevel2 == 15)
  			schTypeName = "工读学校";
  		else if(curLevel2 == 16)
  			schTypeName = "特殊教育学校";
  		else if(curLevel2 == 17)
  			schTypeName = "其他";
  		
  		return schTypeName;
  	}  	
  	
  	//学校类型编号解析获取
  	public static Integer getSchTypeId(String level, Integer level2) {
  		int curLevel2 = 0;
  		//依据新字段学制判断
  		if(level2 != null) {
  			curLevel2 = level2.intValue();
  		}
  		else {    //新学制字段为空时，依据旧学制判断
  			curLevel2 = -1;
  			level = level.trim();
  			int len = level.length();
  			int[] flIdxs = new int[10];
  			for(int i = 0; i < flIdxs.length; i++) {
  				String strIdx = String.valueOf(i);
  				flIdxs[i] = level.indexOf(strIdx);
  			}
  			if(len == 1 && flIdxs[7] != -1)
  				curLevel2 = 0;
  			else if(len == 3 && flIdxs[0] != -1 && flIdxs[7] != -1)
  				curLevel2 = 1;
  			else if(len == 5 && flIdxs[0] != -1 && flIdxs[1] != -1 && flIdxs[7] != -1)
  				curLevel2 = 2;
  			else if(len == 1 && flIdxs[0] != -1)
  				curLevel2 = 3;
  			else if(len == 3 && flIdxs[0] != -1 && flIdxs[1] != -1)
  				curLevel2 = 4;
  			else if(len == 5 && flIdxs[0] != -1 && flIdxs[1] != -1 && flIdxs[2] != -1)
  				curLevel2 = 5;
  			else if(len == 7 && flIdxs[0] != -1 && flIdxs[1] != -1 && flIdxs[2] != -1 && flIdxs[3] != -1)
  				curLevel2 = 6;
  			else if(len == 1 && flIdxs[1] != -1)
  				curLevel2 = 7;
  			else if(len == 1 && flIdxs[2] != -1)
  				curLevel2 = 8;
  			else if(len == 1 && flIdxs[3] != -1)
  				curLevel2 = 9;
  			else if(len == 3 && flIdxs[2] != -1 && flIdxs[3] != -1)
  				curLevel2 = 10;
  			else if(len == 3 && flIdxs[1] != -1 && flIdxs[2] != -1)
  				curLevel2 = 11;
  			else if(len == 5 && flIdxs[1] != -1 && flIdxs[2] != -1 && flIdxs[3] != -1)
  				curLevel2 = 12;
  			else if(len == 1 && flIdxs[6] != -1)
  				curLevel2 = 13;
  			else if(len == 3 && ((flIdxs[3] != -1 && flIdxs[6] != -1) || (flIdxs[0] != -1 && flIdxs[6] != -1) || (flIdxs[6] != -1 && flIdxs[8] != -1)))
  				curLevel2 = 14;
  			else if(len == 1 && flIdxs[9] != -1)
  				curLevel2 = 17;
  		}
  		return curLevel2;
  	}
  	//输出学校性质
  	public static String getSchProp(String schoolNature) {
  		String schProp = "其他";
  		if(schoolNature != null) {
  			int[] flIdxs = new int[5];
  			for(int i = 0; i < flIdxs.length; i++) {
  				String strIdx = String.valueOf(i);
  				flIdxs[i] = schoolNature.indexOf(strIdx);
  			}
  			if(flIdxs[0] != -1 || flIdxs[1] != -1)
  				schProp = "公办";
  			else if(flIdxs[2] != -1 || flIdxs[3] != -1)
  				schProp = "民办";  		
  			//兼容学校性质判断
  			if(schoolNature.equals("0"))
  				schProp = "公办";
  			else if(schoolNature.equals("2"))
  				schProp = "民办";
  			else if(schoolNature.equals("3"))
  				schProp = "外籍人员子女学校";
  		}
  		
  		return schProp;
  	}
  	


  	

  	

}
