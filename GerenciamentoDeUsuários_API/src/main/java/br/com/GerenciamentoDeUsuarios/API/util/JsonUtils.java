package br.com.GerenciamentoDeUsuarios.API.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface JsonUtils {
	ObjectMapper objectMapper = new ObjectMapper();
	
	static ObjectMapper getDefaultObjectMapper() {
		return new ObjectMapper();
	}
	
	static JsonNode parse(String src) throws IOException {
		return JsonUtils.objectMapper.readTree(src);
	}
	
	static <Type> Type fromJson(JsonNode node, Class<Type> objClass) throws JsonProcessingException {
		return JsonUtils.objectMapper.treeToValue(node, objClass);
	}
}
