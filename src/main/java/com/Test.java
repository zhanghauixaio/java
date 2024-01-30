package com;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.Map;

public class Test {
    private static final Map<String, String> hashMap = new HashMap<>();
    static {
        final Map<String, String> map = new HashMap<>();

        for (int i = 0; i < 10; i++) {
            map.put(String.valueOf(i), String.valueOf(i));
        }
        hashMap.putAll(map);
    }

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
}
