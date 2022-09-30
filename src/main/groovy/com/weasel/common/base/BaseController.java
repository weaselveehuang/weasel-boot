package com.weasel.common.base;

import ai.yue.library.base.util.ParamUtils;
import ai.yue.library.base.view.R;
import ai.yue.library.base.view.Result;
import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.OrderBy;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ejlchina.searcher.BeanSearcher;
import com.ejlchina.searcher.SearchResult;
import com.ejlchina.searcher.boot.BeanSearcherProperties;
import com.fhs.core.trans.anno.TransMethodResult;
import com.fhs.trans.service.impl.TransService;
import com.weasel.common.consts.Consts;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author weasel
 * @version 1.0
 * @date 2022/3/25 17:15
 */
public class BaseController<T extends BaseEntity<T>> {
    public static final String REQUEST_MAPPING_PREFIX = "";
    @Autowired
    public IService<T> service;
    @Autowired
    public BeanSearcher beanSearcher;
    @Autowired
    public BeanSearcherProperties beanSearcherProperties;
    @Autowired
    TransService transService;

    public List<T> getList(Map<String, Object> searchParams) {
        return beanSearcher.searchAll(service.getEntityClass(), searchParams);
    }

    public SearchResult<T> getPage(Map<String, Object> searchParams) {
        return beanSearcher.search(service.getEntityClass(), searchParams);
    }

    protected Map<String, Object> buildSearchParams() {
//        Map<String, String[]> params = ServletUtils.getParams(ServletUtils.getRequest());
        Map<String, Object> flat = ParamUtils.getParam();
        String sortKey = beanSearcherProperties.getParams().getSort();
        String orderKey = beanSearcherProperties.getParams().getOrder();
        Object orderByVal = null;
        if (ObjectUtil.isNotEmpty(flat.get(sortKey))) {
            orderByVal = flat.get(sortKey);
            if (ObjectUtil.isNotEmpty(flat.get(orderKey))) {
                orderByVal = orderByVal.toString() + ":" + flat.get(orderKey);
            }
        }

        String orderByKey = beanSearcherProperties.getParams().getOrderBy();
        orderByVal = flat.get(orderByKey);

        String collect = Arrays.stream(ReflectUtil.getFields(service.getEntityClass()))
                .filter(field -> AnnotationUtil.hasAnnotation(field, OrderBy.class))
                .sorted((field1, field2) -> {
                    OrderBy orderBy1 = AnnotationUtil.getAnnotation(field1, OrderBy.class);
                    OrderBy orderBy2 = AnnotationUtil.getAnnotation(field2, OrderBy.class);
                    return orderBy1.sort() - orderBy2.sort();
                })
                .map(field -> {
                    OrderBy orderBy = AnnotationUtil.getAnnotation(field, OrderBy.class);
                    return field.getName() + ":" + (orderBy.asc() ? "asc" : "desc");
                }).collect(Collectors.joining(","));

        if (ObjectUtil.isNotEmpty(orderByVal) && StrUtil.isNotBlank(collect)) {
            orderByVal = orderByVal.toString() + "," + collect;
        } else if (ObjectUtil.isEmpty(orderByVal) && StrUtil.isNotBlank(collect)) {
            orderByVal = collect;
        }
        flat.put(orderByKey, orderByVal);
        return flat;
    }

    @GetMapping
    @TransMethodResult
//    @SaCheckPermission(value = {REQUEST_MAPPING_PREFIX, REQUEST_MAPPING_PREFIX + "===GET"}, mode = SaMode.OR)
    public Result list() {
        Map<String, Object> searchParams = buildSearchParams();
        BeanSearcherProperties.Params.Pagination pagination = beanSearcherProperties.getParams().getPagination();
        if (searchParams.containsKey(pagination.getPage()) && searchParams.containsKey(pagination.getSize())) {
            SearchResult<T> page = getPage(searchParams);
//            transService.transMore(page.getDataList());
            return R.success(page);
        } else {
            return R.success(getList(searchParams));
        }
    }

    @SneakyThrows
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        return R.success(service.getById(id));
    }

    @PostMapping
    public Result save(@Validated(Consts.ValidateGroup.SAVE.class) T entity) {
        beforeSave(entity);
        return R.success(service.save(entity));
    }

    public void beforeSave(T entity) {

    }

    @PutMapping
    public Result update(@Validated(Consts.ValidateGroup.UPDATE.class) T entity) {
        beforeUpdate(entity);
        return R.success(service.updateById(entity));
    }

    public void beforeUpdate(T entity) {
        T dbEntity = entity.selectById();
        Long dbEntityVersion = dbEntity.getVersion();
        Assert.isTrue(ObjectUtil.equal(dbEntityVersion, entity.getVersion()), "该数据已被他人更改，请刷新页面后重试！");
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    public Result remove(@PathVariable Long id) {
        beforeDelete(id);
        return R.success(service.removeById(id));
    }

    public void beforeDelete(Long id) {

    }

    @SneakyThrows
    @DeleteMapping("/batch")
    public Result removeBatch(List<Long> ids) {
        beforeBatchDelete(ids);
        return R.success(service.removeBatchByIds(ids));
    }

    public void beforeBatchDelete(List<Long> ids) {

    }

}
