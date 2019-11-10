package com.dotwait.excel.util;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Zip压缩文件工具类
 *
 * @author DotWait
 * @Date 2019-11-10
 */
public class ZipUtil {
    /**
     * excel数据写入zip文件的输出流
     *
     * @param excelName       excel文件名称
     * @param workbook        excel的workbook
     * @param zipOutputStream zip文件输出流
     * @throws IOException IO异常
     */
    public static void excelWrite(String excelName, SXSSFWorkbook workbook,
                                  ZipOutputStream zipOutputStream) throws IOException {
        zipOutputStream.putNextEntry(new ZipEntry(excelName));
        workbook.write(zipOutputStream);
    }
}
