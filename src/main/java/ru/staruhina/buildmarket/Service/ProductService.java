package ru.staruhina.buildmarket.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.staruhina.buildmarket.Domain.model.Product;
import ru.staruhina.buildmarket.Repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    /**
     * Получение всех фильмов
     *
     * @return
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Получение конкретного фильма
     *
     * @return
     */
    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }

    /**
     * Получение конкретного фильма
     *
     * @return
     */
    public Product getById(int id) {
        return findById(id).orElseThrow();
    }

    /**
     * Получение списка избранных фильмов
     *
     * @return
     */
    public static double getFavouritesTotal(Set<Product> products) {
        // Возврат списка избранных фильмов
        var total = 0;
        for (Product product : products) {
            total += product.getId();
        }
        return total;
    }
}