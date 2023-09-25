/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.presta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Green
 */
//se agrego el final para que no diera unos errores, no se si es necesario
public final class PrestaInterfaz extends javax.swing.JFrame {

    ArrayList<Usuario> usuarios = new ArrayList();
    ArrayList<Tutor> tutores = new ArrayList();
    ArrayList<Material> inventario = new ArrayList();
    Usuario usuarioIdentificado = null;
    boolean inicioSesion = false;
    
    public PrestaInterfaz() throws IOException {
        initComponents();
        cargarTutores("C:\\Users\\Green\\Documents\\NetBeansProjects\\Presta\\tutoresBase.csv");
        mostrarTurores();
        cargarUsuarios("C:\\Users\\Green\\Documents\\NetBeansProjects\\Presta\\usuariosBase.csv");
        mostrarUsuarios();
        cargarInventario();
        cargarComboBox();
    }

    public void mostrarTurores() {
        for (int i = 0; i < tutores.size(); i++) {
            System.out.println(tutores.get(i).toString());
        }
    }

    public void mostrarUsuarios() {
        for (int i = 0; i < usuarios.size(); i++) {
            System.out.println(usuarios.get(i).toString());
        }
    }

    public void iniciarSesion() throws IOException {
        while (usuarioIdentificado == null) {
            String s = JOptionPane.showInputDialog("introduzca su correo: ");

            if (s == null) {
                System.out.println("El usuario cerró la ventana de entrada.");
                return;  // O puedes decidir tomar otra acción en lugar de solo retornar
            }

            for (Usuario usuario : usuarios) {
                if (usuario.getCorreo().equals(s)) {
                    usuarioIdentificado = usuario;
                    break;
                }
            }

            if (usuarioIdentificado != null) {
                inicioSesion = true;
                System.out.println("Usuario encontrado.");
                label_nombreDelUsuario.setText(usuarioIdentificado.getNombre());
                cargarPerfil();
            } else {
                System.out.println("Usuario no encontrado.");
                JOptionPane.showMessageDialog(null, "Error: Ese cuenta no se encuentra registrada");
            }
        }
    }

    public void pedirPrestamo() throws IOException {

    }

    public void agregarMaterial() throws IOException {
        Material material = new Material(inventario.size() + 1, textField_nombreMaterial.getText(), checkBox_Potencia.isSelected());
        guardarArchivoInventario("C:\\Users\\Green\\Documents\\NetBeansProjects\\Presta\\baseDeDatos.csv", material);
        cargarArchivoInventario("C:\\Users\\Green\\Documents\\NetBeansProjects\\Presta\\baseDeDatos.csv");
        DefaultTableModel model = (DefaultTableModel) table_inventario.getModel();
        model.addRow(new Object[]{inventario.get(inventario.size() - 1).getId(), inventario.get(inventario.size() - 1).getNombre(), inventario.get(inventario.size() - 1).isNecesitaPotencia()});
    }

    public void cargarComboBox() throws IOException {
        for (Material mat : inventario) {
            comboBox_materialFiltro.addItem(mat.getNombre());
            comboBox_materiales.addItem(mat.getNombre());
        }
    }

    public void cargarInventario() throws IOException {
        cargarArchivoInventario("C:\\Users\\Green\\Documents\\NetBeansProjects\\Presta\\baseDeDatos.csv");
        DefaultTableModel model = (DefaultTableModel) table_inventario.getModel();
        for (Material mat : inventario) {
            model.addRow(new Object[]{mat.getId(), mat.getNombre(), mat.isNecesitaPotencia()});
        }
    }

