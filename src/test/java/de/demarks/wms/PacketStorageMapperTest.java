package de.demarks.wms;

import de.demarks.wms.dao.PacketStorageMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = {"classpath:config/SpringApplicationConfiguration.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class PacketStorageMapperTest {

    @Autowired
    private PacketStorageMapper packetStorageMapper;

    @Test
    public void selectAllTest(){
//        List<PacketStorage> packetStorageList = packetStorageMapper.selectAll(2, 3001);
//        System.out.println(packetStorageList.size());
    }

    @Test
    public void selectByGoodsIDTest(){
//        List<PacketStorage> packetStorageList = packetStorageMapper.selectByGoodsID(1001, null, 3001);
//        System.out.println(packetStorageList.size());
    }

    @Test
    public void selectByGoodsNameTest(){
//        List<PacketStorage> packetStorageList = packetStorageMapper.selectByGoodsName("3233", 2, 3001);
//        System.out.println(packetStorageList.size());
    }

    @Test
    public void selectByCustomerIDTest(){
//        List<PacketStorage> packetStorageList = packetStorageMapper.selectByCustomerID(2001, 1, null);
//        System.out.println(packetStorageList.size());
    }

    @Test
    public void selectByPacketTraceTest(){
//        List<PacketStorage> packetStorageList = packetStorageMapper.selectByPacketTrace("000", null, null);
//        System.out.println(packetStorageList.size());
    }

    @Test
    public void insertTest(){
//        PacketStorage packetStorage = new PacketStorage();
//        packetStorage.setPacketID(2);
//        packetStorage.setGoodsID(1001);
//        packetStorage.setCustomerID(2001);
//        packetStorage.setRepositoryID(3001);
//        packetStorage.setNumber((long) 100);
//        packetStorage.setStorage((long) 0);
//        packetStorageMapper.insert(packetStorage);
    }

    @Test
    public void updateTest(){
//        List<PacketStorage> packetStorageList = packetStorageMapper.selectByGoodsID(1001, 2, 3001);
//        if (!packetStorageList.isEmpty()){
//            PacketStorage packetStorage = packetStorageList.get(0);
//            packetStorage.setStorage((long) 100);
//            packetStorageMapper.update(packetStorage);
//        }
    }

    @Test
    public void deleteTest(){
//        List<PacketStorage> packetStorageList = packetStorageMapper.selectAll(2, 3001);
//        for (PacketStorage packetStorage:
//                packetStorageList) {
//            packetStorageMapper.delete(packetStorage);
//        }
    }

}
