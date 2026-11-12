package com.example.kmpapp.dinebite.ui

data class FoodItem(
    val id: String,
    val name: String,
    val description: String,
    val price: Int,
    val rating: Double,
    val reviews: String,
    val imageUrl: String,
    val category: String,
    val time: String = "18-22 min",
    val kcal: String = "720 kcal"
)

data class CategoryItem(
    val name: String,
    val imageUrl: String
)

val sharedImageUrls = listOf(
    "https://images.unsplash.com/photo-1579751626657-72bc17010498?w=500",
    "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=500",
    "https://images.unsplash.com/photo-1573080496219-bb080dd4f877?w=500",
    "https://images.unsplash.com/photo-1578985545062-69928b1d9587?w=500",
    "https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=500",
    "https://images.unsplash.com/photo-1473093295043-cdd812d0e601?w=500",
    "https://images.unsplash.com/photo-1603894584373-5ac82b2ae398?w=500",
    "https://images.unsplash.com/photo-1461023058943-07fcbe16d735?w=500"
)

val categoriesList = listOf(
    CategoryItem("All", sharedImageUrls[0]),
    CategoryItem("Burgers", sharedImageUrls[1]),
    CategoryItem("Pizzas", sharedImageUrls[3]),
    CategoryItem("Pastas", sharedImageUrls[4]),
    CategoryItem("Bowls", sharedImageUrls[4]),
    CategoryItem("Desserts", sharedImageUrls[7])
)

val sampleFoodItems = listOf(
    FoodItem(
        "1", "Classic Smash Cheeseburger",
        "Dual smashed prime beef patties, aged cheddar, caramelised onions...",
        249, 4.8, "2.4k",
        sharedImageUrls[1],
        "Burgers"
    ),
    FoodItem(
        "2", "Crispy Nashville Hot Chicken",
        "Spicy buttermilk fried chicken breast dipped in fiery Nashville...",
        289, 4.9, "1.8k",
        sharedImageUrls[2],
        "Burgers"
    ),
    FoodItem(
        "3", "Margherita Pizza",
        "Fresh mozzarella, tomatoes, and basil on artisan sourdough crust.",
        471, 4.7, "3.1k",
        sharedImageUrls[3],
        "Pizzas"
    ),
    FoodItem(
        "4", "Creamy Alfredo Pasta",
        "House-extracted egg tagliorini ribbon pasta tossed in rich sauce.",
        299, 4.8, "1.2k",
        sharedImageUrls[4],
        "Pastas"
    ),
    FoodItem(
        "5", "Truffle Swiss Mushroom Burger",
        "Flame-grilled patty, sautéed wild porcini mushrooms, melted Swiss...",
        329, 4.7, "920",
        sharedImageUrls[5],
        "Burgers"
    ),
    FoodItem(
        "6", "Pepperoni & Hot Honey Crisp",
        "Slow-fermented sourdough crust topped with artisanal spicy pork...",
        449, 4.9, "2.2k",
        sharedImageUrls[6],
        "Pizzas"
    )
)
