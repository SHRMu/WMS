package de.demarks.wms.common.service.Impl;


import de.demarks.wms.common.service.Interface.RepositoryBatchManageService;

import de.demarks.wms.dao.RepositoryBatchMapper;
import de.demarks.wms.domain.RepositoryBatch;
import de.demarks.wms.exception.RepositoryBatchManageServiceException;
import de.demarks.wms.util.aop.UserOperation;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 仓库批次管理 service 实现类
 *
 * @author Shouran
 */
@Service
public class RepositoryBatchManageServiceImpl implements RepositoryBatchManageService {

    @Autowired
    private RepositoryBatchMapper repositoryBatchMapper;

    /**
     * 添加仓库管理员信息
     *
     * @param repositoryBatch 仓库管理员信息
     * @return 返回一个boolean值，值为true代表添加成功，否则代表失败
     */
    @UserOperation(value = "添加仓库批次信息")
    @Override
    public boolean addRepositoryBatch(RepositoryBatch repositoryBatch) throws RepositoryBatchManageServiceException {
        if (repositoryBatch != null) {
//            if (repositoryAdminCheck(repositoryAdmin)) {
                try {
                    // 添加仓库批次信息到数据库中
                    repositoryBatchMapper.insert(repositoryBatch);
                } catch (PersistenceException e) {
                    throw new RepositoryBatchManageServiceException(e, "Fail to persist repository batch info");
                }
//            }
        }
        return false;
    }

}
