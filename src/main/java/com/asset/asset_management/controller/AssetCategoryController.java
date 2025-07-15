package com.asset.asset_management.controller; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asset.asset_management.interfaces.AssetCategoryRepository;

@RestController
@RequestMapping("/api/asset-categories")
@CrossOrigin(origins = "*") 
public class AssetCategoryController {

    @Autowired
    private AssetCategoryRepository categoryRepo;

}