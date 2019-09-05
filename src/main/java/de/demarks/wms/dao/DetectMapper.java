package de.demarks.wms.dao;

import de.demarks.wms.domain.DetectDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetectMapper {

    /**
     * 选择全部的检测记录
     * @return 返回全部检测记录
     */
    List<DetectDO> selectAll();

    /**
     * 添加一条新的入库记录
     *
     * @param detectDO 入库记录
     */
    void insert(DetectDO detectDO);

}
