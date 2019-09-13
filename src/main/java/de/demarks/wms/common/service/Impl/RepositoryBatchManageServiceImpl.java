package de.demarks.wms.common.service.Impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import de.demarks.wms.common.service.Interface.RepositoryBatchManageService;

import de.demarks.wms.dao.DetectStorageMapper;
import de.demarks.wms.dao.RepositoryBatchMapper;
import de.demarks.wms.dao.StorageMapper;
import de.demarks.wms.domain.DetectStorage;
import de.demarks.wms.domain.RepositoryBatch;
import de.demarks.wms.domain.Storage;
import de.demarks.wms.exception.RepositoryBatchManageServiceException;
import de.demarks.wms.util.aop.UserOperation;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * 批次管理 service 实现类
 *
 * @author Shouran
 */
@Service
public class RepositoryBatchManageServiceImpl implements RepositoryBatchManageService {

    @Autowired
    private RepositoryBatchMapper repositoryBatchMapper;
    @Autowired
    private StorageMapper storageMapper;
    @Autowired
    private DetectStorageMapper detectStorageMapper;

    /**
     * 返回指定 Batch ID 的批次记录
     *
     * @param batchID 批次ID
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectById(Integer batchID) throws RepositoryBatchManageServiceException {
        // 初始化結果集
        Map<String, Object> resultSet = new HashMap<>();
        List<RepositoryBatch> batches = new ArrayList<>();
        long total = 0;

        // 查詢
        RepositoryBatch repositoryBatch;
        try {
            repositoryBatch = repositoryBatchMapper.selectByID(batchID,null);
        } catch (PersistenceException e) {
            throw new RepositoryBatchManageServiceException(e);
        }

        if (repositoryBatch != null) {
            batches.add(repositoryBatch);
            total = 1;
        }

        resultSet.put("data", batches);
        resultSet.put("total", total);
        return resultSet;
    }

    /**
     * 返回指定 Batch code 的批次记录
     * 支持模糊查询
     *
     * @param code 仓库名称
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectByCode(String code) throws RepositoryBatchManageServiceException {
        return selectByCode(code, -1, -1);
    }

    /**
     * 分页返回指定 Batch code 的批次记录
     * 支持查询分页以及模糊查询
     *
     * @param code 批次编号
     * @param offset  分页的偏移值
     * @param limit   分页的大小
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectByCode(String code, int offset, int limit) throws RepositoryBatchManageServiceException {
        // 初始化結果集
        Map<String, Object> resultSet = new HashMap<>();
        List<RepositoryBatch> batches;
        long total = 0;
        boolean isPagination = true;

        // validate
        if (offset < 0 || limit < 0)
            isPagination = false;

        // query
        try {
            if (isPagination) {
                PageHelper.offsetPage(offset, limit);
                batches = repositoryBatchMapper.selectByCode(code,null);
                if (batches != null) {
                    PageInfo<RepositoryBatch> pageInfo = new PageInfo<>(batches);
                    total = pageInfo.getTotal();
                } else
                    batches = new ArrayList<>();
            } else {
                batches = repositoryBatchMapper.selectByCode(code,null);
                if (batches != null)
                    total = batches.size();
                else
                    batches = new ArrayList<>();
            }
        } catch (PersistenceException e) {
            throw new RepositoryBatchManageServiceException(e);
        }

        resultSet.put("data", batches);
        resultSet.put("total", total);
        return resultSet;
    }

    /**
     * 返回指定 Batch status 的批次记录
     * 支持模糊查询
     *
     * @param status 批次状态
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectByStatus(String status) throws RepositoryBatchManageServiceException {
        return selectByStatus(status, -1, -1);
    }

    /**
     * 分页返回指定 Batch code 的批次记录
     * 支持查询分页以及模糊查询
     *
     * @param status 批次编号
     * @param offset  分页的偏移值
     * @param limit   分页的大小
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectByStatus(String status, int offset, int limit) throws RepositoryBatchManageServiceException {
        // 初始化結果集
        Map<String, Object> resultSet = new HashMap<>();
        List<RepositoryBatch> batches;
        long total = 0;
        boolean isPagination = true;

        // validate
        if (offset < 0 || limit < 0)
            isPagination = false;

        // query
        try {
            if (isPagination) {
                PageHelper.offsetPage(offset, limit);
                batches = repositoryBatchMapper.selectByStatus(status,null);
                if (batches != null) {
                    PageInfo<RepositoryBatch> pageInfo = new PageInfo<>(batches);
                    total = pageInfo.getTotal();
                } else
                    batches = new ArrayList<>();
            } else {
                batches = repositoryBatchMapper.selectByStatus(status,null);
                if (batches != null)
                    total = batches.size();
                else
                    batches = new ArrayList<>();
            }
        } catch (PersistenceException e) {
            throw new RepositoryBatchManageServiceException(e);
        }

        resultSet.put("data", batches);
        resultSet.put("total", total);
        return resultSet;
    }

    /**
     * 查询所有的批次记录
     *
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    public Map<String, Object> selectAll() throws RepositoryBatchManageServiceException {
        return selectAll(-1,-1);
    }

    /**
     * 分页查询所有的批次记录
     *
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @Override
    public Map<String, Object> selectAll(int offset, int limit) throws RepositoryBatchManageServiceException {
        // 初始化结果集
        Map<String, Object> resultSet = new HashMap<>();
        List<RepositoryBatch> batches;
        long total = 0;
        boolean isPagination = true;

        // validate
        if (offset < 0 || limit < 0)
            isPagination = false;

        //query
        try {
            if (isPagination) {
                PageHelper.offsetPage(offset, limit);
                batches = repositoryBatchMapper.selectAll(null);
                if (batches != null) {
                    PageInfo<RepositoryBatch> pageInfo = new PageInfo<>(batches);
                    total = pageInfo.getTotal();
                } else
                    batches = new ArrayList<>();
            } else {
                batches = repositoryBatchMapper.selectAll(null);
                if (batches != null)
                    total = batches.size();
                else
                    batches = new ArrayList<>();
            }
        } catch (PersistenceException e) {
            throw new RepositoryBatchManageServiceException(e);
        }

        resultSet.put("data", batches);
        resultSet.put("total", total);
        return resultSet;
    }

    /**
     * 添加仓库批次信息
     *
     * @param repositoryBatch 仓库批次信息
     * @return 返回一个boolean值，值为true代表添加成功，否则代表失败
     */
    @UserOperation(value = "添加批次信息")
    @Override
    public boolean addRepositoryBatch(RepositoryBatch repositoryBatch) throws RepositoryBatchManageServiceException {
        // 插入一条新的记录
        if (repositoryBatch != null) {
            try {
                // 有效性验证
                if (batchCheck(repositoryBatch)){
                    repositoryBatch.setTime(new Date());
                    repositoryBatchMapper.insert(repositoryBatch);
                }
                if (repositoryBatch.getId() != null) {
                    return true;
                }
            } catch (PersistenceException e) {
                throw new RepositoryBatchManageServiceException(e);
            }
        }
        return false;
    }

