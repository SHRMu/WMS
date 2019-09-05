package de.demarks.wms.dao;

import de.demarks.wms.domain.RepositoryAdmin;
import de.demarks.wms.domain.RepositoryBatch;
import de.demarks.wms.exception.RepositoryManageServiceException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * RepositoryBatch 映射器
 * @author Shouran
 *
 */
@Repository
public interface RepositoryBatchMapper {

    /**
     * 选择所有的仓库批次信息
     * @return 返回所有的仓库批次信息
     */
    List<RepositoryBatch> selectAll();

    /**
     * 选择指定 Batch ID 的批次信息
     * @param BatchID 系统自动化BATCH_ID
     * @return 返回指定 BATCH_ID 的批次信息
     */
    RepositoryBatch selectByID(Integer BatchID);

    /**
     * 选择指定 Batch Code 的批次信息
     * @param code 各仓库自定的 BATCH_CODE
     * @return 返回指定 BATCH_CODE 的批次信息
     */
    List<RepositoryBatch> selectByCode(String code);

    /**
     * 选择指定 Batch Status 的批次信息
     * @param status 各仓库自定的 BATCH_STATUS
     * @return 返回指定 BATCH_STATUS 的批次信息
     */
    List<RepositoryBatch> selectByStatus(String status);

    /**
     * 选择指定 Batch repositoryID 的批次信息
     * @param repositoryID 各仓库自定的 BATCH_repositoryID
     * @return 返回指定 BATCH_repositoryID 的批次信息
     */
    List<RepositoryBatch> selectByRepositoryID(Integer repositoryID);

    /**
     * 插入一条仓库批次信息
     * @param repositoryBatch 仓库批次信息
     */
    void insert(RepositoryBatch repositoryBatch);

    /**
     * 更新 repositoryBatch 记录
     * @param repositoryBatch 批次信息
     */
    void update(RepositoryBatch repositoryBatch);

    /**
     * 删除指定 Batch ID 的 RepositoryBatch 记录
     * @param BatchID 批次ID
     */
    void deleteByID(Integer BatchID);




}
