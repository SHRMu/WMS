package de.demarks.wms.dao;

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
     *
     * @param packetID
     * @param repositoryID
     * @return
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

    /**
     * 模糊搜索
     * @param goodsName
     * @param packetID
     * @param repositoryID
     * @return
     */
    List<PacketStorage> selectByGoodsName(@Param("goodsName") String goodsName,
                                          @Param("packetID") Integer packetID,
                                          @Param("repositoryID") Integer repositoryID);


    /**
     * 模糊搜索
     * @param trace
     * @param status
     * @param repositoryID
     * @return
     */
    List<PacketStorage> selectByPacketTrace(@Param("trace") String trace,
                                              @Param("status") String status,
                                              @Param("repositoryID") Integer repositoryID);


    List<PacketStorage> selectByDate(@Param("packetID") Integer packetID,
                                     @Param("repositoryID") Integer repositoryID,
                                     @Param("startDate") Date startDate,
                                     @Param("endDate") Date endDate);

    /**
     * 添加
     * @param packetStorage
     */
    void insert(PacketStorage packetStorage);

    /**
     * 更新
     * @param packetStorage
     */
    void update(PacketStorage packetStorage);


    void delete(PacketStorage packetStorage);

}
