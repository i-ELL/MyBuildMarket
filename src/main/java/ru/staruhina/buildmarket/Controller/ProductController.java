package ru.staruhina.buildmarket.Controller;

import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.staruhina.buildmarket.Service.AuthService;
import ru.staruhina.buildmarket.Service.ProductService;


@Controller
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final AuthService authService;

    /**
     * Получение конкретного фильма
     * @return
     */
    @GetMapping("/{id}")
    public String getProduct(
            @PathVariable("id") int id,
            Model model
    ) {

//        // Провряем, авторизован ли пользователь добавляя переменную isAuth
//        model.addAttribute("isAuth", authService.getAuthUser().isPresent());
//
//        // Если пользователь авторизован, то добавляем его в модель
//        authService.getAuthUser().ifPresent(user -> model.addAttribute("user", user));
        model.addAttribute("userInfo", authService.getUserInfo());

        // Проверка на существование продукта
        var product = productService.getById(id);
        if (product == null) {
            return "redirect:/error";
        }
        model.addAttribute("product", product);
        return "product";
    }
}