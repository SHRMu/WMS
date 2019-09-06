package de.demarks.wms.common.service.Interface;

import de.demarks.wms.domain.DetectStorage;
import de.demarks.wms.exception.DetectStorageServiceException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 检测后待发货库存信息管理
 *
 * @author huanyingcool
 */

public interface DetectStorageService {

    /**
     * 返回所有的检测库存记录
     *
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    Map<String, Object> selectAll(Integer batchID, Integer repositoryID) throws DetectStorageServiceException;

    /**
     * 分页返回所有的检测库存记录
     * @param batchID 批次ID
     * @param repositoryID 仓库ID
     * @param offset 分页偏移值
     * @param limit  分页大小
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    Map<String, Object> selectAll(Integer batchID, Integer repositoryID, int offset, int limit) throws DetectStorageServiceException;

    /**
     * 返回指定货物ID的检测库存记录
     *
     * @param goodsID 指定的货物ID
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    Map<String, Object> selectByGoodsID(Integer goodsID, Integer batchID, Integer repositoryID) throws DetectStorageServiceException;

    /**
     * 分页返回指定货物ID的检测库存记录
     *
     * @param goodsID 指定的货物ID
     * @param offset  分页偏移值
     * @param limit   分页大小
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    Map<String, Object> selectByGoodsID(Integer goodsID, Integer batchID, Integer repositoryID, int offset, int limit) throws DetectStorageServiceException;

    /**
     * 返回指定货物名称的检测库存记录
     *
     * @param goodsName 货物名称
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    Map<String, Object> selectByGoodsName(String goodsName, Integer batchID, Integer repositoryID) throws DetectStorageServiceException;

    /**
     * 分页返回指定货物名称的检测库存记录
     *
     * @param goodsName 货物名称
     * @param offset    分页偏移值
     * @param limit     分页大小
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    Map<String, Object> selectByGoodsName(String goodsName, Integer batchID, Integer repositoryID, int offset, int limit) throws DetectStorageServiceException;

    /**
     * 添加一条检测库存记录
     *
     * @param goodsID      指定的货物ID
     * @param batchID      指定的批次ID
     * @param repositoryID 指定的仓库ID
     * @param passed       良品数量
     * @param scratch      划痕数量
     * @param damage       故障数量
     * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
     */
    boolean addDetectStorage(Integer goodsID, Integer batchID, Integer repositoryID, long passed, long scratch, long damage) throws DetectStorageServiceException;

    /**
     * 更新一条检测库存记录
     *
     * @param goodsID      指定的货物ID
     * @param batchID      指定的批次ID
     * @param repositoryID 指定的仓库ID
     * @param passed       良品数量
     * @param scratch      划痕数量
     * @param damage       故障数量
     * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
     */
    boolean updateDetectStorage(Integer goodsID, Integer batchID, Integer repositoryID, long passed, long scratch, long damage) throws DetectStorageServiceException;

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
     * 导入库存记录
     *
     * @param file 保存有的库存记录的文件
     * @return 返回一个Map，其中：key为total代表导入的总记录数，key为available代表有效导入的记录数
     */
    Map<String, Object> importDetectStorage(MultipartFile file) throws DetectStorageServiceException;

    /**
     * 导出库存记录
     *
     * @param storages 保存有库存记录的List
     * @return excel 文件
     */
    File exportDetectStorage(List<DetectStorage> storages);

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
