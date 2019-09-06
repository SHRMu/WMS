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
     *
     * @return 返回全部检测记录
     */
    List<DetectDO> selectAll();

    /**
     * 选择指定货物ID相关的入库记录
     *
     * @param goodsID 指定的货物ID
     * @return 返回指定货物相关的入库记录
     */
    List<DetectDO> selectByGoodsID(Integer goodsID);

    /**
     * 选择指定批次ID相关的入库记录
     *
     * @param batchID 指定的批次ID
     * @return 返回指定货物相关的入库记录
     */
    List<DetectDO> selectByBatchID(Integer batchID);

    /**
     * 选择指定仓库ID相关的入库记录
     *
     * @param repositoryID 指定的仓库ID
     * @return 返回指定仓库相关的入库记录
     */
    List<DetectDO> selectByRepositoryID(Integer repositoryID);

    /**
     * 选择指定仓库ID以及指定日期范围内的入库记录
     *
     * @param repositoryID 指定的仓库ID
     * @param startDate    记录的起始日期
     * @param endDate      记录的结束日期
     * @return 返回所有符合要求的入库记录
     */
    List<DetectDO> selectByRepositoryIDAndDate(@Param("repositoryID") Integer repositoryID,
                                                @Param("startDate") Date startDate,
                                                @Param("endDate") Date endDate);

    /**
     * 选择指定入库记录的ID的入库记录
     *
     * @param id 入库记录ID
     * @return 返回指定ID的入库记录
     */
    DetectDO selectByID(Integer id);

    /**
     * 添加一条新的入库记录
     *
     * @param detectDO 入库记录
     */
    void insert(DetectDO detectDO);

    /**
     * 更新入库记录
     *
     * @param detectDO 入库记录
     */
    void update(DetectDO detectDO);

    /**
     * 删除指定ID的入库记录
     *
     * @param id 指定删除入库记录的ID
     */
    void deleteByID(Integer id);

}
