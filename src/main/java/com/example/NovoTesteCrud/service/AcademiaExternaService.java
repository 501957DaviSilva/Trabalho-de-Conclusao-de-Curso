package com.example.NovoTesteCrud.service;

import com.example.NovoTesteCrud.domain.acad.dto.AcademiaExternaDTO;
import com.example.NovoTesteCrud.domain.acad.dto.AcademiaDetalhesDTO;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class AcademiaExternaService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${google.api.key}")
    private String apiKey;

    private final String FIELD_MASK = "places.name,places.displayName,places.formattedAddress,places.rating,places.userRatingCount,places.businessStatus,places.photos";

    public List<AcademiaExternaDTO> buscarAcademiasProximas(String endereco) {
        String coordenadas = buscarCoordenadas(endereco);
        return buscarAcademias(coordenadas);
    }

    private String buscarCoordenadas(String endereco) {
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" +
                endereco.replace(" ", "+") + "&key=" + apiKey;

        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);
        JsonNode results = response.getBody().path("results");

        if (!results.isArray() || results.size() == 0) {
            throw new RuntimeException("Endereço inválido ou não encontrado");
        }

        JsonNode location = results.get(0).path("geometry").path("location");
        return location.get("lat").asText() + "," + location.get("lng").asText();
    }

    private List<AcademiaExternaDTO> buscarAcademias(String coordenadas) {
        String url = "https://places.googleapis.com/v1/places:searchNearby";

        String[] coords = coordenadas.split(",");
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("includedTypes", List.of("gym"));
        requestBody.put("maxResultCount", 10);
        requestBody.put("locationRestriction", Map.of("circle", Map.of(
                "center", Map.of("latitude", Double.parseDouble(coords[0]), "longitude", Double.parseDouble(coords[1])),
                "radius", 2000
        )));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Goog-Api-Key", apiKey);
        headers.set("X-Goog-FieldMask", FIELD_MASK);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.POST, entity, JsonNode.class);
        JsonNode places = response.getBody().path("places");

        List<AcademiaExternaDTO> lista = new ArrayList<>();
        for (JsonNode place : places) {
            String photoReference = "";
            if (place.has("photos") && place.path("photos").isArray() && place.path("photos").size() > 0) {
                photoReference = place.path("photos").get(0).path("name").asText();
            }

            lista.add(new AcademiaExternaDTO(
                    place.path("displayName").path("text").asText(),
                    place.path("formattedAddress").asText(),
                    place.path("name").asText().replace("places/", ""),                    place.path("rating").asDouble(0),
                    place.path("userRatingCount").asInt(0),
                    photoReference,
                    place.path("businessStatus").asText()
            ));
        }
        return lista;
    }

    public AcademiaDetalhesDTO detalhesComoDTO(String placeId) {
        String url = "https://places.googleapis.com/v1/places/" + placeId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Goog-Api-Key", apiKey);
        headers.set("X-Goog-FieldMask", String.join(",",
                "displayName",
                "formattedAddress",
                "rating",
                "userRatingCount",
                "websiteUri",
                "internationalPhoneNumber",
                "currentOpeningHours.weekdayDescriptions",
                "currentOpeningHours.openNow",
                "googleMapsUri",
                "photos.name"
        ));

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, entity, JsonNode.class);
        JsonNode body = response.getBody();

        String nome = body.path("displayName").path("text").asText();
        String endereco = body.path("formattedAddress").asText();
        String telefone = body.path("internationalPhoneNumber").asText("");
        String site = body.path("websiteUri").asText("");
        double avaliacao = body.path("rating").asDouble(0);
        int totalAvaliacoes = body.path("userRatingCount").asInt(0);
        boolean abertaAgora = body.path("currentOpeningHours").path("openNow").asBoolean(false);

        List<String> horarios = new ArrayList<>();
        body.path("currentOpeningHours").path("weekdayDescriptions").forEach(node -> horarios.add(node.asText()));

        List<String> fotos = new ArrayList<>();
        body.path("photos").forEach(photo -> {
            String ref = photo.path("name").asText(); // ex: places/ChI.../photos/XYZ
            fotos.add("https://places.googleapis.com/v1/" + ref + "/media?maxWidthPx=800&key=" + apiKey);
        });

        String mapsLink = body.path("googleMapsUri").asText();

        return new AcademiaDetalhesDTO(nome, endereco, telefone, site, avaliacao, totalAvaliacoes, abertaAgora, horarios, mapsLink, fotos);
    }

}
