package com.auth.authorization.handlers;

import com.auth.authorization.model.UserRole;
import com.auth.authorization.repository.UserRoleRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class AuthHandler {
    private UserRoleRepository userRoleRepository;
    private final ObjectMapper objectMapper;
    private static final String URI = "http://localhost:8181/v1/data/authz/allow";


    @Autowired
    public AuthHandler(UserRoleRepository userRoleRepository, ObjectMapper objectMapper){
        this.userRoleRepository= userRoleRepository;
        this.objectMapper = objectMapper;
    }

    public boolean isUserAuthorized(String path, String userId) {
        String uniqueId= userId.substring(16, userId.length()-1);
        Optional<UserRole> userRole= userRoleRepository.findByUniqueId(uniqueId);
        if(!userRole.isPresent())
            return false;
        RestTemplate restTemplate= new RestTemplate();
        Map<String, Object> input = new HashMap<>();

        input.put("authorities", userRole.get().getRole());
      //  input.put("method", "GET");
        input.put("path", path);


        ObjectNode requestNode = objectMapper.createObjectNode();
        requestNode.set("input", objectMapper.valueToTree(input));

        JsonNode responseNode = Objects.requireNonNull(restTemplate.postForObject(URI, requestNode, JsonNode.class));

        if (responseNode.has("result") && !responseNode.get("result").asBoolean()) {
           return false;
        }
        else
            return true;
    }
}
