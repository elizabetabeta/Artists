package ba.sum.fpmoz.salon.controller;

import ba.sum.fpmoz.salon.model.Car;
import ba.sum.fpmoz.salon.model.Location;
import ba.sum.fpmoz.salon.repositories.CarRepository;
import ba.sum.fpmoz.salon.model.UserDetails;
import ba.sum.fpmoz.salon.repositories.LocationRepository;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class CarController {
    @Autowired
    CarRepository carRepository;

    @Autowired
    LocationRepository locationRepository;

    private static String UPLOADED_FOLDER = "./uploads/";

    @GetMapping("/cars")
    public String showCars (Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();
        List<Location> locations = locationRepository.findAll();
        model.addAttribute("locations", locations);
        model.addAttribute("user", user);
        model.addAttribute("car", new Car());
        model.addAttribute("cars", carRepository.findAll());
        model.addAttribute("added", false);
        model.addAttribute("activeLink", "Cars");
        return "cars";
    }

    @PostMapping("/car/add")
    public String addPost (@Valid Car car, BindingResult result, @RequestParam("file") MultipartFile file, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("activeLink", "Cars");
        List<Location> locations = locationRepository.findAll();
        model.addAttribute("locations", locations);

        if (file.isEmpty()) {
            result.addError(new FieldError("car", "image", "Molimo odaberite sliku."));
        } else if (!file.getContentType().startsWith("image/")) {
            result.addError(new FieldError("car", "image", "Slika nije ispravnog formata."));
        } else {
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                Files.write(path, bytes);
                car.setImage(path.toString());
            } catch (IOException e) {
                result.addError(new FieldError("car", "image", "Problem s učitavanjem slike na poslužitelj."));
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("car", car);
            model.addAttribute("added", true);
            model.addAttribute("cars", carRepository.findAll());
            model.addAttribute("cars", carRepository.findAll());
            return "cars";
        }
        carRepository.save(car);
        return "redirect:/cars";
    }

    @GetMapping("/car/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();
        model.addAttribute("user", user);
        Car car = carRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
        List<Location> locations = locationRepository.findAll();
        model.addAttribute("locations", locations);
        model.addAttribute("car", car);
        model.addAttribute("cars", carRepository.findAll());
        model.addAttribute("activeLink", "Cars");
        return "car_edit";
    }

    @PostMapping("car/edit/{id}")
    public String editCar (@PathVariable("id") Long id, @Valid Car car, BindingResult result, @RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            result.addError(new FieldError("car", "image", "Molimo odaberite sliku."));
        } else if (!file.getContentType().startsWith("image/")) {
            result.addError(new FieldError("car", "image", "Slika nije ispravnog formata."));
        } else {
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                Files.write(path, bytes);
                car.setImage(path.toString());
            } catch (IOException e) {
                result.addError(new FieldError("car", "image", "Problem s učitavanjem slike na poslužitelj."));
            }
        }

        if (result.hasErrors()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetails user = (UserDetails) auth.getPrincipal();
            //user.getUser().getId();
            model.addAttribute("user", user);
            model.addAttribute("car", car);
            model.addAttribute("cars", carRepository.findAll());
            model.addAttribute("activeLink", "Cars");
            return "car_edit";
        }
        carRepository.save(car);
        return "redirect:/cars";
    }

    @GetMapping("/car/delete/{id}")
    public String deleteCar(@PathVariable("id") Long id, Model model) throws IOException {
        Car car = carRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pogrešan ID"));
        carRepository.delete(car);
        Files.delete(Path.of(car.getImage()));
        return "redirect:/cars";
    }
}