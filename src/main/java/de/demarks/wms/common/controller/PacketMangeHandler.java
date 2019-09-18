package de.demarks.wms.common.controller;

import de.demarks.wms.common.service.Interface.PacketManageService;
import de.demarks.wms.common.service.Interface.PacketRefMangeService;
import de.demarks.wms.common.util.Response;
import de.demarks.wms.common.util.ResponseUtil;
import de.demarks.wms.common.util.StatusUtil;
import de.demarks.wms.dao.PacketMapper;
import de.demarks.wms.domain.PacketDO;
import de.demarks.wms.domain.PacketDTO;
import de.demarks.wms.exception.PacketManageServiceException;
import de.demarks.wms.exception.PreStockManageServiceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 包裹信息管理请求 Handler
 *
 * @author huanyingcool
 *
 */
@RequestMapping(value = "packetManage")
@Controller
public class PacketMangeHandler {

    @Autowired
    private ResponseUtil responseUtil;
    @Autowired
    private PacketMapper packetMapper;
    @Autowired
    private PacketManageService packetManageService;
    @Autowired
    private PacketRefMangeService packetRefMangeService;

    private static final String SEARCH_BY_PACKET_ID = "searchByPacketID";
    private static final String SEARCH_BY_TRACE = "searchByTrace";
    private static final String SEARCH_ACTIVE = "searchActive";
    private static final String SEARCH_ALL= "searchAll";

    private static final String SEARCH_REF_ACTIVE = "searchRefActive";

    /**
     * 通用的记录查询
     * @param searchType
     * @param keyWord
     * @param offset
     * @param limit
     * @return
     * @throws PacketManageServiceException
     */
    private Map<String, Object> query(@Param("searchType") String searchType, @Param("keyWord") String keyWord, @Param("repositoryID") Integer repositoryID,
                                      @Param("offset") int offset, @Param("limit") int limit) throws PacketManageServiceException {

        Map<String, Object> queryResult = null;

        switch (searchType) {
            case SEARCH_BY_PACKET_ID:
                if (StringUtils.isNumeric(keyWord))
                    queryResult = packetManageService.selectByPacketID(Integer.valueOf(keyWord));
                break;
            case SEARCH_BY_TRACE:
                queryResult = packetManageService.selectByTraceApproximate(keyWord, "", repositoryID, offset, limit);
                break;
            case SEARCH_ACTIVE:
                queryResult = packetManageService.selectByTraceApproximate("",StatusUtil.PACKET_STATUS_SEND,repositoryID);
                break;
            case SEARCH_ALL:
                queryResult = packetManageService.selectAll(repositoryID, offset, limit);
                break;
            case SEARCH_REF_ACTIVE:
                queryResult = packetRefMangeService.selectRefApproximate(keyWord,StatusUtil.PACKET_STATUS_SEND,repositoryID);
                break;
            default:
                // do other thing
                break;
        }
        return queryResult;
    }

