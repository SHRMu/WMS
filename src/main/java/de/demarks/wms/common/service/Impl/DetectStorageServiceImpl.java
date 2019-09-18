package de.demarks.wms.common.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import de.demarks.wms.common.service.Interface.DetectStorageService;
import de.demarks.wms.dao.*;
import de.demarks.wms.domain.*;
import de.demarks.wms.exception.DetectStorageServiceException;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  已检测库存 Service
 *
 * @author huanyingcool
 */
@Service
public class DetectStorageServiceImpl implements DetectStorageService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private RepositoryBatchMapper repositoryBatchMapper;

    @Autowired
    private RepositoryMapper repositoryMapper;

    @Autowired
    private DetectStorageMapper detectStorageMapper;

    @Override
    public Map<String, Object> selectAll(Integer batchID, Integer repositoryID) throws DetectStorageServiceException {
        return selectAll(batchID, repositoryID, -1, -1);
    }

    @Override
    public Map<String, Object> selectAll(Integer batchID, Integer repositoryID, int offset, int limit) throws DetectStorageServiceException {
        // 初始化结果集
        Map<String, Object> resultSet = new HashMap<>();
        List<DetectStorage> detectStorageList;
        long total = 0;
        boolean isPagination = true;

        // validate
        if (offset < 0 || limit < 0)
            isPagination = false;

        if (batchID < 0)
            batchID = null;
        if (repositoryID < 0)
            repositoryID = null;

        // query
        try {
            if (isPagination) {
                PageHelper.offsetPage(offset, limit);
                detectStorageList = detectStorageMapper.selectAll(batchID,repositoryID);
                if (detectStorageList != null) {
                    PageInfo<DetectStorage> pageInfo = new PageInfo<>(detectStorageList);
                    total = pageInfo.getTotal();
                } else
                    detectStorageList = new ArrayList<>();
            } else {
                detectStorageList = detectStorageMapper.selectAll(batchID, repositoryID);
                if (detectStorageList != null)
                    total = detectStorageList.size();
                else
                    detectStorageList = new ArrayList<>();
            }
        } catch (PersistenceException e) {
            throw new DetectStorageServiceException(e);
        }

        resultSet.put("data", detectStorageList);
        resultSet.put("total", total);
        return resultSet;
    }

    /**
     * 选择指定ID的记录
     * @param goodsID
     * @param batchID
     * @param repositoryID
     * @return
     * @throws DetectStorageServiceException
     */
    @Override
    public Map<String, Object> selectByGoodsID(Integer goodsID, Integer batchID, Integer repositoryID) throws DetectStorageServiceException {
        return selectByGoodsID(goodsID, batchID, repositoryID, -1, -1);
    }

    /**
     * 分页 选择指定ID的记录
     * @param goodsID
     * @param batchID
     * @param repositoryID
     * @param offset
     * @param limit
     * @return
     * @throws DetectStorageServiceException
     */
    @Override
    public Map<String, Object> selectByGoodsID(Integer goodsID, Integer batchID, Integer repositoryID, int offset, int limit) throws DetectStorageServiceException {
        // 初始化结果集
        Map<String, Object> resultSet = new HashMap<>();
        List<DetectStorage> detectStorageList;
        long total = 0;
        boolean isPagination = true;

        // validate
        if (offset < 0 || limit < 0)
            isPagination = false;

        if (goodsID < 0)
            goodsID = null;
        if (batchID <0)
            batchID = null;
        if (repositoryID <0)
            repositoryID = null;

        // query
        try {
            if (isPagination) {
                PageHelper.offsetPage(offset, limit);
                detectStorageList = detectStorageMapper.selectByGoodsID(goodsID, batchID, repositoryID);
                if (detectStorageList != null) {
                    PageInfo<DetectStorage> pageInfo = new PageInfo<>(detectStorageList);
                    total = pageInfo.getTotal();
                } else
                    detectStorageList = new ArrayList<>();
            } else {
                detectStorageList = detectStorageMapper.selectByGoodsID(goodsID, batchID, repositoryID);
                if (detectStorageList != null)
                    total = detectStorageList.size();
                else
                    detectStorageList = new ArrayList<>();
            }
        } catch (PersistenceException e) {
            throw new DetectStorageServiceException(e);
        }

        resultSet.put("data", detectStorageList);
        resultSet.put("total", total);
        return resultSet;
    }

    /**
     * 模糊查询 返回指定名称的记录
     * @param goodsName
     * @param batchID
     * @param repositoryID
     * @return
     * @throws DetectStorageServiceException
     */
    @Override
    public Map<String, Object> selectByGoodsName(String goodsName, Integer batchID, Integer repositoryID) throws DetectStorageServiceException {
        return selectByGoodsName(goodsName, batchID, repositoryID, -1, -1);
    }

    /**
     * 分页 模糊查询 返回指定名称的记录
     * @param goodsName
     * @param batchID
     * @param repositoryID
     * @param offset
     * @param limit
     * @return
     * @throws DetectStorageServiceException
     */
    @Override
    public Map<String, Object> selectByGoodsName(String goodsName, Integer batchID, Integer repositoryID, int offset, int limit) throws DetectStorageServiceException {
        // 初始化结果集
        Map<String, Object> resultSet = new HashMap<>();
        List<DetectStorage> detectStorageList;
        long total = 0;
        boolean isPagination = true;

        // validate
        if (offset < 0 || limit < 0)
            isPagination = false;

        if (batchID < 0)
            batchID = null;
        if (repositoryID < 0)
            repositoryID = null;

        // query
        try {
            if (isPagination) {
                PageHelper.offsetPage(offset, limit);
                detectStorageList = detectStorageMapper.selectByGoodsName(goodsName, batchID, repositoryID);
                if (detectStorageList != null) {
                    PageInfo<DetectStorage> pageInfo = new PageInfo<>(detectStorageList);
                    total = pageInfo.getTotal();
                } else
                    detectStorageList = new ArrayList<>();
            } else {
                detectStorageList = detectStorageMapper.selectByGoodsName(goodsName, batchID, repositoryID);
                if (detectStorageList != null)
                    total = detectStorageList.size();
                else
                    detectStorageList = new ArrayList<>();
            }
        } catch (PersistenceException e) {
            throw new DetectStorageServiceException(e);
        }

        resultSet.put("data", detectStorageList);
        resultSet.put("total", total);
        return resultSet;
    }

    /**
     * 添加一条记录
     * @param goodsID
     * @param batchID
     * @param repositoryID
     * @param passed
     * @param scratch
     * @param damage
     * @return
     * @throws DetectStorageServiceException
     */
    @Override
    public boolean addDetectStorage(Integer goodsID, Integer customerID, Integer batchID, Integer repositoryID, long passed, long scratch, long damage) throws DetectStorageServiceException {
        try {
            boolean isAvailable = true;
            // ID对应的记录是否存在
            if (!(goodsValidate(goodsID) && customerValidate(customerID) && batchValidate(batchID) && repositoryValidate(repositoryID)))
                isAvailable = false;
            if (passed<0 || scratch<0 || damage<0)
                isAvailable = false;
            Long total = passed + scratch + damage;
            List<DetectStorage> detectStorageList = detectStorageMapper.selectByGoodsID(goodsID, batchID, repositoryID);
            if (!detectStorageList.isEmpty())
                isAvailable = false;
            if (isAvailable) {
                // insert
                DetectStorage detectStorage = new DetectStorage();
                detectStorage.setGoodsID(goodsID);
                detectStorage.setCustomerID(customerID);
                detectStorage.setBatchID(batchID);
                detectStorage.setRepositoryID(repositoryID);
                detectStorage.setNumber(total);
                detectStorage.setPassed(passed);
                detectStorage.setScratch(scratch);
                detectStorage.setDamage(damage);
                detectStorageMapper.insert(detectStorage);
            }
            return isAvailable;
        } catch (PersistenceException e) {
            throw new DetectStorageServiceException(e);
        }

    }

    /**
     * 更新一条检测库存记录
     *
     * @param goodsID      指定的货物ID
     * @param batchID      指定的批次ID
     * @param repositoryID 指定的仓库ID
     * @param number       检测总数
     * @param passed       良品数量
     * @param scratch      划痕数量
     * @param damage       故障数量
     * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
     */
    @Override
    public boolean updateDetectStorage(Integer goodsID, Integer batchID, Integer repositoryID, long number, long passed, long scratch, long damage) throws DetectStorageServiceException {
        try {
            boolean isUpdate = false;
            // validate
            List<DetectStorage> detectStorageList = detectStorageMapper.selectByGoodsID(goodsID, batchID, repositoryID);
            if (detectStorageList != null && !detectStorageList.isEmpty()) {
                if ( passed >= 0 && scratch > 0 && damage > 0) {
                    // update
                    DetectStorage detectStorage = detectStorageList.get(0);
                    detectStorage.setNumber(number);
                    detectStorage.setPassed(passed);
                    detectStorage.setScratch(scratch);
                    detectStorage.setDamage(damage);
                    detectStorageMapper.update(detectStorage);
                    isUpdate = true;
                }
            }
            return isUpdate;
        } catch (PersistenceException e) {
            throw new DetectStorageServiceException(e);
        }
    }

    /**
     * 只更新检测库存中的良品数量
     *
     * @param goodsID      指定的货物ID
     * @param batchID      指定的批次ID
     * @param repositoryID 指定的仓库ID
     * @param passed       良品数量
     * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
     */
    @Override
    public boolean updatePassedDetectStorage(Integer goodsID, Integer batchID, Integer repositoryID, long passed) throws DetectStorageServiceException {
        try {
            boolean isUpdate = false;
            // validate
            List<DetectStorage> detectStorageList = detectStorageMapper.selectByGoodsID(goodsID, batchID, repositoryID);
            if (detectStorageList != null && !detectStorageList.isEmpty()) {
                if ( passed >= 0 ) {
                    // update
                    DetectStorage detectStorage = detectStorageList.get(0);
                    detectStorage.setPassed(passed);
                    detectStorageMapper.update(detectStorage);
                    isUpdate = true;
                }
            }
            return isUpdate;
        } catch (PersistenceException e) {
            throw new DetectStorageServiceException(e);
        }
    }

    /**
     * 删除一条检测库存记录
     * 货物ID与批次ID和仓库ID可唯一确定一条库存记录
     *
     * @param goodsID      指定的货物ID
     * @param batchID      指定的批次ID
     * @param repositoryID 指定的仓库ID
     * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
     */
    @Override
    public boolean deleteDetectStorage(Integer goodsID, Integer batchID, Integer repositoryID) throws DetectStorageServiceException {
        return false;
    }

    /**
     * 获取指定货物ID，批次ID，仓库ID对应的检测库存
     *
     * @param goodsID      货物ID
     * @param batchID      批次ID
     * @param repositoryID 仓库ID
     * @return 若存在则返回对应的记录，否则返回null
     */
    private DetectStorage getDetectStorage(Integer goodsID, Integer batchID, Integer repositoryID) {
        DetectStorage detectStorage = null;
        List<DetectStorage> detectStorageList = detectStorageMapper.selectByGoodsID(goodsID, batchID, repositoryID);
        if (!detectStorageList.isEmpty())
            detectStorage = detectStorageList.get(0);
        return detectStorage;
    }

    /**
     * 为指定的检测库存记录增加指定数目
     *
     * @param goodsID      货物ID
     * @param batchID      批次ID
     * @param repositoryID 仓库ID
     * @param passed       良品数量
     * @param scratch      划痕数量
     * @param damage       故障数量
     * @return 返回一个 boolean 值，若值为true表示数目增加成功，否则表示增加失败
     */
    @Override
    public boolean detectStorageIncrease(Integer goodsID, Integer customerID, Integer batchID, Integer repositoryID, long passed, long scratch, long damage) throws DetectStorageServiceException {
        // 检查货物库存增加数目的有效性
        if ( passed < 0 || scratch < 0 || damage < 0)
            return false;

        synchronized (this) {
            // 检查对应的库存记录是否存在
            DetectStorage detectStorage = getDetectStorage(goodsID, batchID, repositoryID);
            if (detectStorage!= null) {
                long newNumber = detectStorage.getNumber() + passed + scratch + damage;
                long newPassed = detectStorage.getPassed() + passed;
                long newScratch = detectStorage.getScratch() + scratch;
                long newDamage = detectStorage.getDamage() + damage;
                updateDetectStorage(goodsID, batchID, repositoryID, newNumber, newPassed, newScratch, newDamage);
            } else {
                addDetectStorage(goodsID, customerID, batchID, repositoryID, passed, scratch, damage);
            }
        }
        return true;
    }

    /**
     * 从检测的良品库存中减去出库数量
     *
     * @param goodsID      货物ID
     * @param batchID      批次ID
     * @param repositoryID 仓库ID
     * @param number       出库良品数
     * @return 返回一个 boolean 值，若值为 true 表示数目减少成功，否则表示增加失败
     */
    @Override
    public boolean passedDetectStorageDecrease(Integer goodsID, Integer batchID, Integer repositoryID, long number) throws DetectStorageServiceException {
        if (number < 0)
            return false;
        synchronized (this){
            //检查检测库存是否存在该记录
            DetectStorage detectStorage = getDetectStorage(goodsID, batchID, repositoryID);
            if (detectStorage != null){
                //检查需要出库量是否小于待出库库存
                if ( number > detectStorage.getPassed())
                    return false;
                long newPassed = detectStorage.getPassed() - number;
                updatePassedDetectStorage(goodsID, batchID, repositoryID, newPassed);
            }
        }
        return true;
    }

    /**
     * 检查货物ID对应的记录是否存在
     *
     * @param goodsID 货物ID
     * @return 若存在则返回true，否则返回false
     */
    private boolean goodsValidate(Integer goodsID) throws DetectStorageServiceException {
        try {
            Goods goods = goodsMapper.selectById(goodsID);
            return goods != null;
        } catch (PersistenceException e) {
            throw new DetectStorageServiceException(e);
        }
    }

    /**
     * 检查货物ID对应的记录是否存在
     *
     * @param customerID
     * @return 若存在则返回true，否则返回false
     */
    private boolean customerValidate(Integer customerID) throws DetectStorageServiceException {
        try {
            Customer customer = customerMapper.selectById(customerID);
            return customer != null;
        } catch (PersistenceException e) {
            throw new DetectStorageServiceException(e);
        }
    }

    /**
     * 检查批次ID对应的记录是否存在
     *
     * @param batchID 批次ID
     * @return 若存在则返回true，否则返回false
     */
    private boolean batchValidate(Integer batchID) throws DetectStorageServiceException {
        try {
            RepositoryBatch repositoryBatch = repositoryBatchMapper.selectByID(batchID,null);
            return repositoryBatch != null;
        } catch (PersistenceException e) {
            throw new DetectStorageServiceException(e);
        }
    }

    /**
     * 检查仓库ID对应的记录是否存在
     *
     * @param repositoryID 仓库ID
     * @return 若存在则返回true，否则返回false
     */
    private boolean repositoryValidate(Integer repositoryID) throws DetectStorageServiceException {
        try {
            Repository repository = repositoryMapper.selectByID(repositoryID);
            return repository != null;
        } catch (PersistenceException e) {
            throw new DetectStorageServiceException(e);
        }
    }

}
