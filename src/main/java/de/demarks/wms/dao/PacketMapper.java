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
     * 选择所有的包裹
     *
     * @param repositoryID
     * @return
     */
    List<Packet> selectAll(@Param("repositoryID") Integer repositoryID);

    /**
     * 精确查询 返回指定包裹ID的信息
     * @param packetID
     * @return
     */
    Packet selectByID(@Param("packetID") Integer packetID);

    /**
     * 精确查询 返回指定运单号的包裹
     *
     * @param packetTrace
     * @return
     */
    Packet selectByTrace(@Param("packetTrace") String packetTrace);

    /**
     * 模糊查询 返回指定运单号以及状态的信息
     * @param trace
     * @param status
     * @param repositoryID
     * @return
     */
    List<Packet> selectByTraceApproximate(@Param("trace") String trace,
                                          @Param("status") String status,
                                          @Param("repositoryID") Integer repositoryID);

    /**
     * 插入指定的包裹信息
     * @return
     */
    Integer insert(Packet packet);

    /**
     * 更新包裹信息
     * @param packet
     */
    void update(Packet packet);

    /**
     * 删除指定ID号的包裹
     * @param packetID
     */
    void deleteByID(Integer packetID);
}
