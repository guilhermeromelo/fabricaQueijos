
package models;

import java.time.LocalDateTime;

public class Pedido {
    private int pedidoID;
    private String fk_client_cpf;
    private LocalDateTime pedidoDate;
    private double deliveryDeadLine;
    private String note;
    
    //RETIREI O ID DOS CONSTRUTORES POIS NO BANCO USEI O TIPO SERIAL QUE JÁ FAZ O AUTOINCREMENTO
    public Pedido() {
        this.fk_client_cpf = "N/C";
        this.pedidoDate = null;
        this.deliveryDeadLine = 0;
        this.note = "Sem observações";
    }
    //RETIREI O ID DOS CONSTRUTORES POIS NO BANCO USEI O TIPO SERIAL QUE JÁ FAZ O AUTOINCREMENTO
    public Pedido(String fk_client_cpf, LocalDateTime pedidoDate, double deliveryDeadLine, String note) {
        this.fk_client_cpf = fk_client_cpf;
        this.pedidoDate = pedidoDate;
        this.deliveryDeadLine = deliveryDeadLine;
        this.note = note;
    }

    public int getPedidoID() {
        return pedidoID;
    }

    public void setPedidoID(int pedidoID) {
        this.pedidoID = pedidoID;
    }

    public String getFk_client_cpf() {
        return fk_client_cpf;
    }

    public void setFk_client_cpf(String fk_client_cpf) {
        this.fk_client_cpf = fk_client_cpf;
    }

    public LocalDateTime getPedidoDate() {
        return pedidoDate;
    }

    public void setPedidoDate(LocalDateTime pedidoDate) {
        this.pedidoDate = pedidoDate;
    }

    public double getDeliveryDeadLine() {
        return deliveryDeadLine;
    }

    public void setDeliveryDeadLine(double deliveryDeadLine) {
        this.deliveryDeadLine = deliveryDeadLine;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Pedido{" + "pedidoID=" + pedidoID + ", fk_client_cpf=" + fk_client_cpf + ", pedidoDate=" + pedidoDate + ", deliveryDeadLine=" + deliveryDeadLine + ", note=" + note + '}';
    }
}
