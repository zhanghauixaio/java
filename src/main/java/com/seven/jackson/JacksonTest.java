package com.seven.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.*;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class JacksonTest {
    //用于 json和实体类互转
    private ObjectMapper objectMapper = new ObjectMapper();
    private JsonMapper jsonMapper = new JsonMapper();

    //用于 xml和实体类互转
    private XmlMapper xmlMapper = new XmlMapper();

    @SneakyThrows
    @Test
    public void testJson() {
        //获取测试数据
        Teacher teacher = setValue();
        // 实体类转json
        String json = objectMapper.writeValueAsString(teacher);
        System.out.println("json" + json);
    }

    /**
     * json转实体类
     */
    @Test
    public void JsonToBean() throws Exception {
        String json = "{\"name\":\"教师一号\",\"age\":25,\"studentList\":[{\"studentName\":\"学生一号\",\"studentNumber\":1},{\"studentName\":\"学生二号\",\"studentNumber\":2}]}";
        Teacher teacher = jsonMapper.readValue(json, Teacher.class);
        System.out.println("teacher:" + teacher);
        System.out.println("teacher-name:" + teacher.getName());
    }
    @Test
    public void fun1() throws JsonProcessingException {
        JsonMapper mapper = new JsonMapper();
        // mapper.registerModule(new ParameterNamesModule());
        mapper.registerModule(new Jdk8Module());
        String jsonStr = "{\"name\":\"YourBatman\",\"age\":18}";
        Teacher person = mapper.readValue(jsonStr, Teacher.class);
        System.out.println(person.getName());

        System.out.println(mapper.writeValueAsString(OptionalInt.of(1)));
        System.out.println(mapper.writeValueAsString(Optional.of("YourBatman")));
        System.out.println(mapper.writeValueAsString(IntStream.of(1, 2, 3)));
        System.out.println(mapper.writeValueAsString(Stream.of("1", "2", "3")));

        mapper.registerModule(new JavaTimeModule());
        System.out.println(mapper.writeValueAsString(LocalDateTime.now()));
        System.out.println(mapper.writeValueAsString(LocalDate.now()));
        System.out.println(mapper.writeValueAsString(LocalTime.now()));
        System.out.println(mapper.writeValueAsString(Instant.now()));
    }


    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public class Person {
        @JsonProperty
        private String name;
        @JsonProperty
        private Integer age;

        @JsonCreator
        public Person(@JsonProperty("name") String name,@JsonProperty("age") Integer age) {
            this.name = name;
            this.age = age;
        }

    }

    /**
     * map 转 json.
     */
    @Test
    public void MapToJSON() throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "map一号");
        map.put("power", "黑铁用户");
        map.put("num", 8);
        map.put("date", new Date());
        // map 转 json.
        String json = objectMapper.writeValueAsString(map);
        System.out.println("json:" + json);
    }
    /**
     * json 转 map
     */
    @Test
    public void JsonToMap() throws JsonProcessingException {
        String json = "{\"name\":\"map一号\",\"power\":\"黑铁用户\",\"num\":8}";
        // json 转 map
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonNode student = objectMapper.readValue(json, JsonNode.class);
        // Map<String, Object> map = objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});
        System.out.println(student);
        // System.out.println("name:" + map.get("name"));

        String str = "[1,2,4]";
        System.out.println(objectMapper.readValue(str, new TypeReference<List<Long>>() {}));
    }

    @Test
    @SneakyThrows
    public void testXml() {
        //获取测试数据
        Teacher teacher = setValue();
        // 序列化时加上文件头信息
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        // 实体类转xml
        String xml = xmlMapper.writeValueAsString(teacher);
        System.out.println("xml" + xml);
    }

    /**
     * Jackson
     * xml转实体
     */
    @Test
    public void testJavaXmltoBean() throws IOException {
        String xmlContent = "<TOOT><name>教师一号</name><age>25</age><item><TXT><studentName>学生一号</studentName><studentNumber>1</studentNumber></TXT><TXT><studentName>学生二号</studentName><studentNumber>2</studentNumber></TXT></item></TOOT>\n";
        Teacher teacher = new Teacher();
        teacher = xmlMapper.readValue(xmlContent, new TypeReference<Teacher>() {
        });
        System.out.println(teacher);
    }

    //准备的测试数据
    public Teacher setValue() {
        //学生1
        Student student01 = new Student();
        student01.setStudentName("学生一号");
        student01.setStudentNumber(1);
        //学生2
        Student student02 = new Student();
        student02.setStudentName("学生二号");
        student02.setStudentNumber(2);
        //集合
        ArrayList<Student> students = new ArrayList<>();
        students.add(student01);
        students.add(student02);
        //教师
        Teacher teacher = new Teacher();
        teacher.setName("教师一号");
        teacher.setAge(25);
        teacher.setStudentList(students);
        return teacher;
    }
}
