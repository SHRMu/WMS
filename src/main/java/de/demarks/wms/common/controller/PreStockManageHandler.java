package de.demarks.wms.common.controller;
import de.demarks.wms.common.service.Interface.PacketManageService;
import de.demarks.wms.common.service.Interface.PacketStorageManageService;
import de.demarks.wms.common.util.Response;
import de.demarks.wms.common.util.ResponseUtil;
import de.demarks.wms.dao.PacketMapper;
import de.demarks.wms.domain.Packet;
import de.demarks.wms.exception.PacketManageServiceException;
import de.demarks.wms.exception.PreStockManageServiceException;
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
 * 客户预报管理请求Handler
 *
 * @author huanyingcool
 */

@Controller
@RequestMapping("preStockManage")
public class PreStockManageHandler {

    @Autowired
    private ResponseUtil responseUtil;
    @Autowired
    private PacketMapper packetMapper;
    @Autowired
    private PacketManageService packetManageService;
    @Autowired
    private PacketStorageManageService packetStorageManageService;

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
     * 客户预报操作
     *
     * @param trace  包裹单号
     * @param desc   附加单号
     * @param goodsID
     * @param repositoryID
     * @param number
     */
    @RequestMapping(value = "preStockIn", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> preStockIn(@RequestParam("trace") String trace, @Param("desc") String desc,
                                    @RequestParam("repositoryID") Integer repositoryID,
                                    @RequestParam("goodsID") Integer goodsID,
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
            //如果该包裹第一次预报
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
        String result = packetStorageManageService.preStockInOperation(packetID, goodsID, repositoryID, number, personInCharge) ?
                Response.RESPONSE_RESULT_SUCCESS : Response.RESPONSE_RESULT_ERROR;

        // 设置 Response
        responseContent.setResponseResult(result);
        return responseContent.generateResponse();

    }


}
