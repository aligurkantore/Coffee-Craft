package com.example.coffeeapp.util

import android.content.Context
import com.example.coffeeapp.R
import com.example.coffeeapp.models.category.Category
import com.example.coffeeapp.models.coffee.CoffeeResponseModel
import com.example.coffeeapp.models.profile.Language
import javax.inject.Inject

class CoffeeUtil @Inject constructor() {
    fun getCoffeeList(context: Context): MutableList<CoffeeResponseModel> {
        val coffeeList = mutableListOf<CoffeeResponseModel>()
        coffeeList.add(
            CoffeeResponseModel(
                "21",
                context.getString(R.string.americano),
                context.getString(R.string.description_americano),
                context.getString(R.string.medium_roasted),
                R.drawable.americano_pic_1_portrait,
                context.getString(R.string.milk),
                context.getString(R.string.with_milk),
                listOf(
                    CoffeeResponseModel.CoffeePrice("S", 1.38, "$"),
                    CoffeeResponseModel.CoffeePrice("M", 3.15, "$"),
                    CoffeeResponseModel.CoffeePrice("L", 4.29, "$")
                ),
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )

        coffeeList.add(
            CoffeeResponseModel(
                "22",
                context.getString(R.string.americano),
                context.getString(R.string.description_americano),
                context.getString(R.string.medium_roasted),
                R.drawable.americano_pic_2_portrait,
                context.getString(R.string.milk),
                context.getString(R.string.cream),
                listOf(
                    CoffeeResponseModel.CoffeePrice("S", 1.38, "$"),
                    CoffeeResponseModel.CoffeePrice("M", 3.15, "$"),
                    CoffeeResponseModel.CoffeePrice("L", 4.29, "$")
                ),
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )

        coffeeList.add(
            CoffeeResponseModel(
                "23",
                context.getString(R.string.americano),
                context.getString(R.string.description_americano),
                context.getString(R.string.medium_roasted),
                R.drawable.americano_pic_3_portrait,
                context.getString(R.string.milk),
                context.getString(R.string.ice),
                listOf(
                    CoffeeResponseModel.CoffeePrice("S", 1.38, "$"),
                    CoffeeResponseModel.CoffeePrice("M", 3.15, "$"),
                    CoffeeResponseModel.CoffeePrice("L", 4.29, "$")
                ),
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )

        coffeeList.add(
            CoffeeResponseModel(
                "24",
                context.getString(R.string.black_coffee),
                context.getString(R.string.description_black_coffee),
                context.getString(R.string.medium_roasted),
                R.drawable.black_coffee_pic_1_portrait,
                context.getString(R.string.milk),
                context.getString(R.string.chocolate),
                listOf(
                    CoffeeResponseModel.CoffeePrice("S", 1.38, "$"),
                    CoffeeResponseModel.CoffeePrice("M", 3.15, "$"),
                    CoffeeResponseModel.CoffeePrice("L", 4.29, "$")
                ),
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )

        coffeeList.add(
            CoffeeResponseModel(
                "25",
                context.getString(R.string.black_coffee),
                context.getString(R.string.description_black_coffee),
                context.getString(R.string.medium_roasted),
                R.drawable.black_coffee_pic_2_portrait,
                context.getString(R.string.milk),
                context.getString(R.string.white_chocolate),
                listOf(
                    CoffeeResponseModel.CoffeePrice("S", 1.38, "$"),
                    CoffeeResponseModel.CoffeePrice("M", 3.15, "$"),
                    CoffeeResponseModel.CoffeePrice("L", 4.29, "$")
                ),
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )

        coffeeList.add(
            CoffeeResponseModel(
                "26",
                context.getString(R.string.black_coffee),
                context.getString(R.string.description_black_coffee),
                context.getString(R.string.medium_roasted),
                R.drawable.black_coffee_pic_3_portrait,
                context.getString(R.string.milk),
                context.getString(R.string.vanilla),
                listOf(
                    CoffeeResponseModel.CoffeePrice("S", 1.38, "$"),
                    CoffeeResponseModel.CoffeePrice("M", 3.15, "$"),
                    CoffeeResponseModel.CoffeePrice("L", 4.29, "$")
                ),
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )

        coffeeList.add(
            CoffeeResponseModel(
                "27",
                context.getString(R.string.cappuccino),
                context.getString(R.string.description_cappuccino),
                context.getString(R.string.medium_roasted),
                R.drawable.cappuccino_pic_1_portrait,
                context.getString(R.string.milk),
                context.getString(R.string.caramel),
                listOf(
                    CoffeeResponseModel.CoffeePrice("S", 1.38, "$"),
                    CoffeeResponseModel.CoffeePrice("M", 3.15, "$"),
                    CoffeeResponseModel.CoffeePrice("L", 4.29, "$")
                ),
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )

        coffeeList.add(
            CoffeeResponseModel(
                "28",
                context.getString(R.string.cappuccino),
                context.getString(R.string.description_cappuccino),
                context.getString(R.string.medium_roasted),
                R.drawable.cappuccino_pic_2_portrait,
                context.getString(R.string.milk),
                context.getString(R.string.hazelnut),
                listOf(
                    CoffeeResponseModel.CoffeePrice("S", 1.38, "$"),
                    CoffeeResponseModel.CoffeePrice("M", 3.15, "$"),
                    CoffeeResponseModel.CoffeePrice("L", 4.29, "$")
                ),
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )

        coffeeList.add(
            CoffeeResponseModel(
                "29",
                context.getString(R.string.cappuccino),
                context.getString(R.string.description_cappuccino),
                context.getString(R.string.medium_roasted),
                R.drawable.cappuccino_pic_3_portrait,
                context.getString(R.string.milk),
                context.getString(R.string.caramel),
                listOf(
                    CoffeeResponseModel.CoffeePrice("S", 1.38, "$"),
                    CoffeeResponseModel.CoffeePrice("M", 3.15, "$"),
                    CoffeeResponseModel.CoffeePrice("L", 4.29, "$")
                ),
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )

        coffeeList.add(
            CoffeeResponseModel(
                "30",
                context.getString(R.string.espresso),
                context.getString(R.string.description_espresso),
                context.getString(R.string.medium_roasted),
                R.drawable.espresso_pic_1_portrait,
                context.getString(R.string.milk),
                context.getString(R.string.cream),
                listOf(
                    CoffeeResponseModel.CoffeePrice("S", 1.38, "$"),
                    CoffeeResponseModel.CoffeePrice("M", 3.15, "$"),
                    CoffeeResponseModel.CoffeePrice("L", 4.29, "$")
                ),
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)

            )
        )
        coffeeList.add(
            CoffeeResponseModel(
                "31",
                context.getString(R.string.espresso),
                context.getString(R.string.description_espresso),
                context.getString(R.string.medium_roasted),
                R.drawable.espresso_pic_2_portrait,
                context.getString(R.string.milk),
                context.getString(R.string.chocolate),
                listOf(
                    CoffeeResponseModel.CoffeePrice("S", 1.38, "$"),
                    CoffeeResponseModel.CoffeePrice("M", 3.15, "$"),
                    CoffeeResponseModel.CoffeePrice("L", 4.29, "$")
                ),
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )

        coffeeList.add(
            CoffeeResponseModel(
                "32",
                context.getString(R.string.espresso),
                context.getString(R.string.description_espresso),
                context.getString(R.string.medium_roasted),
                R.drawable.espresso_pic_3_portrait,
                context.getString(R.string.milk),
                context.getString(R.string.white_chocolate),
                listOf(
                    CoffeeResponseModel.CoffeePrice("S", 1.38, "$"),
                    CoffeeResponseModel.CoffeePrice("M", 3.15, "$"),
                    CoffeeResponseModel.CoffeePrice("L", 4.29, "$")
                ),
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )

        coffeeList.add(
            CoffeeResponseModel(
                "33",
                context.getString(R.string.latte),
                context.getString(R.string.description_latte),
                context.getString(R.string.medium_roasted),
                R.drawable.latte_pic_1_portrait,
                context.getString(R.string.milk),
                context.getString(R.string.hazelnut),
                listOf(
                    CoffeeResponseModel.CoffeePrice("S", 1.38, "$"),
                    CoffeeResponseModel.CoffeePrice("M", 3.15, "$"),
                    CoffeeResponseModel.CoffeePrice("L", 4.29, "$")
                ),
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )

        coffeeList.add(
            CoffeeResponseModel(
                "34",
                context.getString(R.string.latte),
                context.getString(R.string.description_latte),
                context.getString(R.string.medium_roasted),
                R.drawable.latte_pic_2_portrait,
                context.getString(R.string.milk),
                context.getString(R.string.with_milk),
                listOf(
                    CoffeeResponseModel.CoffeePrice("S", 1.38, "$"),
                    CoffeeResponseModel.CoffeePrice("M", 3.15, "$"),
                    CoffeeResponseModel.CoffeePrice("L", 4.29, "$")
                ),
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )

        coffeeList.add(
            CoffeeResponseModel(
                "35",
                context.getString(R.string.latte),
                context.getString(R.string.description_latte),
                context.getString(R.string.medium_roasted),
                R.drawable.latte_pic_3_portrait,
                context.getString(R.string.milk),
                context.getString(R.string.ice),
                listOf(
                    CoffeeResponseModel.CoffeePrice("S", 1.38, "$"),
                    CoffeeResponseModel.CoffeePrice("M", 3.15, "$"),
                    CoffeeResponseModel.CoffeePrice("L", 4.29, "$")
                ),
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )

        coffeeList.add(
            CoffeeResponseModel(
                "36",
                context.getString(R.string.macchiato),
                context.getString(R.string.description_macchiato),
                context.getString(R.string.medium_roasted),
                R.drawable.macchiato_pic_1_portrait,
                context.getString(R.string.milk),
                context.getString(R.string.cream),
                listOf(
                    CoffeeResponseModel.CoffeePrice("S", 1.38, "$"),
                    CoffeeResponseModel.CoffeePrice("M", 3.15, "$"),
                    CoffeeResponseModel.CoffeePrice("L", 4.29, "$")
                ),
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )

        coffeeList.add(
            CoffeeResponseModel(
                "37",
                context.getString(R.string.macchiato),
                context.getString(R.string.description_macchiato),
                context.getString(R.string.medium_roasted),
                R.drawable.macchiato_pic_2_portrait,
                context.getString(R.string.milk),
                context.getString(R.string.vanilla),
                listOf(
                    CoffeeResponseModel.CoffeePrice("S", 1.38, "$"),
                    CoffeeResponseModel.CoffeePrice("M", 3.15, "$"),
                    CoffeeResponseModel.CoffeePrice("L", 4.29, "$")
                ),
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )

        coffeeList.add(
            CoffeeResponseModel(
                "38",
                context.getString(R.string.macchiato),
                context.getString(R.string.description_macchiato),
                context.getString(R.string.medium_roasted),
                R.drawable.macchiato_pic_3_portrait,
                context.getString(R.string.milk),
                context.getString(R.string.with_milk),
                listOf(
                    CoffeeResponseModel.CoffeePrice("S", 1.38, "$"),
                    CoffeeResponseModel.CoffeePrice("M", 3.15, "$"),
                    CoffeeResponseModel.CoffeePrice("L", 4.29, "$")
                ),
                4.7,
                context.getString(R.string.rating_counts),
                context.getString(R.string.coffee)
            )
        )

        return coffeeList
    }


    fun getCategoryNameList(context: Context): List<Category> {
        val categoryList = mutableListOf<Category>()
        categoryList.add(Category(null, context.getString(R.string.all)))
        categoryList.add(Category(null, context.getString(R.string.americano)))
        categoryList.add(Category(null, context.getString(R.string.black_coffee)))
        categoryList.add(Category(null, context.getString(R.string.cappuccino)))
        categoryList.add(Category(null, context.getString(R.string.espresso)))
        categoryList.add(Category(null, context.getString(R.string.latte)))
        categoryList.add(Category(null, context.getString(R.string.macchiato)))
        return categoryList
    }

    fun getProfileCategoryList(context: Context): List<Category> {
        val profileCategoryList = mutableListOf<Category>()
        profileCategoryList.add(
            Category(
                R.drawable.order_history,
                context.getString(R.string.order_history)
            )
        )
        profileCategoryList.add(
            Category(
                R.drawable.shopping_bag,
                context.getString(R.string.my_cart)
            )
        )
        profileCategoryList.add(
            Category(
                R.drawable.like,
                context.getString(R.string.my_favorites)
            )
        )
        profileCategoryList.add(
            Category(
                R.drawable.payment,
                context.getString(R.string.payment_information)
            )
        )
        profileCategoryList.add(
            Category(
                R.drawable.language,
                context.getString(R.string.languages)
            )
        )
        profileCategoryList.add(
            Category(
                R.drawable.currency,
                context.getString(R.string.currency)
            )
        )
        profileCategoryList.add(
            Category(
                R.drawable.notification,
                context.getString(R.string.notification)
            )
        )
        profileCategoryList.add(
            Category(
                R.drawable.help,
                context.getString(R.string.help)
            )
        )
        profileCategoryList.add(
            Category
                (
                R.drawable.log_out,
                context.getString(R.string.log_out)
            )
        )
        return profileCategoryList
    }

    fun getLanguageList(context: Context): List<Language>{
        val languageList = mutableListOf<Language>()
        languageList.add(Language(context.getString(R.string.english)))
        languageList.add(Language(context.getString(R.string.turkish)))
        languageList.add(Language(context.getString(R.string.german)))
        languageList.add(Language(context.getString(R.string.french)))
        return languageList
    }
}