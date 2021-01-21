import org.apache.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ExportExcelByPoiUtil {

    private static final Logger logger = (Logger) LogManager.getLogger(ExportExcelByPoiUtil.class.getName());

    /**
     * @param title      单元格表头的数组
     * @param widthAttr  单元格每列的宽度
     * @param maps       表名映射的数据
     * @param mergeIndex 需要合并的列的数组
     * @param workbook   传入的工作环境
     * @param startIndex 建表开始的行 包含表头所在行
     */
    public static void createExcel(String[] title, int[] widthAttr, Map<String/*sheet名*/,
            List<Map<String/*对应title的值*/, Object>>> maps, int[] mergeIndex, Workbook workbook, int startIndex) {
        if (title.length == 0) {
            return;
        }

        Sheet sheet = null;

        /*循环sheet页*/
        for (Map.Entry<String, List<Map<String/*对应title的值*/, Object>>> entry : maps.entrySet()) {
            /*实例化sheet对象并且设置sheet名称，book对象*/
            try {
                sheet = workbook.createSheet();
                workbook.setSheetName(0, entry.getKey());
                //workbook.setSelectedTab(0);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            // 设置样式 头 cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            // 水平方向的对齐方式
            CellStyle cellStyle_head = style(0, workbook);
            // 导出时间
            CellStyle cellStyle_export = style(3, workbook);
            // 标题
            CellStyle cellStyle_title = style(1, workbook);
            // 正文
            CellStyle cellStyle = style(2, workbook);

            // 设置列宽
            for (int i = 0; i < widthAttr.length; i++) {
                sheet.setColumnWidth((short) i, (short) widthAttr[i]);
            }

            Row row2 = sheet.createRow(startIndex);
            for (int i = 0; i < title.length; i++) {
                /*创建单元格，指定类型*/
                Cell cell_1 = row2.createCell(i, Cell.CELL_TYPE_STRING);
                //设置标题的值
                cell_1.setCellValue(title[i]);
                //设置标题样式
                cell_1.setCellStyle(cellStyle_title);
            }
            /*得到当前sheet下的数据集合*/
            List<Map<String/*对应title的值*/, Object>> list = entry.getValue();
            /*遍历该数据集合*/
            List<Poimodel> poiModels = new ArrayList();
            if (null != workbook) {
                Iterator iterator = list.iterator();

                int index = startIndex + 1;
                while (iterator.hasNext()) {
                    Row row = sheet.createRow(index);
                    Map<String, String> map = (Map<String, String>) iterator.next();
                    /*循环列数，给当前行塞值*/
                    for (int i = 0; i < title.length; i++) {

                        if (index == startIndex + 1) {
                            Poimodel model = new Poimodel(map.getOrDefault(title[i], ""), map.getOrDefault(title[i], ""), index, i);

                            poiModels.add(model);
                        }


                        for (int j : mergeIndex) {


                            if (j > 0 && i == j) {
                                if (!poiModels.get(j - 1).oldContent.equals(map.getOrDefault(title[i - 1], ""))) {

                                    if (poiModels.get(i).rowIndex != index - 1) {
                                        //  sheet.addMergedRegion(new CellRangeAddress(poiModels.get(i).rowIndex, index - 1, poiModels.get(i).cellIndex, poiModels.get(i).cellIndex));
                                        sheet.addMergedRegionUnsafe(new CellRangeAddress(poiModels.get(i).rowIndex, index - 1, poiModels.get(i).cellIndex, poiModels.get(i).cellIndex));

                                    }
                                    poiModels.set(i, new Poimodel(map.getOrDefault(title[i], ""), poiModels.get(i).content, index, i));

                                } else if (!poiModels.get(i).content.equals(map.getOrDefault(title[i], ""))) {

                                    if (poiModels.get(i).rowIndex != index - 1) {
                                        // sheet.addMergedRegion(new CellRangeAddress(poiModels.get(i).rowIndex, index - 1, poiModels.get(i).cellIndex, poiModels.get(i).cellIndex));
                                        sheet.addMergedRegionUnsafe(new CellRangeAddress(poiModels.get(i).rowIndex, index - 1, poiModels.get(i).cellIndex, poiModels.get(i).cellIndex));
                                        poiModels.set(i, new Poimodel(map.getOrDefault(title[i], ""), poiModels.get(i).content, index, i));

                                    } else {
                                        poiModels.set(i, new Poimodel(map.getOrDefault(title[i], ""), poiModels.get(i).content, index, i));

                                    }
                                } else {
                                    poiModels.set(i, new Poimodel(map.getOrDefault(title[i], ""), poiModels.get(i).content, poiModels.get(i).rowIndex, i));

                                }
                            }

                            //第一列 上下两行数据不一样是时
                            if (j == 0 && i == j) {
                                if (!poiModels.get(i).content.equals(map.getOrDefault(title[i], ""))) {
                                    if (poiModels.get(i).rowIndex != index - 1) {
                                        //  sheet.addMergedRegion(new CellRangeAddress(poiModels.get(i).rowIndex, index - 1, poiModels.get(i).cellIndex, poiModels.get(i).cellIndex));
                                        sheet.addMergedRegionUnsafe(new CellRangeAddress(poiModels.get(i).rowIndex, index - 1, poiModels.get(i).cellIndex, poiModels.get(i).cellIndex));
                                    }
                                    poiModels.set(i, new Poimodel(map.getOrDefault(title[i], ""), poiModels.get(i).content, index, i));

                                } else {
                                    poiModels.set(i, new Poimodel(map.getOrDefault(title[i], ""), poiModels.get(i).content, poiModels.get(i).rowIndex, i));

                                }

                            }
                            //最后几行处理
                            if (i == j && index == list.size()) {
                                if (poiModels.get(i).rowIndex != index) {
                                    CellRangeAddress cra = new CellRangeAddress(poiModels.get(i).rowIndex /*从第二行开始*/, index /*到第几行*/, poiModels.get(i).cellIndex /*从某一列开始*/, poiModels.get(i).cellIndex /*到第几列*/);
                                    //在sheet里增加合并单元格
                                    // sheet.addMergedRegion(cra);
                                    sheet.addMergedRegionUnsafe(cra);
                                    poiModels.set(i, new Poimodel(map.getOrDefault(title[i], ""), map.getOrDefault(title[i], ""), index, i));

                                }

                            }

                        }
                        Cell cell = row.createCell(i, Cell.CELL_TYPE_STRING);
                        cell.setCellValue(map.get(title[i]));
                        cell.setCellStyle(cellStyle);

                    }
                    index++;
                }
            }

        }

    }

    /**
     * @param title      单元格表头的数组
     * @param widthAttr  单元格每列的宽度
     * @param maps       表名映射的数据
     * @param mergeIndex 需要合并的列的数组
     * @param workbook   传入的工作环境
     * @param startIndex 建表开始的行 包含表头所在行
     * @param sheetnum   给第几个表命名
     */
    public static void createExcel(String[] title, int[] widthAttr, Map<String/*sheet名*/,
            List<Map<String/*对应title的值*/, Object>>> maps, int[] mergeIndex, Workbook workbook, int startIndex, int sheetnum) {
        if (title.length == 0) {
            return;
        }
        Sheet sheet = null;
        /*循环sheet页*/
        for (Map.Entry<String, List<Map<String/*对应title的值*/, Object>>> entry : maps.entrySet()) {
            /*实例化sheet对象并且设置sheet名称，book对象*/
            try {
                sheet = workbook.createSheet();
                workbook.setSheetName(sheetnum, entry.getKey());
                //workbook.setSelectedTab(0);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            // 设置样式 头 cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            // 水平方向的对齐方式
            CellStyle cellStyle_head = style(0, workbook);
            // 导出时间
            CellStyle cellStyle_export = style(3, workbook);
            // 标题
            CellStyle cellStyle_title = style(1, workbook);
            // 正文
            CellStyle cellStyle = style(2, workbook);
            // 设置列宽
            for (int i = 0; i < widthAttr.length; i++) {
                sheet.setColumnWidth((short) i, (short) widthAttr[i]);
            }
            Row row2 = sheet.createRow(startIndex);
            for (int i = 0; i < title.length; i++) {
                /*创建单元格，指定类型*/
                Cell cell_1 = row2.createCell(i, Cell.CELL_TYPE_STRING);
                //设置标题的值
                cell_1.setCellValue(title[i]);
                //设置标题样式
                cell_1.setCellStyle(cellStyle_title);
            }
            /*得到当前sheet下的数据集合*/
            List<Map<String/*对应title的值*/, Object>> list = entry.getValue();
            /*遍历该数据集合*/
            List<Poimodel> poiModels = new ArrayList();
            ArrayList<Integer> arrayList = new ArrayList<>();
            for (int index : mergeIndex) {
                arrayList.add(index);
            }
            if (null != workbook) {
                Iterator iterator = list.iterator();
                int index = startIndex + 1;
                boolean isStart = true;
                while (iterator.hasNext()) {
                    Row row = sheet.createRow(index);
                    Map<String, Object> map = (Map<String, Object>) iterator.next();
                    /*循环列数，给当前行塞值*/
                    for (int i = 0; i < title.length; i++) {
                        if (index == startIndex + 1) {
                            Poimodel model = new Poimodel(map.getOrDefault(title[i], ""), map.getOrDefault(title[i], ""), index, i);
                            poiModels.add(model);
                        }
                        for (int j : mergeIndex) {
                            if (j > 0 && i == j) {
                                if (!poiModels.get(j - 1).oldContent.equals(map.getOrDefault(title[i - 1], ""))) {
                                    createCellValue(map.get(title[i]), workbook, cellStyle, row.createCell(i));
                                    if (poiModels.get(i).rowIndex != index - 1) {
                                        sheet.addMergedRegionUnsafe(new CellRangeAddress(poiModels.get(i).rowIndex, index - 1, poiModels.get(i).cellIndex, poiModels.get(i).cellIndex));
                                    }
                                    poiModels.set(i, new Poimodel(map.getOrDefault(title[i], ""), poiModels.get(i).content, index, i));
                                } else if (!poiModels.get(i).content.equals(map.getOrDefault(title[i], ""))) {
                                    createCellValue(map.get(title[i]), workbook, cellStyle, row.createCell(i));
                                    if (poiModels.get(i).rowIndex != index - 1) {
                                        sheet.addMergedRegionUnsafe(new CellRangeAddress(poiModels.get(i).rowIndex, index - 1, poiModels.get(i).cellIndex, poiModels.get(i).cellIndex));
                                        poiModels.set(i, new Poimodel(map.getOrDefault(title[i], ""), poiModels.get(i).content, index, i));
                                    } else {
                                        poiModels.set(i, new Poimodel(map.getOrDefault(title[i], ""), poiModels.get(i).content, index, i));
                                    }
                                } else {
                                    createCellValue(null, workbook, cellStyle, row.createCell(i));
                                    poiModels.set(i, new Poimodel(map.getOrDefault(title[i], ""), poiModels.get(i).content, poiModels.get(i).rowIndex, i));
                                }
                                //第一行数据防止被覆盖 所以放到最后面执行
                                if (index == startIndex + 1) {
                                    createCellValue(map.get(title[i]), workbook, cellStyle, row.createCell(i));
                                }
                            }
                            //第一列 上下两行数据不一样是时
                            if (j == 0 && i == j) {
                                createCellValue(map.get(title[i]), workbook, cellStyle, row.createCell(i));
                                if (!poiModels.get(i).content.equals(map.getOrDefault(title[i], ""))) {
                                    if (poiModels.get(i).rowIndex != index - 1) {
                                        sheet.addMergedRegionUnsafe(new CellRangeAddress(poiModels.get(i).rowIndex, index - 1, poiModels.get(i).cellIndex, poiModels.get(i).cellIndex));
                                    }
                                    poiModels.set(i, new Poimodel(map.getOrDefault(title[i], ""), poiModels.get(i).content, index, i));
                                } else {
                                    poiModels.set(i, new Poimodel(map.getOrDefault(title[i], ""), poiModels.get(i).content, poiModels.get(i).rowIndex, i));
                                }
                            }
                            //最后几行处理
                            if (i == j && index == list.size()) {
                                if (poiModels.get(i).rowIndex != index) {
                                    CellRangeAddress cra = new CellRangeAddress(poiModels.get(i).rowIndex /*从第二行开始*/, index /*到第几行*/, poiModels.get(i).cellIndex /*从某一列开始*/, poiModels.get(i).cellIndex /*到第几列*/);
                                    //在sheet里增加合并单元格
                                    //   sheet.addMergedRegion(cra);
                                    sheet.addMergedRegionUnsafe(cra);
                                    poiModels.set(i, new Poimodel(map.getOrDefault(title[i], ""), map.getOrDefault(title[i], ""), index, i));
                                }
                            }
                        }

                        if (!arrayList.contains(i)) {
                            createCellValue(map.get(title[i]), workbook, cellStyle, row.createCell(i));
                        }


                    }
                    index++;
                }
            }

        }

    }


    /**
     *  单元格填数据
     * @param data
     * @param workbook
     * @param cellStyle
     * @param cell1
     */
    private static void createCellValue(Object data, Workbook workbook, CellStyle cellStyle, Cell cell1) {
        //  Cell cell = row.createCell(i, Cell.CELL_TYPE_STRING);
        Cell cell = cell1;
        // Boolean isNum = false;//data是否为数值型
        Boolean isInteger = false;//data是否为整数
        // Boolean isPercent=false;//data是否为百分数
        String dataType = null;
        if (data != null || "".equals(data)) {
            //判断data是否为数值型
            //  isNum = data.toString().matches("^(-?\\d+)(\\.\\d+)?$");
            //判断data是否为整数（小数部分是否为0）
            isInteger = data.toString().matches("^[-\\+]?[\\d]*$");
            // System.out.println("=================="+data.getClass().getName());
            //判断data是否为百分数（是否包含“%”）
            // isPercent=data.toString().contains("%");
            dataType = data.getClass().getName();
        }

        //如果单元格内容是数值类型，涉及到金钱（金额、本、利），则设置cell的类型为数值型，设置data的类型为数值类型
        if (dataType != "java.lang.String") {
            // if (!isPercent) {
            DataFormat df = workbook.createDataFormat(); // 此处设置数据格式
            if (isInteger) {
                cellStyle.setDataFormat(df.getFormat("##0"));//数据格式只显示整数

            } else {
                cellStyle.setDataFormat(df.getFormat("###0.00"));//保留两位小数点

            }
            // 设置单元格格式
            cell.setCellStyle(cellStyle);
            // 设置单元格内容为double类型
            if (data != null) {
                cell.setCellValue(Double.parseDouble(data.toString()));
            } else {
                cell.setCellValue(Double.parseDouble("0"));
            }
        } else {
            cell.setCellStyle(cellStyle);
            // 设置单元格内容为字符型
            if (data != null)
                cell.setCellValue(String.valueOf(data));
        }
    }


    /**
     * 根据指定的类判断是否合并
     *
     * @param title
     * @param widthAttr
     * @param maps
     * @param mergeIndex
     * @param workbook
     * @param startIndex
     * @param sheetnum
     */
    public static void createExcelMergeByIndex(String[] title, int[] widthAttr, Map<String/*sheet名*/,
            List<Map<String/*对应title的值*/, Object>>> maps, int[] mergeIndex, Workbook workbook, int startIndex, int sheetnum, int mergebyIndex) {
        if (title.length == 0) {
            return;
        }

        Sheet sheet = null;

        /*循环sheet页*/
        for (Map.Entry<String, List<Map<String/*对应title的值*/, Object>>> entry : maps.entrySet()) {
            /*实例化sheet对象并且设置sheet名称，book对象*/
            try {
                sheet = workbook.createSheet();
                workbook.setSheetName(sheetnum, entry.getKey());
                //workbook.setSelectedTab(0);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            // 设置样式 头 cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            // 水平方向的对齐方式
            CellStyle cellStyle_head = style(0, workbook);
            // 导出时间
            CellStyle cellStyle_export = style(3, workbook);
            // 标题
            CellStyle cellStyle_title = style(1, workbook);
            // 正文
            CellStyle cellStyle = style(2, workbook);

            // 设置列宽
            for (int i = 0; i < widthAttr.length; i++) {
                sheet.setColumnWidth((short) i, (short) widthAttr[i]);
            }

            Row row2 = sheet.createRow(startIndex);
            for (int i = 0; i < title.length; i++) {
                /*创建单元格，指定类型*/
                Cell cell_1 = row2.createCell(i, Cell.CELL_TYPE_STRING);
                //设置标题的值
                cell_1.setCellValue(title[i]);
                //设置标题样式
                cell_1.setCellStyle(cellStyle_title);
            }
            /*得到当前sheet下的数据集合*/
            List<Map<String/*对应title的值*/, Object>> list = entry.getValue();
            /*遍历该数据集合*/
            List<Poimodel> poiModels = new ArrayList();

            ArrayList<Integer> arrayList = new ArrayList<>();
            for (int index : mergeIndex) {
                arrayList.add(index);
            }

            if (null != workbook) {
                Iterator iterator = list.iterator();

                int index = startIndex + 1;
                boolean isStart = true;
                while (iterator.hasNext()) {
                    Row row = sheet.createRow(index);
                    Map<String, Object> map = (Map<String, Object>) iterator.next();
                    /*循环列数，给当前行塞值*/
                    for (int i = 0; i < title.length; i++) {

                        if (index == startIndex + 1) {
                            Poimodel model = new Poimodel(map.getOrDefault(title[i], ""), map.getOrDefault(title[i], ""), index, i);
                            poiModels.add(model);
                        }

                        for (int j : mergeIndex) {

                            if (j > 0 && i == j) {
                                if (!poiModels.get(mergebyIndex).oldContent.equals(map.getOrDefault(title[mergebyIndex], ""))) {
                                    createCellValue(map.get(title[i]), workbook, cellStyle, row.createCell(i));
                                    if (poiModels.get(i).rowIndex != index - 1) {
                                        //  sheet.addMergedRegion(new CellRangeAddress(poiModels.get(i).rowIndex, index - 1, poiModels.get(i).cellIndex, poiModels.get(i).cellIndex));
                                        sheet.addMergedRegionUnsafe(new CellRangeAddress(poiModels.get(i).rowIndex, index - 1, poiModels.get(i).cellIndex, poiModels.get(i).cellIndex));

                                    }
                                    poiModels.set(i, new Poimodel(map.getOrDefault(title[i], ""), poiModels.get(i).content, index, i));

                                } else if (!poiModels.get(i).content.equals(map.getOrDefault(title[i], ""))) {
                                    createCellValue(map.get(title[i]), workbook, cellStyle, row.createCell(i));
                                    if (poiModels.get(i).rowIndex != index - 1) {
                                        //  sheet.addMergedRegion(new CellRangeAddress(poiModels.get(i).rowIndex, index - 1, poiModels.get(i).cellIndex, poiModels.get(i).cellIndex));
                                        sheet.addMergedRegionUnsafe(new CellRangeAddress(poiModels.get(i).rowIndex, index - 1, poiModels.get(i).cellIndex, poiModels.get(i).cellIndex));
                                        poiModels.set(i, new Poimodel(map.getOrDefault(title[i], ""), poiModels.get(i).content, index, i));

                                    } else {

                                        poiModels.set(i, new Poimodel(map.getOrDefault(title[i], ""), poiModels.get(i).content, index, i));

                                    }
                                } else {
                                    createCellValue(null, workbook, cellStyle, row.createCell(i));
                                    poiModels.set(i, new Poimodel(map.getOrDefault(title[i], ""), poiModels.get(i).content, poiModels.get(i).rowIndex, i));

                                }

                                //第一行数据防止被覆盖 所以放到最后面执行
                                if (index == startIndex + 1) {
                                    createCellValue(map.get(title[i]), workbook, cellStyle, row.createCell(i));
                                    // logger.error( "       "+ map.get(title[i]));
                                }
                            }

                            //第一列 上下两行数据不一样是时
                            if (j == 0 && i == j) {
                                createCellValue(map.get(title[i]), workbook, cellStyle, row.createCell(i));
                                if (!poiModels.get(i).content.equals(map.getOrDefault(title[i], ""))) {
                                    //  createCellValue(map.get(title[i]), workbook, cellStyle, row.createCell(i));
                                    if (poiModels.get(i).rowIndex != index - 1) {
                                        //   sheet.addMergedRegion(new CellRangeAddress(poiModels.get(i).rowIndex, index - 1, poiModels.get(i).cellIndex, poiModels.get(i).cellIndex));
                                        sheet.addMergedRegionUnsafe(new CellRangeAddress(poiModels.get(i).rowIndex, index - 1, poiModels.get(i).cellIndex, poiModels.get(i).cellIndex));
                                    }

                                    poiModels.set(i, new Poimodel(map.getOrDefault(title[i], ""), poiModels.get(i).content, index, i));
                                } else {
                                    // createCellValue(map.get(title[i]), workbook, cellStyle, row.createCell(i));
                                    poiModels.set(i, new Poimodel(map.getOrDefault(title[i], ""), poiModels.get(i).content, poiModels.get(i).rowIndex, i));

                                }

                            }
                            //最后几行处理
                            if (i == j && index == list.size()) {
                                if (poiModels.get(i).rowIndex != index) {
                                    CellRangeAddress cra = new CellRangeAddress(poiModels.get(i).rowIndex /*从第二行开始*/, index /*到第几行*/, poiModels.get(i).cellIndex /*从某一列开始*/, poiModels.get(i).cellIndex /*到第几列*/);
                                    //在sheet里增加合并单元格
                                    //   sheet.addMergedRegion(cra);
                                    sheet.addMergedRegionUnsafe(cra);
                                    poiModels.set(i, new Poimodel(map.getOrDefault(title[i], ""), map.getOrDefault(title[i], ""), index, i));
                                }
                            }
                        }

                        if (!arrayList.contains(i)) {
                            createCellValue(map.get(title[i]), workbook, cellStyle, row.createCell(i));
                        }


                    }
                    index++;
                }
            }

        }

    }



    /**
     * @param @return 设定文件  index 0:头 1：标题 2：正文
     * @return HSSFCellStyle    返回类型
     * @throws
     * @Title: style
     * @Description: TODO(样式)
     * @author: GMY
     * @date: 2018年5月9日 下午5:16:48
     */
    public static CellStyle style(int index, Workbook workbook) {
        CellStyle cellStyle = null;
        if (index == 0) {
            // 设置头部样式
            cellStyle = workbook.createCellStyle();
            // 设置字体大小 位置
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            // 生成一个字体
            Font font = workbook.createFont();
            //设置字体
            font.setFontName("微软雅黑");
            //字体颜色
            font.setColor(HSSFColor.BLACK.index);

            // HSSFColor.VIOLET.index

            font.setFontHeightInPoints((short) 12);
            font.setBold(true);// 字体增粗

            cellStyle.setFillForegroundColor(HSSFColor.GREEN.index); //背景白色
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setFont(font);
        }
        //标题
        if (index == 1) {
            cellStyle = workbook.createCellStyle();
            // 设置字体大小 位置
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            // 生成一个字体
            Font font_title = workbook.createFont();
            //设置字体
            font_title.setFontName("微软雅黑");
            font_title.setColor(HSSFColor.BLACK.index);// HSSFColor.VIOLET.index

            //字体颜色
            font_title.setFontHeightInPoints((short) 10);
            font_title.setBold(true); // 字体增粗

            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
            cellStyle.setFillForegroundColor(HSSFColor.BLUE_GREY.index); //背景白色
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            //cellStyle.setWrapText(true) ;// 自动换行

            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setFont(font_title);
        }
        //正文
        if (index == 2) {
            // 设置样式
            cellStyle = workbook.createCellStyle();
            // 设置字体大小 位置
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            // 生成一个字体// 生成一个字体

            Font font_title = workbook.createFont();
            //设置字体
            font_title.setFontName("微软雅黑");
            //cellStyle.setWrapText(true) ;// 自动换行
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setFillForegroundColor(HSSFColor.WHITE.index); //背景白色
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setFont(font_title);
        }
        //时间
        if (index == 3) {
            cellStyle = workbook.createCellStyle();
            // 设置字体大小 位置
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            // 生成一个字体// 生成一个字体
            Font font_title = workbook.createFont();
            //设置字体
            font_title.setFontName("微软雅黑");
            font_title.setColor(HSSFColor.BLACK.index);// HSSFColor.VIOLET.index
            font_title.setFontHeightInPoints((short) 10);
            font_title.setBold(false); // 字体增粗
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);

            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 上下居中
            cellStyle.setFont(font_title);

        }
        if (index == 4) {
            // 设置样式
            cellStyle = workbook.createCellStyle();
            // 居中
            // cellStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
        }
        return cellStyle;
    }

    /**
     * @param @param sheet
     * @param @param region
     * @param @param cs    设定文件
     * @return void    返回类型
     * @throws
     * @Title: setRegionStyle
     * @Description: TODO(合并单元格后边框不显示问题)
     */
    public static void setRegionStyle(Sheet sheet, CellRangeAddress region, CellStyle cs) {
        for (int i = region.getFirstRow(); i <= region.getLastRow(); i++) {
            Row row = CellUtil.getRow(i, sheet);
            for (int j = region.getFirstColumn(); j <= region.getLastColumn(); j++) {
                Cell cell = CellUtil.getCell(row, (short) j);
                cell.setCellStyle(cs);
            }
        }
    }

}