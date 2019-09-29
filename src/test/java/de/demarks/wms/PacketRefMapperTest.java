package de.demarks.wms;

import de.demarks.wms.dao.PacketRefMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = {"classpath:config/SpringApplicationConfiguration.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class PacketRefMapperTest {

    @Autowired
    PacketRefMapper packetRefMapper;

    @Test
    public void selectAllTest(){
//        List<PacketRef> packetRefList = packetRefMapper.selectAll(3001);
//        System.out.println(packetRefList.size());
    }

    @Test
    public void selectByPacketIDTest(){
//        PacketRef packetRef = packetRefMapper.selectByPacketID(3);
//        if (packetRef != null)
//            System.out.println(packetRef.toString());
    }

    @Test
    public void selectByRefIDTest(){
//        List<PacketRef> packetRefList = packetRefMapper.selectByRefID(1,3002);
//        System.out.println(packetRefList.size());

    }

    @Test
    public void selectByTraceTest(){
//        PacketRef packetRef = packetRefMapper.selectByTrace("00340456", 1, 3001);
//        if (packetRef != null)
//            System.out.println(packetRef.toString());
    }

    @Test
    public void selectByTraceApproximateTest(){
//        List<PacketRef> packetRefList = packetRefMapper.selectByTraceApproximate("00", "已签收", 3001);
//        System.out.println(packetRefList.size());

    }

    @Test
    public void insertTest(){
//        packetRefMapper.insert("0034999",2);
    }

    @Test
    public void deleteByPacketIDTest(){
//        packetRefMapper.deleteByPacketID(5);
    }



}
