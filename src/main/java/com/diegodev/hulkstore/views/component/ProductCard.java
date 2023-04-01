package com.diegodev.hulkstore.views.component;

import com.diegodev.hulkstore.data.entity.shop.Product;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class ProductCard extends ListItem {

    private final Product product;
    private final Div div;

    public ProductCard(Product product) {
        this.product = product;
        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM,
                BorderRadius.LARGE);

        div = new Div();
        div.addClassNames(Background.CONTRAST, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                Margin.Bottom.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);
        div.setHeight("160px");
        add(div);

        addHeader();
        addSubtitle();
        addDescription();
        addProductImage();

        if(!product.isOutOfStock()) {
            addButtonLayout();
        }

    }

    private void addHeader() {
        Span header = new Span();
        header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        header.setText(product.getName());
        add(header);
    }

    private void addSubtitle() {
        Span subtitle = new Span();
        subtitle.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subtitle.setText(product.getCategory());
        add(subtitle);
    }

    private void addDescription() {
        Paragraph description = new Paragraph(product.getDescription());
        description.addClassName(Margin.Vertical.MEDIUM);
        add(description);
    }

    private void addButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();

        Button buyButton = new Button("Buy", new Icon(VaadinIcon.MONEY));
        buyButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        buyButton.addClickListener(buttonClickEvent -> {
            if (!product.isOutOfStock()){
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication != null) {

                }
            }
        });

        Button addToCartButton = new Button("Add to Cart", new Icon(VaadinIcon.CART));
        addToCartButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        buttonLayout.add(buyButton, addToCartButton);
        add(buttonLayout);
    }

    private void addProductImage() {
        Image image = new Image();
        image.setWidth("100%");
        image.setAlt(product.getName());
        if(product.getProductImageUrl() != null && !product.getProductImageUrl().isEmpty()) {
            image.setSrc(product.getProductImageUrl());
        } else {
            image.setSrc("https://www.nicepng.com/png/detail/304-3048415_business-advice-product-icon-png.png");
        }

        div.add(image);
    }

    public void addBadge(String label, String type) {
        Span badge = new Span();
        badge.getElement().setAttribute("theme", type);
        badge.setText(label);
        add(badge);
    }

    public void addBadge(String label) {
        Span badge = new Span();
        badge.getElement().setAttribute("theme", "badge");
        badge.setText(label);
        add(badge);
    }
}