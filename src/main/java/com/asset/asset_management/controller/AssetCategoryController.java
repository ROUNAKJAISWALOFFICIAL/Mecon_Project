package com.asset.asset_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asset.asset_management.entities.AssetCategory;
import com.asset.asset_management.interfaces.AssetCategoryRepository;

@RestController
@RequestMapping("/api/asset-categories")
@CrossOrigin(origins = "*") // allow HTML to call this
public class AssetCategoryController {

    @Autowired
    private AssetCategoryRepository categoryRepo;

    @GetMapping
    public List<AssetCategory> getAllCategories() {
        return categoryRepo.findAll();
    }
}

