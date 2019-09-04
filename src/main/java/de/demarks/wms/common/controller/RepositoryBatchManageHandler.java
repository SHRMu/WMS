package de.demarks.wms.common.controller;

import de.demarks.wms.common.service.Interface.RepositoryBatchManageService;
import de.demarks.wms.common.util.Response;
import de.demarks.wms.common.util.ResponseUtil;
import de.demarks.wms.domain.RepositoryBatch;
import de.demarks.wms.exception.RepositoryBatchManageServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping(value = "/**/repositoryBatchManage")
public class RepositoryBatchManageHandler {

    @Autowired
    private ResponseUtil responseUtil;

    @Autowired
    private RepositoryBatchManageService repositoryBatchManageService;

    /**
     * 添加一条仓库批次信息
     *
     * @param repositoryBatch 仓库批次信息
     * @return 返回一个map，其中：key 为 result表示操作的结果，包括：success 与 error
     */
    @RequestMapping(value = "addRepositoryBatch", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> addRepositoryBatch(@RequestBody RepositoryBatch repositoryBatch) throws RepositoryBatchManageServiceException {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();

        // 添加结果
        String result = repositoryBatchManageService.addRepositoryBatch(repositoryBatch)
                ? Response.RESPONSE_RESULT_SUCCESS : Response.RESPONSE_RESULT_ERROR;

        // 设置 Response
        responseContent.setResponseResult(result);
        return responseContent.generateResponse();
    }

}
