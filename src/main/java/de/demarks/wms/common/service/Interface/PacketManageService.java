package de.demarks.wms.common.service.Interface;

import de.demarks.wms.domain.Packet;
import de.demarks.wms.exception.PacketManageServiceException;
import org.apache.ibatis.annotations.Param;
import org.apache.tools.ant.taskdefs.Pack;

import java.util.Map;

/**
 * 包裹运单管理 Service
 *
 * @author huanyingcool
 */
public interface PacketManageService {

    /**
     * 查询所有的包裹记录
     *
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    Map<String, Object> selectAll(@Param("status") String status) throws PacketManageServiceException;

    /**
     * 分页查询所有包裹记录
     *
     * @param offset 分页的偏移值
     * @param limit  分页的大小
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    Map<String, Object> selectAll(@Param("status") String status,
                                  @Param("offset")int offset,
                                  @Param("limit") int limit) throws PacketManageServiceException;

    /**
     * 查询指定包裹ID的记录
     *
     * @param id 包裹ID
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    Map<String, Object> selectByID(@Param("id") Integer id) throws PacketManageServiceException;


    /**
     * 模糊查询 查询指定包裹号的记录
     *
     * @param trace
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    Map<String, Object> selectByTraceApproximate(@Param("trace") String trace) throws PacketManageServiceException;

    /**
     * 模糊查询 分页指定包裹号的记录
     * @param trace  包裹号
     * @param offset 分页的偏移值
     * @param limit  分页的大小
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    Map<String, Object> selectByTraceApproximate(@Param("trace") String trace,
                                                 @Param("offset")int offset,
                                                 @Param("limit") int limit) throws PacketManageServiceException;

    /**
     * 添加附加包裹
     * @return
     * @throws PacketManageServiceException
     */
    public boolean addPacketRef(String desc, Integer id) throws PacketManageServiceException;

    /**
     * 添加包裹信息
     *
     */
    public boolean addPacket(Packet packet) throws PacketManageServiceException;

    /**
     * 更新包裹信息
     * @param packet
     * @return
     * @throws PacketManageServiceException
     */
    public boolean updatePacket(Packet packet) throws PacketManageServiceException;

    /**
     * 删除指定包裹ID的信息
     * @param id
     * @return
     * @throws PacketManageServiceException
     */
    public boolean deletePacket(Integer id) throws  PacketManageServiceException;

}
