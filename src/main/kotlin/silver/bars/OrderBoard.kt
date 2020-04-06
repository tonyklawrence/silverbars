package silver.bars

import silver.bars.domain.Direction
import silver.bars.domain.Order
import silver.bars.domain.OrderId
import silver.bars.domain.OrderSummary

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

    fun summary(): List<OrderSummary> {
        val summaries = orders.map { OrderSummary(it.quantity, it.price, it.direction) }
        return summaries.groupingBy { it.price }
            .reduce { _, acc, summary -> acc.copy(quantity = acc.quantity + summary.quantity) }.values
            .toList()
    }
}

interface IdGenerator<T> {
    fun newId(): T
}