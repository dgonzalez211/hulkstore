package com.diegodev.hulkstore.views.admin;

import com.diegodev.hulkstore.model.Order;
import com.diegodev.hulkstore.service.OrderService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@RolesAllowed("ADMIN")
@Component
@Scope("prototype")
@Route(value = "order-list", layout = AdminPanelView.class)
@PageTitle("Orders List | HulkStore")
public class OrderListView extends VerticalLayout {
    Grid<Order> grid = new Grid<>(Order.class);
    OrderService service;

    public OrderListView(OrderService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        add(getToolbar(), getContent());
        updateList();
    }

    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout(grid);
        content.setFlexGrow(2, grid);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureGrid() {
        grid.addClassNames("orders-grid");
        grid.setSizeFull();
        grid.setColumns("createdDate", "totalPrice");
        grid.addColumn(order -> order.getUser().getName()).setHeader("User").setSortable(true);
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private HorizontalLayout getToolbar() {
        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void updateList() {
        grid.setItems(service.findAllOrders());
    }
}