    public void cargarPerfil() {
        textField_usuarioNombre.setText(usuarioIdentificado.getNombre());
        textField_usuarioEmail.setText(usuarioIdentificado.getCorreo());
        textField_usuarioTipo.setText(usuarioIdentificado.getTipoUsuarioString());
        switch (usuarioIdentificado.getTipoUsuario()) {
            case 0 -> {
                textField_identificador.setName("Id");
                Administrador admin = (Administrador) usuarioIdentificado;
                textField_identificador.setText("" + admin.getId());
                paneles_presta.setEnabledAt(paneles_presta.indexOfComponent(panel_agregarMateriales), true);
                paneles_presta.setEnabledAt(paneles_presta.indexOfComponent(panel_devolucionPrestamo), true);
                paneles_presta.setEnabledAt(paneles_presta.indexOfComponent(panel_prestamoMaterial), true);
                panel_tutor.setVisible(false);
                textField_tutorNombre.setVisible(false);
                textField_tutorCorreo.setVisible(false);

            }
            case 1 -> {

            }
            case 2 -> {
                textField_identificador.setName("Número de empleado");
                Docente docente = (Docente) usuarioIdentificado;
                textField_identificador.setText("" + docente.getNumEmpleado());
                panel_tutor.setVisible(false);
                textField_tutorNombre.setVisible(false);
                textField_tutorCorreo.setVisible(false);
                paneles_presta.setEnabledAt(paneles_presta.indexOfComponent(panel_agregarMateriales), false);
                paneles_presta.setEnabledAt(paneles_presta.indexOfComponent(panel_devolucionPrestamo), false);
                paneles_presta.setEnabledAt(paneles_presta.indexOfComponent(panel_prestamoMaterial), false);
            }
            case 3 -> {
                textField_identificador.setName("Matricula");
                Alumno alumno = (Alumno) usuarioIdentificado;
                textField_identificador.setText("" + alumno.getMatricula());
                panel_tutor.setVisible(true);
                textField_tutorNombre.setVisible(true);
                textField_tutorCorreo.setVisible(true);
                textField_tutorNombre.setText(alumno.getTutor().getNombre());
                textField_tutorCorreo.setText(alumno.getTutor().getCorreo());
                paneles_presta.setEnabledAt(paneles_presta.indexOfComponent(panel_agregarMateriales), false);
                paneles_presta.setEnabledAt(paneles_presta.indexOfComponent(panel_devolucionPrestamo), false);
                paneles_presta.setEnabledAt(paneles_presta.indexOfComponent(panel_prestamoMaterial), false);
            }
            default -> {
            }
        }
    }

