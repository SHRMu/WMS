package de.demarks.wms.common.controller;

import de.demarks.wms.common.service.Interface.RepositoryBatchManageService;
import de.demarks.wms.common.util.Response;
import de.demarks.wms.common.util.ResponseUtil;
import de.demarks.wms.common.util.StatusUtil;
import de.demarks.wms.domain.RepositoryBatch;
import de.demarks.wms.exception.RepositoryBatchManageServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping(value = "/**/repositoryBatchManage")
public class RepositoryBatchManageHandler {

    @Autowired
    private RepositoryBatchManageService repositoryBatchManageService;
    @Autowired
    private ResponseUtil responseUtil;

    private static final String SEARCH_ALL = "searchAll";
    private static final String SEARCH_BY_ID = "searchByID";
    private static final String SEARCH_BY_CODE = "searchByCode";
    private static final String SEARCH_BY_ACTIVE = "searchByActive";

    /**
     * 通用的记录查询
     *
     * @param searchType 查询方式
     * @param keyword    查询关键字
     * @param offset     分页偏移值
     * @param limit      分页大小
     * @return 返回所有符合条件的查询结果
     */
    private Map<String, Object> query(String searchType, String keyword, int offset, int limit) throws RepositoryBatchManageServiceException {
        Map<String, Object> queryResult = null;

        switch (searchType) {
            case SEARCH_ALL:
                queryResult = repositoryBatchManageService.selectAll(offset, limit);
                break;
            case SEARCH_BY_ID:
                if (StringUtils.isNumeric(keyword)) {
                    queryResult = repositoryBatchManageService.selectById(Integer.valueOf(keyword));
                }
                break;
            case SEARCH_BY_CODE:
                queryResult = repositoryBatchManageService.selectByCode(keyword, offset, limit);
                break;
            case SEARCH_BY_ACTIVE:
                queryResult = repositoryBatchManageService.selectByStatus(StatusUtil.BATCH_STATUS_AVAILABLE, offset, limit);
            default:
                // do other thing
                break;
        }

        return queryResult;
    }

    /**
     * 查询批次信息
     *
     * @param searchType 查询类型
     * @param offset     分页偏移值
     * @param limit      分页大小
     * @param keyWord    查询关键字
     * @return 返回一个Map，其中key=rows，表示查询出来的记录；key=total，表示记录的总条数
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "getRepositoryBatchList", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> getRepositoryBatchList(@RequestParam("searchType") String searchType,
                                          @RequestParam("offset") int offset, @RequestParam("limit") int limit,
                                          @RequestParam("keyWord") String keyWord) throws RepositoryBatchManageServiceException {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();

        List<RepositoryBatch> rows = null;
        long total = 0;

        // 查询
        Map<String, Object> queryResult = query(searchType, keyWord, offset, limit);

        if (queryResult != null) {
            rows = (List<RepositoryBatch>) queryResult.get("data");
            total = (long) queryResult.get("total");
        }

        // 设置 Response
        responseContent.setCustomerInfo("rows", rows);
        responseContent.setResponseTotal(total);
        return responseContent.generateResponse();
    }


    /**
     * 添加一条批次信息
     *
     * @param repositoryBatch 批次信息
     * @return 返回一个map，其中：key 为 result表示操作的结果，包括：success 与 error
     */
    @RequestMapping(value = "addRepositoryBatch", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> addRepositoryBatch(@RequestBody RepositoryBatch repositoryBatch) throws RepositoryBatchManageServiceException {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();

        // 添加记录
        String result = repositoryBatchManageService.addRepositoryBatch(repositoryBatch) ? Response.RESPONSE_RESULT_SUCCESS : Response.RESPONSE_RESULT_ERROR;

        // 设置 Response
        responseContent.setResponseResult(result);
        return responseContent.generateResponse();
    }

    /**
     * 更新批次信息
     *
     * @param repositoryBatch 批次信息
     * @return 返回一个map，其中：key 为 result 的值为操作的结果，包括：success 与 error；key 为 data
     * 的值为仓库信息
     */
    @RequestMapping(value = "updateRepositoryBatch", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> updateRepositoryBatch(@RequestBody RepositoryBatch repositoryBatch) throws RepositoryBatchManageServiceException {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();

        // 更新
        String result = repositoryBatchManageService.updateRepositoryBatch(repositoryBatch) ? Response.RESPONSE_RESULT_SUCCESS : Response.RESPONSE_RESULT_ERROR;

        // 设置 Response
        responseContent.setResponseResult(result);
        return responseContent.generateResponse();
    }

    /**
     * 删除指定 batchID 和 repositoryID 的批次信息
     *
     * @param batchID      批次ID
     * @param repositoryID 仓库ID
     * @return 返回一个map，其中：key 为 result 的值为操作的结果，包括：success 与 error；key 为 data
     * 的值为仓库信息
     */
    @RequestMapping(value = "deleteRepositoryBatch", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> deleteRepositoryBatch(@RequestParam("batchID") Integer batchID,
                                              @RequestParam("repositoryID") Integer repositoryID) throws RepositoryBatchManageServiceException {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();

        // 删除记录
        String result = repositoryBatchManageService.deleteRepositoryBatch(batchID, repositoryID) ? Response.RESPONSE_RESULT_SUCCESS : Response.RESPONSE_RESULT_ERROR;

        // 设置 Response
        responseContent.setResponseResult(result);
        return responseContent.generateResponse();
    }

}
