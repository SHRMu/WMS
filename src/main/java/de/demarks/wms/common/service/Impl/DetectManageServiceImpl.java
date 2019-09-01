package de.demarks.wms.common.service.Impl;

import de.demarks.wms.common.service.Interface.DetectStorageManageService;
import de.demarks.wms.common.service.Interface.DetectManageService;
import de.demarks.wms.dao.DetectMapper;
import de.demarks.wms.dao.GoodsMapper;
import de.demarks.wms.dao.RepositoryMapper;
import de.demarks.wms.domain.DetectDO;
import de.demarks.wms.domain.Goods;
import de.demarks.wms.domain.Repository;
import de.demarks.wms.exception.DetectManageServiceException;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DetectManageServiceImpl implements DetectManageService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private RepositoryMapper repositoryMapper;

    @Autowired
    private DetectMapper detectMapper;

    @Autowired
    private DetectStorageManageService detectStorageManageService;

    @Override
    public boolean detectionOperation(Integer goodsID, Integer batch, Integer repositoryID, long passed, long scratch, long damage, String personInCharge) throws DetectManageServiceException {
        // ID对应的记录是否存在
        if (!(goodsValidate(goodsID) && repositoryValidate(repositoryID)))
            return false;

        if (personInCharge == null)
            return false;

        // 检查入库数量有效性
        if (passed < 0)
            return false;

        try {
            // 更新库存记录
            boolean isSuccess = true;
//            isSuccess = detectStorageManageService.storageIncrease(goodsID, repositoryID, number);
//
            // 保存入库记录
            if (isSuccess) {
                DetectDO detectDO = new DetectDO();
                detectDO.setGoodID(goodsID);
                detectDO.setBatch(batch);
                detectDO.setRepositoryID(repositoryID);
                detectDO.setPassed(passed);
                detectDO.setScratch(scratch);
                detectDO.setDamage(damage);
                detectDO.setTime(new Date());
                detectDO.setPersonInCharge(personInCharge);
                detectMapper.insert(detectDO);
            }

            return isSuccess;
        } catch (PersistenceException e) {
            throw new DetectManageServiceException(e);
        }
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
}
