package com.xmomen.module.system.controller;

import com.xmomen.framework.mybatis.page.Page;
import com.xmomen.framework.web.exceptions.ArgumentValidException;
import com.xmomen.module.logger.Log;
import com.xmomen.module.system.model.DictionaryCreate;
import com.xmomen.module.system.model.DictionaryModel;
import com.xmomen.module.system.model.DictionaryQuery;
import com.xmomen.module.system.model.DictionaryUpdate;
import com.xmomen.module.system.service.DictionaryService;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

/**
 * @author  tanxinzheng
 * @date    2016-10-23 12:15:19
 * @version 1.0.0
 */
@RestController
@RequestMapping(value = "/dictionary")
public class DictionaryController {

    @Autowired
    DictionaryService dictionaryService;

    /**
     * 数据字典列表
     * @param   limit           每页结果数
     * @param   offset          页码
     * @param   keyword         关键字
     * @param   id              主键
     * @param   ids             主键数组
     * @param   excludeIds      不包含主键数组
     * @return  Page<DictionaryModel> 数据字典领域分页对象
     */
    @RequestMapping(method = RequestMethod.GET)
    @Log(actionName = "查询数据字典列表")
    public Page<DictionaryModel> getDictionaryList(@RequestParam(value = "limit") Integer limit,
                                  @RequestParam(value = "offset") Integer offset,
                                  @RequestParam(value = "keyword", required = false) String keyword,
                                  @RequestParam(value = "id", required = false) String id,
                                  @RequestParam(value = "ids", required = false) String[] ids,
                                  @RequestParam(value = "excludeIds", required = false) String[] excludeIds){
        DictionaryQuery dictionaryQuery = new DictionaryQuery();
        dictionaryQuery.setId(id);
        dictionaryQuery.setExcludeIds(excludeIds);
        dictionaryQuery.setIds(ids);
        dictionaryQuery.setKeyword(keyword);
        return dictionaryService.getDictionaryModelPage(limit, offset, dictionaryQuery);
    }

    /**
     * 查询单个数据字典
     * @param   id  主键
     * @return  DictionaryModel   数据字典领域对象
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @Log(actionName = "查询数据字典")
    public DictionaryModel getDictionaryById(@PathVariable(value = "id") String id){
        return dictionaryService.getOneDictionaryModel(id);
    }

    /**
     * 新增数据字典
     * @param   dictionaryCreate  新增对象参数
     * @param   bindingResult   参数校验结果
     * @return  DictionaryModel   数据字典领域对象
     */
    @RequestMapping(method = RequestMethod.POST)
    @Log(actionName = "新增数据字典")
    public DictionaryModel createDictionary(@RequestBody @Valid DictionaryCreate dictionaryCreate, BindingResult bindingResult) throws ArgumentValidException {
        if(bindingResult != null && bindingResult.hasErrors()){
            throw new ArgumentValidException(bindingResult);
        }
        return dictionaryService.createDictionary(dictionaryCreate);
    }

    /**
     * 更新数据字典
     * @param id                            主键
     * @param dictionaryUpdate 更新对象参数
     * @param bindingResult                 参数校验结果
     * @throws ArgumentValidException       参数校验异常类
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @Log(actionName = "更新数据字典")
    public void updateDictionary(@PathVariable(value = "id") String id,
                           @RequestBody @Valid DictionaryUpdate dictionaryUpdate, BindingResult bindingResult) throws ArgumentValidException {
        if(bindingResult != null && bindingResult.hasErrors()){
            throw new ArgumentValidException(bindingResult);
        }
        dictionaryService.updateDictionary(dictionaryUpdate);
    }

    /**
     *  删除数据字典
     * @param id    主键
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @Log(actionName = "删除单个数据字典")
    public void deleteDictionary(@PathVariable(value = "id") String id){
        dictionaryService.deleteDictionary(id);
    }

    /**
     *  删除数据字典
     * @param ids    主键
     */
    @RequestMapping(method = RequestMethod.DELETE)
    @Log(actionName = "批量删除数据字典")
    public void deleteDictionarys(@RequestParam(value = "ids") String[] ids){
        dictionaryService.deleteDictionary(ids);
    }

    /**
    * 导出
    * @param id     主键
    * @param ids    包含的主键数组
    * @param excludeIds     排除的主键数组
    * @param keyword    关键字
    * @param modelMap   modelMap对象
    * @return ModelAndView JEECG_EXCEL_VIEW Excel报表视图
    */
    @RequestMapping(value="/export", method = RequestMethod.GET)
    public ModelAndView exportDictionary(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "ids", required = false) String[] ids,
            @RequestParam(value = "excludeIds", required = false) String[] excludeIds,
            @RequestParam(value = "keyword", required = false) String keyword,
            ModelMap modelMap) {
        DictionaryQuery dictionaryQuery = new DictionaryQuery();
        dictionaryQuery.setId(id);
        dictionaryQuery.setExcludeIds(excludeIds);
        dictionaryQuery.setIds(ids);
        dictionaryQuery.setKeyword(keyword);
        List<DictionaryModel> dictionaryList = dictionaryService.getDictionaryModelList(dictionaryQuery);
        modelMap.put(NormalExcelConstants.FILE_NAME, "数据字典信息");
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams());
        modelMap.put(NormalExcelConstants.CLASS, DictionaryModel.class);
        modelMap.put(NormalExcelConstants.DATA_LIST, dictionaryList);
        return new ModelAndView(NormalExcelConstants.JEECG_EXCEL_VIEW);
    }


}
