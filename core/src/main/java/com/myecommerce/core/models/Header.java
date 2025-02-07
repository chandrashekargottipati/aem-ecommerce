package com.myecommerce.core.models;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.wcm.api.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Model(
    adaptables = SlingHttpServletRequest.class,
    adapters = {Header.class, ComponentExporter.class},
    resourceType = Header.RESOURCE_TYPE,
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Exporter(
    name = ExporterConstants.SLING_MODEL_EXPORTER_NAME,
    extensions = ExporterConstants.SLING_MODEL_EXTENSION
)
public class Header implements ComponentExporter {

    static final String RESOURCE_TYPE = "myecommerce/components/test";

    @ValueMapValue
    private String logoPath;

    @ValueMapValue
    private String logoAlt;

    @ValueMapValue
    private String logoLink;

    @ValueMapValue
    private String navigationRoot;

    @ValueMapValue
    private int navigationDepth;

    @ValueMapValue
    private boolean enableSearch;

    @ValueMapValue
    private String searchPlaceholder;

    @ValueMapValue
    private String searchResultsPage;

    @ValueMapValue
    private boolean enableCart;

    @ValueMapValue
    private String cartPage;

    @ValueMapValue
    private boolean enableLogin;

    @ValueMapValue
    private String loginPage;

    @ValueMapValue
    private String accountPage;

    @SlingObject
    private ResourceResolver resourceResolver;

    @ScriptVariable
    private Page currentPage;

    private List<NavigationItem> navigationItems;
    private int cartItemCount;
    private boolean isLoggedIn;
    private String userName;

    @PostConstruct
    protected void init() {
        // Initialize navigation
        navigationItems = buildNavigation();

        // Initialize cart count
        cartItemCount = getCartItemCount();

        // Initialize user status
        initializeUserStatus();
    }

    private List<NavigationItem> buildNavigation() {
        List<NavigationItem> items = new ArrayList<>();

        if (StringUtils.isNotEmpty(navigationRoot)) {
            Resource rootResource = resourceResolver.getResource(navigationRoot);
            if (rootResource != null) {
                Page rootPage = rootResource.adaptTo(Page.class);
                if (rootPage != null) {
                    items = buildNavigationItems(rootPage, navigationDepth);
                }
            }
        }

        return items;
    }

    private List<NavigationItem> buildNavigationItems(Page root, int depth) {
        List<NavigationItem> items = new ArrayList<>();

        if (depth > 0) {
            for (Iterator<Page> it = root.listChildren(); it.hasNext(); ) {
                Page child = it.next();
                if (!child.isHideInNav()) {
                    NavigationItem item = new NavigationItem();
                    item.setTitle(child.getNavigationTitle() != null ?
                                child.getNavigationTitle() : child.getTitle());
                    item.setPath(child.getPath());
                    item.setActive(currentPage.getPath().startsWith(child.getPath()));

                    if (depth > 1) {
                        item.setChildren(buildNavigationItems(child, depth - 1));
                    }

                    items.add(item);
                }
            }
        }

        return items;
    }

    private int getCartItemCount() {
        // Implement cart service integration
        // This is a placeholder - implement your cart service logic
        return 0;
    }

    private void initializeUserStatus() {
        // Implement user service integration
        // This is a placeholder - implement your user service logic
        isLoggedIn = false;
        userName = "";
    }

    // Getters
    public String getLogoPath() {
        return logoPath;
    }

    public String getLogoAlt() {
        return logoAlt;
    }

    public String getLogoLink() {
        return logoLink;
    }

    public List<NavigationItem> getNavigationItems() {
        return navigationItems;
    }

    public boolean isEnableSearch() {
        return enableSearch;
    }

    public String getSearchPlaceholder() {
        return searchPlaceholder;
    }

    public String getSearchResultsPage() {
        return searchResultsPage;
    }

    public boolean isEnableCart() {
        return enableCart;
    }

    public String getCartPage() {
        return cartPage;
    }



    public boolean isEnableLogin() {
        return enableLogin;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public String getAccountPage() {
        return accountPage;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String getExportedType() {
        return RESOURCE_TYPE;
    }
}
