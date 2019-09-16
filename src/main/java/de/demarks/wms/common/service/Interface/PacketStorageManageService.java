package de.demarks.wms.common.service.Interface;

import de.demarks.wms.exception.PacketStorageManageServiceException;
import de.demarks.wms.exception.PreStockManageServiceException;
import de.demarks.wms.exception.StockRecordManageServiceException;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 预报库存管理 Service
 *
 * @author huanyingcool
 */
public interface PacketStorageManageService {

   /**
    *  返回所有的预报库存记录
    * @param repositoryID
    * @return
    * @throws PacketStorageManageServiceException
    */
   Map<String, Object> selectAll(@Param("repository") Integer repositoryID) throws PacketStorageManageServiceException;

   /**
    * 分页 返回所有的预报库存记录
    * @param repositoryID
    * @param offset
    * @param limit
    * @return
    * @throws PacketStorageManageServiceException
    */
   Map<String, Object> selectAll(@Param("repository") Integer repositoryID,
                                 @Param("offset") Integer offset,
                                 @Param("limit") Integer limit) throws PacketStorageManageServiceException;

   /**
    *
    * @param goodsID
    * @param packetID
    * @param repositoryID
    * @return
    * @throws PacketStorageManageServiceException
    */
   Map<String, Object> selectByGoodsID(@Param("goodsID") Integer goodsID,
                                    @Param("packetID") Integer packetID,
                                    @Param("repositoryID") Integer repositoryID) throws PacketStorageManageServiceException;

   /**
    *
    * @param goodsID
    * @param packetID
    * @param repositoryID
    * @param offset
    * @param limit
    * @return
    * @throws PacketStorageManageServiceException
    */
   Map<String, Object> selectByGoodsID(@Param("goodsID") Integer goodsID,
                                       @Param("packetID") Integer packetID,
                                       @Param("repositoryID") Integer repositoryID,
                                       @Param("offset") Integer offset,
                                       @Param("limit") Integer limit) throws PacketStorageManageServiceException;

   /**
    *
    * @param packetID
    * @param status
    * @param repositoryID
    * @return
    * @throws PacketStorageManageServiceException
    */
   Map<String, Object> selectByPacketID(@Param("packetID") Integer packetID, @Param("status") String status,
                                          @Param("repositoryID") Integer repositoryID) throws PacketStorageManageServiceException;

   /**
    *
    * @param packetID
    * @param status
    * @param repositoryID
    * @param offset
    * @param limit
    * @return
    * @throws PacketStorageManageServiceException
    */
   Map<String, Object> selectByPacketID(@Param("packetID") Integer packetID, @Param("status") String status,
                                          @Param("repositoryID") Integer repositoryID, @Param("offset") Integer offset, @Param("limit") Integer limit) throws PacketStorageManageServiceException;

   /**
    *
    * @param trace
    * @param status
    * @param repositoryID
    * @return
    * @throws PacketStorageManageServiceException
    */
   Map<String, Object> selectApproximate(@Param("trace") String trace,
                                         @Param("status") String status,
                                         @Param("repositoryID") Integer repositoryID) throws PacketStorageManageServiceException;

   /**
    *
    * @param trace
    * @param status
    * @param repositoryID
    * @param offset
    * @param limit
    * @return
    * @throws PacketStorageManageServiceException
    */
   Map<String, Object> selectApproximate(@Param("trace") String trace,
                                         @Param("status") String status,
                                         @Param("repositoryID") Integer repositoryID,
                                         @Param("offset") Integer offset,
                                         @Param("limit") Integer limit) throws PacketStorageManageServiceException;


   /**
    *
    * @param goodsID
    * @param packetID
    * @param repositoryID
    * @param number
    * @param storage
    * @return
    * @throws PacketStorageManageServiceException
    */
   boolean updatePacketStorage(@Param("goodsID") Integer goodsID,
                               @Param("packetID") Integer packetID,
                               @Param("repositoryID") Integer repositoryID,
                               @Param("number") long number,
                               @Param("storage") long storage) throws PacketStorageManageServiceException;

   /**
    *
    * @param goodsID
    * @param packetID
    * @param repositoryID
    * @param number
    * @return
    * @throws PacketStorageManageServiceException
    */
   boolean packetStorageDecrease(@Param("goodsID") Integer goodsID,
                                 @Param("packetID") Integer packetID,
                                 @Param("repositoryID") Integer repositoryID,
                                 @Param("number") long number) throws PacketStorageManageServiceException;

}