package silver.bars

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.hasElement
import com.natpryce.hamkrest.isEmpty
import org.junit.jupiter.api.Test
import silver.bars.domain.Direction.Sell
import silver.bars.domain.Order

class OrdersTest {
    private val orders = OrdersBoard()

    @Test fun `a new order board has no orders`() {
        assertThat(orders.summary(), isEmpty)
    }

    @Test fun `can register an order`() {
        val orderId = orders.register("user1", 3.5, 306, Sell)
        assertThat(orders.summary(), hasElement(Order(orderId, "user1", 3.5, 306, Sell)))
    }
}