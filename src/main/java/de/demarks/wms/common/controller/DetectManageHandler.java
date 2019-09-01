package de.demarks.wms.common.controller;

import de.demarks.wms.common.service.Interface.DetectManageService;
import de.demarks.wms.common.util.Response;
import de.demarks.wms.common.util.ResponseUtil;
import de.demarks.wms.exception.DetectManageServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping(value = "detectManage")
public class DetectManageHandler {

    @Autowired
    private ResponseUtil responseUtil;

    @Autowired
    private DetectManageService detectManageService;

    /**
     * 货物检测操作
     *
     * @param goodsID      货物ID
     * @param batch        批次号
     * @param repositoryID 入库仓库ID
     * @param passed       良品数量
     * @param scratch      划痕数量
     * @param damage       故障数量
     * @param request      http 请求
     * @return 返回一个map，key为result的值表示操作是否成功
     */
    @RequestMapping(value = "detect", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> stockIn(@RequestParam("goodsID") Integer goodsID, @RequestParam("batch") Integer batch,
                                @RequestParam("repositoryID") Integer repositoryID,
                                @RequestParam("passed") long passed, @RequestParam("scratch") long scratch,
                                @RequestParam("damage") long damage, HttpServletRequest request) throws DetectManageServiceException {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();

        HttpSession session = request.getSession();
        String personInCharge = (String) session.getAttribute("userName");

        String result = detectManageService.detectionOperation(goodsID, batch, repositoryID, passed, scratch, damage, personInCharge) ?
                Response.RESPONSE_RESULT_SUCCESS : Response.RESPONSE_RESULT_ERROR;

        // 设置 Response
        responseContent.setResponseResult(result);
        return responseContent.generateResponse();
    }

}
