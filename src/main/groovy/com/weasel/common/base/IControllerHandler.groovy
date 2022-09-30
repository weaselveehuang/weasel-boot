package com.weasel.common.base

import cn.hutool.core.collection.CollUtil
import cn.hutool.core.lang.Assert
import cn.hutool.core.util.ObjectUtil
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.tangzc.mpe.actable.annotation.Column
import com.tangzc.mpe.actable.annotation.Unique
import com.weasel.annotation.RefTables
import com.weasel.modules.sys.entity.SysRole

/**
 * @author weasel
 * @date 2022/7/25 8:50
 * @version 1.0
 */
trait IControllerHandler<T extends BaseEntity<T>> {

    void checkUnique(T entity) {
        def tableInfo = TableInfoHelper.getTableInfo(entity.class)
        tableInfo.fieldList.each {
            def field = it.field
            def isUnique = field.isAnnotationPresent(Unique)
            if (isUnique) {
                Column column = field.getAnnotation(Column)
                String comment = column.comment()

                def queryWrapper = Wrappers.query().eq(it.column, entity[field.name])
                entity.id && queryWrapper.ne(tableInfo.keyColumn, entity[tableInfo.keyProperty])
                def count = entity.selectCount(queryWrapper)
                Assert.isTrue(0 == count, "已存在相同${comment}记录！")
            }
        }
    }

    void beforeSave(T entity) {
        checkUnique(entity)
    }

    void afterSave(T entity) {}

    void beforeUpdate(T entity) {
        T dbEntity = entity.selectById()
        Long dbEntityVersion = dbEntity.getVersion()
        Assert.isTrue(ObjectUtil.equal(dbEntityVersion, entity.getVersion()), "该数据已被他人更改，请刷新页面后重试！")
        checkUnique(entity)
    }

    void afterUpdate(T entity) {}

    void beforeDelete(T entity) {
        def refTables = entity.class.getAnnotation(RefTables)
        refTables && refTables.value().each {
            long count = it.entityClass().newInstance().selectCount(Wrappers.query().eq(it.refColumn(), entity[it.selfColumn()]))
            Assert.isTrue(0 == count, it.erroMsg())
        }
    }

    void afterDelete(T entity) {}

    void beforeBatchDelete(List<T> entities) {
        if (entities) {
            def entity = entities.get(0)
            def refTables = entity.class.getAnnotation(RefTables)
            refTables && refTables.value().each {
                def selfColumnValues = CollUtil.getFieldValues(entities, it.selfColumn());
                long count = it.entityClass().newInstance().selectCount(Wrappers.query().in(it.refColumn(), selfColumnValues))
                Assert.isTrue(0 == count, it.erroMsg())
            }
        }
    }

    void afterBatchDelete(List<T> entities) {}
}
