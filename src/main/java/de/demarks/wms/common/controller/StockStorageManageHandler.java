package de.demarks.wms.common.controller;

import de.demarks.wms.common.service.Interface.StockRecordManageService;
import de.demarks.wms.common.service.Interface.StockStorageManageService;
import de.demarks.wms.common.util.Response;
import de.demarks.wms.common.util.ResponseUtil;
import de.demarks.wms.domain.StockStorage;
import de.demarks.wms.exception.StorageManageServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 库存管理请求处理
 *
 * @author huanyingcool
 */
@Controller
@RequestMapping(value = "/**/storageManage")
public class StockStorageManageHandler {

    @Autowired
    private StockStorageManageService stockStorageManageService;
    @Autowired
    private ResponseUtil responseUtil;


    private static final String SEARCH_BY_GOODS_NAME = "searchByGoodsName";
    private static final String SEARCH_BY_GOODS_ID = "searchByGoodsID";
    private static final String SEARCH_ALL = "searchAll";

    /**
     * 查询库存信息
     *
     * @param searchType       查询类型
     * @param keyword          查询关键字
     * @param batchBelong      查询批次
     * @param repositoryID     查询仓库
     * @param offset           分页偏移值
     * @param limit            分页大小
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    private Map<String, Object> query(String searchType, String keyword, String batchBelong, Integer repositoryID,
                                      int offset, int limit) throws StorageManageServiceException {
        Map<String, Object> queryResult = null;

        switch (searchType) {
            case SEARCH_ALL:
                if (StringUtils.isNumeric(batchBelong)) {
                    Integer batchID = Integer.valueOf(batchBelong);
                    queryResult = stockStorageManageService.selectAll(batchID, repositoryID, offset, limit);
                } else
                    queryResult = stockStorageManageService.selectAll(-1, repositoryID, offset, limit);
                break;
            case SEARCH_BY_GOODS_ID:
                if (StringUtils.isNumeric(keyword)) {
                    Integer goodsID = Integer.valueOf(keyword);
                    if (StringUtils.isNumeric(batchBelong)) {
                        Integer batchID = Integer.valueOf(batchBelong);
                        queryResult = stockStorageManageService.selectByGoodsID(goodsID, batchID, repositoryID, offset, limit);
                    } else
                        queryResult = stockStorageManageService.selectByGoodsID(goodsID, -1, repositoryID, offset, limit);
                }
                break;
            case SEARCH_BY_GOODS_NAME:
                if (StringUtils.isNumeric(batchBelong)){
                    Integer batchID = Integer.valueOf(batchBelong);
                    queryResult = stockStorageManageService.selectByGoodsName(keyword, batchID, repositoryID, offset, limit);
                }else
                    queryResult = stockStorageManageService.selectByGoodsName(keyword,-1, repositoryID, offset, limit);
                break;
            default:
                // do other thing
                break;
        }

        return queryResult;
    }

    /**
     * 可指定仓库对库存信息查询
     *
     * @param keyword          查询关键字
     * @param searchType       查询类型
     * @param batchBelong      查询所属的批次
     * @param repositoryID     查询所属的仓库
     * @param offset           分页偏移值
     * @param limit            分页大小
     * @return 结果的一个Map，其中： key为 rows 的代表记录数据；key 为 total 代表结果记录的数量
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "getStorageList", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> getStorageListWithRepoID(@RequestParam("keyword") String keyword,@RequestParam("searchType") String searchType,
                                                 @RequestParam("batchBelong") String batchBelong, @RequestParam("repositoryID") Integer repositoryID,
                                                 @RequestParam("offset") int offset, @RequestParam("limit") int limit) throws StorageManageServiceException {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();

        List<StockStorage> rows;
        long total = 0;

        // query
        Map<String, Object> queryResult = query(searchType, keyword, batchBelong, repositoryID, offset, limit);
        if (queryResult != null) {
            rows = (List<StockStorage>) queryResult.get("data");
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
    Map<String, Object> addStorageRecord(@RequestBody Map<String, Object> params) throws StorageManageServiceException {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();
        String isSuccess = Response.RESPONSE_RESULT_ERROR;
        boolean isAvailable = true;

        String goodsID = (String) params.get("goodsID");
        String batchID = (String) params.get("batchID");
        String repositoryID = (String) params.get("repositoryID");
        String number = (String) params.get("number");

        if (StringUtils.isBlank(goodsID) || !StringUtils.isNumeric(goodsID))
            isAvailable = false;
        if (StringUtils.isBlank(batchID) || !StringUtils.isNumeric(batchID))
            isAvailable = false;
        if (StringUtils.isBlank(repositoryID) || !StringUtils.isNumeric(repositoryID))
            isAvailable = false;
        if (StringUtils.isBlank(number) || !StringUtils.isNumeric(number))
            isAvailable = false;

        if (isAvailable) {
            isSuccess = stockStorageManageService.addStorage(Integer.valueOf(goodsID), Integer.valueOf(batchID), Integer.valueOf(repositoryID),
                    Integer.valueOf(number)) ? Response.RESPONSE_RESULT_SUCCESS : Response.RESPONSE_RESULT_ERROR;
        }

        // 设置 Response
        responseContent.setResponseResult(isSuccess);
        return responseContent.generateResponse();
    }

    /**
     * 更新库存信息
     *
     * @return 返回一个map，其中：key 为 result表示操作的结果，包括：success 与 error
     */
    @RequestMapping(value = "updateStorageRecord", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> updateStorageRecord(@RequestBody Map<String, Object> params) throws StorageManageServiceException {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();
        boolean isAvailable = true;
        String result = Response.RESPONSE_RESULT_ERROR;

        String goodsID = (String) params.get("goodsID");
        String batchID = (String) params.get("batchID");
        String repositoryID = (String) params.get("repositoryID");
        String number = (String) params.get("number");

        if (StringUtils.isBlank(goodsID) || !StringUtils.isNumeric(goodsID))
            isAvailable = false;
        if (StringUtils.isBlank(batchID) || !StringUtils.isNumeric(batchID))
            isAvailable = false;
        if (StringUtils.isBlank(repositoryID) || !StringUtils.isNumeric(repositoryID))
            isAvailable = false;
        if (StringUtils.isBlank(number) || !StringUtils.isNumeric(number))
            isAvailable = false;

        if (isAvailable) {
            result = stockStorageManageService.updateStorage(Integer.valueOf(goodsID), Integer.valueOf(batchID), Integer.valueOf(repositoryID),
                    Integer.valueOf(number)) ? Response.RESPONSE_RESULT_SUCCESS : Response.RESPONSE_RESULT_ERROR;
        }

        // 设置 Response
        responseContent.setResponseResult(result);
        return responseContent.generateResponse();
    }

    /**
     * 删除一条库存信息
     *
     * @param goodsID      货物ID
     * @param batchID      批次ID
     * @param repositoryID 仓库ID
     * @return 返回一个map，其中：key 为 result表示操作的结果，包括：success 与 error
     */
    @RequestMapping(value = "deleteStorageRecord", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> deleteStorageRecord(@RequestParam("goodsID") String goodsID,
                                            @RequestParam("batchID") String batchID,
                                            @RequestParam("repositoryID") String repositoryID) throws StorageManageServiceException {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();

        String result = Response.RESPONSE_RESULT_ERROR;
        boolean isAvailable = true;

        if (StringUtils.isBlank(goodsID) || !StringUtils.isNumeric(goodsID))
            isAvailable = false;
        if (StringUtils.isBlank(batchID) || !StringUtils.isNumeric(batchID))
            isAvailable = false;
        if (StringUtils.isBlank(repositoryID) || !StringUtils.isNumeric(repositoryID))
            isAvailable = false;

        if (isAvailable) {
            result = stockStorageManageService.deleteStorage(Integer.valueOf(goodsID), Integer.valueOf(batchID), Integer.valueOf(repositoryID))
                    ? Response.RESPONSE_RESULT_SUCCESS : Response.RESPONSE_RESULT_ERROR;
        }

        // 设置 Response
        responseContent.setResponseResult(result);
        return responseContent.generateResponse();
    }

//    /**
//     * 导入库存信息
//     *
//     * @param file 保存有库存信息的文件
//     * @return 返回一个map，其中：key 为 result表示操作的结果，包括：success 与
//     * error；key为total表示导入的总条数；key为available表示有效的条数
//     */
//    @RequestMapping(value = "importStorageRecord", method = RequestMethod.POST)
//    public
//    @ResponseBody
//    Map<String, Object> importStorageRecord(@RequestParam("file") MultipartFile file) throws StorageManageServiceException {
//        // 初始化 Response
//        Response responseContent = responseUtil.newResponseInstance();
//        String result = Response.RESPONSE_RESULT_ERROR;
//
//        int total = 0;
//        int available = 0;
//
//        if (file != null) {
//            Map<String, Object> importInfo = storageManageService.importStorage(file);
//            if (importInfo != null) {
//                total = (int) importInfo.get("total");
//                available = (int) importInfo.get("available");
//                result = Response.RESPONSE_RESULT_SUCCESS;
//            }
//        }
//
//        // 设置 Response
//        responseContent.setResponseResult(result);
//        responseContent.setResponseTotal(total);
//        responseContent.setCustomerInfo("available", available);
//        return responseContent.generateResponse();
//    }

//    /**
//     * 导出库存信息
//     *
//     * @param searchType       查询类型
//     * @param keyword          查询关键字
//     * @param batchBelong      查询所属批次
//     * @param repositoryBelong 查询所属仓库
//     * @param request          请求
//     * @param response         响应
//     */
//    @SuppressWarnings("unchecked")
//    @RequestMapping(value = "exportStorageRecord", method = RequestMethod.GET)
//    public void exportStorageRecord(@RequestParam("searchType") String searchType,
//                                    @RequestParam("keyword") String keyword,
//                                    @RequestParam(value = "batchBelong", required = false) String batchBelong,
//                                    @RequestParam(value = "repositoryBelong", required = false) String repositoryBelong,
//                                    HttpServletRequest request, HttpServletResponse response) throws StorageManageServiceException, IOException {
//        String fileName = "storageRecord.xlsx";
//
//        HttpSession session = request.getSession();
//        Integer sessionBatchBelong = (Integer) session.getAttribute("batchBelong");
//        Integer sessionRepositoryBelong = (Integer) session.getAttribute("repositoryBelong");
//        if (sessionBatchBelong != null && !sessionBatchBelong.equals("none"))
//            batchBelong = sessionBatchBelong.toString();
//        if (sessionRepositoryBelong != null && !sessionRepositoryBelong.equals("none"))
//            repositoryBelong = sessionRepositoryBelong.toString();
//
//        List<Storage> storageList = null;
//        Map<String, Object> queryResult = query(searchType, keyword, batchBelong, repositoryBelong, -1, -1);
//        if (queryResult != null)
//            storageList = (List<Storage>) queryResult.get("data");
//
//        File file = storageManageService.exportStorage(storageList);
//        if (file != null) {
//            // 设置响应头
//            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
//            FileInputStream inputStream = new FileInputStream(file);
//            OutputStream outputStream = response.getOutputStream();
//            byte[] buffer = new byte[8192];
//
//            int len;
//            while ((len = inputStream.read(buffer, 0, buffer.length)) > 0) {
//                outputStream.write(buffer, 0, len);
//                outputStream.flush();
//            }
//
//            inputStream.close();
//            outputStream.close();
//
//        }
//    }
}
