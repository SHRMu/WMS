package de.demarks.wms.common.controller;

import de.demarks.wms.common.service.Interface.PacketManageService;
import de.demarks.wms.common.service.Interface.PacketStorageManageService;
import de.demarks.wms.common.util.Response;
import de.demarks.wms.common.util.ResponseUtil;
import de.demarks.wms.dao.PacketMapper;
import de.demarks.wms.domain.Packet;
import de.demarks.wms.exception.PacketManageServiceException;
import de.demarks.wms.exception.PreStockManageServiceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.tools.ant.taskdefs.Pack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import sun.security.krb5.internal.PAData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
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

    private static final String SEARCH_BY_ID = "searchByID";
    private static final String SEARCH_BY_TRACE = "searchByTrace";
    private static final String SEARCH_ACTIVE_BY_TRACE = "searchActiveByTrace";
    private static final String SEARCH_ALL_ACTIVE = "searchAllActive";
    private static final String SEARCH_ALL = "searchAll";

    /**
     * 通用的记录查询
     * @param searchType
     * @param keyWord
     * @param offset
     * @param limit
     * @return
     * @throws PacketManageServiceException
     */
    private Map<String, Object> query(@Param("searchType") String searchType,
                                      @Param("keyWord") String keyWord,
                                      @Param("offset") int offset,
                                      @Param("limit") int limit) throws PacketManageServiceException {
        Map<String, Object> queryResult = null;

        switch (searchType) {
            case SEARCH_BY_ID:
                if (StringUtils.isNumeric(keyWord))
                    queryResult = packetManageService.selectByPacketID(Integer.valueOf(keyWord));
                break;
            case SEARCH_ACTIVE_BY_TRACE:
                queryResult = packetManageService.selectByTraceApproximate(keyWord, "已发货", null, offset, limit);
                break;
            case SEARCH_BY_TRACE:
                queryResult = packetManageService.selectByTraceApproximate(keyWord,null, null, offset, limit);
                break;
            case SEARCH_ALL_ACTIVE:
                queryResult = packetManageService.selectAll(null, offset, limit);
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

    /**
     * 更新一条包裹信息
     *
     * @param packet 包裹信息
     * @return 返回一个map，其中：key 为 result表示操作的结果，包括：success 与 error
     */
    @RequestMapping(value = "updatePacket", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> updatePacket(@RequestBody Packet packet) throws PacketManageServiceException {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();

        String result = packetManageService.updatePacket(packet) ? Response.RESPONSE_RESULT_SUCCESS : Response.RESPONSE_RESULT_ERROR;

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


    private Integer packetSingleStockIn(String packetTrace, Integer repositoryID) throws PacketManageServiceException{
        //精确查询packetTrace是否存在
        Packet packet = packetMapper.selectByTrace(packetTrace);
        Integer packetID = null;
        if ( packet != null){
            packetID = packet.getId();
        }else {
            //如果不存在输入的packetTrace，首先创建Packet
            packet = new Packet();
            packet.setTrace(packetTrace);
            packet.setStatus("已发货");
            packet.setRepositoryID(repositoryID);
            packet.setTime(new Date());
            boolean isSuccess = packetManageService.addPacket(packet);
            if (isSuccess)
                packetID = packet.getId();
        }
        return packetID;
    }

    /**
     *  客户预报操作
     * @param trace
     * @param desc
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
    Map<String, Object> preStockIn(@RequestParam("trace") String trace,
                                   @RequestParam("goodsID") Integer goodsID,
                                   @RequestParam("repositoryID") Integer repositoryID,
                                   @Param("desc") String desc,
                                   @RequestParam("number") long number, HttpServletRequest request) throws PacketManageServiceException, PreStockManageServiceException {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();
        HttpSession session = request.getSession();
        String personInCharge = (String) session.getAttribute("userName"); //默认获取的userName取锁定客户ID

        Packet packet = packetMapper.selectByTrace(trace);
        Integer packetID;
        if (packet != null){
            //如果包裹已有预报信息
            packetID = packet.getId();
        }else{
            //首先预报包裹信息
            packet = new Packet();
            packet.setTrace(trace);
            packet.setStatus("已发货");
            packet.setDesc(desc);
            packet.setRepositoryID(repositoryID);
            //TODO : addPacket怎样能够直接返回id值
            packetManageService.addPacket(packet);
            packetID = packetMapper.selectByTrace(trace).getId();
        }

        //执行包裹预报入库操作
        String result = packetManageService.packetStockInOperation(packetID, goodsID, repositoryID, number, personInCharge) ?
                Response.RESPONSE_RESULT_SUCCESS : Response.RESPONSE_RESULT_ERROR;

        // 设置 Response
        responseContent.setResponseResult(result);
        return responseContent.generateResponse();

    }

}
