package Dominio;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity

public class Historico_OS implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)

    private Integer id;

    private LocalDateTime data;

    private String evento;

    @Column (columnDefinition = "text", nullable = false)
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "OS")

    private OS os;

    public Historico_OS() {
    }

    public Historico_OS(Integer id, LocalDateTime data, String evento, String observacoes, OS os) {
        this.id = id;
        this.data = data;
        this.evento = evento;
        this.observacoes = observacoes;
        this.os = os;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public OS getOs() {
        return os;
    }

    public void setOs(OS os) {
        this.os = os;
    }


}
