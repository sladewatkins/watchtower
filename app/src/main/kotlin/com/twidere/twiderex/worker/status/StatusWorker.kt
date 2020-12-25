/*
 *  Twidere X
 *
 *  Copyright (C) 2020 Tlaster <tlaster@outlook.com>
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
package com.twidere.twiderex.worker.status

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.twidere.services.http.MicroBlogException
import com.twidere.services.microblog.StatusService
import com.twidere.services.microblog.model.IStatus
import com.twidere.services.twitter.TwitterErrorCodes
import com.twidere.twiderex.db.mapper.toDbTimeline
import com.twidere.twiderex.db.model.TimelineType
import com.twidere.twiderex.model.MicroBlogKey
import com.twidere.twiderex.model.ui.UiStatus
import com.twidere.twiderex.model.ui.UiStatus.Companion.toUi
import com.twidere.twiderex.repository.AccountRepository
import com.twidere.twiderex.repository.StatusRepository

abstract class StatusWorker(
    appContext: Context,
    params: WorkerParameters,
    private val accountRepository: AccountRepository,
    private val statusRepository: StatusRepository,
) : CoroutineWorker(appContext, params) {
    companion object {
        inline fun <reified T : StatusWorker> create(
            accountKey: MicroBlogKey,
            status: UiStatus,
        ) = OneTimeWorkRequestBuilder<T>()
            .setInputData(
                workDataOf(
                    "accountKey" to accountKey.toString(),
                    "statusKey" to status.statusKey.toString(),
                )
            )
            .build()

        fun createWorkData(accountKey: MicroBlogKey, status: UiStatus) =
            workDataOf(
                "statusKey" to status.statusKey.toString(),
                "accountKey" to accountKey.toString(),
                "liked" to status.liked,
                "retweeted" to status.retweeted,
                "retweetCount" to status.retweetCount,
                "likeCount" to status.likeCount,
            )
    }

    override suspend fun doWork(): Result {
        val accountKey = inputData.getString("accountKey")?.let {
            MicroBlogKey.valueOf(it)
        } ?: return Result.failure()
        val status = inputData.getString("statusKey")?.let {
            MicroBlogKey.valueOf(it)
        }?.let {
            statusRepository.loadFromCache(it, accountKey = accountKey)
        } ?: return Result.failure()
        val service = accountRepository.findByAccountKey(accountKey)?.let {
            accountRepository.getAccountDetails(it)
        }?.let {
            it.service as? StatusService
        } ?: return Result.failure()
        return try {
            val result = doWork(service, status)
                .toDbTimeline(accountKey = accountKey, timelineType = TimelineType.Custom)
                .toUi(accountKey = accountKey)
            Result.success(
                createWorkData(accountKey = accountKey, status = result)
            )
        } catch (e: MicroBlogException) {
            e.errors?.firstOrNull()?.let {
                when (it.code) {
                    TwitterErrorCodes.AlreadyRetweeted -> {
                        Result.success(
                            createWorkData(accountKey, status.copy(retweeted = true))
                        )
                    }
                    TwitterErrorCodes.AlreadyFavorited -> {
                        Result.success(
                            createWorkData(accountKey, status.copy(liked = true))
                        )
                    }
                    else -> {
                        Result.failure()
                    }
                }
            } ?: Result.failure()
        } catch (e: Throwable) {
            Result.failure()
        }
    }

    protected abstract suspend fun doWork(
        service: StatusService,
        status: UiStatus,
    ): IStatus
}