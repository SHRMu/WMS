package de.demarks.wms.dao;

import com.sun.tracing.dtrace.ProviderAttributes;
import de.demarks.wms.domain.PacketStorage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
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
    List<PacketStorage> selectAll(@Param("repositoryID") Integer repositoryID);

    /**
     * 选择指定 包裹ID 和 货物ID 的预报记录
     *
     *
     * @return 返回指定 包裹ID 和 货物ID 的预报记录
     */
    PacketStorage selectByPacketAndGoodsID(@Param("packetID") Integer packetID,
                                           @Param("goodsID") Integer goodsID,
                                           @Param("repositoryID") Integer repositoryID);

    /**
     * 预报操作是 插入 一条预报记录
     *
     * @param packetStorage 预报记录
     */
    void insert(PacketStorage packetStorage);

    /**
     * 更新入库记录
     *
     * @param stockInDO 入库记录
     */
    void update(PacketStorage stockInDO);

    /**
     * 删除指定ID的入库记录
     *
     * @param id 指定删除入库记录的ID
     */
    void deleteByID(Integer id);

}
