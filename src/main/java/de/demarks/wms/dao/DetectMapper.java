package de.demarks.wms.dao;

import de.demarks.wms.domain.DetectDO;
import org.springframework.stereotype.Repository;

@Repository
public interface DetectMapper {

    /**
     * 添加一条新的入库记录
     *
     * @param detectDO 入库记录
     */
    void insert(DetectDO detectDO);

}
