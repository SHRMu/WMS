package de.demarks.wms.common.service.Impl;

import de.demarks.wms.common.service.Interface.*;
import de.demarks.wms.dao.*;
import de.demarks.wms.domain.*;
import de.demarks.wms.exception.PreStockManageServiceException;
import de.demarks.wms.util.aop.UserOperation;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public boolean preStockInOperation(Integer packetID, Integer goodsID, Integer repositoryID, long number, String personInCharge) throws PreStockManageServiceException {

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
            PacketStorage packetStorage = packetStorageMapper.selectByPacketAndGoodsID(packetID, goodsID, repositoryID);
            if ( packetStorage != null){
                Long num = packetStorage.getNumber();
                packetStorage.setNumber(num + number);
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
                packetStorage.setStorage((long) 0);
                packetStorageMapper.insert(packetStorage);
                return true;
            }
        } catch (PersistenceException e) {
            throw new PreStockManageServiceException(e);
        }
    }

    /**
     * 检查包裹ID对应的记录是否存在
     *
     *
     * @return 若存在则返回true，否则返回false
     */
    private boolean packetValidate(Integer packetID) throws PreStockManageServiceException {
        try {
            Packet packet = packetMapper.selectByID(packetID);
            return packet != null;
        } catch (PersistenceException e) {
            throw new PreStockManageServiceException(e);
        }
    }

    /**
     * 检查仓库ID对应的记录是否存在
     *
     * @param repositoryID 仓库ID
     * @return 若存在则返回true，否则返回false
     */
    private boolean repositoryValidate(Integer repositoryID) throws PreStockManageServiceException {
        try {
            Repository repository = repositoryMapper.selectByID(repositoryID);
            return repository != null;
        } catch (PersistenceException e) {
            throw new PreStockManageServiceException(e);
        }
    }

    /**
     * 检查货物ID对应的记录是否存在
     *
     * @param goodsID 货物ID
     * @return 若存在则返回true，否则返回false
     */
    private boolean goodsValidate(Integer goodsID) throws PreStockManageServiceException {
        try {
            Goods goods = goodsMapper.selectById(goodsID);
            return goods != null;
        } catch (PersistenceException e) {
            throw new PreStockManageServiceException(e);
        }
    }

}
