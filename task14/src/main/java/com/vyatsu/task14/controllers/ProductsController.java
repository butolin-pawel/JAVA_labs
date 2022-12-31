package com.vyatsu.task14.controllers;

import com.vyatsu.task14.entities.Filtr;
import com.vyatsu.task14.entities.Product;
import com.vyatsu.task14.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductsController {
    private ProductsService productsService;

    @Autowired
    public void setProductsService(ProductsService productsService) {
        this.productsService = productsService;
    }
    public Long editid;
    private Pattern pattern = Pattern.compile("");
    public Filtr filter = new Filtr();
    boolean ad = false;
    byte st = 2;

    boolean filtered = false;
    @GetMapping
    public String showProductsList(Model model) {
        Product product = new Product();
        int min;
        int max;
        if(filter.getMaxcost() == "" || filter.getMincost() =="") {
            min = 0;
            max = Integer.MAX_VALUE;
            model.addAttribute("mincost","");
            model.addAttribute("maxcost","");
        }
        else{
            min = Integer.parseInt(filter.getMincost());
            max = Integer.parseInt(filter.getMaxcost());
            model.addAttribute("mincost",min);
            model.addAttribute("maxcost",max);
        }
        Filtr filtr = filter;
        String sub = filter.getSubstr();
        List<Product> filpr = new ArrayList<Product>();
        for (Product p:
             productsService.getAllProducts()) {
            if(pattern.matcher(p.getTitle()).find()){
                filpr.add(p);
            }
        }
        filpr = filpr.stream().filter(p -> p.getPrice() > min && p.getPrice()< max).collect(Collectors.toList());
        model.addAttribute("products", filpr);
        model.addAttribute("product", product);
        model.addAttribute("substring",sub);
        model.addAttribute("editid",editid);

        model.addAttribute("Filtr",filtr);
        model.addAttribute("ad",ad);
        model.addAttribute("st",st);
        return "products";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute(value = "product")Product product) {
        productsService.add(product);
        ad = false;
        return "redirect:/products";
    }

    @GetMapping("/show/{id}")
    public String showOneProduct(Model model, @PathVariable(value = "id") Long id) {
        Product product = productsService.getById(id);
        model.addAttribute("product", product);
        return "product-page";
    }
    @GetMapping("/del/{id}")
    public String delProduct(Model model, @PathVariable(value = "id") Long id){
        Product product = productsService.getById(id);
        productsService.delete(product);
        return "redirect:/products";
    }
    @GetMapping("/edit/{id}")
    public String editProduct(Model model, @PathVariable(value = "id") Long id){
            editid = id;
            return "redirect:/products";
    }
    @GetMapping("/edit")
    public String editfin(Model model, @ModelAttribute(value = "product1") Product product){
       Product edproduct = productsService.getById(product.getId());
       edproduct.setTitle(product.getTitle());
       edproduct.setPrice(product.getPrice());
       editid = 0l;
       return "redirect:/products";
    }
    @PostMapping("filtr")
    public String filter(Model model, @ModelAttribute(value = "fil")Filtr filtr) {
        pattern = Pattern.compile(filtr.getSubstr());

        if(filtr.getMaxcost() == "" || filtr.getMincost() =="") {
            filter.setMaxcost("");
            filter.setMincost("");
        }
        else{
            filter.setMaxcost(filtr.getMaxcost());
            filter.setMincost(filtr.getMincost());
        }
            filter.setSubstr(filtr.getSubstr());
            return "redirect:/products";
        }
        @GetMapping("/add")
        public String showform(Model model){
        ad = true;
            return "redirect:/products";
        }
    @GetMapping("/set1")
    public String show1(Model model){
        st = 1;
        return "redirect:/products";
    }
    @GetMapping("/set2")
    public String show2(Model model){
        st = 2;
        return "redirect:/products";
    }
    @GetMapping("/set3")
    public String show3(Model model){
        st = 3;
        return "redirect:/products";
    }
        @PostMapping("reset")
    public String res(Model model){
        pattern = Pattern.compile("");
        filter.setMincost("");
        filter.setMaxcost("");
        filter.setSubstr("");
        filtered = false;
            return "redirect:/products";
        }

    }
