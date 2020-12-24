
package models;

public class QueijoPedido {
    private int queijoPedidoID;
    private int fk_id_pedido;
    private int fk_id_queijo;
    private int quantity;

    public QueijoPedido() {
        this.queijoPedidoID = 0;
        this.fk_id_pedido = 0;
        this.fk_id_queijo = 0;
        this.quantity = 0;
    }

    public QueijoPedido(int queijoPedidoID, int fk_id_pedido, int fk_id_queijo, int quantity) {
        this.queijoPedidoID = queijoPedidoID;
        this.fk_id_pedido = fk_id_pedido;
        this.fk_id_queijo = fk_id_queijo;
        this.quantity = quantity;
    }

    public int getQueijoPedidoID() {
        return queijoPedidoID;
    }

    public void setQueijoPedidoID(int queijoPedidoID) {
        this.queijoPedidoID = queijoPedidoID;
    }

    public int getFk_id_pedido() {
        return fk_id_pedido;
    }

    public void setFk_id_pedido(int fk_id_pedido) {
        this.fk_id_pedido = fk_id_pedido;
    }

    public int getFk_id_queijo() {
        return fk_id_queijo;
    }

    public void setFk_id_queijo(int fk_id_queijo) {
        this.fk_id_queijo = fk_id_queijo;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "QueijoPedido{" + "id=" + queijoPedidoID + ", fk_id_pedido=" + fk_id_pedido + ", fk_id_queijo=" + fk_id_queijo + ", quantity=" + quantity + '}';
    }
    
}
