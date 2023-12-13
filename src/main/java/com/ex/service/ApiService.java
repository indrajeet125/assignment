package com.ex.service;

import com.ex.model.AuthRequest;
import com.ex.model.CreateCustomer;
import com.ex.model.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class ApiService {


    public static String token = "";

    public boolean addCustomer(HttpSession session, CreateCustomer customer) {
        String token = (String) session.getAttribute("token");
        String apiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=create";

        WebClient client = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) // Specify content type
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(customer);

            String response = client.post()
                    .bodyValue(json)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            assert response != null;
            String trim = response.trim();
//            System.out.println("Response: " + trim);
            return trim.equals("Successfully Created"); // Adjust based on your actual API response

        } catch (JsonProcessingException e) {
            return false;
        }
    }

    public List<Customer> getCustomerList(HttpSession session) {
        String token = (String) session.getAttribute("token");
        String apiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list";
        WebClient client = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .build();

        String response = client.get()
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper mapper = new ObjectMapper();
        List<Customer> customerList = null;
        try {
            customerList = mapper.readValue(response,
                    new TypeReference<List<Customer>>() {
                    });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return customerList;
    }

    public boolean deleteCustomer(HttpSession session, String uuid) {
        String token = (String) session.getAttribute("token");
        String trimuuid = uuid.trim();
        String apiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=delete&uuid=" + trimuuid;

        try {
            WebClient client = WebClient.builder()
                    .baseUrl(apiUrl)
                    .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .build();

            String response = client.post()
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

//            assert response != null;
            String trim = response.trim();
            return trim.equals("Successfully deleted");
        } catch (Exception e) {
            return false;
        }
    }


    public boolean update(HttpSession session, String uuid, CreateCustomer customer) {
        String token = (String) session.getAttribute("token");
        String apiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=update";

        WebClient client = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) // Specify content type
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(customer);

            String response = client.post()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("uuid", uuid)
                            .build())
                    .bodyValue(json)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            String trim = response.trim();
//            System.out.println("Response: " + trim);
            return trim != null && trim.equals("Successfully Updated"); // Adjust based on your actual API response
        } catch (Exception e) {
//            System.out.println("error occurred in update");
            return false;
        }
    }

    //...................utils method .................

    public String generateToken(HttpSession session, AuthRequest authRequest) {
        String apiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp?cmd=login";
        WebClient client = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) // Specify content type
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(authRequest);
            String response = client.post()
                    .bodyValue(json)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            assert response != null;
            token = extractToken(response);
//            System.out.println("token: " + token);
            return token;
        } catch (Exception e) {
            return null;
        }


    }

    private String extractToken(String response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(response);
            token = jsonNode.get("access_token").asText();

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return token;
    }
}
