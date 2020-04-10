package com.ulan.app.munduz.ui.home

import com.ulan.app.munduz.data.model.SliderImage
import com.ulan.app.munduz.data.firebase.FirebaseRepository
import com.ulan.app.munduz.data.room.repository.KeysRepositoryImpl
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.listeners.ProductListCallback
import com.ulan.app.munduz.listeners.SliderImagesCallback
import javax.inject.Inject

class HomePresenterImpl : HomePresenter {

    private var mRepository: FirebaseRepository
    private var mRoomRepository: KeysRepositoryImpl
    private var mView: HomeView? = null

    @Inject
    constructor(view: HomeView, mRepository: FirebaseRepository, roomRepository: KeysRepositoryImpl) {
        this.mView = view
        this.mRepository = mRepository
        this.mRoomRepository = roomRepository
    }

    override fun setToolbar() {
        mView?.showToolbar()
    }

    override fun loadProducts() {
            mRepository.loadNewProducts(object : ProductListCallback {
                override fun onCallback(values: MutableList<Product>) {
                    if (values.size > 0) {
                        mView?.showProducts(values)
                    } else {
                        mView?.showEmptyData()
                    }
                }
            })
    }

    override fun loadSliderImages() {
            mRepository.loadSliderPhotos(object : SliderImagesCallback {
                override fun onCallback(value: ArrayList<SliderImage>) {
                    mView?.showSliderImages(value)
                }
            })
    }

    override fun detachView() {
        mView = null
    }

}