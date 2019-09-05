package de.demarks.wms.common.controller;

import de.demarks.wms.common.service.Interface.DetectStorageService;
import de.demarks.wms.common.util.ResponseUtil;
import de.demarks.wms.exception.DetectStorageServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * @author huanyingcool
 */
@Controller
public class DetectStorageManageHandler {

    @Autowired
    private ResponseUtil responseUtil;

    @Autowired
    private DetectStorageService detectStorageManageService;

    private static final String SEARCH_BY_GOODS_ID = "searchByGoodsID";
    private static final String SEARCH_BY_GOODS_NAME = "searchByGoodsName";
    private static final String SEARCH_BY_GOODS_TYPE = "searchByGoodsType";
    private static final String SEARCH_ALL = "searchAll";

    /**
     * 查询库存信息
     *
     * @param searchType       查询类型
     * @param keyword          查询关键字
     * @param repositoryBelong 查询仓库
     * @param offset           分页偏移值
     * @param limit            分页大小
     * @return 结果的一个Map，其中： key为 data 的代表记录数据；key 为 total 代表结果记录的数量
     */
    private Map<String, Object> query(String searchType, String keyword, String repositoryBelong, int offset,
                                      int limit) throws DetectStorageServiceException {
        Map<String, Object> queryResult = null;

        switch (searchType) {
            case SEARCH_ALL:
                if (StringUtils.isNumeric(repositoryBelong)) {
                    Integer repositoryID = Integer.valueOf(repositoryBelong);
                    queryResult = detectStorageManageService.selectAll(repositoryID, offset, limit);
                } else {
                    queryResult = detectStorageManageService.selectAll(null, offset, limit);
                }
                break;
            case SEARCH_BY_GOODS_ID:
                if (StringUtils.isNumeric(keyword)) {
                    Integer goodsID = Integer.valueOf(keyword);
                    if (StringUtils.isNumeric(repositoryBelong)) {
                        Integer repositoryID = Integer.valueOf(repositoryBelong);
                        queryResult = detectStorageManageService.selectByGoodsID(goodsID, repositoryID, offset, limit);
                    } else
                        queryResult = detectStorageManageService.selectByGoodsID(goodsID, null, offset, limit);
                }
                break;
            case SEARCH_BY_GOODS_TYPE:
                if (StringUtils.isNumeric(repositoryBelong)) {
                    Integer repositoryID = Integer.valueOf(repositoryBelong);
                    queryResult = detectStorageManageService.selectByGoodsType(keyword, repositoryID, offset, limit);
                } else
                    queryResult = detectStorageManageService.selectByGoodsType(keyword, null, offset, limit);
                break;
            case SEARCH_BY_GOODS_NAME:
                if (StringUtils.isNumeric(repositoryBelong)) {
                    Integer repositoryID = Integer.valueOf(repositoryBelong);
                    queryResult = detectStorageManageService.selectByGoodsName(keyword, repositoryID, offset, limit);
                } else
                    queryResult = detectStorageManageService.selectByGoodsName(keyword, null, offset, limit);
                break;
            default:
                // do other thing
                break;
        }

        return queryResult;
    }



}
