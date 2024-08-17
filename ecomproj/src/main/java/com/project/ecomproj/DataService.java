package com.project.ecomproj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class DataService {

    @Autowired
    private deporepo dataRepository;

    public List<productItem> getAllDataItems() {
        return dataRepository.findAll();
    }

    public productItem addElement(productItem dataItem) {
        return dataRepository.save(dataItem);
    }

    public Optional<productItem> getElementId(int id) {
        return dataRepository.findById(id);
    }

    public productItem addProduct(productItem product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageDate(imageFile.getBytes());
        return dataRepository.save(product);

    }

    public productItem getProductById(int id) {
        return dataRepository.findById(id).orElse(null);

    }

	public productItem updateProduct(int id, productItem product, MultipartFile imageFile) throws IOException {
		product.setImageDate(imageFile.getBytes());
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());

        return  dataRepository.save(product);
	}

    public void delete(int id) {
       dataRepository.deleteById(id);

    }




 
}

