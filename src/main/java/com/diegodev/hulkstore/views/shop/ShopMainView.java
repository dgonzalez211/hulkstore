package com.diegodev.hulkstore.views.shop;

import com.diegodev.hulkstore.model.Product;
import com.diegodev.hulkstore.service.ShopService;
import com.diegodev.hulkstore.utils.NotificationHelper;
import com.diegodev.hulkstore.views.MainLayout;
import com.diegodev.hulkstore.views.component.ProductCard;
import com.diegodev.hulkstore.views.util.AuthenticationUtil;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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

    private final ShopService service;
    private OrderedList imageContainer;

    public ShopMainView(ShopService service) {
        this.service = service;
        constructUI();
        loadProducts();
    }

    private void constructUI() {
        addClassNames("shop-main-view");
        addClassNames(MaxWidth.SCREEN_MEDIUM, Margin.Horizontal.MEDIUM, Padding.Bottom.SMALL, Padding.Horizontal.SMALL);
        setMaxWidth("1600px");

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Product Shop");
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
        Paragraph description = new Paragraph("Choose a product to buy");
        description.addClassNames(Margin.Bottom.XLARGE, Margin.Top.NONE, TextColor.SECONDARY);
        headerContainer.add(header, description);

        Button cartButton = new Button("Checkout", new Icon(VaadinIcon.CART));
        cartButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        cartButton.setHeight("60px");
        cartButton.addClickListener(buttonClickEvent -> {
            AuthenticationUtil.doIfAuthenticated(user -> {
                if (service.isUserCartEmpty(user)) {
                    NotificationHelper.showNotification("Your car is empty, add items first!", NotificationVariant.LUMO_ERROR);
                    return;
                }
                UI.getCurrent().navigate(CheckoutFormView.class);
            });
        });

        imageContainer = new OrderedList();
        imageContainer.addClassNames(Gap.MEDIUM, Display.INLINE_FLEX, ListStyleType.NONE, Margin.SMALL, Padding.SMALL);
        imageContainer.setMaxWidth("1600px");

        container.add(headerContainer, cartButton);
        add(container, imageContainer);
    }

    private void loadProducts() {
        List<Product> products = service.findAllProducts(null);
        products.forEach(product -> {
            ProductCard card = new ProductCard(service);
            card.addHeader(product.getName());
            card.addSubtitle(product.getCategory());
            card.addDescription(product.getDescription());
            card.setProductImage(product.getImageURL());
            card.setProductId(product.getId());

            if(service.isOutOfStock(product.getId())) {
                card.addBadge("Out of stock", "badge error");
            } else {
                card.addBadge("Available", "badge success");
                card.addButtonLayout();
            }

            imageContainer.add(card);
        });
    }
}