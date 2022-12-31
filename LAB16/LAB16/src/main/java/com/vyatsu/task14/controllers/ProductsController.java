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
    private int curent_page = 0;
    private int page_rows = 7;
    byte st = 2;
    boolean ad = false;
    @GetMapping
    public String showProductsList(Model model) {
        Product product = new Product();
        Filtr filtr = filter;
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
        String sub = filter.getSubstr();
        String us ="пользовватель";
        List<Integer> pages = new ArrayList<>();
        List<List<Product>> ppl = new ArrayList<>();
        List<Product> filpr = new ArrayList<Product>();
        for (Product p:
                productsService.getAllProducts()) {
            if(pattern.matcher(p.getTitle()).find()){
                filpr.add(p);
            }
        }
        filpr = filpr.stream().filter(p -> p.getPrice() > min && p.getPrice()< max).collect(Collectors.toList());
        int countofprod = filpr.size();
        for (int i = 0; i <= filpr.size() / page_rows; i++){
            ppl.add(new ArrayList<>());
            pages.add(i);
            if(i == filpr.size() / page_rows){
                for(int j = 0; j < filpr.size() - i * page_rows; j++){
                    ppl.get(i)
                            .add(filpr.get(i * page_rows + j));
                }
                break;
            }
            for (int j = 0; j < page_rows; j++){
                ppl.get(i)
                        .add(filpr.get(i * page_rows + j));
            }
        }
        if(ppl.get(ppl.size() - 1).size() == 0)
        {
            pages.remove(ppl.size() - 1);
        }
        List<Product> mostview = productsService.getAllProducts();
        mostview = mostview.stream().sorted((p1, p2) -> p2.getView() - p1.getView()).limit(3).collect(Collectors.toList());
        if(ad){curent_page = ppl.size()-1;}
        model.addAttribute("products", ppl.get(curent_page));
        model.addAttribute("product", product);
        model.addAttribute("substring",sub);
        model.addAttribute("minpr",min);
        model.addAttribute("maxpr", max);
        model.addAttribute("editid",editid);
        model.addAttribute("Filtr",filtr);
        model.addAttribute("ad",ad);
        model.addAttribute("current_page", curent_page);
        model.addAttribute("pages", pages);
        model.addAttribute("countofprod",countofprod);
        model.addAttribute("mostview",mostview);
        model.addAttribute("nameus",us);
        model.addAttribute("st",st);
        return "products";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute(value = "product")Product product) {
        productsService.add(product);
        ad = false;
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
    @GetMapping("/show/{id}")
    public String showOneProduct(Model model, @PathVariable(value = "id") Long id) {
        Product product = productsService.getById(id);
        product.setView(product.getView()+1);
        productsService.update(product);
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
        productsService.update(edproduct);
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
    @PostMapping("reset")
    public String res(Model model){
        pattern = Pattern.compile("");
        filter.setMincost("");
        filter.setMaxcost("");
        filter.setSubstr("");
        return "redirect:/products";
    }
    @GetMapping("/page/{id}")
    public  String viewPage(Model model, @PathVariable(value = "id") int id){
        curent_page = id;
        return "redirect:/products";
    }

}