    public void cargarUsuarios(String nombre) throws IOException {
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(new File(nombre));
            br = new BufferedReader(fr);
            String linea;
            while ((linea = br.readLine()) != null) {
                String arreglo[] = linea.split(",");
                System.out.println(arreglo[arreglo.length - 1]);
                switch (arreglo[arreglo.length - 1]) {
                    case "0":
                        Administrador a = new Administrador(Integer.parseInt(arreglo[0]), arreglo[1], arreglo[2], Integer.parseInt(arreglo[3]));
                        usuarios.add(a);
                        break;
                    case "1":
                        System.out.println("todavi no existe");
                        break;
                    case "2":
                        Docente d = new Docente(Integer.parseInt(arreglo[0]), arreglo[1], arreglo[2], Integer.parseInt(arreglo[3]));
                        usuarios.add(d);
                        break;
                    case "3":
                        System.out.println(arreglo[3]);
                        Alumno al = new Alumno(Integer.parseInt(arreglo[0]), buscarTutor(arreglo[3]), (String) arreglo[1], (String) arreglo[2], Integer.parseInt(arreglo[4]));
                        usuarios.add(al);
                        break;
                    default:
                        break;
                }

            }
        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public Tutor buscarTutor(String correo) {
        Tutor tutor = null;
        for (int i = 0; i < tutores.size(); i++) {
            if (tutores.get(i).getCorreo().equals(correo)) {
                tutor = tutores.get(i);
            }
        }
        return tutor;
    }

    public void cargarTutores(String nombre) throws IOException {
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(new File(nombre));
            br = new BufferedReader(fr);
            String linea;
            while ((linea = br.readLine()) != null) {
                String arreglo[] = linea.split(",");
                Tutor m = new Tutor((String) arreglo[0], (String) arreglo[1]);
                tutores.add(m);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void guardarArchivoInventario(String nombre, Material material) throws IOException {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(new File(nombre), true);
            pw = new PrintWriter(fichero);
            String linea = material.getId() + "," + material.getNombre() + "," + material.isNecesitaPotencia();
            pw.println(linea);
        } catch (IOException ex) {
        } finally {
            try {
                if (fichero != null) {
                    fichero.close();
                }
            } catch (IOException e) {
            }
        }
    }

    public void cargarArchivoInventario(String nombre) throws IOException {
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(new File(nombre));
            br = new BufferedReader(fr);
            String linea;
            while ((linea = br.readLine()) != null) {
                String arreglo[] = linea.split(",");
                boolean b = false;
                if (arreglo[2].equals("true")) {
                    b = true;
                }
                Material m = new Material(Integer.parseInt(arreglo[0]), arreglo[1], b);
                inventario.add(m);
            }
        } catch (IOException | NumberFormatException ex) {
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException ex) {
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        paneles_presta = new javax.swing.JTabbedPane();
        panel_generarPrestamo = new javax.swing.JPanel();
        panel_prestamo = new javax.swing.JPanel();
        label_prestamo = new javax.swing.JLabel();
        scrollPane_materialesPrestamo = new javax.swing.JScrollPane();
        table_materialesPrestamo = new javax.swing.JTable();
        button_enviarPrestamo = new javax.swing.JButton();
        panel_material = new javax.swing.JPanel();
        label_generarPrestamoMaterial = new javax.swing.JLabel();
        comboBox_materiales = new javax.swing.JComboBox<>();
        textField_descripcion = new javax.swing.JTextField();
        comboBox_cantidad = new javax.swing.JComboBox<>();
        label_cantidad = new javax.swing.JLabel();
        label_potenciador = new javax.swing.JLabel();
        comboBox_potenciador = new javax.swing.JComboBox<>();
        button_agregarMaterial = new javax.swing.JButton();
        panel_perfilUsuario = new javax.swing.JPanel();
        panel_informacionUsuario = new javax.swing.JPanel();
        textField_usuarioNombre = new javax.swing.JTextField();
        textField_usuarioEmail = new javax.swing.JTextField();
        textField_usuarioTipo = new javax.swing.JTextField();
        textField_identificador = new javax.swing.JTextField();
        panel_tutor = new javax.swing.JPanel();
        textField_tutorNombre = new javax.swing.JTextField();
        textField_tutorCorreo = new javax.swing.JTextField();
        panel_consultarMisPrestamos = new javax.swing.JPanel();
        panel_filtroMateriales = new javax.swing.JPanel();
        comboBox_materialFiltro = new javax.swing.JComboBox<>();
        textField_descripcionFiltro = new javax.swing.JTextField();
        comboBox_estadoFiltro = new javax.swing.JComboBox<>();
        textField_numPrestadosFiltro = new javax.swing.JTextField();
        textField_numDevueltosFIltro = new javax.swing.JTextField();
        label_misPrestamosMaterial = new javax.swing.JLabel();
        label_estadoMaterial = new javax.swing.JLabel();
        panel_prestamos = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_prestamos = new javax.swing.JTable();
        panel_agregarMateriales = new javax.swing.JPanel();
        panel_inventario = new javax.swing.JPanel();
        scrollPane_inventario = new javax.swing.JScrollPane();
        table_inventario = new javax.swing.JTable();
        panel_agregarMaterial = new javax.swing.JPanel();
        textField_nombreMaterial = new javax.swing.JTextField();
        checkBox_Potencia = new javax.swing.JCheckBox();
        button_agregarMaterialInventario = new javax.swing.JButton();
        panel_devolucionPrestamo = new javax.swing.JPanel();
        panel_prestamoMaterial = new javax.swing.JPanel();
        panel_titulo = new javax.swing.JPanel();
        label_titulo = new javax.swing.JLabel();
        panel_nombreDelUsuario = new javax.swing.JPanel();
        label_nombreDelUsuario = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        paneles_presta.setBackground(new java.awt.Color(255, 255, 255));

        panel_generarPrestamo.setBackground(new java.awt.Color(255, 255, 255));

        panel_prestamo.setBackground(new java.awt.Color(255, 255, 255));

        label_prestamo.setText("Préstamo");

        table_materialesPrestamo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Material", "Descripción", "Potenciador"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollPane_materialesPrestamo.setViewportView(table_materialesPrestamo);

        button_enviarPrestamo.setText("Enviar préstamo");

        javax.swing.GroupLayout panel_prestamoLayout = new javax.swing.GroupLayout(panel_prestamo);
        panel_prestamo.setLayout(panel_prestamoLayout);
        panel_prestamoLayout.setHorizontalGroup(
            panel_prestamoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_prestamoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_prestamoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_prestamoLayout.createSequentialGroup()
                        .addComponent(label_prestamo)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(scrollPane_materialesPrestamo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_prestamoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(button_enviarPrestamo)
                .addContainerGap())
        );
        panel_prestamoLayout.setVerticalGroup(
            panel_prestamoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_prestamoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_prestamo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPane_materialesPrestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(button_enviarPrestamo)
                .addContainerGap())
        );

        panel_material.setBackground(new java.awt.Color(255, 255, 255));

        label_generarPrestamoMaterial.setText("Material");

        textField_descripcion.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        textField_descripcion.setText("descripción");
        textField_descripcion.setToolTipText("");
        textField_descripcion.setBorder(javax.swing.BorderFactory.createTitledBorder("Descripción"));

        comboBox_cantidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        label_cantidad.setText("Cantidad");

        label_potenciador.setText("Potenciador");

        comboBox_potenciador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A", "Pico", "Nano", "Micro", "Mili", "Kilo", "Mega", "Giga", "Tera" }));

        button_agregarMaterial.setText("Agregar");
        button_agregarMaterial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_agregarMaterialActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_materialLayout = new javax.swing.GroupLayout(panel_material);
        panel_material.setLayout(panel_materialLayout);
        panel_materialLayout.setHorizontalGroup(
            panel_materialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_materialLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_materialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textField_descripcion)
                    .addGroup(panel_materialLayout.createSequentialGroup()
                        .addGroup(panel_materialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label_generarPrestamoMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(button_agregarMaterial)
                            .addGroup(panel_materialLayout.createSequentialGroup()
                                .addComponent(label_potenciador)
                                .addGap(18, 18, 18)
                                .addComponent(label_cantidad))
                            .addGroup(panel_materialLayout.createSequentialGroup()
                                .addComponent(comboBox_potenciador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(comboBox_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 67, Short.MAX_VALUE))
                    .addComponent(comboBox_materiales, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel_materialLayout.setVerticalGroup(
            panel_materialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_materialLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_generarPrestamoMaterial)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboBox_materiales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textField_descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_materialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_potenciador)
                    .addComponent(label_cantidad))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_materialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboBox_potenciador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboBox_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(button_agregarMaterial)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        comboBox_potenciador.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout panel_generarPrestamoLayout = new javax.swing.GroupLayout(panel_generarPrestamo);
        panel_generarPrestamo.setLayout(panel_generarPrestamoLayout);
        panel_generarPrestamoLayout.setHorizontalGroup(
            panel_generarPrestamoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_generarPrestamoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_material, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panel_prestamo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel_generarPrestamoLayout.setVerticalGroup(
            panel_generarPrestamoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_generarPrestamoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_generarPrestamoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panel_prestamo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_material, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(168, Short.MAX_VALUE))
        );

        paneles_presta.addTab("Generar Préstamo", panel_generarPrestamo);

        panel_perfilUsuario.setBackground(new java.awt.Color(255, 255, 255));

        panel_informacionUsuario.setBackground(new java.awt.Color(255, 255, 255));
        panel_informacionUsuario.setBorder(javax.swing.BorderFactory.createTitledBorder("Información De Perfil"));

        textField_usuarioNombre.setEditable(false);
        textField_usuarioNombre.setBorder(javax.swing.BorderFactory.createTitledBorder("Nombre:"));

        textField_usuarioEmail.setEditable(false);
        textField_usuarioEmail.setBorder(javax.swing.BorderFactory.createTitledBorder("Email:"));

        textField_usuarioTipo.setEditable(false);
        textField_usuarioTipo.setBorder(javax.swing.BorderFactory.createTitledBorder("Tipo De Usuario:"));

        textField_identificador.setEditable(false);
        textField_identificador.setBorder(javax.swing.BorderFactory.createTitledBorder("Id"));

        javax.swing.GroupLayout panel_informacionUsuarioLayout = new javax.swing.GroupLayout(panel_informacionUsuario);
        panel_informacionUsuario.setLayout(panel_informacionUsuarioLayout);
        panel_informacionUsuarioLayout.setHorizontalGroup(
            panel_informacionUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_informacionUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_informacionUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(textField_usuarioTipo, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                    .addComponent(textField_usuarioEmail)
                    .addComponent(textField_usuarioNombre)
                    .addComponent(textField_identificador))
                .addContainerGap(86, Short.MAX_VALUE))
        );
        panel_informacionUsuarioLayout.setVerticalGroup(
            panel_informacionUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_informacionUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(textField_identificador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(textField_usuarioNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textField_usuarioEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textField_usuarioTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        panel_tutor.setBackground(new java.awt.Color(255, 255, 255));
        panel_tutor.setBorder(javax.swing.BorderFactory.createTitledBorder("Tutor"));

        textField_tutorNombre.setEditable(false);
        textField_tutorNombre.setBorder(javax.swing.BorderFactory.createTitledBorder("Nombre"));

        textField_tutorCorreo.setEditable(false);
        textField_tutorCorreo.setBorder(javax.swing.BorderFactory.createTitledBorder("Correo"));

        javax.swing.GroupLayout panel_tutorLayout = new javax.swing.GroupLayout(panel_tutor);
        panel_tutor.setLayout(panel_tutorLayout);
        panel_tutorLayout.setHorizontalGroup(
            panel_tutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_tutorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_tutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(textField_tutorCorreo, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addComponent(textField_tutorNombre))
                .addGap(15, 15, 15))
        );
        panel_tutorLayout.setVerticalGroup(
            panel_tutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tutorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(textField_tutorNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textField_tutorCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_perfilUsuarioLayout = new javax.swing.GroupLayout(panel_perfilUsuario);
        panel_perfilUsuario.setLayout(panel_perfilUsuarioLayout);
        panel_perfilUsuarioLayout.setHorizontalGroup(
            panel_perfilUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_perfilUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_informacionUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_tutor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel_perfilUsuarioLayout.setVerticalGroup(
            panel_perfilUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_perfilUsuarioLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(panel_perfilUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panel_tutor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_informacionUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(179, Short.MAX_VALUE))
        );

        paneles_presta.addTab("Perfil De Usuario", panel_perfilUsuario);

        panel_consultarMisPrestamos.setBackground(new java.awt.Color(255, 255, 255));

        panel_filtroMateriales.setBackground(new java.awt.Color(255, 255, 255));
        panel_filtroMateriales.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtros para la tabla"));

        textField_descripcionFiltro.setBorder(javax.swing.BorderFactory.createTitledBorder("Descripción"));

        comboBox_estadoFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Devuelto", "Prestado" }));

        textField_numPrestadosFiltro.setEditable(false);
        textField_numPrestadosFiltro.setBorder(javax.swing.BorderFactory.createTitledBorder("No. Préstamo Prestados"));

        textField_numDevueltosFIltro.setEditable(false);
        textField_numDevueltosFIltro.setBorder(javax.swing.BorderFactory.createTitledBorder("No. Préstamos Devueltos"));

        label_misPrestamosMaterial.setText("Material");

        label_estadoMaterial.setText("Estado");

        javax.swing.GroupLayout panel_filtroMaterialesLayout = new javax.swing.GroupLayout(panel_filtroMateriales);
        panel_filtroMateriales.setLayout(panel_filtroMaterialesLayout);
        panel_filtroMaterialesLayout.setHorizontalGroup(
            panel_filtroMaterialesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_filtroMaterialesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_filtroMaterialesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textField_descripcionFiltro)
                    .addComponent(comboBox_estadoFiltro, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textField_numPrestadosFiltro)
                    .addComponent(textField_numDevueltosFIltro)
                    .addGroup(panel_filtroMaterialesLayout.createSequentialGroup()
                        .addGroup(panel_filtroMaterialesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label_misPrestamosMaterial)
                            .addComponent(label_estadoMaterial))
                        .addGap(0, 160, Short.MAX_VALUE))
                    .addComponent(comboBox_materialFiltro, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel_filtroMaterialesLayout.setVerticalGroup(
            panel_filtroMaterialesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_filtroMaterialesLayout.createSequentialGroup()
                .addComponent(label_misPrestamosMaterial)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboBox_materialFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textField_descripcionFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_estadoMaterial)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboBox_estadoFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(textField_numPrestadosFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textField_numDevueltosFIltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panel_prestamos.setBackground(new java.awt.Color(255, 255, 255));
        panel_prestamos.setBorder(javax.swing.BorderFactory.createTitledBorder("Préstamos"));

        table_prestamos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Material", "Descripción", "Fecha Pedido", "Fecha Devuelto"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table_prestamos);

        javax.swing.GroupLayout panel_prestamosLayout = new javax.swing.GroupLayout(panel_prestamos);
        panel_prestamos.setLayout(panel_prestamosLayout);
        panel_prestamosLayout.setHorizontalGroup(
            panel_prestamosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_prestamosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel_prestamosLayout.setVerticalGroup(
            panel_prestamosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_prestamosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panel_consultarMisPrestamosLayout = new javax.swing.GroupLayout(panel_consultarMisPrestamos);
        panel_consultarMisPrestamos.setLayout(panel_consultarMisPrestamosLayout);
        panel_consultarMisPrestamosLayout.setHorizontalGroup(
            panel_consultarMisPrestamosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_consultarMisPrestamosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_filtroMateriales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel_prestamos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel_consultarMisPrestamosLayout.setVerticalGroup(
            panel_consultarMisPrestamosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_consultarMisPrestamosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_consultarMisPrestamosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panel_prestamos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_filtroMateriales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(129, Short.MAX_VALUE))
        );

        paneles_presta.addTab("Consultar Mis Préstamos", panel_consultarMisPrestamos);

        panel_agregarMateriales.setBackground(new java.awt.Color(255, 255, 255));

        panel_inventario.setBorder(javax.swing.BorderFactory.createTitledBorder("Inventario"));

        table_inventario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nombre", "Necesita Potencia"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        scrollPane_inventario.setViewportView(table_inventario);

        javax.swing.GroupLayout panel_inventarioLayout = new javax.swing.GroupLayout(panel_inventario);
        panel_inventario.setLayout(panel_inventarioLayout);
        panel_inventarioLayout.setHorizontalGroup(
            panel_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_inventarioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane_inventario, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel_inventarioLayout.setVerticalGroup(
            panel_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_inventarioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane_inventario, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        panel_agregarMaterial.setBorder(javax.swing.BorderFactory.createTitledBorder("Material A Agregar"));

        textField_nombreMaterial.setBorder(javax.swing.BorderFactory.createTitledBorder("Nombre:"));

        checkBox_Potencia.setText("Potencia");

        button_agregarMaterialInventario.setText("Aceptar");
        button_agregarMaterialInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_agregarMaterialInventarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_agregarMaterialLayout = new javax.swing.GroupLayout(panel_agregarMaterial);
        panel_agregarMaterial.setLayout(panel_agregarMaterialLayout);
        panel_agregarMaterialLayout.setHorizontalGroup(
            panel_agregarMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_agregarMaterialLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_agregarMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textField_nombreMaterial)
                    .addGroup(panel_agregarMaterialLayout.createSequentialGroup()
                        .addComponent(checkBox_Potencia, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 141, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_agregarMaterialLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(button_agregarMaterialInventario)))
                .addContainerGap())
        );
        panel_agregarMaterialLayout.setVerticalGroup(
            panel_agregarMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_agregarMaterialLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(textField_nombreMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkBox_Potencia)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(button_agregarMaterialInventario)
                .addContainerGap(189, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_agregarMaterialesLayout = new javax.swing.GroupLayout(panel_agregarMateriales);
        panel_agregarMateriales.setLayout(panel_agregarMaterialesLayout);
        panel_agregarMaterialesLayout.setHorizontalGroup(
            panel_agregarMaterialesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_agregarMaterialesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_agregarMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panel_inventario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel_agregarMaterialesLayout.setVerticalGroup(
            panel_agregarMaterialesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_agregarMaterialesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_agregarMaterialesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panel_inventario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_agregarMaterial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(109, Short.MAX_VALUE))
        );

        paneles_presta.addTab("Agregar Materiales", panel_agregarMateriales);

        javax.swing.GroupLayout panel_devolucionPrestamoLayout = new javax.swing.GroupLayout(panel_devolucionPrestamo);
        panel_devolucionPrestamo.setLayout(panel_devolucionPrestamoLayout);
        panel_devolucionPrestamoLayout.setHorizontalGroup(
            panel_devolucionPrestamoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 813, Short.MAX_VALUE)
        );
        panel_devolucionPrestamoLayout.setVerticalGroup(
            panel_devolucionPrestamoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 433, Short.MAX_VALUE)
        );

        paneles_presta.addTab("Devolución de Material", panel_devolucionPrestamo);

        javax.swing.GroupLayout panel_prestamoMaterialLayout = new javax.swing.GroupLayout(panel_prestamoMaterial);
        panel_prestamoMaterial.setLayout(panel_prestamoMaterialLayout);
        panel_prestamoMaterialLayout.setHorizontalGroup(
            panel_prestamoMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 813, Short.MAX_VALUE)
        );
        panel_prestamoMaterialLayout.setVerticalGroup(
            panel_prestamoMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 433, Short.MAX_VALUE)
        );

        paneles_presta.addTab("Préstamos de Material", panel_prestamoMaterial);

        panel_titulo.setBackground(new java.awt.Color(204, 204, 255));

        label_titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_titulo.setText("Préstamo (prototipo)");
        label_titulo.setToolTipText("");

        javax.swing.GroupLayout panel_tituloLayout = new javax.swing.GroupLayout(panel_titulo);
        panel_titulo.setLayout(panel_tituloLayout);
        panel_tituloLayout.setHorizontalGroup(
            panel_tituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_titulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panel_tituloLayout.setVerticalGroup(
            panel_tituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_titulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        label_titulo.getAccessibleContext().setAccessibleName("");

        label_nombreDelUsuario.setText("Usuario nombre");

        javax.swing.GroupLayout panel_nombreDelUsuarioLayout = new javax.swing.GroupLayout(panel_nombreDelUsuario);
        panel_nombreDelUsuario.setLayout(panel_nombreDelUsuarioLayout);
        panel_nombreDelUsuarioLayout.setHorizontalGroup(
            panel_nombreDelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_nombreDelUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_nombreDelUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel_nombreDelUsuarioLayout.setVerticalGroup(
            panel_nombreDelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_nombreDelUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_nombreDelUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(paneles_presta, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(panel_titulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_nombreDelUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_titulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(panel_nombreDelUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(paneles_presta))
        );

        paneles_presta.getAccessibleContext().setAccessibleName("");

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void button_agregarMaterialInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_agregarMaterialInventarioActionPerformed
        try {
            agregarMaterial();
        } catch (IOException ex) {
            Logger.getLogger(PrestaInterfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_button_agregarMaterialInventarioActionPerformed

    private void button_agregarMaterialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_agregarMaterialActionPerformed
        DefaultTableModel model = (DefaultTableModel) table_materialesPrestamo.getModel();
        String nombreMaterial = comboBox_materiales.getSelectedItem().toString();
        String descripcion = textField_descripcion.getText();
        String potencia = comboBox_potenciador.getSelectedItem().toString();
        int cantidad = Integer.parseInt(comboBox_cantidad.getSelectedItem().toString());
        for (int i = 0; i < cantidad; i++) {
            model.addRow(new Object[]{nombreMaterial, descripcion, potencia});
        }

    }//GEN-LAST:event_button_agregarMaterialActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PrestaInterfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrestaInterfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrestaInterfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrestaInterfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new PrestaInterfaz().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(PrestaInterfaz.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button_agregarMaterial;
    private javax.swing.JButton button_agregarMaterialInventario;
    private javax.swing.JButton button_enviarPrestamo;
    private javax.swing.JCheckBox checkBox_Potencia;
    private javax.swing.JComboBox<String> comboBox_cantidad;
    private javax.swing.JComboBox<String> comboBox_estadoFiltro;
    private javax.swing.JComboBox<String> comboBox_materialFiltro;
    private javax.swing.JComboBox<String> comboBox_materiales;
    private javax.swing.JComboBox<String> comboBox_potenciador;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel label_cantidad;
    private javax.swing.JLabel label_estadoMaterial;
    private javax.swing.JLabel label_generarPrestamoMaterial;
    private javax.swing.JLabel label_misPrestamosMaterial;
    private javax.swing.JLabel label_nombreDelUsuario;
    private javax.swing.JLabel label_potenciador;
    private javax.swing.JLabel label_prestamo;
    private javax.swing.JLabel label_titulo;
    private javax.swing.JPanel panel_agregarMaterial;
    private javax.swing.JPanel panel_agregarMateriales;
    private javax.swing.JPanel panel_consultarMisPrestamos;
    private javax.swing.JPanel panel_devolucionPrestamo;
    private javax.swing.JPanel panel_filtroMateriales;
    private javax.swing.JPanel panel_generarPrestamo;
    private javax.swing.JPanel panel_informacionUsuario;
    private javax.swing.JPanel panel_inventario;
    private javax.swing.JPanel panel_material;
    private javax.swing.JPanel panel_nombreDelUsuario;
    private javax.swing.JPanel panel_perfilUsuario;
    private javax.swing.JPanel panel_prestamo;
    private javax.swing.JPanel panel_prestamoMaterial;
    private javax.swing.JPanel panel_prestamos;
    private javax.swing.JPanel panel_titulo;
    private javax.swing.JPanel panel_tutor;
    private javax.swing.JTabbedPane paneles_presta;
    private javax.swing.JScrollPane scrollPane_inventario;
    private javax.swing.JScrollPane scrollPane_materialesPrestamo;
    private javax.swing.JTable table_inventario;
    private javax.swing.JTable table_materialesPrestamo;
    private javax.swing.JTable table_prestamos;
    private javax.swing.JTextField textField_descripcion;
    private javax.swing.JTextField textField_descripcionFiltro;
    private javax.swing.JTextField textField_identificador;
    private javax.swing.JTextField textField_nombreMaterial;
    private javax.swing.JTextField textField_numDevueltosFIltro;
    private javax.swing.JTextField textField_numPrestadosFiltro;
    private javax.swing.JTextField textField_tutorCorreo;
    private javax.swing.JTextField textField_tutorNombre;
    private javax.swing.JTextField textField_usuarioEmail;
    private javax.swing.JTextField textField_usuarioNombre;
    private javax.swing.JTextField textField_usuarioTipo;
    // End of variables declaration//GEN-END:variables
}
