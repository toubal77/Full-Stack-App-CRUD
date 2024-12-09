package categorie;

import categorie.entities.Category;
import categorie.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() == 0) {
            Category electronics = new Category("Électronique", null, true);
            Category clothing = new Category("Vêtements", null, true);
            Category homeAndGarden = new Category("Maison et Jardin", null, true);
            Category healthAndBeauty = new Category("Santé et Beauté", null, true);
            Category sports = new Category("Sports", null, true);
            Category automotive = new Category("Automobile", null, true);
            Category books = new Category("Livres", null, true);
            Category toys = new Category("Jouets", null, true);
            Category music = new Category("Musique", null, true);
            Category groceries = new Category("Épicerie", null, true);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date creationDate = dateFormat.parse("2024-10-16");

            electronics = categoryRepository.save(electronics);
         /*   electronics.setCreationDate(creationDate);
            clothing.setCreationDate(creationDate);
            homeAndGarden.setCreationDate(creationDate);
            healthAndBeauty.setCreationDate(creationDate);
            sports.setCreationDate(creationDate);
            automotive.setCreationDate(creationDate);
            books.setCreationDate(creationDate);
            toys.setCreationDate(creationDate);
            music.setCreationDate(creationDate);
            groceries.setCreationDate(creationDate); */

            Category telephone = new Category("Téléphones", electronics, false);
            telephone.setParent(electronics);
            electronics.setChildren(telephone);

            categoryRepository.save(electronics);
            categoryRepository.save(clothing);
            categoryRepository.save(homeAndGarden);
            categoryRepository.save(healthAndBeauty);
            categoryRepository.save(sports);
            categoryRepository.save(automotive);
            categoryRepository.save(books);
            categoryRepository.save(toys);
            categoryRepository.save(music);
            categoryRepository.save(groceries);
        }
    }
}