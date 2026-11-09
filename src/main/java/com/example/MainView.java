package com.example;

import java.time.LocalDateTime;


import com.example.model.Mesero;
import com.example.model.Pedido;
import com.example.model.Plato;
import com.example.model.Mesa;
import com.example.model.Usuario;
import com.example.service.CrudService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Sistema inventario RESTAURANTE")
@Route("")
public class MainView extends VerticalLayout {

    private final CrudService crudService;

    public MainView(CrudService crudService) {
        this.crudService = crudService;
        setSizeFull();
        setPadding(true);
        setSpacing(true);

        TabSheet tabs = new TabSheet();
        tabs.setWidthFull();
        tabs.add("Mesas", crearSeccionMesas());
        tabs.add("Pedidos", crearSeccionPedidos());
        tabs.add("Platos", crearSeccionPlatos());
        tabs.add("Meseros", crearSeccionMeseros());
        tabs.add("Usuarios", crearSeccionUsuarios());
        

        add(new H2("Sistema inventario RESTAURANTE"), tabs);
    }

    // ==========================================
    // 1. MESAS
    // ==========================================
    private Component crearSeccionMesas() {
        VerticalLayout layout = new VerticalLayout();
        TextField idField = new TextField("ID");
        TextField nombreField = new TextField("Nombre de quien reserva");
        TextField capacidadPersonasField = new TextField("Capacidad Personas");
        

        ComboBox<String> numeroMesaField = new ComboBox<>("Número de Mesa");
    numeroMesaField.setItems("Mesa 1", "Mesa 2", "Mesa 3", "Mesa 4", "Mesa 5", "Mesa 6" , "Mesa 7", "Mesa 8", "Mesa 9", "Mesa 10");

    ComboBox<String> estadoField = new ComboBox<>("Estado de Mesa");
    estadoField.setItems("Disponible", "Ocupada", "Reservada");

        ComboBox<String> ubicacionField = new ComboBox<>("Ubicación");
    ubicacionField.setItems("Salón Principal", "Terraza", "Balcón");

        FormLayout form = new FormLayout(idField, nombreField, numeroMesaField, capacidadPersonasField, ubicacionField, estadoField);

        Grid<Mesa> grid = new Grid<>(Mesa.class, false);
        grid.addColumn(p -> p.getId()).setHeader("ID").setAutoWidth(true);
        grid.addColumn(p -> p.getNombre()).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(p -> p.getNumeroMesa()).setHeader("numero Mesa").setAutoWidth(true);
        grid.addColumn(p -> p.getCapacidadPersonas()).setHeader("Capacidad Personas").setAutoWidth(true);
        grid.addColumn(p -> p.getUbicacion()).setHeader("Ubicación").setAutoWidth(true);
        grid.addColumn(p -> p.getEstado()).setHeader("Estado").setAutoWidth(true);

        Runnable refrescar = () -> grid.setItems(crudService.listarTodos(Mesa.class));
        refrescar.run();

        grid.asSingleSelect().addValueChangeListener(e -> {
            Mesa p = e.getValue();
            if (p != null) {
                idField.setValue(p.getId() != null ? p.getId().toString() : "");
                nombreField.setValue(p.getNombre() != null ? p.getNombre() : "");
                numeroMesaField.setValue(p.getNumeroMesa() != null ? p.getNumeroMesa() : "");
                capacidadPersonasField.setValue(p.getCapacidadPersonas() != null ? p.getCapacidadPersonas() : "");
                ubicacionField.setValue(p.getUbicacion() != null ? p.getUbicacion() : "");
                estadoField.setValue(p.getEstado() != null ? p.getEstado() : "");
            }
        });

        Button btnCrear = new Button("Crear", e -> {
            Mesa p = new Mesa(
                nombreField.getValue(),
                numeroMesaField.getValue(),
                capacidadPersonasField.getValue(),
                ubicacionField.getValue(),
                estadoField.getValue()
            );
            crudService.guardar(p);
            refrescar.run();
            idField.setValue(p.getId() != null ? p.getId().toString() : "");
            Notification.show("Mesa guardada con ID: " + p.getId());
        });
        btnCrear.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button btnConsultar = new Button("Consultar", e -> {
            Mesa p = crudService.buscarPorId(Mesa.class, parseId(idField));
            if (p != null) {
                grid.asSingleSelect().setValue(p);
                Notification.show("Encontrado: " + p.getNombre());
            } else {
                Notification.show("No encontrado");
            }
        });

        Button btnActualizar = new Button("Actualizar", e -> {
            Mesa p = crudService.buscarPorId(Mesa.class, parseId(idField));
            if (p != null) {
                p.setNombre(nombreField.getValue());
                p.setNumeroMesa(numeroMesaField.getValue());
                p.setCapacidadPersonas(capacidadPersonasField.getValue());
                p.setUbicacion(ubicacionField.getValue());
                p.setEstado(estadoField.getValue());
                crudService.actualizar(p);
                refrescar.run();
                Notification.show("Mesa actualizada");
            } else {
                Notification.show("ID no encontrado");
            }
        });

        Button btnEliminar = new Button("Eliminar", e -> {
            crudService.eliminar(Mesa.class, parseId(idField));
            refrescar.run();
            limpiar(idField, nombreField, numeroMesaField, capacidadPersonasField, ubicacionField, estadoField);
            Notification.show("Mesa eliminada");
        });
        btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button btnLimpiar = new Button("Limpiar", e -> {
            limpiar(idField, nombreField, numeroMesaField, capacidadPersonasField, ubicacionField, estadoField);
            grid.asSingleSelect().clear();
        });

        layout.add(form, crearAcciones(btnCrear, btnConsultar, btnActualizar, btnEliminar, btnLimpiar), grid);
        return layout;
    }

