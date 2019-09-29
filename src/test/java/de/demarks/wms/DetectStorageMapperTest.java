package de.demarks.wms;

import de.demarks.wms.dao.DetectStorageMapper;
import de.demarks.wms.domain.DetectStorage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.crypto.spec.DESedeKeySpec;
import java.util.List;

@ContextConfiguration(locations = {"classpath:config/SpringApplicationConfiguration.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class DetectStorageMapperTest {

    @Autowired
    DetectStorageMapper detectStorageMapper;

//    @Test
//    public void selectAllTest(){
//        List<DetectStorage> detectStorageList = detectStorageMapper.selectAll(null, null);
//        System.out.println(detectStorageList.size());
//    }
//
//    @Test
//    public void selectByGoodsIDTest(){
//        List<DetectStorage> detectStorageList = detectStorageMapper.selectByGoodsID(1001, null, null);
//        System.out.println(detectStorageList.size());
//    }
//
//    @Test
//    public void selectByGoodsNameTest(){
//        List<DetectStorage> detectStorageList = detectStorageMapper.selectByGoodsName("", null, null);
//        System.out.println(detectStorageList.size());
//    }
//
//    @Test
//    public void insertTest(){
//        DetectStorage detectStorage = new DetectStorage();
//        detectStorage.setGoodsID(1001);
//        detectStorage.setBatchID(1);
//        detectStorage.setRepositoryID(3001);
//        detectStorage.setCustomerID(2001);
//        detectStorage.setNumber(100);
//        detectStorage.setPassed(70);
//        detectStorage.setScratch(20);
//        detectStorage.setDamage(10);
//        detectStorageMapper.insert(detectStorage);
//    }
//
//    @Test
//    public void updateTest(){
//        List<DetectStorage> detectStorageList = detectStorageMapper.selectByGoodsID(1001, 1, 3001);
//        if (!detectStorageList.isEmpty()){
//            DetectStorage detectStorage = detectStorageList.get(0);
//            detectStorage.setDamage(100);
//            detectStorageMapper.update(detectStorage);
//        }
//    }
//
//    @Test
//    public void deleteTest(){
//
//    }
//
//    @Test
//    public void deleteByIDTest(){
//        detectStorageMapper.deleteByID(1001,1,3001);
//    }
}
