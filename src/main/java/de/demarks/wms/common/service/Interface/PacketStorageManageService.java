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

   Map<String, Object> selectAll(@Param("packetID") Integer packetID,
                                 @Param("repositoryID") Integer repositoryID) throws PacketStorageManageServiceException;

   Map<String, Object> selectAll(@Param("packetID") Integer packetID,
                                 @Param("repositoryID") Integer repositoryID,
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


   Map<String, Object> selectByGoodsName(@Param("goodsName") String goodsName,
                                       @Param("packetID") Integer packetID,
                                       @Param("repositoryID") Integer repositoryID) throws PacketStorageManageServiceException;


   Map<String, Object> selectByGoodsName(@Param("goodsName") String goodsName,
                                          @Param("packetID") Integer packetID,
                                          @Param("repositoryID") Integer repositoryID,
                                          @Param("offset") Integer offset,
                                          @Param("limit") Integer limit) throws PacketStorageManageServiceException;

   /**
    *
    * @param trace
    * @param status
    * @param repositoryID
    * @return
    * @throws PacketStorageManageServiceException
    */
   Map<String, Object> selectByTrace(@Param("trace") String trace,
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
   Map<String, Object> selectByTrace(@Param("trace") String trace,
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