package de.demarks.wms.dao;

import com.sun.tracing.dtrace.ProviderAttributes;
import de.demarks.wms.domain.PacketStorage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 *  客户预报数量记录 操作映射器
 *
 * @author huanyingcool
 */
@Repository
public interface PacketStorageMapper {

    /**
     * 选择 全部 预报记录
     *
     * @return 返回全部的预报
     */
    List<PacketStorage> selectAll(@Param("packetID") Integer packetID, @Param("status") String status,
                                  @Param("repositoryID") Integer repositoryID);

    List<PacketStorage> selectByGoodsID(@Param("goodsID") Integer goodsID, @Param("packetID") Integer packetID,
                                        @Param("repositoryID") Integer repositoryID);

    List<PacketStorage> selectApproximate(@Param("trace") String trace,
                                          @Param("status") String status,
                                          @Param("repositoryID") Integer repositoryID);
    /**
     * 添加一条记录
     *
     * @param packetStorage 预报记录
     */
    void insert(PacketStorage packetStorage);

    /**
     * 更新预报库存记录
     *
     * @param packetStorage 入库记录
     */
    void update(PacketStorage packetStorage);

    /**
     * 删除指定ID的入库记录
     * @param packetID
     * @param goodsID
     * @param repositoryID
     */
    void delete(@Param("goodsID") Integer goodsID,
                @Param("packetID") Integer packetID,
                @Param("repositoryID") Integer repositoryID);

}
