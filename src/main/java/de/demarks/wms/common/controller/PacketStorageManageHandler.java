package de.demarks.wms.common.controller;
import com.sun.org.apache.bcel.internal.generic.BREAKPOINT;
import de.demarks.wms.common.service.Interface.PacketManageService;
import de.demarks.wms.common.service.Interface.PacketStorageManageService;
import de.demarks.wms.common.util.Response;
import de.demarks.wms.common.util.ResponseUtil;
import de.demarks.wms.common.util.StatusUtil;
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

    private static final String SEARCH_BY_GOODID = "searchByGoodsID";
    private static final String SEARCH_BY_GOODNAME = "searchByGoodsName";
    private static final String SEARCH_ALL = "searchAll";
    private static final String SEARCH_BY_PACKETID = "searchByPacketID";
    private static final String SEARCH_BY_TRACE = "searchByTrace";

    /**
     *  查询预报包裹库存信息
     *
     * @param searchType       查询类型
     * @param keyword          查询关键字
     * @param repositoryID     查询仓库
     * @param offset           分页偏移值
     * @param limit            分页大小
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    private Map<String, Object> query(String searchType, String keyword, String packetInfo, Integer repositoryID,
                                      int offset, int limit) throws PacketStorageManageServiceException {

        Map<String, Object> queryResult = null;

        switch (searchType) {
            case SEARCH_BY_GOODID:
                if (StringUtils.isNumeric(keyword)){
                    Integer goodsID = Integer.valueOf(keyword);
                    if (StringUtils.isNumeric(packetInfo)){
                        Integer packetID = Integer.valueOf(packetInfo);
                        queryResult = packetStorageManageService.selectByGoodsID(goodsID, packetID, repositoryID);
                    }else
                        queryResult = packetStorageManageService.selectByGoodsID(goodsID, -1, repositoryID);
                }
                break;
            case SEARCH_BY_GOODNAME:
                if (StringUtils.isNumeric(packetInfo)){
                    Integer packetID = Integer.valueOf(packetInfo);
                    queryResult = packetStorageManageService.selectByGoodsName(keyword, packetID, repositoryID);
                }else
                    queryResult = packetStorageManageService.selectByGoodsName(keyword, -1, repositoryID);
                break;
            case SEARCH_ALL:
                if (StringUtils.isNumeric(packetInfo)){
                    Integer packetID = Integer.valueOf(packetInfo);
                    queryResult = packetStorageManageService.selectAll(packetID, repositoryID);
                }else
                    queryResult = packetStorageManageService.selectAll(-1, repositoryID);
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
     * @param repositoryID     查询所属的仓库
     * @param offset           分页偏移值
     * @param limit            分页大小
     * @return 结果的一个Map，其中： key为 rows 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "getStorageList", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> getStorageListWithPacket(@RequestParam("searchType") String searchType, @RequestParam("keyword") String keyword,
                                                 @RequestParam("packetID") String packetID, @RequestParam("repositoryID") Integer repositoryID,
                                                 @RequestParam("offset") int offset, @RequestParam("limit") int limit) throws PacketStorageManageServiceException {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();
        List<PacketStorage> rows;
        long total = 0;

        // query
        Map<String, Object> queryResult = query(searchType, keyword, packetID, repositoryID, offset, limit);
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
