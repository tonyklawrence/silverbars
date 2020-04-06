package silver.bars

import silver.bars.domain.Direction
import silver.bars.domain.Order
import silver.bars.domain.OrderId

class OrderBoard(private val orderIds: IdGenerator<OrderId>) {
    private val orders = mutableListOf<Order>()

    fun register(userId: String, quantity: Double, price: Int, direction: Direction): OrderId {
        val orderId = orderIds.newId()
        orders.add(Order(orderId, userId, quantity, price, direction))
        return orderId
    }

    fun cancel(orderId: OrderId): OrderNotFound? =
        when (orders.removeIf { it.id == orderId }) {
            false -> OrderNotFound(orderId)
            true -> null
        }

    fun summary(): List<Order> = orders
}

interface IdGenerator<T> {
    fun newId(): T
}