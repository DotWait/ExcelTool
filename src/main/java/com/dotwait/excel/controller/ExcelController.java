package com.dotwait.excel.controller;

import com.dotwait.excel.entity.User;
import com.dotwait.excel.util.ExcelUtil;
import com.dotwait.excel.util.FakeDataUtil;
import com.dotwait.excel.util.TraceUtil;
import com.dotwait.excel.util.ZipUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Excel导出的Controller
 * <p>
 * 压缩需要导出的excel至zip文件后导出
 *
 * @author DotWait
 * @Date 2019-11-10
 */
@RestController
public class ExcelController {
    /**
     * 总数据量
     */
    private static final int TOTAL_NUMBER = 10 * 100000;
    /**
     * 单个excel的数据量
     */
    private static final int EXCEL_NUMBER = 100000;
    /**
     * excel文件的后缀名
     */
    private static final String EXCEL_SUFFIX = ".xlsx";
    /**
     * 导出的zip文件名
     */
    private static final String ZIP_FILE_NAME = "excel.zip";

    /**
     * 大数据量excel导出至zip
     *
     * @param response response
     * @throws IOException IO异常
     */
    @RequestMapping(value = "/export")
    public void export(HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
        /*
         * application/octet-stream
         * 这是应用程序文件的默认值。意思是 未知的应用程序文件 ，浏览器一般不会自动执行或询问执行。
         */
        response.setContentType("application/octet-stream; charset=utf-8");
        /*
         * 在常规的HTTP应答中，Content-Disposition 响应头指示回复的内容该以何种形式展示
         * attachment 意味着消息体应该被下载到本地
         */
        response.setHeader("Content-Disposition", "attachment; filename=" + ZIP_FILE_NAME);
        TraceUtil traceUtil = new TraceUtil();
        traceUtil.start();
        try {
            for (int i = 0; i < TOTAL_NUMBER / EXCEL_NUMBER + 1; i++) {
                TraceUtil trace = new TraceUtil();
                trace.start();
                //生成指定数量的假数据
                List<User> users = FakeDataUtil.generate(EXCEL_NUMBER);
                //数据写入excel
                SXSSFWorkbook workbook = ExcelUtil.export(users);
                //excel写入zip
                ZipUtil.excelWrite(i + EXCEL_SUFFIX, workbook, zipOutputStream);
                trace.stop("导出第" + i + "个excel成功");
            }
            traceUtil.stop("导出十个excel");
            zipOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            zipOutputStream.close();
            outputStream.close();
        }
    }

    /**
     * 读取的本地文件路径
     */
    private static final String READ_FILE_PATH = "D:/result.txt";
    /**
     * 写入的本地zip文件路径
     */
    private static final String WRITE_ZIP_PATH = "D:/result.zip";
    /**
     * 写入的zip中文件的名称
     */
    private static final String WRITE_FILE_NAME = "hello.txt";

    /**
     * 读取本地文件，写入zip文件后保存至本地
     *
     * @return 是否成功
     */
    @RequestMapping("/file")
    public String file() {
        File file = new File(READ_FILE_PATH);
        if (!file.exists()) {
            System.out.println(READ_FILE_PATH + " is not exist");
            return "failed";
        }

        FileInputStream fis = null;
        FileOutputStream fos = null;
        ZipOutputStream zipOutputStream = null;
        try {
            fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            fos = new FileOutputStream(new File(WRITE_ZIP_PATH));
            zipOutputStream = new ZipOutputStream(fos);
            zipOutputStream.putNextEntry(new ZipEntry(WRITE_FILE_NAME));
            int c;
            while ((c = fis.read(buffer)) != -1) {
                zipOutputStream.write(buffer, 0, c);
            }
            zipOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipOutputStream != null) {
                try {
                    zipOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "success";
    }
}
