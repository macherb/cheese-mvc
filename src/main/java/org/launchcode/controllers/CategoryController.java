package org.launchcode.controllers;

import org.launchcode.models.Category;
import org.launchcode.models.data.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryDao categoryDao;

    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("categories", categoryDao.findAll());
        model.addAttribute("title", Category.titleList);

        return "category/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddCategoryForm(Model model) {

        model.addAttribute(new Category());
        model.addAttribute("title", "Add Category");

        return "category/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid Category category, Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("title", Category.titleAdd);
            return "category/add";
        }
        categoryDao.save(category);
        return "redirect:";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveCategoryForm(Model model) {
        model.addAttribute("categories", categoryDao.findAll());
        model.addAttribute("title", Category.titleRemove);
        return "category/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveCategoryForm(Model model, @RequestParam int[] categoryIds) {
        for (int categoryId : categoryIds) {
            final Category category = categoryDao.findOne(categoryId);
            categoryDao.delete(category);
        }
        model.addAttribute("categories", categoryDao.findAll());
        model.addAttribute("title", Category.titleRemove);
        return "redirect:";//"category/confirmRemove";
    }

    @RequestMapping(value = "edit/{categoryId}", method = RequestMethod.GET)
    public String displayEditForm(Model model, @PathVariable int categoryId) {
        model.addAttribute("title", "Edit Category");
        Category category = categoryDao.findOne(categoryId);
        model.addAttribute(category);

        return "category/edit";
    }

    @RequestMapping(value = "edit/{categoryId}", method = RequestMethod.POST)
    public String processEditForm(Model model,
                                  @ModelAttribute  @Valid Category category,
                                  Errors errors,
                                  @PathVariable int categoryId) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Edit Category");
            model.addAttribute(category);
            return "category/edit";
        }

        Category newCategory = categoryDao.findOne(categoryId);

        newCategory.setName(category.getName());

        categoryDao.save(newCategory);
        model.addAttribute("categories", categoryDao.findAll());
        model.addAttribute("title", Category.titleList);
        return "category/index";//return "redirect:";//return "category/edit";
    }
}
