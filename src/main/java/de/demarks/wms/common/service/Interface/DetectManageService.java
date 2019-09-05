package de.demarks.wms.common.service.Interface;

import de.demarks.wms.exception.DetectManageServiceException;

import java.util.Map;

/**
 *
 * 检测记录管理
 *
 * @author huanyingcool
 *
 */
public interface DetectManageService {

    /**
     * 货物检测操作
     *
     * @param goodsID       货物ID
     * @param BatchID       批次ID
     * @param repositoryID  仓库ID
     * @param passed        良品数量
     * @param scratch       划痕数量
     * @param damage        故障数量
     * @return 返回一个boolean 值，若值为true表示入库成功，否则表示入库失败
     */
    boolean detectOperation(Integer goodsID, Integer BatchID, Integer repositoryID, long passed, long scratch, long damage, String personInCharge) throws DetectManageServiceException;

    /**
     * 查询检测记录
     *
     * @param batchID 批次号
     * @param repositoryID 仓库ID
     * @param endDateStr   查询记录起始日期
     * @param startDateStr 查询记录结束日期
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    Map<String, Object> selectDetectRecord(Integer batchID, Integer repositoryID, String startDateStr, String endDateStr) throws DetectManageServiceException;

    /**
     * 分页查询检测记录
     *
     * @param batchID 批次号
     * @param repositoryID 仓库ID
     * @param endDateStr   查询记录起始日期
     * @param startDateStr 查询记录结束日期
     * @param offset       分页偏移值
     * @param limit        分页大小
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    Map<String, Object> selectDetectRecord(Integer batchID, Integer repositoryID, String startDateStr, String endDateStr, int offset, int limit) throws DetectManageServiceException;

}
