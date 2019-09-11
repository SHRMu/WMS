package de.demarks.wms.common.controller;

import de.demarks.wms.common.service.Interface.PacketManageService;
import de.demarks.wms.common.util.Response;
import de.demarks.wms.common.util.ResponseUtil;
import de.demarks.wms.domain.Packet;
import de.demarks.wms.exception.PacketManageServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.security.krb5.internal.PAData;

import java.util.List;
import java.util.Map;

/**
 * 包裹信息管理请求 Handler
 *
 * @author huanyingcool
 *
 */
@RequestMapping(value = "/**/packetManage")
@Controller
public class PacketMangeHandler {

    @Autowired
    private ResponseUtil responseUtil;
    @Autowired
    private PacketManageService packetManageService;

    private static final String SEARCH_BY_ID = "searchByID";
    private static final String SEARCH_BY_TRACE = "searchByTrace";
    private static final String SEARCH_ALL_ACTIVE = "searchAllActive";

    /**
     * 通用的记录查询
     *
     * @param searchType 查询类型
     * @param keyWord    查询关键字
     * @param offset     分页偏移值
     * @param limit      分页大小
     * @return 返回一个 Map ，包含所有符合要求的查询结果，以及记录的条数
     */
    private Map<String, Object> query(String searchType, String keyWord, int offset, int limit) throws PacketManageServiceException {
        Map<String, Object> queryResult = null;

        switch (searchType) {
            case SEARCH_BY_ID:
                if (StringUtils.isNumeric(keyWord))
                    queryResult = packetManageService.selectByID(Integer.valueOf(keyWord));
                break;
            case SEARCH_BY_TRACE:
                queryResult = packetManageService.selectByTraceApproximate(keyWord, offset, limit);
                break;
            case SEARCH_ALL_ACTIVE:
                queryResult = packetManageService.selectAll("已发货", offset, limit);
                break;
            default:
                // do other thing
                break;
        }
        return queryResult;
    }


    /**
     * 搜索匹配未到货的包裹信息
     *
     * @param searchType 搜索类型
     * @param offset     如有多条记录时分页的偏移值
     * @param limit      如有多条记录时分页的大小
     * @param keyWord    搜索的关键字
     * @return 返回所有符合要求的记录
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "getPacketList", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> getPacketList(@RequestParam("searchType") String searchType,
                                     @RequestParam("offset") int offset, @RequestParam("limit") int limit,
                                     @RequestParam("keyWord") String keyWord) throws PacketManageServiceException {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();
        List<Packet> rows = null;
        long total = 0;

        // 查询
        Map<String, Object> queryResult = query(searchType, keyWord, offset, limit);

        if (queryResult != null) {
            rows = (List<Packet>) queryResult.get("data");
            total = (long) queryResult.get("total");
        }

        // 设置 Response
        responseContent.setCustomerInfo("rows", rows);
        responseContent.setResponseTotal(total);
        return responseContent.generateResponse();
    }

    /**
     * 添加一条包裹信息
     *
     * @param packet 包裹信息
     * @return 返回一个map，其中：key 为 result表示操作的结果，包括：success 与 error
     */
    @RequestMapping(value = "addPacket", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> addPacket(@RequestBody Packet packet) throws PacketManageServiceException {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();

        // 设置包裹已发货
        packet.setStatus("已发货");
        String result = packetManageService.addPacket(packet) ? Response.RESPONSE_RESULT_SUCCESS : Response.RESPONSE_RESULT_ERROR;

        // 设置 Response
        responseContent.setResponseResult(result);
        return responseContent.generateResponse();
    }

}
