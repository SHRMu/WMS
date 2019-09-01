package de.demarks.wms.common.service.Impl;

import de.demarks.wms.common.service.Interface.DetectStorageManageService;
import de.demarks.wms.dao.DetectStorageMapper;
import de.demarks.wms.dao.GoodsMapper;
import de.demarks.wms.dao.RepositoryMapper;
import de.demarks.wms.domain.DetectStorage;
import de.demarks.wms.domain.Goods;
import de.demarks.wms.domain.Repository;
import de.demarks.wms.domain.Storage;
import de.demarks.wms.exception.DetectStorageManageServiceException;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

@Service
public class DetectStorageManageServiceImpl implements DetectStorageManageService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private RepositoryMapper repositoryMapper;

    @Autowired
    private DetectStorageMapper detectStorageMapper;

    @Override
    public Map<String, Object> selectAll(Integer repositoryID) throws DetectStorageManageServiceException {
        return null;
    }

    @Override
    public Map<String, Object> selectAll(Integer repositoryID, int offset, int limit) throws DetectStorageManageServiceException {
        return null;
    }

    @Override
    public Map<String, Object> selectByGoodsID(Integer goodsID, Integer repositoryID) throws DetectStorageManageServiceException {
        return null;
    }

    @Override
    public Map<String, Object> selectByGoodsID(Integer goodsID, Integer repositoryID, int offset, int limit) throws DetectStorageManageServiceException {
        return null;
    }

    @Override
    public Map<String, Object> selectByGoodsName(String goodsName, Integer repositoryID) throws DetectStorageManageServiceException {
        return null;
    }

    @Override
    public Map<String, Object> selectByGoodsName(String goodsName, Integer repositoryID, int offset, int limit) throws DetectStorageManageServiceException {
        return null;
    }

    @Override
    public Map<String, Object> selectByGoodsType(String goodsType, Integer Repository) throws DetectStorageManageServiceException {
        return null;
    }

    @Override
    public Map<String, Object> selectByGoodsType(String goodsType, Integer repositoryID, int offset, int limit) throws DetectStorageManageServiceException {
        return null;
    }

    @Override
    public boolean addNewStorage(Integer goodsID, Integer repositoryID, long passed, long scratch, long damage) throws DetectStorageManageServiceException {
        try {
            boolean isAvailable = true;

            // validate
            Goods goods = goodsMapper.selectById(goodsID);
            Repository repository = repositoryMapper.selectByID(repositoryID);
            if (goods == null)
                isAvailable = false;
            if (repository == null)
                isAvailable = false;
            if (passed<0 || scratch<0 || damage<0)
                isAvailable = false;
            List<DetectStorage> detectStorageList = detectStorageMapper.selectByGoodsIDAndRepositoryID(goodsID, repositoryID);
            if (!(detectStorageList != null && detectStorageList.isEmpty()))
                isAvailable = false;

            if (isAvailable) {
                // insert
                DetectStorage detectStorage = new DetectStorage();
                detectStorage.setGoodsID(goodsID);
                detectStorage.setRepositoryID(repositoryID);
                detectStorage.setPassed(passed);
                detectStorage.setScratch(scratch);
                detectStorage.setDamage(damage);
                detectStorageMapper.insert(detectStorage);
            }

            return isAvailable;
        } catch (PersistenceException e) {
            throw new DetectStorageManageServiceException(e);
        }

    }

    @Override
    public boolean updateStorage(Integer goodsID, Integer repositoryID, long passed, long scratch, long damage) throws DetectStorageManageServiceException {
        try {
            boolean isUpdate = false;
            // validate
            List<DetectStorage> detectStorageList = detectStorageMapper.selectByGoodsIDAndRepositoryID(goodsID, repositoryID);
            if (detectStorageList != null && !detectStorageList.isEmpty()) {
                if ( passed >= 0 && scratch > 0 && damage > 0) {
                    // update
                    DetectStorage detectStorage = detectStorageList.get(0);
                    detectStorage.setPassed(passed);
                    detectStorage.setScratch(scratch);
                    detectStorage.setDamage(damage);
                    detectStorageMapper.update(detectStorage);
                    isUpdate = true;
                }
            }

            return isUpdate;
        } catch (PersistenceException e) {
            throw new DetectStorageManageServiceException(e);
        }
    }

    @Override
    public boolean storageDecrease(Integer goodsID, Integer repositoryID, long passed, long scratch, long damage) throws DetectStorageManageServiceException {
        return false;
    }

    @Override
    public boolean deleteStorage(Integer goodsID, Integer repositoryID) throws DetectStorageManageServiceException {
        return false;
    }

    @Override
    public Map<String, Object> importStorage(MultipartFile file) throws DetectStorageManageServiceException {
        return null;
    }

    @Override
    public File exportStorage(List<Storage> storages) {
        return null;
    }

    /**
     * 为指定的货物库存记录增加指定数目
     *
     * @param goodsID      货物ID
     * @param repositoryID 仓库ID
     * @param passed       入库数量
     * @param scratch      划痕数量
     * @param damage       故障数量
     * @return 返回一个 boolean 值，若值为true表示数目增加成功，否则表示增加失败
     */
    @Override
    public boolean storageIncrease(Integer goodsID, Integer repositoryID, long passed, long scratch, long damage) throws DetectStorageManageServiceException {
        // 检查货物库存增加数目的有效性
        if ( passed < 0 || scratch < 0 || damage < 0)
            return false;

        synchronized (this) {
            // 检查对应的库存记录是否存在
            DetectStorage detectStorage = getStorage(goodsID, repositoryID);
            if (detectStorage != null) {
                long newPassed = detectStorage.getPassed() + passed;
                long newScratch = detectStorage.getScratch() + scratch;
                long newDamage = detectStorage.getDamage() + damage;
                updateStorage(goodsID, repositoryID, newPassed, newScratch, newDamage);
            } else {
                addNewStorage(goodsID, repositoryID, passed, scratch, damage);
            }
        }
        return true;
    }

    /**
     * 获取指定货物ID，仓库ID对应的库存记录
     *
     * @param goodsID      货物ID
     * @param repositoryID 仓库ID
     * @return 若存在则返回对应的记录，否则返回null
     */
    private DetectStorage getStorage(Integer goodsID, Integer repositoryID) {
        DetectStorage detectStorage = null;
        List<DetectStorage> detectStorageList = detectStorageMapper.selectByGoodsIDAndRepositoryID(goodsID, repositoryID);
        if (!detectStorageList.isEmpty())
            detectStorage = detectStorageList.get(0);
        return detectStorage;
    }

}
