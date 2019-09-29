package de.demarks.wms;

import de.demarks.wms.dao.StockOutMapper;
import de.demarks.wms.domain.StockInDO;
import de.demarks.wms.domain.StockOutDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;
import java.util.List;

@ContextConfiguration(locations = {"classpath:config/SpringApplicationConfiguration.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class StockOutMapperTest {

    @Autowired
    StockOutMapper stockOutMapper;

    @Test
    public void selectAllTest(){
        List<StockOutDO> stockOutDOS = stockOutMapper.selectAll(1, 3001);
        System.out.println(stockOutDOS.size());
    }

    @Test
    public void selectByRecordIDTest(){
        StockOutDO stockOutDO = stockOutMapper.selectByRecordID(1);
        if (stockOutDO != null)
            System.out.println(stockOutDO.toString());
    }

    @Test
    public void selectByGoodsIDTest(){
        List<StockOutDO> stockOutDOS = stockOutMapper.selectByGoodsID(1001,null,null);
        System.out.println(stockOutDOS.size());
    }

    @Test
    public void selectByGoodsNameTest(){
        List<StockOutDO> stockOutDOS = stockOutMapper.selectByGoodsName("", null, null);
        System.out.println(stockOutDOS.size());
    }

    @Test
    public void selectByCustomerIDTest(){
        List<StockOutDO> stockOutDOS = stockOutMapper.selectByCustomerID(1001, null, null);
        System.out.println(stockOutDOS.size());
    }

    @Test
    public void selectByDateTest(){
        List<StockOutDO> stockOutDOS = stockOutMapper.selectByDate(null, null, null, null);
        System.out.println(stockOutDOS.size());
    }

    @Test
    public void insertTest(){
        StockOutDO stockOutDO = new StockOutDO();
        stockOutDO.setGoodsID(1001);
        stockOutDO.setBatchID(1);
        stockOutDO.setRepositoryID(3001);
        stockOutDO.setCustomerID(2001);
        stockOutDO.setNumber(2);
        stockOutDO.setPersonInCharge("admin");
        stockOutDO.setTime(new Date());
        stockOutMapper.insert(stockOutDO);
    }


}
