package VdeVigilancia.Projeto_OS.Models;

public enum Status {
    ABERTA,
    EM_ANDAMENTO,
    FECHADA,
    CANCELADA;

    public String toString(Status status) {
        String result;
        switch(status) {
            case ABERTA:
                result = "Aberta";
                break;
            case EM_ANDAMENTO:
                result = "Em andamento";
                break;
            case FECHADA:
                result = "Fechada";
                break;
            case CANCELADA:
                result = "Cancelada";
                break;
            default:
                result = "Erro";
                break;
        }
        return result;
    }
}
