package VdeVigilancia.Projeto_OS.Dominio;

public enum StatusOS {
    ABERTA ("Aberta"),
    EM_ANDAMENTO ("Em andamento"),
    FECHADA ("Fechada"),
    CANCELADA ("Cancelada");

    private String descricao;

    StatusOS (String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return descricao;
    }

    public static StatusOS fromDescicao (String descricao){
        for (StatusOS status : StatusOS.values()){
            if (status.descricao.equalsIgnoreCase(descricao)){
                return status;
            }
        }
        throw new IllegalArgumentException("Status com descrição " + descricao + " não encontrada");
    }
}
