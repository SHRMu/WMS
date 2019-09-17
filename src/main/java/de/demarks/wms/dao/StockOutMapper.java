package de.demarks.wms.dao;

import de.demarks.wms.domain.StockOutDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 出库记录 映射器
 *
 * @author Ken
 */
@Repository
public interface StockOutMapper {


    List<StockOutDO> selectAll(@Param("batchID") Integer batchID,
                               @Param("repositoryID") Integer repositoryID);

    StockOutDO selectByRecordID(@Param("recordID") Integer recordID);

    List<StockOutDO> selectByGoodsID(@Param("goodsID") Integer goodsID,
                                     @Param("batchID") Integer batchID,
                                     @Param("repositoryID") Integer repositoryID);

    List<StockOutDO> selectByGoodsName(@Param("goodsName") String goodsName,
                                       @Param("batchID") Integer batchID,
                                       @Param("repositoryID") Integer repositoryID);

    List<StockOutDO> selectByCustomerID(Integer customerID);

    /**
     * 选择指定批次，仓库ID以及指定日期范围内的出库记录
     *
     * @param batchID      指定的批次ID
     * @param repositoryID 指定的仓库ID
     * @param startDate    记录起始日期
     * @param endDate      记录结束日期
     * @return 返回所有符合指定要求的出库记录
     */
    List<StockOutDO> selectByDate(@Param("batchID") Integer batchID,
                                  @Param("repositoryID") Integer repositoryID,
                                 @Param("startDate") Date startDate,
                                 @Param("endDate") Date endDate);



    /**
     * 插入一条新的出库记录
     *
     * @param stockOutDO 出库记录
     */
    void insert(StockOutDO stockOutDO);

    /**
     * 更新出库记录
     *
     * @param stockOutDO 出库记录
     */
    void update(StockOutDO stockOutDO);

    /**
     * 删除指定ID的出库记录
     *
     * @param id 指定的出库记录ID
     */
    void deleteById(Integer id);
}
