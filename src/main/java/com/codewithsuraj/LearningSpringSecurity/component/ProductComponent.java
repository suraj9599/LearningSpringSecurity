package com.codewithsuraj.LearningSpringSecurity.component;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductComponent {

    List<Product> products = new ArrayList<>(List.of(
            new Product(1,"Book"),
            new Product(2,"Pen")
    ));
    @GetMapping("/health-check")
    public String healthCheck(){
        return "Server is running...";
    };

    @GetMapping("/product")
    public List<Product> getAllProducts(){
        return products.stream().toList();
    }

    @PostMapping("/product")
    public boolean addProduct(@RequestBody Product product){
        return products.add(product);
    }
    @GetMapping("/about")
    public String getAbout(HttpServletRequest request){
        return "About Me  " + request.getSession().getId();
    }
    @GetMapping("/home")
    public String getHome(HttpServletRequest request){
        return "Home " + request.getSession().getId();
    }
    @GetMapping("csrf")
    public CsrfToken getCsrfToken(HttpServletRequest request){
        return (CsrfToken) request.getAttribute("_csrf");
    }
}
