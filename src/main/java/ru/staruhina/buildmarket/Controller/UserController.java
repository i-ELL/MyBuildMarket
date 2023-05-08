package ru.staruhina.buildmarket.Controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import ru.staruhina.buildmarket.Service.AuthService;
import ru.staruhina.buildmarket.Service.ProductService;
import ru.staruhina.buildmarket.Service.UserService;


@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final ProductService productService;
    private final AuthService authService;
    private final UserService userService;

//    @PostMapping("/add-to-favorite/{id}")
//    public String addToFavorite(@PathVariable Integer id, Model model) {
//        model.addAttribute("userInfo", authService.getUserInfo());
//
//        if (userService.addToCartByProductId(id)) {
//            return "redirect:/products/" + id + "?success";
//        } else {
//            return "redirect:/products/" + id + "?error";
//        }
//    }

    @PostMapping("/add-to-favourite/{id}")
    public String addToFavorite(Model model, @PathVariable int id) {
        model.addAttribute("userInfo", authService.getUserInfo());
        userService.addToFavouriteById(id);
        return "redirect:/";
    }

    /**
     * Страница профиля пользователя
     *
     * @return
     */
    @GetMapping("/profile")
    public String profile(Model model) {
        // Провряем, авторизован ли пользователь добавляя переменную isAuth
        model.addAttribute("userInfo", authService.getUserInfo());


        // Если пользователь авторизован, то добавляем его в модель
        var user = authService.getAuthUser().orElse(null);

        var favourite = user.getProducts();
        model.addAttribute("favourite", favourite);
        model.addAttribute("favouriteTotal", ProductService.getFavouritesTotal(favourite));

        return "profile";
    }



    /*
     * Удаление товара из избранного по id товара
     */
    @PostMapping("/delete-from-favourite/{id}")
    public String deleteFromFavourite(Model model, @PathVariable int id) {
        model.addAttribute("userInfo", authService.getUserInfo());
        userService.deleteFromFavouriteById(id);
        return "redirect:/user/profile";
    }
}