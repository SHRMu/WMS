package de.demarks.wms.common.service.Interface;

import de.demarks.wms.exception.DetectManageServiceException;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 检测记录管理
 *
 * @author huanyingcool
 *
 */
public interface DetectManageService {

    /**
     * 货物检测操作
     * @param goodsID
     * @param batchID
     * @param repositoryID
     * @param passed
     * @param scratch
     * @param damage
     * @param personInCharge
     * @return
     * @throws DetectManageServiceException
     */
    boolean detectOperation(@Param("goodsID") Integer goodsID,
                            @Param("batchID") Integer batchID,
                            @Param("repositoryID") Integer repositoryID,
                            @Param("passed") long passed, @Param("scratch") long scratch, @Param("damage") long damage,
                            @Param("personInCharge") String personInCharge) throws DetectManageServiceException;

    /**
     * 查询检测记录
     * @param goodsID
     * @param batchID
     * @param repositoryID
     * @param startDateStr
     * @param endDateStr
     * @return
     * @throws DetectManageServiceException
     */
    Map<String, Object> selectDetectRecordByGoodsID(@Param("goodsID") Integer goodsID,
                                           @Param("batchID") Integer batchID,
                                           @Param("repositoryID") Integer repositoryID,
                                           @Param("startDateStr") String startDateStr, @Param("endDateStr") String endDateStr) throws DetectManageServiceException;

    /**
     * 分页 查询检测记录
     * @param goodsID
     * @param batchID
     * @param repositoryID
     * @param startDateStr
     * @param endDateStr
     * @param offset
     * @param limit
     * @return
     * @throws DetectManageServiceException
     */
    Map<String, Object> selectDetectRecordByGoodsID(@Param("goodsID") Integer goodsID,
                                           @Param("batchID") Integer batchID,
                                           @Param("repositoryID") Integer repositoryID,
                                           @Param("startDateStr") String startDateStr, @Param("endDateStr") String endDateStr,
                                           @Param("offset") int offset, @Param("limit") int limit) throws DetectManageServiceException;


    Map<String, Object> selectDetectRecordByGoodsName(@Param("goodsName") String goodsName,
                                                    @Param("batchID") Integer batchID,
                                                    @Param("repositoryID") Integer repositoryID,
                                                    @Param("startDateStr") String startDateStr, @Param("endDateStr") String endDateStr) throws DetectManageServiceException;


    Map<String, Object> selectDetectRecordByGoodsName(@Param("goodsName") String goodsName,
                                                    @Param("batchID") Integer batchID,
                                                    @Param("repositoryID") Integer repositoryID,
                                                    @Param("startDateStr") String startDateStr, @Param("endDateStr") String endDateStr,
                                                    @Param("offset") int offset, @Param("limit") int limit) throws DetectManageServiceException;

}
