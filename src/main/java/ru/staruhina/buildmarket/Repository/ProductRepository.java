package ru.staruhina.buildmarket.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.staruhina.buildmarket.Domain.model.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}