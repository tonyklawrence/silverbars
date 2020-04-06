package silver.bars

import silver.bars.domain.Direction
import silver.bars.domain.Order
import silver.bars.domain.OrderId

class OrdersBoard {
    private val orders = mutableListOf<Order>()

    fun register(userId: String, quantity: Double, price: Int, direction: Direction): OrderId {
        val orderId = OrderId("foo")
        orders.add(Order(orderId, userId, quantity, price, direction))
        return orderId
    }

    fun summary(): List<Order> {
        return orders
    }
}