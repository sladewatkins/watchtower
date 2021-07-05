/*
 *  Twidere X
 *
 *  Copyright (C) 2020-2021 Tlaster <tlaster@outlook.com>
 * 
 *  This file is part of Twidere X.
 * 
 *  Twidere X is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  Twidere X is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with Twidere X. If not, see <http://www.gnu.org/licenses/>.
 */
package com.twidere.twiderex.worker.dm

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.twidere.services.microblog.LookupService
import com.twidere.services.twitter.TwitterService
import com.twidere.twiderex.db.CacheDatabase
import com.twidere.twiderex.db.mapper.autolink
import com.twidere.twiderex.db.mapper.toDbDirectMessage
import com.twidere.twiderex.db.mapper.toDbUser
import com.twidere.twiderex.db.model.DbDMEventWithAttachments
import com.twidere.twiderex.db.model.DbUser
import com.twidere.twiderex.model.DirectMessageSendData
import com.twidere.twiderex.model.MicroBlogKey
import com.twidere.twiderex.model.toWorkData
import com.twidere.twiderex.repository.AccountRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class TwitterDirectMessageSendWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    accountRepository: AccountRepository,
    notificationManagerCompat: NotificationManagerCompat,
    contentResolver: ContentResolver,
    cacheDatabase: CacheDatabase,
) : DirectMessageSendWorker<TwitterService>(
    context,
    workerParams,
    cacheDatabase,
    contentResolver,
    accountRepository,
    notificationManagerCompat
) {
    companion object {
        fun create(
            data: DirectMessageSendData,
        ) = OneTimeWorkRequestBuilder<TwitterDirectMessageSendWorker>()
            .setInputData(
                Data.Builder()
                    .putAll(data.toWorkData())
                    .build()
            )
            .build()
    }

    override suspend fun sendMessage(
        service: TwitterService,
        sendData: DirectMessageSendData,
        mediaIds: ArrayList<String>
    ): DbDMEventWithAttachments = service.sendDirectMessage(
        recipientId = sendData.recipientUserKey.id,
        text = sendData.text,
        attachmentType = "media",
        mediaId = mediaIds.firstOrNull()
    )?.toDbDirectMessage(
        accountKey = sendData.accountKey,
        sender = lookUpUser(cacheDatabase, sendData.accountKey, service)
    ) ?: throw Error()

    private suspend fun lookUpUser(database: CacheDatabase, userKey: MicroBlogKey, service: TwitterService): DbUser {
        return database.userDao().findWithUserKey(userKey) ?: let {
            val user = (service as LookupService).lookupUser(userKey.id)
                .toDbUser(userKey)
            database.userDao().insertAll(listOf(user))
            user
        }
    }

    override suspend fun uploadImage(
        originUri: Uri,
        scramblerUri: Uri,
        service: TwitterService
    ): String? {
        val type = contentResolver.getType(originUri)
        val size = contentResolver.openFileDescriptor(scramblerUri, "r")?.statSize
        return contentResolver.openInputStream(scramblerUri)?.use {
            service.uploadFile(
                it,
                type ?: "image/*",
                size ?: it.available().toLong()
            )
        } ?: throw Error()
    }

    override suspend fun autoLink(text: String): String {
        return autolink.autoLink(text)
    }
}