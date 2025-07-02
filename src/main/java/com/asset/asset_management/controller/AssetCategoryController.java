package com.asset.asset_management.controller; // Ensure this matches your actual folder structure

// import java.util.List; // Not needed if getAllCategories is removed
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.GetMapping; // Not needed if getAllCategories is removed
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import com.asset.asset_management.entities.AssetCategory; // Only needed if you perform other operations returning AssetCategory
import com.asset.asset_management.interfaces.AssetCategoryRepository;

@RestController
@RequestMapping("/api/asset-categories")
@CrossOrigin(origins = "*") // allow HTML to call this
public class AssetCategoryController {

    @Autowired
    private AssetCategoryRepository categoryRepo;

    // IMPORTANT: We are REMOVING or commenting out this method
    // because MasterDataController will now handle GET /api/asset-categories
    /*
    @GetMapping
    public List<AssetCategory> getAllCategories() {
        return categoryRepo.findAll();
    }
    */

    // You can add other specific CRUD operations for AssetCategory here, e.g.:
    // @GetMapping("/{id}")
    // public AssetCategory getCategoryById(@PathVariable Long id) {
    //     return categoryRepo.findById(id).orElse(null); // Or throw an exception
    // }

    // @PostMapping
    // public AssetCategory createCategory(@RequestBody AssetCategory category) {
    //     return categoryRepo.save(category);
    // }

    // @PutMapping("/{id}")
    // public AssetCategory updateCategory(@PathVariable Long id, @RequestBody AssetCategory categoryDetails) {
    //     // Implementation for updating a category
    //     // Make sure to handle the case where the category with 'id' doesn't exist
    //     return categoryRepo.save(categoryDetails);
    // }

    // @DeleteMapping("/{id}")
    // public void deleteCategory(@PathVariable Long id) {
    //     categoryRepo.deleteById(id);
    // }
}