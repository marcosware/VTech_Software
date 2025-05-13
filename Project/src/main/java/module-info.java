module Projeto_OS {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires spring.boot.autoconfigure;
    requires spring.boot;

    opens VdeVigilancia.Projeto_OS to javafx.fxml;
    opens VdeVigilancia.Projeto_OS.Application to javafx.fxml;
    opens VdeVigilancia.Projeto_OS.Models to javafx.fxml;
    exports VdeVigilancia.Projeto_OS;
    exports VdeVigilancia.Projeto_OS.Application;
    exports VdeVigilancia.Projeto_OS.Models;
}