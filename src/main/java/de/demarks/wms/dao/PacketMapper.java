package de.demarks.wms.dao;

import de.demarks.wms.domain.Packet;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
    List<Packet> selectAll(@Param("repositoryID") Integer repositoryID);

    /**
     * 返回指定PacketID
     * @param packetID
     * @return
     */
    Packet selectByPacketID(@Param("packetID") Integer packetID);

    /**
     * 精确查询 返回指定运单号
     * @param trace
     * @param repositoryID
     * @return
     */
    Packet selectByTrace(@Param("trace") String trace,
                         @Param("repositoryID") Integer repositoryID);

    /**
     * 模糊查询 返回指定运单号
     * @param trace
     * @param status
     * @param repositoryID
     * @return
     */
    List<Packet> selectByTraceApproximate(@Param("trace") String trace,
                                         @Param("status") String status,
                                         @Param("repositoryID") Integer repositoryID);

    /**
     * 添加
     * @param packet
     * @return
     */
    Integer insert(Packet packet);

    /**
     * 更新
     * @param packet
     */
    void update(Packet packet);

    /**
     * 删除指定packetID
     * @param packetID
     */
    void deleteByPacketID(@Param("packetID") Integer packetID);
}
