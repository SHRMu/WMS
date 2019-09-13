package de.demarks.wms.common.controller;
import de.demarks.wms.common.service.Interface.PacketManageService;
import de.demarks.wms.common.service.Interface.PacketStorageManageService;
import de.demarks.wms.common.util.Response;
import de.demarks.wms.common.util.ResponseUtil;
import de.demarks.wms.dao.PacketMapper;
import de.demarks.wms.domain.Packet;
import de.demarks.wms.domain.PacketStorage;
import de.demarks.wms.exception.PacketManageServiceException;
import de.demarks.wms.exception.PacketStorageManageServiceException;
import de.demarks.wms.exception.PreStockManageServiceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 *
 *  客户预报库存管理
 *
 * @author huanyingcool
 */

@Controller
@RequestMapping("**/packetStorageManage")
public class PacketStorageManageHandler {

    @Autowired
    private ResponseUtil responseUtil;
    @Autowired
    private PacketStorageManageService packetStorageManageService;

    private static final String SEARCH_BY_GOODS_ID = "searchByGoodsID";
    private static final String SEARCH_ALL_ACTIVE = "searchAllActive";
    private static final String SEARCH_ALL = "searchAll";

    /**
     *  查询预报包裹库存信息
     *
     * @param searchType       查询类型
     * @param keyword          查询关键字
     * @param packetBelong
     * @param repositoryBelong 查询仓库
     * @param offset           分页偏移值
     * @param limit            分页大小
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    private Map<String, Object> query(String searchType, String keyword, String packetBelong, String repositoryBelong,
                                            int offset, int limit) throws PacketStorageManageServiceException {
        Map<String, Object> queryResult = null;

        switch (searchType) {
            case SEARCH_BY_GOODS_ID:
                if (StringUtils.isNumeric(keyword) && StringUtils.isNumeric(packetBelong)) {
                    Integer goodsID = Integer.valueOf(keyword);
                    Integer packetID = Integer.valueOf(packetBelong);
                    if (StringUtils.isNumeric(repositoryBelong)) {
                        Integer repositoryID = Integer.valueOf(repositoryBelong);
                        queryResult = packetStorageManageService.selectByGoodsID(goodsID, packetID, repositoryID, offset, limit);
                    }else
                        queryResult = packetStorageManageService.selectByGoodsID(goodsID, packetID, null, offset, limit);
                }
                break;
            case SEARCH_ALL:
//                queryResult = packetStorageManageService.selectAll();
                break;
            default:
                break;
        }
        return queryResult;
    }

    /**
     * 指定包裹信息对预报库存查询
     *
     * @param keyword          查询关键字
     * @param searchType       查询类型
     * @param packetBelong
     * @param repositoryBelong 查询所属的仓库
     * @param offset           分页偏移值
     * @param limit            分页大小
     * @return 结果的一个Map，其中： key为 rows 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "getStorageListWithPacket", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> getStorageListWithPacket(@RequestParam("keyword") String keyword,@RequestParam("searchType") String searchType,
                                                 @RequestParam("packetBelong") String packetBelong, @RequestParam("repositoryBelong") String repositoryBelong,
                                                 @RequestParam("offset") int offset, @RequestParam("limit") int limit) throws PacketStorageManageServiceException {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();
        List<PacketStorage> rows;
        long total = 0;

        // query
        Map<String, Object> queryResult = query(searchType, keyword, packetBelong, repositoryBelong, offset, limit);
        if (queryResult != null) {
            rows = (List<PacketStorage>) queryResult.get("data");
            total = (long) queryResult.get("total");
        } else
            rows = new ArrayList<>();

        // 设置 Response
        responseContent.setCustomerInfo("rows", rows);
        responseContent.setResponseTotal(total);
        return responseContent.generateResponse();
    }

    /**
     * 指定包裹信息对预报库存查询
     *
     * @param keyword          查询关键字
     * @param searchType       查询类型
     * @param packetStatus
     * @param repositoryBelong 查询所属的仓库
     * @param offset           分页偏移值
     * @param limit            分页大小
     * @return 结果的一个Map，其中： key为 rows 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "getStorageListWithStatus", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> getStorageListWithStatus(@RequestParam("keyword") String keyword,@RequestParam("searchType") String searchType,
                                                 @RequestParam("packetStatus") String packetStatus, @RequestParam("repositoryBelong") String repositoryBelong,
                                                 @RequestParam("offset") int offset, @RequestParam("limit") int limit) throws PacketStorageManageServiceException {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();
        List<PacketStorage> rows;
        long total = 0;

        // query
        Map<String, Object> queryResult = query(searchType, keyword, packetStatus, repositoryBelong, offset, limit);
        if (queryResult != null) {
            rows = (List<PacketStorage>) queryResult.get("data");
            total = (long) queryResult.get("total");
        } else
            rows = new ArrayList<>();

        // 设置 Response
        responseContent.setCustomerInfo("rows", rows);
        responseContent.setResponseTotal(total);
        return responseContent.generateResponse();
    }



}
