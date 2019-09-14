package de.demarks.wms.common.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import de.demarks.wms.common.service.Interface.PacketManageService;
import de.demarks.wms.common.util.StatusUtil;
import de.demarks.wms.dao.*;
import de.demarks.wms.domain.*;
import de.demarks.wms.exception.PacketManageServiceException;
import de.demarks.wms.util.aop.UserOperation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.tools.ant.taskdefs.Pack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *  包裹预报服务实现
 *
 * @author huanyingcool
 */

@Service
public class PacketManageServiceImpl implements PacketManageService {

    @Autowired
    private PacketMapper packetMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private RepositoryMapper repositoryMapper;
    @Autowired
    private PacketRefMapper packetRefMapper;
    @Autowired
    private PacketStorageMapper packetStorageMapper;

    /**
     * 模糊查询 查询指定包裹号的记录
     * @param repositoryID
     * @return
     * @throws PacketManageServiceException
     */
    @Override
    public Map<String, Object> selectAll(Integer repositoryID) throws PacketManageServiceException {
        return selectAll(repositoryID, -1, -1);
    }

    /**
     * 模糊查询 分页指定包裹号的记录
     * @param repositoryID
     * @param offset 分页的偏移值
     * @param limit  分页的大小
     * @return
     * @throws PacketManageServiceException
     */
    @Override
    public Map<String, Object> selectAll(Integer repositoryID, int offset, int limit) throws PacketManageServiceException {
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
                packetList = packetMapper.selectAll(repositoryID);
                if (packetList != null) {
                    PageInfo<Packet> pageInfo = new PageInfo<>(packetList);
                    total = pageInfo.getTotal();
                } else
                    packetList = new ArrayList<>();
            } else {
                packetList = packetMapper.selectAll(repositoryID);
                if (packetList != null)
                    total = packetList.size();
                else
                    packetList = new ArrayList<>();
            }
        } catch (PersistenceException e) {
            throw new PacketManageServiceException(e);
        }

        List<PacketDTO> packetDTOS = new ArrayList<>();
        if (!packetList.isEmpty()){}
            packetList.forEach(packet -> packetDTOS.add(packetConvertToPacketDTO(packet)));

        resultSet.put("data", packetDTOS);
        resultSet.put("total", total);
        return resultSet;
    }

    /**
     * 查询指定包裹ID的记录
     * @param packetID 包裹ID
     * @return
     * @throws PacketManageServiceException
     */
    @Override
    public Map<String, Object> selectByPacketID(Integer packetID) throws PacketManageServiceException {
        // 初始化结果集
        Map<String, Object> resultSet = new HashMap<>();
        List<Packet> packetList = new ArrayList<>();
        long total = 0;

        // 查询
        Packet packet;
        try {
            packet = packetMapper.selectByID(packetID);
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
     *
     * @param status
     * @param repositoryID
     * @return
     * @throws PacketManageServiceException
     */
    @Override
    public Map<String, Object> selectByStatus(String status, Integer repositoryID) throws PacketManageServiceException {
        return selectByStatus(status, repositoryID, -1, -1);
    }

    @Override
    public Map<String, Object> selectByStatus(String status, Integer repositoryID, int offset, int limit) throws PacketManageServiceException {
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
                packetList = packetMapper.selectByStatus(status, repositoryID);
                if (packetList != null) {
                    PageInfo<Packet> pageInfo = new PageInfo<>(packetList);
                    total = pageInfo.getTotal();
                } else
                    packetList = new ArrayList<>();
            } else {
                packetList = packetMapper.selectByStatus(status, repositoryID);
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

    @Override
    public Map<String, Object> selectApproximate(String trace, String status, Integer repositoryID) throws PacketManageServiceException {
        return  selectApproximate(trace, status, repositoryID, -1, -1);
    }

    @Override
    public Map<String, Object> selectApproximate(String trace, String status, Integer repositoryID, int offset, int limit) throws PacketManageServiceException {
        // 初始化结果集
        Map<String, Object> resultSet = new HashMap<>();
        List<PacketRef> packetRefList;

        long total = 0;
        boolean isPagination = true;

        // validate
        if (offset < 0 || limit < 0)
            isPagination = false;

        // query
        try {
            if (isPagination) {
                PageHelper.offsetPage(offset, limit);
                packetRefList = packetRefMapper.selectApproximate(trace,status,repositoryID);
                if (packetRefList != null) {
                    PageInfo<PacketRef> pageInfo = new PageInfo<>(packetRefList);
                    total = pageInfo.getTotal();
                } else
                    packetRefList = new ArrayList<>();
            } else {
                packetRefList = packetRefMapper.selectApproximate(trace,status,repositoryID);
                if (packetRefList != null)
                    total = packetRefList.size();
                else
                    packetRefList = new ArrayList<>();
            }
        } catch (PersistenceException e) {
            throw new PacketManageServiceException(e);
        }

        List<Packet> packetList = new ArrayList<>();
        if (!packetRefList.isEmpty()){
            for (PacketRef packetRef:
                    packetRefList) {
                Integer refid = packetRef.getRefid();
                Packet packet = packetMapper.selectByID(refid);
                if (!packetList.contains(packet))
                    packetList.add(packet);
            }

        }

        List<PacketDTO> packetDTOS = new ArrayList<>();
        packetList.forEach(packet -> packetDTOS.add(packetConvertToPacketDTO(packet)));

        resultSet.put("data", packetDTOS);
        resultSet.put("total", total);
        return resultSet;
    }

    @Override
    public Map<String, Object> selectRefApproximate(String trace, String status, Integer repositoryID) throws PacketManageServiceException {
        return  selectRefApproximate(trace, status, repositoryID, -1, -1);
    }

    @Override
    public Map<String, Object> selectRefApproximate(String trace, String status, Integer repositoryID, int offset, int limit) throws PacketManageServiceException {
        // 初始化结果集
        Map<String, Object> resultSet = new HashMap<>();
        List<PacketRef> packetRefList;

        long total = 0;
        boolean isPagination = true;

        // validate
        if (offset < 0 || limit < 0)
            isPagination = false;

        // query
        try {
            if (isPagination) {
                PageHelper.offsetPage(offset, limit);
                packetRefList = packetRefMapper.selectApproximate(trace,status,repositoryID);
                if (packetRefList != null) {
                    PageInfo<PacketRef> pageInfo = new PageInfo<>(packetRefList);
                    total = pageInfo.getTotal();
                } else
                    packetRefList = new ArrayList<>();
            } else {
                packetRefList = packetRefMapper.selectApproximate(trace,status,repositoryID);
                if (packetRefList != null)
                    total = packetRefList.size();
                else
                    packetRefList = new ArrayList<>();
            }
        } catch (PersistenceException e) {
            throw new PacketManageServiceException(e);
        }

        List<PacketDTO> packetDTOS = new ArrayList<>();
        packetRefList.forEach(packetRef -> packetDTOS.add(packetRefConvertToPacketDTO(packetRef)));

        resultSet.put("data", packetDTOS);
        resultSet.put("total", total);
        return resultSet;
    }

    /**
     * 添加附加包裹
     * @return
     * @throws PacketManageServiceException
     */
    @Override
    public boolean addPacketRef(Packet packet) throws PacketManageServiceException {
        try{
            if ( packet!= null){
                Integer refID = packet.getId();
                String desc = packet.getDesc();
                List<PacketRef> packetRefList = packetRefMapper.selectAll(refID);
                if (!packetRefList.isEmpty()){
                    deletePacketRefByRefID(refID);
                }
                packetRefMapper.insert(packet.getTrace(),refID); //添加主单信息
                if (desc.contains(",")){
                    String[] traces = desc.split(",");
                    for (String trace:
                            traces) {
                        PacketRef packetRef = packetRefMapper.selectByTrace(trace);
                        if (packetRef == null)
                            packetRefMapper.insert(trace, refID);
                    }
                }else{
                    PacketRef packetRef = packetRefMapper.selectByTrace(desc);
                    if ( packetRef == null)
                        packetRefMapper.insert(desc, refID);
                }
                return true;
            }
            return false;
        }catch (PersistenceException e) {
            throw new PacketManageServiceException(e);
        }
    }

    /**
     * 删除附加包裹信息
     * @param refID
     * @return
     * @throws PacketManageServiceException
     */
    @Override
    public boolean deletePacketRefByRefID(Integer refID) throws PacketManageServiceException {
        try{
            if (refID != null){
                packetRefMapper.deleteByRefID(refID);
                return true;
            }
            return false;
        }catch (PersistenceException e) {
            throw new PacketManageServiceException(e);
        }
    }

    /**
     * 添加包裹信息
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
                    // 通过单号验证包裹信息是否存在
                    Packet p = packetMapper.selectByTrace(packet.getTrace(),packet.getRepositoryID());
                    if (p != null){ //如果已经存在该单号
                        updatePacket(packet);
                        return true;
                    }
                    packet.setTime(new Date());
                    packetMapper.insert(packet);
                    Packet packetID = packetMapper.selectByTrace(packet.getTrace(),packet.getRepositoryID());
                    //添加附加包裹信息
                    String desc = packetID.getDesc();
                    if (desc.equals("")){
                        packetRefMapper.insert(packetID.getTrace(), packetID.getId());
                        return true;
                    }
                    addPacketRef(packetID);
                    return true;
                }
            }
            return false;
        } catch (PersistenceException e) {
            throw new PacketManageServiceException(e);
        }
    }

    /**
     * 更新包裹信息
     * @param packet
     * @return
     */
    @Override
    public boolean updatePacket(Packet packet) throws PacketManageServiceException {
        try {
            // 更新记录
            if (packet != null) {
                // 检验附加包裹修改
                if (packetRefCheck(packet)){
                    deletePacketRefByRefID(packet.getId());
                    addPacketRef(packet);
                }
                if (packetCheck(packet)) {
                    packet.setTime(new Date());
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
     * 删除包裹信息
     * @param packetID
     * @return
     */
    @Override
    public boolean deletePacket(Integer packetID) throws PacketManageServiceException {
        try {
            //检查该包裹是否已签收
            Packet packet = packetMapper.selectByID(packetID);
            String status = packet.getStatus();
            if(status.equalsIgnoreCase(StatusUtil.PACKET_STATUS_RECEIVE)){
                //先删除相关依赖的PacketRef
                deletePacketRefByRefID(packetID);
                packetMapper.deleteByID(packetID);
                return true;
            }
            return false;
        } catch (PersistenceException e) {
            throw new PacketManageServiceException(e);
        }
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

    /**
     * 检查附加包裹信息是否修改
     * @param packet
     * @return
     */
    private boolean packetRefCheck(Packet packet){
        String desc = packet.getDesc();
        String oldDesc = packetMapper.selectByID(packet.getId()).getDesc();
        if (desc.equalsIgnoreCase(oldDesc))
            return false;
        return true;
    }

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm");

    private PacketDTO packetConvertToPacketDTO(Packet packet){
        PacketDTO packetDTO = new PacketDTO();
        packetDTO.setId(packet.getId());
        packetDTO.setTrace(packet.getTrace());
        packetDTO.setTime(dateFormat.format(packet.getTime()));
        packetDTO.setStatus(packet.getStatus());
        packetDTO.setDesc(packet.getDesc());
        return packetDTO;
    }

    private PacketDTO packetRefConvertToPacketDTO(PacketRef packetRef){
        PacketDTO packetDTO = new PacketDTO();
        packetDTO.setId(packetRef.getRefid()); //只返回主单单号
        packetDTO.setTrace(packetRef.getTrace());
        packetDTO.setTime(dateFormat.format(packetRef.getTime()));
        packetDTO.setStatus(packetRef.getStatus());
        if (packetRef.getTrace().equalsIgnoreCase(packetRef.getReftrace()))
            packetDTO.setDesc(packetRef.getDesc()); //只有主单保留子单描述信息
        packetDTO.setRepositoryID(packetRef.getRepositoryID());
        return packetDTO;
    }

    /**
     * 客户操作包裹预入库操作
     *
     * @param packetID     包裹ID
     * @param goodsID      货物ID
     * @param repositoryID 入库仓库ID
     * @param number       入库数量
     * @return 返回一个boolean 值，若值为true表示入库成功，否则表示入库失败
     */
    @UserOperation(value = "客户预报")
    @Override
    public boolean packetStockInOperation(Integer packetID, Integer goodsID, Integer repositoryID, long number, String personInCharge) throws PacketManageServiceException {

        // 验证对应ID的记录是否存在
        if (!(packetValidate(packetID) && goodsValidate(goodsID) && repositoryValidate(repositoryID)))
            return false;

        if (personInCharge == null)
            return false;

        //必须用户账户登录下测试
//        Customer customer = customerMapper.selectByName(personInCharge);
//        if (customer == null)
//            return false;

        // 检查入库数量有效性
        if (number < 0)
            return false;

        try {
            //查看当前PacketID和GoodsID是否有预报
            List<PacketStorage> packetStorageList = packetStorageMapper.selectAll(goodsID, packetID, repositoryID);
            PacketStorage packetStorage;
            if ( !packetStorageList.isEmpty()){
                //增加预报数量
                packetStorage = packetStorageList.get(0);
                Long newNum = packetStorage.getNumber() + number;
                Long newSto = packetStorage.getStorage() + number;
                packetStorage.setNumber(newNum);
                packetStorage.setStorage(newSto);
                packetStorageMapper.update(packetStorage);
                return true;
            }else {
                //初次预报
                packetStorage = new PacketStorage();
                packetStorage.setPacketID(packetID);
                packetStorage.setGoodsID(goodsID);
                packetStorage.setCustomerID(2001);
                packetStorage.setRepositoryID(repositoryID);
                packetStorage.setNumber(number);
                packetStorage.setStorage(number);
                packetStorageMapper.insert(packetStorage);
                return true;
            }
        } catch (PersistenceException e) {
            throw new PacketManageServiceException(e);
        }
    }

    /**
     * 检查包裹ID对应的记录是否存在
     *
     *
     * @return 若存在则返回true，否则返回false
     */
    private boolean packetValidate(Integer packetID) throws PacketManageServiceException {
        try {
            Packet packet = packetMapper.selectByID(packetID);
            return packet != null;
        } catch (PersistenceException e) {
            throw new PacketManageServiceException(e);
        }
    }

    /**
     * 检查仓库ID对应的记录是否存在
     *
     * @param repositoryID 仓库ID
     * @return 若存在则返回true，否则返回false
     */
    private boolean repositoryValidate(Integer repositoryID) throws PacketManageServiceException {
        try {
            Repository repository = repositoryMapper.selectByID(repositoryID);
            return repository != null;
        } catch (PersistenceException e) {
            throw new PacketManageServiceException(e);
        }
    }

    /**
     * 检查货物ID对应的记录是否存在
     *
     * @param goodsID 货物ID
     * @return 若存在则返回true，否则返回false
     */
    private boolean goodsValidate(Integer goodsID) throws PacketManageServiceException {
        try {
            Goods goods = goodsMapper.selectById(goodsID);
            return goods != null;
        } catch (PersistenceException e) {
            throw new PacketManageServiceException(e);
        }
    }



}
