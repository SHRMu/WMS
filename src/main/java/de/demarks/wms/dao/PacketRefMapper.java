package de.demarks.wms.dao;

import de.demarks.wms.domain.Packet;
import de.demarks.wms.domain.PacketRef;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 包裹依赖信息 PacketRef 映射器
 *
 * @author huanyingcool
 *
 */

@Repository
public interface PacketRefMapper {

    /**
     * 选择所有的附加包裹
     * @return
     */
    List<Packet> selectAll();

    /**
     *返回指定包裹ID的信息
     * @param packetID     包裹ID
     */
    PacketRef selectByID(@Param("packetID") Integer packetID);

    /**
     * 精确查询 返回指定运单号的包裹
     * @param packetTrace
     * @return
     */
    PacketRef selectByTrace(@Param("packetTrace") String packetTrace);

    /**
     * 模糊查询 返回指定运单号的信息
     * @param trace   包裹追踪号
     * @param repositoryID  仓库ID
     */
    List<PacketRef> selectByTraceApproximate(@Param("trace") String trace,
                                            @Param("status") String status,
                                            @Param("repositoryID") Integer repositoryID);


    /**
     *
     * @param trace
     * @param refID
     */
    void insert(@Param("trace") String trace,
                @Param("refID") Integer refID);

    /**
     * 更新包裹信息
     * @param packet
     */
    void update(Packet packet);

    /**
     * 删除指定ID号的包裹
     * @param packetID
     */
    void delete(Integer packetID);
}
