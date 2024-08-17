package com.project.ecomproj;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class productController {

    @Autowired
    private DataService service;

    @GetMapping
    public String get() {
        return "Hello World";
    }

    @GetMapping("/product")
    public List<productItem> getAllId() {
        return service.getAllDataItems();
    }

    @GetMapping("/product/{id}")
    public Optional<productItem> getproduct(@PathVariable int id) {
        return service.getElementId(id);
    }

    @PostMapping("/update")
    public productItem createDataItem(@RequestBody productItem newDataItem) {
        service.addElement(newDataItem);
        return newDataItem;
    }

    @PostMapping("/product")
    public ResponseEntity<?> addproduct(@RequestPart("product") String productJson,
            @RequestPart("imageFile") MultipartFile imageFile) {
        try {
            // Convert the JSON string back to a productItem object
            ObjectMapper objectMapper = new ObjectMapper();
            productItem product = objectMapper.readValue(productJson, productItem.class);

            // Proceed with your service logic
            productItem savedProduct = service.addProduct(product, imageFile);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId) {
        productItem product = service.getProductById(productId);
        byte[] imageFile = product.getImageDate();
        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(product.getImageType()))
                .body(imageFile);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestPart productItem product,
            @RequestPart MultipartFile imageFile) throws IOException {
        productItem product1 = service.updateProduct(id, product, imageFile);
        if (product1 != null) {
            return new ResponseEntity<>("updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("updated", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        productItem product = service.getProductById(id);
        if (product != null) {
            service.delete(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Not", HttpStatus.NOT_FOUND);
        }
    }

}
