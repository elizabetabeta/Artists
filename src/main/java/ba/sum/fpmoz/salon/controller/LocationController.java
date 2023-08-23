package ba.sum.fpmoz.salon.controller;

import ba.sum.fpmoz.salon.model.Location;
import ba.sum.fpmoz.salon.repositories.LocationRepository;
import ba.sum.fpmoz.salon.model.UserDetails;
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
import java.util.List;


@Controller
public class LocationController {
    @Autowired
    LocationRepository locationRepository;

    @GetMapping("/locations")
    public String showLocation (Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();
        List<Location> locations = locationRepository.findAll();
        model.addAttribute("user", user);
        model.addAttribute("location", new Location());
        model.addAttribute("locations", locations);
        model.addAttribute("added", false);
        model.addAttribute("activeLink", "Locations");
        return "locations";
    }

    @PostMapping("/location/add")
    public String addPost (@Valid Location location, BindingResult result, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("activeLink", "Locations");

        if (result.hasErrors()) {
            model.addAttribute("location", location);
            model.addAttribute("added", true);
            model.addAttribute("locations", locationRepository.findAll());
            model.addAttribute("locations", locationRepository.findAll());
            return "locations";
        }
        locationRepository.save(location);
        return "redirect:/locations";
    }

    @GetMapping("/location/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();
        model.addAttribute("user", user);
        Location location = locationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());

        model.addAttribute("location", location);
        model.addAttribute("locations", locationRepository.findAll());
        model.addAttribute("activeLink", "Locations");
        return "location_edit";
    }

    @PostMapping("location/edit/{id}")
    public String editLocation (@PathVariable("id") Long id, @Valid Location location, BindingResult result, Model model) {

        if (result.hasErrors()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetails user = (UserDetails) auth.getPrincipal();
            //user.getUser().getId();
            model.addAttribute("user", user);
            model.addAttribute("location", location);
            model.addAttribute("locations", locationRepository.findAll());
            model.addAttribute("activeLink", "Locations");
            return "location_edit";
        }
        locationRepository.save(location);
        return "redirect:/locations";
    }

    @GetMapping("/location/delete/{id}")
    public String deleteLocation(@PathVariable("id") Long id, Model model) throws IOException {
        Location location = locationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pogrešan ID"));
        locationRepository.delete(location);
        return "redirect:/locations";
    }
}