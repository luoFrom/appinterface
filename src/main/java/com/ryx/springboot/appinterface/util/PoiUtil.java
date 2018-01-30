package com.ryx.springboot.appinterface.util;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * poi 读取 和写入 excel 工具类
 */
public class PoiUtil {

    /** logger **/
    private static final Logger logger= LoggerFactory.getLogger(PoiUtil.class);
    /**
     * 通过传入对象list集合写入excel表
     * @param contentList 需写入的list集合对象
     * @param outpath  excel输出路径，包含excel文件名
     * @param <T>
     * @throws IOException
     */
    public static <T>  void writeExcel(List<T> contentList , String outpath) throws IOException{

        if(contentList !=null && outpath !=null){
            //创建工作簿，若包含".xlsx"则认为是10版本，否则用03版
            Workbook wb=new HSSFWorkbook();
            if(outpath.contains(".xlsx")){
                wb=new XSSFWorkbook();
            }
            CreationHelper helper=wb.getCreationHelper();
            //
            Sheet sheet=wb.createSheet("sheet1");
            //利用反射获取传入对象第一个元素的所有属性
            Field[] fields=contentList.get(0).getClass().getDeclaredFields();
            Row row=null;
            Cell cell=null;
            //遍历内容list，每行
            for (int i=0;i<contentList.size();++i){
                //创建第一行
                row =sheet.createRow(i);
                //设置行高
                row.setHeightInPoints(20.0F);
                //遍历元素属性，每列
                for (int j=0;j<fields.length;j++){
                    //创建单元格样式
                    CellStyle cellStyle=createStyleCell(wb);
                    cell = row.createCell(j);
                    //设置居中对齐
                    cellStyle=setCellStyleAlignment(cellStyle);
                    cellStyle=setCellFormat(helper,cellStyle,"yyyy-MM-dd HH:mm:ss");
                    //设置单元格样式
                    cell.setCellStyle(cellStyle);
                    //获取当前行内容对象
                    Object o = contentList.get(i);
                    try {
                        String fi = fields[j].getName();
                        String mname = "get" + fi.substring(0, 1).toUpperCase() + fi.substring(1);
                        Method mt = o.getClass().getMethod(mname);
                        //反射方法过去值
                        String val = mt.invoke(o) != null ? PramTypeJudgeUtil.obj2Str(mt.invoke(o)) : "";
                        //将值写入单元格
                        cell.setCellValue(val);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            for (int j=0;j<fields.length;j++){
                //设置列宽自动调整
                sheet.autoSizeColumn(j);
            }
            //输出文件
            OutputStream os = new FileOutputStream(new File(outpath));
            wb.write(os);
            os.close();

        }else{
            System.err.println("传入参数存在空：contentList:"+contentList+"\t outpath:"+outpath);
            logger.error("传入参数存在空：contentList:"+contentList+"\t outpath:"+outpath);
        }

    }

    /***
     *  通用写入excel表，将需写入内容放入list集合
     * @param list
     * @param outpath
     * @throws IOException
     */
    public static void writeExcelUniversal(List<List<Object>> list, String outpath) throws IOException {
        if(list !=null && outpath !=null) {
            //创建工作簿，若包含".xlsx"则认为是10版本，否则用03版
            Workbook wb = new HSSFWorkbook();
            if (outpath.contains(".xlsx")) {
                wb = new XSSFWorkbook();
            }
            CreationHelper helper = wb.getCreationHelper();
            Sheet sheet1 = wb.createSheet("sheet1");
            Row row = null;
            Cell cell = null;
            int maxCol=0;
            //循环第一层list，每行
            for(int i = 0; i < list.size(); ++i) {
                List<Object> rowlist = (List)list.get(i);
                row = sheet1.createRow(i);
                row.setHeightInPoints(20.0F);
                //获取最大列数
                if(rowlist.size()>maxCol){
                    maxCol=rowlist.size();
                }

                //循环第二层list，每列
                for(int j = 0; j < rowlist.size(); ++j) {
                    CellStyle cellStyle = createStyleCell(wb);
                    cell = row.createCell(j);
                    cellStyle = setCellStyleAlignment(cellStyle);
                    cellStyle = setCellFormat(helper, cellStyle, "yyyy-MM-dd HH:mm:ss");
                    //设置单元格样式
                    cell.setCellStyle(cellStyle);
                    //设置单元格值
                    cell.setCellValue(PramTypeJudgeUtil.obj2Str(rowlist.get(j)));
                }
            }

            for (int j=0;j< maxCol;j++){
                //设置列宽自动调整
                sheet1.autoSizeColumn(j);
            }

            OutputStream os = new FileOutputStream(new File(outpath));
            wb.write(os);
            os.close();
        }else {
            System.err.println("传入参数存在空：contentList:"+list+"\t outpath:"+outpath);
            logger.error("传入参数存在空：contentList:"+list+"\t outpath:"+outpath);
        }

    }

    /**
     * 读取excel，返回传入对象的list集合
     * @param excelpath
     * @param t
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> readExcel(String excelpath, T t) throws Exception {
        InputStream is = new FileInputStream(new File(excelpath));
        Workbook wb = WorkbookFactory.create(is);
        List<T> list = new ArrayList();
        Sheet ts = wb.getSheetAt(0);
        //获取对象的所有属性
        Field[] fields = t.getClass().getDeclaredFields();

        if (ts != null) {
            //读取每行内容
            for(int rowN = 0; rowN <= ts.getLastRowNum(); ++rowN) {
                Row row = ts.getRow(rowN);
                if (row != null) {
                    Object obj = t.getClass().newInstance();

                    //循环次数小于对象的元素个数且小于表格列数
                    for(int i = 0; i < fields.length && i<row.getLastCellNum(); ++i) {
                        Cell cell = row.getCell(i);
                        String fi = fields[i].getName();
                        String sname = "set" + fi.substring(0, 1).toUpperCase() + fi.substring(1);
                        //利用反射注入读取的值
                        //获取属性的set方法
                        Method mt = obj.getClass().getMethod(sname, fields[i].getType());
                        //获取单元格的string类型的值
                        String strCellVal=PramTypeJudgeUtil.getCellStringValue(cell);
                        //注入数据
                        mt.invoke(obj, newInstance(fields[i].getType(),strCellVal) );

                    }
                    list.add((T) obj);
                }
            }
        }

        return list;
    }

    /**
     * 得到带构造的类的实例
     * 实现类的有参实例
     * */
    private static Object newInstance(Class clazz, Object... args){

        try {
            Class[] argsClass = new Class[args.length];
            for (int i = 0, j = args.length; i < j; i++) {
                argsClass[i] = args[i].getClass();
            }
            Constructor cons = clazz.getConstructor(argsClass);

            return cons.newInstance(args);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 通用方法读取excel返回list集合
     * @param excelpath
     * @return
     * @throws Exception
     */
    public static List<List<String>> readExcelUniversal(String excelpath) throws Exception {
        InputStream is = new FileInputStream(new File(excelpath));
        Workbook wb = WorkbookFactory.create(is);
        Sheet ts = wb.getSheetAt(0);
        List<List<String>> lastlist = new ArrayList();
        if (ts != null) {
            //读取每行内容
            for(int rowN = 0; rowN <= ts.getLastRowNum(); ++rowN) {
                //获取行对象
                Row row = ts.getRow(rowN);
                if (row != null) {
                    List<String> rowList = new ArrayList();
                    //读取每列
                    for(int i = 0; i < row.getLastCellNum(); ++i) {
                        //获取单元格对象
                        Cell cell = row.getCell(i);
                        //将获取的值放入行内容list集合
                        rowList.add(PramTypeJudgeUtil.getCellStringValue(cell));
                    }
                    //将行内容list集合放入总list集合
                    lastlist.add(rowList);
                }
            }
        }

        return lastlist;
    }

    /**
     * 创建样式
     * @param wb
     * @return
     */
    private static CellStyle createStyleCell(Workbook wb) {
        CellStyle cellStyle = wb.createCellStyle();
        //设置边框细线条
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        //设置黑色边框颜色
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        return cellStyle;
    }

    /**
     * 设置水平居中与垂直居中对齐样式
     * @param cellStyle
     * @return
     */
    private static CellStyle setCellStyleAlignment(CellStyle cellStyle) {
        //中间对齐
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        //垂直对齐，居中
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        return cellStyle;
    }

    /**
     * 设置单元格日期格式化
     * @param helper
     * @param cellStyle
     * @param fmt
     * @return
     */
    private static CellStyle setCellFormat(CreationHelper helper, CellStyle cellStyle, String fmt) {
        cellStyle.setDataFormat(helper.createDataFormat().getFormat(fmt));
        return cellStyle;
    }

    /**
     * 设置背景填充色
     * @param cellStyle
     * @param bg
     * @param fg
     * @param fp
     * @return
     */
    private static CellStyle setFillBackgroundColors(CellStyle cellStyle, short bg, short fg, short fp) {
        cellStyle.setFillForegroundColor(fg);
        cellStyle.setFillPattern(fp);
        return cellStyle;
    }

    /**
     * 设置字体
     * @param wb
     * @return
     */
    private static Font createFonts(Workbook wb) {
        Font font = wb.createFont();
        font.setFontName("黑体");
        font.setColor((short)12);
        font.setItalic(true);
        font.setFontHeight((short)300);
        return font;
    }
}
