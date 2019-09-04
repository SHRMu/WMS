package de.demarks.wms.dao;

import de.demarks.wms.domain.RepositoryAdmin;
import de.demarks.wms.domain.RepositoryBatch;
import org.springframework.stereotype.Repository;

import java.util.List;

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
     * 选择指定 BATCH_ID 的批次信息
     * @param id 系统自动化BATCH_ID
     * @return 返回指定 BATCH_ID 的批次信息
     */
    RepositoryBatch selectByID(Integer id);

    /**
     * 选择指定 BATCH_NUMBER 的批次信息
     * @param number 各仓库自定的BATCH_NUMBER
     * @return 返回指定 BATCH_NUMBER 的批次信息
     */
    List<RepositoryBatch> selectByNumber(Integer number);


    List<RepositoryBatch> selectByRepositoryID(Integer repositoryID);

    /**
     * 插入一条仓库管理员信息
     * @param repositoryBatch 仓库管理员信息
     */
    void insert(RepositoryBatch repositoryBatch);


}
