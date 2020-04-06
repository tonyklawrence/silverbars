package silver.bars

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.hasElement
import org.junit.jupiter.api.Test
import silver.bars.Direction.Sell

class OrdersTest {

    @Test fun `register an order`() {
        val orders = OrdersBoard()
        val orderId = orders.register("user1", 3.5, 306, Sell)

        assertThat(orders.summary(), hasElement(Order(orderId, "user1", 3.5, 306, Sell)))
    }
}


enum class Direction { Buy, Sell }

data class Order(val id: OrderId, val userId: String, val quantity: Double, val price: Int, val direction: Direction)
data class OrderId(val id: String)

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
