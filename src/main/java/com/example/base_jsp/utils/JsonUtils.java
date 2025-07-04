package com.example.base_jsp.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.ToNumberPolicy;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class JsonUtils {
	public static final Gson GSON = new GsonBuilder().setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE).create();

	public static final ObjectMapper mapper = new ObjectMapper();

	private static final ObjectMapper MAPPER = new ObjectMapper().findAndRegisterModules()
	                                                             .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
	                                                             .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
	                                                             .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
	                                                             .setSerializationInclusion(JsonInclude.Include.NON_NULL);


	/**
	 * @param object đối tượng cần chuyển đổi
	 * @return {@link Map} chứa các thuộc tính của object
	 */
	public static Map<String, Object> toMap(Object object) {
		// Input object đã là một Map
		if (object instanceof Map) {
			return GSON.fromJson(
					GSON.toJson(object),
					new TypeToken<Map<String, Object>>() {}.getType()
			);
		}

		// Convert sang chuỗi
		String str = DataUtils.toString(object);

		// Nếu chuỗi rỗng thì trả về một Map rỗng
		if (TextUtil.isBlank(str)) {
			return Collections.emptyMap();
		}

		// Nếu object là một Map thì convert sang Map
		if (isMap(object)) {
			return GSON.fromJson(str, new TypeToken<Map<String, Object>>() {}.getType());
		}

		// Trả về một Map rỗng
		return Collections.emptyMap();
	}

	/**
	 * @param object đối tượng cần chuyển đổi
	 * @param type  kiểu dữ liệu cần chuyển đổi
	 * @return {@link List} chứa các phần tử của object
	 */
	public static List<Object> toList(Object object, Type type) {
		if (!isList(object)) {
			return Collections.emptyList();
		}

		if (object instanceof List) {
			return GSON.fromJson(GSON.toJson(object), new TypeToken<List<Object>>() {}.getType());
		}

		String str = DataUtils.toString(object);

		return GSON.fromJson(str, type);
	}

	public static Object toMapOrList(Object json, Type type) {
		boolean isMap = isMap(json);
		boolean isList = isList(json);

		if (!isMap && !isList) {
			return null;
		}

		if (isMap) {
			return toMap(json);
		}

		return toList(json, type);
	}

	public static boolean isMap(Object json) {
		String jsonString = DataUtils.toString(json);

		return jsonString.startsWith("{") && jsonString.endsWith("}");
	}

	public static boolean isList(Object json) {
		String jsonString = DataUtils.toString(json);

		return jsonString.startsWith("[") && jsonString.endsWith("]");
	}

	public static <T> T toClass(Object json, Class<T> clazz) {
		if (Objects.isNull(json)) {
			return null;
		}

		if (json instanceof String) {
			return GSON.fromJson((String) json, clazz);
		}

		return GSON.fromJson(GSON.toJson(json), clazz);
	}

	public static List<Object[]> queryResult(List<?> query) {
		return mapper.convertValue(query, new TypeReference<>() {});
	}
}
