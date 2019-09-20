package de.demarks.wms.common.controller;
import de.demarks.wms.common.service.Interface.PacketStorageManageService;
import de.demarks.wms.common.util.Response;
import de.demarks.wms.common.util.ResponseUtil;
import de.demarks.wms.common.util.StatusUtil;
import de.demarks.wms.domain.PacketStorage;
import de.demarks.wms.exception.PacketStorageManageServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 添加一条库存信息
     *
     * @return 返回一个map，其中：key 为 result表示操作的结果，包括：success 与 error
     */
    @RequestMapping(value = "addStorageRecord", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> addStorageRecord(@RequestBody Map<String, Object> params) throws PacketStorageManageServiceException {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();
        String isSuccess = Response.RESPONSE_RESULT_ERROR;
        boolean isAvailable = true;

        String packetID = (String) params.get("packetID");
        String goodsID = (String) params.get("goodsID");
        String repositoryID = (String) params.get("repositoryID");
        String number = (String) params.get("number");
        String storage = (String) params.get("storage");

        if (StringUtils.isBlank(packetID)||!StringUtils.isNumeric(packetID))
            isAvailable = false;
        if (StringUtils.isBlank(goodsID) || !StringUtils.isNumeric(goodsID))
            isAvailable = false;
        if (StringUtils.isBlank(repositoryID) || !StringUtils.isNumeric(repositoryID))
            isAvailable = false;
        if (StringUtils.isBlank(number) || !StringUtils.isNumeric(number))
            isAvailable = false;
        if (StringUtils.isBlank(storage) || !StringUtils.isNumeric(storage))
            isAvailable = false;
        if (isAvailable) {
            isSuccess = packetStorageManageService.addStorage(Integer.valueOf(packetID), Integer.valueOf(goodsID), Integer.valueOf(repositoryID),
                    Integer.valueOf(number), Integer.valueOf(storage)) ? Response.RESPONSE_RESULT_SUCCESS : Response.RESPONSE_RESULT_ERROR;
        }

        // 设置 Response
        responseContent.setResponseResult(isSuccess);
        return responseContent.generateResponse();
    }


}
