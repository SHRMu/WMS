package de.demarks.wms.common.service.Impl;

import de.demarks.wms.common.service.Interface.DetectStorageService;
import de.demarks.wms.common.service.Interface.DetectManageService;
import de.demarks.wms.common.service.Interface.StorageManageService;
import de.demarks.wms.dao.*;
import de.demarks.wms.domain.DetectDO;
import de.demarks.wms.domain.Goods;
import de.demarks.wms.domain.Repository;
import de.demarks.wms.domain.RepositoryBatch;
import de.demarks.wms.exception.DetectManageServiceException;
import de.demarks.wms.exception.DetectStorageServiceException;
import de.demarks.wms.exception.StorageManageServiceException;
import de.demarks.wms.util.aop.UserOperation;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class DetectManageServiceImpl implements DetectManageService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private RepositoryBatchMapper repositoryBatchMapper;

    @Autowired
    private RepositoryMapper repositoryMapper;

    @Autowired
    private StorageManageService storageManageService;

    @Autowired
    private DetectStorageService detectStorageService;

    @Autowired
    private DetectMapper detectMapper;


    /**
     * 货物检测操作
     *
     * @param goodsID       货物ID
     * @param batchID       批次ID
     * @param repositoryID  仓库ID
     * @param passed        良品数量
     * @param scratch       划痕数量
     * @param damage        故障数量
     * @return 返回一个boolean 值，若值为true表示入库成功，否则表示入库失败
     */
    @UserOperation(value = "货物检测登记")
    @Override
    public boolean detectOperation(Integer goodsID, Integer batchID, Integer repositoryID, long passed, long scratch, long damage, String personInCharge) throws DetectManageServiceException {
        // ID对应的记录是否存在
        if (!(goodsValidate(goodsID) && batchValidate(batchID) && repositoryValidate(repositoryID)))
            return false;

        if (personInCharge == null)
            return false;

        // 检查数量有效性
        if (passed < 0 || scratch < 0 || damage < 0)
            return false;

        try {
            // 更新库存记录
            boolean isSuccess;
            long total = passed + scratch + damage; //检测总数

            isSuccess = storageManageService.storageDecrease(goodsID, batchID, repositoryID, total) && detectStorageService.storageIncrease(goodsID, batchID, repositoryID, passed, scratch, damage );

            // 保存入库记录
            if (isSuccess) {
                DetectDO detectDO = new DetectDO();
                detectDO.setGoodsID(goodsID);
                detectDO.setBatchID(batchID);
                detectDO.setRepositoryID(repositoryID);
                detectDO.setNumber(total);
                detectDO.setPassed(passed);
                detectDO.setScratch(scratch);
                detectDO.setDamage(damage);
                detectDO.setTime(new Date());
                detectDO.setPersonInCharge(personInCharge);
                detectMapper.insert(detectDO);
            }

            return isSuccess;
        } catch (PersistenceException | StorageManageServiceException | DetectStorageServiceException e) {
            throw new DetectManageServiceException(e);
        }
    }


    /**
     * 查询检测记录
     *
     * @param batchID      批次
     * @param repositoryID 仓库ID
     * @param endDateStr   查询记录起始日期
     * @param startDateStr 查询记录结束日期
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectDetectRecord(Integer batchID, Integer repositoryID, String startDateStr, String endDateStr) throws DetectManageServiceException{
        return selectDetectRecord(batchID, repositoryID, startDateStr, endDateStr, -1, -1);
    }

    /**
     * 分页查询检测记录
     *
     * @param batchID      批次
     * @param repositoryID 仓库ID
     * @param endDateStr   查询记录起始日期
     * @param startDateStr 查询记录结束日期
     * @param offset       分页偏移值
     * @param limit        分页大小
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> selectDetectRecord(Integer batchID, Integer repositoryID, String startDateStr, String endDateStr, int offset, int limit) throws DetectManageServiceException{
        return null;
    }


    /**
     * 检查货物ID对应的记录是否存在
     *
     * @param goodsID 货物ID
     * @return 若存在则返回true，否则返回false
     */
    private boolean goodsValidate(Integer goodsID) throws DetectManageServiceException {
        try {
            Goods goods = goodsMapper.selectById(goodsID);
            return goods != null;
        } catch (PersistenceException e) {
            throw new DetectManageServiceException(e);
        }
    }

    /**
     * 检查批次ID对应的记录是否存在
     *
     * @param batchID 批次ID
     * @return 若存在则返回true，否则返回false
     */
    private boolean batchValidate(Integer batchID) throws DetectManageServiceException {
        try {
            RepositoryBatch repositoryBatch = repositoryBatchMapper.selectByID(batchID);
            return repositoryBatch != null;
        } catch (PersistenceException e) {
            throw new DetectManageServiceException(e);
        }
    }


    /**
     * 检查仓库ID对应的记录是否存在
     *
     * @param repositoryID 仓库ID
     * @return 若存在则返回true，否则返回false
     */
    private boolean repositoryValidate(Integer repositoryID) throws DetectManageServiceException {
        try {
            Repository repository = repositoryMapper.selectByID(repositoryID);
            return repository != null;
        } catch (PersistenceException e) {
            throw new DetectManageServiceException(e);
        }
    }

//    /**
//     * 检查供应商ID对应的记录是否存在
//     *
//     * @param supplierID 供应商ID
//     * @return 若存在则返回true，否则返回false
//     */
//    private boolean supplierValidate(Integer supplierID) throws StockRecordManageServiceException {
//        try {
//            Supplier supplier = supplierMapper.selectById(supplierID);
//            return supplier != null;
//        } catch (PersistenceException e) {
//            throw new StockRecordManageServiceException(e);
//        }
//    }



}