    /**
     * 搜索匹配的包裹信息
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
    Map<String, Object> getPacketList(@RequestParam("searchType") String searchType, @RequestParam("keyWord") String keyWord, @Param("repositoryID") Integer repositoryID,
                                      @RequestParam("offset") int offset, @RequestParam("limit") int limit) throws PacketManageServiceException {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();
        List<PacketDO> rows = null;
        long total = 0;

        if (repositoryID == null)
            repositoryID = -1;

        // 查询
        Map<String, Object> queryResult = query(searchType, keyWord, repositoryID, offset, limit);

        if (queryResult != null) {
            rows = (List<PacketDO>) queryResult.get("data");
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
     * @param packetDO 包裹信息
     * @return 返回一个map，其中：key 为 result表示操作的结果，包括：success 与 error
     */
    @RequestMapping(value = "addPacket", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> addPacket(@RequestBody PacketDO packetDO) throws PacketManageServiceException {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();

        // 设置包裹已发货
        packetDO.setStatus("已发货");
        String result = packetManageService.addPacket(packetDO) ? Response.RESPONSE_RESULT_SUCCESS : Response.RESPONSE_RESULT_ERROR;

        // 设置 Response
        responseContent.setResponseResult(result);
        return responseContent.generateResponse();
    }

    /**
     * 更新一条包裹信息
     *
     * @param packetDO 包裹信息
     * @return 返回一个map，其中：key 为 result表示操作的结果，包括：success 与 error
     */
    @RequestMapping(value = "updatePacket", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> updatePacket(@RequestBody PacketDO packetDO) throws PacketManageServiceException {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();

        String result = packetManageService.updatePacket(packetDO) ? Response.RESPONSE_RESULT_SUCCESS : Response.RESPONSE_RESULT_ERROR;

        // 设置 Response
        responseContent.setResponseResult(result);
        return responseContent.generateResponse();
    }

    /**
     * 删除一条包裹信息
     *
     * @param packetID 包裹信息
     * @return 返回一个map，其中：key 为 result表示操作的结果，包括：success 与 error
     */
    @RequestMapping(value = "deletePacket", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> deletePacket(@RequestParam("packetID") Integer packetID) throws PacketManageServiceException {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();

        String result = packetManageService.deletePacket(packetID) ? Response.RESPONSE_RESULT_SUCCESS : Response.RESPONSE_RESULT_ERROR;

        // 设置 Response
        responseContent.setResponseResult(result);
        return responseContent.generateResponse();
    }

    /**
     *  客户预报操作
     * @param trace
     * @param repositoryID
     * @param goodsID
     * @param number
     * @param request
     * @return
     * @throws PacketManageServiceException
     * @throws PreStockManageServiceException
     */
    @RequestMapping(value = "packetStockIn", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> preStockIn(@RequestParam("trace") String trace, @RequestParam("goodsID") Integer goodsID, @RequestParam("repositoryID") Integer repositoryID,
                                   @RequestParam("number") long number, HttpServletRequest request) throws PacketManageServiceException {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();
        HttpSession session = request.getSession();
        String personInCharge = (String) session.getAttribute("userName"); //默认获取的userName取锁定客户ID

        PacketDO packetDO = packetMapper.selectByTrace(trace,repositoryID); //验证是否有过该包裹预报
        Integer packetID;
        if (packetDO != null){
            //如果包裹已有预报信息
            packetID = packetDO.getId();
        }else{
            //首先预报包裹信息
            packetDO = new PacketDO();
            packetDO.setTrace(trace);
            packetDO.setStatus("已发货");
            packetDO.setRepositoryID(repositoryID);
            packetManageService.addPacket(packetDO);
            packetID = packetMapper.selectByTrace(trace,repositoryID).getId();
        }

        //执行包裹预报操作
        String result = packetManageService.packetStockInOperation(packetID, goodsID, repositoryID, number, personInCharge) ?
                Response.RESPONSE_RESULT_SUCCESS : Response.RESPONSE_RESULT_ERROR;

        // 设置 Response
        responseContent.setResponseResult(result);
        return responseContent.generateResponse();

    }

    /**
     *
     * @param packetIDStr
     * @param repositoryID
     * @param startDateStr
     * @param endDateStr
     * @param limit
     * @param offset
     * @return
     * @throws ParseException
     * @throws PacketManageServiceException
     */
    @SuppressWarnings({"SingleStatementInBlock", "unchecked"})
    @RequestMapping(value = "searchPacketRecord", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> searchDetectRecord(@RequestParam("packetID") String packetIDStr, @RequestParam("repositoryID") Integer repositoryID,
                                           @RequestParam("startDate") String startDateStr, @RequestParam("endDate") String endDateStr,
                                           @RequestParam("limit") int limit, @RequestParam("offset") int offset) throws ParseException, PacketManageServiceException {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();
        List<PacketDTO> rows = null;
        long total = 0;

        // 参数检查
        String regex = "([0-9]{4})-([0-9]{2})-([0-9]{2})";
        boolean startDateFormatCheck = (StringUtils.isEmpty(startDateStr) || startDateStr.matches(regex));
        boolean endDateFormatCheck = (StringUtils.isEmpty(endDateStr) || endDateStr.matches(regex));
        boolean packetIDCheck = (StringUtils.isEmpty(packetIDStr) || StringUtils.isNumeric(packetIDStr));

        if (startDateFormatCheck && endDateFormatCheck && packetIDCheck) {
            Integer packetID = -1;
            if (StringUtils.isNumeric(packetIDStr)) {
                packetID = Integer.valueOf(packetIDStr);
            }

            // 转到 Service 执行查询
            Map<String, Object> queryResult = packetManageService.selectPacketRecord(packetID, repositoryID, startDateStr, endDateStr, offset, limit);
            if (queryResult != null) {
                rows = (List<PacketDTO>) queryResult.get("data");
                total = (long) queryResult.get("total");
            }
        } else
            responseContent.setResponseMsg("Request argument error");

        if (rows == null)
            rows = new ArrayList<>(0);

        responseContent.setCustomerInfo("rows", rows);
        responseContent.setResponseTotal(total);
        return responseContent.generateResponse();
    }

}
