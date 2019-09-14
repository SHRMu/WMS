package de.demarks.wms.dao;

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
     * 指定refID
     * @param refID
     * @return
     */
    List<PacketRef> selectAll(@Param("refID") Integer refID);

    /**
     * 指定packetID
     * @param packetID
     */
    PacketRef selectByID(@Param("packetID") Integer packetID);

    /**
     * 精确查询 返回指定运单号的包裹
     * @param packetTrace
     * @return
     */
    PacketRef selectByTrace(@Param("packetTrace") String packetTrace);

    /**
     *
     * @param trace
     * @param status
     * @param repositoryID
     * @return
     */
    List<PacketRef> selectApproximate(@Param("trace") String trace,
                                      @Param("status") String status,
                                      @Param("repositoryID") Integer repositoryID);

    /**
     * 添加附加包裹
     * @param trace
     * @param refID
     */
    void insert(@Param("trace") String trace,
                @Param("refID") Integer refID);

    /**
     * 删除指定ID号的包裹
     * @param packetID
     */
    void deleteByID(@Param("packetID") Integer packetID);

    /**
     * 删除指定refID的包裹
     * @param refID
     */
    void deleteByRefID(@Param("refID") Integer refID);
}
