package com.example.eccomerceappcustomer

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {

    @Insert
    suspend fun insertProduct(product: ProductModelRoom)

    @Delete
    suspend fun deleteProduct(product: ProductModelRoom)

    @Query("SELECT * FROM products")
    fun getAllProducts() : LiveData<List<ProductModelRoom>>

    @Query("SELECT * FROM products WHERE productId = :id")
    fun isExit(id:String): ProductModelRoom

}