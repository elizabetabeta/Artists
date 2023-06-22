package ba.sum.fpmoz.artists.controller;

import ba.sum.fpmoz.artists.repositories.PostRepository;
import ba.sum.fpmoz.artists.model.Post;
import ba.sum.fpmoz.artists.model.UserDetails;
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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class PostController {
    @Autowired
    PostRepository postRepository;

    private static String UPLOADED_FOLDER = "./uploads/";

    @GetMapping("/posts")
    public String showPosts (Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("post", new Post());
        model.addAttribute("posts", postRepository.findAll());
        model.addAttribute("added", false);
        model.addAttribute("activeLink", "Posts");
        return "posts";
    }

    @GetMapping("/profile")
    public String showProfile (Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("post", new Post());
        model.addAttribute("posts", postRepository.findAll());
        model.addAttribute("added", false);
        model.addAttribute("activeLink", "Posts");
        return "profile";
    }

    @PostMapping("/post/add")
    public String addPost (@Valid Post post, BindingResult result, @RequestParam("file") MultipartFile file, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("activeLink", "Posts");

        if (file.isEmpty()) {
            result.addError(new FieldError("post", "image", "Molimo odaberite sliku."));
        } else if (!file.getContentType().startsWith("image/")) {
            result.addError(new FieldError("post", "image", "Slika nije ispravnog formata."));
        } else {
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                Files.write(path, bytes);
                post.setImage(path.toString());
            } catch (IOException e) {
                result.addError(new FieldError("post", "image", "Problem s učitavanjem slike na poslužitelj."));
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("post", post);
            model.addAttribute("added", true);
            model.addAttribute("posts", postRepository.findAll());
            model.addAttribute("posts", postRepository.findAll());
            return "posts";
        }
        postRepository.save(post);
        return "redirect:/posts";
    }

    @GetMapping("/post/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();
        model.addAttribute("user", user);
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
        model.addAttribute("post", post);
        model.addAttribute("posts", postRepository.findAll());
        model.addAttribute("activeLink", "Posts");
        return "post_edit";
    }

    @PostMapping("post/edit/{id}")
    public String editPost (@PathVariable("id") Long id, @Valid Post post, BindingResult result, @RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            result.addError(new FieldError("post", "image", "Molimo odaberite sliku."));
        } else if (!file.getContentType().startsWith("image/")) {
            result.addError(new FieldError("post", "image", "Slika nije ispravnog formata."));
        } else {
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                Files.write(path, bytes);
                post.setImage(path.toString());
            } catch (IOException e) {
                result.addError(new FieldError("post", "image", "Problem s učitavanjem slike na poslužitelj."));
            }
        }

        if (result.hasErrors()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetails user = (UserDetails) auth.getPrincipal();
            //user.getUser().getId();
            model.addAttribute("user", user);
            model.addAttribute("post", post);
            model.addAttribute("posts", postRepository.findAll());
            model.addAttribute("activeLink", "Posts");
            return "post_edit";
        }
        postRepository.save(post);
        return "redirect:/posts";
    }

    @GetMapping("/post/delete/{id}")
    public String deletePost(@PathVariable("id") Long id, Model model) throws IOException {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pogrešan ID"));
        postRepository.delete(post);
        Files.delete(Path.of(post.getImage()));
        return "redirect:/posts";
    }
}