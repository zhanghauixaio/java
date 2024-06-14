package com.seven;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.seven.spring.vo.DemoVo;
import com.seven.spring.vo.User;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

public class Test {
    private static final Map<String, String> hashMap = new HashMap<>();
    static {
        final Map<String, String> map = new HashMap<>();

        for (int i = 0; i < 10; i++) {
            map.put(String.valueOf(i), String.valueOf(i));
        }
        hashMap.putAll(map);
    }

    // @org.junit.Test
    // public void testStream() {
    //     // List<String> list = Arrays.asList("hzohoa", "ahwehof", "hwe");
    //     List<User> list = new ArrayList<>();
    //     list.add(new User("hha", "hhaoi"));
    //     list.add(new User("werqe", "hoae"));
    //     DemoVo demoVo = new DemoVo();
    //     demoVo.setUsers(list);
    //     // 1、foreach
    //     // list.stream().forEach(i -> System.out.println(i.getName()));
    //     // demoVo.getUsers().forEach(i -> i.setName("123"));
    //     // list.forEach(i -> System.out.println(i.getName()));
    //     // demoVo.getUsers().forEach(i -> System.out.println(i.getName()));
    //     User user1 = new User("test", "test");
    //     user1.setName("hqe");
    //     System.out.println(user1.getName());
    //     list.stream().filter(user -> user.getName().startsWith("h")).forEach(user -> System.out.println(user.getName()));
    // }
    public static void main(String[] args) {
//        Jedis jedis = new Jedis("localhost",6379);
//        jedis.auth("123456");
//        jedis.set("jedis","redis.jedis");
//
//        System.out.println(jedis.get("jedis"));
//        LinkedHashSet set = new LinkedHashSet();
//        Iterator iterator = set.iterator();
        Map<String, String> map = new HashMap<>(256);

        String s = "行哈理工";
        for (int i = 0; i < 9000000; i++) {
            map.put(String.valueOf(i),s);
        }
        System.out.println(map.size());
        // System.out.println(Character.isLetter(s.charAt(0)));
        // System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd")
        //         .format(LocalDate.MIN));
        // System.out.println(LocalDateTime.MIN.isAfter(LocalDateTime.now()));
    }

    /**
     * test switch 执行顺序
     */
    @org.junit.Test
    public void test() {

        System.getProperties();
        SecurityManager securityManager = System.getSecurityManager();
        List<String> list = new ArrayList<>();
        boolean notEmpty = ObjectUtil.isNotEmpty(list);
        System.out.println(notEmpty);
        System.out.println(list.size());
        int i = LocalDate.now().compareTo(LocalDate.now().plusDays(1L));
        System.out.println(i);
    }

    @org.junit.Test
    public void testEnum() {
        Map<String, String> map = new HashMap<>();
        map.put("name", null);
        map.put("age", "123");
        if ("123".equals(map.get("age"))) {
            System.out.println("age get");
        }
        // if (map.get("name").equals("hha")) {
        //     System.out.println("name get");
        // }
        map.get("name");
        if ("hhh".equals(map.get("name"))) {
            System.out.println("name get");
        }
        System.out.println(StringUtils.isBlank(map.get("name")));
    }

    @org.junit.Test
    public void testMath() {
        int i = ((8 * 7 + 5) / 7 * 256) / 256;
        System.out.println(i);
        double floor = (double) ((8 * 7 + 5) / 7 * 256) / 256;
        System.out.println(floor);

        System.out.println(360 % 150);
    }

    @org.junit.Test
    public void testNull() {
        Boolean a = null;
        System.out.println(a);
    }

    @org.junit.Test
    public void testBigDecimalToString() {
        AVo aVo = new AVo();
        aVo.setAge(BigDecimal.ONE);
        A a = BeanUtil.copyProperties(aVo, A.class);
        System.out.println(a.getAge());
    }
    @Getter
    @Setter
    class AVo {
        private BigDecimal age;
    }
    @Getter
    @Setter
    class A {
        private String age;
    }

    @org.junit.Test
    public void testBeanUtil() {
        AVo aVo = new AVo();
        BeanUtil.copyProperties(aVo, A.class);
    }
}
