//package com.weasel.modules.sys.controller;
//
//import ai.yue.library.base.view.R;
//import ai.yue.library.base.view.Result;
//import ai.yue.library.base.view.ResultEnum;
//import cn.dev33.satoken.annotation.SaCheckPermission;
//import cn.dev33.satoken.annotation.SaMode;
//import cn.hutool.core.collection.CollUtil;
//import cn.hutool.core.collection.ListUtil;
//import cn.hutool.core.lang.Assert;
//import cn.hutool.core.util.EnumUtil;
//import com.alibaba.excel.EasyExcel;
//import com.alibaba.excel.ExcelWriter;
//import com.alibaba.excel.context.AnalysisContext;
//import com.alibaba.excel.read.listener.ReadListener;
//import com.alibaba.excel.write.metadata.WriteSheet;
//import com.alibaba.fastjson.JSON;
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import com.ejlchina.searcher.boot.BeanSearcherProperties;
//import com.fhs.core.trans.anno.TransMethodResult;
//import com.fhs.trans.service.impl.TransService;
//import com.weasel.common.base.BaseController;
//import com.weasel.common.consts.Consts;
//import com.weasel.modules.sys.entity.SysRole;
//import com.weasel.modules.sys.entity.SysRoleMenu;
//import com.weasel.modules.sys.entity.SysUserRole;
//import com.weasel.modules.sys.enums.DefaultRole;
//import com.weasel.modules.sys.service.SysRoleService;
//import com.weasel.modules.sys.util.RoleUtil;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletResponse;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
///**
// * @author weasel
// * @version 1.0
// * @date 2022/3/24 17:35
// */
//@RestController
//@RequestMapping("/sys/role")
//@Slf4j
//public class SysRoleController extends BaseController<SysRole> {
//    public static final String REQUEST_MAPPING_PREFIX = "/sys/role";
//    @Autowired
//    SysRoleService sysRoleService;
//    @Autowired
//    TransService transService;
//
//    @Override
//    public Result list() {
//        Result success = super.list();
//        success.setData(RoleUtil.buildRoles(getList(buildSearchParams())));
//        return success;
//    }
//
//    @GetMapping("/menus/{id}")
//    public Result getMenus(@PathVariable Long id) {
//        SysRole entity = new SysRole();
//        entity.setId(id);
//        List<SysRoleMenu> roleMenus = new SysRoleMenu().selectList(Wrappers.<SysRoleMenu>lambdaQuery().eq(SysRoleMenu::getRoleId, id));
//        entity.setMenu(roleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList()));
//        return R.success(entity);
//    }
//
//    @PostMapping("/menus")
//    @SaCheckPermission("/sys/role/menus/{id}===POST===INLINE")
//    public Result saveMenus(SysRole entity) {
//        return R.success(sysRoleService.saveRoleMenu(entity));
//
//    }
//
////    @Override
////    @SaCheckPermission(value = {REQUEST_MAPPING_PREFIX, REQUEST_MAPPING_PREFIX + "===GET"}, mode = SaMode.OR)
////    @TransMethodResult
////    public Result list() {
////        return super.list();
////    }
//
//    @Override
//    @SaCheckPermission(value = {"/sys/role/{id}===GET===INLINE", "/sys/role/{id}===INLINE"}, mode = SaMode.OR)
//    public Result getById(@PathVariable Long id) {
//        return super.getById(id);
//    }
//
//    @Override
//    @SaCheckPermission({"/sys/role===POST===TOOLBAR"})
//    public Result save(@Validated(Consts.ValidateGroup.SAVE.class) SysRole entity) {
//        return super.save(entity);
//    }
//
//    @Override
//    public void beforeSave(SysRole entity) {
//        Assert.isTrue(0 == new SysRole().selectCount(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getName, entity.getName())), "??????????????????????????????");
//        Assert.isTrue(0 == new SysRole().selectCount(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getCode, entity.getCode())), "???????????????????????????");
//    }
//
//    @Override
////    @SaCheckPermission({"/sys/role/{id}===PUT===INLINE"})
//    public Result update(SysRole entity) {
//        return super.update(entity);
//    }
//
//    @Override
//    public void beforeUpdate(SysRole entity) {
//        Assert.isFalse(EnumUtil.contains(DefaultRole.class, entity.getCode()), "????????????????????????????????????");
//        Assert.isTrue(0 == new SysRole().selectCount(Wrappers.<SysRole>lambdaQuery().ne(SysRole::getId, entity.getId()).eq(SysRole::getName, entity.getName())), "??????????????????????????????");
//        Assert.isTrue(0 == new SysRole().selectCount(Wrappers.<SysRole>lambdaQuery().ne(SysRole::getId, entity.getId()).eq(SysRole::getCode, entity.getCode())), "??????????????????????????????");
//    }
//
//    @Override
//    @SaCheckPermission({"/sys/role/{id}===DELETE===INLINE"})
//    public Result remove(@PathVariable Long id) {
//        return super.remove(id);
//    }
//
//    @Override
//    public void beforeDelete(Long id) {
//        Assert.isTrue(0 == new SysRole().selectCount(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getId, id).in(SysRole::getCode, EnumUtil.getFieldValues(DefaultRole.class, "value"))), "???????????????????????????????????????????????????");
//        Assert.isTrue(0 == new SysRoleMenu().selectCount(Wrappers.<SysRoleMenu>lambdaQuery().eq(SysRoleMenu::getRoleId, id)), "???????????????????????????????????????????????????????????????");
//        Assert.isTrue(0 == new SysUserRole().selectCount(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getRoleId, id)), "???????????????????????????????????????????????????????????????");
//    }
//
//    @Override
//    @SaCheckPermission({"/sys/role/batch===DELETE===BATCH"})
//    public Result removeBatch(List<Long> ids) {
//        return super.removeBatch(ids);
//    }
//
//    @Override
//    public void beforeBatchDelete(List<Long> ids) {
//        Assert.isTrue(0 == new SysRole().selectCount(Wrappers.<SysRole>lambdaQuery().in(SysRole::getId, ids).in(SysRole::getCode, EnumUtil.getFieldValues(DefaultRole.class, "value"))), "?????????????????????????????????????????????????????????");
//        Assert.isTrue(0 == new SysRoleMenu().selectCount(Wrappers.<SysRoleMenu>lambdaQuery().in(SysRoleMenu::getRoleId, ids)), "??????????????????????????????????????????????????????????????????");
//        Assert.isTrue(0 == new SysUserRole().selectCount(Wrappers.<SysUserRole>lambdaQuery().in(SysUserRole::getRoleId, ids)), "??????????????????????????????????????????????????????????????????");
//    }
//
//    @PostMapping("export")
//    @SneakyThrows
//    public void exportData(String filename, String[] onlySelect, HttpServletResponse response) {
//        try {
//            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//            response.setCharacterEncoding("utf-8");
//            // ??????URLEncoder.encode???????????????????????? ?????????easyexcel????????????
//            String fileName = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
//            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);
//
//            ExcelWriter excelWriter = null;
//            try {
//                excelWriter = EasyExcel.write(response.getOutputStream(), SysRole.class).autoCloseStream(Boolean.FALSE).build();
//                // ???????????? ???????????????sheet??????????????????
//                WriteSheet writeSheet = EasyExcel.writerSheet("????????????").includeColumnFiledNames(ListUtil.toList(onlySelect)).build();
//
//                Map<String, Object> searchParams = buildSearchParams();
//                BeanSearcherProperties.Params.Pagination paginationProps = beanSearcherProperties.getParams().getPagination();
//                String pageField = paginationProps.getPage();
//                String sizeField = paginationProps.getSize();
//                int page = paginationProps.getStart();
//                int size = paginationProps.getDefaultSize();
//                searchParams.put(sizeField, size);
//                writeData(excelWriter, writeSheet, searchParams, pageField, page);
//            } finally {
//                // ???????????????finish ??????????????????
//                if (excelWriter != null) {
//                    excelWriter.finish();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            // ??????response
//            response.reset();
//            response.setContentType("application/json");
//            response.setCharacterEncoding("utf-8");
//            response.setStatus(ResultEnum.ERROR_PROMPT.getCode());
//            response.getWriter().println(JSON.toJSONString(R.errorPrompt("??????????????????")));
//        }
//    }
//
//    private void writeData(ExcelWriter excelWriter, WriteSheet writeSheet, Map<String, Object> searchParams, String pageField, int page) {
//        searchParams.put(pageField, page);
//        List<SysRole> list = beanSearcher.searchList(SysRole.class, searchParams);
//        if (CollUtil.isNotEmpty(list)) {
//            transService.transMore(list);
//            excelWriter.write(list, writeSheet);
//            page++;
//            writeData(excelWriter, writeSheet, searchParams, pageField, page);
//        }
//    }
//
//    @PostMapping("import")
//    @SneakyThrows
//    public Result importData(MultipartFile file) {
//        EasyExcel.read(file.getInputStream(), SysRole.class, new ReadListener<SysRole>() {
//            /**
//             * ??????5??????????????????????????????????????????100??????????????????list ?????????????????????
//             */
//            private static final int BATCH_COUNT = 100;
//            /**
//             * ???????????????
//             */
//            private List<SysRole> cachedDataList = new ArrayList<>(BATCH_COUNT);
//
//            /**
//             * ??????????????????????????????????????????
//             *
//             * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
//             * @param context
//             */
//            @Override
//            public void invoke(SysRole data, AnalysisContext context) {
//                log.info("?????????????????????:{}", JSON.toJSONString(data));
//                cachedDataList.add(data);
//                // ??????BATCH_COUNT????????????????????????????????????????????????????????????????????????????????????OOM
//                if (cachedDataList.size() >= BATCH_COUNT) {
//                    saveData();
//                    // ?????????????????? list
//                    cachedDataList = new ArrayList<>(BATCH_COUNT);
//                }
//            }
//
//            /**
//             * ??????????????????????????? ???????????????
//             *
//             * @param context
//             */
//            @Override
//            public void doAfterAllAnalysed(AnalysisContext context) {
//                // ???????????????????????????????????????????????????????????????????????????
//                saveData();
//                log.info("???????????????????????????");
//            }
//
//            /**
//             * ?????????????????????
//             */
//            private void saveData() {
//                log.info("{}????????????????????????????????????", cachedDataList.size());
//                service.saveBatch(cachedDataList);
//                log.info("????????????????????????");
//            }
//        }).sheet().doRead();
//        return R.success();
//    }
//
//}
