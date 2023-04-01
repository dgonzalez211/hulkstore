package com.diegodev.hulkstore.views.admin;


import com.diegodev.hulkstore.data.entity.shop.Product;
import com.diegodev.hulkstore.data.service.ProductService;
import com.diegodev.hulkstore.views.MainLayout;
import com.diegodev.hulkstore.views.component.ProductForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@RolesAllowed("ADMIN")
@Component
@Scope("prototype")
@Route(value = "product-list", layout = MainLayout.class)
@PageTitle("Products List | HulkStore")
public class ProductListView extends VerticalLayout {
    Grid<Product> grid = new Grid<>(Product.class);
    TextField filterText = new TextField();
    ProductForm form;
    ProductService service;

    public ProductListView(ProductService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateList();
        closeEditor();
    }

    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new ProductForm();
        form.setWidth("25em");
        form.addListener(ProductForm.SaveEvent.class, this::saveProduct);
        form.addListener(ProductForm.DeleteEvent.class, this::deleteProduct);
        form.addListener(ProductForm.CloseEvent.class, e -> closeEditor());
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns("code", "name", "description", "category", "stock", "price");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event ->
                editProduct(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addProductButton = new Button("Add product");
        addProductButton.addClickListener(click -> addProduct());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addProductButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void editProduct(Product contact) {
        if (contact == null) {
            closeEditor();
        } else {
            form.setProduct(contact);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void saveProduct(ProductForm.SaveEvent event) {
        service.saveProduct(event.getProduct());
        updateList();
        closeEditor();
    }

    private void deleteProduct(ProductForm.DeleteEvent event) {
        service.deleteProduct(event.getProduct());
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setProduct(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addProduct() {
        grid.asSingleSelect().clear();
        editProduct(new Product());
    }

    private void updateList() {
        grid.setItems(service.findAllProducts(filterText.getValue()));
    }
}