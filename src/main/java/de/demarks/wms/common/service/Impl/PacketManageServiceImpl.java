package de.demarks.wms.common.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import de.demarks.wms.common.service.Interface.PacketManageService;
import de.demarks.wms.dao.PacketMapper;
import de.demarks.wms.dao.PacketRefMapper;
import de.demarks.wms.domain.Packet;
import de.demarks.wms.domain.PacketRef;
import de.demarks.wms.exception.PacketManageServiceException;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.tools.ant.taskdefs.Pack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *  包裹预报服务实现
 *
 * @author huanyingcool
 */

@Service
public class PacketManageServiceImpl implements PacketManageService {

    @Autowired
    PacketMapper packetMapper;
    @Autowired
    PacketRefMapper packetRefMapper;

    /**
     * 查询所有的包裹记录
     * @param status
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectAll(String status) throws PacketManageServiceException {
        return selectAll(status,-1,-1);
    }

    /**
     * 分页查询所有包裹记录
     *
     * @param status
     * @param offset 分页的偏移值
     * @param limit  分页的大小
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectAll(String status, int offset, int limit) throws PacketManageServiceException {
        // 初始化结果集
        Map<String, Object> resultSet = new HashMap<>();
        List<Packet> packetList;
        long total = 0;
        boolean isPagination = true;

        // validate
        if (offset < 0 || limit < 0)
            isPagination = false;

        // query
        try {
            if (isPagination) {
                PageHelper.offsetPage(offset, limit);
                packetList = packetMapper.selectAll(status);
                if (packetList != null) {
                    PageInfo<Packet> pageInfo = new PageInfo<>(packetList);
                    total = pageInfo.getTotal();
                } else
                    packetList = new ArrayList<>();
            } else {
                packetList = packetMapper.selectAll(status);
                if (packetList != null)
                    total = packetList.size();
                else
                    packetList = new ArrayList<>();
            }
        } catch (PersistenceException e) {
            throw new PacketManageServiceException(e);
        }

        resultSet.put("data", packetList);
        resultSet.put("total", total);
        return resultSet;

    }

    /**
     * 查询指定包裹ID的记录
     *
     * @param id 包裹ID
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectByID(Integer id) throws PacketManageServiceException {
        // 初始化结果集
        Map<String, Object> resultSet = new HashMap<>();
        List<Packet> packetList = new ArrayList<>();
        long total = 0;

        // 查询
        Packet packet;
        try {
            packet = packetMapper.selectByID(id);
        } catch (PersistenceException e) {
            throw new PacketManageServiceException(e);
        }

        if (packet != null) {
            packetList.add(packet);
            total = 1;
        }
        resultSet.put("data", packetList);
        resultSet.put("total", total);
        return resultSet;
    }

    /**
     * 模糊查询 查询指定包裹号的记录
     * @param trace
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectByTraceApproximate(@Param("trace") String trace) throws PacketManageServiceException {
        return selectByTraceApproximate(trace,-1, -1);
    }

    /**
     * 模糊查询 分页指定包裹号的记录
     * @param trace  包裹号
     * @param offset 分页的偏移值
     * @param limit  分页的大小
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectByTraceApproximate(@Param("trace") String trace,
                                                        @Param("offset")int offset,
                                                        @Param("limit") int limit) throws PacketManageServiceException {
        // 初始化结果集
        Map<String, Object> resultSet = new HashMap<>();
        List<Packet> packetList;
        long total = 0;
        boolean isPagination = true;

        // validate
        if (offset < 0 || limit < 0)
            isPagination = false;

        // query
        try {
            if (isPagination) {
                PageHelper.offsetPage(offset, limit);
                packetList = packetMapper.selectByTraceApproximate(trace, null, null);
                if (packetList != null) {
                    PageInfo<Packet> pageInfo = new PageInfo<>(packetList);
                    total = pageInfo.getTotal();
                } else
                    packetList = new ArrayList<>();
            } else {
                packetList = packetMapper.selectByTraceApproximate(trace, null, null);
                if (packetList != null)
                    total = packetList.size();
                else
                    packetList = new ArrayList<>();
            }
        } catch (PersistenceException e) {
            throw new PacketManageServiceException(e);
        }

        resultSet.put("data", packetList);
        resultSet.put("total", total);
        return resultSet;
    }

    /**
     * 添加附加包裹
     * @return
     * @throws PacketManageServiceException
     */
    @Override
    public boolean addPacketRef(String desc, Integer id) throws PacketManageServiceException {
        try{
            if ( id != null && desc!= null){
                if (desc.contains(",")){
                    String[] traces = desc.split(",");
                    for (String trace:
                            traces) {
                        PacketRef packetRef = packetRefMapper.selectByTrace(trace);
                        if (packetRef == null)
                            packetRefMapper.insert(trace, id);
                    }
                }else{
                    PacketRef packetRef = packetRefMapper.selectByTrace(desc);
                    if ( packetRef == null)
                        packetRefMapper.insert(desc, id);
                }
                return true;
            }
            return false;
        }catch (PersistenceException e) {
            throw new PacketManageServiceException(e);
        }
    }

    /**
     *
     * @param packet
     * @return
     * @throws PacketManageServiceException
     */
    @Override
    public boolean addPacket(Packet packet) throws PacketManageServiceException {
        try {
            // 插入新的记录
            if (packet!= null) {
                // 验证
                if (packetCheck(packet)) {
                    //检查单号是否已经存在
                    Packet p = packetMapper.selectByTrace(packet.getTrace());
                    if (p != null){
                        addPacketRef(packet.getDesc(), packet.getId()); //如果已经存在该单号
                        return true;
                    }
                    packet.setTime(new Date());
                    packetMapper.insert(packet);
                    Packet packetID = packetMapper.selectByTrace(packet.getTrace());
                    //添加附加包裹信息
                    String desc = packetID.getDesc();
                    addPacketRef(desc, packetID.getId());
                    return true;
                }
            }
            return false;
        } catch (PersistenceException e) {
            throw new PacketManageServiceException(e);
        }
    }

    /**
     *
     * @param packet
     * @return
     * @throws PacketManageServiceException
     */
    @Override
    public boolean updatePacket(Packet packet) throws PacketManageServiceException {
        try {
            // 更新记录
            if (packet != null) {
                // 检验
                if (packetCheck(packet)) {
                    packetMapper.update(packet);
                    return true;
                }
            }
            return false;
        } catch (PersistenceException e) {
            throw new PacketManageServiceException(e);
        }
    }

    /**
     *
     * @param id
     * @return
     * @throws PacketManageServiceException
     */
    @Override
    public boolean deletePacket(Integer id) throws PacketManageServiceException {
        return false;
    }

    /**
     * 检查包裹信息是否满足要求
     *
     * @param packet 货物信息
     * @return 若货物信息满足要求则返回true，否则返回false
     */
    private boolean packetCheck(Packet packet) {
        if (packet!= null) {
            if ( (packet.getTrace()!=null) && (packet.getStatus()!=null) && (packet.getRepositoryID()!=null)) {
                return true;
            }
        }
        return false;
    }


}
