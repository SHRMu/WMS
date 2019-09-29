package de.demarks.wms;

import de.demarks.wms.dao.PacketMapper;
import de.demarks.wms.domain.PacketDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = {"classpath:config/SpringApplicationConfiguration.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class PacketMapperTest {

    @Autowired
    PacketMapper packetMapper;

//    @Test
//    public void selectAllTest(){
//        List<PacketDO> packetDOS = packetMapper.selectAll(null);
//        System.out.println(packetDOS.size());
//    }
//
//    @Test
//    public void selectByPacketIDTest(){
//        PacketDO packetDO = packetMapper.selectByPacketID(1);
//        if (packetDO!=null)
//            System.out.println(packetDO.toString());
//    }
//
    @Test
    public void selectByTraceTest(){
        PacketDO packetDO = packetMapper.selectByTrace("23545", 3001);
        if (packetDO !=null)
            System.out.println(packetDO.toString());

    }
//
//    @Test
//    public void selectByTraceApproximateTest(){
//        List<PacketDO> packetDOS = packetMapper.selectByTraceApproximate("", "发货中", 3001);
//        System.out.println(packetDOS.size());
//    }
//
//    @Test
//    public void selectByDate(){
//        List<PacketDO> packetDOS = packetMapper.selectByDate(1, 3001, null, new Date());
//        System.out.println(packetDOS.size());
//    }
//
//    @Test
//    public void insertTest(){
//        PacketDO packetDO = new PacketDO();
//    }
//
//    @Test
//    public void insertBatchTest(){
//        List<PacketDO> packetDOS = packetMapper.selectByTraceApproximate("", null, null);
//        for (PacketDO packetDO:
//             packetDOS) {
//            packetDO.setTime(new Date());
//        }
//        packetMapper.insertBatch(packetDOS);
//    }
//
//    @Test
//    public void updateTest(){
//        PacketDO packetDO = packetMapper.selectByPacketID(4);
//        if (packetDO != null){
//            packetDO.setStatus("已签收");
//            packetMapper.update(packetDO);
//        }
//    }
//
//    @Test
//    public void deleteByPacketIDTest(){
//        packetMapper.deleteByPacketID(4);
//    }
}
