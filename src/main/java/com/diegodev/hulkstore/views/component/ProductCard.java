package com.diegodev.hulkstore.views.component;

import com.diegodev.hulkstore.model.Product;
import com.diegodev.hulkstore.service.ShopService;
import com.diegodev.hulkstore.utils.NotificationHelper;
import com.diegodev.hulkstore.views.shop.CheckoutFormView;
import com.diegodev.hulkstore.views.util.AuthenticationUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.theme.lumo.LumoUtility.*;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class ProductCard extends ListItem {

    private final ShopService service;
    @Setter
    private Integer productId;
    private final Div div;

    public ProductCard(ShopService service) {
        this.service = service;
        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM,
                BorderRadius.LARGE);

        div = new Div();
        div.addClassNames(Background.CONTRAST, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                Margin.Bottom.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);
        div.setHeight("160px");
        add(div);
    }

    public void addHeader(String productName) {
        Span header = new Span();
        header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        header.setText(productName);
        add(header);
    }

    public void addSubtitle(String productCategory) {
        Span subtitle = new Span();
        subtitle.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subtitle.setText(productCategory);
        add(subtitle);
    }

    public void addDescription(String productDescription) {
        Paragraph description = new Paragraph(productDescription);
        description.addClassName(Margin.Vertical.MEDIUM);
        add(description);
    }

    public void addButtonLayout() {
        Product product = service.getProductById(productId);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        IntegerField quantityField = new IntegerField();
        quantityField.setValue(1);
        quantityField.setStepButtonsVisible(true);
        quantityField.setMin(1);
        quantityField.setMax(product.getStock().intValue());
        quantityField.setWidth("100px");

        Button buyButton = new Button("Buy", new Icon(VaadinIcon.MONEY));
        buyButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        buyButton.addClickListener(buttonClickEvent -> {
            AuthenticationUtil.doIfAuthenticated(user -> {
                service.addToCart(quantityField.getValue(), product, user);
                UI.getCurrent().navigate(CheckoutFormView.class);
            });
        });

        Button addToCartButton = new Button("Add to Cart", new Icon(VaadinIcon.CART));
        addToCartButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        addToCartButton.addClickListener(buttonClickEvent -> {
            AuthenticationUtil.doIfAuthenticated(user -> {
                service.addToCart(quantityField.getValue(), product, user);
                NotificationHelper.showNotification("Product added to your shopping cart!", NotificationVariant.LUMO_SUCCESS);
            });
        });

        buttonLayout.add(buyButton, addToCartButton, quantityField);
        add(buttonLayout);
    }

    public void setProductImage(String productImageUrl) {
        Image image = new Image();
        image.setWidth("100%");
        image.setSrc(productImageUrl);
        div.add(image);
    }

    public void addBadge(String label, String type) {
        Span badge = new Span();
        badge.getElement().setAttribute("theme", type);
        badge.setText(label);
        add(badge);
    }
}