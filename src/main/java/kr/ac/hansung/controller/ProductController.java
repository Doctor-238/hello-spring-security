package kr.ac.hansung.controller;

import jakarta.validation.Valid;
import kr.ac.hansung.dto.ProductDto;
import kr.ac.hansung.entity.Product;
import kr.ac.hansung.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public String list(@RequestParam(defaultValue = "") String keyword,
                       @RequestParam(defaultValue = "0") int page,
                       Model model) {
        Page<Product> productPage = productService.findAll(keyword, PageRequest.of(page, 5, Sort.by("createdAt").descending()));
        model.addAttribute("productPage", productPage);
        model.addAttribute("keyword", keyword);
        return "products/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "products/detail";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("product", new ProductDto());
        return "products/add";
    }

    @PostMapping
    public String save(@ModelAttribute ProductDto dto) {
        productService.save(dto);
        return "redirect:/products";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Product product = productService.findById(id);
        ProductDto dto = new ProductDto();
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());
        dto.setStock(product.getStock());
        model.addAttribute("product", dto);
        model.addAttribute("productId", id);
        return "products/edit";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("product") ProductDto dto,
                         BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("productId", id);
            return "products/edit";
        }
        productService.updateProduct(id, dto);
        return "redirect:/products/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        productService.deleteById(id);
        return "redirect:/products";
    }
}
