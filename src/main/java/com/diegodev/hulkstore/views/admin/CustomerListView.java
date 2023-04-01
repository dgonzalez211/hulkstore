package com.diegodev.hulkstore.views.admin;

import com.diegodev.hulkstore.data.entity.shop.Customer;
import com.diegodev.hulkstore.data.service.CustomerService;
import com.diegodev.hulkstore.views.MainLayout;
import com.diegodev.hulkstore.views.component.CustomerForm;
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
@Route(value = "customer-list", layout = MainLayout.class)
@PageTitle("Customers List | HulkStore")
public class CustomerListView extends VerticalLayout {
    Grid<Customer> grid = new Grid<>(Customer.class);
    TextField filterText = new TextField();
    CustomerForm form;
    CustomerService service;

    public CustomerListView(CustomerService service) {
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
        form = new CustomerForm();
        form.setWidth("25em");
        form.addListener(CustomerForm.SaveEvent.class, this::saveCustomer);
        form.addListener(CustomerForm.DeleteEvent.class, this::deleteCustomer);
        form.addListener(CustomerForm.CloseEvent.class, e -> closeEditor());
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns("username", "name", "email", "phone");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event ->
                editCustomer(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        HorizontalLayout toolbar = new HorizontalLayout(filterText);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void editCustomer(Customer contact) {
        if (contact == null) {
            closeEditor();
        } else {
            form.setCustomer(contact);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void saveCustomer(CustomerForm.SaveEvent event) {
        service.saveCustomer(event.getCustomer());
        updateList();
        closeEditor();
    }

    private void deleteCustomer(CustomerForm.DeleteEvent event) {
        service.deleteCustomer(event.getCustomer());
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setCustomer(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(service.findAllCustomers(filterText.getValue()));
    }
}