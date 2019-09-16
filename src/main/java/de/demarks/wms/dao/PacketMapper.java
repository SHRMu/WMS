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
     * 返回全部包裹信息
     * @param repositoryID
     * @return
     */
    List<Packet> selectAll(@Param("repositoryID") Integer repositoryID);

    /**
     * 返回指定包裹ID的信息
     * @param packetID
     * @return
     */
    Packet selectByPacketID(@Param("packetID") Integer packetID);

    /**
     * 精确查询 返回指定运单号的包裹信息
     * @param trace
     * @param repositoryID
     * @return
     */
    Packet selectByTrace(@Param("trace") String trace,
                         @Param("repositoryID") Integer repositoryID);

    /**
     * 模糊查询 返回指定运单号的包裹信息
     * @param trace
     * @param status
     * @param repositoryID
     * @return
     */
    List<Packet> selectApproximate(@Param("trace") String trace,
                                   @Param("status") String status,
                                   @Param("repositoryID") Integer repositoryID);

    /**
     * 添加包裹信息
     * @return
     */
    Integer insert(Packet packet);

    /**
     * 更新包裹信息
     * @param packet
     */
    void update(Packet packet);

    /**
     * 删除指定packetID号的包裹
     * @param packetID
     */
    void deleteByID(Integer packetID);
}
