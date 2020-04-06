package silver.bars

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.hasElement
import org.junit.jupiter.api.Test
import silver.bars.domain.Direction.Sell
import silver.bars.domain.Order

class OrdersTest {

    @Test fun `register an order`() {
        val orders = OrdersBoard()
        val orderId = orders.register("user1", 3.5, 306, Sell)

        assertThat(orders.summary(), hasElement(Order(orderId, "user1", 3.5, 306, Sell)))
    }
}