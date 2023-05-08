package ru.staruhina.buildmarket.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.staruhina.buildmarket.Domain.dto.UserRegisterDTO;
import ru.staruhina.buildmarket.Domain.model.Product;
import ru.staruhina.buildmarket.Domain.model.User;
import ru.staruhina.buildmarket.Mapper.UserMapper;
import ru.staruhina.buildmarket.Repository.UserRepository;


import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthService authService;
    private final ProductService productService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Хеширование пароля
     *
     * @param password
     * @return
     */
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    /**
     * Сохранение пользователя
     */
    public void save(User user) {
        userRepository.save(user);
    }

    /**
     * Получение всех пользователей
     *
     * @return
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Получение пользователя по id
     * @param id
     * @return
     */
    public User getById(int id) {
        return findById(id).orElse(null);
    }


    /**
     * Поиск пользователя по username
     *
     * @param username
     * @return
     */
    public Optional<User> findByUserName(String username) {
        return userRepository.findByUsername(username);
    }


    /**
     * Поиск пользователя по email
     *
     * @param email
     * @return
     */
//    public Optional<User> findByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }


    /**
     * Получение конкретного юзера
     *
     * @return
     */
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }


    /**
     * Регистрация пользователя
     *
     * @param userRegisterDTO
     */
    public void register(UserRegisterDTO userRegisterDTO) {
        save(userMapper.registerDTOToUser(userRegisterDTO));
    }


    /**
     * Добавление товара в избранное по id фильма
     *
     * @param productId
     * @return
     */
    public boolean addToFavouriteById(int productId) {
        //Получаем авторизованного пользователя
        var user = authService.getAuthUser().orElse(null);

        // Если пользователь не авторизован, то ничего не делаем
        if (user == null) {
            return false;
        }

        // Добавляем фильм в избранное
        var product = productService.findById(productId).orElse(null);
        // Получаем список фильмов пользователя
        var products = user.getProducts();

        // Если фильм уже есть в избранном, то ничего не делаем
        if (products.contains(product)) {
            return false;
        }
        //Добавляем фильм в избранное
        products.add(product);
        //Сохраняем пользователя
        save(user);
        return true;
    }


    /**
     * Добавление фильма в избранное
     * @param product
     * @return
     */
    public boolean addToFavourite(Product product) {
        // Получаем авторизованного пользователя
        var user = authService.getAuthUser().orElse(null);

        // Если пользователь не авторизован, то ничего не делаем
        if (user == null) {
            return false;
        }

        // Добавляем товар в корзину
        var products = user.getProducts();

        // Если товар уже есть в корзине, то ничего не делаем
        if (products.contains(product)) {
            return false;
        }

        // Добавляем товар в корзину
        products.add(product);

        // Сохраняем пользователя
        save(user);
        return true;
    }

    /**
     * Удаление фильма из избранного по id фильма
     * @param id
     * @return
     */
    public boolean deleteFromFavouriteById(int id) {
        var product = productService.findById(id).orElse(null);

        if (product == null) {
            return false;
        } else {
            return removeFromFavourite(product);
        }
    }

    /**
     * Удаление фильма из избранного
     * @param product
     * @return
     */
    public boolean removeFromFavourite(Product product) {
        var user = authService.getAuthUser().orElse(null);
        if (user == null) {
            return false;
        }

        var products = user.getProducts();
        products.remove(product);

        save(user);
        return true;
    }


//    /**
//     * Обновление данных пользователя
//     */
//    public void update(User user, UserEditDTO userEditDTO) {
//        user.setUsername(userEditDTO.getUsername());
//        user.setImage(userEditDTO.getImage());
//        user.setDescription(userEditDTO.getDescription());
//        save(user);
//    }

//    /**
//     * Обновление данных пользователя
//     * @param userEditDTO
//     * @return
//     */
//    public boolean update(UserEditDTO userEditDTO) {
//        // Получаем авторизованного пользователя
//        var user = authService.getAuthUser().orElse(null);
//
//        // Если пользователь не авторизован, то ничего не делаем
//        if (user == null) {
//            return false;
//        }
//        // Проверяем, что username не занят другим пользователем
//        var userWithSameUsername = findByUserName(userEditDTO.getUsername()).orElse(null);
//        if (userWithSameUsername != null && !userWithSameUsername.equals(user)) {
//            return false;
//        }
//
//        // Обновляем данные пользователя
//        update(user, userEditDTO);
//        return true;
//    }
//
//    /**
//     * Получение данных пользователя
//     * @return
//     */
//    public UserEditDTO getUserEditDTO() {
//        var user = authService.getAuthUser().orElse(null);
//        if (user == null) {
//            return null;
//        }
//        var user1 = findByUserName(user.getUsername()).orElse(null);
//        return userMapper.userToUserEditDTO(user1);
//    }

}
