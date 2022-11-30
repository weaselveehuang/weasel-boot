package com.weasel.common.base

import ai.yue.library.base.util.ParamUtils
import ai.yue.library.base.validation.Validator
import ai.yue.library.base.view.R
import ai.yue.library.base.view.Result
import ai.yue.library.base.view.ResultEnum
import cn.hutool.core.annotation.AnnotationUtil
import cn.hutool.core.collection.CollUtil
import cn.hutool.core.collection.ListUtil
import cn.hutool.core.convert.Convert
import cn.hutool.core.util.*
import cn.hutool.extra.spring.SpringUtil
import com.alibaba.excel.EasyExcel
import com.alibaba.excel.ExcelWriter
import com.alibaba.excel.context.AnalysisContext
import com.alibaba.excel.read.listener.ReadListener
import com.alibaba.excel.support.ExcelTypeEnum
import com.alibaba.excel.write.builder.ExcelWriterBuilder
import com.alibaba.excel.write.metadata.WriteSheet
import com.alibaba.fastjson.JSON
import com.baomidou.mybatisplus.annotation.OrderBy
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.service.IService
import com.ejlchina.searcher.BeanSearcher
import com.ejlchina.searcher.SearchResult
import com.ejlchina.searcher.boot.BeanSearcherProperties
import com.fhs.trans.service.impl.TransService
import com.weasel.common.base.excel.form.ExportForm
import com.weasel.common.consts.Consts
import com.weasel.modules.sys.util.TreeUtil
import groovy.util.logging.Slf4j
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

import javax.servlet.http.HttpServletResponse
import java.nio.charset.StandardCharsets
import java.util.stream.Collectors

/**
 * @author weasel
 * @date 2022/7/22 14:42
 * @version 1.0
 */
@Api(tags = '数据操作')
@Slf4j
@RestController
@RequestMapping("/_d")
class DynamicController {
    @Autowired
    Validator validator
    @Autowired
    BeanSearcherProperties beanSearcherProperties
    @Autowired
    TransService transService

    /**
     * Test @RequestParam
     *
     * @param moduleName 模块名
     * @param entityName 实体名
     */
    @ApiOperation(value = '查询列表', notes = '根据筛选条件查询多条数据，支持分页')
    @ApiImplicitParam(name = "moduleName")
    @GetMapping(['/{moduleName}/{entityName}'])
    Result list(@PathVariable @ApiParam('模块名') String moduleName, @PathVariable @ApiParam('业务名') String entityName) {
        def beanSearcher = SpringUtil.getBean(BeanSearcher)
        def entityType = getEntityType(moduleName, entityName)
        Map<String, Object> searchParams = buildSearchParams(entityType)
        BeanSearcherProperties.Params.Pagination pagination = beanSearcherProperties.getParams().getPagination()

        if (TreeEntity.isAssignableFrom(entityType)) {
            def all = beanSearcher.searchAll(entityType, null)
            def query = beanSearcher.searchAll(entityType, searchParams)
            transService.transMore(query)
            query = TreeUtil.build(all, query)
            return R.success(query)
        } else if (searchParams.containsKey(pagination.getPage()) && searchParams.containsKey(pagination.getSize())) {
            SearchResult page = beanSearcher.search(entityType, searchParams)
            transService.transMore(page.getDataList())
            return R.success(page)
        } else {
            def all = beanSearcher.searchAll(entityType, searchParams)
            transService.transMore(all)
            return R.success(all)
        }
    }

    @GetMapping('/{moduleName}/{entityName}/{id}')
    Result getById(@PathVariable String moduleName, @PathVariable String entityName, @PathVariable Long id) {
        R.success(getEntityType(moduleName, entityName).newInstance().selectById(id))
    }

    @PostMapping('/{moduleName}/{entityName}')
    Result save(@PathVariable String moduleName, @PathVariable String entityName) {
        def entityType = getEntityType(moduleName, entityName)
        def entity = ParamUtils.getParam(entityType)
        validator.valid(entity, Consts.ValidateGroup.SAVE)

        IControllerHandler controllerHandler = getControllerHandler(entityType)
        // step1: beforeSave
        controllerHandler.beforeSave(entity)
        // step2: save
        def insert = entity.insert()
        // step3: afterSave
        controllerHandler.afterSave(entity)
        R.success()
    }

    @PutMapping('/{moduleName}/{entityName}')
    Result update(@PathVariable String moduleName, @PathVariable String entityName) {
        def entityType = getEntityType(moduleName, entityName)
        def entity = ParamUtils.getParam(entityType)
        validator.valid(entity, Consts.ValidateGroup.UPDATE)

        IControllerHandler controllerHandler = getControllerHandler(entityType)
        // step1: beforeUpdate
        controllerHandler && controllerHandler.beforeUpdate(entity)
        // step2: update
        entity.updateById()
        // step3: afterUpdate
        controllerHandler && controllerHandler.afterUpdate(entity)
        R.success()
    }

