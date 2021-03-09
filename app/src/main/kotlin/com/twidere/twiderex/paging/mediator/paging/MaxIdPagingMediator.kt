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
package com.twidere.twiderex.paging.mediator.paging

import com.twidere.services.microblog.model.IStatus
import com.twidere.twiderex.db.CacheDatabase
import com.twidere.twiderex.db.model.DbPagingTimelineWithStatus
import com.twidere.twiderex.model.MicroBlogKey
import com.twidere.twiderex.notification.InAppNotification
import com.twidere.twiderex.paging.SinceMaxPagination

abstract class MaxIdPagingMediator(
    accountKey: MicroBlogKey,
    database: CacheDatabase,
    inAppNotification: InAppNotification
) : PagingTimelineMediatorBase<SinceMaxPagination>(accountKey, database, inAppNotification) {
    override fun provideNextPage(
        raw: List<IStatus>,
        result: List<DbPagingTimelineWithStatus>
    ): SinceMaxPagination {
        return SinceMaxPagination(maxId = result.lastOrNull()?.status?.status?.data?.statusId)
    }
}