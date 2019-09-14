package de.demarks.wms.common.service.Interface;

import de.demarks.wms.domain.Packet;
import de.demarks.wms.exception.PacketManageServiceException;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 包裹运单管理 Service
 *
 * @author huanyingcool
 */
public interface PacketManageService {


    /**
     * 模糊查询 查询指定包裹号的记录
     * @param repositoryID
     * @return
     * @throws PacketManageServiceException
     */
    Map<String, Object> selectAll(@Param("repositoryID") Integer repositoryID) throws PacketManageServiceException;

    /**
     * 模糊查询 分页指定包裹号的记录
     * @param repositoryID
     * @param offset 分页的偏移值
     * @param limit  分页的大小
     * @return
     * @throws PacketManageServiceException
     */
    Map<String, Object> selectAll(@Param("repositoryID") Integer repositoryID,
                                  @Param("offset")int offset,
                                  @Param("limit") int limit) throws PacketManageServiceException;

    /**
     *
     * @param status
     * @param repositoryID
     * @return
     * @throws PacketManageServiceException
     */
    Map<String, Object> selectByStatus(@Param("status") String status,
                                       @Param("repositoryID") Integer repositoryID) throws PacketManageServiceException;

    /**
     *
     * @param status
     * @param repositoryID
     * @param offset
     * @param limit
     * @return
     * @throws PacketManageServiceException
     */
    Map<String, Object> selectByStatus(@Param("status") String status,
                                       @Param("repositoryID") Integer repositoryID,
                                       @Param("offset")int offset,
                                       @Param("limit") int limit) throws PacketManageServiceException;

    /**
     *
     * @param trace
     * @param status
     * @param repositoryID
     * @return
     * @throws PacketManageServiceException
     */
    Map<String, Object> selectApproximate(@Param("trace") String trace,
                                          @Param("status") String status,
                                          @Param("repositoryID") Integer repositoryID) throws PacketManageServiceException;

    /**
     *
     * @param trace
     * @param status
     * @param repositoryID
     * @param offset
     * @param limit
     * @return
     * @throws PacketManageServiceException
     */
    Map<String, Object> selectApproximate(@Param("trace") String trace,
                                          @Param("status") String status,
                                          @Param("repositoryID") Integer repositoryID,
                                          @Param("offset")int offset,
                                          @Param("limit") int limit) throws PacketManageServiceException;

    /**
     *
     * @param trace
     * @param status
     * @param repositoryID
     * @return
     * @throws PacketManageServiceException
     */
    Map<String, Object> selectRefApproximate(@Param("trace") String trace,
                                             @Param("status") String status,
                                             @Param("repositoryID") Integer repositoryID) throws PacketManageServiceException;

    /**
     *
     * @param trace
     * @param status
     * @param repositoryID
     * @param offset
     * @param limit
     * @return
     * @throws PacketManageServiceException
     */
    Map<String, Object> selectRefApproximate(@Param("trace") String trace,
                                             @Param("status") String status,
                                             @Param("repositoryID") Integer repositoryID,
                                             @Param("offset")int offset,
                                             @Param("limit") int limit) throws PacketManageServiceException;


    /**
     * 查询指定包裹ID的记录
     *
     * @param packetID 包裹ID
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    Map<String, Object> selectByPacketID(@Param("packetID") Integer packetID) throws PacketManageServiceException;

    /**
     * 添加附加包裹信息
     * @return
     * @throws PacketManageServiceException
     */
    boolean addPacketRef(Packet packet) throws PacketManageServiceException;

    /**
     * 删除附加包裹信息
     * @param refID
     * @return
     * @throws PacketManageServiceException
     */
    boolean deletePacketRefByRefID(Integer refID) throws PacketManageServiceException;

    /**
     * 添加包裹信息
     * @param packet
     * @return
     * @throws PacketManageServiceException
     */
    boolean addPacket(Packet packet) throws PacketManageServiceException;

    /**
     * 更新包裹信息
     * @param packet
     * @return
     */
    boolean updatePacket(Packet packet) throws PacketManageServiceException;

    /**
     * 删除指定包裹ID的信息
     * @param packetID
     * @return
     * @throws PacketManageServiceException
     */
    boolean deletePacket(Integer packetID) throws  PacketManageServiceException;

    /**
     * 客户预报操作
     *
     * @param packetID     包裹ID
     * @param goodsID      货物ID
     * @param repositoryID 入库仓库ID
     * @param number       入库数量
     * @return 返回一个boolean 值，若值为true表示入库成功，否则表示入库失败
     */
    boolean packetStockInOperation(Integer packetID, Integer goodsID, Integer repositoryID, long number, String personInCharge) throws PacketManageServiceException;

}
