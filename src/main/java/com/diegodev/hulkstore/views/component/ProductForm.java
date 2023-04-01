package com.diegodev.hulkstore.views.component;

import com.diegodev.hulkstore.data.entity.shop.Product;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class ProductForm extends FormLayout {

    private Product product;
    TextField name = new TextField("Name");
    TextField code = new TextField("Code");
    TextField description = new TextField("Description");
    TextField category = new TextField("Category");
    NumberField stock = new NumberField("Stock");
    NumberField price = new NumberField("Price");
    TextField productImageUrl = new TextField("Image URL");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    // Other fields omitted
    Binder<Product> binder = new BeanValidationBinder<>(Product.class);

    public ProductForm() {
        addClassName("product-form");
        binder.bindInstanceFields(this);

        add(name, code, description, category, stock, price, productImageUrl, createButtonsLayout());
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, product)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(product);
            fireEvent(new SaveEvent(this, product));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public void setProduct(Product contact) {
        this.product = contact;
        binder.readBean(contact);
    }

    // Events
    public static abstract class ProductFormEvent extends ComponentEvent<ProductForm> {
        private final Product contact;

        protected ProductFormEvent(ProductForm source, Product contact) {
            super(source, false);
            this.contact = contact;
        }

        public Product getProduct() {
            return contact;
        }
    }

    public static class SaveEvent extends ProductFormEvent {
        SaveEvent(ProductForm source, Product contact) {
            super(source, contact);
        }
    }

    public static class DeleteEvent extends ProductFormEvent {
        DeleteEvent(ProductForm source, Product contact) {
            super(source, contact);
        }

    }

    public static class CloseEvent extends ProductFormEvent {
        CloseEvent(ProductForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}