package ulanapp.munduz.data.repository

import android.util.Log
import com.google.firebase.database.*
import ulanapp.munduz.data.models.Product
import ulanapp.munduz.data.models.SliderImage
import ulanapp.munduz.helpers.Constants.Companion.FIREBASE_ERROR_TITLE
import ulanapp.munduz.helpers.Constants.Companion.PRODUCTS_TABLE
import ulanapp.munduz.helpers.Constants.Companion.PRODUCT_CATEGORY
import ulanapp.munduz.helpers.Constants.Companion.PRODUCT_ID
import ulanapp.munduz.helpers.Constants.Companion.PRODUCT_RECOMMEND
import ulanapp.munduz.helpers.Constants.Companion.SLIDER_TABLE
import ulanapp.munduz.helpers.Constants.Companion.TAG
import ulanapp.munduz.interfaces.ProductCallback
import ulanapp.munduz.interfaces.ProductsCallback
import ulanapp.munduz.interfaces.SliderImagesCallback

class FirebaseRepositoryImpl : FirebaseRepository {

    private val database: DatabaseReference

    init {
        val firebase: FirebaseDatabase = FirebaseDatabase.getInstance()
        this.database = firebase.reference
    }

    override fun loadProducts(callback: ProductsCallback) {
        val products = mutableListOf<Product>()
        val productsRef = database.child(PRODUCTS_TABLE)
        productsRef.keepSynced(true)
        productsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (item: DataSnapshot in p0.children) {
                    val product: Product? = item.getValue(Product::class.java)
                    if (product?.visible == true) {
                        products.add(product)
                    }
                }
                callback.onCallback(products)
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d(TAG, FIREBASE_ERROR_TITLE + p0.message)
            }
        })
    }

    override fun loadProductsByRecommendation(callback: ProductsCallback) {
        val products = mutableListOf<Product>()
        val queryRef = database.child(PRODUCTS_TABLE).orderByChild(PRODUCT_RECOMMEND).equalTo(true)
        queryRef.keepSynced(true)
        queryRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (item: DataSnapshot in p0.children) {
                    val product: Product? = item.getValue(Product::class.java)
                    if (product?.visible == true) {
                        products.add(product)
                    }
                }
                callback.onCallback(products)
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d(TAG, FIREBASE_ERROR_TITLE + p0.message)
            }
        })
    }

    override fun loadProductsByCategory(category: String, callback: ProductsCallback) {
        val products = mutableListOf<Product>()
        val queryRef =
            database.child(PRODUCTS_TABLE).orderByChild(PRODUCT_CATEGORY).equalTo(category)
        queryRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (item: DataSnapshot in p0.children) {
                    val product: Product? = item.getValue(Product::class.java)
                    if (product?.visible == true) {
                        products.add(product)
                    }
                }
                callback.onCallback(products)
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d(TAG, FIREBASE_ERROR_TITLE + p0.message)
            }
        })
    }

    override fun loadProductByKey(key: String, callback: ProductCallback) {
        var product: Product? = null
        val queryRef = database.child(PRODUCTS_TABLE).orderByChild(PRODUCT_ID).equalTo(key)
        queryRef.keepSynced(true)
        queryRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (item: DataSnapshot in p0.children) {
                    val fetchedProduct: Product? = item.getValue(Product::class.java)
                    product = fetchedProduct!!
                }
                callback.onCallback(product)
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d(TAG, FIREBASE_ERROR_TITLE + p0.message)
            }
        })
    }

    override fun loadSliderPhotos(callback: SliderImagesCallback) {
        val sliderImages = ArrayList<SliderImage>()
        val productsRef = database.child(SLIDER_TABLE)
        productsRef.keepSynced(true)
        productsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (item: DataSnapshot in p0.children) {
                    val image = item.getValue(SliderImage::class.java)
                    sliderImages.add(image!!)
                }
                callback.onCallback(sliderImages.toList())
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d(TAG, FIREBASE_ERROR_TITLE + p0.message)
            }
        })
    }

}