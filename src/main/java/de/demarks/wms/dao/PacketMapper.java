package de.demarks.wms.dao;

import de.demarks.wms.domain.PacketDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 包裹运单信息 Packet 映射器
 *
 * @author huanyingcool
 *
 */

@Repository
public interface PacketMapper {

    /**
     * 返回全部
     * @param repositoryID
     * @return
     */
    List<PacketDO> selectAll(@Param("repositoryID") Integer repositoryID);

    /**
     * 返回指定PacketID
     * @param packetID
     * @return
     */
    PacketDO selectByPacketID(@Param("packetID") Integer packetID);

    /**
     * 精确查询 返回指定运单号
     * @param trace
     * @param repositoryID
     * @return
     */
    PacketDO selectByTrace(@Param("trace") String trace,
                           @Param("repositoryID") Integer repositoryID);

    /**
     * 模糊查询 返回指定运单号
     * @param trace
     * @param status
     * @param repositoryID
     * @return
     */
    List<PacketDO> selectByTraceApproximate(@Param("trace") String trace,
                                            @Param("status") String status,
                                            @Param("repositoryID") Integer repositoryID);


    List<PacketDO> selectByDate(@Param("packetID") Integer packetID,
                                @Param("repositoryID") Integer repositoryID,
                                @Param("startDate") Date startDate,
                                @Param("endDate") Date endDate);

    /**
     * 添加
     * @param packetDO
     * @return
     */
    Integer insert(PacketDO packetDO);

    /**
     * 更新
     * @param packetDO
     */
    void update(PacketDO packetDO);

    /**
     * 删除指定packetID
     * @param packetID
     */
    void deleteByPacketID(@Param("packetID") Integer packetID);
}
