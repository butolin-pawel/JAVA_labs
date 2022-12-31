package com.vyatsu.task14.services;

import com.vyatsu.task14.entities.Filtr;
import com.vyatsu.task14.entities.Product;
import com.vyatsu.task14.repositories.IProductRepository;
//import com.vyatsu.task14.repositories.ProductRepository;
import com.vyatsu.task14.repositories.specifications.ProductSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class ProductsService {
    private IProductRepository repository;

    @Autowired
    public void setProductRepository(IProductRepository iProductRepository) {
        repository = iProductRepository;
    }

    public Product getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Product> getAllProducts() {
        return repository.findAll((ProductSpecs.titleContainsWord("")));
    }
    public List<Product> getFilterProduct(Filtr filter){
        return repository.findAll(ProductSpecs.titleContainsWord(filter.getSubstr())
                .and(ProductSpecs.priceGreaterThanOrEq(BigDecimal.valueOf(Integer.parseInt(filter.getMincost()))))
                .and(ProductSpecs.priceLesserThanOrEq(BigDecimal.valueOf(Integer.parseInt(filter.getMaxcost())))));
    }

    public void add(Product product) {
        repository.save(product);
    }
    public void delete(Product product) {repository.delete(product);}
    public void update(Product product){
        repository.save(product);
    }
}
