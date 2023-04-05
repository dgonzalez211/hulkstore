package com.diegodev.hulkstore.views.shop;

import com.diegodev.hulkstore.dto.cart.CartDto;
import com.diegodev.hulkstore.dto.cart.CartItemDto;
import com.diegodev.hulkstore.service.ShopService;
import com.diegodev.hulkstore.utils.NotificationHelper;
import com.diegodev.hulkstore.views.MainLayout;
import com.diegodev.hulkstore.views.util.AuthenticationUtil;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.*;
import jakarta.annotation.security.PermitAll;

import java.text.NumberFormat;
import java.util.*;

@PageTitle("Checkout Form")
@Route(value = "checkout-form", layout = MainLayout.class)
@PermitAll
public class CheckoutFormView extends Div {

    private final ShopService service;

    private static final Set<String> states = new LinkedHashSet<>();
    private static final Set<String> countries = new LinkedHashSet<>();

    static {
        states.add("Bogota");
        countries.add("Colombia");
    }

    public CheckoutFormView(ShopService service) {
        this.service = service;
        addClassNames("checkout-form-view");
        addClassNames(Display.FLEX, FlexDirection.ROW, Height.FULL, AlignItems.START);

        VerticalLayout content = new VerticalLayout();
        VerticalLayout asideLayout = new VerticalLayout();
        content.addClassNames(Display.GRID, Gap.XLARGE, AlignItems.START, JustifyContent.CENTER, MaxWidth.SCREEN_MEDIUM,
                Margin.Horizontal.SMALL, Padding.Bottom.SMALL, Padding.Horizontal.SMALL);
        asideLayout.setWidth("500px");

        content.add(createCheckoutForm());
        asideLayout.add(createAside(), createEmptyCarButton());
        add(content, asideLayout);
    }

    private Component createCheckoutForm() {
        Section checkoutForm = new Section();
        checkoutForm.addClassNames(Display.FLEX, FlexDirection.COLUMN, Flex.GROW);

        H2 header = new H2("Checkout");
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
        Paragraph note = new Paragraph("All fields are required unless otherwise noted");
        note.addClassNames(Margin.Bottom.XLARGE, Margin.Top.NONE, TextColor.SECONDARY);
        checkoutForm.add(header, note);

        checkoutForm.add(createPersonalDetailsSection());
        checkoutForm.add(createPaymentInformationSection());
        checkoutForm.add(new Hr());
        checkoutForm.add(createFooter());

        return checkoutForm;
    }

    private Section createPersonalDetailsSection() {
        Section personalDetails = new Section();
        personalDetails.addClassNames(Display.FLEX, FlexDirection.COLUMN, Margin.Bottom.XLARGE, Margin.Top.MEDIUM);

        Paragraph stepOne = new Paragraph("Checkout 1/2");
        stepOne.addClassNames(Margin.NONE, FontSize.SMALL, TextColor.SECONDARY);

        H3 header = new H3("Personal details");
        header.addClassNames(Margin.Bottom.MEDIUM, Margin.Top.SMALL, FontSize.XXLARGE);

        TextField name = new TextField("Name");
        name.setRequiredIndicatorVisible(true);
        name.setPattern("[\\p{L} \\-]+");
        name.addClassNames(Margin.Bottom.SMALL);

        EmailField email = new EmailField("Email address");
        email.setRequiredIndicatorVisible(true);
        email.addClassNames(Margin.Bottom.SMALL);

        TextField phone = new TextField("Phone number");
        phone.setRequiredIndicatorVisible(true);
        phone.setPattern("[\\d \\-\\+]+");
        phone.addClassNames(Margin.Bottom.SMALL);

        Checkbox rememberDetails = new Checkbox("Remember personal details for next time");
        rememberDetails.addClassNames(Margin.Top.SMALL);

        personalDetails.add(stepOne, header, name, email, phone, rememberDetails);
        return personalDetails;
    }

    private Component createPaymentInformationSection() {
        Section paymentInfo = new Section();
        paymentInfo.addClassNames(Display.FLEX, FlexDirection.COLUMN, Margin.Bottom.XLARGE, Margin.Top.MEDIUM);

        Paragraph stepThree = new Paragraph("Checkout 2/2");
        stepThree.addClassNames(Margin.NONE, FontSize.SMALL, TextColor.SECONDARY);

        H3 header = new H3("Payment information");
        header.addClassNames(Margin.Bottom.MEDIUM, Margin.Top.SMALL, FontSize.XXLARGE);

        TextField cardHolder = new TextField("Cardholder name");
        cardHolder.setRequiredIndicatorVisible(true);
        cardHolder.setPattern("[\\p{L} \\-]+");
        cardHolder.addClassNames(Margin.Bottom.SMALL);

        Div subSectionOne = new Div();
        subSectionOne.addClassNames(Display.FLEX, FlexWrap.WRAP, Gap.MEDIUM);

        TextField cardNumber = new TextField("Card Number");
        cardNumber.setRequiredIndicatorVisible(true);
        cardNumber.setPattern("[\\d ]{12,23}");
        cardNumber.addClassNames(Margin.Bottom.SMALL);

        TextField securityCode = new TextField("Security Code");
        securityCode.setRequiredIndicatorVisible(true);
        securityCode.setPattern("[0-9]{3,4}");
        securityCode.addClassNames(Flex.GROW, Margin.Bottom.SMALL);
        securityCode.setHelperText("What is this?");

        subSectionOne.add(cardNumber, securityCode);

        Div subSectionTwo = new Div();
        subSectionTwo.addClassNames(Display.FLEX, FlexWrap.WRAP, Gap.MEDIUM);

        Select<String> expirationMonth = new Select<>();
        expirationMonth.setLabel("Expiration month");
        expirationMonth.setRequiredIndicatorVisible(true);
        expirationMonth.setItems("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");

        Select<String> expirationYear = new Select<>();
        expirationYear.setLabel("Expiration year");
        expirationYear.setRequiredIndicatorVisible(true);
        expirationYear.setItems("23", "24", "25", "26", "27", "28", "29");

        subSectionTwo.add(expirationMonth, expirationYear);

        paymentInfo.add(stepThree, header, cardHolder, subSectionOne, subSectionTwo);
        return paymentInfo;
    }

