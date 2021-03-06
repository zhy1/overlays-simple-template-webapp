package com.xmomen.framework.web.views;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.export.ExcelExportServer;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by tanxinzheng on 17/6/24.
 */
public class ExcelView extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook hssfWorkbook, HttpServletRequest httpServletRequest, HttpServletResponse response) throws Exception {
        String codedFileName = "临时文件";
        Workbook workbook = null;
//        if (model.containsKey(NormalExcelConstants.MAP_LIST)) {
//            List<Map<String, Object>> list = (List<Map<String, Object>>) model
//                    .get(NormalExcelConstants.MAP_LIST);
//            if (list.size() == 0) {
//                throw new RuntimeException("MAP_LIST IS NULL");
//            }
//            workbook = ExcelExportUtil.exportExcel(
//                    (ExportParams) list.get(0).get(NormalExcelConstants.PARAMS), (Class<?>) list.get(0)
//                            .get(NormalExcelConstants.CLASS),
//                    (Collection<?>) list.get(0).get(NormalExcelConstants.DATA_LIST));
//            for (int i = 1; i < list.size(); i++) {
//                new ExcelExportServer().createSheet(workbook,
//                        (ExportParams) list.get(i).get(NormalExcelConstants.PARAMS), (Class<?>) list
//                                .get(i).get(NormalExcelConstants.CLASS),
//                        (Collection<?>) list.get(i).get(NormalExcelConstants.DATA_LIST));
//            }
//        } else {
//            workbook = ExcelExportUtil.exportExcel(
//                    (ExportParams) model.get(NormalExcelConstants.PARAMS),
//                    (Class<?>) model.get(NormalExcelConstants.CLASS),
//                    (Collection<?>) model.get(NormalExcelConstants.DATA_LIST));
//        }
//        if (model.containsKey(NormalExcelConstants.FILE_NAME)) {
//            codedFileName = (String) model.get(NormalExcelConstants.FILE_NAME);
//        }
//        if (workbook instanceof HSSFWorkbook) {
//            codedFileName += HSSF;
//        } else {
//            codedFileName += XSSF;
//        }
//        if (isIE(request)) {
//            codedFileName = java.net.URLEncoder.encode(codedFileName, "UTF8");
//        } else {
//            codedFileName = new String(codedFileName.getBytes("UTF-8"), "ISO-8859-1");
//        }
        response.setHeader("content-disposition", "attachment;filename=" + codedFileName);
        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
    }
}
