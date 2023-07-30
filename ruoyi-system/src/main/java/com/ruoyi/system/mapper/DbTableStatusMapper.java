package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.DbTableStatus;
import com.ruoyi.system.domain.OpqGroup;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author zjzaki
 * @Package com.ruoyi.system.mapper
 * @Date 2023/7/27 13:56
 */
@Repository
public interface DbTableStatusMapper {
    /**
     * 查询数据库表状态
     * @return 数据库表状态实体
     */
    List<DbTableStatus> showTableStatus(List<OpqGroup> groups);
}
