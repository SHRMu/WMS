package de.demarks.wms.dao;

import de.demarks.wms.domain.DetectStorage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  已检测 库存映射器
 *
 * @author huanyingcool
 *
 */
@Repository
public interface DetectStorageMapper {

    /**
     * 返回所有的信息
     * @param repositoryID
     * @return
     */
    List<DetectStorage> selectAll(@Param("repositoryID") Integer repositoryID);

    /**
     * 返回指定ID的信息
     * @param goodsID
     * @param batchID
     * @param repositoryID
     * @return
     */
    List<DetectStorage> selectByID(@Param("goodsID") Integer goodsID,
                                   @Param("batchID") Integer batchID,
                                   @Param("repositoryID") Integer repositoryID);


    /**
     * 模糊搜索 返回指定名称的信息
     * @param goodsName
     * @param batchID
     * @param repositoryID
     * @return
     */
    List<DetectStorage> selectApproximate(@Param("goodsName") String goodsName,
                                          @Param("batchID") Integer batchID,
                                          @Param("repositoryID") Integer repositoryID);

    /**
     * 添加一条信息
     * @param detectStorage
     */
    void insert(DetectStorage detectStorage);

    /**
     * 批量导入库存信息
     * @param storages 若干条库存信息
     */
    void insertBatch(List<DetectStorage> storages);

    /**
     * 更新全部信息
     * @param storage
     */
    void update(DetectStorage storage);

    /**
     * 删除指定批次，仓库中的货物ID的库存
     * @param goodsID 货物ID
     * @param batchID 批次ID
     * @param repositoryID 仓库ID
     */
    void delete(@Param("goodsID") Integer goodsID,
                @Param("batchID") Integer batchID,
                @Param("repositoryID") Integer repositoryID);

}
