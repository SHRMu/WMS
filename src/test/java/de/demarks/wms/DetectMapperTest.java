package de.demarks.wms;

import de.demarks.wms.dao.DetectMapper;
import de.demarks.wms.domain.DetectDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

@ContextConfiguration(locations = {"classpath:config/SpringApplicationConfiguration.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class DetectMapperTest {

    @Autowired
    private DetectMapper detectMapper;

//    @Test
//    public void selectAllTest(){
//        List<DetectDO> detectDOS = detectMapper.selectAll(1, null);
//        System.out.println(detectDOS.size());
//    }
//
//    @Test
//    public void selectByRecordIDTest(){
//        DetectDO detectDO = detectMapper.selectByRecordID(1);
//        if (detectDO !=null)
//            System.out.println(detectDO.toString());
//    }
//
//    @Test
//    public void selectByGoodsIDTest(){
//        List<DetectDO> detectDOS = detectMapper.selectByGoodsID(1001, null, null, new Date(), null);
//        System.out.println(detectDOS.size());
//    }
//
//    @Test
//    public void selectByGoodsNameTest(){
//        List<DetectDO> detectDOS = detectMapper.selectByGoodsName("", null, null, null, null);
//        System.out.println(detectDOS.size());
//    }
//
//    @Test
//    public void insertTest(){
//        DetectDO detectDO = new DetectDO();
//        detectDO.setGoodsID(1001);
//        detectDO.setCustomerID(2001);
//        detectDO.setBatchID(1);
//        detectDO.setRepositoryID(3001);
//        detectDO.setNumber(100);
//        detectDO.setPassed(50);
//        detectDO.setScratch(33);
//        detectDO.setDamage(17);
//        detectDO.setTime(new Date());
//        detectDO.setPersonInCharge("admim");
//        detectMapper.insert(detectDO);
//    }
//
//    @Test
//    public void updateTest(){
//        DetectDO detectDO = detectMapper.selectByRecordID(1);
//        detectDO.setTime(new Date());
//        detectDO.setDesc("test");
//        detectMapper.update(detectDO);
//    }
//
//    @Test
//    public void deleteByRecordIDTest(){
//        detectMapper.deleteByRecordID(2);
//    }

}
