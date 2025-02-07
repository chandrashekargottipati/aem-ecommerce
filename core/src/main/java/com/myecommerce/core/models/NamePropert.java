package com.myecommerce.core.models;

// ProductCollectionModel.java (Sling Model)

//If you want to reuse the list item from core components

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class)
class ProductCollectionModel {

    private static final Logger LOG = LoggerFactory.getLogger(ProductCollectionModel.class);

    @ValueMapValue
    @Default(values = "Outdoor Collection")
    private String title;

    @ValueMapValue
    private String filterType;

    @ValueMapValue
    private String sortType;

    @ValueMapValue
    private String[] productPaths;

    private List<Product> products; // Custom Product class

    @Inject
    private SlingHttpServletRequest request;

    @PostConstruct
    protected void init() {
        products = new ArrayList<>();
        if (productPaths != null) {
            for (String path : productPaths) {
                Resource productResource = request.getResourceResolver().getResource(path);
                if (productResource != null) {
                    Product product = productResource.adaptTo(Product.class); // Adapt to your Product model
                    if (product != null) {
                        products.add(product);
                    } else {
                        LOG.warn("Could not adapt resource at {} to Product model", path);
                    }
                } else {
                    LOG.warn("Product resource not found at {}", path);
                }
            }
        }

        // Apply sorting and filtering logic here based on filterType and sortType

    }


    // Getters
    public String getTitle() {
        return title;
    }

    public String getFilterType() {
        return filterType;
    }

    public String getSortType() {
        return sortType;
    }

    public List<Product> getProducts() {
        return products;
    }


    //Inner Class for Product
    public class Product{

        @ValueMapValue
        private String name;

        @ValueMapValue
        private String price;

        @ValueMapValue
        private String image;


        public String getName() {
            return name;
        }

        public String getPrice() {
            return price;
        }

        public String getImage() {
            return image;
        }

    }

}