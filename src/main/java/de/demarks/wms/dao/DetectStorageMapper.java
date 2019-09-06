package de.demarks.wms.dao;

import de.demarks.wms.domain.DetectStorage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  已检测待发货库存映射器
 *
 * @author huanyingcool
 *
 */
@Repository
public interface DetectStorageMapper {

    /**
     * 选择指定批次ID和仓库ID的检测库存信息
     * @param batchID       批次ID
     * @param repositoryID  仓库ID
     * @return 返回所有的库存信息
     */
    List<DetectStorage> selectAll(@Param("batchID")Integer batchID, @Param("repositoryID") Integer repositoryID);

    /**
     * 选择指定货物ID检测库存信息
     * @param goodsID      货物ID
     * @return 返回所有指定货物ID和仓库ID的库存信息
     */
    List<DetectStorage> selectByGoodsID(@Param("goodsID") Integer goodsID,
                                        @Param("batchID") Integer batchID,
                                        @Param("repositoryID") Integer repositoryID);

    /**
     * 选择指定货物名的检测库存信息
     * @param goodsName 货物名称
     * @return 返回所有指定货物名称的检测库存信息
     */
    List<DetectStorage> selectByGoodsName(@Param("goodsName") String goodsName,
                                          @Param("batchID") Integer batchID,
                                          @Param("repositoryID") Integer repositoryID);


    /**
     * 选择指定批次ID和仓库ID的库存信息
     * @param batchCode 批次编号
     * @param repositoryID 库存ID
     * @return 返回所有指定货物ID和仓库ID的库存信息
     */
    List<DetectStorage> selectByBatchCodeAndRepositoryID(@Param("batchCode") String batchCode,
                                                       @Param("repositoryID") Integer repositoryID);

    /**
     * 更新库存信息
     * 该库存信息必需已经存在于数据库当中，否则更新无效
     * @param storage 库存信息
     */
    void update(DetectStorage storage);

    /**
     * 插入新的库存信息
     * @param detectStorage 库存信息
     */
    void insert(DetectStorage detectStorage);

    /**
     * 批量导入库存信息
     * @param storages 若干条库存信息
     */
    void insertBatch(List<DetectStorage> storages);

    /**
     * 删除指定货物ID的库存信息
     * @param goodsID 货物ID
     */
    void deleteByGoodsID(Integer goodsID);

    /**
     * 删除指定仓库的库存信息
     * @param repositoryID 仓库ID
     */
    void deleteByRepositoryID(Integer repositoryID);

    /**
     * 删除指定仓库中的指定货物的库存信息
     * @param goodsID 货物ID
     * @param repositoryID 仓库ID
     */
    void deleteByRepositoryIDAndGoodsID(@Param("goodsID") Integer goodsID, @Param("repositoryID") Integer repositoryID);

}
