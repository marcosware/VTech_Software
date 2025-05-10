module VdeVigilancia.Projeto_OS {
    requires javafx.fxml;
    requires javafx.controls;

    requires java.desktop;
    requires com.dlsc.formsfx;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires java.sql;
    requires spring.data.relational;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires kernel;
    requires layout;
    exports VdeVigilancia.Projeto_OS;
    opens VdeVigilancia.Projeto_OS to javafx.fxml;
    opens VdeVigilancia.Projeto_OS.Dominio to org.hibernate.orm.core;




}