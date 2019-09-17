package de.demarks.wms.dao;

import com.sun.tracing.dtrace.ProviderAttributes;
import de.demarks.wms.domain.PacketStorage;
import org.apache.ibatis.annotations.Param;
import org.apache.tools.ant.taskdefs.Pack;
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
     * 返回全部信息
     *
     * @return 返回全部的预报
     */
    List<PacketStorage> selectAll(@Param("packetID") Integer packetID,
                                  @Param("repositoryID") Integer repositoryID);

    /**
     * 精确搜索
     * @param goodsID
     * @param packetID
     * @param repositoryID
     * @return
     */
    List<PacketStorage> selectByGoodsID(@Param("goodsID") Integer goodsID,
                                        @Param("packetID") Integer packetID,
                                        @Param("repositoryID") Integer repositoryID);


    List<PacketStorage> selectByGoodsName(@Param("goodsID") String goodsName,
                                          @Param("packetID") Integer packetID,
                                          @Param("repositoryID") Integer repositoryID);

    /**
     * 模糊搜索
     * @param trace
     * @param status
     * @param repositoryID
     * @return
     */
    List<PacketStorage> selectByTrace(@Param("trace") String trace,
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
