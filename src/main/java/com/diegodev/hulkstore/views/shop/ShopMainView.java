package com.diegodev.hulkstore.views.shop;

import com.diegodev.hulkstore.data.entity.shop.Product;
import com.diegodev.hulkstore.data.service.ProductService;
import com.diegodev.hulkstore.views.MainLayout;
import com.diegodev.hulkstore.views.component.ProductCard;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility.*;
import jakarta.annotation.security.PermitAll;

import java.util.List;


@PageTitle("Shop")
@Route(value = "shop", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
public class ShopMainView extends Main implements HasComponents, HasStyle {

    private final ProductService productService;
    private OrderedList imageContainer;

    public ShopMainView(ProductService productService) {
        this.productService = productService;
        constructUI();
    }

    private void constructUI() {
        addClassNames("shop-main-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Product Shop");
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
        Paragraph description = new Paragraph("Choose a product to buy");
        description.addClassNames(Margin.Bottom.XLARGE, Margin.Top.NONE, TextColor.SECONDARY);
        headerContainer.add(header, description);

        Select<String> sortBy = new Select<>();
        sortBy.setLabel("Sort by");
        sortBy.setItems("Name");
        sortBy.setValue("Name");

        imageContainer = new OrderedList();
        imageContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);

        container.add(headerContainer, sortBy);
        add(container, imageContainer);
        loadProducts();
    }

    private void loadProducts() {
        List<Product> products = productService.findAllProducts(null);
        products.forEach(product -> {
            ProductCard card = new ProductCard(product);
            if(product.isOutOfStock()) {
                card.addBadge("Out of stock", "badge error");
            } else {
                card.addBadge("Available", "badge success");
            }

            imageContainer.add(card);
            //card.addClickListener(event -> {
            //UI.getCurrent().navigate(RegridView.class);
        });
    }
}