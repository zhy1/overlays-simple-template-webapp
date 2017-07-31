package com.xmomen.framework.poi;

import com.xmomen.framework.exception.BusinessException;
import com.xmomen.module.system.model.DictionaryModel;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by tanxinzheng on 17/7/30.
 */
public class ExcelUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelImportUtil.class);

    /**
     * 导出Excel View
     * @param clazz
     * @param data
     * @param filename
     * @param <T>
     * @return
     */
    public static <T> ModelAndView export(ModelMap modelMap, Class clazz, List<T> data, String filename){
        return export(modelMap, clazz, data, filename, new ExportParams());
    }

    /**
     * 导出Excel View
     * @param clazz
     * @param data
     * @param filename
     * @param exportParams
     * @param <T>
     * @return
     */
    public static <T> ModelAndView export(ModelMap modelMap, Class clazz, List<T> data, String filename, ExportParams exportParams){
        String datetime = DateFormatUtils.ISO_DATE_FORMAT.format(new Date());
        modelMap.put(NormalExcelConstants.FILE_NAME,  filename + "_" + datetime);
        modelMap.put(NormalExcelConstants.PARAMS, exportParams);
        modelMap.put(NormalExcelConstants.CLASS, clazz);
        modelMap.put(NormalExcelConstants.DATA_LIST, data);
        return new ModelAndView(NormalExcelConstants.JEECG_EXCEL_VIEW);
    }

    /**
     * Excel转换为实体对象
     * @param multipartFile
     * @param model
     * @param <T>
     * @return
     */
    public static <T> List<T> transform(MultipartFile multipartFile, Class<T> model){
        return transform(multipartFile, model, new ImportParams());
    }

    /**
     * Excel转换为实体对象
     * @param multipartFile
     * @param model
     * @param importParams
     * @param <T>
     * @return
     */
    public static <T> List<T> transform(MultipartFile multipartFile, Class<T> model, ImportParams importParams){
        try {
            if(multipartFile.isEmpty()){
                throw new BusinessException("文件不能空");
            }
            if(!(multipartFile.getOriginalFilename().endsWith(".xls")
                    || multipartFile.getOriginalFilename().endsWith(".xlsx"))){
                throw new BusinessException("请选择正确格式的Excel文件上传，文件后缀（.xls）或（.xlsx)");
            }
            return transform(multipartFile.getInputStream(), model, importParams);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new BusinessException("Excel导入失败，错误信息：" + e.getMessage());
        }
    }

    /**
     * Excel转换为实体对象
     * @param inputStream
     * @param model
     * @param <T>
     * @return
     */
    public static <T> List<T> transform(InputStream inputStream, Class<T> model){
        return transform(inputStream, model, new ImportParams());
    }

    /**
     * Excel转换为实体对象
     * @param file
     * @param model
     * @param <T>
     * @return
     */
    public static <T> List<T> transform(File file, Class<T> model){
        return transform(file, model, new ImportParams());
    }

    /**
     * Excel转换为实体对象
     * @param file
     * @param model
     * @param importParams
     * @param <T>
     * @return
     */
    public static <T> List<T> transform(File file, Class<T> model, ImportParams importParams){
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
            throw new BusinessException("Excel导入失败，错误信息：未找到指定文件");
        }
        return transform(in, model, importParams);
    }

    /**
     * Excel转换为实体对象
     * @param inputStream
     * @param model
     * @param importParams
     * @param <T>
     * @return
     */
    public static <T> List<T> transform(InputStream inputStream, Class<T> model, ImportParams importParams){
        try {
            return ExcelImportUtil.importExcel(inputStream, model, importParams);
        } catch (Exception e) {
            throw new BusinessException("Excel模板不正确，导入失败");
        }
    }


}
