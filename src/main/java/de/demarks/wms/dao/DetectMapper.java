package de.demarks.wms.dao;

import de.demarks.wms.domain.DetectDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 *
 * 检测记录映射器
 *
 * @author huanyingcool
 */

@Repository
public interface DetectMapper {

    /**
     * 选择全部的检测记录
     * @param batchID   检测批次ID
     * @param repositoryID  仓库ID
     * @return 返回全部检测记录
     */
    List<DetectDO> selectAll(@Param("batchID") Integer batchID,
                             @Param("repositoryID") Integer repositoryID);

    /**
     * 指定检测记录ID的检测记录
     *
     * @param id        检测记录ID
     * @param batchID   检测批次ID
     * @param repositoryID  仓库ID
     * @return 返回指定ID的检测记录
     */
    DetectDO selectByID(@Param("id") Integer id,
                        @Param("batchID") Integer batchID,
                        @Param("repositoryID") Integer repositoryID);

    /**
     * 选择指定货物ID的检测记录
     *
     * @param goodsID 指定的货物ID
     * @param batchID   检测批次ID
     * @param repositoryID  仓库ID
     * @return 返回指定货物相关的入库记录
     */
    List<DetectDO> selectByGoodsID(@Param("goodsID") Integer goodsID,
                                   @Param("batchID") Integer batchID,
                                   @Param("repositoryID") Integer repositoryID);

    /**
     * 选择指定批次ID, 仓库ID以及指定日期范围内的检测记录
     *
     * @param batchID      指定的批次ID
     * @param repositoryID 指定的仓库ID
     * @param startDate    记录的起始日期
     * @param endDate      记录的结束日期
     * @return 返回所有符合要求的入库记录
     */
    List<DetectDO> selectByBatchRepoIDAndDate(@Param("batchID") Integer batchID,
                                               @Param("repositoryID") Integer repositoryID,
                                                @Param("startDate") Date startDate,
                                                @Param("endDate") Date endDate);

    /**
     * 添加一条新的检测记录
     *
     * @param detectDO 入库记录
     */
    void insert(DetectDO detectDO);

    /**
     * 更新检测记录
     *
     * @param detectDO 检测记录
     */
    void update(DetectDO detectDO);

    /**
     * 删除指定ID的检测记录
     *
     * @param id 指定删除检测记录的ID
     */
    void deleteByID(Integer id);

}
