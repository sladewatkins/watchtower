/*
 *  TwidereX
 *
 *  Copyright (C) 2020 Tlaster <tlaster@outlook.com>
 * 
 *  This file is part of TwidereX.
 * 
 *  TwidereX is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  TwidereX is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with TwidereX. If not, see <http://www.gnu.org/licenses/>.
 */
package com.twidere.services.twitter.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaV2(
    val url: String? = null,
    val height: Long? = null,

    @SerialName("media_key")
    val mediaKey: String? = null,

    val type: String? = null,
    val width: Long? = null,

    @SerialName("public_metrics")
    val publicMetrics: MediaPublicMetrics? = null,

    @SerialName("duration_ms")
    val durationMS: Long? = null,

    @SerialName("preview_image_url")
    val previewImageURL: String? = null
)
