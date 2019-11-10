package com.dotwait.excel.util;

import com.dotwait.excel.entity.User;
import org.apache.commons.collections4.ListUtils;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.List;

/**
 * Excel导出的工具类
 * <p>
 * POI各个工具的信息：
 * HSSFWorkbook:是操作Excel2003以前（包括2003）的版本，扩展名是.xls；
 * XSSFWorkbook:是操作Excel2007的版本，扩展名是.xlsx；
 * 从POI 3.8版本开始，提供了一种基于XSSF的低内存占用的API----SXSSF
 * 针对 SXSSF Beta 3.8下，会有临时文件产生，根据实际情况，使用workbook.dispose()删除临时文件
 * <p>
 * 区别：
 * 与XSSF的对比
 * 在一个时间点上，只可以访问一定数量的数据
 * 不再支持Sheet.clone()
 * 不再支持公式的求值
 * 在使用Excel模板下载数据时将不能动态改变表头，因为这种方式已经提前把excel写到硬盘的了就不能再改了
 *
 * @author DotWait
 * @Date 2019-11-10
 */
public class ExcelUtil {
    /**
     * workbook内存缓冲行数，默认值100
     */
    private static final int BUFFER_LINE = 100;
    /**
     * Sheet阈值，单个Sheet可写入行数的最大值
     */
    private static final int SHEET_THRESHOLD = 50000;
    /**
     * sheet名称前缀
     */
    private static final String SHEET_PREFIX = "sheet";

    /**
     * 导出Excel
     *
     * @param users 需要写入excel的假数据
     * @return excel的workbook
     */
    public static SXSSFWorkbook export(List<User> users) {
        //SXSSFWorkbook默认创建XSSFWorkbook
        SXSSFWorkbook workbook = new SXSSFWorkbook(BUFFER_LINE);
        //是否对临时文件进行压缩，压缩会使导出时间延长
        workbook.setCompressTempFiles(false);
        //是否需要多个Sheet
        if (users.size() <= SHEET_THRESHOLD) {
            SXSSFSheet sheet = workbook.createSheet(SHEET_PREFIX);
            setValueOfSheet(sheet, users);
        } else {
            List<List<User>> partition = ListUtils.partition(users, SHEET_THRESHOLD);
            for (int j = 0; j < partition.size(); j++) {
                SXSSFSheet sheet = workbook.createSheet(SHEET_PREFIX + j);
                setValueOfSheet(sheet, partition.get(j));
            }
        }
        return workbook;
    }

    /**
     * 将数据写入Sheet，每个Sheet的具体处理在此方法中，根据实际情况改变
     *
     * @param sheet excel的Sheet
     * @param users 假数据
     */
    private static void setValueOfSheet(SXSSFSheet sheet, List<User> users) {
        for (int i = 0; i < users.size(); i++) {
            SXSSFRow row = sheet.createRow(i);
            row.createCell(0).setCellValue(users.get(i).getName());
            row.createCell(1).setCellValue(users.get(i).getAge());
            row.createCell(2).setCellValue(users.get(i).getJob());
            row.createCell(3).setCellValue(users.get(i).getDate());
            row.createCell(4).setCellValue(users.get(i).getTimestamp());
            row.createCell(5).setCellValue(users.get(i).getData());
        }
    }
}
