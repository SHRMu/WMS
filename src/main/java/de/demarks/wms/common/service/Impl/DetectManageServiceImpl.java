package de.demarks.wms.common.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import de.demarks.wms.common.service.Interface.DetectStorageService;
import de.demarks.wms.common.service.Interface.DetectManageService;
import de.demarks.wms.common.service.Interface.StorageManageService;
import de.demarks.wms.dao.*;
import de.demarks.wms.domain.*;
import de.demarks.wms.exception.DetectManageServiceException;
import de.demarks.wms.exception.DetectStorageServiceException;
import de.demarks.wms.exception.StorageManageServiceException;
import de.demarks.wms.util.aop.UserOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DetectManageServiceImpl implements DetectManageService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private RepositoryBatchMapper repositoryBatchMapper;
    @Autowired
    private RepositoryMapper repositoryMapper;

    @Autowired
    private DetectMapper detectMapper;
    @Autowired
    private DetectStorageService detectStorageService;
    @Autowired
    private StorageManageService storageManageService;

    /**
     * 货物检测操作
     * @param goodsID
     * @param batchID
     * @param repositoryID
     * @param passed
     * @param scratch
     * @param damage
     * @param personInCharge
     * @return
     * @throws DetectManageServiceException
     */
    @UserOperation(value = "检测录入")
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
            boolean deSuccess, inSuccess;
            long total = passed + scratch + damage; //检测总数

            //从待测数库存中减去总数
            deSuccess = storageManageService.storageDecrease(goodsID, batchID, repositoryID, total);
            //在已检测库存中添加详细数量
            inSuccess = detectStorageService.detectStorageIncrease(goodsID, batchID, repositoryID, passed, scratch, damage);

            // 保存入库记录
            if (deSuccess && inSuccess) {
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
            return deSuccess && inSuccess;
        } catch (PersistenceException | StorageManageServiceException | DetectStorageServiceException e) {
            throw new DetectManageServiceException(e);
        }
    }

    /**
     * 查询检测记录
     * @param goodsID
     * @param batchID
     * @param repositoryID
     * @param startDateStr
     * @param endDateStr
     * @return
     * @throws DetectManageServiceException
     */
    @Override
    public Map<String, Object> selectDetectRecordByGoodsID(Integer goodsID, Integer batchID, Integer repositoryID, String startDateStr, String endDateStr) throws DetectManageServiceException{
        return selectDetectRecordByGoodsID(goodsID, batchID, repositoryID, startDateStr, endDateStr, -1, -1);
    }

    /**
     * 分页 查询检测记录
     * @param goodsID
     * @param batchID
     * @param repositoryID
     * @param startDateStr
     * @param endDateStr
     * @param offset
     * @param limit
     * @return
     * @throws DetectManageServiceException
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> selectDetectRecordByGoodsID(Integer goodsID, Integer batchID, Integer repositoryID, String startDateStr, String endDateStr, int offset, int limit) throws DetectManageServiceException{
        // 初始化结果集
        Map<String, Object> result = new HashMap<>();
        List<DetectDO> detectDOS;
        long total = 0;
        boolean isPagination = true;

        // 检查是否需要分页查询
        if (offset < 0 || limit < 0)
            isPagination = false;

        // 检查传入参数
        if (goodsID<0)
            goodsID = null;
        if (batchID<0)
            batchID = null;
        if (repositoryID<0)
            repositoryID = null;

        // 转换 Date 对象
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;
        Date newEndDate = null;
        try {
            if (StringUtils.isNotEmpty(startDateStr))
                startDate = dateFormat.parse(startDateStr);
            if (StringUtils.isNotEmpty(endDateStr))
            {
                endDate = dateFormat.parse(endDateStr);
                newEndDate = new Date(endDate.getTime()+(24*60*60*1000)-1);
            }
        } catch (ParseException e) {
            throw new DetectManageServiceException(e);
        }

        // 查询记录
        try {
            if (isPagination) {
                PageHelper.offsetPage(offset, limit);
                detectDOS = detectMapper.selectByGoodsID(goodsID, batchID, repositoryID, startDate, endDate);
                if (detectDOS != null)
                    total = new PageInfo<>(detectDOS).getTotal();
                else
                    detectDOS = new ArrayList<>(10);
            } else {
                detectDOS = detectMapper.selectByGoodsID(goodsID, batchID, repositoryID, startDate, endDate);
                if (detectDOS != null)
                    total = detectDOS.size();
                else
                    detectDOS = new ArrayList<>(10);
            }
        } catch (PersistenceException e) {
            throw new DetectManageServiceException(e);
        }

        List<DetectDTO> detectDTOS = new ArrayList<>();
        if (detectDOS != null)
            detectDOS.forEach(detectDO -> detectDTOS.add(detectDoConvertToDetectDTO(detectDO)));

        result.put("data", detectDTOS);
        result.put("total", total);
        return result;
    }

    /**
     *
     * @param goodsName
     * @param batchID
     * @param repositoryID
     * @param startDateStr
     * @param endDateStr
     * @return
     * @throws DetectManageServiceException
     */
    @Override
    public Map<String, Object> selectDetectRecordByGoodsName(String goodsName, Integer batchID, Integer repositoryID, String startDateStr, String endDateStr) throws DetectManageServiceException {
        return selectDetectRecordByGoodsName(goodsName,batchID,repositoryID,startDateStr, endDateStr, -1, -1);
    }

    /**
     * 分页 模糊查询
     * @param goodsName
     * @param batchID
     * @param repositoryID
     * @param startDateStr
     * @param endDateStr
     * @param offset
     * @param limit
     * @return
     * @throws DetectManageServiceException
     */
    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> selectDetectRecordByGoodsName(String goodsName, Integer batchID, Integer repositoryID, String startDateStr, String endDateStr, int offset, int limit) throws DetectManageServiceException {
        // 初始化结果集
        Map<String, Object> result = new HashMap<>();
        List<DetectDO> detectDOS;
        long total = 0;
        boolean isPagination = true;

        // 检查是否需要分页查询
        if (offset < 0 || limit < 0)
            isPagination = false;

        // 检查传入参数
        if (batchID<0)
            batchID = null;
        if (repositoryID<0)
            repositoryID = null;

        // 转换 Date 对象
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;
        Date newEndDate = null;
        try {
            if (StringUtils.isNotEmpty(startDateStr))
                startDate = dateFormat.parse(startDateStr);
            if (StringUtils.isNotEmpty(endDateStr))
            {
                endDate = dateFormat.parse(endDateStr);
                newEndDate = new Date(endDate.getTime()+(24*60*60*1000)-1);
            }
        } catch (ParseException e) {
            throw new DetectManageServiceException(e);
        }

        // 查询记录
        try {
            if (isPagination) {
                PageHelper.offsetPage(offset, limit);
                detectDOS = detectMapper.selectByGoodsName(goodsName, batchID, repositoryID, startDate, endDate);
                if (detectDOS != null)
                    total = new PageInfo<>(detectDOS).getTotal();
                else
                    detectDOS = new ArrayList<>(10);
            } else {
                detectDOS = detectMapper.selectByGoodsName(goodsName, batchID, repositoryID, startDate, endDate);
                if (detectDOS != null)
                    total = detectDOS.size();
                else
                    detectDOS = new ArrayList<>(10);
            }
        } catch (PersistenceException e) {
            throw new DetectManageServiceException(e);
        }

        List<DetectDTO> detectDTOS = new ArrayList<>();
        if (detectDOS != null)
            detectDOS.forEach(detectDO -> detectDTOS.add(detectDoConvertToDetectDTO(detectDO)));

        result.put("data", detectDTOS);
        result.put("total", total);
        return result;
    }

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm");

    private DetectDTO detectDoConvertToDetectDTO(DetectDO detectDO) {
        DetectDTO detectDTO = new DetectDTO();
        detectDTO.setId(detectDO.getId());
        detectDTO.setGoodsID(detectDO.getGoodsID());
        detectDTO.setGoodsName(detectDO.getGoodsName());
        detectDTO.setBatchID(detectDO.getBatchID());
        detectDTO.setBatchCode(detectDO.getBatchCode());
        detectDTO.setTime(dateFormat.format(detectDO.getTime()));
        return detectDTO;
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
            RepositoryBatch repositoryBatch = repositoryBatchMapper.selectByID(batchID,null);
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

}
