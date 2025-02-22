package com.example.androidkt_2to4_6m.Realtimedatabase.firebase

import com.o7services.androidkotlin_9_11am.realtimedatabase.MenuModel

interface ClickInterface {
    fun editClick(menuModel: MenuModel, position:Int)
    fun deleteClick(menuModel: MenuModel,position: Int)


}