    /**
     * 更新仓库批次记录
     *
     * @param repositoryBatch 仓库批次信息
     * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
     */
    @UserOperation(value = "修改批次信息")
    @Override
    public boolean updateRepositoryBatch(RepositoryBatch repositoryBatch) throws RepositoryBatchManageServiceException {
        // 更新仓库记录
        if (repositoryBatch != null) {
            // 有效性验证
            try {
                if (batchCheck(repositoryBatch)) {
                    if (repositoryBatch.getId() != null) {
                        repositoryBatchMapper.update(repositoryBatch);
                        return true;
                    }
                }
            } catch (PersistenceException e) {
                throw new RepositoryBatchManageServiceException(e);
            }
        }
        return false;
    }

    /**
     * 删除批次记录
     *
     * @param batchID 批次ID
     * @param repositoryID 仓库ID
     * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
     */
    @UserOperation(value = "删除批次信息")
    @Override
    public boolean deleteRepositoryBatch(Integer batchID, Integer repositoryID) throws RepositoryBatchManageServiceException {
        try {
            //检查该批次下是否有待检测的库存
            List<Storage> storages = storageMapper.selectAll(null, batchID, repositoryID);
            if ( storages != null && !storages.isEmpty())
                return  false;

            //检查该批次下所有已测良品均已发货
            List<DetectStorage> detectStorageList = detectStorageMapper.selectAll(batchID, repositoryID);
            if ( detectStorageList != null && !detectStorageList.isEmpty()){
                for (DetectStorage detectStorage:
                     detectStorageList) {
                    if (detectStorage.getPassed() > 0)
                        return false;
                }
            }

            //检查该批次状态是否为 完结
            RepositoryBatch repositoryBatch = repositoryBatchMapper.selectByID(batchID,null);
            String status = repositoryBatch.getStatus();
            if (!status.equals("完结"))
                return false;

            // 删除记录
            repositoryBatchMapper.deleteByID(batchID);

            return true;
        } catch (PersistenceException e) {
            throw new RepositoryBatchManageServiceException(e);
        }
    }

    /**
     * 检查批次是否满足
     *
     * @param repositoryBatch 仓库信息
     * @return 若仓库信息满足要求则返回true，否则返回false
     */
    private boolean batchCheck(RepositoryBatch repositoryBatch) {
        return repositoryBatch.getCode()!= null && repositoryBatch.getStatus()!= null && repositoryBatch.getRepositoryID()!= null;
    }

}
