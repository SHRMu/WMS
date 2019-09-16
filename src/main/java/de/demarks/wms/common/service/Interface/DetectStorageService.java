package de.demarks.wms.common.service.Interface;

import de.demarks.wms.domain.DetectStorage;
import de.demarks.wms.exception.DetectStorageServiceException;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 已检测库存信息管理
 *
 * @author huanyingcool
 */

public interface DetectStorageService {

    /**
     * 选择所有记录
     * @param repositoryID
     * @return
     * @throws DetectStorageServiceException
     */
    Map<String, Object> selectAll(@Param("repositoryID") Integer repositoryID) throws DetectStorageServiceException;

    /**
     * 分页 选择所有记录
     * @param repositoryID
     * @param offset
     * @param limit
     * @return
     * @throws DetectStorageServiceException
     */
    Map<String, Object> selectAll(@Param("repositoryID") Integer repositoryID,
                                  @Param("offset") int offset, @Param("limit") int limit) throws DetectStorageServiceException;

    /**
     * 选择指定ID的记录
     * @param goodsID
     * @param batchID
     * @param repositoryID
     * @return
     * @throws DetectStorageServiceException
     */
    Map<String, Object> selectByID(@Param("goodsID") Integer goodsID,
                                   @Param("batchID") Integer batchID,
                                   @Param("repositoryID") Integer repositoryID) throws DetectStorageServiceException;

    /**
     * 分页 选择指定ID的记录
     * @param goodsID
     * @param batchID
     * @param repositoryID
     * @param offset
     * @param limit
     * @return
     * @throws DetectStorageServiceException
     */
    Map<String, Object> selectByID(@Param("goodsID") Integer goodsID,
                                    @Param("batchID") Integer batchID,
                                    @Param("repositoryID") Integer repositoryID,
                                    @Param("offset") int offset, @Param("limit") int limit) throws DetectStorageServiceException;

    /**
     * 模糊查询 返回指定名称的记录
     * @param goodsName
     * @param batchID
     * @param repositoryID
     * @return
     * @throws DetectStorageServiceException
     */
    Map<String, Object> selectApproximate(String goodsName, Integer batchID, Integer repositoryID) throws DetectStorageServiceException;

    /**
     * 分页 模糊查询 返回指定名称的记录
     * @param goodsName
     * @param batchID
     * @param repositoryID
     * @param offset
     * @param limit
     * @return
     * @throws DetectStorageServiceException
     */
    Map<String, Object> selectApproximate(String goodsName, Integer batchID, Integer repositoryID, int offset, int limit) throws DetectStorageServiceException;

    /**
     * 添加一条记录
     * @param goodsID
     * @param batchID
     * @param repositoryID
     * @param passed
     * @param scratch
     * @param damage
     * @return
     * @throws DetectStorageServiceException
     */
    boolean addDetectStorage(Integer goodsID, Integer batchID, Integer repositoryID, long passed, long scratch, long damage) throws DetectStorageServiceException;

    /**
     * 更新一条检测库存记录
     *
     * @param goodsID      指定的货物ID
     * @param batchID      指定的批次ID
     * @param repositoryID 指定的仓库ID
     * @param number       检测总数
     * @param passed       良品数量
     * @param scratch      划痕数量
     * @param damage       故障数量
     * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
     */
    boolean updateDetectStorage(Integer goodsID, Integer batchID, Integer repositoryID, long number ,long passed, long scratch, long damage) throws DetectStorageServiceException;

    /**
     * 只更新检测库存中的良品数量
     *
     * @param goodsID      指定的货物ID
     * @param batchID      指定的批次ID
     * @param repositoryID 指定的仓库ID
     * @param passed       良品数量
     * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
     */
    boolean updatePassedDetectStorage(Integer goodsID, Integer batchID, Integer repositoryID, long passed) throws DetectStorageServiceException;

    /**
     * 删除一条检测库存记录
     * 货物ID与批次ID和仓库ID可唯一确定一条库存记录
     *
     * @param goodsID      指定的货物ID
     * @param batchID      指定的批次ID
     * @param repositoryID 指定的仓库ID
     * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
     */
    boolean deleteDetectStorage(Integer goodsID, Integer batchID, Integer repositoryID) throws DetectStorageServiceException;

    /**
     * 为指定的检测库存增加指定数目
     *
     * @param goodsID      货物ID
     * @param batchID      批次ID
     * @param repositoryID 仓库ID
     * @param passed       良品数量
     * @param scratch      划痕数量
     * @param damage       故障数量
     * @return 返回一个 boolean 值，若值为true表示数目增加成功，否则表示增加失败
     */
    boolean detectStorageIncrease(Integer goodsID, Integer batchID, Integer repositoryID, long passed, long scratch, long damage) throws DetectStorageServiceException;

    /**
     * 从检测的良品库存中减去出库数量
     *
     * @param goodsID      货物ID
     * @param batchID      批次ID
     * @param repositoryID 仓库ID
     * @param number       出库良品数
     * @return 返回一个 boolean 值，若值为 true 表示数目减少成功，否则表示增加失败
     */
    boolean passedDetectStorageDecrease(Integer goodsID, Integer batchID, Integer repositoryID, long number) throws DetectStorageServiceException;

}
