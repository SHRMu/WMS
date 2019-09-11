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
     * @param status
     * @return
     */
    List<Packet> selectAll(@Param("status") String status);

    /**
     *返回指定包裹ID的信息
     *
     * @param packetID     包裹ID
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
     * @param trace   包裹追踪号
     * @param status        包裹状态
     * @param repositoryID  仓库ID
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
