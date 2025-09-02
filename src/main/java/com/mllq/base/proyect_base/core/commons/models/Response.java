package com.mllq.base.proyect_base.core.commons.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.net.URI;

@Setter
@Getter
public class Response {
    // 200 OK con body
    public static <D> ResponseEntity<D> ok(D body) {
        return ResponseEntity.ok(body);
    }

    // 200 OK con headers y body
    public static <D> ResponseEntity<D> ok(D body, HttpHeaders headers) {
        return ResponseEntity.ok()
                .headers(headers)
                .body(body);
    }

    // 200 OK con header único
    public static <D> ResponseEntity<D> ok(D body, String headerName, String headerValue) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(headerName, headerValue);
        return ResponseEntity.ok()
                .headers(headers)
                .body(body);
    }

    // 201 Created con location y body
    public static ResponseEntity<ResponseSimple> created(String location) {
        return ResponseEntity.created(URI.create(location)).body(new ResponseSimple());
    }
    public static <D> ResponseEntity<D> created(URI location, D body) {
        return ResponseEntity.created(location).body(body);
    }
    // 204 No Content
    public static ResponseEntity<Void> noContent() {
        return ResponseEntity.noContent().build();
    }

    // 204 No Content con headers
    public static ResponseEntity<Void> noContent(HttpHeaders headers) {
        return ResponseEntity.noContent().headers(headers).build();
    }

}
