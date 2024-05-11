package com.seven.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public class JsonUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss[.SSS]");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final Gson gson;

    private static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss[.SSS]";

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @SneakyThrows
    public String toString(Object data) {
        if (Objects.isNull(data)) {
            return null;
        }
        return OBJECT_MAPPER.writeValueAsString(data);
    }

    @SneakyThrows
    public <T> List<T> toList(String json, Class<T> clazz) {
        TypeReference<List<T>> typeReference =
                new TypeReference<List<T>>() {
                    @Override
                    public Type getType() {
                        return new ParameterizedTypeImpl(clazz, List.class);
                    }
                };
        return OBJECT_MAPPER.readValue(json, typeReference);
    }

    @AllArgsConstructor
    static class ParameterizedTypeImpl implements ParameterizedType {
        private final Type acutalType;
        private final Type rawType;

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[] {acutalType};
        }

        @Override
        public Type getRawType() {
            return rawType;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }

    static {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(
                LocalDateTime.class, new LocalDateTimeDeserializer(DATE_TIME_FORMATTER));
        javaTimeModule.addSerializer(
                LocalDateTime.class, new LocalDateTimeSerializer(DATE_TIME_FORMATTER));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DATE_FORMATTER));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DATE_FORMATTER));
        OBJECT_MAPPER.registerModule(javaTimeModule);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    static {
        // LocalDateTime的序列化
        JsonSerializer<LocalDateTime> localDateTimeJsonSerializer =
                (localDateTime, type, jsonSerializationContext) -> {
                    if (localDateTime == null) {
                        return new JsonPrimitive("");
                    }
                    return new JsonPrimitive(localDateTime.format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
                };
        // LocalDateTime的反序列化
        JsonDeserializer<LocalDateTime> localDateTimeJsonDeserializer =
                (jsonElement, type, jsonDeserializationContext) -> {
                    String str = jsonElement.getAsJsonPrimitive().getAsString();
                    if (str == null || str.isEmpty()) {
                        return null;
                    }
                    return LocalDateTime.parse(str, DateTimeFormatter.ofPattern(TIME_FORMAT));
                };
        //        // HashMap的反序列化
        GsonHashMapDeserializer gsonHashMapDeserializer = new GsonHashMapDeserializer();
        //
        gson =
                new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd HH:mm:ss[.SSS]")
                        .registerTypeAdapter(LocalDateTime.class, localDateTimeJsonSerializer)
                        .registerTypeAdapter(LocalDateTime.class, localDateTimeJsonDeserializer)
                        .registerTypeAdapter(
                                new TypeToken<Map<String, Object>>() {}.getType(), gsonHashMapDeserializer)
                        .create();
    }

    /**
     * json字符串转换成Map对象
     *
     * @param json json字符串
     * @return Map<String, Object>
     */
    public static Map<String, Object> toMap(String json) {
        if (StringUtils.isBlank(json)) return new HashMap<>();
        return gson.fromJson(json, new TypeToken<Map<String, Object>>() {}.getType());
    }

    public static <T> T fromJson(String text, Class<T> tClass) {
        return gson.fromJson(text, tClass);
    }
}