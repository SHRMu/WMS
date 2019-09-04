package de.demarks.wms.common.service.Interface;

import de.demarks.wms.domain.RepositoryBatch;
import de.demarks.wms.exception.RepositoryBatchManageServiceException;

/**
 * 仓库批次管理 service
 *
 * @author Shouran
 */
public interface RepositoryBatchManageService {

    /**
     * 添加仓库批次信息
     *
     * @param repositoryBatch 仓库批次信息
     * @return 返回一个boolean值，值为true代表添加成功，否则代表失败
     */
    boolean addRepositoryBatch(RepositoryBatch repositoryBatch) throws RepositoryBatchManageServiceException;

}
