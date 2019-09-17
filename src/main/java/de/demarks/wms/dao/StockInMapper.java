package de.demarks.wms.dao;

import de.demarks.wms.domain.StockInDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 入库记录映射器
 *
 * @author huanyingcool
 */
@Repository
public interface StockInMapper {

    /**
     *
     * @param batchID
     * @param repositoryID
     * @return
     */
    List<StockInDO> selectAll(@Param("batchID") Integer batchID,
                              @Param("repositoryID") Integer repositoryID);

    /**
     *
     * @param recordID
     * @return
     */
    StockInDO selectByRecordID(@Param("recordID") Integer recordID);

    /**
     *
     * @param goodsID
     * @param batchID
     * @param repositoryID
     * @return
     */
    List<StockInDO> selectByGoodsID(@Param("goodsID") Integer goodsID,
                                    @Param("batchID") Integer batchID,
                                    @Param("repositoryID") Integer repositoryID);

    /**
     *
     * @param goodsName
     * @param batchID
     * @param repositoryID
     * @return
     */
    List<StockInDO> selectByGoodsName(@Param("goodsName") String goodsName,
                                      @Param("batchID") Integer batchID,
                                      @Param("repositoryID") Integer repositoryID);

    /**
     *
     * @param customerID
     * @param batchID
     * @param repositoryID
     * @return
     */
    List<StockInDO> selectByCustomerID(@Param("customerID") Integer customerID,
                                       @Param("batchID") Integer batchID,
                                       @Param("repositoryID") Integer repositoryID);

    /**
     * 选择指定批次，仓库ID以及指定日期范围内的入库记录
     *
     * @param batchID      指定批次ID
     * @param repositoryID 指定的仓库ID
     * @param startDate    记录的起始日期
     * @param endDate      记录的结束日期
     * @return 返回所有符合要求的入库记录
     */
    List<StockInDO> selectByDate(@Param("batchID") Integer batchID,
                                 @Param("repositoryID") Integer repositoryID,
                                 @Param("startDate") Date startDate,
                                 @Param("endDate") Date endDate);

    /**
     * 添加一条新的入库记录
     *
     * @param stockInDO 入库记录
     */
    void insert(StockInDO stockInDO);

    /**
     * 更新入库记录
     *
     * @param stockInDO 入库记录
     */
    void update(StockInDO stockInDO);

    /**
     * 删除指定ID的入库记录
     *
     * @param id 指定删除入库记录的ID
     */
    void deleteByID(Integer id);

}