    @DeleteMapping('/{moduleName}/{entityName}')
    Result remove(@PathVariable String moduleName, @PathVariable String entityName) {
        def entityType = getEntityType(moduleName, entityName)
        def entity = ParamUtils.getParam(entityType)

        IControllerHandler controllerHandler = getControllerHandler(entityType)
        // step1: beforeDelete
        controllerHandler && controllerHandler.beforeDelete(entity)
        // step2: delete
        entity.deleteById()
        // step3: afterDelete
        controllerHandler && controllerHandler.afterDelete(entity)
        R.success()
    }

    @DeleteMapping('/{moduleName}/{entityName}/batch')
    Result removeBatch(@PathVariable String moduleName, @PathVariable String entityName, @RequestBody def entities) {
        def entityType = getEntityType(moduleName, entityName)
        entities = Convert.toList(entityType, entities)

        IControllerHandler controllerHandler = getControllerHandler(entityType)
        // step1: beforeBatchDelete
        controllerHandler && controllerHandler.beforeBatchDelete(entities)
        // step2: batchDelete
        entityType.newInstance().delete(Wrappers.query().in('id', entities*.id))
        // step3: afterDelete
        controllerHandler && controllerHandler.afterBatchDelete(entities)
        R.success()
    }

    @PostMapping('/{moduleName}/{entityName}/export')
    void export(@PathVariable String moduleName, @PathVariable String entityName, ExportForm exportForm, HttpServletResponse response) {
        try {
//            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
//            response.setCharacterEncoding("utf-8")
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLUtil.encode(exportForm.filename, CharsetUtil.CHARSET_UTF_8).replaceAll("\\+", "%20")
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName)

            ExcelWriter excelWriter = null
            try {
                def entityType = getEntityType(moduleName, entityName)

                ExcelWriterBuilder excelWriterBuilder = EasyExcel.write(response.getOutputStream(), entityType).autoCloseStream(Boolean.FALSE).excelType(exportForm.bookType)
                if (exportForm.bookType == ExcelTypeEnum.CSV) {
                    excelWriterBuilder = excelWriterBuilder.charset(CharsetUtil.CHARSET_GBK)
                }
                excelWriter = excelWriterBuilder.build()
                // 这里注意 如果同一个sheet只要创建一次
                WriteSheet writeSheet = EasyExcel.writerSheet().includeColumnFieldNames(ListUtil.toList(exportForm.onlySelect)).build()
                writeData(entityType, exportForm, excelWriter, writeSheet)
            } finally {
                // 千万别忘记finish 会帮忙关闭流
                if (excelWriter != null) {
                    excelWriter.finish()
                }
            }
        } catch (Exception e) {
            e.printStackTrace()
            // 重置response
            response.reset()
            response.setContentType(MediaType.APPLICATION_JSON_VALUE)
            response.setCharacterEncoding(StandardCharsets.UTF_8.name())
            response.setStatus(ResultEnum.ERROR_PROMPT.getCode())
            response.getWriter().println(JSON.toJSONString(R.errorPrompt("导出文件失败")))
        }
    }

    private void writeData(def entityType, ExportForm exportForm, ExcelWriter excelWriter, WriteSheet writeSheet, int ... _page) {
        def beanSearcher = SpringUtil.getBean(BeanSearcher)
        BeanSearcherProperties.Params.Pagination pagination = beanSearcherProperties.getParams().getPagination()
        String pageField = pagination.getPage()
        String sizeField = pagination.getSize()
        int page = pagination.getStart()
        if (_page) page = _page[0]
        int size = pagination.getDefaultSize()
        Map<String, Object> searchParams = [:]
        searchParams.put('onlySelect', exportForm.onlySelect)
        switch (exportForm.scopeType) {
            case ExportForm.ScopeType.all:
                searchParams.put(pageField, page)
                List list = beanSearcher.searchList(entityType, searchParams)
                if (CollUtil.isNotEmpty(list)) {
                    transService.transMore(list)
                    excelWriter.write(list, writeSheet)
                    page++
                    writeData(entityType, exportForm, excelWriter, writeSheet, page)
                }
                break
            case ExportForm.ScopeType.filtered:
                searchParams.putAll(buildSearchParams(entityType))
                searchParams.put(pageField, page)
                searchParams.put(sizeField, size)
                List list = beanSearcher.searchList(entityType, searchParams)
                if (CollUtil.isNotEmpty(list)) {
                    transService.transMore(list)
                    excelWriter.write(list, writeSheet)
                    page++
                    writeData(entityType, exportForm, excelWriter, writeSheet, page)
                }
                break
            case ExportForm.ScopeType.selected:
                def ids = ParamUtils.getParam().ids
                searchParams.put('id', ids)
                searchParams.put('id-op', 'il')
                List list = beanSearcher.searchList(entityType, searchParams)
                if (CollUtil.isNotEmpty(list)) {
                    transService.transMore(list)
                    excelWriter.write(list, writeSheet)
                }
                break
            case ExportForm.ScopeType.page:
                searchParams.putAll(buildSearchParams(entityType))
                searchParams.put(pageField, page)
                searchParams.put(sizeField, size)
                List list = beanSearcher.searchList(entityType, searchParams)
                if (CollUtil.isNotEmpty(list)) {
                    transService.transMore(list)
                    excelWriter.write(list, writeSheet)
                }
                break
        }
    }

    @PostMapping('/{moduleName}/{entityName}/import')
    Result 'import'(@PathVariable String moduleName, @PathVariable String entityName, MultipartFile file) {
        def entityType = getEntityType(moduleName, entityName)
        EasyExcel.read(file.getInputStream(), entityType, new ReadListener<?>() {
            /**
             * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
             */
            private static final int BATCH_COUNT = 100
            /**
             * 缓存的数据
             */
            private List<?> cachedDataList = new ArrayList<>(BATCH_COUNT)

            /**
             * 这个每一条数据解析都会来调用
             *
             * @param data one row value. Is is same as {@link com.alibaba.excel.context.AnalysisContext#readRowHolder()}
             * @param context
             */
            @Override
            void invoke(Object data, AnalysisContext context) {
                log.info("解析到一条数据:{}", JSON.toJSONString(data))
                cachedDataList.add(data)
                // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
                if (cachedDataList.size() >= BATCH_COUNT) {
                    saveData()
                    // 存储完成清理 list
                    cachedDataList = new ArrayList<>(BATCH_COUNT)
                }
            }

            /**
             * 所有数据解析完成了 都会来调用
             *
             * @param context
             */
            @Override
            void doAfterAllAnalysed(AnalysisContext context) {
                // 这里也要保存数据，确保最后遗留的数据也存储到数据库
                saveData()
                log.info("所有数据解析完成！")
            }

            /**
             * 加上存储数据库
             */
            private void saveData() {
                log.info("{}条数据，开始存储数据库！", cachedDataList.size())
                getService(entityType).saveBatch(cachedDataList)
                log.info("存储数据库成功！")
            }
        }).sheet().doRead()
        return R.success()
    }

    protected Map<String, Object> buildSearchParams(def entityType) {
        Map<String, Object> flat = ParamUtils.getParam()
        String sortKey = beanSearcherProperties.getParams().getSort()
        String orderKey = beanSearcherProperties.getParams().getOrder()
        Object orderByVal = null
        if (ObjectUtil.isNotEmpty(flat.get(sortKey))) {
            orderByVal = flat.get(sortKey)
            if (ObjectUtil.isNotEmpty(flat.get(orderKey))) {
                orderByVal = orderByVal.toString() + ":" + flat.get(orderKey)
            }
        }

        String orderByKey = beanSearcherProperties.getParams().getOrderBy()
        orderByVal = orderByVal ? (orderByVal + ',' + flat.get(orderByKey)) : flat.get(orderByKey)

        String collect = Arrays.stream(ReflectUtil.getFields(entityType))
                .filter(field -> AnnotationUtil.hasAnnotation(field, OrderBy.class))
                .sorted((field1, field2) -> {
                    OrderBy orderBy1 = AnnotationUtil.getAnnotation(field1, OrderBy.class)
                    OrderBy orderBy2 = AnnotationUtil.getAnnotation(field2, OrderBy.class)
                    return orderBy1.sort() - orderBy2.sort()
                })
                .map(field -> {
                    OrderBy orderBy = AnnotationUtil.getAnnotation(field, OrderBy.class)
                    return field.getName() + ":" + (orderBy.asc() ? "asc" : "desc")
                }).collect(Collectors.joining(","))

        if (ObjectUtil.isNotEmpty(orderByVal) && StrUtil.isNotBlank(collect)) {
            orderByVal = orderByVal.toString() + "," + collect
        } else if (ObjectUtil.isEmpty(orderByVal) && StrUtil.isNotBlank(collect)) {
            orderByVal = collect
        }
        flat.put(orderByKey, orderByVal)
        return flat
    }

    protected static Class getEntityType(String moduleName, String entityName) {
        def tableName = "${moduleName}_${entityName}"
        def tableInfo = TableInfoHelper.getTableInfo(tableName)
        tableInfo.getEntityType()
    }

    protected static IControllerHandler getControllerHandler(Class entityType) {
        def camelCase = StrUtil.lowerFirst(entityType.simpleName)
        def controllerHandler = "${camelCase}ControllerHandler"
        def containsControllerHandler = SpringUtil.applicationContext.containsBean(controllerHandler)
        if (containsControllerHandler) return SpringUtil.getBean(controllerHandler, IControllerHandler)
        else return new DefaultControllerHandler()
    }

    protected static IService getService(Class entityType) {
        def camelCase = StrUtil.lowerFirst(entityType.simpleName)
        def service = "${camelCase}Service"
        def containsService = SpringUtil.applicationContext.containsBean(service)
        if (containsService) return SpringUtil.getBean(service)
    }
}
