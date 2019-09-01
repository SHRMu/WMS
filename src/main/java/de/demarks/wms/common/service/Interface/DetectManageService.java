package de.demarks.wms.common.service.Interface;

import de.demarks.wms.exception.DetectManageServiceException;

public interface DetectManageService {

    /**
     * 货物入库操作
     *
     * @param goodsID      货物ID
     * @param batch        批次号
     * @param repositoryID 入库仓库ID
     * @param passed       入库数量
     * @param scratch      划痕数量
     * @param damage       故障数量
     *
     * @return 返回一个boolean 值，若值为true表示入库成功，否则表示入库失败
     */
    boolean detectionOperation(Integer goodsID, Integer batch, Integer repositoryID, long passed, long scratch, long damage, String personInCharge) throws DetectManageServiceException;

}
