package com.capstone.diacare.domain.datasource

import com.capstone.diacare.data.local.ChatDao
import com.capstone.diacare.data.model.ChatEntity
import javax.inject.Inject

class ChatDataSource @Inject constructor(
    private val chatDao: ChatDao
) {

    suspend fun insertMessage(data: ChatEntity): Boolean {
        return try {
            chatDao.insertChat(data)
            true
        }catch (e : Exception){
            e.printStackTrace()
            false
        }
    }

    suspend fun getAllMessage() : List<ChatEntity>{
        return try {
            chatDao.getAllChat()
        }catch (e : Exception){
            e.printStackTrace()
            emptyList()
        }

    }
}