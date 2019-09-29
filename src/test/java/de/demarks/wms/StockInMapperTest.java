package de.demarks.wms;

import de.demarks.wms.dao.StockInMapper;
import de.demarks.wms.domain.StockInDO;
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
public class StockInMapperTest{

    @Autowired
    private StockInMapper stockInMapper;

//    @Test
//    public void selectAllTest(){
//        List<StockInDO> stockInDOS = stockInMapper.selectAll(null, null);
//        System.out.println(stockInDOS.size());
//    }
//
//    @Test
//    public void selectByRecordIDTest(){
//        StockInDO stockInDO = stockInMapper.selectByRecordID(1);
//        if (stockInDO !=null)
//            System.out.println(stockInDO.toString());
//    }
//
//    @Test
//    public void selectByGoodsIDTest(){
//        List<StockInDO> stockInDOS = stockInMapper.selectByGoodsID(1001, null, null);
//        System.out.println(stockInDOS.size());
//    }
//
//    @Test
//    public void selectByGoodsNameTest(){
//        List<StockInDO> stockInDOS = stockInMapper.selectByGoodsName("", null, null);
//        System.out.println(stockInDOS.size());
//    }
//
//    @Test
//    public void selectByDateTest(){
//        List<StockInDO> stockInDOS = stockInMapper.selectByDate(null, null, null, new Date());
//        System.out.println(stockInDOS.size());
//    }
//
//    @Test
//    public void insertTest(){
//        StockInDO stockInDO = new StockInDO();
//        stockInDO.setGoodsID(1001);
//        stockInDO.setBatchID(1);
//        stockInDO.setCustomerID(2001);
//        stockInDO.setNumber(100);
//        stockInDO.setPersonInCharge("admin");
//        stockInDO.setTime(new Date());
//        stockInDO.setRepositoryID(3001);
//        stockInMapper.insert(stockInDO);
//
//    }
//
//   @Test
//    public void updateTest(){
//       StockInDO stockInDO = stockInMapper.selectByRecordID(1);
//       stockInDO.setNumber(200);
//       stockInMapper.update(stockInDO);
//   }
//
//   @Test
//    public void deleteByRecordIDTest(){
//        stockInMapper.deleteByRecordID(1);
//   }

}
