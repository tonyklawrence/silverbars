package silver.bars.domain

data class Order(val id: OrderId, val userId: String, val quantity: Double, val price: Int, val direction: Direction)