    private void limpiar(TextField idField, TextField nombreField, ComboBox<String> numeroMesaField,
            TextField capacidadPersonasField, ComboBox<String> ubicacionField, ComboBox<String> estadoField) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'limpiar'");
    }

    // ==========================================
    // 2. PEDIDOS
    // ==========================================
    private Component crearSeccionPedidos() {
        VerticalLayout layout = new VerticalLayout();
        TextField idField = new TextField("ID");
        TextField descripcionPlatosField = new TextField("Descripción del plato");
        TextField totalVentasField = new TextField("Total Ventas");
        TextField horaPedidoField = new TextField("Hora Pedido");
        

        ComboBox<String> estadoField = new ComboBox<>("Estado");
    estadoField.setItems("En preparación", "Servido", "Pagado");

        ComboBox<String> numeroMesaField = new ComboBox<>("Número de Mesa");
    numeroMesaField.setItems("Mesa 1", "Mesa 2", "Mesa 3", "Mesa 4", "Mesa 5", "Mesa 6" , "Mesa 7", "Mesa 8", "Mesa 9", "Mesa 10");

        FormLayout form = new FormLayout(idField, numeroMesaField, descripcionPlatosField, totalVentasField, horaPedidoField, estadoField);

        Grid<Pedido> grid = new Grid<>(Pedido.class, false);
        grid.addColumn(p -> p.getId()).setHeader("ID").setAutoWidth(true);
        grid.addColumn(p -> p.getNumeroMesa()).setHeader("Numero de Mesa").setAutoWidth(true);
        grid.addColumn(p -> p.getDescripcionPlatos()).setHeader("Descripción del plato").setAutoWidth(true);
        grid.addColumn(p -> p.getTotalVentas()).setHeader("Total Ventas").setAutoWidth(true);
        grid.addColumn(p -> p.getHoraPedido()).setHeader("Hora del pedido").setAutoWidth(true);
        grid.addColumn(p -> p.getEstado()).setHeader("Estado").setAutoWidth(true);

        Runnable refrescar = () -> grid.setItems(crudService.listarTodos(Pedido.class));
        refrescar.run();

        grid.asSingleSelect().addValueChangeListener(e -> {
            Pedido p = e.getValue();
            if (p != null) {
                idField.setValue(p.getId() != null ? p.getId().toString() : "");
                numeroMesaField.setValue(p.getNumeroMesa() != null ? p.getNumeroMesa() : "");
                descripcionPlatosField.setValue(p.getDescripcionPlatos() != null ? p.getDescripcionPlatos() : "");
                totalVentasField.setValue(p.getTotalVentas() != null ? p.getTotalVentas() : "");
                horaPedidoField.setValue(p.getHoraPedido() != null ? p.getHoraPedido() : "");
                estadoField.setValue(p.getEstado() != null ? p.getEstado() : "");
            }
        });

        Button btnCrear = new Button("Crear", e -> {
            Pedido p = new Pedido(
                numeroMesaField.getValue(),
                descripcionPlatosField.getValue(),
                totalVentasField.getValue(),
                horaPedidoField.getValue(),
                estadoField.getValue()
            );
            crudService.guardar(p);
            refrescar.run();
            idField.setValue(p.getId() != null ? p.getId().toString() : "");
            Notification.show("Pedido guardado con ID: " + p.getId());
        });
        btnCrear.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button btnConsultar = new Button("Consultar", e -> {
            Pedido p = crudService.buscarPorId(Pedido.class, parseId(idField));
            if (p != null) {
                grid.asSingleSelect().setValue(p);
                Notification.show("Encontrado: " + p.getNumeroMesa());
            } else {
                Notification.show("No encontrado");
            }
        });

        Button btnActualizar = new Button("Actualizar", e -> {
            Pedido p = crudService.buscarPorId(Pedido.class, parseId(idField));
            if (p != null) {
                p.setNumeroMesa(numeroMesaField.getValue());
                p.setDescripcionPlatos(descripcionPlatosField.getValue());
                p.setTotalVentas(totalVentasField.getValue());
                p.setHoraPedido(horaPedidoField.getValue());
                p.setEstado(estadoField.getValue());
                crudService.actualizar(p);
                refrescar.run();
                Notification.show("Pedido actualizado");
            } else {
                Notification.show("ID no encontrado");
            }
        });

        Button btnEliminar = new Button("Eliminar", e -> {
            crudService.eliminar(Pedido.class, parseId(idField));
            refrescar.run();
            limpiar(idField, numeroMesaField, descripcionPlatosField, totalVentasField, horaPedidoField, estadoField);
            Notification.show("Pedido eliminado");
        });
        btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button btnLimpiar = new Button("Limpiar", e -> {
            limpiar(idField, numeroMesaField, descripcionPlatosField, totalVentasField, horaPedidoField, estadoField);
            grid.asSingleSelect().clear();
        });

        layout.add(form, crearAcciones(btnCrear, btnConsultar, btnActualizar, btnEliminar, btnLimpiar), grid);
        return layout;
    }

    private void limpiar(TextField idField, ComboBox<String> numeroMesaField, TextField descripcionPlatosField,
            TextField totalVentasField, TextField horaPedidoField, ComboBox<String> estadoField) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'limpiar'");
    }



    // ==========================================
    // 3. Platos
    // ==========================================

	
    private Component crearSeccionPlatos() {
        VerticalLayout layout = new VerticalLayout();
        TextField idField = new TextField("ID");
        TextField nombreField = new TextField("Nombre");
        TextField codigoField = new TextField("Código de Menú");
        TextField precioField = new TextField("Precio");
        
        
        ComboBox<String> stockField = new ComboBox<>("Disponible");
    stockField.setItems("Si", "No");

        ComboBox<String> categoriaField = new ComboBox<>("Categoría");
    categoriaField.setItems("Entrada", "Plato Fuerte", "Comida rápida" ,"Postre", "Bebidas");


        FormLayout form = new FormLayout(idField, nombreField, codigoField, categoriaField, precioField, stockField);

        Grid<Plato> grid = new Grid<>(Plato.class, false);
        grid.addColumn(Plato::getCodigoMenu).setHeader("Código de Menú").setAutoWidth(true);
        grid.addColumn(Plato::getId).setHeader("ID").setAutoWidth(true);
        grid.addColumn(Plato::getNombre).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(Plato::getCategoria).setHeader("Categoría").setAutoWidth(true);
        grid.addColumn(Plato::getPrecio).setHeader("Precio").setAutoWidth(true);
        grid.addColumn(Plato::getDisponible).setHeader("Disponible").setAutoWidth(true);

        Runnable refrescar = () -> grid.setItems(crudService.listarTodos(Plato.class));
        refrescar.run();

        grid.asSingleSelect().addValueChangeListener(e -> {
            Plato c = e.getValue();
            if (c != null) {
                idField.setValue(c.getId() != null ? c.getId().toString() : "");
                codigoField.setValue(c.getCodigoMenu() != null ? c.getCodigoMenu() : "");
                nombreField.setValue(c.getNombre() != null ? c.getNombre() : "");
                categoriaField.setValue(c.getCategoria() != null ? c.getCategoria() : "");
                precioField.setValue(c.getPrecio() != null ? c.getPrecio().toString() : "");
                stockField.setValue(c.getDisponible() != null ? c.getDisponible().toString() : "");
            }
        });

        Button btnCrear = new Button("Crear", e -> {
            Plato c = new Plato(
                codigoField.getValue(), 
                nombreField.getValue(), 
                categoriaField.getValue(), 
                precioField.getValue() != null && !precioField.getValue().isEmpty() ? Double.parseDouble(precioField.getValue()) : null, 
                stockField.getValue() != null && !stockField.getValue().isEmpty() ? Boolean.parseBoolean(stockField.getValue()) : null
            );
            crudService.guardar(c);
            refrescar.run();
            idField.setValue(c.getId() != null ? c.getId().toString() : "");
            Notification.show("Plato guardado con ID: " + c.getId());
        });
        btnCrear.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button btnConsultar = new Button("Consultar", e -> {
            Plato c = crudService.buscarPorId(Plato.class, parseId(idField));
            if (c != null) {
                grid.asSingleSelect().setValue(c);
                Notification.show("Encontrado: " + c.getNombre());
            } else {
                Notification.show("No encontrado");
            }
        });

        Button btnActualizar = new Button("Actualizar", e -> {
            Plato c = crudService.buscarPorId(Plato.class, parseId(idField));
            if (c != null) {
                c.setCodigoMenu(codigoField.getValue());
                c.setNombre(nombreField.getValue());
                c.setCategoria(categoriaField.getValue());
                c.setPrecio(precioField.getValue() != null && !precioField.getValue().isEmpty() ? Double.parseDouble(precioField.getValue()) : null);
                c.setDisponible(stockField.getValue() != null && !stockField.getValue().isEmpty() ? Boolean.parseBoolean(stockField.getValue()) : null);
                
                crudService.actualizar(c);
                refrescar.run();
                Notification.show("Plato actualizado");
            } else {
                Notification.show("ID no encontrado");
            }
        });

        Button btnEliminar = new Button("Eliminar", e -> {
            crudService.eliminar(Plato.class, parseId(idField));
            refrescar.run();
            idField.clear();
            codigoField.clear();
            nombreField.clear();
            categoriaField.clear();
            precioField.clear();
            stockField.clear();
            Notification.show("Plato eliminado");
        });
        btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button btnLimpiar = new Button("Limpiar", e -> {
            idField.clear();
            codigoField.clear();
            nombreField.clear();
            categoriaField.clear();
            precioField.clear();
            stockField.clear();
            grid.asSingleSelect().clear();
        });

        layout.add(form, crearAcciones(btnCrear, btnConsultar, btnActualizar, btnEliminar, btnLimpiar), grid);
        return layout;
    }


    // ==========================================
    // 4. MESERO
    // ==========================================
    private Component crearSeccionMeseros() {
    VerticalLayout layout = new VerticalLayout();

    TextField idField = new TextField("ID");
    TextField nombreField = new TextField("Nombre");
    TextField telefonoField = new TextField("Teléfono");

    ComboBox<String> turnoField = new ComboBox<>("Turnos");
    turnoField.setItems("Mañana", "Tarde", "Noche");

    FormLayout form = new FormLayout(
        idField,
        nombreField,
        telefonoField,
        turnoField
    );

    Grid<Mesero> grid = new Grid<>(Mesero.class, false);

    grid.addColumn(m -> m.getId()).setHeader("ID").setAutoWidth(true);
    grid.addColumn(m -> m.getNombre()).setHeader("Nombre").setAutoWidth(true);
    grid.addColumn(m -> m.getTelefono()).setHeader("Teléfono").setAutoWidth(true);
    grid.addColumn(m -> m.getTurno()).setHeader("Turnos").setAutoWidth(true);


    Runnable refrescar = () -> grid.setItems(
        crudService.listarTodos(Mesero.class)
    );

    refrescar.run();

    grid.asSingleSelect().addValueChangeListener(e -> {
        Mesero m = e.getValue();

        if (m != null) {
            idField.setValue(m.getId() != null ? m.getId().toString() : "");
            nombreField.setValue(m.getNombre() != null ? m.getNombre() : "");
            telefonoField.setValue(m.getTelefono() != null ? m.getTelefono() : "");
            turnoField.setValue(m.getTurno() != null ? m.getTurno() : "");
        }
    });

    Button btnCrear = new Button("Crear", e -> {

        Mesero m = new Mesero(
            null,
            nombreField.getValue(),
            telefonoField.getValue(),
            turnoField.getValue()
        );

        crudService.guardar(m);
        refrescar.run();

        Notification.show("Mesero guardado con ID: " + m.getId());
    });

    btnCrear.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

    Button btnConsultar = new Button("Consultar", e -> {

        Mesero m = crudService.buscarPorId(Mesero.class, parseId(idField));

        if (m != null) {
            grid.asSingleSelect().setValue(m);
            Notification.show("Encontrado: " + m.getNombre());
        } else {
            Notification.show("No encontrado");
        }
    });

    Button btnActualizar = new Button("Actualizar", e -> {

        Mesero m = crudService.buscarPorId(Mesero.class, parseId(idField));

        if (m != null) {
            m.setNombre(nombreField.getValue());
            m.setTelefono(telefonoField.getValue());
            m.setTurno(turnoField.getValue());

            crudService.actualizar(m);
            refrescar.run();

            Notification.show("Mesero actualizado");
        } else {
            Notification.show("ID no encontrado");
        }
    });

    Button btnEliminar = new Button("Eliminar", e -> {

        crudService.eliminar(Mesero.class, parseId(idField));
        refrescar.run();

        limpiar(idField, nombreField, telefonoField);
        turnoField.clear();

        Notification.show("Mesero eliminado");
    });

    btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);

    Button btnLimpiar = new Button("Limpiar", e -> {

        limpiar(idField, nombreField, telefonoField);
        turnoField.clear();

        grid.asSingleSelect().clear();
    });

    layout.add(
        form,
        crearAcciones(btnCrear, btnConsultar, btnActualizar, btnEliminar, btnLimpiar),
        grid
    );

    return layout;
}

    

    
    // ==========================================
    // 5. USUARIOS
    // ==========================================
    private Component crearSeccionUsuarios() {
        VerticalLayout layout = new VerticalLayout();
        TextField idField = new TextField("ID");
        TextField nombreField = new TextField("Nombre");
        TextField apellidoField = new TextField("Apellido");
        TextField emailField = new TextField("Email");
        TextField telefonoField = new TextField("Teléfono");
        TextField direccionField = new TextField("Dirección");

        ComboBox<String> rolField = new ComboBox<>("Rol");
    rolField.setItems("Administrador", "Jefe", "Encargado", "Administrador auxiliar");

    ComboBox<String> estadoField = new ComboBox<>("Estado");
    estadoField.setItems("Activo", "Inactivo");

        FormLayout form = new FormLayout(idField, nombreField, apellidoField, emailField, telefonoField, direccionField, rolField, estadoField);

        Grid<Usuario> grid = new Grid<>(Usuario.class, false);
        grid.addColumn(u -> u.getId()).setHeader("ID").setAutoWidth(true);
        grid.addColumn(u -> u.getNombre()).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(u -> u.getApellido()).setHeader("Apellido").setAutoWidth(true);
        grid.addColumn(u -> u.getEmail()).setHeader("Email").setAutoWidth(true);
        grid.addColumn(u -> u.getTelefono()).setHeader("Teléfono").setAutoWidth(true);
        grid.addColumn(u -> u.getRol()).setHeader("Rol").setAutoWidth(true);
        grid.addColumn(u -> u.getEstado()).setHeader("Estado").setAutoWidth(true);

        Runnable refrescar = () -> grid.setItems(crudService.listarTodos(Usuario.class));
        refrescar.run();

        grid.asSingleSelect().addValueChangeListener(e -> {
            Usuario u = e.getValue();
            if (u != null) {
                idField.setValue(u.getId() != null ? u.getId().toString() : "");
                nombreField.setValue(u.getNombre() != null ? u.getNombre() : "");
                apellidoField.setValue(u.getApellido() != null ? u.getApellido() : "");
                emailField.setValue(u.getEmail() != null ? u.getEmail() : "");
                telefonoField.setValue(u.getTelefono() != null ? u.getTelefono() : "");
                direccionField.setValue(u.getDireccion() != null ? u.getDireccion() : "");
                rolField.setValue(u.getRol() != null ? u.getRol() : "");
                estadoField.setValue(u.getEstado() != null ? u.getEstado() : "");
            }
        });

        Button btnCrear = new Button("Crear", e -> {
            Usuario u = new Usuario(
                nombreField.getValue(),
                apellidoField.getValue(),
                emailField.getValue(),
                telefonoField.getValue(),
                direccionField.getValue(),
                rolField.getValue(),
                estadoField.getValue(),
                LocalDateTime.now()
            );
            crudService.guardar(u);
            refrescar.run();
            idField.setValue(u.getId() != null ? u.getId().toString() : "");
            Notification.show("Usuario guardado con ID: " + u.getId());
        });
        btnCrear.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button btnConsultar = new Button("Consultar", e -> {
            Usuario u = crudService.buscarPorId(Usuario.class, parseId(idField));
            if (u != null) {
                grid.asSingleSelect().setValue(u);
                Notification.show("Encontrado: " + u.getNombre() + " " + u.getApellido());
            } else {
                Notification.show("No encontrado");
            }
        });

        Button btnActualizar = new Button("Actualizar", e -> {
            Usuario u = crudService.buscarPorId(Usuario.class, parseId(idField));
            if (u != null) {
                u.setNombre(nombreField.getValue());
                u.setApellido(apellidoField.getValue());
                u.setEmail(emailField.getValue());
                u.setTelefono(telefonoField.getValue());
                u.setDireccion(direccionField.getValue());
                u.setRol(rolField.getValue());
                u.setEstado(estadoField.getValue());
                crudService.actualizar(u);
                refrescar.run();
                Notification.show("Usuario actualizado");
            } else {
                Notification.show("ID no encontrado");
            }
        });

        Button btnEliminar = new Button("Eliminar", e -> {
            crudService.eliminar(Usuario.class, parseId(idField));
            refrescar.run();
            limpiar(idField, nombreField, apellidoField, emailField, telefonoField, direccionField, rolField, estadoField);
            Notification.show("Usuario eliminado");
        });
        btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button btnLimpiar = new Button("Limpiar", e -> {
            limpiar(idField, nombreField, apellidoField, emailField, telefonoField, direccionField, rolField, estadoField);
            grid.asSingleSelect().clear();
        });

        layout.add(form, crearAcciones(btnCrear, btnConsultar, btnActualizar, btnEliminar, btnLimpiar), grid);
        return layout;
    }

    

    private void limpiar(TextField idField, TextField nombreField, TextField apellidoField, TextField emailField,
            TextField telefonoField, TextField direccionField, ComboBox<String> rolField,
            ComboBox<String> estadoField) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'limpiar'");
    }

    // ==========================================
    // MÉTODOS AUXILIARES
    // ==========================================
    private Long parseId(TextField field) {
        try {
            return Long.parseLong(field.getValue().trim());
        } catch (Exception e) {
            return null;
        }
    }

    private Double parseDouble(String val) {
        try {
            return Double.parseDouble(val.trim());
        } catch (Exception e) {
            return 0.0;
        }
    }

    private Integer parseInt(String val) {
        try {
            return Integer.parseInt(val.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    private void limpiar(TextField... campos) {
        for (TextField c : campos) {
            c.clear();
        }
    }

    private HorizontalLayout crearAcciones(Button... botones) {
        HorizontalLayout layout = new HorizontalLayout(botones);
        layout.getStyle().set("flex-wrap", "wrap");
        return layout;
    } 

    
}
