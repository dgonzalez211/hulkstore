package com.diegodev.hulkstore.views.admin;

import com.diegodev.hulkstore.views.MainLayout;
import com.diegodev.hulkstore.views.shop.ShopMainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.theme.lumo.LumoUtility.*;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.context.annotation.Scope;
import org.vaadin.lineawesome.LineAwesomeIcon;

@RolesAllowed("ADMIN")
@org.springframework.stereotype.Component
@Scope("prototype")
@Route(value = "admin-panel", layout = MainLayout.class)
@PageTitle("Administration Panel | HulkStore")
public class AdminPanelView extends AppLayout {

    /**
     * A simple navigation item component, based on ListItem element.
     */
    public static class MenuItemInfo extends ListItem {

        private final Class<? extends Component> view;

        public MenuItemInfo(String menuTitle, Component icon, Class<? extends Component> view) {
            this.view = view;
            RouterLink link = new RouterLink();
            // Use Lumo classnames for various styling
            link.addClassNames(Display.FLEX, Gap.XSMALL, Height.MEDIUM, AlignItems.CENTER, Padding.Horizontal.SMALL,
                    TextColor.BODY);
            link.setRoute(view);

            Span text = new Span(menuTitle);
            // Use Lumo classnames for various styling
            text.addClassNames(FontWeight.MEDIUM, FontSize.MEDIUM, Whitespace.NOWRAP);

            if (icon != null) {
                link.add(icon);
            }
            link.add(text);
            add(link);
        }

        public Class<?> getView() {
            return view;
        }

    }

    private final AccessAnnotationChecker accessChecker;

    public AdminPanelView(AccessAnnotationChecker accessChecker) {
        this.accessChecker = accessChecker;
        addToNavbar(createHeaderContent());
    }

    private Component createHeaderContent() {
        Header header = new Header();
        header.addClassNames(BoxSizing.BORDER, Display.FLEX, FlexDirection.COLUMN, Width.FULL);

        Div layout = new Div();
        layout.addClassNames(Display.FLEX, AlignItems.CENTER, Padding.Horizontal.LARGE);

        H1 appName = new H1("Administration Panel");
        appName.addClassNames(Margin.Vertical.MEDIUM, Margin.End.AUTO, FontSize.LARGE);
        layout.add(appName);

        Nav nav = new Nav();
        nav.addClassNames(Display.FLEX, Overflow.AUTO, Padding.Horizontal.MEDIUM, Padding.Vertical.XSMALL);

        // Wrap the links in a list; improves accessibility
        UnorderedList list = new UnorderedList();
        list.addClassNames(Display.FLEX, Gap.SMALL, ListStyleType.NONE, Margin.NONE, Padding.NONE);
        nav.add(list);

        for (MenuItemInfo menuItem : createMenuItems()) {
            if (accessChecker.hasAccess(menuItem.getView())) {
                list.add(menuItem);
            }

        }

        header.add(layout, nav);
        return header;
    }

    private MenuItemInfo[] createMenuItems() {
        return new MenuItemInfo[]{
                new MenuItemInfo("Home", LineAwesomeIcon.HOME_SOLID.create(), ShopMainView.class),
                new MenuItemInfo("Products", LineAwesomeIcon.CART_PLUS_SOLID.create(), ProductListView.class),
                new MenuItemInfo("Users", LineAwesomeIcon.USERS_SOLID.create(), UserListView.class),
                new MenuItemInfo("Orders", LineAwesomeIcon.CROWN_SOLID.create(), OrderListView.class),
        };
    }

}