    private Footer createFooter() {
        Footer footer = new Footer();
        footer.addClassNames(Display.FLEX, AlignItems.CENTER, JustifyContent.BETWEEN, Margin.Vertical.MEDIUM);

        Button cancel = new Button("Cancel order");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        cancel.addClickListener(buttonClickEvent -> {
            UI.getCurrent().navigate(ShopMainView.class);
        });

        Button pay = new Button("Pay securely", new Icon(VaadinIcon.LOCK));
        pay.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        pay.addClickListener(buttonClickEvent -> {
            AuthenticationUtil.doIfAuthenticated(user -> {
                service.placeOrder(user);
                NotificationHelper.showNotification("Thanks for your purchase!", NotificationVariant.LUMO_SUCCESS);
                UI.getCurrent().navigate(ShopMainView.class);
            });
        });

        footer.add(cancel, pay);
        return footer;
    }

    private Aside createAside() {
        Aside aside = new Aside();
        aside.addClassNames(Background.CONTRAST_5, BoxSizing.BORDER, Padding.LARGE, BorderRadius.LARGE,
                Position.STICKY, Margin.Horizontal.SMALL, Padding.Bottom.SMALL, Padding.Horizontal.SMALL);
        aside.setWidth("500px");
        Header headerSection = new Header();
        headerSection.addClassNames(Display.FLEX, AlignItems.CENTER, JustifyContent.BETWEEN, Margin.Bottom.MEDIUM);
        H3 header = new H3("Order");
        header.addClassNames(Margin.NONE);
        Button edit = new Button("Edit");
        edit.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        headerSection.add(header, edit);

        UnorderedList ul = new UnorderedList();
        ul.addClassNames(ListStyleType.NONE, Margin.NONE, Padding.NONE, Display.FLEX, FlexDirection.COLUMN, Gap.MEDIUM);

        AuthenticationUtil.doIfAuthenticated(user -> {
            CartDto cartDto = service.listCartItems(user);
            List<CartItemDto> cartItems = cartDto.getCartItems();
            List<CartItemDto> listWithoutDuplicates = new ArrayList<>(new HashSet<>(cartItems));
            listWithoutDuplicates.forEach(cartItem -> ul.add(
                    createListItem(
                            cartItem.getProduct().getName(),
                            cartItem.getProduct().getDescription(),
                            cartItem.getProduct().getPrice(),
                            cartItem.getQuantity()
                    )
            ));
        });

        aside.add(headerSection, ul);
        return aside;
    }

    private ListItem createListItem(String primary, String secondary, Double price, Integer quantity) {
        ListItem item = new ListItem();
        item.addClassNames(Display.FLEX, JustifyContent.BETWEEN);

        Div subSection = new Div();
        subSection.addClassNames(Display.FLEX, FlexDirection.COLUMN);
        Span productQuantity = new Span(" x" + quantity);
        productQuantity.getElement().getThemeList().add("badge contrast");
        subSection.add(new Span(new Span(primary), productQuantity));
        Span secondarySpan = new Span(secondary);
        secondarySpan.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subSection.add(secondarySpan);

        Locale colombiaLocale = new Locale("es", "CO");
        NumberFormat priceFormat = NumberFormat.getCurrencyInstance(colombiaLocale);

        Span priceSpan = new Span(priceFormat.format(price * quantity));

        item.add(subSection, priceSpan);
        return item;
    }

    private Button createEmptyCarButton() {
        Button emptyCartButton = new Button("Empty Car", new Icon(VaadinIcon.TRASH));
        emptyCartButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        emptyCartButton.setHeight("60px");
        emptyCartButton.addClickListener(buttonClickEvent -> {
            AuthenticationUtil.doIfAuthenticated(user -> {
                service.emptyUserCart(user);
                NotificationHelper.showNotification("Items from shopping cart have been deleted!", NotificationVariant.LUMO_ERROR);
                UI.getCurrent().navigate(ShopMainView.class);
            });
        });
        return emptyCartButton;
    }
}