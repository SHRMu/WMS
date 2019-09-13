package de.demarks.wms.dao;

import de.demarks.wms.domain.Storage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

/**
 * 待检测库存信息映射器
 *
 * @author huanyingcool
 *
 */
@Repository
public interface StorageMapper {

	/**
	 * 选择所有的库存信息
	 * @return 返回所有的库存信息
	 */
	List<Storage> selectAll(@Param("goodsID") Integer goodsID,
							@Param("batchID") Integer batchID,
							@Param("repositoryID") Integer repositoryID);
	
	/**
	 * 选择指定货物名的库存信息
	 * @param goodsName 货物名称
	 * @return 返回所有指定货物名称的库存信息
	 */
	List<Storage> selectByGoodsName(@Param("goodsName") String goodsName,
									 @Param("batchID") Integer batchID,
									 @Param("repositoryID") Integer repositoryID);

	/**
	 * 更新库存信息
	 * 该库存信息必需已经存在于数据库当中，否则更新无效
	 * @param storage 库存信息
	 */
	void update(Storage storage);
	
	/**
	 * 插入新的库存信息
	 * @param storage 库存信息
	 */
	void insert(Storage storage);
	
	/**
	 * 批量导入库存信息
	 * @param storages 若干条库存信息
	 */
	void insertBatch(List<Storage> storages);
	
	/**
	 * 删除指定仓库中的指定货物的库存信息
	 * @param goodsID 货物ID
	 * @param batchID 批次ID
	 * @param repositoryID 仓库ID
	 */
	void delete(@Param("goodsID") Integer goodsID,@Param("batchID") Integer batchID, @Param("repositoryID") Integer repositoryID);

}
