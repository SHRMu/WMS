package de.demarks.wms.common.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import de.demarks.wms.common.service.Interface.*;
import de.demarks.wms.dao.*;
import de.demarks.wms.domain.*;
import de.demarks.wms.exception.PacketStorageManageServiceException;
import de.demarks.wms.exception.PreStockManageServiceException;
import de.demarks.wms.util.aop.UserOperation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 预报库存管理 Service
 * @author huanyingcool
 */
@Service
public class PacketStorageManageServiceImpl implements PacketStorageManageService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private PacketMapper packetMapper;
    @Autowired
    private PacketStorageMapper packetStorageMapper;
    @Autowired
    private RepositoryMapper repositoryMapper;

    /**
     *
     * @param repositoryID
     * @return
     * @throws PacketStorageManageServiceException
     */
    @Override
    public Map<String, Object> selectAll(@Param("repositoryID") Integer repositoryID) throws PacketStorageManageServiceException {
        return  selectAll(repositoryID, -1, -1);
    }

    /**
     *
     * @param repositoryID
     * @param offset
     * @param limit
     * @return
     * @throws PacketStorageManageServiceException
     */
    @Override
    public Map<String, Object> selectAll(@Param("repository") Integer repositoryID,
                                         @Param("offset") Integer offset,
                                         @Param("limit") Integer limit) throws PacketStorageManageServiceException {
        // 初始化结果集
        Map<String, Object> resultSet = new HashMap<>();
        List<PacketStorage> packetStorageList;
        long total = 0;
        boolean isPagination = true;

        // validate
        if (offset < 0 || limit < 0)
            isPagination = false;

        // query
        try {
            if (isPagination) {
                PageHelper.offsetPage(offset, limit);
                packetStorageList = packetStorageMapper.selectAll(null,null, repositoryID);
                if (packetStorageList != null) {
                    PageInfo<PacketStorage> pageInfo = new PageInfo<>(packetStorageList);
                    total = pageInfo.getTotal();
                } else
                    packetStorageList = new ArrayList<>();
            } else {
                packetStorageList = packetStorageMapper.selectAll(null,null, repositoryID);
                if (packetStorageList != null)
                    total = packetStorageList.size();
                else
                    packetStorageList = new ArrayList<>();
            }
        } catch (PersistenceException e) {
            throw new PacketStorageManageServiceException(e);
        }

        resultSet.put("data", packetStorageList);
        resultSet.put("total", total);
        return resultSet;
    }

    /**
     *
     * @param goodsID
     * @param packetID
     * @param repositoryID
     * @return
     * @throws PacketStorageManageServiceException
     */
    @Override
    public Map<String, Object> selectByGoodsID(Integer goodsID, Integer packetID, Integer repositoryID) throws PacketStorageManageServiceException {
        return  selectByGoodsID(goodsID, packetID, repositoryID, -1 ,-1);
    }

    /**
     *
     * @param goodsID
     * @param packetID
     * @param repositoryID
     * @param offset
     * @param limit
     * @return
     * @throws PacketStorageManageServiceException
     */
    @Override
    public Map<String, Object> selectByGoodsID(@Param("goodsID") Integer goodsID,
                                               @Param("packetID") Integer packetID,
                                               @Param("repositoryID") Integer repositoryID,
                                               @Param("offset") Integer offset,
                                               @Param("limit") Integer limit) throws PacketStorageManageServiceException {
        // 初始化结果集
        Map<String, Object> resultSet = new HashMap<>();
        List<PacketStorage> packetStorageList;

        long total = 0;
        boolean isPagination = true;

        // validate
        if (offset < 0 || limit < 0)
            isPagination = false;

        // query
        try {
            if (isPagination) {
                PageHelper.offsetPage(offset, limit);
                packetStorageList = packetStorageMapper.selectAll(goodsID, packetID, repositoryID);
                if (packetStorageList != null) {
                    PageInfo<PacketStorage> pageInfo = new PageInfo<>(packetStorageList);
                    total = pageInfo.getTotal();
                } else
                    packetStorageList = new ArrayList<>();
            } else {
                packetStorageList = packetStorageMapper.selectAll(goodsID, packetID, repositoryID);
                if (packetStorageList != null)
                    total = packetStorageList.size();
                else
                    packetStorageList = new ArrayList<>();
            }
        } catch (PersistenceException e) {
            throw new PacketStorageManageServiceException(e);
        }

        resultSet.put("data", packetStorageList);
        resultSet.put("total", total);
        return resultSet;
    }


    /**
     *
     * @param goodsID
     * @param packetID
     * @param repositoryID
     * @param number
     * @return
     * @throws PacketStorageManageServiceException
     */
    @UserOperation(value = "更新包裹到货状态")
    @Override
    public boolean updatePacketStorage(Integer goodsID, Integer packetID, Integer repositoryID, long number, long storage) throws PacketStorageManageServiceException {
        try {
            boolean isUpdate = false;
            // validate
            List<PacketStorage> packetStorageList = packetStorageMapper.selectAll(goodsID, packetID, repositoryID);
            if (packetStorageList != null && !packetStorageList.isEmpty()) {
                // update
                PacketStorage packetStorage = packetStorageList.get(0);
                packetStorage.setNumber(number);
                packetStorage.setStorage(storage); //设置新的未到货数量
                packetStorageMapper.update(packetStorage);
                isUpdate = true;
            }

            return isUpdate;
        } catch (PersistenceException e) {
            throw new PacketStorageManageServiceException(e);
        }
    }

    /**
     *
     * @param goodsID
     * @param packetID
     * @param repositoryID
     * @param number
     * @return
     * @throws PacketStorageManageServiceException
     */
    @Override
    public boolean packetStorageDecrease(@Param("goodsID") Integer goodsID,
                                         @Param("packetID") Integer packetID,
                                         @Param("repositoryID") Integer repositoryID,
                                         @Param("number") long number) throws PacketStorageManageServiceException {
        synchronized (this) {
            // 检查对应的库存记录是否存在
            PacketStorage packetStorage = getPacketStorage(goodsID, packetID, repositoryID);
            if (null != packetStorage) {
                // 检查库存减少数目的范围是否合理
                if (number < 0 || packetStorage.getStorage() < number)
                    return false;
                long newNumber = packetStorage.getNumber();
                long newStorage = packetStorage.getStorage() - number;
                updatePacketStorage(goodsID, packetID, repositoryID, newNumber, newStorage);
                return true;
            } else
                return false;
        }
    }

    /**
     *
     * @param goodsID
     * @param packetID
     * @param repositoryID
     * @return
     */
    private PacketStorage getPacketStorage(@Param("goodsID") Integer goodsID,
                                           @Param("packetID") Integer packetID,
                                           @Param("repositoryID") Integer repositoryID) {
        PacketStorage packetStorage = null;
        List<PacketStorage> packetStorageList = packetStorageMapper.selectAll(goodsID, packetID, repositoryID);
        if (!packetStorageList.isEmpty())
            packetStorage = packetStorageList.get(0);
        return packetStorage;
    }
}
