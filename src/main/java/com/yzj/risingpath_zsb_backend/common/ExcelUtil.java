package com.yzj.risingpath_zsb_backend.common;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

public class ExcelUtil {

    public static void process(String fileName, List target, Class clazs , HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
        EasyExcel.write(response.getOutputStream(), clazs).excelType(ExcelTypeEnum.XLS).autoCloseStream(Boolean.TRUE)
                .sheet("用户报表").doWrite(target);
    }
}
