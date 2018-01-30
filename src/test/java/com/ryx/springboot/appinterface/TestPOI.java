package com.ryx.springboot.appinterface;

import com.ryx.springboot.appinterface.domain.User;
import com.ryx.springboot.appinterface.util.PoiUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(JUnit4.class)
public class TestPOI {

    @Test
    public void testWriteObj() throws  Exception{
        List<User> userList= new ArrayList<>(10);
        for (int i=0;i<10;i++){
            User user=new User();
            user.setName("test"+i);
            user.setAddTime(new Date());
            user.setId(Long.parseLong(i+""));
            userList.add(user);
            System.out.println("user"+i+":"+user);
        }
        System.out.println("开始写入：===============================================");
        PoiUtil.writeExcel(userList,"d:/testuser.xlsx");
        System.out.println("完成");
        System.out.println("开始读取：===============================================");
        List<User> readList=PoiUtil.readExcel("d:/testuser.xlsx",new User());
        for (User user:readList) {
            System.out.println("user:"+user);
        }
        System.out.println("完成");
    }

    @Test
    public void  testUniversalWrite() throws Exception{
        List<List<Object>> conList=new ArrayList<>(12);
        for (int i=0;i<10;i++){
            List<Object> list=new ArrayList<>();
            list.add(i);
            list.add(null);
            list.add(null);
            list.add(new Date());
            list.add(new Date());
            conList.add(list);
            System.out.println("list:"+list);
        }
        System.out.println("通用型开始写入：===============================================");
        PoiUtil.writeExcelUniversal(conList,"d:/testUniversal.xlsx");
        System.out.println("完成");

        System.out.println("开始读取：===============================================");
        List<List<String>> readList=PoiUtil.readExcelUniversal("d:/testUniversal.xlsx");
        for (List list:readList) {
            System.out.println("list:"+list);
        }
        System.out.println("完成");
    }

    @Test
    public  void testReadObj() throws Exception {

        System.out.println("开始读取：");
        List<User> readList=PoiUtil.readExcel("d:/testuser.xlsx",new User());
        for (User user:readList) {
            System.out.println("user:"+user);
        }
        System.out.println("完成");
    }

    @Test
    public  void testReadUniversal() throws Exception {
        System.out.println("开始读取：");
        List<List<String>> readList=PoiUtil.readExcelUniversal("d:/testuser.xlsx");
        for (List list:readList) {
            System.out.println("list:"+list);
        }
        System.out.println("完成");

    }

}
